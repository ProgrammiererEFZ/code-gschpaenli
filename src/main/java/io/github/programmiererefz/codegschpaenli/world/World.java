// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.world;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.PetRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * The pet simulation for one IDE frame: a set of horizontal surfaces (edges of IDE components), pets that walk,
 * sit, nap, jump between surfaces and fall off edges, and an optional ball. Pure Java, no IDE dependencies.
 *
 * <p>Coordinates are pixels in the overlay, y grows downwards. The bottom of the overlay is always a floor.</p>
 */
public final class World {

    public static final String FLOOR_KEY = "floor";

    private final Random rnd;
    private final List<Pet> pets = new ArrayList<>();
    private List<Surface> surfaces = List.of();
    private List<Wall> walls = List.of();
    private Ball ball;
    /** Mouse pointer position for pets that follow it; NaN while the pointer is outside the window. */
    private double pointerX = Double.NaN;
    private double pointerY = Double.NaN;

    private double width = 800;
    private double height = 600;
    /** Pet length in pixels. */
    private double petSize = 44;
    private double speed = 1.0;
    /** Fraction of the time pets spend moving rather than lounging (0..1). */
    private double activity = 0.5;

    public World(long seed) {
        this.rnd = new Random(seed);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Configuration
    // ------------------------------------------------------------------------------------------------------------

    public void setBounds(double width, double height) {
        this.width = Math.max(1, width);
        this.height = Math.max(1, height);
        for (Pet p : pets) p.x = clampToWorld(p.x);
    }

    public void setPetSize(double px) {
        this.petSize = Math.max(12, px);
    }

    public void setSpeed(double speed) {
        this.speed = Math.max(0.2, Math.min(4, speed));
    }

    public void setActivity(double activity) {
        this.activity = Math.max(0, Math.min(1, activity));
    }

    public double petSize() {
        return petSize;
    }

    public double petHeight() {
        return petSize * PetRenderer.UNIT_HEIGHT / PetRenderer.UNIT_LENGTH;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public List<Pet> pets() {
        return Collections.unmodifiableList(pets);
    }

    public List<Surface> surfaces() {
        return surfaces;
    }

    public Ball ball() {
        return ball;
    }

    public List<Wall> walls() {
        return walls;
    }

    /**
     * Replaces the climbable walls. The window's left and right edges are always added. Pets climbing a wall that
     * vanished or moved let go and fall.
     */
    public void setWalls(List<Wall> newWalls) {
        List<Wall> list = new ArrayList<>();
        for (Wall w : newWalls) {
            if (w.height() >= petHeight() * 2 && w.x() > petSize * 0.5 && w.x() < width - petSize * 0.5) list.add(w);
        }
        list.add(new Wall("window-left", "window left edge", 0, 0, height, false, true));
        list.add(new Wall("window-right", "window right edge", width, 0, height, true, false));
        this.walls = List.copyOf(list);
        for (Pet p : pets) {
            if (p.pendingClimb != null) {
                p.pendingClimb = findWall(p.pendingClimb.key(), p.pendingClimb.label());
            }
            if (p.climbWall != null) {
                Wall match = findWall(p.climbWall.key(), p.climbWall.label());
                if (match == null || Math.abs(match.x() - p.climbWall.x()) > 2) {
                    if (p.behavior == Pet.Behavior.CLIMB) startFalling(p);
                    p.climbWall = null;
                } else {
                    p.climbWall = match;
                }
            }
        }
    }

    private Wall findWall(Object key, String label) {
        for (Wall w : walls) {
            if (Objects.equals(w.key(), key) && Objects.equals(w.label(), label)) return w;
        }
        return null;
    }

    /**
     * Replaces the walkable surfaces. Pets keep standing on a surface that still exists (matched by key) and are
     * moved along with it; pets whose surface vanished start to fall.
     */
    public void setSurfaces(List<Surface> newSurfaces) {
        List<Surface> list = new ArrayList<>();
        double minWidth = petSize * 0.9;
        double minY = petHeight() * 1.1; // keep room above for the pet's head
        for (Surface s : newSurfaces) {
            if (s.width() >= minWidth && s.y() >= minY && s.y() <= height) list.add(s);
        }
        list.add(new Surface(FLOOR_KEY, "window bottom", 0, width, height - 1));
        this.surfaces = List.copyOf(list);

        for (Pet p : pets) {
            if (p.climbTarget != null) {
                p.climbTarget = find(p.climbTarget.key(), p.climbTarget.label());
            }
            if (p.surface == null) continue;
            Surface match = find(p.surface.key(), p.surface.label());
            if (match == null) {
                match = surfaceAt(p.x, p.y, 3);
            }
            if (match == null || !match.spans(p.x)) {
                if (match != null && Math.abs(p.x - match.clampX(p.x, 0)) < petSize * 0.5) {
                    p.x = match.clampX(p.x, 0);
                } else {
                    startFalling(p);
                    continue;
                }
            }
            p.surface = match;
            p.y = match.y();
        }
        if (ball != null && ball.surface != null) {
            Surface match = find(ball.surface.key(), ball.surface.label());
            if (match == null || !match.spans(ball.x)) {
                ball.surface = null;
            } else {
                ball.surface = match;
                ball.y = match.y() - ball.radius;
            }
        }
    }

    private Surface find(Object key, String label) {
        for (Surface s : surfaces) {
            if (Objects.equals(s.key(), key) && Objects.equals(s.label(), label)) return s;
        }
        return null;
    }

    private Surface surfaceAt(double x, double y, double tolerance) {
        for (Surface s : surfaces) {
            if (Math.abs(s.y() - y) <= tolerance && s.spans(x)) return s;
        }
        return null;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Pets
    // ------------------------------------------------------------------------------------------------------------

    /**
     * Adds a pet that fades in somewhere in the window. Where is decided on the next {@link #tick}, once the surfaces
     * of a freshly opened window are known: it either pops up on a random surface or appears part way up a climbable
     * wall and climbs to the surface above (see {@link #placeNewPet}).
     */
    public Pet addPet(String id, PetKind kind, PetKind.Palette palette, String name) {
        Pet p = new Pet(id, kind, palette, name);
        p.x = width / 2;
        p.y = petHeight() + 2;
        p.faceRight = rnd.nextBoolean();
        p.energy = 0.6 + rnd.nextDouble() * 0.4;
        p.fade = 0;
        p.spawnPending = true;
        startFalling(p);
        pets.add(p);
        return p;
    }

    /** Adds a visitor that appears like any new pet (see {@link #addPet}). */
    public Pet addVisitor(String id, PetKind kind, PetKind.Palette palette, String name) {
        Pet p = addPet(id, kind, palette, name);
        p.visitor = true;
        return p;
    }

    /** Share of new pets that appear climbing a wall rather than on a surface (when a suitable wall exists). */
    private static final double CLIMB_SPAWN_CHANCE = 0.35;

    /** Picks the spot where a just-added pet appears. */
    private void placeNewPet(Pet p) {
        p.spawnPending = false;
        if (rnd.nextDouble() < CLIMB_SPAWN_CHANCE && spawnClimbing(p)) return;
        if (spawnOnSurface(p)) return;
        // no surfaces known at all: drop in from the top
        p.x = clampToWorld(petSize + rnd.nextDouble() * Math.max(1, width - 2 * petSize));
        p.y = petHeight() + 2;
    }

    /**
     * Lets the pet pop up on a random surface, with a little drop onto it. Every surface gets a chance, weighted by
     * the square root of its usable width so that long bars are likelier without crowding out the short ones.
     */
    private boolean spawnOnSurface(Pet p) {
        List<Surface> candidates = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        double total = 0;
        for (Surface s : surfaces) {
            double usable = usableHi(s) - usableLo(s);
            if (usable < petSize * 0.3) continue;
            double w = Math.sqrt(usable);
            candidates.add(s);
            weights.add(w);
            total += w;
        }
        if (candidates.isEmpty()) return false;
        double r = rnd.nextDouble() * total;
        Surface s = candidates.getLast();
        for (int i = 0; i < candidates.size(); i++) {
            r -= weights.get(i);
            if (r <= 0) {
                s = candidates.get(i);
                break;
            }
        }
        double lo = usableLo(s);
        double hi = usableHi(s);
        p.x = lo + rnd.nextDouble() * Math.max(0, hi - lo);
        p.y = s.y() - petHeight() * 0.6;
        startFalling(p);
        return true;
    }

    /** Left end of the part of a surface a pet can stand on (surfaces may reach past the window sides). */
    private double usableLo(Surface s) {
        return Math.max(s.x1() + petSize * 0.15, petSize * 0.5);
    }

    private double usableHi(Surface s) {
        return Math.min(s.x2() - petSize * 0.15, width - petSize * 0.5);
    }

    /**
     * Puts the pet part way up a random climbable wall (the window sides included), climbing towards a surface
     * above it.
     */
    private boolean spawnClimbing(Pet p) {
        record Option(Wall wall, int side, double y, Surface target) {
        }
        List<Option> options = new ArrayList<>();
        double half = petSize * 0.5;
        for (Wall w : walls) {
            for (int side : new int[]{+1, -1}) {
                if (side > 0 && !w.fromLeft() || side < 0 && !w.fromRight()) continue;
                // p.y is the middle of the climbing pet's body: keep it all on the wall and inside the window
                double yLo = w.y1() + half + petSize * 1.5; // leave at least a little climbing to do
                double yHi = Math.min(w.y2(), height - 1) - half;
                if (yHi <= yLo) continue;
                double y = yLo + rnd.nextDouble() * (yHi - yLo);
                Surface target = nearestSurfaceAbove(w, side, y);
                // the target must be clearly above the pet's head
                if (target == null || target.y() > y - half - petSize) continue;
                options.add(new Option(w, side, y, target));
            }
        }
        if (options.isEmpty()) return false;
        Option o = options.get(rnd.nextInt(options.size()));
        beginClimb(p, o.wall(), o.side(), false, o.target(), o.y());
        return true;
    }

    /** Makes a pet leave: it walks towards the nearest window side, fades out and is then removed. */
    public void sendAway(Pet p) {
        if (p.leaving) return;
        p.leaving = true;
        p.leaveTime = 0;
        p.staying = false;
        p.followTime = 0;
    }

    public void removePet(String id) {
        pets.removeIf(p -> p.id.equals(id));
    }

    public void clearPets() {
        pets.clear();
    }

    /** Returns the top-most pet whose body contains the point, or null. */
    public Pet petAt(double px, double py) {
        double w = petSize * 0.55;
        double h = petHeight() * 0.85;
        for (int i = pets.size() - 1; i >= 0; i--) {
            Pet p = pets.get(i);
            if (p.spawnPending) continue; // not placed yet
            if (p.behavior == Pet.Behavior.CLIMB) {
                // rotated: the body sticks out sideways from the wall, centred on p.y
                double dx = (px - p.x) * -p.climbSide;
                if (dx >= -2 && dx <= h && Math.abs(py - p.y) <= w) return p;
            } else if (Math.abs(px - p.x) <= w && py <= p.y + 2 && py >= p.y - h) {
                return p;
            }
        }
        return null;
    }

    public void pet(Pet p) {
        if (p.behavior == Pet.Behavior.SLEEP) {
            setBehavior(p, Pet.Behavior.SIT, 1.5 + rnd.nextDouble() * 2);
        }
        p.happyTimer = 2.2;
        p.hearts.add(0.0);
        if (p.behavior == Pet.Behavior.WALK || p.behavior == Pet.Behavior.RUN) {
            setBehavior(p, Pet.Behavior.IDLE, 2.2);
        }
    }

    public void grab(Pet p) {
        if (p.leaving && p.visitor) {
            // picked up on its way out: the visitor decides to stay a little longer (an adopted pet sent home still goes)
            p.leaving = false;
            p.fadingOut = false;
            p.fade = 1;
        }
        if (p.behavior == Pet.Behavior.CLIMB && p.climbWall != null) {
            p.y += petSize * 0.5; // the pet's anchor moves from mid-body (on the wall) back to its feet
        }
        p.climbWall = null;
        p.climbTarget = null;
        p.pendingClimb = null;
        p.behavior = Pet.Behavior.HELD;
        p.surface = null;
        p.vx = 0;
        p.vy = 0;
        p.stateTime = 0;
    }

    public void drag(Pet p, double x, double y) {
        p.x = clampToWorld(x);
        p.y = Math.max(petHeight(), Math.min(height - 1, y));
    }

    public void release(Pet p, double vx, double vy) {
        startFalling(p);
        p.vx = clampAbs(vx, 900);
        p.vy = clampAbs(vy, 900);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Commands (the pet's right-click menu)
    // ------------------------------------------------------------------------------------------------------------

    /**
     * Tells the pet to stay: it sits down where it is (right away, or once it has landed or finished climbing) and
     * stays put, now and then dozing off, until {@link #free}. It ignores balls, but still falls when its floor goes
     * away, and stays wherever the user drops it.
     */
    public void stay(Pet p) {
        if (p.leaving) return;
        p.staying = true;
        p.followTime = 0;
        if (p.surface != null && p.behavior != Pet.Behavior.SIT && p.behavior != Pet.Behavior.SLEEP) {
            setBehavior(p, Pet.Behavior.SIT, 6 + rnd.nextDouble() * 10);
        }
    }

    /** Ends {@link #stay} and {@link #follow}: the pet roams on its own again. */
    public void free(Pet p) {
        boolean wasBound = p.staying || p.followTime > 0;
        p.staying = false;
        p.followTime = 0;
        if (wasBound && p.surface != null && p.behavior != Pet.Behavior.SLEEP) {
            setBehavior(p, Pet.Behavior.IDLE, 0.3 + rnd.nextDouble() * 0.7);
        }
    }

    /** The pet runs after the mouse pointer (see {@link #setPointer}) for the given time, then sits down. */
    public void follow(Pet p, double seconds) {
        if (p.leaving) return;
        p.staying = false;
        p.followTime = seconds;
        if (p.surface != null) setBehavior(p, Pet.Behavior.CHASE, seconds);
    }

    /** Lies down for a nap right away. Returns false when the pet is not standing on anything. */
    public boolean nap(Pet p) {
        if (p.surface == null || p.leaving) return false;
        p.followTime = 0;
        setBehavior(p, Pet.Behavior.SLEEP, 25 + rnd.nextDouble() * 20);
        return true;
    }

    public void wake(Pet p) {
        if (p.behavior == Pet.Behavior.SLEEP) setBehavior(p, Pet.Behavior.SIT, 1 + rnd.nextDouble() * 2);
    }

    /** Turns a visitor into a regular pet that no longer leaves on its own; one on its way out turns back. */
    public void adopt(Pet p) {
        p.visitor = false;
        p.leaving = false;
        p.fadingOut = false;
    }

    /** Tosses a ball just for this pet, from where it stands towards the roomier side; only this pet goes after it. */
    public void throwBallFor(Pet p) {
        if (p.leaving) return;
        p.staying = false;
        p.followTime = 0;
        double r = Math.max(4, petSize * 0.13);
        double dir = p.x < width / 2 ? 1 : -1;
        double x = Math.max(r, Math.min(width - r, p.x + dir * petSize * 0.6));
        double y = Math.max(r, p.y - petHeight() * 2);
        ball = new Ball(x, y, dir * (140 + rnd.nextDouble() * 160), -120 - rnd.nextDouble() * 120, r);
        if (p.surface != null) setBehavior(p, Pet.Behavior.CHASE, 20);
    }

    /** Where the mouse pointer is, for pets that follow it. */
    public void setPointer(double x, double y) {
        pointerX = x;
        pointerY = y;
    }

    /** The pointer left the window: following pets wait where they are. */
    public void clearPointer() {
        pointerX = Double.NaN;
        pointerY = Double.NaN;
    }

    public boolean hasFollowers() {
        for (Pet p : pets) if (p.followTime > 0) return true;
        return false;
    }

    // ------------------------------------------------------------------------------------------------------------
    // Ball
    // ------------------------------------------------------------------------------------------------------------

    public void throwBall() {
        double r = Math.max(4, petSize * 0.13);
        boolean fromLeft = rnd.nextBoolean();
        double x = fromLeft ? width * (0.1 + rnd.nextDouble() * 0.3) : width * (0.6 + rnd.nextDouble() * 0.3);
        double vx = (fromLeft ? 1 : -1) * (180 + rnd.nextDouble() * 260);
        ball = new Ball(x, petHeight() + r, vx, -150 - rnd.nextDouble() * 150, r);
        for (Pet p : pets) {
            if (p.behavior == Pet.Behavior.HELD || p.staying || p.followTime > 0) continue;
            if (p.behavior == Pet.Behavior.SLEEP && rnd.nextDouble() < 0.5) continue;
            if (p.behavior != Pet.Behavior.AIRBORNE && p.behavior != Pet.Behavior.CLIMB) {
                setBehavior(p, Pet.Behavior.CHASE, 20);
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // Simulation
    // ------------------------------------------------------------------------------------------------------------

    private double gravity() {
        return 1500 * Math.sqrt(petSize / 44.0);
    }

    private double walkSpeed() {
        return petSize * 0.85 * speed;
    }

    private double runSpeed() {
        return petSize * 3.0 * speed;
    }

    /** Advances the simulation by dt seconds. */
    public void tick(double dt) {
        dt = Math.min(dt, 0.1);
        if (ball != null) tickBall(dt);
        for (Pet p : pets) {
            if (p.spawnPending) placeNewPet(p);
        }
        for (Pet p : pets) tickPet(p, dt);
        pets.removeIf(p -> p.leaving && p.fade <= 0);
    }

    private void tickPet(Pet p, double dt) {
        p.animTime += dt;
        p.stateTime += dt;
        if (p.happyTimer > 0) p.happyTimer -= dt;
        for (int i = p.hearts.size() - 1; i >= 0; i--) {
            double age = p.hearts.get(i) + dt / 1.4;
            if (age >= 1) p.hearts.remove(i);
            else p.hearts.set(i, age);
        }
        p.blinkTimer -= dt;
        if (p.blinkTimer <= 0) {
            p.blink = !p.blink;
            p.blinkTimer = p.blink ? 0.14 : 1.5 + rnd.nextDouble() * 4;
        }
        if (p.followTime > 0) {
            p.followTime -= dt;
            if (p.followTime <= 0) {
                p.followTime = 0;
                if (p.behavior == Pet.Behavior.CHASE) setBehavior(p, Pet.Behavior.SIT, 1.5 + rnd.nextDouble() * 2);
            }
        }

        if (!p.leaving && p.fade < 1) p.fade = Math.min(1, p.fade + dt * 2);
        if (p.leaving && tickLeaving(p, dt)) return;

        switch (p.behavior) {
            case HELD -> {
                p.gaitPhase += dt * 4;
                return;
            }
            case AIRBORNE -> {
                tickAirborne(p, dt);
                return;
            }
            case SLEEP -> p.energy = Math.min(1, p.energy + dt / 25);
            case WALK, RUN, CHASE, CLIMB -> p.energy = Math.max(0, p.energy - dt / 90);
            default -> p.energy = Math.max(0, p.energy - dt / 400);
        }

        if (p.behavior == Pet.Behavior.CLIMB) {
            tickClimb(p, dt);
            return;
        }
        if (p.surface == null) {
            startFalling(p);
            return;
        }

        switch (p.behavior) {
            case WALK, RUN -> tickMoving(p, dt);
            case CHASE -> tickChase(p, dt);
            default -> {
                p.vx = 0;
                if (p.stateTime >= p.stateDuration) decide(p);
            }
        }
    }

    /**
     * Walks a leaving visitor to the nearest window side and fades it out there (or wherever it gets stuck).
     *
     * @return true when the regular behaviour update must be skipped this frame
     */
    private boolean tickLeaving(Pet p, double dt) {
        p.leaveTime += dt;
        if (p.fadingOut) {
            p.fade = Math.max(0, p.fade - dt * 1.4);
            if (p.behavior == Pet.Behavior.WALK || p.behavior == Pet.Behavior.RUN) {
                p.x = clampToWorld(p.x + p.vx * dt);
                p.gaitPhase += Math.abs(p.vx) * dt / (petSize * 0.55) * Math.PI;
            }
            return p.behavior != Pet.Behavior.AIRBORNE && p.behavior != Pet.Behavior.CLIMB;
        }
        if (p.behavior == Pet.Behavior.AIRBORNE || p.behavior == Pet.Behavior.CLIMB || p.behavior == Pet.Behavior.HELD) {
            if (p.leaveTime > 15) p.fadingOut = true;
            return false; // finish the jump/climb first
        }
        double edge = p.x < width / 2 ? petSize * 0.5 : width - petSize * 0.5;
        double dir = Math.signum(edge - p.x);
        Surface s = p.surface;
        boolean blocked = s == null
                || (dir < 0 ? p.x <= s.x1() + petSize * 0.2 : p.x >= s.x2() - petSize * 0.2);
        if (Math.abs(edge - p.x) < 2 || blocked || p.leaveTime > 12) {
            p.fadingOut = true;
            return true;
        }
        p.pendingClimb = null;
        p.behavior = Pet.Behavior.WALK;
        p.faceRight = dir > 0;
        p.vx = dir * walkSpeed() * 1.4;
        p.x = clampToWorld(p.x + p.vx * dt);
        p.gaitPhase += Math.abs(p.vx) * dt / (petSize * 0.55) * Math.PI;
        return true;
    }

    private void tickMoving(Pet p, double dt) {
        double v = p.behavior == Pet.Behavior.RUN ? runSpeed() : walkSpeed();
        double dir = Math.signum(p.targetX - p.x);
        if (dir == 0 || Math.abs(p.targetX - p.x) < 1.5 || p.stateTime >= p.stateDuration) {
            if (p.pendingClimb != null && Math.abs(p.targetX - p.x) < 3) {
                startClimb(p, p.pendingClimb, p.pendingSide, p.pendingDown);
                return;
            }
            decide(p);
            return;
        }
        p.faceRight = dir > 0;
        p.vx = dir * v;
        stepAlongSurface(p, dt);
    }

    /** Moves the pet horizontally on its surface; handles edges (turn around or step off). */
    private void stepAlongSurface(Pet p, double dt) {
        double nx = p.x + p.vx * dt;
        p.gaitPhase += Math.abs(p.vx) * dt / (petSize * 0.55) * Math.PI;
        Surface s = p.surface;
        double edgeMargin = petSize * 0.15;
        // the usable part of the surface also ends at the window sides
        double lo = Math.max(s.x1(), petSize * 0.5 - edgeMargin);
        double hi = Math.min(s.x2(), width - petSize * 0.5 + edgeMargin);
        if (nx < lo + edgeMargin || nx > hi - edgeMargin) {
            double dir = Math.signum(p.vx);
            double edgeX = dir < 0 ? s.x1() - 1 : s.x2() + 1; // first x past the surface's end
            boolean edgeInsideWindow = edgeX > petSize * 0.5 && edgeX < width - petSize * 0.5;
            double drop = dropBelow(s, edgeX);
            boolean stepOff = edgeInsideWindow && s.y() < height - 2 && (p.behavior == Pet.Behavior.CHASE
                    ? chaseTargetBelow(p)
                    : rnd.nextDouble() < (drop < petSize * 6 ? 0.35 : 0.12));
            if (stepOff) {
                p.x = edgeX; // already past the edge, so the pet does not land back on the surface it left
                startFalling(p);
                p.vx = Math.signum(p.vx) * Math.min(Math.abs(p.vx), walkSpeed() * 1.5);
                return;
            }
            p.x = clampToWorld(s.clampX(nx, edgeMargin));
            if (p.behavior != Pet.Behavior.CHASE) {
                // turn around and walk somewhere else on this surface
                p.targetX = s.clampX(p.x - Math.signum(p.vx) * (petSize + rnd.nextDouble() * s.width() * 0.6), edgeMargin);
            } else {
                p.vx = 0;
            }
            return;
        }
        p.x = clampToWorld(nx);
    }

    /** Whatever the chasing pet is after (the ball, or the pointer it follows) is below its current level. */
    private boolean chaseTargetBelow(Pet p) {
        if (p.followTime > 0) return !Double.isNaN(pointerY) && pointerY > p.y + petHeight() * 0.5;
        return ball != null && ball.y > p.y;
    }

    /** Runs after the mouse pointer along the current surface, and jumps over to the surface under the pointer. */
    private void tickFollow(Pet p, double dt) {
        if (Double.isNaN(pointerX)) {
            p.vx = 0; // the pointer is outside the window: wait for it
            return;
        }
        double dx = pointerX - p.x;
        Surface under = surfaceUnderPointer();
        if (under != null && under != p.surface && p.stateTime > 0.4) {
            if (jumpTo(p, under, pointerX)) return;
        }
        if (Math.abs(dx) < petSize * 0.35) {
            p.vx = 0;
            return;
        }
        p.faceRight = dx > 0;
        p.vx = Math.signum(dx) * runSpeed() * (Math.abs(dx) > petSize * 3 ? 1 : 0.6);
        stepAlongSurface(p, dt);
    }

    /** The first surface at or below the pointer. */
    private Surface surfaceUnderPointer() {
        Surface best = null;
        for (Surface s : surfaces) {
            if (s.y() < pointerY - petHeight() * 0.5 || !s.spans(pointerX)) continue;
            if (best == null || s.y() < best.y()) best = s;
        }
        return best;
    }

    private void tickChase(Pet p, double dt) {
        if (p.followTime > 0) {
            tickFollow(p, dt);
            return;
        }
        if (ball == null || p.stateTime > p.stateDuration) {
            setBehavior(p, Pet.Behavior.IDLE, 1 + rnd.nextDouble() * 2);
            return;
        }
        double dx = ball.x - p.x;
        p.faceRight = dx > 0;
        boolean sameLevel = Math.abs(ball.bottom() - p.y) < petHeight() * 0.6;
        if (sameLevel && Math.abs(dx) < petSize * 0.4) {
            // caught it!
            ball = null;
            p.happyTimer = 3;
            p.hearts.add(0.0);
            for (Pet o : pets) {
                if (o.behavior == Pet.Behavior.CHASE && o.followTime <= 0) {
                    setBehavior(o, Pet.Behavior.IDLE, 0.5 + rnd.nextDouble() * 2);
                }
            }
            setBehavior(p, Pet.Behavior.SIT, 3 + rnd.nextDouble() * 3);
            return;
        }
        boolean ballHigher = ball.surface != null && ball.y < p.y - petHeight() * 0.5;
        boolean ballAcrossGap = ball.surface != null && sameLevel && !p.surface.spans(ball.x);
        if (ball.surface != null && ball.surface != p.surface && (ballHigher || ballAcrossGap)
                && Math.abs(ball.vx) < 40 && p.stateTime > 0.4) {
            // ball rests on a higher surface or across a gap: try to jump over to it
            if (jumpTo(p, ball.surface, ball.x)) return;
        }
        if (Math.abs(dx) < 2) {
            p.vx = 0;
            return;
        }
        p.vx = Math.signum(dx) * runSpeed() * 1.1;
        stepAlongSurface(p, dt);
    }

    private void tickAirborne(Pet p, double dt) {
        double prevY = p.y;
        p.vy += gravity() * dt;
        p.x += p.vx * dt;
        p.y += p.vy * dt;
        if (p.vy > 0) p.fallSpeed = Math.max(p.fallSpeed, p.vy);
        if (p.x < petSize * 0.5 || p.x > width - petSize * 0.5) {
            p.x = clampToWorld(p.x);
            p.vx = -p.vx * 0.3;
        }
        if (p.vx != 0) p.faceRight = p.vx > 0;
        if (p.vy >= 0) {
            Surface landing = null;
            for (Surface s : surfaces) {
                if (prevY <= s.y() + 0.5 && p.y >= s.y() && s.spans(p.x)) {
                    if (landing == null || s.y() < landing.y()) landing = s;
                }
            }
            if (landing != null) {
                land(p, landing);
            }
        }
        if (p.y > height) {
            // safety net: below the floor
            Surface floor = find(FLOOR_KEY, "window bottom");
            if (floor != null) land(p, floor);
            else p.y = height - 1;
        }
    }

    private void land(Pet p, Surface s) {
        boolean hard = p.fallSpeed > 700;
        p.surface = s;
        p.y = s.y();
        p.x = s.clampX(p.x, 0);
        p.vx = 0;
        p.vy = 0;
        p.fallSpeed = 0;
        if (p.staying) {
            setBehavior(p, Pet.Behavior.SIT, 2 + rnd.nextDouble() * 4);
        } else if (p.followTime > 0) {
            setBehavior(p, Pet.Behavior.CHASE, p.followTime);
        } else if (ball != null && rnd.nextDouble() < 0.8) {
            setBehavior(p, Pet.Behavior.CHASE, 15);
        } else if (hard) {
            setBehavior(p, Pet.Behavior.SIT, 0.8 + rnd.nextDouble());
        } else {
            setBehavior(p, Pet.Behavior.IDLE, 0.3 + rnd.nextDouble() * 0.8);
        }
    }

    private void startFalling(Pet p) {
        if (p.behavior == Pet.Behavior.CLIMB && p.climbWall != null) {
            // let go of the wall: move the feet point off the wall and drop
            p.x = clampToWorld(p.climbWall.x() - p.climbSide * petSize * 0.45);
            p.y += petSize * 0.5;
        }
        p.climbWall = null;
        p.climbTarget = null;
        p.pendingClimb = null;
        p.behavior = Pet.Behavior.AIRBORNE;
        p.surface = null;
        p.stateTime = 0;
        p.fallSpeed = 0;
        if (p.vy < 0) p.vy = 0;
    }

    private void setBehavior(Pet p, Pet.Behavior b, double duration) {
        p.pendingClimb = null;
        p.behavior = b;
        p.stateTime = 0;
        p.stateDuration = duration;
        if (b != Pet.Behavior.WALK && b != Pet.Behavior.RUN && b != Pet.Behavior.CHASE) p.vx = 0;
    }

    /** Chooses what to do next when the current behaviour finishes. */
    private void decide(Pet p) {
        Surface s = p.surface;
        if (s == null) {
            startFalling(p);
            return;
        }
        if (p.staying) {
            // staying put: keep sitting, and now and then doze off
            boolean doze = p.behavior != Pet.Behavior.SLEEP && rnd.nextDouble() < 0.2 + (1 - p.energy) * 0.3;
            if (doze) setBehavior(p, Pet.Behavior.SLEEP, 15 + rnd.nextDouble() * 20);
            else setBehavior(p, Pet.Behavior.SIT, 6 + rnd.nextDouble() * 10);
            return;
        }
        double tired = 1 - p.energy;
        double move = 0.25 + activity * 0.5;
        double wWalk = 34 * move;
        double wRun = 8 * move * p.energy;
        double wJump = 22 * move * (0.5 + p.energy);
        double wIdle = 14;
        double wSit = 14 + tired * 10;
        double wSleep = (p.behavior == Pet.Behavior.SLEEP ? 0 : 3) + tired * tired * 30 * (1.2 - activity);
        // pets low in the window are keen to climb back up
        double wClimb = 7 * move * (p.y > height * 0.6 ? 3 : 1);

        double total = wWalk + wRun + wJump + wIdle + wSit + wSleep + wClimb;
        double r = rnd.nextDouble() * total;
        double edgeMargin = petSize * 0.15;

        if ((r -= wClimb) < 0) {
            if (tryApproachWall(p)) return;
            r = 0; // fall through to walking
        } else if ((r -= wJump) < 0) {
            if (tryRandomJump(p)) return;
            r = 0; // fall through to walking
        }
        if ((r -= wWalk) < 0 || r == 0) {
            double dist = petSize * (1 + rnd.nextDouble() * 5);
            double dir = rnd.nextDouble() < 0.6 ? (p.faceRight ? 1 : -1) : (p.faceRight ? -1 : 1);
            p.targetX = s.clampX(p.x + dir * dist, edgeMargin);
            if (Math.abs(p.targetX - p.x) < 4) p.targetX = s.clampX(p.x - dir * dist, edgeMargin);
            // sometimes deliberately walk off the edge to explore
            if (rnd.nextDouble() < 0.25 && s.y() < height - 2) p.targetX = p.x + dir * dist;
            setBehavior(p, Pet.Behavior.WALK, 4 + rnd.nextDouble() * 8);
            return;
        }
        if ((r -= wRun) < 0) {
            double dir = rnd.nextBoolean() ? 1 : -1;
            p.targetX = s.clampX(p.x + dir * petSize * (3 + rnd.nextDouble() * 8), edgeMargin);
            setBehavior(p, Pet.Behavior.RUN, 1.5 + rnd.nextDouble() * 3);
            return;
        }
        if ((r -= wIdle) < 0) {
            if (rnd.nextDouble() < 0.3) p.faceRight = !p.faceRight;
            setBehavior(p, Pet.Behavior.IDLE, 1.5 + rnd.nextDouble() * 3);
            return;
        }
        if ((r -= wSit) < 0) {
            setBehavior(p, Pet.Behavior.SIT, 3 + rnd.nextDouble() * 8);
            return;
        }
        setBehavior(p, Pet.Behavior.SLEEP, 10 + rnd.nextDouble() * 25);
    }

    /** Picks a climbable wall reachable by walking along the current surface and heads for its foot. */
    private boolean tryApproachWall(Pet p) {
        Surface s = p.surface;
        if (s == null) return false;
        List<double[]> options = new ArrayList<>(); // {wallIndex, side, standX}
        double reach = petSize * 9;
        boolean low = p.y > height * 0.6;
        for (int i = 0; i < walls.size(); i++) {
            Wall w = walls.get(i);
            // up: the wall must reach down to our level and rise well above us
            boolean canUp = w.y1() <= p.y - petHeight() * 1.5 && w.y2() >= p.y - 3;
            // down: the wall must start at (or above) our level and go well below us
            boolean canDown = w.y1() <= p.y + 3 && w.y2() >= p.y + petHeight() * 2;
            for (int side : new int[]{+1, -1}) {
                if (side > 0 && !w.fromLeft() || side < 0 && !w.fromRight()) continue;
                double standX = w.x() - side * petSize * 0.5;
                if (standX < s.x1() - 1 || standX > s.x2() + 1) continue;
                if (Math.abs(standX - p.x) > reach) continue;
                if (canUp && nearestSurfaceAbove(w, side, p.y) != null) {
                    options.add(new double[]{i, side, standX, 0});
                    if (low) options.add(new double[]{i, side, standX, 0}); // pets low down prefer going up
                }
                if (canDown && nearestSurfaceBelow(w, side, p.y) != null) {
                    options.add(new double[]{i, side, standX, 1});
                }
            }
        }
        if (options.isEmpty()) return false;
        double[] o = options.get(rnd.nextInt(options.size()));
        double standX = clampToWorld(o[2]);
        setBehavior(p, Pet.Behavior.WALK, 4 + Math.abs(standX - p.x) / Math.max(1, walkSpeed()) * 1.5);
        p.targetX = standX;
        p.pendingClimb = walls.get((int) o[0]);
        p.pendingSide = (int) o[1];
        p.pendingDown = o[3] > 0;
        return true;
    }

    /** The highest surface below {@code fromY} that a pet climbing down {@code w} on the given side can step onto. */
    private Surface nearestSurfaceBelow(Wall w, int side, double fromY) {
        double stepX = w.x() - side * petSize * 0.45;
        Surface best = null;
        for (Surface s : surfaces) {
            if (s.y() <= fromY + petHeight() * 1.5 || s.y() > w.y2() + 3) continue;
            if (!s.spans(stepX)) continue;
            if (best == null || s.y() < best.y()) best = s;
        }
        return best;
    }

    /** How far a pet would fall when stepping off surface {@code s} at {@code x}. */
    private double dropBelow(Surface s, double x) {
        double best = height - s.y();
        for (Surface o : surfaces) {
            if (o.y() > s.y() + 1 && o.spans(x)) best = Math.min(best, o.y() - s.y());
        }
        return best;
    }

    /** The lowest surface above {@code fromY} that a pet climbing {@code w} on the given side can step onto. */
    private Surface nearestSurfaceAbove(Wall w, int side, double fromY) {
        double stepX = w.x() - side * petSize * 0.45;
        Surface best = null;
        for (Surface s : surfaces) {
            if (s.y() >= fromY - petHeight() * 0.8 || s.y() < w.y1() - 3) continue;
            if (!s.spans(stepX)) continue;
            if (best == null || s.y() > best.y()) best = s;
        }
        return best;
    }

    private void startClimb(Pet p, Wall w, int side, boolean down) {
        Surface target = down ? nearestSurfaceBelow(w, side, p.y) : nearestSurfaceAbove(w, side, p.y);
        if (target == null) {
            setBehavior(p, Pet.Behavior.SIT, 1 + rnd.nextDouble() * 2);
            return;
        }
        // going up the pet starts with its feet on the surface; going down it hangs just below the edge
        double startY = down ? p.y + petSize * 0.5 : p.y - petSize * 0.5;
        beginClimb(p, w, side, down, target, startY);
    }

    private void beginClimb(Pet p, Wall w, int side, boolean down, Surface target, double startY) {
        setBehavior(p, Pet.Behavior.CLIMB, 120);
        p.climbDown = down;
        p.surface = null;
        p.climbWall = w;
        p.climbSide = side;
        p.climbTarget = target;
        p.x = w.x();
        p.y = startY;
        p.vx = 0;
        p.vy = 0;
        p.faceRight = side > 0;
    }

    private void tickClimb(Pet p, double dt) {
        Wall w = p.climbWall;
        if (w == null) {
            startFalling(p);
            return;
        }
        double v = climbSpeed() * (p.climbDown ? 1.25 : 1);
        p.y += p.climbDown ? v * dt : -v * dt;
        p.gaitPhase += v * dt / (petSize * 0.55) * Math.PI;
        double top = p.y - petSize * 0.5;
        double bottom = p.y + petSize * 0.5;
        Surface target = p.climbTarget;
        boolean arrived = target != null && (p.climbDown ? bottom >= target.y() : top <= target.y());
        if (arrived) {
            double nx = w.x() - p.climbSide * petSize * 0.45;
            p.climbWall = null;
            p.climbTarget = null;
            p.surface = target;
            p.y = target.y();
            p.x = clampToWorld(target.clampX(nx, 0));
            setBehavior(p, rnd.nextDouble() < 0.5 ? Pet.Behavior.SIT : Pet.Behavior.IDLE, 0.8 + rnd.nextDouble() * 2);
            return;
        }
        boolean endOfWall = p.climbDown ? bottom >= w.y2() : top <= w.y1();
        if (target == null || endOfWall || p.stateTime > p.stateDuration || rnd.nextDouble() < dt * 0.015) {
            int side = p.climbSide;
            startFalling(p); // nowhere to go, got tired, or simply let go
            p.vx = -side * walkSpeed();
        }
    }

    private double climbSpeed() {
        return petSize * 1.6 * speed;
    }

    private boolean tryRandomJump(Pet p) {
        List<Surface> candidates = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        double maxUp = Math.max(110, petSize * 3.2);
        double maxDx = Math.max(160, petSize * 5);
        for (Surface s : surfaces) {
            if (s == p.surface) continue;
            double dy = p.y - s.y(); // > 0 means the target is above
            if (dy > maxUp || dy < -Math.max(260, petSize * 7)) continue;
            if (Math.abs(dy) < 6 && Math.abs(s.clampX(p.x, 0) - p.x) < 2) continue;
            double nx = s.clampX(p.x, petSize * 0.3);
            if (Math.abs(nx - p.x) > maxDx) continue;
            candidates.add(s);
            weights.add(dy > 0 ? 2.0 : 1.0);
        }
        if (candidates.isEmpty()) return false;
        double total = weights.stream().mapToDouble(Double::doubleValue).sum();
        double r = rnd.nextDouble() * total;
        for (int i = 0; i < candidates.size(); i++) {
            r -= weights.get(i);
            if (r <= 0) {
                Surface s = candidates.get(i);
                double tx = s.clampX(p.x + (rnd.nextDouble() - 0.5) * petSize * 3, petSize * 0.3);
                return jumpTo(p, s, tx);
            }
        }
        return false;
    }

    /** Launches a ballistic jump that lands at (tx, target.y). */
    private boolean jumpTo(Pet p, Surface target, double tx) {
        double maxUp = Math.max(110, petSize * 3.2);
        double ty = target.y();
        if (p.y - ty > maxUp) return false;
        tx = target.clampX(tx, petSize * 0.3);
        double g = gravity();
        double apexY = Math.min(p.y, ty) - petSize * (0.35 + rnd.nextDouble() * 0.4);
        double vy0 = -Math.sqrt(2 * g * (p.y - apexY));
        double t1 = -vy0 / g;
        double t2 = Math.sqrt(2 * Math.max(0, ty - apexY) / g);
        double vx = (tx - p.x) / Math.max(0.05, t1 + t2);
        if (Math.abs(vx) > petSize * 12) return false;
        if (p.surface != null && ty > p.y + 1) {
            // on the way down the pet passes its own level again after 2*t1; if that point is still above its own
            // surface it would simply land back where it started
            double xBack = p.x + vx * 2 * t1;
            if (p.surface.spans(xBack) || p.surface.spans(tx)) return false;
        }
        startFalling(p);
        p.vx = vx;
        p.vy = vy0;
        p.faceRight = vx >= 0;
        return true;
    }

    private void tickBall(double dt) {
        Ball b = ball;
        b.age += dt;
        b.spin += b.vx * dt / Math.max(1, b.radius);
        if (b.age > 30) {
            ball = null;
            return;
        }
        if (b.surface != null) {
            b.x += b.vx * dt;
            b.vx *= Math.pow(0.35, dt); // rolling friction
            if (Math.abs(b.vx) < 3) b.vx = 0;
            if (!b.surface.spans(b.x)) {
                b.surface = null; // rolled off the edge
            }
        } else {
            double prevBottom = b.bottom();
            b.vy += gravity() * 0.8 * dt;
            b.x += b.vx * dt;
            b.y += b.vy * dt;
            if (b.vy > 0) {
                Surface hit = null;
                for (Surface s : surfaces) {
                    if (prevBottom <= s.y() + 0.5 && b.bottom() >= s.y() && s.spans(b.x)) {
                        if (hit == null || s.y() < hit.y()) hit = s;
                    }
                }
                if (hit != null) {
                    b.y = hit.y() - b.radius;
                    if (b.vy > 120) {
                        b.vy = -b.vy * 0.55;
                        b.vx *= 0.85;
                    } else {
                        b.vy = 0;
                        b.surface = hit;
                    }
                }
            }
            if (b.y > height) {
                b.y = height - 1 - b.radius;
                b.vy = 0;
            }
        }
        if (b.x - b.radius < 0) {
            b.x = b.radius;
            b.vx = Math.abs(b.vx) * 0.7;
        } else if (b.x + b.radius > width) {
            b.x = width - b.radius;
            b.vx = -Math.abs(b.vx) * 0.7;
        }
    }

    private double clampToWorld(double x) {
        double m = petSize * 0.5;
        if (width < 2 * m) return width / 2;
        return Math.max(m, Math.min(width - m, x));
    }

    private static double clampAbs(double v, double max) {
        return Math.max(-max, Math.min(max, v));
    }
}
