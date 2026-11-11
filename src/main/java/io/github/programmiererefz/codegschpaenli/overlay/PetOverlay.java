// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.ui.JBColor;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.ui.JBFont;
import io.github.programmiererefz.codegschpaenli.pixel.PixelRenderer;
import io.github.programmiererefz.codegschpaenli.render.PetRenderer;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Ball;
import io.github.programmiererefz.codegschpaenli.world.Pet;
import io.github.programmiererefz.codegschpaenli.world.World;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A transparent component on top of an IDE frame's layered pane that draws the pets. It only claims mouse
 * events over a pet ({@link #contains(int, int)}), so everything else stays fully clickable.
 */
final class PetOverlay extends JComponent {

    /**
     * Above all IDE content (layer 0) and palette components (100), but below notification balloons and lightweight
     * popups (299/300), so completion lists, menus, tooltips and balloons always stay on top of the pets.
     */
    static final Integer LAYER = 150;

    private static final double SURFACE_REFRESH_SECONDS = 0.5;

    /** Snapshot and measurement runs keep animating while the sandbox IDE is in the background. */
    private static final boolean DEV_RUN = DebugSnapshots.ENABLED || PerfProbe.ENABLED;

    private final JRootPane rootPane;
    private final JLayeredPane layeredPane;
    private final World world;
    private final Visitors visitors;
    private final ComponentAdapter resizeListener;
    private final Map<String, Rectangle> lastPetRects = new HashMap<>();
    private Rectangle lastBallRect;
    private double surfaceTimer;
    private PetsSettings.StateBean settings;
    private PetsSettings.Size size = PetsSettings.Size.SMALL;

    // mouse interaction
    private Pet pressedPet;
    private int pressX;
    private int pressY;
    private double grabOffsetY;
    private boolean dragging;
    private final double[] lastMouse = new double[3]; // x, y, nanos
    private double throwVx;
    private double throwVy;

    PetOverlay(JRootPane rootPane, long seed) {
        this.rootPane = rootPane;
        this.layeredPane = rootPane.getLayeredPane();
        this.world = new World(seed);
        this.visitors = new Visitors(world, seed * 31 + 7);
        setOpaque(false);
        setFocusable(false);
        setFont(JBFont.small());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        layeredPane.add(this, LAYER);
        updateBounds();
        resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBounds();
                surfaceTimer = 0;
            }
        };
        layeredPane.addComponentListener(resizeListener);

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onPress(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                onDrag(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                onRelease(e);
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        // the overlay never scrolls: hand wheel events over a pet to whatever is underneath it
        addMouseWheelListener(this::redispatchToUnderlying);
    }

    JRootPane rootPane() {
        return rootPane;
    }

    World world() {
        return world;
    }

    private void updateBounds() {
        setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        world.setBounds(layeredPane.getWidth(), layeredPane.getHeight());
    }

    void detach() {
        layeredPane.removeComponentListener(resizeListener);
        Rectangle b = getBounds();
        layeredPane.remove(this);
        layeredPane.repaint(b);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Settings and pets
    // ------------------------------------------------------------------------------------------------------------

    void applySettings(PetsSettings.StateBean s, PetsSettings.Size size) {
        this.settings = s;
        this.size = size;
        world.setPetSize(JBUIScale.scale((float) size.px));
        world.setSpeed(s.speed / 100.0);
        world.setActivity(s.activity / 100.0);

        Set<String> wanted = new HashSet<>();
        for (PetsSettings.PetEntry e : s.pets) wanted.add(e.id);
        for (Pet p : new ArrayList<>(world.pets())) {
            if (!p.visitor && !wanted.contains(p.id)) world.removePet(p.id);
        }
        Set<String> have = new HashSet<>();
        for (Pet p : world.pets()) have.add(p.id);
        // a pet whose look or name changed is re-created
        for (PetsSettings.PetEntry e : s.pets) {
            Pet existing = null;
            for (Pet p : world.pets()) if (p.id.equals(e.id)) existing = p;
            if (existing != null && (existing.kind != e.petKind() || !existing.palette.equals(e.palette())
                    || !existing.name.equals(e.name))) {
                world.removePet(e.id);
                have.remove(e.id);
            }
            if (!have.contains(e.id)) world.addPet(e.id, e.petKind(), e.palette(), e.name);
        }
        refreshSurfaces();
        repaint();
    }

    void throwBall() {
        world.throwBall();
    }

    /** Whether this overlay currently has anything to animate. */
    boolean isBusy() {
        return settings != null && settings.enabled && !isPaused()
                && (!world.pets().isEmpty() || world.ball() != null || visitors.active(settings));
    }

    private void refreshSurfaces() {
        if (settings == null || !isShowing()) return;
        world.setPetSize(JBUIScale.scale((float) size.px)); // follows View | Appearance | Zoom IDE
        SurfaceScanner.Result r = SurfaceScanner.scan(layeredPane, this, settings);
        world.setSurfaces(r.surfaces());
        world.setWalls(r.walls());
    }

    // ------------------------------------------------------------------------------------------------------------
    // Animation
    // ------------------------------------------------------------------------------------------------------------

    boolean isPaused() {
        if (!isShowing()) return true;
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w == null) return true;
        if (w instanceof java.awt.Frame f && (f.getExtendedState() & java.awt.Frame.ICONIFIED) != 0) return true;
        if (com.intellij.ide.PowerSaveMode.isEnabled() && !DEV_RUN) return true; // File | Power Save Mode
        return settings != null && settings.pauseWhenInactive && !w.isActive() && !dragging && !DEV_RUN;
    }

    void tick(double dt) {
        if (settings == null) return;
        if (world.pets().isEmpty() && world.ball() == null && !visitors.active(settings)) return;
        if (isPaused()) return;
        long t = PerfProbe.now();
        visitors.tick(dt, settings);
        surfaceTimer -= dt;
        if (surfaceTimer <= 0) {
            surfaceTimer = SURFACE_REFRESH_SECONDS;
            t = PerfProbe.lap(PerfProbe.SIM, t);
            refreshSurfaces();
            t = PerfProbe.lap(PerfProbe.SCAN, t);
        }
        world.tick(dt);
        t = PerfProbe.lap(PerfProbe.SIM, t);
        repaintDirty();
        PerfProbe.lap(PerfProbe.REPAINT, t);
    }

    /**
     * Repaints only the areas around each pet (old and new position). Uses paintImmediately per area, because
     * repaint() would merge far-apart pets into one huge dirty rectangle and repaint most of the IDE every frame.
     */
    private void repaintDirty() {
        List<Rectangle> dirty = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Pet p : world.pets()) {
            Rectangle now = petRect(p);
            Rectangle before = lastPetRects.put(p.id, now);
            dirty.add(before == null ? now : before.union(now));
            seen.add(p.id);
        }
        lastPetRects.entrySet().removeIf(e -> {
            if (!seen.contains(e.getKey())) {
                dirty.add(e.getValue());
                return true;
            }
            return false;
        });
        Ball b = world.ball();
        Rectangle ballNow = b == null ? null : ballRect(b);
        if (lastBallRect != null || ballNow != null) {
            dirty.add(lastBallRect == null ? ballNow : ballNow == null ? lastBallRect : lastBallRect.union(ballNow));
        }
        lastBallRect = ballNow;

        RepaintManager rm = RepaintManager.currentManager(this);
        for (Rectangle r : mergeOverlapping(dirty)) {
            Rectangle clipped = r.intersection(new Rectangle(0, 0, getWidth(), getHeight()));
            if (clipped.isEmpty()) continue;
            PerfProbe.addRepaintedArea(clipped);
            paintImmediately(clipped);
            // When no ancestor up to the window is opaque (the IDE's root pane on Windows), paintImmediately() quietly
            // becomes a deferred repaint of the window, and Swing merges those into one rectangle spanning all pets.
            // Painting the pending region right away keeps each area separate. A no-op when nothing is pending.
            rm.paintDirtyRegions();
        }
    }

    private static List<Rectangle> mergeOverlapping(List<Rectangle> rects) {
        List<Rectangle> out = new ArrayList<>();
        for (Rectangle r : rects) {
            Rectangle cur = new Rectangle(r);
            boolean changed = true;
            while (changed) {
                changed = false;
                for (int i = 0; i < out.size(); i++) {
                    if (out.get(i).intersects(cur)) {
                        cur = cur.union(out.remove(i));
                        changed = true;
                        break;
                    }
                }
            }
            out.add(cur);
        }
        return out;
    }

    private Rectangle petRect(Pet p) {
        double size = world.petSize();
        double h = world.petHeight();
        if (p.behavior == Pet.Behavior.CLIMB) {
            // turned sideways against the wall, centred on p.y; hearts may float above
            int x = (int) Math.floor(p.x - h * 1.4);
            int y = (int) Math.floor(p.y - size * 0.75 - h * 1.2);
            return new Rectangle(x, y, (int) Math.ceil(h * 2.8) + 2, (int) Math.ceil(size * 1.5 + h * 1.2) + 4);
        }
        double halfW = size * 0.85;
        int below = 0;
        if (settings != null && settings.showNames) {
            // name tags can be wider than the pet, and go below it when there is no space above
            halfW = Math.max(halfW, nameTagWidth(p) / 2.0 + 2);
            if (tagBelow(p)) below = getFontMetrics(getFont()).getHeight() + 6;
        }
        int x = (int) Math.floor(p.x - halfW);
        int y = (int) Math.floor(p.y - h * 2.4);
        return new Rectangle(x, y, (int) Math.ceil(halfW * 2) + 2, (int) Math.ceil(h * 2.4 + size * 0.2) + 4 + below);
    }

    private int nameTagWidth(Pet p) {
        if (p.name == null || p.name.isBlank()) return 0;
        java.awt.Font f = getFont().deriveFont(java.awt.Font.PLAIN, Math.max(9f, getFont().getSize2D() - 2f));
        return getFontMetrics(f).stringWidth(p.name) + 8;
    }

    /** Name tags normally float above the pet, but go below it near the top of the window. */
    private boolean tagBelow(Pet p) {
        return p.y - world.petHeight() * 1.15 - getFontMetrics(getFont()).getHeight() < 2;
    }

    private static Rectangle ballRect(Ball b) {
        int r = (int) Math.ceil(b.radius) + 3;
        return new Rectangle((int) b.x - r, (int) b.y - r, 2 * r, 2 * r);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Painting
    // ------------------------------------------------------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        if (settings == null) return;
        long t = PerfProbe.now();
        Graphics2D g2 = (Graphics2D) g;
        Rectangle clip = g.getClipBounds();
        double size = world.petSize();
        double h = world.petHeight();
        double pxPerUnit = size / PetRenderer.UNIT_LENGTH;

        Ball b = world.ball();
        if (b != null && (clip == null || clip.intersects(ballRect(b)))) {
            PetRenderer.paintBall(g2, b.x, b.y, b.radius, b.spin);
        }
        Color zColor = new JBColor(new Color(0x5A6270), new Color(0xC9CED6));
        Color tagBg = new JBColor(new Color(0, 0, 0, 140), new Color(0, 0, 0, 170));
        for (Pet p : world.pets()) {
            if (clip != null && !clip.intersects(petRect(p))) continue;
            PetRenderer.State st = new PetRenderer.State(p.pose(), p.animTime, p.gaitPhase, p.tilt(), p.blink,
                    p.isHappy(), p.isScared());
            Graphics2D gp = g2;
            if (p.fade < 1) {
                gp = (Graphics2D) g2.create();
                gp.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                        (float) Math.max(0, Math.min(1, p.fade))));
            }
            if (p.kind.isPixel()) {
                PixelRenderer.paint(gp, p.kind, p.palette, st, p.x, p.y, size, p.faceRight);
            } else {
                PetRenderer.paint(gp, p.kind, p.palette, st, p.x, p.y, pxPerUnit, p.faceRight);
            }
            if (gp != g2) gp.dispose();
            if (p.behavior == Pet.Behavior.SLEEP) {
                PetRenderer.paintZzz(g2, p.x + (p.faceRight ? -size * 0.1 : -size * 0.3), p.y - h * 0.55,
                        size * 0.24, p.animTime, zColor);
            }
            for (double age : p.hearts) {
                PetRenderer.paintHeart(g2, p.x + (p.faceRight ? size * 0.2 : -size * 0.2), p.y - h * 1.15,
                        Math.max(8, size * 0.24), age);
            }
            if (settings.showNames && p.behavior != Pet.Behavior.CLIMB && p.fade >= 1) {
                double bottom = tagBelow(p) ? p.y + getFontMetrics(getFont()).getHeight() + 2 : p.y - h * 1.15;
                PetRenderer.paintNameTag(g2, p.name, p.x, bottom, tagBg, Color.WHITE);
            }
        }
        PerfProbe.lap(PerfProbe.DRAW, t);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Mouse: only pets are "solid", everything else passes through to the IDE
    // ------------------------------------------------------------------------------------------------------------

    @Override
    public boolean contains(int x, int y) {
        if (settings == null || !settings.enabled) return false;
        if (dragging) return true;
        return world.petAt(x, y) != null;
    }

    private void redispatchToUnderlying(MouseEvent e) {
        java.awt.Point lp = SwingUtilities.convertPoint(this, e.getPoint(), layeredPane);
        for (java.awt.Component c : layeredPane.getComponents()) { // top-most first
            if (c == this || !c.isVisible()) continue;
            java.awt.Point cp = SwingUtilities.convertPoint(layeredPane, lp, c);
            if (!c.contains(cp)) continue;
            java.awt.Component target = SwingUtilities.getDeepestComponentAt(c, cp.x, cp.y);
            if (target != null) target.dispatchEvent(SwingUtilities.convertMouseEvent(this, e, target));
            break;
        }
        e.consume();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setFont(JBFont.small()); // re-derive after LaF / IDE zoom changes
    }

    private void onPress(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) return;
        pressedPet = world.petAt(e.getX(), e.getY());
        if (pressedPet == null) return;
        pressX = e.getX();
        pressY = e.getY();
        grabOffsetY = pressedPet.y - e.getY();
        dragging = false;
        lastMouse[2] = 0;
        recordMouse(e);
        throwVx = 0;
        throwVy = 0;
        e.consume();
    }

    private void onDrag(MouseEvent e) {
        if (pressedPet == null) return;
        if (!dragging && Math.hypot(e.getX() - pressX, e.getY() - pressY) > JBUIScale.scale(4)) {
            dragging = true;
            world.grab(pressedPet);
        }
        if (dragging) {
            world.drag(pressedPet, e.getX(), e.getY() + grabOffsetY);
            recordMouse(e);
            repaintDirty();
        }
        e.consume();
    }

    private void onRelease(MouseEvent e) {
        if (pressedPet == null) return;
        if (dragging) {
            world.release(pressedPet, throwVx, throwVy);
        } else {
            world.pet(pressedPet);
        }
        dragging = false;
        pressedPet = null;
        surfaceTimer = 0;
        e.consume();
    }

    private void recordMouse(MouseEvent e) {
        long now = System.nanoTime();
        double dt = (now - lastMouse[2]) / 1e9;
        if (lastMouse[2] != 0 && dt > 0.001 && dt < 0.2) {
            // exponential smoothing of the drag velocity, used when the pet is let go
            throwVx = throwVx * 0.5 + (e.getX() - lastMouse[0]) / dt * 0.5;
            throwVy = throwVy * 0.5 + (e.getY() - lastMouse[1]) / dt * 0.5;
        }
        lastMouse[0] = e.getX();
        lastMouse[1] = e.getY();
        lastMouse[2] = now;
    }
}
