// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: axolotl. A wide smiling head with three feathery gill fronds (E) on the back of the head, a
 * long body on tiny legs and a tail with a light fin (T) along it.
 */
final class PixelAxolotl {
    private PixelAxolotl() {}

    private static final String[] STAND_A = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOOEEE...OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            "....OGGGO..OGGGO....",
    };

    private static final String[] STAND_GILLS = {
            "..........E.E.......",
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOEEE.E..OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            "....OGGGO..OGGGO....",
    };

    private static final String[] STAND_BLINK = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBOBO",
            "..OOOOEEE...OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            "....OGGGO..OGGGO....",
    };

    private static final String[] STAND_GRIN = {
            "..........E.E.......",
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOEEE.E..OBBBBBBO",
            ".OTTTTTTOOOOBBNBBPNO",
            "OTTBABBBBABBBBBNNNBO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            "....OGGGO..OGGGO....",
    };

    private static final String[] WALK_1 = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "OOOOOOEEE...OBBBBBBO",
            "OTTTTTTTOOOOBBBNBBBO",
            ".OTBABBBBABBBBBBNNNO",
            "..OBBBBABBBBBBBLLLO.",
            "...OOLLLLLLLLLLLLO..",
            "....OGGO.....OGGO...",
            "...OGGO.......OGGGO.",
    };

    private static final String[] WALK_2 = {
            "..........E.E.......",
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOEEE.E..OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            ".....OGGGO..OGGGO...",
    };

    private static final String[] WALK_3 = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "...OOOEEE...OBBBBBBO",
            "..OTTTTTOOOOBBBNBBBO",
            ".OTBABBBBABBBBBBNNNO",
            "OTTBBBBABBBBBBBLLLO.",
            "OTOOOLLLLLLLLLLLLO..",
            "......OGGO..OGGO....",
            "......OGGGO.OGGO....",
    };

    private static final String[] WALK_4 = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOOEEE...OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".....OGGO...OGGO....",
            ".....OGGGO..OGGGO...",
    };

    private static final String[] RUN_1 = {
            "....................",
            "..........E.E.......",
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            "OOOOOOOO.EEEOBBBBYBO",
            "OTTTTEEEOEOOOBBBBBBO",
            ".OTBABBBBABBBBBNBBBO",
            "..OBBBBABBBBBBBBNNNO",
            "...OOLLLLLLLBBBLLLO.",
            "...OGGO.......OGGO..",
            "..OGGO.........OGGGO",
    };

    private static final String[] RUN_2 = {
            ".........E.E........",
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOOEEE...OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".......OGGOOGGO.....",
            ".......OGGGOGGGO....",
    };

    private static final String[] RUN_3 = {
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            ".....EEE.E..OBBBBBBO",
            "...OOOOO....BBBNBBBO",
            "..OTTTTTOOOOBBBBNNNO",
            ".OTBABBBBABBBBBLLLOO",
            "OTTBBBBABBBBBBBLLLO.",
            "OTOOOLLLLLLLLLLLLO..",
            "...OGGO.......OGGO..",
            "..OGGO.........OGGGO",
    };

    private static final String[] RUN_4 = {
            "..........E.E.......",
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBBBO",
            ".........EEEOBBBBYBO",
            "..OOOEEE.E..OBBBBBBO",
            ".OTTTTTTOOOOBBBNBBBO",
            "OTTBABBBBABBBBBBNNNO",
            "OTBBBBBABBBBBBBLLLO.",
            ".OOOOLLLLLLLLLLLLO..",
            ".......OGGOOGGO.....",
            ".......OGGGOGGGO....",
    };

    private static final String[] SIT_A = {
            "........EEE.........",
            "..........EE..OOOO..",
            ".........E.EEOBBBBO.",
            "......EE....OBBBBBBO",
            ".....E.EEEEEOBBBBYBO",
            ".........EEEOBBBBBBO",
            "......EEEOOOBBBNBBBO",
            ".....OOBBBBBBBBBNNNO",
            "...OOBBABBBBBBBLLLO.",
            "..OTBBBBBBABBLLLLO..",
            ".OTTBBBBBBBBLLLOGO..",
            "OTTBBBABBBBLLLOGGO..",
            "OTOOGGOOOOOOOOOGGO..",
            ".O.OGGGO......OGGGO.",
    };

    private static final String[] SIT_GILLS = {
            ".........EEE........",
            "..........EE..OOOO..",
            "......E..E.EEOBBBBO.",
            ".....E.E....OBBBBBBO",
            "........EEEEOBBBBYBO",
            ".........EEEOBBBBBBO",
            ".....EEEOEOOBBBNBBBO",
            ".....OOBBBBBBBBBNNNO",
            "...OOBBABBBBBBBLLLO.",
            "..OTBBBBBBABBLLLLO..",
            ".OTTBBBBBBBBLLLOGO..",
            "OTTBBBABBBBLLLOGGO..",
            "OTOOGGOOOOOOOOOGGO..",
            ".O.OGGGO......OGGGO.",
    };

    private static final String[] SIT_BLINK = {
            "........EEE.........",
            "..........EE..OOOO..",
            ".........E.EEOBBBBO.",
            "......EE....OBBBBBBO",
            ".....E.EEEEEOBBBBOBO",
            ".........EEEOBBBBBBO",
            "......EEEOOOBBBNBBBO",
            ".....OOBBBBBBBBBNNNO",
            "...OOBBABBBBBBBLLLO.",
            "..OTBBBBBBABBLLLLO..",
            ".OTTBBBBBBBBLLLOGO..",
            "OTTBBBABBBBLLLOGGO..",
            "OTOOGGOOOOOOOOOGGO..",
            ".O.OGGGO......OGGGO.",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "..........E.........",
            "........EEE...OOOO..",
            "......EEE.EE.OBBBBO.",
            "....EEOEEE..OBBBBBBO",
            "..OOBEEABBOOOBBBOOBO",
            ".OBBABBBBBBBOBBBBBBO",
            "OBBBBBBABBBBBBBNBBBO",
            "OTTTTTTTTTTTBBBBNNNO",
            "OTBBBABBBBABBBBLLLO.",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "..........E.........",
            "........EEE.........",
            "......EEE.EE..OOOO..",
            "....EEOEEE...OBBBBO.",
            "..OOBEEABBOOOBBBBBBO",
            ".OBBBBBBBBBBOBBBOOBO",
            "OBBABBBBBBBBOBBBBBBO",
            "OBBBBBBABBBBBBBNBBBO",
            "OTTTTTTTTTTTBBBBNNNO",
            "OTBBBABBBBABBBBLLLO.",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] JUMP_A = {
            "........EEE.........",
            ".........EE.........",
            ".....E..E.EE..OOOO..",
            "....E.E....E.OBBBBO.",
            ".......EEEE.OBBBBBBO",
            "........EEE.OBBBBYBO",
            "....EEE.E...OBBBBBBO",
            ".........OOOBBBNBBBO",
            "......OOOBBBBBBBNNNO",
            "..OOOOBBBABBBBBLLLOO",
            ".OTTTBBABBBBBBLLLLO.",
            "OTTBBBBBBLLLLLLOO...",
            "OTOGGGOOOOOOOGGO....",
            ".O.OOO.....OGGO.....",
    };

    private static final String[] FALL_A = {
            "........EEE.........",
            "..........EE........",
            ".........E.EE.OOOO..",
            "......EE....EOBBBBO.",
            ".....E.EEEEEOBBBBWBO",
            ".........EEEOBBBBKBO",
            "......EEE...OBBBBBBO",
            "..OOOOOOOOOOBBBNBBBO",
            ".OTTTTTTBBBBBBBBNKKO",
            "OTTBABBBBABBBBBLLLOO",
            "OTBBBBBABBBBBBBLLLO.",
            "OGOOOLLLLLLLLLLLOGO.",
            "OGGO.OO......OO.OGGO",
            ".OO..............OO.",
    };

    private static final String[] FALL_B = {
            ".........EEE........",
            "..........EE........",
            "......E..E.EE.OOOO..",
            ".....E.E....EOBBBBO.",
            "........EEEEOBBBBWBO",
            ".........EEEOBBBBKBO",
            ".....EEE.E..OBBBBBBO",
            "OOOOOOOOOOOOBBBNBBBO",
            "OTTTTTTTBBBBBBBBNKKO",
            ".OTBABBBBABBBBBLLLOO",
            "OGOBBBBABBBBBBBLLLO.",
            "OGGOOLLLLLLLLLLLOGGO",
            ".OGO.OO......OO..OGO",
            "..O...............O.",
    };

    private static final String[] HELD_A = {
            ".....EEE..OOOO......",
            "......EE.OBBBBO.....",
            "..E..E.EOBBBBWBO....",
            ".E.E....OBBBBKBO....",
            "....EEEEOBBBBBBO....",
            ".....EEEBBBNBBBO....",
            ".EEE.E.OBBBBNNNO....",
            ".......OBBBLLLO.....",
            ".......OBBBLLLOO....",
            ".......OBBABLLOGGO..",
            "....OGOOBBBBLLO.OGO.",
            "....OGGOBBBBLLO.....",
            ".OOOOBBBBABBLLO.....",
            "OTTTTTTTOOOOOOO.....",
    };

    private static final String[] HELD_B = {
            "....EEE...OOOO......",
            "......EE.OBBBBO.....",
            ".....E.EOBBBBWBO....",
            "..EE....OBBBBKBO....",
            ".E.EEEEEOBBBBBBO....",
            ".....EEEBBBNBBBO....",
            "..EEE..OBBBBNNNO....",
            ".......OBBBLLLO.....",
            ".......OBBBLLLO.....",
            "......OOBBABLLOO....",
            ".....OGOBBBBLLOGO...",
            ".....OGOBBBBLLOGGO..",
            "..OOOOOBBABBLLO.....",
            ".OTTTTTTOOOOOO......",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_GILLS, STAND_A, STAND_GILLS, STAND_A, STAND_BLINK,
                    STAND_A, STAND_GILLS, STAND_GRIN, STAND_GRIN)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_GILLS, SIT_A, SIT_GILLS, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Leucistic", new Color(0xF7CAD3), new Color(0xFDE6EA), new Color(0xF4B4C2),
                        new Color(0xE5506F), new Color(0x6E2E3E), new Color(0x141418), new Color(0x9A3C55),
                        new Color(0xEDAABA), new Color(0xFBDDE4)),
                new PetKind.Palette("Wild", new Color(0x5E6145), new Color(0x8C8E68), new Color(0x4F523A),
                        new Color(0xA4607E), new Color(0x1B1C12), new Color(0xE2C25A), new Color(0x1B1C12),
                        new Color(0x3D3F2B), new Color(0x7F8360)),
                new PetKind.Palette("Golden", new Color(0xF4D27A), new Color(0xFCEDBF), new Color(0xEEC468),
                        new Color(0xF29A7E), new Color(0x7A5220), new Color(0xC8506A), new Color(0x8A5A28),
                        new Color(0xFAE7A6), new Color(0xFBE9B4)),
                new PetKind.Palette("Blue", new Color(0x6F8FCB), new Color(0xAFC6EE), new Color(0x5E7EBA),
                        new Color(0xA48BEA), new Color(0x18223E), new Color(0x101626), new Color(0x18223E),
                        new Color(0x5674B0), new Color(0x9DB6E6)));
    }
}
