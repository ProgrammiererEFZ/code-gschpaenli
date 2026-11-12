package io.github.programmiererefz.codegschpaenli.world;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldTest {

    private static World newWorld() {
        World world = new World(42);
        world.setBounds(800, 600);
        world.setSurfaces(List.of());
        world.setWalls(List.of());
        return world;
    }

    @Test
    void droppedPetLandsOnTheFloorAndStaysInsideTheWindow() {
        World world = newWorld();
        Pet pet = world.addPet("p1", PetKind.CAT, PetKind.CAT.palettes().getFirst(), "Mimi");

        for (int i = 0; i < 30 * 5; i++) world.tick(1.0 / 30); // five simulated seconds at 30 fps

        assertNotNull(pet.surface, "pet should have landed on a surface");
        assertEquals(World.FLOOR_KEY, pet.surface.key());
        assertEquals(world.height() - 1, pet.y, 1e-6);
        assertTrue(pet.x >= 0 && pet.x <= world.width(), "pet left the window: x=" + pet.x);
    }

    @Test
    void newPetsAppearOnManySurfacesAndSometimesClimbing() {
        World world = new World(7);
        world.setBounds(800, 600);
        world.setSurfaces(List.of(
                new Surface("toolbar", "toolbar", 0, 800, 60),
                new Surface("tabs", "editor tabs", 200, 800, 120),
                new Surface("project", "project view", 0, 200, 300),
                new Surface("terminal", "terminal", 200, 800, 420)));
        world.setWalls(List.of(new Wall("divider", "project divider", 200, 60, 600, true, true)));

        Set<String> landedOn = new HashSet<>();
        int climbing = 0;
        for (int i = 0; i < 200; i++) {
            Pet pet = world.addPet("p" + i, PetKind.CAT, PetKind.CAT.palettes().getFirst(), "Mimi");
            world.tick(1.0 / 30);
            assertTrue(pet.x >= 0 && pet.x <= world.width(), "pet outside the window: x=" + pet.x);
            assertTrue(pet.y >= 0 && pet.y <= world.height(), "pet outside the window: y=" + pet.y);
            if (pet.behavior == Pet.Behavior.CLIMB) {
                climbing++;
                assertNotNull(pet.climbTarget);
                assertTrue(pet.climbTarget.y() < pet.y, "a spawned climber should head up");
            } else {
                // let it drop onto its surface
                for (int t = 0; t < 30 && pet.surface == null; t++) world.tick(1.0 / 30);
                assertNotNull(pet.surface, "pet should have landed");
                landedOn.add((String) pet.surface.key());
            }
            world.removePet(pet.id);
        }

        assertEquals(Set.of("toolbar", "tabs", "project", "terminal", World.FLOOR_KEY), landedOn);
        assertTrue(climbing > 20 && climbing < 120, "climbing spawns: " + climbing);
    }

    @Test
    void removedPetIsGone() {
        World world = newWorld();
        world.addPet("p1", PetKind.DOG, PetKind.DOG.palettes().getFirst(), "Rex");
        world.removePet("p1");
        assertTrue(world.pets().isEmpty());
    }
}
