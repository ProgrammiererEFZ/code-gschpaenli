package io.github.programmiererefz.codegschpaenli.world;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void removedPetIsGone() {
        World world = newWorld();
        world.addPet("p1", PetKind.DOG, PetKind.DOG.palettes().getFirst(), "Rex");
        world.removePet("p1");
        assertTrue(world.pets().isEmpty());
    }
}
