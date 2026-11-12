// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/** Original pixel art: squirrel. Bounds along in hops, sits up to nibble a nut and sleeps under its bushy tail. */
final class PixelSquirrel {
    private PixelSquirrel() {}

    private static final String[] STAND_A = {
            "..OOOOO.............",
            ".OTTTTTOO...........",
            "OTTBBBBTTO....OO....",
            "OTBBBBBBBTO..OEO....",
            "OTBBBOOOBTO.OEEBOO..",
            "OTBBBO..OTOOBBBBBBO.",
            "OTBBBO...O.OBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOGOGO.",
            "...OBBBBBOOOOOOGOGO.",
            "...OOGGGGGGO...OGOGO",
    };

    private static final String[] STAND_FLICK = {
            "...OOOOO............",
            "..OTTTTTOO..........",
            ".OTBBBBBTTO...OO....",
            "OTBBBBBBBBTO.OEO....",
            "OTBBBOOOOBTOOEEBOO..",
            "OTBBBO...OTOBBBBBBO.",
            "OTBBBO....OOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOGOGO.",
            "...OBBBBBOOOOOOGOGO.",
            "...OOGGGGGGO...OGOGO",
    };

    private static final String[] STAND_BLINK = {
            "..OOOOO.............",
            ".OTTTTTOO...........",
            "OTTBBBBTTO....OO....",
            "OTBBBBBBBTO..OEO....",
            "OTBBBOOOBTO.OEEBOO..",
            "OTBBBO..OTOOBBBBBBO.",
            "OTBBBO...O.OBBBBBBBO",
            "OTBBBOOOOOOBBBBBOOBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOGOGO.",
            "...OBBBBBOOOOOOGOGO.",
            "...OOGGGGGGO...OGOGO",
    };

    private static final String[] STAND_SNIFF = {
            "..OOOOO.............",
            ".OTTTTTOO...........",
            "OTTBBBBTTO....OO....",
            "OTBBBBBBBTO..OEO....",
            "OTBBBOOOBTO.OEEBOO..",
            "OTBBBO..OTOOBBBBBBO.",
            "OTBBBO...O.OBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBB",
            "OTBBOBBBBBBBBBBLLLLN",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOGOGO.",
            "...OBBBBBOOOOOOGOGO.",
            "...OOGGGGGGO...OGOGO",
    };

    private static final String[] RUN_1 = {
            "....................",
            "..OOOOO.............",
            ".OTTTTTOO.....OO....",
            "OTTBBBBTTO...OEO....",
            "OTBBBBBBBTO.OEEBOO..",
            "OTBBBOOOBTOOBBBBBBO.",
            "OTBBBO..OTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OOBBBOBBBBBOGOGO.",
            "....OOBBBOOOOOOGOGO.",
            ".....OOGGGGGGOOGOGO.",
    };

    private static final String[] RUN_2 = {
            "....................",
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLGGGO",
            "..OGOOBBBOOOOOOOOOO.",
            ".OGO................",
            "OGGO................",
    };

