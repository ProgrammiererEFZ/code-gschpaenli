// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Hand-made pixel-art animation frames for one species.
 *
 * <p>Every frame is a {@code String[]} of equal-length rows, all frames of a sheet share the same size. The pet faces
 * <b>right</b>, its feet touch the <b>last row</b>, and its body is horizontally centred. Characters are palette
 * slots, so every colour variant reuses the same frames:</p>
 * <pre>
 *   . or space  transparent
 *   O  outline (darkest)      B  body (main fur/feather colour)   L  light (belly, muzzle, chest)
 *   G  legs / paws / feet     E  ear inside / secondary colour    A  accent (stripes, spots, shading)
 *   T  tail tip / highlight   Y  eye                              N  nose / beak / mouth
 *   W  white (fixed)          K  black (fixed)                    P  pink blush (fixed)
 * </pre>
 */
public final class PixelSheet {

    /** One animation: frames played in a loop at {@code fps} (WALK/RUN/CLIMB follow the pet's stride instead). */
    public record Anim(List<String[]> frames, double fps) {
    }

    private final int width;
    private final int height;
    private final Map<Pose, Anim> anims;

    private PixelSheet(int width, int height, Map<Pose, Anim> anims) {
        this.width = width;
        this.height = height;
        this.anims = anims;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /** The animation for a pose, falling back to a related pose when the sheet has none. */
    public Anim anim(Pose pose) {
        for (Pose p : fallbacks(pose)) {
            Anim a = anims.get(p);
            if (a != null) return a;
        }
        return anims.get(Pose.STAND);
    }

    private static List<Pose> fallbacks(Pose pose) {
        return switch (pose) {
            case STAND -> List.of(Pose.STAND);
            case WALK, CLIMB, CLIMB_DOWN -> List.of(Pose.WALK, Pose.STAND);
            case RUN -> List.of(Pose.RUN, Pose.WALK, Pose.STAND);
            case SIT -> List.of(Pose.SIT, Pose.STAND);
            case SLEEP -> List.of(Pose.SLEEP, Pose.SIT, Pose.STAND);
            case JUMP -> List.of(Pose.JUMP, Pose.FALL, Pose.WALK, Pose.STAND);
            case FALL -> List.of(Pose.FALL, Pose.JUMP, Pose.STAND);
            case HELD -> List.of(Pose.HELD, Pose.FALL, Pose.JUMP, Pose.STAND);
        };
    }

    public static Builder builder(int width, int height) {
        return new Builder(width, height);
    }

    public static final class Builder {
        private final int width;
        private final int height;
        private final Map<Pose, Anim> anims = new EnumMap<>(Pose.class);

        private Builder(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /** Adds an animation; each frame is given as its rows. Validates frame sizes and characters. */
        public Builder anim(Pose pose, double fps, String[]... frames) {
            List<String[]> list = new ArrayList<>();
            for (String[] f : frames) {
                if (f.length != height) {
                    throw new IllegalArgumentException(pose + ": frame has " + f.length + " rows, expected " + height);
                }
                for (String row : f) {
                    if (row.length() != width) {
                        throw new IllegalArgumentException(pose + ": row '" + row + "' has " + row.length()
                                + " columns, expected " + width + " in frame " + Arrays.toString(f));
                    }
                    for (char c : row.toCharArray()) {
                        if (".  OBLGEATYNWKP".indexOf(c) < 0) {
                            throw new IllegalArgumentException(pose + ": unknown pixel '" + c + "' in row '" + row + "'");
                        }
                    }
                }
                list.add(f);
            }
            if (list.isEmpty()) throw new IllegalArgumentException(pose + ": no frames");
            anims.put(pose, new Anim(List.copyOf(list), fps));
            return this;
        }

        public PixelSheet build() {
            if (!anims.containsKey(Pose.STAND)) throw new IllegalStateException("a sheet needs at least a STAND animation");
            return new PixelSheet(width, height, Map.copyOf(anims));
        }
    }
}
