// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Surface;
import io.github.programmiererefz.codegschpaenli.world.Wall;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Finds horizontal edges of IDE components that pets can walk on, and vertical borders they can climb, by walking
 * the Swing hierarchy of a frame.
 *
 * <p>Components are recognised by the simple names of their classes <em>and superclasses</em>, so that
 * internal subclasses introduced by newer IDE versions are still matched. Only names are used (no compile-time
 * dependency on internal classes), which keeps the plugin working across IDE releases.</p>
 */
final class SurfaceScanner {

    private enum Edge { TOP, BOTTOM, CENTER }

    /**
     * @param className simple class name to match; a trailing '*' matches any name with that prefix
     * @param walls     whether the component's vertical borders are climbable
     */
    private record Rule(String className, Edge edge, Category category, String label, boolean walls) {
        Rule(String className, Edge edge, Category category, String label) {
            this(className, edge, category, label, false);
        }

        boolean matches(String simpleName) {
            return className.endsWith("*")
                    ? simpleName.startsWith(className.substring(0, className.length() - 1))
                    : className.equals(simpleName);
        }
    }

    private enum Category { STATUS_BAR, TOOL_WINDOWS, EDITOR_TABS, TOOLBARS, PANELS }

    private static final List<Rule> RULES = List.of(
            new Rule("IdeStatusBarImpl", Edge.TOP, Category.STATUS_BAR, "status bar"),
            new Rule("InternalDecorator", Edge.TOP, Category.TOOL_WINDOWS, "tool window", true),
            new Rule("ToolWindowHeader", Edge.TOP, Category.TOOL_WINDOWS, "tool window header"),
            new Rule("JBRunnerTabs", Edge.TOP, Category.TOOL_WINDOWS, "run/debug tabs"), // subclass of JBEditorTabs
            new Rule("SingleHeightTabs", Edge.TOP, Category.TOOL_WINDOWS, "tool window tabs"), // ditto
            new Rule("JBEditorTabs", Edge.TOP, Category.EDITOR_TABS, "editor tabs", true),
            new Rule("MainToolbar", Edge.BOTTOM, Category.TOOLBARS, "main toolbar"),
            new Rule("ToolbarFrameHeader", Edge.BOTTOM, Category.TOOLBARS, "window header"),
            new Rule("NewNavBarPanel", Edge.TOP, Category.TOOLBARS, "navigation bar"),
            new Rule("NavBarPanel", Edge.TOP, Category.TOOLBARS, "navigation bar"),
            new Rule("NavBarContainer", Edge.TOP, Category.TOOLBARS, "navigation bar"),
            new Rule("ActionToolbarImpl", Edge.TOP, Category.TOOLBARS, "toolbar"),
            new Rule("BreadcrumbsWrapper", Edge.TOP, Category.PANELS, "breadcrumbs"),
            new Rule("Breadcrumbs", Edge.TOP, Category.PANELS, "breadcrumbs"),
            new Rule("EditorNotificationPanel", Edge.TOP, Category.PANELS, "editor banner"),
            new Rule("OnePixelDivider", Edge.CENTER, Category.PANELS, "divider", true),
            new Rule("ConsoleViewImpl", Edge.TOP, Category.PANELS, "console"),
            new Rule("FindPopupPanel", Edge.TOP, Category.PANELS, "find bar"),
            new Rule("SearchReplaceComponent", Edge.TOP, Category.PANELS, "find bar")
    );

    private SurfaceScanner() {
    }

    /** Walkable surfaces and climbable walls of one window, in layered-pane coordinates. */
    record Result(List<Surface> surfaces, List<Wall> walls) {
    }

