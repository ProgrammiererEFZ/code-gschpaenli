// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/** Original pixel art: penguin. Waddles when walking and slides on its belly when running. */
final class PixelPenguin {
    private PixelPenguin() {}

    private static final String[] STAND_A = {
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            "......OGGGOOGGGO....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBOOBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            "......OGGGOOGGGO....",
    };

    private static final String[] STAND_FLAP = {
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".OOOOOBBBBLLLLO.....",
            "OAAAAABBBBLLLLLO....",
            ".OOOOBBBBBLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            "......OGGGOOGGGO....",
    };

    private static final String[] WALK_1 = {
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            "......OGGGOOGGGO....",
    };

    private static final String[] WALK_2 = {
            ".........OOOO.......",
            "........OBBBBO......",
            ".......OBBBBBBO.....",
            ".......OBBBWYBBOO...",
            ".......OBBTBBLLNNO..",
            ".......OBTTLLLLOO...",
            "......OBBBBLLLLO....",
            "......OBOABLLLLO....",
            ".....OBBOAALLLLLO...",
            ".....OBBOAALLLLLO...",
            ".....OBBBOALLLLLO...",
            ".....OBBBBOLLLLLO...",
            "...OBBBBBBLLLLLO....",
            "....OOOOOOOOOOO.....",
            ".......OGGO.OGO.....",
            "...........OGGGO....",
    };

    private static final String[] WALK_3 = {
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".....OGGGO.OGGGO....",
    };

    private static final String[] WALK_4 = {
            ".......OOOO.........",
            "......OBBBBO........",
            ".....OBBBBBBO.......",
            ".....OBBBWYBBOO.....",
            ".....OBBTBBLLNNO....",
            ".....OBTTLLLLOO.....",
            "....OBBBBLLLLO......",
            "....OBOABLLLLO......",
            "...OBBOAALLLLLO.....",
            "...OBBOAALLLLLO.....",
            "...OBBBOALLLLLO.....",
            "...OBBBBOLLLLLO.....",
            ".OBBBBBBLLLLLO......",
            "..OOOOOOOOOOO.......",
            ".......OGO..OGGO....",
            "......OGGGO.........",
    };

    private static final String[] SLIDE_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            ".......OOOOOOO......",
            "....OOOBBBBBBBOO....",
            "..OOBBBBBBBBBBWYBO..",
            ".OGOBBAAAABBBBTLLNNO",
            ".OGOBBBBBBBLLLLLLOO.",
            "..OOLLLLLLLLLLLLO...",
            "....OOOOOOOOOOOO....",
    };

    private static final String[] SLIDE_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            ".......OOOOOOO......",
            "....OOAAOBBBBBOO....",
            "..OOBAAABBBBBBWYBO..",
            ".OOGBBBBBBBBBBTLLNNO",
            "OGOOBBBBBBBLLLLLLOO.",
            "..OOLLLLLLLLLLLLO...",
            "....OOOOOOOOOOOO....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOGGGOGGGO.....",
    };

    private static final String[] SIT_B = {
            "....................",
            "....................",
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBOOBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".....OBOABLLLLO.....",
            "....OBBOAALLLLLO....",
            "....OBBOAALLLLLO....",
            "....OBBBOALLLLLO....",
            "....OBBBBOLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOGGGOGGGO.....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            ".......OOOOOOO......",
            "....OOOBBBBBBBOO....",
            "..OOBBBBBBBBBBOOBO..",
            "..OOBBAAAABBBBTLLNNO",
            "..OBBBBBBBBLLLLLLOO.",
            "..OOLLLLLLLLLLLLO...",
            "....OOOOOOOOOOOO....",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOOOOO.....",
            "....OOBBBBBBBBBOO...",
            "..OOBBBBBBBBBBOOBO..",
            "..OOBBAAAABBBBTLLNNO",
            "..OBBBBBBBBLLLLLLOO.",
            "..OOLLLLLLLLLLLLO...",
            "....OOOOOOOOOOOO....",
    };

    private static final String[] JUMP_A = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "..OO..OBTTLLLLOO....",
            ".OAAOOBBBBLLLLO.....",
            "..OAAABBBBLLLLO.....",
            "...OOBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".......OGO.OGO......",
            ".......OGGO.OGGO....",
    };

    private static final String[] FALL_A = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWWBBOO....",
            "......OBBTBBLLNNO...",
            "..OO..OBTTLLLLOO....",
            ".OAAOOBBBBLLLLO.....",
            "..OAAABBBBLLLLO.....",
            "...OOBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".......OGO.OGO......",
            ".......OGGO.OGGO....",
    };

    private static final String[] FALL_B = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWWBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".OOOOOBBBBLLLLO.....",
            "OAAAAABBBBLLLLLO....",
            ".OOOOBBBBBLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".......OGO.OGO......",
            ".......OGGOOGGO.....",
    };

    private static final String[] HELD_A = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "..OO..OBTTLLLLOO....",
            ".OAAOOBBBBLLLLO.....",
            "..OAAABBBBLLLLO.....",
            "...OOBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".......OGO.OGO......",
            ".......OGGO.OGGO....",
    };

    private static final String[] HELD_B = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBBBBBBO......",
            "......OBBBWYBBOO....",
            "......OBBTBBLLNNO...",
            "......OBTTLLLLOO....",
            ".....OBBBBLLLLO.....",
            ".OOOOOBBBBLLLLO.....",
            "OAAAAABBBBLLLLLO....",
            ".OOOOBBBBBLLLLLO....",
            "....OBBBBLLLLLLO....",
            "....OBBBBBLLLLLO....",
            "..OBBBBBBLLLLLO.....",
            "...OOOOOOOOOOO......",
            ".......OGO.OGO......",
            ".......OGGOOGGO.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_A, STAND_BLINK, STAND_A, STAND_A, STAND_FLAP, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, SLIDE_A, SLIDE_A, SLIDE_B, SLIDE_B)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_A, SIT_B, SIT_B, SIT_B)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Emperor", new Color(0x434C60), new Color(0xF7F7F2), new Color(0x6B6F7A),
                        new Color(0x434C60), new Color(0x101218), new Color(0x101218), new Color(0xF0923A),
                        new Color(0x5E6A84), new Color(0xFFC94A)),
                new PetKind.Palette("Little blue", new Color(0x4F7DB3), new Color(0xF4F6FA), new Color(0xF2B8C0),
                        new Color(0x4F7DB3), new Color(0x1B2A40), new Color(0x1B2A40), new Color(0x6B6E78),
                        new Color(0x6E98CC), new Color(0xF4F6FA)),
                new PetKind.Palette("Rockhopper", new Color(0x3A3F4A), new Color(0xFAFAF7), new Color(0xF4A6B4),
                        new Color(0x3A3F4A), new Color(0x101114), new Color(0xD8323A), new Color(0xE8603A),
                        new Color(0x555C6B), new Color(0xF7D43A)),
                new PetKind.Palette("Chick", new Color(0x9AA0A8), new Color(0xDDE1E6), new Color(0x50545C),
                        new Color(0x9AA0A8), new Color(0x2A2D33), new Color(0x14161A), new Color(0x50545C),
                        new Color(0x80868F), new Color(0xDDE1E6)));
    }
}
