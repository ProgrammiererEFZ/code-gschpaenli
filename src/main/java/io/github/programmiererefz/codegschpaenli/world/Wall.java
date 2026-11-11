// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.world;

/**
 * A vertical edge pets can climb (tool window borders, dividers, the window sides), in overlay coordinates.
 *
 * @param x          horizontal position of the edge
 * @param y1         top end (smaller y)
 * @param y2         bottom end
 * @param fromLeft   a pet standing left of the wall may climb it
 * @param fromRight  a pet standing right of the wall may climb it
 */
public record Wall(Object key, String label, double x, double y1, double y2, boolean fromLeft, boolean fromRight) {

    public double height() {
        return y2 - y1;
    }
}
