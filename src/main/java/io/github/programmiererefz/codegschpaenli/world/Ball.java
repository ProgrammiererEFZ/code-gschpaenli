// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.world;

/** A ball the pets chase. Coordinates are the ball centre. */
public final class Ball {
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double radius;
    public double spin;
    public double age;
    /** Surface the ball is currently resting/rolling on, or null while in the air. */
    public Surface surface;

    Ball(double x, double y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    public double bottom() {
        return y + radius;
    }
}
