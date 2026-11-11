// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/** Original pixel art: mouse. */
final class PixelMouse {
    private PixelMouse() {}

    private static final String[] STAND_A = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGGO...OGGO....",
    };

    private static final String[] STAND_SNIFF = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLLN",
            "...OBBBBBBBBBBPLLLLO",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGGO...OGGO....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBBBBO.",
            "....OBBBBBBBBBBOOBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGGO...OGGO....",
    };

    private static final String[] WALK_1 = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGGO...OGGO....",
    };

    private static final String[] WALK_2 = {
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGO..OGO.......",
            "....OGGO..OGGO......",
    };

    private static final String[] WALK_3 = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".......OGGOGGO......",
    };

    private static final String[] WALK_4 = {
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            "......OGO...OGO.....",
            "......OGGO..OGGO....",
    };

    private static final String[] RUN_1 = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            "...OGO.......OGO....",
            "..OGO.........OGO...",
            ".OO............OO...",
    };

    private static final String[] RUN_2 = {
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            "......OGOOGO........",
            ".....OGGOGGO........",
    };

    private static final String[] RUN_3 = {
            "....................",
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            "........OGGOGGO.....",
    };

    private static final String[] RUN_4 = {
            "....................",
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOOOOOOOOOOO....",
            ".....OGO...OGO......",
            "....OGGO....OGGO....",
    };

    private static final String[] SIT_A = {
            "....................",
            ".........OOO........",
            "........OBBBO.......",
            ".......OBEEEBOOO....",
            ".......OBEEEBBBBO...",
            "........OOOBBWYBO...",
            "........OBBBBYYBBO..",
            "........OBBBBPBLLNO.",
            ".......OBBBBBBLLLO..",
            ".......OBBBBLLLGGO..",
            "......OBBBBBLLLLO...",
            "......OBBBBBLLLLO...",
            "T.....OBBBBBLLLLO...",
            ".TTTTOGGGGOOOGGO....",
    };

    private static final String[] SIT_GROOM = {
            "....................",
            ".........OOO........",
            "........OBBBO.......",
            ".......OBEEEBOOO....",
            ".......OBEEEBBBBO...",
            "........OOOBBBBBO...",
            "........OBBBBOOBBO..",
            "........OBBBBPBLGGO.",
            ".......OBBBBBBLLLO..",
            ".......OBBBBLLLLO...",
            "......OBBBBBLLLLO...",
            "......OBBBBBLLLLO...",
            "T.....OBBBBBLLLLO...",
            ".TTTTOGGGGOOOGGO....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            ".........OOO........",
            "........OBBBO.......",
            ".......OBEEEBOOO....",
            ".......OBEEEBBBBO...",
            "........OOOBBBBBO...",
            "........OBBBBOOBBO..",
            "........OBBBBPBLLNO.",
            ".......OBBBBBBLLLO..",
            ".......OBBBBLLLGGO..",
            "......OBBBBBLLLLO...",
            "......OBBBBBLLLLO...",
            "T.....OBBBBBLLLLO...",
            ".TTTTOGGGGOOOGGO....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOO......",
            ".......OOOOBBBO.....",
            ".....OOBBBOBEEBO....",
            "....OBBBBBOBEEBO....",
            "...OBBBBBBBOOOOBO...",
            "..OBBBBBBBBBBBBBBO..",
            "..OBBBBBBBBBBOOBLNO.",
            ".OTTTTTTTOOOOOOOOO..",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOO......",
            "......OOOOOBBBO.....",
            "....OOBBBBOBEEBO....",
            "...OBBBBBBOBEEBO....",
            "...OBBBBBBBOOOOBO...",
            "..OBBBBBBBBBBBBBBO..",
            "..OBBBBBBBBBBOOBLNO.",
            ".OTTTTTTTOOOOOOOOO..",
    };

    private static final String[] JUMP_A = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOGOOOOOOGOO....",
            "...OGO.......OGO....",
            "..OGO.........OGO...",
            ".OGGO..........OGGO.",
    };

    private static final String[] FALL_A = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWWBO.",
            "....OBBBBBBBBBBWYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOGOOOOOOGOO....",
            "...OGO......OGO.....",
            "..OGO........OGO....",
            "....................",
    };

    private static final String[] FALL_B = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWWBO.",
            "....OBBBBBBBBBBWYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOGOOOOOOGOO....",
            ".....OGO...OGO......",
            "....OGGO...OGGO.....",
            "....................",
    };

    private static final String[] HELD_A = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOGOOOOOOGOO....",
            ".....OGO....OGO.....",
            ".....OGO....OGO.....",
            ".....OGGO...OGGO....",
    };

    private static final String[] HELD_B = {
            "..........OOO.......",
            ".........OBBBO......",
            "........OBEEEBOOOO..",
            "......OOOBEEEBBBBBO.",
            ".....OBBBBOOOBBWYBO.",
            "....OBBBBBBBBBBYYBBO",
            "....OBBBBBBBBBBBLLNO",
            "...OBBBBBBBBBBPLLLO.",
            "T..TBBBBBBBBBBBLLO..",
            "T.TOBBBBBBLLLLLLO...",
            ".T..OOGOOOOOOGOO....",
            ".....OGO....OGO.....",
            "......OGO....OGO....",
            "......OGGO...OGGO...",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_SNIFF, STAND_A, STAND_SNIFF, STAND_A, STAND_A, STAND_BLINK, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 2.5, SIT_A, SIT_A, SIT_GROOM, SIT_A, SIT_GROOM, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Grey", new Color(0xA3A6AD), new Color(0xE9E6E1), new Color(0xF2A9B4),
                        new Color(0xF4B3BE), new Color(0x303238), new Color(0x121216), new Color(0xEE7F92),
                        new Color(0x8A8D94), new Color(0xEFA3B0)),
                new PetKind.Palette("Brown", new Color(0xA77B55), new Color(0xF1DEC4), new Color(0xF0B0AE),
                        new Color(0xF2B3B8), new Color(0x3E2A1A), new Color(0x140E0A), new Color(0xE5808E),
                        new Color(0x8A6242), new Color(0xE8A6A4)),
                new PetKind.Palette("White", new Color(0xF5F3EF), new Color(0xFFFFFF), new Color(0xF6B3BE),
                        new Color(0xF7AFC0), new Color(0x8E8A86), new Color(0x9E1F3A), new Color(0xEE7F92),
                        new Color(0xE2DDD3), new Color(0xF6B3BE)));
    }
}
