// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.settings.PetNames;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Pet;
import io.github.programmiererefz.codegschpaenli.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Random visitors for one window: keeps the number of visiting pets within the configured range, lets each one stay
 * for a while (one to eight minutes), then sends it on its way and lets someone new drop in.
 */
final class Visitors {

    private static final double MIN_STAY = 60;
    private static final double MAX_STAY = 480;
    private static final double SPAWN_GAP = 2.5;

    private final World world;
    private final Random rnd;
    private final Map<String, Double> stayLeft = new HashMap<>();
    private double nextEvent = 8;
    private double cooldown;
    private int counter;

    Visitors(World world, long seed) {
        this.world = world;
        this.rnd = new Random(seed);
    }

    boolean active(PetsSettings.StateBean s) {
        return s.visitorsEnabled || !stayLeft.isEmpty();
    }

    void tick(double dt, PetsSettings.StateBean s) {
        List<Pet> present = new ArrayList<>();
        for (Pet p : world.pets()) {
            if (p.visitor && !p.leaving) present.add(p);
        }
        stayLeft.keySet().removeIf(id -> world.pets().stream().noneMatch(p -> p.id.equals(id)));

        List<PetKind> kinds = allowedKinds(s);
        if (!s.visitorsEnabled || kinds.isEmpty()) {
            for (Pet p : present) world.sendAway(p);
            return;
        }
        int min = Math.max(0, Math.min(20, s.visitorsMin));
        int max = Math.max(min, Math.min(20, s.visitorsMax));
        for (Pet p : present) stayLeft.merge(p.id, -dt, Double::sum);
        cooldown -= dt;
        int n = present.size();

        if (n > max) {
            if (cooldown <= 0) {
                world.sendAway(present.get(rnd.nextInt(n)));
                cooldown = SPAWN_GAP;
            }
            return;
        }
        if (n < min) {
            if (cooldown <= 0) {
                spawn(kinds);
                cooldown = SPAWN_GAP;
            }
            return;
        }

        nextEvent -= dt;
        if (nextEvent > 0) return;
        nextEvent = 20 + rnd.nextDouble() * 70;

        List<Pet> overstayed = new ArrayList<>();
        for (Pet p : present) {
            if (stayLeft.getOrDefault(p.id, 0.0) <= 0) overstayed.add(p);
        }
        boolean canArrive = n < max;
        // a visitor whose time is up may leave even at the minimum: a replacement then drops in shortly after
        boolean canLeave = !overstayed.isEmpty();
        if (canLeave && (!canArrive || rnd.nextBoolean())) {
            world.sendAway(overstayed.get(rnd.nextInt(overstayed.size())));
            cooldown = SPAWN_GAP * 2;
        } else if (canArrive && rnd.nextDouble() < 0.7) {
            spawn(kinds);
        }
    }

    private void spawn(List<PetKind> kinds) {
        PetKind kind = kinds.get(rnd.nextInt(kinds.size()));
        PetKind.Palette pal = kind.palettes().get(rnd.nextInt(kind.palettes().size()));
        String id = "visitor-" + (counter++) + "-" + Long.toHexString(rnd.nextLong());
        world.addVisitor(id, kind, pal, PetNames.random(kind));
        stayLeft.put(id, MIN_STAY + rnd.nextDouble() * (MAX_STAY - MIN_STAY));
    }

    private static List<PetKind> allowedKinds(PetsSettings.StateBean s) {
        List<PetKind> kinds = new ArrayList<>();
        if (s.visitorsCartoon) kinds.addAll(PetKind.inGroup(PetKind.Group.CARTOON));
        if (s.visitorsPixel) kinds.addAll(PetKind.inGroup(PetKind.Group.PIXEL));
        return kinds;
    }
}
