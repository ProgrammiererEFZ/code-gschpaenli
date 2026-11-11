// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.world;

/**
 * A horizontal segment pets can stand on, in overlay coordinates (pixels, y grows downwards).
 *
 * @param key   stable identity across layout refreshes (e.g. the Swing component the edge belongs to), so pets
 *              standing on a surface move with it when the IDE layout changes
 * @param label human-readable description, for debugging
 */
public record Surface(Object key, String label, double x1, double x2, double y) {

    public double width() {
        return x2 - x1;
    }

    public boolean spans(double x) {
        return x >= x1 && x <= x2;
    }

    public double clampX(double x, double margin) {
        double lo = x1 + margin;
        double hi = x2 - margin;
        if (lo > hi) return (x1 + x2) / 2;
        return Math.max(lo, Math.min(hi, x));
    }
}
