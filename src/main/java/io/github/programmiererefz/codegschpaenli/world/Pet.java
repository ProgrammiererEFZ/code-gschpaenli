// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.world;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.util.ArrayList;
import java.util.List;

/** Mutable simulation state of a single pet. Positions are in overlay pixels; (x, y) is the point between the feet. */
public final class Pet {

    public enum Behavior { IDLE, SIT, SLEEP, WALK, RUN, CHASE, AIRBORNE, HELD, CLIMB }

    public final String id;
    public final PetKind kind;
    public final PetKind.Palette palette;
    public final String name;

    public double x;
    public double y;
    public double vx;
    public double vy;
    public boolean faceRight = true;

    public Behavior behavior = Behavior.AIRBORNE;
    public double stateTime;
    public double stateDuration;
    public double targetX;

    /** Surface currently stood on; null while airborne, held or climbing. */
    public Surface surface;

    /** Wall being climbed; while climbing, (x, y) is the point on the wall level with the middle of the body. */
    public Wall climbWall;
    /** +1 when the climbed wall is to the pet's right, -1 when it is to the left. */
    public int climbSide = 1;
    /** Whether the current (or pending) climb goes downwards. */
    public boolean climbDown;
    boolean pendingDown;
    Surface climbTarget;
    Wall pendingClimb;
    int pendingSide;

    public double animTime;
    public double gaitPhase;
    public boolean blink;
    double blinkTimer = 2;
    public double happyTimer;
    /** Ages (0..1) of hearts floating above the pet. */
    public final List<Double> hearts = new ArrayList<>();
    /** 0..1, drains while active and refills while sleeping. Low energy makes naps likely. */
    double energy = 0.8;
    /** Peak downward speed during the current fall, used to choose the scared falling pose. */
    double fallSpeed;

    /** A random visitor (not one of the user's own pets); visitors come and go on their own. */
    public boolean visitor;
    /** A visitor on its way out: it walks towards the window side and fades away. */
    public boolean leaving;
    /** Opacity 0..1, used to fade visitors in and out. */
    public double fade = 1;
    boolean fadingOut;
    double leaveTime;

    public Pet(String id, PetKind kind, PetKind.Palette palette, String name) {
        this.id = id;
        this.kind = kind;
        this.palette = palette;
        this.name = name;
    }

    public Pose pose() {
        return switch (behavior) {
            case IDLE -> Pose.STAND;
            case SIT -> Pose.SIT;
            case SLEEP -> Pose.SLEEP;
            case WALK -> Pose.WALK;
            case RUN -> Pose.RUN;
            case CHASE -> Math.abs(vx) > 1 ? Pose.RUN : Pose.STAND;
            case HELD -> Pose.HELD;
            case CLIMB -> climbDown ? Pose.CLIMB_DOWN : Pose.CLIMB;
            case AIRBORNE -> vy < 0 || fallSpeed < 450 ? Pose.JUMP : Pose.FALL;
        };
    }

    /** Body tilt in radians while airborne, following the trajectory. */
    public double tilt() {
        if (behavior != Behavior.AIRBORNE) return 0;
        double t = Math.atan2(vy, Math.max(60, Math.abs(vx))) * 0.45;
        return Math.max(-0.5, Math.min(0.5, t));
    }

    public boolean isScared() {
        return behavior == Behavior.HELD || (behavior == Behavior.AIRBORNE && fallSpeed >= 450);
    }

    public boolean isHappy() {
        return happyTimer > 0;
    }
}
