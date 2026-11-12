// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: owl. Blinks, glances back and cocks its head when idle, waddles in little hops, flaps its
 * wings when running, jumping or falling and sleeps perched, fluffed up with its head sunk in.
 */
final class PixelOwl {
    private PixelOwl() {}

    private static final String[] STAND = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLOOLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
    };

    private static final String[] STAND_BACK = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLKYLBLKYLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
    };

    private static final String[] STAND_TILT = {
            ".....OO.............",
            ".....OBO............",
            "....OBBB......OO....",
            "....OBBBOOOOOOBO....",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBBBBO...",
            "....OBBBLYKLBLLLLO..",
            "....OBBBLYYLNLYKLO..",
            "....OBBBBLLLNLYYLO..",
            "....OBBBBBLLLNLLLO..",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
    };

    private static final String[] STAND_TILT_BLINK = {
            ".....OO.............",
            ".....OBO............",
            "....OBBB......OO....",
            "....OBBBOOOOOOBO....",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLLLLO..",
            "....OBBBBLLLNLOOLO..",
            "....OBBBBBLLLNLLLO..",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
    };

    private static final String[] WALK_1 = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO..OGGO...OGGO...",
    };

    private static final String[] WALK_2 = {
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO....OGGOOGGO....",
            "....................",
    };

    private static final String[] WALK_3 = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO....OGGOOGGO....",
    };

    private static final String[] WALK_4 = {
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            "..OO...OGGO.OGGO....",
            "....................",
    };

    private static final String[] RUN_1 = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBOOO.",
            "....OBBBLLLLBLLLLOOO",
            "OOO.OBBBLYKLBLYKLO..",
            "OEEOOBBBLYYLNLYYLO..",
            "OAEEEOOBBLLLNNLLLO..",
            ".OAEEEEOBBLLLLLLO...",
            "..OOOEEBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    private static final String[] RUN_2 = {
            "OO...OO.......OOEEO.",
            "OEO..OBO.....OBOOEO.",
            "OEEOOBBBOOOOOBBBOO..",
            "OEEEOBBBBBBBBBBBO...",
            ".OEEEOBBLLLLBLLLLO..",
            "..OEEEOBLYKLBLYKLO..",
            "..OAEEEOLYYLNLYYLO..",
            "...OAEEOBLLLNNLLLO..",
            "...OOEEOBBLLLLLLO...",
            "...OOEBBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
            ".......OGGO.OGGO....",
    };

    private static final String[] RUN_3 = {
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBOOO.",
            "....OBBBLLLLBLLLLOOO",
            "OOO.OBBBLYKLBLYKLO..",
            "OEEOOBBBLYYLNLYYLO..",
            "OAEEEOOBBLLLNNLLLO..",
            ".OAEEEEOBBLLLLLLO...",
            "..OOOEEBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
            ".......OGGO.OGGO....",
    };

    private static final String[] RUN_4 = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "...OOBBBBBLLLLLLO...",
            ".OOEEBBBBBLLALLLO...",
            "OEEEEOBBBBLALLALO...",
            "OAEEOBBBBBLLLALLO...",
            "OAOTTBBBBBLALLLO....",
            "OOOTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLYKLBLYKLO..",
            "....OBBBLYYLNLYYLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OOOOOOGGOOOGGO....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLOOLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OOOOOOGGOOOGGO....",
    };

    private static final String[] SIT_HALF = {
            "....................",
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLBLOOLO..",
            "....OBBBLYKLNLYKLO..",
            "....OBBBBLLLNNLLLO..",
            "....OBBBBBLLLLLLO...",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OOOOOOGGOOOGGO....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLOOLO..",
            "....OBBBBLLLNNLLLO..",
            "...OEEBBBBLLALLLO...",
            "...OEEEBBBLALLALO...",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OOOOOOGGOOOGGO....",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLOOLO..",
            "....OBBBBLLLNNLLLO..",
            "..OOEEBBBBLLALLLOO..",
            ".OTOEEEBBBLALLALLO..",
            "..OTEEEBBBLLLALLO...",
            "..OTTEEEBBLALLLO....",
            "..OOOOOOGGOOOGGO....",
    };

    private static final String[] JUMP_A = {
            "OO...OO.......OOEEO.",
            "OEO..OBO.....OBOOEO.",
            "OEEOOBBBOOOOOBBBOO..",
            "OEEEOBBBBBBBBBBBO...",
            ".OEEEOBBLLLLBLLLLO..",
            "..OEEEOBLYKLBLYKLO..",
            "..OAEEEOLYYLNLYYLO..",
            "...OAEEOBLLLNNLLLO..",
            "...OOEEOBBLLLLLLO...",
            "...OOEBBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
            ".......OGGO.OGGO....",
    };

    private static final String[] FALL_A = {
            "................OO..",
            "OO...OO.......OOEEO.",
            "OEO..OBO.....OBOOEO.",
            "OEEOOBBBOOOOOBBBOO..",
            "OEEEOBBBBBBBBBBBO...",
            ".OEEEOBBLLLLBLLLLO..",
            "..OEEEOBLWKLBLWKLO..",
            "..OAEEEOLWWLNLWWLO..",
            "...OAEEOBLLLNNLLLO..",
            "...OOEEOBBLLLLLLO...",
            "...OOEBBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    private static final String[] FALL_B = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLWKLBLWKLO..",
            "....OBBBLWWLNLWWLO..",
            "....OBBBBLLLNNLLLO..",
            "...OOBBBBBLLLLLLO...",
            ".OOEEBBBBBLLALLLO...",
            "OEEEEOBBBBLALLALO...",
            "OAEEOBBBBBLLLALLO...",
            "OAOTTBBBBBLALLLO....",
            "OOOTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    private static final String[] HELD_A = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBOOO.",
            "....OBBBLLLLBLLLLOOO",
            "OOO.OBBBLYKLBLYKLO..",
            "OEEOOBBBLYYLNLYYLO..",
            "OAEEEOOBBLLLNNLLLO..",
            ".OAEEEEOBBLLLLLLO...",
            "..OOOEEBBBLLALLLO...",
            "...OBBBBBBLALLALO...",
            "..OTBBBBBBLLLALLO...",
            "..OTTBBBBBLALLLO....",
            "..OTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    private static final String[] HELD_B = {
            "....................",
            ".....OO.......OO....",
            ".....OBO.....OBO....",
            "....OBBBOOOOOBBBO...",
            "....OBBBBBBBBBBBO...",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLLLLBLLLLO..",
            "....OBBBLOOLNLOOLO..",
            "....OBBBBLLLNNLLLO..",
            "...OOBBBBBLLLLLLO...",
            ".OOEEBBBBBLLALLLO...",
            "OEEEEOBBBBLALLALO...",
            "OAEEOBBBBBLLLALLO...",
            "OAOTTBBBBBLALLLO....",
            "OOOTOOOOOOOOOOO.....",
            ".......OGO..OGO.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND, STAND, STAND, STAND_BLINK, STAND, STAND, STAND_BACK, STAND_BACK, STAND,
                    STAND_TILT, STAND_TILT, STAND_TILT_BLINK, STAND_TILT, STAND, STAND, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_A, SIT_BLINK, SIT_A, SIT_HALF, SIT_HALF, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Tawny", new Color(0xA86E3C), new Color(0xF2DEBB), new Color(0xD9A441),
                        new Color(0x7A4A24), new Color(0x2E1A0C), new Color(0xF2B233), new Color(0xE0922E),
                        new Color(0x8A5A30), new Color(0x8E5A2E)),
                new PetKind.Palette("Snowy", new Color(0xF2F2EE), new Color(0xFFFFFF), new Color(0xC9C9C2),
                        new Color(0xDADBDF), new Color(0x6A6C74), new Color(0xF2C21A), new Color(0x3A3A40),
                        new Color(0x55585F), new Color(0xDADBDF)),
                new PetKind.Palette("Grey", new Color(0x8E8A84), new Color(0xE6E0D6), new Color(0xC8B89A),
                        new Color(0x6A655F), new Color(0x262321), new Color(0xF2A623), new Color(0x3A3530),
                        new Color(0x55504A), new Color(0x6A655F)),
                new PetKind.Palette("Midnight", new Color(0x4C5274), new Color(0xBCC1DE), new Color(0xE0B04A),
                        new Color(0x363B58), new Color(0x13141E), new Color(0xF2D23A), new Color(0xE0922E),
                        new Color(0x7C82AE), new Color(0x363B58)));
    }
}