    static Result scan(JLayeredPane layeredPane, Component exclude, PetsSettings.StateBean s) {
        List<Surface> out = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();
        Rectangle world = new Rectangle(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        walk(layeredPane, layeredPane, exclude, s, world, out, walls, 0);
        return new Result(dropShadowed(merge(out)), mergeWalls(walls));
    }

    private static void walk(Component c, JLayeredPane lp, Component exclude, PetsSettings.StateBean s,
                             Rectangle world, List<Surface> out, List<Wall> walls, int depth) {
        if (c == exclude || !c.isVisible() || c.getWidth() <= 0 || c.getHeight() <= 0 || depth > 60) return;
        if (c != lp) {
            Rule rule = match(c);
            if (rule != null && enabled(rule.category(), s)) {
                addSurface(c, rule, lp, world, out);
                if (rule.walls()) addWalls(c, rule, lp, world, walls);
            }
        }
        if (c instanceof Container container && !isLeafForScanning(c)) {
            for (Component child : container.getComponents()) {
                walk(child, lp, exclude, s, world, out, walls, depth + 1);
            }
        }
    }

    /** The left and right borders of tool windows, and vertical dividers, are climbable. */
    private static void addWalls(Component c, Rule rule, JLayeredPane lp, Rectangle world, List<Wall> walls) {
        if (!c.isShowing()) return;
        Rectangle visible = c instanceof JComponent jc ? jc.getVisibleRect() : new Rectangle(0, 0, c.getWidth(), c.getHeight());
        Rectangle r = SwingUtilities.convertRectangle(c, visible, lp).intersection(world);
        if (r.isEmpty()) return;
        if (rule.edge() == Edge.CENTER) {
            if (c.getHeight() > c.getWidth() * 3) {
                walls.add(new Wall(c, rule.label(), r.x + r.width / 2.0, r.y, r.y + r.height, true, true));
            }
            return;
        }
        walls.add(new Wall(c, rule.label() + " left", r.x, r.y, r.y + r.height, true, true));
        walls.add(new Wall(c, rule.label() + " right", r.x + r.width, r.y, r.y + r.height, true, true));
    }

    /** Joins walls at the same x whose vertical ranges overlap or touch. */
    private static List<Wall> mergeWalls(List<Wall> in) {
        List<Wall> sorted = new ArrayList<>(in);
        sorted.sort(Comparator.comparingDouble(Wall::x).thenComparingDouble(Wall::y1));
        List<Wall> out = new ArrayList<>();
        for (Wall w : sorted) {
            boolean merged = false;
            for (int i = 0; i < out.size(); i++) {
                Wall o = out.get(i);
                if (Math.abs(o.x() - w.x()) <= 3 && w.y1() <= o.y2() + 2 && w.y2() >= o.y1() - 2) {
                    Wall taller = o.height() >= w.height() ? o : w;
                    out.set(i, new Wall(taller.key(), taller.label(), o.x(), Math.min(o.y1(), w.y1()),
                            Math.max(o.y2(), w.y2()), o.fromLeft() || w.fromLeft(), o.fromRight() || w.fromRight()));
                    merged = true;
                    break;
                }
            }
            if (!merged) out.add(w);
        }
        return out;
    }

    /**
     * Drops surfaces that sit just a few pixels below a wider one (e.g. the navigation bar inside the status bar);
     * pets on them would look like they stand on the same line anyway.
     */
    private static List<Surface> dropShadowed(List<Surface> in) {
        List<Surface> out = new ArrayList<>();
        for (Surface s : in) {
            boolean shadowed = false;
            for (Surface o : in) {
                if (o == s || o.y() >= s.y() || s.y() - o.y() > 8) continue;
                double overlap = Math.min(o.x2(), s.x2()) - Math.max(o.x1(), s.x1());
                if (overlap >= s.width() * 0.5) {
                    shadowed = true;
                    break;
                }
            }
            if (!shadowed) out.add(s);
        }
        return out;
    }

    /** Big content components never contain surfaces; skipping them keeps scans cheap. */
    private static boolean isLeafForScanning(Component c) {
        if (c instanceof javax.swing.JTree || c instanceof javax.swing.JTable || c instanceof javax.swing.JList
                || c instanceof javax.swing.text.JTextComponent || c instanceof javax.swing.JViewport && isListLikeViewport(c)) {
            return true;
        }
        String n = c.getClass().getSimpleName();
        return n.equals("EditorComponentImpl") || n.equals("EditorGutterComponentImpl");
    }

    private static boolean isListLikeViewport(Component c) {
        // viewports of trees/lists/tables hold only their view; anything else (e.g. panels) is still scanned
        Component view = ((javax.swing.JViewport) c).getView();
        return view instanceof javax.swing.JTree || view instanceof javax.swing.JList || view instanceof javax.swing.JTable;
    }

    private static Rule match(Component c) {
        for (Class<?> k = c.getClass(); k != null && k != JComponent.class && k != Object.class; k = k.getSuperclass()) {
            String name = k.getSimpleName();
            for (Rule r : RULES) {
                if (r.matches(name)) return r;
            }
        }
        return null;
    }

    private static boolean enabled(Category c, PetsSettings.StateBean s) {
        return switch (c) {
            case STATUS_BAR -> s.walkOnStatusBar;
            case TOOL_WINDOWS -> s.walkOnToolWindows;
            case EDITOR_TABS -> s.walkOnEditorTabs;
            case TOOLBARS -> s.walkOnToolbars;
            case PANELS -> s.walkOnPanels;
        };
    }

    private static void addSurface(Component c, Rule rule, JLayeredPane lp, Rectangle world, List<Surface> out) {
        if (!c.isShowing()) return;
        Rectangle visible = c instanceof JComponent jc ? jc.getVisibleRect() : new Rectangle(0, 0, c.getWidth(), c.getHeight());
        if (visible.isEmpty()) return;
        if (rule.edge() == Edge.CENTER && c.getWidth() <= c.getHeight()) return; // vertical divider, not walkable
        if ("toolbar".equals(rule.label()) && (c.getWidth() < 3 * c.getHeight() || c.getWidth() < 120)) return; // vertical/tiny toolbars
        Rectangle r = SwingUtilities.convertRectangle(c, visible, lp).intersection(world);
        if (r.isEmpty()) return;
        // Only use edges that are really the edge of the component, not a scrolled-away/clipped part of it.
        double y = switch (rule.edge()) {
            case TOP -> visible.y == 0 ? r.y : Double.NaN;
            case BOTTOM -> visible.y + visible.height == c.getHeight() ? r.y + r.height : Double.NaN;
            case CENTER -> r.y + r.height / 2.0;
        };
        if (Double.isNaN(y)) return;
        out.add(new Surface(c, rule.label(), r.x, r.x + r.width, y));
    }

    /** Merges surfaces at (almost) the same height that overlap or touch, so pets can walk across seams. */
    private static List<Surface> merge(List<Surface> in) {
        List<Surface> sorted = new ArrayList<>(in);
        sorted.sort(Comparator.comparingDouble(Surface::y).thenComparingDouble(Surface::x1));
        List<Surface> out = new ArrayList<>();
        for (Surface s : sorted) {
            boolean merged = false;
            for (int i = 0; i < out.size(); i++) {
                Surface o = out.get(i);
                if (Math.abs(o.y() - s.y()) <= 2 && s.x1() <= o.x2() + 2 && s.x2() >= o.x1() - 2) {
                    Surface wider = o.width() >= s.width() ? o : s;
                    out.set(i, new Surface(wider.key(), wider.label(), Math.min(o.x1(), s.x1()), Math.max(o.x2(), s.x2()), o.y()));
                    merged = true;
                    break;
                }
            }
            if (!merged) out.add(s);
        }
        return out;
    }
}
