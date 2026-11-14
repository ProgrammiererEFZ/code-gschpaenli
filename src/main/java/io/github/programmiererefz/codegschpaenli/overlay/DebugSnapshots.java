// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.openapi.diagnostic.Logger;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.settings.PetNames;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Pet;
import io.github.programmiererefz.codegschpaenli.world.Surface;

import javax.imageio.ImageIO;
import javax.swing.JRootPane;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;

/**
 * Development aid, inactive unless the JVM is started with {@code -Dcodegschpaenli.debug.snapshotDir=<dir>}
 * (see the {@code petsDebugDir} Gradle property). Periodically renders every project window, including the
 * pets, to PNG files and writes the detected surfaces and pet states next to them.
 */
final class DebugSnapshots {

    private static final Logger LOG = Logger.getInstance(DebugSnapshots.class);
    static final String DIR = System.getProperty("codegschpaenli.debug.snapshotDir");
    static final boolean ENABLED = DIR != null && !DIR.isBlank();

    private static final int EVERY_FRAMES = 90;
    private static final int MAX_SHOTS = 30;
    private static final int BALL_AT_SHOT = 5;

    private int shots;

    /**
     * Adds a handful of pets so all species can be inspected. With {@code -Dcodegschpaenli.debug.kinds=KOALA,PIXEL_OWL,...}
     * the adopted pets are replaced by exactly those species (first colour variant), random visitors and
     * name tags are switched off, and after a few seconds the pets are spread evenly over the walkable surfaces, for a
     * controlled scene (e.g. screenshots). {@code -Dcodegschpaenli.debug.windowSize=1280x800} also resizes each project
     * window so that its content has exactly that size.
     */
    static void seedPets() {
        if (!ENABLED) return;
        PetsSettings s = PetsSettings.getInstance();
        String kindList = System.getProperty("codegschpaenli.debug.kinds", "").trim();
        if (!kindList.isEmpty()) {
            s.getState().pets.clear();
            for (String name : kindList.split(",")) {
                PetKind k = PetKind.parse(name.trim());
                s.getState().pets.add(new PetsSettings.PetEntry(k, k.palettes().get(0).name(), PetNames.random(k)));
            }
            s.getState().showNames = false;
            s.getState().visitorsEnabled = false;
            return;
        }
        int wanted = Integer.getInteger("codegschpaenli.debug.pets", 0);
        PetKind[] kinds = PetKind.values();
        for (int i = s.getState().pets.size(); i < wanted; i++) {
            PetKind k = kinds[i % kinds.length];
            s.getState().pets.add(new PetsSettings.PetEntry(k, k.palettes().get(i / kinds.length % k.palettes().size()).name(),
                    PetNames.random(k)));
        }
        s.getState().showNames = true;
        // exercise random visitors with the pixel-art group
        s.getState().visitorsEnabled = true;
        s.getState().visitorsMin = 3;
        s.getState().visitorsMax = 4;
        s.getState().visitorsCartoon = false;
        s.getState().visitorsPixel = true;
    }

    void onFrame(int frameCounter, Collection<PetOverlay> overlays) {
        if (!ENABLED || frameCounter % EVERY_FRAMES != 0 || shots >= MAX_SHOTS) return;
        shots++;
        if (shots == 1) resizeWindows(overlays);
        if (shots == 2) {
            // open a bottom tool window so there is a surface in the middle of the window
            // (-Dcodegschpaenli.debug.toolWindow=Terminal picks another one), and optionally a file in the editor
            // (-Dcodegschpaenli.debug.openFile=src/Main.java, relative to the project; all other editors are closed)
            String toolWindow = System.getProperty("codegschpaenli.debug.toolWindow", "Problems View");
            String openFile = System.getProperty("codegschpaenli.debug.openFile", "").trim();
            for (com.intellij.openapi.project.Project p : com.intellij.openapi.project.ProjectManager.getInstance().getOpenProjects()) {
                if (!openFile.isEmpty() && p.getBasePath() != null) {
                    var file = com.intellij.openapi.vfs.LocalFileSystem.getInstance()
                            .refreshAndFindFileByPath(p.getBasePath() + "/" + openFile);
                    if (file != null) {
                        var fem = com.intellij.openapi.fileEditor.FileEditorManager.getInstance(p);
                        for (var other : fem.getOpenFiles()) {
                            if (!other.equals(file)) fem.closeFile(other);
                        }
                        // not FileEditorManager.openFile: all its blocking variants are deprecated since 2026.3
                        new com.intellij.openapi.fileEditor.OpenFileDescriptor(p, file).navigate(true);
                    }
                }
                var twm = com.intellij.openapi.wm.ToolWindowManager.getInstance(p);
                var tw = twm.getToolWindow(toolWindow);
                if (tw != null) tw.show();
                // the Project view shows the project's full path; hide it so snapshots can be used as screenshots
                var project = twm.getToolWindow("Project");
                if (project != null) project.hide();
            }
        }
        boolean scene = !System.getProperty("codegschpaenli.debug.kinds", "").isBlank();
        if (scene && shots == 4) { // after the tool window and editor have settled
            for (PetOverlay o : overlays) spreadPets(o.world());
        }
        int i = 0;
        for (PetOverlay o : overlays) {
            if (shots == BALL_AT_SHOT && !scene) o.throwBall();
            write(o, i++);
        }
    }

