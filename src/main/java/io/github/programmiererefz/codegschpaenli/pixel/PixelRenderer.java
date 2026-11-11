// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.PetRenderer;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Draws pixel-art pets from a {@link PixelSheet}. Frames are rendered once per palette into small images and
 * cached; painting scales them up with nearest-neighbour sampling so the pixels stay crisp.
 */
public final class PixelRenderer {

    private static final Color WHITE = new Color(0xFFFFFF);
    private static final Color BLACK = new Color(0x141418);
    private static final Color PINK = new Color(0xFF8FA3);

    private record Key(PetKind kind, String palette, String[] frame) {
        // identity of the frame array is what matters; arrays compare by identity in records
    }

    private static final Map<Key, BufferedImage> CACHE = new LinkedHashMap<>(256, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Key, BufferedImage> eldest) {
            return size() > 600;
        }
    };

    private PixelRenderer() {
    }

    /**
     * Size of one art pixel in user-space pixels for a pet of the given length. Rounded to whole pixels where the
     * pet is big enough, so art pixels line up with screen pixels.
     */
    public static double pixelSize(PixelSheet sheet, double petSize) {
        // one art-pixel size for all species (based on an 18 px reference width), so a duck and a cat drawn side
        // by side use the same pixel grid and keep their relative sizes
        double raw = petSize / 18.0;
        return raw >= 1.5 ? Math.round(raw) : Math.max(1, raw);
    }

    public static void paint(Graphics2D g, PetKind kind, PetKind.Palette pal, PetRenderer.State s, double x,
                             double groundY, double petSize, boolean faceRight) {
        PixelSheet sheet = PixelArt.sheet(kind);
        if (sheet == null) return;
        Pose pose = s.pose();
        PixelSheet.Anim anim = sheet.anim(pose);
        List<String[]> frames = anim.frames();
        int idx;
        if (pose == Pose.WALK || pose == Pose.RUN || pose == Pose.CLIMB || pose == Pose.CLIMB_DOWN) {
            idx = (int) Math.floor(s.gaitPhase() / (Math.PI / 2)); // four frames per stride
        } else {
            idx = (int) Math.floor(s.time() * anim.fps());
        }
        String[] frame = frames.get(Math.floorMod(idx, frames.size()));
        BufferedImage img = image(kind, pal, frame, sheet);

        double px = pixelSize(sheet, petSize);
        double w = sheet.width() * px;
        double h = sheet.height() * px;

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            g2.translate(Math.round(x), Math.round(groundY));
            if (pose == Pose.CLIMB || pose == Pose.CLIMB_DOWN) {
                // same convention as the vector renderer: feet against the wall at (x, groundY), head up or down
                boolean down = pose == Pose.CLIMB_DOWN;
                g2.rotate(faceRight ? -Math.PI / 2 : Math.PI / 2);
                if (faceRight == down) g2.scale(-1, 1);
            } else if (!faceRight) {
                g2.scale(-1, 1);
            }
            g2.drawImage(img, (int) Math.round(-w / 2), (int) Math.round(-h), (int) Math.round(w), (int) Math.round(h), null);
        } finally {
            g2.dispose();
        }
    }

    private static synchronized BufferedImage image(PetKind kind, PetKind.Palette pal, String[] frame, PixelSheet sheet) {
        Key key = new Key(kind, pal.name(), frame);
        BufferedImage img = CACHE.get(key);
        if (img != null) return img;
        img = new BufferedImage(sheet.width(), sheet.height(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < frame.length; y++) {
            String row = frame[y];
            for (int x = 0; x < row.length(); x++) {
                Color c = color(row.charAt(x), pal);
                if (c != null) img.setRGB(x, y, c.getRGB());
            }
        }
        CACHE.put(key, img);
        return img;
    }

    private static Color color(char c, PetKind.Palette p) {
        return switch (c) {
            case 'O' -> p.outline();
            case 'B' -> p.body();
            case 'L' -> p.belly();
            case 'G' -> p.leg();
            case 'E' -> p.ear();
            case 'A' -> p.accent();
            case 'T' -> p.tailTip();
            case 'Y' -> p.eye();
            case 'N' -> p.nose();
            case 'W' -> WHITE;
            case 'K' -> BLACK;
            case 'P' -> PINK;
            default -> null;
        };
    }
}