    private static final String[] RUN_3 = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            ".OOGOOBBBOOOOOOGGGGO",
            "OGGOO..........OOOO.",
            "....................",
            "....................",
    };

    private static final String[] RUN_4 = {
            "..OOOOO.............",
            ".OTTTTTOO.....OO....",
            "OTTBBBBTTO...OEO....",
            "OTBBBBBBBTO.OEEBOO..",
            "OTBBBOOOBTOOBBBBBBO.",
            "OTBBBO..OTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "...OOGGGGGOOOOOGOGO.",
            "....OOOOOO.....OGOGO",
            "...............OGOGO",
    };

    private static final String[] WALK_1 = {
            "..OOOOO.............",
            ".OTTTTTOO...........",
            "OTTBBBBTTO....OO....",
            "OTBBBBBBBTO..OEO....",
            "OTBBBOOOBTO.OEEBOO..",
            "OTBBBO..OTOOBBBBBBO.",
            "OTBBBO...O.OBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OOBBBOBBBBBOGOGO.",
            "....OOBBBOOOOOOGOGO.",
            ".....OOGGGGGGOOGOGO.",
    };

    private static final String[] WALK_2 = {
            "..OOOOO.............",
            ".OTTTTTOO.....OO....",
            "OTTBBBBTTO...OEO....",
            "OTBBBBBBBTO.OEEBOO..",
            "OTBBBOOOBTOOBBBBBBO.",
            "OTBBBO..OTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLGGO.",
            "..OGOOBBBOOOOOOOGGO.",
            ".OGO................",
            "OGGO................",
    };

    private static final String[] WALK_3 = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "..OGOOBBBOOOOOOGGGO.",
            ".OGO...........OOGGO",
            "OGO.................",
            "....................",
    };

    private static final String[] WALK_4 = {
            "..OOOOO.............",
            ".OTTTTTOO.....OO....",
            "OTTBBBBTTO...OEO....",
            "OTBBBBBBBTO.OEEBOO..",
            "OTBBBOOOBTOOBBBBBBO.",
            "OTBBBO..OTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "...OOGGGGGOOOOOGOGO.",
            "....OOOOOO.....OGOGO",
            "...............OGOGO",
    };

    private static final String[] SIT_A = {
            "....OOOOO...........",
            "...OTTTTTOO...OO....",
            "..OTBBBBBTTO.OEO....",
            ".OTBBBBBBBTOOEEBOO..",
            ".OTBBBOOOBTOBBBBBBO.",
            ".OTBBO..OTOOBBBBYWBO",
            "OTBBBO...O.OBBBBYYBN",
            "OTBBBO....OBBBBBLLLO",
            "OTBBBO...OBBBBLLLLO.",
            "OTBBBBO.OBBBBLLOOOO.",
            ".OTBBBO.OBBBBLLGAAGO",
            ".OTBBBBOBBBBBLLGAAGO",
            "..OTBBBOBBOBBLLOOOO.",
            "...OTBBOBBBOBLLLLO..",
            "....OOTOBBBBOLLLLO..",
            "......OOGGGGGOGGGO..",
    };

    private static final String[] SIT_CHEW = {
            "....OOOOO...........",
            "...OTTTTTOO...OO....",
            "..OTBBBBBTTO.OEO....",
            ".OTBBBBBBBTOOEEBOO..",
            ".OTBBBOOOBTOBBBBBBO.",
            ".OTBBO..OTOOBBBBYWBO",
            "OTBBBO...O.OBBBBYYBN",
            "OTBBBO....OBBBBBLLLO",
            "OTBBBO...OBBBBLLOOOO",
            "OTBBBBO.OBBBBLLGAAGO",
            ".OTBBBO.OBBBBLLGAAGO",
            ".OTBBBBOBBBBBLLOOOO.",
            "..OTBBBOBBOBBLLLLO..",
            "...OTBBOBBBOBLLLLO..",
            "....OOTOBBBBOLLLLO..",
            "......OOGGGGGOGGGO..",
    };

    private static final String[] SIT_BLINK = {
            "....OOOOO...........",
            "...OTTTTTOO...OO....",
            "..OTBBBBBTTO.OEO....",
            ".OTBBBBBBBTOOEEBOO..",
            ".OTBBBOOOBTOBBBBBBO.",
            ".OTBBO..OTOOBBBBBBBO",
            "OTBBBO...O.OBBBBOOBN",
            "OTBBBO....OBBBBBLLLO",
            "OTBBBO...OBBBBLLLLO.",
            "OTBBBBO.OBBBBLLOOOO.",
            ".OTBBBO.OBBBBLLGAAGO",
            ".OTBBBBOBBBBBLLGAAGO",
            "..OTBBBOBBOBBLLOOOO.",
            "...OTBBOBBBOBLLLLO..",
            "....OOTOBBBBOLLLLO..",
            "......OOGGGGGOGGGO..",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...OOOOOOO..........",
            ".OOTTTTTTTOO........",
            "OTTBBBBBBBTTO..OO...",
            "OTBBBBBBBBBTO.OEO...",
            "OTBBBBBBBBBBTOEEBOO.",
            "OTBBBBBBBBBBTOBBBBBO",
            "OTBBBBBBBBBTOBBBBBBO",
            "OTBBBBBBBBTOBBBOOBBN",
            ".OTTTTTTTTOBBBBBBLLO",
            "..OOOOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...OOOOOOO..........",
            ".OOTTTTTTTOO........",
            "OTBBBBBBBBTTO.......",
            "OTTBBBBBBBTTO..OO...",
            "OTBBBBBBBBBTO.OEO...",
            "OTBBBBBBBBBBTOEEBOO.",
            "OTBBBBBBBBBBTOBBBBBO",
            "OTBBBBBBBBBTOBBBBBBO",
            "OTBBBBBBBBTOBBBOOBBN",
            ".OTTTTTTTTOBBBBBBLLO",
            "..OOOOOOOOOOOOOOOOO.",
    };

    private static final String[] JUMP_A = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            ".OOGOOBBBOOOOOOGGGGO",
            "OGGOO..........OOOO.",
            "....................",
            "....................",
    };

    private static final String[] FALL_A = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBWWBO",
            "OTBBBOOOOOOBBBBBWYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "..OGOOOOOOOOOOOOGO..",
            ".OGO.............OGO",
            "OGO...............OO",
            "....................",
    };

    private static final String[] FALL_B = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBWWBO",
            "OTBBBOOOOOOBBBBBWYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "...OGOOOOOOOOOOGOO..",
            "...OGO.........OGO..",
            "...OO..........OO...",
            "....................",
    };

    private static final String[] HELD_A = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "....OGOOOOOOOOGOGO..",
            "....OGO.......OGOGO.",
            "....OGO.......OGOGO.",
            "....OGGO......OOOOO.",
    };

    private static final String[] HELD_B = {
            "..OOOOO.......OO....",
            ".OTTTTTOO....OEO....",
            "OTTBBBBTTO..OEEBOO..",
            "OTBBBBBBBTOOBBBBBBO.",
            "OTBBBOOOBTOOBBBBYWBO",
            "OTBBBOOOOOOBBBBBYYBN",
            "OTBBOBBBBBBBBBBLLLLO",
            ".OTOBBBBBBBBBBBLLLO.",
            ".OTOBBBBBBBBBBBLLO..",
            "..OOBBOBBBBBBBOLLO..",
            "..OOBBBOBBBBBBOLLO..",
            "...OBBBBOBBBBBOLLO..",
            "....OGOOOOOOOOGOGO..",
            ".....OGO......OGOGO.",
            ".....OGO.....OGOGO..",
            ".....OGGO....OOOOO..",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_FLICK, STAND_A, STAND_SNIFF, STAND_A, STAND_A, STAND_BLINK, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 2.5, SIT_A, SIT_CHEW, SIT_A, SIT_CHEW, SIT_A, SIT_A, SIT_BLINK, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                // name, body, belly, leg, ear (tufts), outline, eye, nose, accent (nut), tail fringe
                new PetKind.Palette("Red", new Color(0xC9622B), new Color(0xFBE8CF), new Color(0xB4531F),
                        new Color(0xA2461A), new Color(0x4A1F0B), new Color(0x1A0F08), new Color(0x3A1A0E),
                        new Color(0xC99248), new Color(0xE8894A)),
                new PetKind.Palette("Grey", new Color(0x9A9BA2), new Color(0xF2F0EA), new Color(0x8C8D94),
                        new Color(0xB89A82), new Color(0x3A3B42), new Color(0x141418), new Color(0x4A3C3C),
                        new Color(0xB98242), new Color(0xD8DADF)),
                new PetKind.Palette("Black", new Color(0x3C363B), new Color(0x5C545A), new Color(0x332E32),
                        new Color(0x4E464C), new Color(0x0F0D0F), new Color(0x0A0808), new Color(0x1E1A1C),
                        new Color(0xC28C4A), new Color(0x5F575D)),
                new PetKind.Palette("Albino", new Color(0xF2EDE5), new Color(0xFFFFFF), new Color(0xEDE6DC),
                        new Color(0xF4B3BE), new Color(0x9A8E86), new Color(0xC2304A), new Color(0xEE8F9A),
                        new Color(0xB98048), new Color(0xFFFFFF)));
    }
}
