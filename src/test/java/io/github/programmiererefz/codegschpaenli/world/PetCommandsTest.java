package io.github.programmiererefz.codegschpaenli.world;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** The commands of a pet's right-click menu, at the simulation level. */
class PetCommandsTest {

    private static final double DT = 1.0 / 30;

    private static World newWorld() {
        World world = new World(42);
        world.setBounds(800, 600);
        world.setSurfaces(List.of());
        world.setWalls(List.of());
        world.setActivity(1); // restless pets, so standing still is down to the command
        return world;
    }

    private static void run(World world, double seconds) {
        for (int i = 0; i < Math.round(seconds / DT); i++) world.tick(DT);
    }

    private static Pet landedPet(World world, String id) {
        Pet p = world.addPet(id, PetKind.CAT, PetKind.CAT.palettes().getFirst(), "Mimi");
        run(world, 3);
        assertNotNull(p.surface, "pet should have landed");
        return p;
    }

    @Test
    void stayingPetSitsAndDoesNotMove() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.stay(p);
        assertEquals(Pet.Behavior.SIT, p.behavior);
        double x = p.x;
        for (int i = 0; i < 30 * 120; i++) {
            world.tick(DT);
            assertTrue(p.behavior == Pet.Behavior.SIT || p.behavior == Pet.Behavior.SLEEP, "behaviour " + p.behavior);
            assertEquals(x, p.x, 1e-9);
        }
    }

    @Test
    void stayingPetIgnoresTheBall() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.stay(p);
        double x = p.x;
        world.throwBall();
        for (int i = 0; i < 30 * 10; i++) {
            world.tick(DT);
            assertNotEquals(Pet.Behavior.CHASE, p.behavior);
        }
        assertEquals(x, p.x, 1e-9);
    }

    @Test
    void stayingPetDroppedSomewhereElseStaysThere() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.stay(p);
        world.grab(p);
        world.drag(p, 200, 300);
        world.release(p, 0, 0);
        run(world, 3);
        assertNotNull(p.surface);
        assertEquals(200, p.x, 1e-6);
        run(world, 60);
        assertEquals(200, p.x, 1e-6);
        assertTrue(p.staying);
    }

    @Test
    void freedPetRoamsAgain() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.stay(p);
        run(world, 10);
        double x = p.x;
        world.free(p);
        assertFalse(p.staying);
        boolean moved = false;
        for (int i = 0; i < 30 * 60 && !moved; i++) {
            world.tick(DT);
            moved = Math.abs(p.x - x) > world.petSize();
        }
        assertTrue(moved, "a freed pet should walk around again");
    }

    @Test
    void adoptedPetSentAwayWalksOffAndIsRemoved() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.stay(p);
        world.sendAway(p);
        assertFalse(p.staying, "leaving ends staying");
        run(world, 0.5);
        // being picked up on the way out does not change its mind (unlike a visitor)
        world.grab(p);
        assertTrue(p.leaving);
        world.release(p, 0, 0);
        run(world, 30);
        assertTrue(world.pets().isEmpty(), "the pet should have gone");
    }

    @Test
    void visitorPickedUpOnItsWayOutStays() {
        World world = newWorld();
        Pet v = world.addVisitor("v1", PetKind.DOG, PetKind.DOG.palettes().getFirst(), "Rex");
        run(world, 3);
        world.sendAway(v);
        world.grab(v);
        assertFalse(v.leaving);
    }

    @Test
    void adoptedVisitorNoLongerLeaves() {
        World world = newWorld();
        Pet v = world.addVisitor("v1", PetKind.DOG, PetKind.DOG.palettes().getFirst(), "Rex");
        run(world, 3);
        world.sendAway(v);
        run(world, 0.5);
        world.adopt(v);
        assertFalse(v.visitor);
        assertFalse(v.leaving);
        run(world, 30);
        assertTrue(world.pets().contains(v));
        assertEquals(1, v.fade, 1e-9);
    }

    @Test
    void followingPetRunsToThePointerAndSitsDownAfterwards() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        double target = p.x < world.width() / 2 ? 700 : 100;
        world.setPointer(target, world.height() - 20);
        world.follow(p, 5);
        assertTrue(p.isFollowing());
        assertTrue(world.hasFollowers());
        run(world, 4);
        assertEquals(target, p.x, world.petSize());
        run(world, 1.2);
        assertFalse(p.isFollowing());
        assertFalse(world.hasFollowers());
        assertEquals(Pet.Behavior.SIT, p.behavior);
    }

    @Test
    void followingPetWaitsWhileThePointerIsOutside() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        world.clearPointer();
        world.follow(p, 10);
        double x = p.x;
        run(world, 5);
        assertEquals(x, p.x, 1e-9);
        assertTrue(p.isFollowing());
    }

    @Test
    void napAndWakeUp() {
        World world = newWorld();
        Pet p = landedPet(world, "p1");
        assertTrue(world.nap(p));
        assertEquals(Pet.Behavior.SLEEP, p.behavior);
        run(world, 10);
        assertEquals(Pet.Behavior.SLEEP, p.behavior);
        world.wake(p);
        assertEquals(Pet.Behavior.SIT, p.behavior);
    }

    @Test
    void ballThrownForOnePetIsChasedOnlyByIt() {
        World world = newWorld();
        Pet a = landedPet(world, "a");
        Pet b = landedPet(world, "b");
        world.stay(a); // playing ends staying
        world.throwBallFor(a);
        assertNotNull(world.ball());
        assertFalse(a.staying);
        assertEquals(Pet.Behavior.CHASE, a.behavior);
        assertNotEquals(Pet.Behavior.CHASE, b.behavior);
    }
}
