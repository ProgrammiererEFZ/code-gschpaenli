// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.settings;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Default names offered when adopting a pet. */
public final class PetNames {

    private static final List<String> COMMON = List.of(
            "Pixel", "Byte", "Mochi", "Biscuit", "Nugget", "Pepper", "Waffles", "Noodle", "Tofu", "Pickle",
            "Sprout", "Maple", "Cookie", "Juniper", "Ziggy", "Olive", "Bean", "Dumpling", "Clover", "Sushi");

    private static final List<String> CAT = List.of("Whiskers", "Luna", "Tiger", "Miso", "Felix", "Kernel", "Socks", "Nova");
    private static final List<String> DOG = List.of("Buddy", "Rex", "Scout", "Bailey", "Ruby", "Lambda", "Max", "Daisy");
    private static final List<String> FOX = List.of("Ember", "Rusty", "Firefox", "Sly", "Copper", "Saffron", "Kit");
    private static final List<String> BUNNY = List.of("Thumper", "Clover", "Hopper", "Cottontail", "Bun", "Daisy");
    private static final List<String> PANDA = List.of("Bao", "Bamboo", "Mei", "Oreo", "Panko", "Lin");
    private static final List<String> DUCK = List.of("Quackers", "Puddle", "Waddles", "Ducky", "Rubber", "Splash");
    private static final List<String> FROG = List.of("Ribbit", "Lily", "Hopscotch", "Toad", "Croak");
    private static final List<String> HEDGEHOG = List.of("Spike", "Prickles", "Bramble", "Quill", "Hazel");
    private static final List<String> RACCOON = List.of("Bandit", "Rocket", "Rascal", "Trashy", "Ringo", "Masky");
    private static final List<String> HAMSTER = List.of("Peanut", "Cheeks", "Hammy", "Butterscotch", "Nibbles", "Pip");
    private static final List<String> PIG = List.of("Truffle", "Hamlet", "Oinkers", "Peppa", "Bacon", "Snout");
    private static final List<String> PENGUIN = List.of("Tux", "Pebble", "Waddle", "Flipper", "Iceberg", "Pingu");
    private static final List<String> MOUSE = List.of("Squeak", "Cheddar", "Whisper", "Click", "Brie", "Scroll");
    private static final List<String> SQUIRREL = List.of("Hazelnut", "Acorn", "Chip", "Nutmeg", "Scamper", "Cache");
    private static final List<String> TURTLE = List.of("Shelly", "Tortellini", "Speedy", "Sheldon", "Crush", "Mossy");
    private static final List<String> KOALA = List.of("Eucalyptus", "Kip", "Gumtree", "Matilda", "Snoozy", "Bluey");
    private static final List<String> SHEEP = List.of("Woolly", "Dolly", "Fleecy", "Baabara", "Cotton", "Shaun");
    private static final List<String> LION = List.of("Leo", "Simba", "Nala", "Mufasa", "Sunny", "Roary");
    private static final List<String> OWL = List.of("Hoot", "Hedwig", "Athena", "Nightly", "Archimedes", "Hooty");
    private static final List<String> LADYBUG = List.of("Dotty", "Scarlet", "Polka", "Breakpoint", "Spotty", "Bugsy", "Lady");
    private static final List<String> SEAL = List.of("Selkie", "Splash", "Blubber", "Sandy", "Pebbles", "Squishy");
    private static final List<String> CHICKEN = List.of("Henrietta", "Nugget", "Pecky", "Clucky", "Eggbert", "Drumstick");
    private static final List<String> AXOLOTL = List.of("Axel", "Gilly", "Lotl", "Bubbles", "Frilly", "Mochi");
    private static final List<String> PARROT = List.of("Polly", "Kiwi", "Mango", "Echo", "Captain", "Rio", "Paprika");
    private static final List<String> COCKATOO = List.of("Crest", "Sunny", "Snowball", "Cookie", "Banjo", "Mohawk");
    private static final List<String> BEE = List.of("Buzz", "Honey", "Bumble", "Pollen", "Beeswax", "Clover", "Stinger");
    private static final List<String> CRAB = List.of("Clawdia", "Snips", "Pincer", "Coral", "Bisque", "Sidestep");
    private static final List<String> SNAIL = List.of("Escargot", "Glide", "Comet", "Slimey", "Spiral", "Shelldon", "Lazy Loader");
    private static final List<String> BAT = List.of("Echo", "Mango", "Fig", "Nightshade", "Batsy", "Ping", "Dusk");
    private static final List<String> OCELOT = List.of("Rosetta", "Ozzy", "Speckles", "Pounce", "Dotcom", "Tawny");
    private static final List<String> OTTER = List.of("Otto", "Ripple", "Kelp", "Brook", "Paddle", "Pebble", "Slick");

