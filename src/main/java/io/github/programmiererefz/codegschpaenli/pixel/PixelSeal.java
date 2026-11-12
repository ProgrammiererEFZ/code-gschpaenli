// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: seal. Props itself up on its fore-flippers with its head held high, galumphs along with a
 * body ripple when walking, belly-slides when running and sleeps in a banana curve with its tail flippers up.
 * Palette slots: E is the muzzle, A the spots, T the head sheen and whiskers.
 */
final class PixelSeal {
    private PixelSeal() {}

    private static final String[] STAND_A = {
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBYWBO...",
            ".........OBBBYYBEOT.",
            ".........OBBBBBEENO.",
            ".........OBBBBLEEOT.",
            "........OBBBBLLOO...",
            ".......OBBBBBLLLO...",
            "OO...OOBBABBBLLLO...",
            "OGO.OBBBBBBABLLLLO..",
            ".OGOBBABBBBBBLLLLO..",
            "OGGBBBBBBBLLLLLOGGGO",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBBBBO...",
            ".........OBBBOOBEOT.",
            ".........OBBBBBEENO.",
            ".........OBBBBLEEOT.",
            "........OBBBBLLOO...",
            ".......OBBBBBLLLO...",
            "OO...OOBBABBBLLLO...",
            "OGO.OBBBBBBABLLLLO..",
            ".OGOBBABBBBBBLLLLO..",
            "OGGBBBBBBBLLLLLOGGGO",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] STAND_CLAP_A = {
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBYWBO...",
            ".........OBBBYYBEOT.",
            ".........OBBBBBEENO.",
            ".........OBBBBLEEOT.",
            "........OBBBBLLOO...",
            ".......OBBBBBLLLO...",
            "OO...OOBBABBBLLLOOO.",
            "OGO.OBBBBBBABLLLOGGO",
            ".OGOBBABBBBBBLLLLOO.",
            "OGGBBBBBBBLLLLLLLO..",
            ".OOOOOOOOOOOOOOOOO..",
    };

