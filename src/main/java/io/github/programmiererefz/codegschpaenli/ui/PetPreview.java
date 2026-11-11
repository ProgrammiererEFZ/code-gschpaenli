// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.pixel.PixelRenderer;
import io.github.programmiererefz.codegschpaenli.render.PetRenderer;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** A small animated preview that cycles a pet through a few poses. Used in the adopt dialog and settings. */
public final class PetPreview extends JComponent {

    private static final Pose[] CYCLE = {Pose.WALK, Pose.WALK, Pose.STAND, Pose.SIT, Pose.RUN, Pose.SLEEP};

    private PetKind kind = PetKind.CAT;
    private PetKind.Palette palette = PetKind.CAT.palettes().get(0);
    private final Timer timer;
    private final long start = System.nanoTime();

    public PetPreview() {
        setPreferredSize(new Dimension(JBUI.scale(170), JBUI.scale(90)));
        setOpaque(false);
        timer = new Timer(40, e -> repaint());
    }

    public void setPet(PetKind kind, PetKind.Palette palette) {
        this.kind = kind;
        this.palette = palette;
        repaint();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        timer.start();
    }

    @Override
    public void removeNotify() {
        timer.stop();
        super.removeNotify();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double t = (System.nanoTime() - start) / 1e9;
        Pose pose = CYCLE[(int) (t / 2.5) % CYCLE.length];
        // leave headroom for ears, hops and the bunny's tall ears
        double px = Math.min(getWidth() * 0.55, getHeight() * 0.62) / PetRenderer.UNIT_LENGTH;
        double ground = getHeight() - JBUI.scale(10);
        g2.setColor(JBColor.border());
        g2.fillRect(JBUI.scale(8), (int) ground, getWidth() - JBUI.scale(16), JBUI.scale(2));
        double phase = t * (pose == Pose.RUN ? 13 : 6);
        PetRenderer.State s = new PetRenderer.State(pose, t, phase, 0, ((int) (t * 10)) % 37 == 0, false, false);
        if (kind.isPixel()) {
            PixelRenderer.paint(g2, kind, palette, s, getWidth() / 2.0, ground, px * PetRenderer.UNIT_LENGTH, true);
        } else {
            PetRenderer.paint(g2, kind, palette, s, getWidth() / 2.0, ground, px, true);
        }
        if (pose == Pose.SLEEP) {
            PetRenderer.paintZzz(g2, getWidth() / 2.0 + px * 10, ground - px * 12, px * 7, t, JBColor.foreground());
        }
    }
}