    private PetNames() {
    }

    public static String random(PetKind kind) {
        List<String> specific = switch (kind.name().replace("PIXEL_", "")) {
            case "CAT" -> CAT;
            case "DOG" -> DOG;
            case "FOX" -> FOX;
            case "BUNNY" -> BUNNY;
            case "PANDA" -> PANDA;
            case "DUCK" -> DUCK;
            case "FROG" -> FROG;
            case "HEDGEHOG" -> HEDGEHOG;
            case "RACCOON" -> RACCOON;
            case "HAMSTER" -> HAMSTER;
            case "PIG" -> PIG;
            case "PENGUIN" -> PENGUIN;
            case "MOUSE" -> MOUSE;
            case "TURTLE" -> TURTLE;
            case "KOALA" -> KOALA;
            case "SHEEP" -> SHEEP;
            case "LION" -> LION;
            case "OWL" -> OWL;
            case "SQUIRREL" -> SQUIRREL;
            case "LADYBUG" -> LADYBUG;
            case "SEAL" -> SEAL;
            case "CHICKEN" -> CHICKEN;
            case "AXOLOTL" -> AXOLOTL;
            case "PARROT" -> PARROT;
            case "COCKATOO" -> COCKATOO;
            case "BEE" -> BEE;
            case "CRAB" -> CRAB;
            case "SNAIL" -> SNAIL;
            case "BAT" -> BAT;
            case "OCELOT" -> OCELOT;
            case "OTTER" -> OTTER;
            default -> COMMON;
        };
        ThreadLocalRandom r = ThreadLocalRandom.current();
        List<String> pool = r.nextBoolean() ? specific : COMMON;
        return pool.get(r.nextInt(pool.size()));
    }

    /** A short greeting for the roll call. */
    public static String greeting(PetKind kind) {
        return switch (kind.name().replace("PIXEL_", "")) {
            case "CAT" -> "Meow! Your code compiles... probably.";
            case "DOG" -> "Woof! Shall we fetch some bugs?";
            case "FOX" -> "Yip! What does the fox refactor?";
            case "BUNNY" -> "*twitches nose* Hop to it!";
            case "PANDA" -> "*munches bamboo* Take a break soon.";
            case "DUCK" -> "Quack! Have you tried explaining it to me?";
            case "FROG" -> "Ribbit. I caught a bug for you.";
            case "HEDGEHOG" -> "*snuffles* Careful, sharp edges in that code.";
            case "RACCOON" -> "*rummages* One dev's dead code is my treasure.";
            case "HAMSTER" -> "*stuffs cheeks* I'm caching everything for later.";
            case "PIG" -> "Oink! Rolling around in your legacy code.";
            case "PENGUIN" -> "*waddles* Sliding in to review your kernel patch.";
            case "MOUSE" -> "Squeak! Point and click, point and click.";
            case "TURTLE" -> "Slow and steady passes the tests.";
            case "KOALA" -> "*yawns* Wake me when the build is green.";
            case "SHEEP" -> "Baa! I've been counting your bugs to fall asleep.";
            case "LION" -> "Roar! I'm the king of this codebase.";
            case "OWL" -> "Hoo! Still up debugging? Me too.";
            case "SQUIRREL" -> "*buries a nut* I stashed your snippets for winter.";
            case "LADYBUG" -> "*flutters* Relax, I'm the only bug you'll ever want in your code.";
            case "SEAL" -> "*claps flippers* Arf! Seal of approval on that commit.";
            case "CHICKEN" -> "*pecks at the screen* Bawk! Found a bug, can I eat it?";
            case "AXOLOTL" -> "*wiggles gills* I regenerate, you refactor. Same thing really.";
            case "PARROT" -> "Squawk! Hello world! Hello world! *repeats it until it compiles*";
            case "COCKATOO" -> "*raises crest* SCREECH! Oh, sorry. Is this a meeting?";
            case "BEE" -> "Bzzz! Busy as a bee, pollinating your pull requests.";
            case "CRAB" -> "*snip snip* Let me trim that trailing whitespace for you.";
            case "SNAIL" -> "*slides in* No rush, I left a trail so you can find your way back.";
            case "BAT" -> "*squeak* My echolocation found a bug two files over.";
            case "OCELOT" -> "*pads silently* I spotted a bug... and a few more rosettes.";
            case "OTTER" -> "*juggles a pebble* Holding paws so we never drift from main.";
            default -> "Hello!";
        };
    }
}