    /** Drops the pets evenly over the walkable surfaces (round robin, evenly spaced along each surface). */
    private static void spreadPets(io.github.programmiererefz.codegschpaenli.world.World world) {
        java.util.List<Surface> surfaces = new java.util.ArrayList<>();
        for (Surface s : world.surfaces()) {
            if (!"window bottom".equals(s.label())) surfaces.add(s);
        }
        if (surfaces.isEmpty()) return;
        java.util.List<Pet> pets = new java.util.ArrayList<>(world.pets());
        int n = surfaces.size();
        for (int si = 0; si < n; si++) {
            Surface s = surfaces.get(si);
            java.util.List<Pet> here = new java.util.ArrayList<>();
            for (int pi = si; pi < pets.size(); pi += n) here.add(pets.get(pi));
            for (int k = 0; k < here.size(); k++) {
                Pet p = here.get(k);
                double x = s.x1() + s.width() * (k + 0.5 + (si % 2) * 0.25) / (here.size() + 0.25);
                world.grab(p);
                world.drag(p, x, s.y() - 2);
                world.release(p, 0, 0);
            }
        }
    }

    /** Resizes each project window so that its content (the root pane) matches {@code codegschpaenli.debug.windowSize}. */
    private static void resizeWindows(Collection<PetOverlay> overlays) {
        String size = System.getProperty("codegschpaenli.debug.windowSize", "").trim();
        String[] wh = size.split("x");
        if (wh.length != 2) return;
        int w = Integer.parseInt(wh[0].trim());
        int h = Integer.parseInt(wh[1].trim());
        for (PetOverlay o : overlays) {
            java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(o.rootPane());
            if (win == null) continue;
            if (win instanceof java.awt.Frame f) f.setExtendedState(java.awt.Frame.NORMAL);
            JRootPane rp = o.rootPane();
            win.setSize(win.getWidth() + w - rp.getWidth(), win.getHeight() + h - rp.getHeight());
            win.validate();
        }
    }

    private void write(PetOverlay o, int index) {
        JRootPane rp = o.rootPane();
        if (rp.getWidth() <= 0 || rp.getHeight() <= 0) return;
        try {
            File dir = new File(DIR);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
            BufferedImage img = new BufferedImage(rp.getWidth(), rp.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            rp.paint(g);
            g.dispose();
            String base = String.format("window%d-shot%02d", index, shots);
            ImageIO.write(img, "png", new File(dir, base + ".png"));

            StringBuilder sb = new StringBuilder();
            sb.append("root pane ").append(rp.getWidth()).append('x').append(rp.getHeight()).append('\n');
            sb.append("surfaces:\n");
            for (Surface s : o.world().surfaces()) {
                sb.append(String.format("  y=%6.1f x=%6.1f..%6.1f  %-18s %s%n", s.y(), s.x1(), s.x2(), s.label(),
                        s.key() == null ? "" : s.key().getClass().getName()));
            }
            sb.append("walls:\n");
            for (io.github.programmiererefz.codegschpaenli.world.Wall w : o.world().walls()) {
                sb.append(String.format("  x=%6.1f y=%6.1f..%6.1f  %s%n", w.x(), w.y1(), w.y2(), w.label()));
            }
            sb.append("pets:\n");
            for (Pet p : o.world().pets()) {
                sb.append(String.format("  %-10s %-6s %-9s x=%6.1f y=%6.1f on=%s%n", p.name, p.kind, p.behavior, p.x, p.y,
                        p.surface == null ? "-" : p.surface.label()));
            }
            if (o.world().ball() != null) {
                sb.append(String.format("ball x=%.1f y=%.1f%n", o.world().ball().x, o.world().ball().y));
            }
            Files.writeString(new File(dir, base + ".txt").toPath(), sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException | RuntimeException e) {
            LOG.warn("Code Gschpänli debug snapshot failed", e);
        }
    }
}
