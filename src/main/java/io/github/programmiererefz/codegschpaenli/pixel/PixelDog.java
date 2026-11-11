// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;
import java.awt.Color;
import java.util.List;

/** Original pixel art: dog. */
final class PixelDog {
    private PixelDog() {}

    private static final String[] STAND_1 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".O.......OEEBBBBBO..",
            "OTO.....OEEEOBYWBOO.",
            "OBO.....OEEEOBYYLLLN",
            ".OBO....OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OLLOGGO.OGGOLLO...",
            "..OOOOOOO.OOOOOOO..."
    };

    private static final String[] STAND_2 = {
            "....................",
            "...........OOOOO....",
            "..O.......OBBABBO...",
            ".OTO.....OEEBBBBBO..",
            ".OBO....OEEEOBYWBOO.",
            ".OBO....OEEEOBYYLLLN",
            "..OBO...OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OLLOGGO.OGGOLLO...",
            "..OOOOOOO.OOOOOOO..."
    };

    private static final String[] STAND_3 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".O.......OEEBBBBBO..",
            "OTO.....OEEEOBBBBOO.",
            "OBO.....OEEEOBOOLLLN",
            ".OBO....OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OLLOGGO.OGGOLLO...",
            "..OOOOOOO.OOOOOOO..."
    };

    private static final String[] WALK_1 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".O.......OEEBBBBBO..",
            "OTO.....OEEEOBYWBOO.",
            "OBO.....OEEEOBYYLLLN",
            ".OBO....OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            ".OBBO.OGGOGGO.OBBO..",
            "OLLO..OGGOGGO..OLLO.",
            "OOOO..OOOOOOO..OOOO."
    };

    private static final String[] WALK_2 = {
            "...........OOOOO....",
            "..O.......OBBABBO...",
            ".OTO.....OEEBBBBBO..",
            ".OBO....OEEEOBYWBOO.",
            ".OBO....OEEEOBYYLLLN",
            "..OBO...OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OLLOGGO.OGGOBBO...",
            "..OOOOGGO.OGGOLLO...",
            ".....OOOO.OOOOOOO..."
    };

    private static final String[] WALK_3 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".O.......OEEBBBBBO..",
            "OTO.....OEEEOBYWBOO.",
            "OBO.....OEEEOBYYLLLN",
            ".OBO....OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OGGOBBOOOBBOGGO...",
            ".OGGO.OBBOBBO.OGGO..",
            "OGGO..OLLOLLO..OGGO.",
            "OOOO..OOOOOOO..OOOO."
    };

    private static final String[] WALK_4 = {
            "...........OOOOO....",
            "..O.......OBBABBO...",
            ".OTO.....OEEBBBBBO..",
            ".OBO....OEEEOBYWBOO.",
            ".OBO....OEEEOBYYLLLN",
            "..OBO...OEEEOBBLLLLO",
            "..OBOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            "..OBAABBBBBOBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBOGGOOOGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OBBOGGO.OGGOLLO...",
            "..OLLOGGO.OGGOOOO...",
            "..OOOOOOO.OOOO......"
    };

    private static final String[] RUN_1 = {
            "...........OOOOO....",
            "......OOO.OBBABBO...",
            ".....OEEEOOBBBBBBO..",
            "......OOEEEBBBYWBOO.",
            "........OBBBBBYYLLLN",
            ".OOO....OBBBBBBLLLLO",
            "OTBBOOOOOBBBBBBBOPO.",
            ".OOBBAABBBBBBBBLOO..",
            "..OBAABBBBBBBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBGGOOOOOOGGBBO..",
            ".OBBGGO......OGGBBO.",
            "OLLGGO........OGGLLO",
            "OOOOOO........OOOOOO",
            "...................."
    };

    private static final String[] RUN_2 = {
            "....................",
            "...........OOOOO....",
            "......OOO.OBBABBO...",
            ".....OEEEOOBBBBBBO..",
            "......OOEEEBBBYWBOO.",
            "........OBBBBBYYLLLN",
            ".OOO....OBBBBBBLLLLO",
            "OTBBOOOOOBBBBBBBOPO.",
            ".OOBBAABBBBBBBBLOO..",
            "..OBAABBBBBBBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBGGOOOOOGGBBO...",
            ".OBBGGO....OGGBBO...",
            "OLLGGO.....OGGLLO...",
            "OOOOOO.....OOOOOO..."
    };

    private static final String[] RUN_3 = {
            "....................",
            "...........OOOOO....",
            "......OOO.OBBABBO...",
            ".....OEEEOOBBBBBBO..",
            "......OOEEEBBBYWBOO.",
            "........OBBBBBYYLLLN",
            ".OOO....OBBBBBBLLLLO",
            "OTBBOOOOOBBBBBBBOPO.",
            ".OOBBAABBBBBBBBLOO..",
            "..OBAABBBBBBBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OOBBGGOOGGBBOOO...",
            "....OBBGOOGBBO......",
            ".....OLLOOLLO.......",
            ".....OOOOOOOO......."
    };

    private static final String[] RUN_4 = {
            "....................",
            "...........OOOOO....",
            "......OOO.OBBABBO...",
            ".....OEEEOOBBBBBBO..",
            "......OOEEEBBBYWBOO.",
            "........OBBBBBYYLLLN",
            ".OOO....OBBBBBBLLLLO",
            "OTBBOOOOOBBBBBBBOPO.",
            ".OOBBAABBBBBBBBLOO..",
            "..OBAABBBBBBBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "...OBBGGOOOOOGGBBO..",
            "...OBBGGO....OGGLLO.",
            "...OLLGGO....OOOOOO.",
            "...OOOOOO..........."
    };

    private static final String[] SIT_1 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".........OEEBBBBBO..",
            "........OEEEOBYWBOO.",
            "........OEEEOBYYLLLN",
            "........OEEEOBBLLLLO",
            "........OOEEOBBBOOO.",
            "........OBOEOBBLO...",
            ".......OBBBOBBBLO...",
            "......OBAABBBBLLO...",
            "......OBABBBBBLLO...",
            ".....OBBBBBOGGBBO...",
            "..OOOBBBBBBOGGBBO...",
            "..OTBBBBLLLOGGLLO...",
            "..OOOOOOOOOOOOOOO..."
    };

    private static final String[] SIT_2 = {
            "....................",
            "...........OOOOO....",
            "..........OBBABBO...",
            ".........OEEBBBBBO..",
            "........OEEEOBYWBOO.",
            "........OEEEOBYYLLLN",
            "........OEEEOBBLLLLO",
            "........OOEEOBBBOOO.",
            "........OBOEOBBLO...",
            ".......OBBBOBBBLO...",
            "......OBAABBBBLLO...",
            "......OBABBBBBLLO...",
            "..OO.OBBBBBOGGBBO...",
            "..OTOBBBBBBOGGBBO...",
            "..OBBBBBLLLOGGLLO...",
            "..OOOOOOOOOOOOOOO..."
    };

    private static final String[] SLEEP_1 = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOOOO....",
            "..........OBBBBBO...",
            "...OOOOOOOEEBBBBBO..",
            "..OBBBBBOEEEOBBBBOO.",
            ".OBBAABBOEEEOBOOLLLN",
            ".OBAABBBOEEEOBBLLLLO",
            ".OBBBBABBOEEOBBBOOO.",
            ".OTTOBBBBBOOOLLLLLLO",
            ".OOOOOOOOOOOOOOOOOO."
    };

    private static final String[] SLEEP_2 = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOOOO....",
            "....OOOOO.OBBBBBO...",
            "...OBBBBBOEEBBBBBO..",
            "..OBBBBBOEEEOBBBBOO.",
            ".OBBAABBOEEEOBOOLLLN",
            ".OBAABBBOEEEOBBLLLLO",
            ".OBBBBABBOEEOBBBOOO.",
            ".OTTOBBBBBOOOLLLLLLO",
            ".OOOOOOOOOOOOOOOOOO."
    };

    private static final String[] JUMP_1 = {
            "....................",
            "...........OOOOO....",
            "..O...OOO.OBBABBO...",
            ".OTO.OEEEOOBBBBBBO..",
            ".OBO..OOEEEBBBYWBOO.",
            ".OBO....OBBBBBYYLLLN",
            "..OBO...OBBBBBBLLLLO",
            "..OBOOOOOBBBBBBBOPO.",
            "..OBBAABBBBBBBBLOO..",
            "..OBAABBBBBBBBLLO...",
            "..OBBBBBBAABBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            "..OBBGGOOOOOGGBBO...",
            ".OBBGGO....OGGLLO...",
            "OLLGGO.....OOOOOO...",
            "OOOOOO.............."
    };

    private static final String[] FALL_1 = {
            "......OOOO..........",
            ".....OEEEEOOOOOO....",
            "......OEEEOBBBBBO...",
            ".O.....OEEBBBBBBBO..",
            "OTO.....OOBBBBWWBOO.",
            "OBO.....OBBBBBWYLLLN",
            ".OBO....OBBBBBBLLLLO",
            "..OBOOOOOBBBBBBBOOO.",
            "..OBBBBBBBBBBBBLO...",
            "..OBBBBBBBBBBBLLO...",
            "..OBBBBBBBBBBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            ".OBBOGGOOOOOGGOBBO..",
            "OBBOOGGO...OGGOOBBO.",
            "OLLO.OOO...OOO.OLLO.",
            "OOOO...........OOOO."
    };

    private static final String[] FALL_2 = {
            "......OOOO..........",
            ".....OEEEEOOOOOO....",
            ".O....OEEEOBBBBBO...",
            ".......OEEBBBBBBBO..",
            ".OTO....OOBBBBWWBOO.",
            ".OBO....OBBBBBWYLLLN",
            ".OBO....OBBBBBBLLLLO",
            "..OBOOOOOBBBBBBBOOO.",
            "..OBBBBBBBBBBBBLO...",
            "..OBBBBBBBBBBBLLO...",
            "..OBBBBBBBBBBLLLO...",
            "..OBBBBBBLLLLLLLO...",
            ".OBBOGGOOOOOGGOBBO..",
            "OLLOOGGO...OGGOOLLO.",
            "OOOO.OOO...OOO.OOOO.",
            "...................."
    };

    private static final String[] HELD_1 = {
            "...........OOOOO....",
            "..........OBBABBO...",
            ".........OEEBBBBBO..",
            "........OEEEOBWWBOO.",
            "........OEEEOBWYLLLN",
            "........OEEEOBBLLLLO",
            "...OOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            ".OBBAABBBBBOBBLLO...",
            "OBOBBBBBBLLLLLLLO...",
            "OTOBBOGGOOOGGOBBO...",
            "OOOBBOGGO.OGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            ".OBBO.OGGOGGO.OBBO..",
            "OLLO..OOOOOOO.OLLO..",
            "OOOO..........OOOO.."
    };

    private static final String[] HELD_2 = {
            "...........OOOOO....",
            "..........OBBABBO...",
            ".........OEEBBBBBO..",
            "........OEEEOBWWBOO.",
            "........OEEEOBWYLLLN",
            "........OEEEOBBLLLLO",
            "...OOOOOOOEEOBBBOOO.",
            "..OBBAABBBOEOBBLO...",
            ".OBBAABBBBBOBBLLO...",
            ".OBBBBBBBLLLLLLLO...",
            ".OTBBOGGOOOGGOBBO...",
            ".OOBBOGGO.OGGOBBO...",
            "..OBBOGGO.OGGOBBO...",
            "..OBBOGGO.OGGOOBBO..",
            "..OLLOOOO.OOOOOLLO..",
            "..OOOO........OOOO.."
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND_1, STAND_2, STAND_1, STAND_2, STAND_1, STAND_3)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 2, SIT_1, SIT_2)
            .anim(Pose.SLEEP, 1.2, SLEEP_1, SLEEP_2)
            .anim(Pose.JUMP, 1, JUMP_1)
            .anim(Pose.FALL, 6, FALL_1, FALL_2)
            .anim(Pose.HELD, 4, HELD_1, HELD_2)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Brown", new Color(0xB5773F), new Color(0xF2DDBD), new Color(0x93602F), new Color(0x6B3E1C), new Color(0x3B2210), new Color(0x22140A), new Color(0x22140A), new Color(0xA0662F), new Color(0xF2DDBD)),
                new PetKind.Palette("Golden", new Color(0xE9B866), new Color(0xFFF0D2), new Color(0xCF9A49), new Color(0xC48537), new Color(0x6E4716), new Color(0x2B1B0D), new Color(0x3A2412), new Color(0xDBA752), new Color(0xFFF6E3)),
                new PetKind.Palette("Black & tan", new Color(0x463C3E), new Color(0xC8894A), new Color(0x2E2729), new Color(0x262022), new Color(0x0F0C0D), new Color(0xB07A3A), new Color(0x111111), new Color(0x2A2426), new Color(0xC8894A)),
                new PetKind.Palette("Spotty", new Color(0xF4F2EE), new Color(0xFFFFFF), new Color(0xD9D5CE), new Color(0x34302F), new Color(0x5C5754), new Color(0x1E1A1A), new Color(0x1E1A1A), new Color(0x34302F), new Color(0xFFFFFF))
        );
    }
}