    private static final String[] STAND_CLAP_B = {
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBYWBO...",
            ".........OBBBYYBEOT.",
            ".........OBBBBBEENO.",
            ".........OBBBBLEEOT.",
            "........OBBBBLLOO...",
            ".......OBBBBBLLLO...",
            "OO...OOBBABBBLLLO...",
            "OGO.OBBBBBBABLLLOO..",
            ".OGOBBABBBBBBLLOGGO.",
            "OGGBBBBBBBLLLLLOGGO.",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] WALK_1 = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBYWBO...",
            ".........OBBBYYBEOT.",
            ".........OBBBBBEENO.",
            "......OOOOBBBBLEEOT.",
            "....OOBBBBBBBLLOO...",
            "OO.OBBBABBBBBLLLO...",
            "OGOBBBBBBBABBLLLO...",
            ".OGBBABBBBBBBLLLLO..",
            "OGGBBBBBBBLLLLLOGGGO",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] WALK_2 = {
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".........OBTBYWBO...",
            ".........OBBBYYBEOT.",
            ".......OOOBBBBBEENO.",
            ".....OOBBBBBBBLEEOT.",
            "....OBBBBABBBBLLOO..",
            "...OBBABBBBBBLLLLO..",
            "..OBBBBBBBABBLLLLO..",
            "OOOBBABBBBBBLLLLLO..",
            "OGOBBBBBBBBLLLLLOGO.",
            "OGGOOBBBBLLLLLLOGGO.",
            ".OO..OOOOOOOOOOOOO..",
    };

    private static final String[] WALK_3 = {
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            ".....OOOOOOBBBBO....",
            "....OBBABBBTBYWBO...",
            "...OBBBBBBBBBYYBEOT.",
            "..OBBBBBABBBBBBEENO.",
            ".OBABBBBBBBBBLLEEOT.",
            ".OBBBBBABBBBLLLOO...",
            ".OBBBBBBBBBLLLLLO...",
            "OGOBBABBBBBLLLLLO...",
            "OGGOOBBBBLLLLLOGGO..",
            ".OO..OOOOOOOOOOOOO..",
    };

    private static final String[] WALK_4 = {
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OBBBBO....",
            ".......OOOBTBYWBO...",
            ".....OOBBBBBBYYBEOT.",
            "...OOBBBABBBBBBEENO.",
            "OO.OBBBBBBBBBLLEEOT.",
            "OGOBBABBBBABLLLLOO..",
            ".OGBBBBBBBBLLLLLLO..",
            "OGGOBBBBBLLLLLLOGGGO",
            ".OO.OOOOOOOOOOOOOOO.",
    };

    private static final String[] SLIDE_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            ".......OOOOBBBBOO...",
            "..OOOOOBBBBBBTBYWO..",
            "OOGGOBBBABBBBBBYYBO.",
            "OGGOBBBBBBABBBBBBEEO",
            ".OOOBBBABBBBBBLLLENO",
            "...OOLLLLLLLLLLLLOO.",
    };

    private static final String[] SLIDE_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            ".......OOOOBBBBOO...",
            ".OOOOOOBBBBBBTBYWO..",
            "OGGGOBBBABBBBBBYYBO.",
            ".OOGOBBBBBBABBBBBEEO",
            "..OOBBBABBBBBBLLLENO",
            "...OOLLLLLLLLLLLLOO.",
    };

    private static final String[] SIT_A = {
            "............OOOT....",
            "...........OBENO....",
            "..........OBBEEOT...",
            "..........OBYWBO....",
            ".........OBBYYBO....",
            ".........OBTBBLO....",
            "........OBBBBLLO....",
            "........OBBABLLLO...",
            ".......OBBBBBLLLO...",
            "OO....OBBABBBLLLO...",
            "OGO..OBBBBBBLLLLO...",
            ".OGOOBBABBBBLLLLOO..",
            "OGGBBBBBBBBLLLLOGGO.",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] SIT_BLINK = {
            "............OOOT....",
            "...........OBENO....",
            "..........OBBEEOT...",
            "..........OBBBBO....",
            ".........OBBOOBO....",
            ".........OBTBBLO....",
            "........OBBBBLLO....",
            "........OBBABLLLO...",
            ".......OBBBBBLLLO...",
            "OO....OBBABBBLLLO...",
            "OGO..OBBBBBBLLLLO...",
            ".OGOOBBABBBBLLLLOO..",
            "OGGBBBBBBBBLLLLOGGO.",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".O.O................",
            "OGOGO...............",
            "OGGGO...............",
            ".OGGO...............",
            "..OGOOOOOOOOOO......",
            "..OBBBBABBBBBBOOOO..",
            "..OBBABBBBBABBBBBBO.",
            "...OBBBBBBBBBBBOOBEO",
            "...OBBABBBBBLLLLEENO",
            "....OOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".O.O................",
            "OGOGO...............",
            "OGGGO...............",
            ".OGGO....OOOOO......",
            "..OGOOOOOBBBBBOOO...",
            "..OBBBBABBBBBBBBBOO.",
            "..OBBABBBBBABBBBBBBO",
            "...OBBBBBBBBBBBOOBEO",
            "...OBBABBBBBLLLLEENO",
            "....OOOOOOOOOOOOOOO.",
    };

    private static final String[] JUMP_A = {
            ".............OOOO...",
            "............OBBBBO..",
            "...........OBTBYWBO.",
            "..........OBBBBYYBEO",
            ".........OBBBBBBEENO",
            "........OBBBBBBLLOO.",
            ".......OBBBABBLLLO..",
            ".OO..OOBBBBBBLLLLO..",
            "OGGOOBBABBBBLLLLO...",
            "OGOBBBBBBABLLLLO....",
            ".OOBBBABBBLLLLOGO...",
            "...OOBBBBLLLLOGGO...",
            ".....OOOOOOOOOOO....",
            "....................",
    };

    private static final String[] FALL_A = {
            "....................",
            ".............OOOO...",
            "............OBBBBO..",
            "...........OBTBWWBO.",
            "...........OBBBWYBEO",
            "..........OBBBBBBENO",
            "..OO......OBBBBBLLO.",
            ".OGGOOOOOOBBBBLLLOOO",
            ".OGOBBBBABBBBLLLLOGO",
            "..OBBABBBBBABLLLLGGO",
            "OOBBBBBBBBBBLLLLLOO.",
            "OGOBBABBBBBLLLLLO...",
            ".OGOOOOOOOOOOOOO....",
            "..O.................",
    };

    private static final String[] FALL_B = {
            "....................",
            ".............OOOO...",
            "............OBBBBO..",
            "...........OBTBWWBO.",
            "...........OBBBWYBEO",
            "..........OBBBBBBENO",
            "..........OBBBBBLLO.",
            "OOO..OOOOOBBBBLLLO..",
            "OGGOOBBBABBBBLLLLOOO",
            ".OGOBBABBBBBABLLLGGO",
            "..OBBBBBBBBBBLLLLOGO",
            ".OOBBABBBBBLLLLLOO..",
            "OGOOOOOOOOOOOOOO....",
            ".O..................",
    };

    private static final String[] HELD_A = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBTBYWBO......",
            "......OBBBYYBEO.....",
            "......OBBBBBEENO....",
            "......OBBBLLEEO.....",
            ".....OOBBBLLLOOO....",
            "....OGOBBBLLLOGO....",
            "....OGOBBBLLLOGO....",
            ".....OBBABLLLLO.....",
            ".....OBBBBLLLLO.....",
            "......OBBBLLLO......",
            ".....OGOOBLLOOGO....",
            ".....OGO.OOO.OGO....",
    };

    private static final String[] HELD_B = {
            "........OOOO........",
            ".......OBBBBO.......",
            "......OBTBYWBO......",
            "......OBBBYYBEO.....",
            "......OBBBBBEENO....",
            "......OBBBLLEEO.....",
            "....OOOBBBLLLOOO....",
            "...OGGOBBBLLLOGGO...",
            "....OOOBBBLLLOOO....",
            ".....OBBABLLLLO.....",
            ".....OBBBBLLLLO.....",
            "......OBBBLLLO......",
            "......OGOBLLOGO.....",
            "......OGOOOOOGO.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_A, STAND_BLINK, STAND_A, STAND_A, STAND_CLAP_A,
                    STAND_CLAP_B, STAND_CLAP_A, STAND_CLAP_B, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, SLIDE_A, SLIDE_A, SLIDE_B, SLIDE_B)
            .anim(Pose.SIT, 1.0, SIT_A, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                // name, body, belly, flippers, muzzle, outline, eye, nose, spots, sheen
                new PetKind.Palette("Harbour", new Color(0x8A929C), new Color(0xC6CCD3), new Color(0x6A727C),
                        new Color(0xB3BAC3), new Color(0x262A31), new Color(0x101216), new Color(0x1A1C21),
                        new Color(0x646C77), new Color(0xD3D9DF)),
                new PetKind.Palette("White pup", new Color(0xF2EFE8), new Color(0xFFFFFF), new Color(0xD8D3C9),
                        new Color(0xFFFFFF), new Color(0x77736C), new Color(0x16141A), new Color(0x2E2729),
                        new Color(0xE2DDD3), new Color(0xFFFFFF)),
                new PetKind.Palette("Dark brown", new Color(0x5E4536), new Color(0x8A6B57), new Color(0x402E24),
                        new Color(0x957867), new Color(0x170F0B), new Color(0x0C0807), new Color(0x0C0807),
                        new Color(0x4C372B), new Color(0xB89C86)),
                new PetKind.Palette("Spotted", new Color(0xB9BFC6), new Color(0xE8EAEC), new Color(0x8D959E),
                        new Color(0xDADDE1), new Color(0x2E333A), new Color(0x101216), new Color(0x1A1C21),
                        new Color(0x3C424A), new Color(0xE0E4E8)));
    }
}
