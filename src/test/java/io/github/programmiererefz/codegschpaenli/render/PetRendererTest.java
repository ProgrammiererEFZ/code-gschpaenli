// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.render;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PetRendererTest {

    private static final int W = 160;
    private static final int H = 140;

    private static int[] render(PetKind kind, PetKind.Palette pal, PetRenderer.State s, boolean faceRight) {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = img.createGraphics();
        try {
            PetRenderer.paint(g, kind, pal, s, W / 2.0, H * 0.7, 2.5, faceRight);
        } finally {
            g.dispose();
        }
        return ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    /**
     * Every cartoon pet draws in every pose, and drawing a frame again gives exactly the same pixels. The first draw
     * of a frame often computes and caches its shapes, the second always takes them from the cache, so this catches
     * cached shapes that differ from freshly computed ones or get changed after caching.
     */
    @Test
    void everyCartoonPetDrawsTheSameFrameTwiceAlike() {
        for (PetKind kind : PetKind.inGroup(PetKind.Group.CARTOON)) {
            for (PetKind.Palette pal : kind.palettes()) {
                for (Pose pose : Pose.values()) {
                    for (boolean happy : new boolean[]{false, true}) {
                        for (int i = 0; i < 6; i++) {
                            double t = 1000 + i * 0.37 + pose.ordinal() * 0.11;
                            PetRenderer.State s = new PetRenderer.State(pose, t, t * 8, 0, false, happy, false);
                            boolean faceRight = i % 2 == 0;
                            int[] first = render(kind, pal, s, faceRight);
                            int[] again = render(kind, pal, s, faceRight);
                            String what = kind + "/" + pal.name() + "/" + pose + " happy=" + happy + " t=" + t;
                            assertTrue(Arrays.stream(first).anyMatch(px -> px != 0), "nothing drawn: " + what);
                            assertArrayEquals(first, again, "second draw differs: " + what);
                        }
                    }
                }
            }
        }
    }
}
