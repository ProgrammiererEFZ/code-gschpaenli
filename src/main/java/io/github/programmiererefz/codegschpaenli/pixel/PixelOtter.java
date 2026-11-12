// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: otter. Long and low on short legs, with a thick tapering tail, a light face and chest and long
 * whiskers. It lopes along with an arching back, bounds when running, stands up on its hind legs to juggle a pebble
 * and sleeps floating on its back, holding the pebble on its chest. Palette slots: E is the pebble, A the darker
 * back, T the whiskers.
 */
final class PixelOtter {
    private PixelOtter() {}

    private static final String[] STAND = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBYWBBOT",
            "...OOBBBBBBBBBBBBBYYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            "....OGGO......OGGO......",
    };

    private static final String[] STAND_BLINK = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBBBBBOT",
            "...OOBBBBBBBBBBBBBOOBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            "....OGGO......OGGO......",
    };

    private static final String[] STAND_SNIFF = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBYWBBN.",
            "...OOBBBBBBBBBBBBBYYBBOT",
            ".OOBBBBBBBBBBBBBBLLLLLO.",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            "....OGGO......OGGO......",
    };

    private static final String[] WALK_1 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBYWBBOT",
            "...OOBBBBBBBBBBBBBYYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            "....OGGO......OGGO......",
    };

    private static final String[] WALK_2 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "........OOOO..OBOOOOO...",
            ".......OAAAAOOBBBBBBBO..",
            ".....OOABBBBABBBBBYWBBOT",
            "...OOBBBBBBBBBBBBBYYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOOGGOOOOOOGGOOOO....",
            ".....OGGO....OGGO.......",
    };

    private static final String[] WALK_3 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBYWBBOT",
            "...OOBBBBBBBBBBBBBYYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOGGOOOOOOOOOOGGOO....",
            "...OGGO.........OGGO....",
    };

    private static final String[] WALK_4 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "........OOOO..OBOOOOO...",
            ".......OAAAAOOBBBBBBBO..",
            ".....OOABBBBABBBBBYWBBOT",
            "...OOBBBBBBBBBBBBBYYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            ".....OGGO....OGGO.......",
    };

    private static final String[] RUN_1 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "................OO......",
            "...............OBOOOOO..",
            "...OOOOOOOOOOOOBBBBBBBO.",
            ".OOAAAAAAAAAAABBBBBYWBBO",
            "OBBBBBBBBBBBBBBBBBBYYBNO",
            ".OOOBBBBBBBBBBBBBLLLLLO.",
            "...OGGOOOOOOOOOOOOGGO...",
            "..OGGO............OGGO..",
            ".OGO................OGO.",
    };

    private static final String[] RUN_2 = {
            "........................",
            "........................",
            "........................",
            "........................",
            "................OO......",
            "...............OBOOOOO..",
            "...OOOOOOOOOOOOBBBBBBBO.",
            ".OOAAAAAAAAAAABBBBBYWBBO",
            "OBBBBBBBBBBBBBBBBBBYYBNO",
            ".OOOBBBBBBBBBBBBBLLLLLO.",
            "...OGGOOOOOOOOOOOOGGO...",
            "..OGGO............OGGO..",
            ".OGO................OGO.",
            "........................",
    };

    private static final String[] RUN_3 = {
            "........................",
            "........................",
            "........................",
            "........................",
            ".................OO.....",
            "........OOOOO...OBOOOO..",
            "......OOAAAAAOOOBBBBBBO.",
            ".....OABBBBBBAABBBBBYWBO",
            "....OBBBBBBBBBBBBBBBYYNO",
            "...OBBBBBBBBBBBBBBLLLLLO",
            "OOOBBBBBBBBBBBBBBLLLLO..",
            "OBBBBOOBBBBBBBBLLLLOO...",
            ".OOOOOOGGOOOGGOOOO......",
            "......OGGO.OGGO.........",
    };

    private static final String[] RUN_4 = {
            "........................",
            "........................",
            "........................",
            "........................",
            ".................OO.....",
            "........OOOOO...OBOOOO..",
            "......OOAAAAAOOOBBBBBBO.",
            ".....OABBBBBBAABBBBBYWBO",
            "....OBBBBBBBBBBBBBBBYYNO",
            "...OBBBBBBBBBBBBBBLLLLLO",
            "OOOBBBBBBBBBBBBBBLLLLO..",
            "OBBBBOOBBBBBBBBLLLLOO...",
            ".OOOOOOOGGOGGOOOOO......",
            ".......OGGOGGO..........",
    };

    private static final String[] SIT = {
            "............OO..........",
            "...........OBOOOOO......",
            "..........OBBBBBBBO.....",
            "..........OBBBBYWBBO....",
            "..........OBBBBYYBNOT...",
            "...........OBBLLLLLO.T..",
            "..........OBBBLLLLO.....",
            "..........OBBGEEGLO.....",
            ".........OBBBLLLLLO.....",
            ".........OBBBLLLLLO.....",
            "........OBBBBLLLLLO.....",
            ".OOOO...OBBBBLLLLLO.....",
            "OBBBBOOOBBBBBLLLLLO.....",
            ".OOOOOOOOOGGOOOGGOO.....",
    };

    private static final String[] SIT_BLINK = {
            "............OO..........",
            "...........OBOOOOO......",
            "..........OBBBBBBBO.....",
            "..........OBBBBBBBBO....",
            "..........OBBBBOOBNOT...",
            "...........OBBLLLLLO.T..",
            "..........OBBBLLLLO.....",
            "..........OBBGEEGLO.....",
            ".........OBBBLLLLLO.....",
            ".........OBBBLLLLLO.....",
            "........OBBBBLLLLLO.....",
            ".OOOO...OBBBBLLLLLO.....",
            "OBBBBOOOBBBBBLLLLLO.....",
            ".OOOOOOOOOGGOOOGGOO.....",
    };

    private static final String[] SIT_TOSS = {
            "............OO......OO..",
            "...........OBOOOOO.OEEO.",
            "..........OBBBBBBBO.OO..",
            "..........OBBBBYWBBO....",
            "..........OBBBBYYBNOT...",
            "...........OBBLLLLLO.T..",
            "..........OBBBLLLLO.....",
            "..........OBBGLLGLO.....",
            ".........OBBBLLLLLO.....",
            ".........OBBBLLLLLO.....",
            "........OBBBBLLLLLO.....",
            ".OOOO...OBBBBLLLLLO.....",
            "OBBBBOOOBBBBBLLLLLO.....",
            ".OOOOOOOOOGGOOOGGOO.....",
    };

    private static final String[] SLEEP_A = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...........OO...........",
            ".OO.OO....OEEO...OO.....",
            "OGGOGGO..OGEEGO..OBOOO..",
            ".OGOGOOOOOOOOOOOOBBBBBO.",
            "..OLLLLLLLLLLLLLLBBOOBNO",
            "OOBBBBBBBBBBBBBBBBBBLLOT",
            ".OOOOOOOOOOOOOOOOOOOOO..",
    };

    private static final String[] SLEEP_B = {
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "........................",
            "...........OO...........",
            "..........OEEO..........",
            ".OO.OO...OGEEGO..OO.....",
            "OGGOGGO...OGGO...OBOOO..",
            ".OGOGOOOOOOOOOOOOBBBBBO.",
            "..OLLLLLLLLLLLLLLBBOOBNO",
            "OOBBBBBBBBBBBBBBBBBBLLOT",
            ".OOOOOOOOOOOOOOOOOOOOO..",
    };

    private static final String[] JUMP = {
            "..................OO....",
            ".................OBOOOO.",
            "................OBBBBBBO",
            "...............OBBBBYWBO",
            "..............OBBBBBYYNO",
            "............OOBBBBBLLLLO",
            "..........OOAABBBBLLLLO.",
            "........OOAABBBBBBLLLO..",
            "......OOABBBBBBBBLLLO...",
            "....OOBBBBBBBBBBLLLO....",
            "OOOBBBBBBBBBBOOOGGO.....",
            ".OOOOBBBBBOOGGOOO.......",
            "....OGGOOOO.............",
            "...OGGO.................",
    };

    private static final String[] FALL_A = {
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBWWBBOT",
            "...OOBBBBBBBBBBBBBWYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOGGOOOOOOOOOOGGOO....",
            "...OGO..........OGO.....",
            "..OGO............OGO....",
    };

    private static final String[] FALL_B = {
            "........................",
            "........................",
            "........................",
            "........................",
            "...............OO.......",
            "..............OBOOOOO...",
            ".......OOOOOOOBBBBBBBO..",
            ".....OOAAAAAABBBBBWWBBOT",
            "...OOBBBBBBBBBBBBBWYBNO.",
            ".OOBBBBBBBBBBBBBBLLLLLOT",
            "OBBBBBBBBBBBBBBBLLLLLO..",
            "OOOOOGGOOOOOOOOGGOOO....",
            "....OGO........OGO......",
            ".....OGO......OGO.......",
    };

    private static final String[] HELD_A = {
            ".........OO.............",
            "........OBOOOOO.........",
            ".......OBBBBBBBO........",
            ".......OBBBBYWBBO.......",
            ".......OBBBBYYBNO.T.....",
            "........OBBLLLLLO..T....",
            "......OOOBBLLLLOOO......",
            ".....OGGOBBLLLLOGGO.....",
            "......OOOBBLLLLOOO......",
            "........OBBBLLLO........",
            "........OBBBLLLO........",
            ".......OOOBBBLOOO.......",
            "......OGGOOBBOOGGO......",
            ".......OO..OBO..OO......",
    };

    private static final String[] HELD_B = {
            ".........OO.............",
            "........OBOOOOO.........",
            ".......OBBBBBBBO........",
            ".......OBBBBYWBBO.......",
            ".......OBBBBYYBNO.T.....",
            "........OBBLLLLLO..T....",
            "......OOOBBLLLLOOO......",
            ".....OGGOBBLLLLOGGO.....",
            "......OOOBBLLLLOOO......",
            "........OBBBLLLO........",
            "........OBBBLLLO........",
            ".......OOOBBBLOOO.......",
            "......OOGGOBBOGGOO......",
            "........OO.OBO.OO.......",
    };

    static final PixelSheet SHEET = PixelSheet.builder(24, 14)
            .anim(Pose.STAND, 2.5, STAND, STAND, STAND_SNIFF, STAND, STAND, STAND_BLINK, STAND, STAND_SNIFF,
                    STAND_SNIFF, STAND)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 2.5, SIT, SIT, SIT_TOSS, SIT, SIT_TOSS, SIT, SIT, SIT_BLINK, SIT)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                // name, body, face/chest, paws, pebble, outline, eye, nose, back, whiskers
                new PetKind.Palette("River", new Color(0x8A5A3B), new Color(0xE6CDAA), new Color(0x5E3B26),
                        new Color(0x9AA5B0), new Color(0x2E1C10), new Color(0x120C08), new Color(0x1A100A),
                        new Color(0x74492E), new Color(0xF2E8DA)),
                new PetKind.Palette("Sea otter", new Color(0x4E3E33), new Color(0xD9C9B0), new Color(0x33271F),
                        new Color(0x9AA5B0), new Color(0x17100C), new Color(0x0A0705), new Color(0x0A0705),
                        new Color(0x3F3129), new Color(0xF2EADC)),
                new PetKind.Palette("Golden", new Color(0xC8914E), new Color(0xF7E6C4), new Color(0x8E5E2C),
                        new Color(0x9AA5B0), new Color(0x4A2C12), new Color(0x1E140C), new Color(0x2A1A0E),
                        new Color(0xAE7A3E), new Color(0xFFF6E6)),
                new PetKind.Palette("Grey", new Color(0x8C8884), new Color(0xE8E4DE), new Color(0x5C5956),
                        new Color(0xB4A48C), new Color(0x2A2928), new Color(0x121212), new Color(0x1A1A1A),
                        new Color(0x77736F), new Color(0xFFFFFF)));
    }
}
