// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/** Original pixel art: turtle. */
final class PixelTurtle {
    private PixelTurtle() {}

    private static final String[] STAND_A = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGYGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGOGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] STAND_LOOK = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO...OOOO.",
            ".OTTBBBABBBBO.OGGGGO",
            ".OTBAAAAAAABO.OEGYGO",
            "OTBBABBBABBBBOOGGGNO",
            "OBBBABBBABBBBOOGGGO.",
            "OABBABBABBABOGGGGO..",
            "OLLLLLLLLLLLOGGGGO..",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] WALK_1 = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGYGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] WALK_2 = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBOOOOOO.",
            "OBBBABBBABBBBOGGGGGO",
            "OABBABBABBABOGEGYGGO",
            "OLLLLLLLLLLLOGGGGNO.",
            ".OOGGGOOOOOOGGGOOO..",
            ".OGGGO.....OGGGGO...",
            ".OOOOO......OOOO....",
    };

    private static final String[] WALK_3 = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGYGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] WALK_4 = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBOOOOOO.",
            "OBBBABBBABBBBOGGGGGO",
            "OABBABBABBABOGEGYGGO",
            "OLLLLLLLLLLLOGGGGNO.",
            ".OOOGGGOOOGGGOOOOO..",
            "...OGGGGO.OGGGO.....",
            "....OOOO..OOOOO.....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBOOOO...",
            "OBBBABBBABBBBOEGYO..",
            "OABBABBABBABOGGGNO..",
            "OLLLLLLLLLLLOOOOO...",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBOOOO...",
            "OBBBABBBABBBBOEGOO..",
            "OABBABBABBABOGGGNO..",
            "OLLLLLLLLLLLOOOOO...",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBO......",
            "OBBBABBBABBBBO......",
            "OABBABBABBABBO......",
            "OLLLLLLLLLLLLO......",
            ".OOOOOOOOOOOO.......",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO.......",
            "OTBBABBBABBBBO......",
            "OBBBABBBABBBBO......",
            "OABBABBABBABBOO.....",
            "OLLLLLLLLLLLLOGO....",
            ".OOOOOOOOOOOOOO.....",
    };

    private static final String[] JUMP_A = {
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGYGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            ".OGGGO.....OGGGO....",
            "OGGGO.......OGGGO...",
            "OOOO.........OOOO...",
    };

    private static final String[] FALL_A = {
            "....................",
            "....................",
            "....................",
            "...OGO......OGO.....",
            "..OGGO.....OGGO.....",
            "..OGGOOOOOOOGGOOOO..",
            ".OLLLLLLLLLLLLGGGGO.",
            "OABBABBABBABBBOGGGGO",
            "OBBBABBBABBBBOOGGWGO",
            "OTBBABBBABBBBO.OOOO.",
            ".OTBAAAAAAABO.......",
            ".OTTBBBABBBBO.......",
            "..OOTTBABBOO........",
            "....OOOOOO..........",
    };

    private static final String[] FALL_B = {
            "....................",
            "....................",
            "....................",
            "..OGO........OGO....",
            "...OGO.....OGGO.....",
            "..OGGOOOOOOOGGOOOO..",
            ".OLLLLLLLLLLLLGGGGO.",
            "OABBABBABBABBBOGGGGO",
            "OBBBABBBABBBBOOGGWGO",
            "OTBBABBBABBBBO.OOOO.",
            ".OTBAAAAAAABO.......",
            ".OTTBBBABBBBO.......",
            "..OOTTBABBOO........",
            "....OOOOOO..........",
    };

    private static final String[] HELD_A = {
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGYGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "..OGGGO...OGGGO.....",
            "..OGGGO...OGGGO.....",
            "..OOOOO...OOOOO.....",
    };

    private static final String[] HELD_B = {
            "....................",
            "....................",
            "....OOOOOO..........",
            "..OOTTBABBOO........",
            ".OTTBBBABBBBO.......",
            ".OTBAAAAAAABO..OOOO.",
            "OTBBABBBABBBBOOGGGGO",
            "OBBBABBBABBBBOOEGOGO",
            "OABBABBABBABOGGGGGNO",
            "OLLLLLLLLLLLOGGGGOO.",
            ".OOGGGOOOOOGGGOOOO..",
            "...OGGGO.OGGGO......",
            "...OGGGO.OGGGO......",
            "...OOOOO.OOOOO......",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_LOOK, STAND_LOOK, STAND_A, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.SIT, 1.0, SIT_A, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 0.8, SLEEP_A, SLEEP_A, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Green", new Color(0x6E9A44), new Color(0xE8D58A), new Color(0x9CC07A),
                        new Color(0x9CC07A), new Color(0x23331A), new Color(0x111111), new Color(0x23331A),
                        new Color(0x4C7330), new Color(0x9BC46A)),
                new PetKind.Palette("Box", new Color(0x7A5230), new Color(0xE8C77A), new Color(0x8C9A6A),
                        new Color(0x8C9A6A), new Color(0x2A1C10), new Color(0x111111), new Color(0x2A1C10),
                        new Color(0xE0B54A), new Color(0xA27346)),
                new PetKind.Palette("Sea", new Color(0x3F8C8C), new Color(0xF0E2B8), new Color(0x7FB5A8),
                        new Color(0x7FB5A8), new Color(0x163234), new Color(0x111111), new Color(0x163234),
                        new Color(0x2C6466), new Color(0x7FC6C0)),
                new PetKind.Palette("Red-eared", new Color(0x4F6E3A), new Color(0xE6D27A), new Color(0x7E9A56),
                        new Color(0xD8352A), new Color(0x1C2A14), new Color(0x111111), new Color(0x1C2A14),
                        new Color(0xA6B85A), new Color(0x6E8E4E)));
    }
}
