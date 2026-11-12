// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: crab. Drawn face-on, because a crab scuttles sideways: walking right shows its front, with both
 * claws raised and the eyes up on their stalks. Palette slots: B shell, T shell shine, A shell spots, L underside,
 * E claws, N claw tips and mouth, G legs and eye stalks, Y pupils.
 */
final class PixelCrab {
    private PixelCrab() {}

    private static final String[] STAND_A = {
            ".......OO..OO.......",
            ".O...OOWYOOWYOO...O.",
            "ONO.ONOWWOOWWONO.ONO",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_SNAP = {
            ".......OO..OO.......",
            "......OWYOOWYO......",
            "..OOO.OWWOOWWO.OOO..",
            ".OENEO.OO..OO.OENEO.",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_SNAP_L = {
            ".......OO..OO.......",
            "......OWYOOWYOO...O.",
            "..OOO.OWWOOWWONO.ONO",
            ".OENEO.OO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_BOTH = {
            ".......OO..OO.......",
            "......OWYOOWYO......",
            "..OOO.OWWOOWWO.OOO..",
            ".OENEO.OO..OO.OENEO.",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_LOOK = {
            ".......OO..OO.......",
            ".O...OOWWOOWWOO...O.",
            "ONO.ONOWYOOWYONO.ONO",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_TWITCH = {
            ".......OO.OO........",
            ".O...OOWYOWYO.O...O.",
            "ONO.ONOWWOWWOONO.ONO",
            "OEO.OEOOO.OO.OEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            ".O...O.OO..OO.O...O.",
            "ONO.ONOOOOOOOONO.ONO",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] WALK_1 = {
            ".......OO..OO.......",
            "......OWYOOWYOO...O.",
            "..OOO.OWWOOWWONO.ONO",
            ".OENEO.OO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            ".G.GOOOOOOOOOOOOG..G",
            ".G.G.G........G..G.G",
            ".G..G.G........G..G.",
            "..G.G..G........G..G",
    };

    private static final String[] WALK_2 = {
            ".......OO..OO.......",
            "......OWYOOWYO......",
            ".O...OOWWOOWWOO...O.",
            "ONO.ONOOO..OOONO.ONO",
            "OEO.OEO.G..G.OEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEOOOOOOOOEEEEEO",
            ".OEEEOTTBBBBBBOEEEO.",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABOEO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] WALK_3 = {
            ".......OO..OO.......",
            "......OWYOOWYO......",
            "..OOO.OWWOOWWO.OOO..",
            ".OENEO.OO..OO.OENEO.",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG.G.",
            "G.G..G........G.G.G.",
            ".G..G........G.G..G.",
            "G..G........G..G.G..",
    };

    private static final String[] WALK_4 = {
            ".......OO..OO.......",
            "......OWYOOWYOO...O.",
            ".O...OOWWOOWWONO.ONO",
            "ONO.ONOOO..OOOEO.OEO",
            "OEO.OEO.G..G.OEEOEEO",
            "OEEOEEO.G..G.OEEEEEO",
            "OEEEEEOOOOOOOOOEEEO.",
            ".OEEEOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "G.G..G........G..G.G",
            "G.G..G........G..G.G",
            ".G.G..G......G..G.G.",
    };

    private static final String[] RUN_1 = {
            ".O...O.OO..OO.O...O.",
            "ONO.ONOWYOOWYONO.ONO",
            "OEO.OEOWWOOWWOEO.OEO",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..GG",
            ".G.G............GG.G",
            ".G.G.G........GG..G.",
            ".G..G.G.........G.G.",
            "..G.G..G............",
    };

    private static final String[] RUN_2 = {
            ".......OO..OO.......",
            "..OOO.OWYOOWYO.OOO..",
            ".OENEOOWWOOWWOOENEO.",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEO..G..G..OEEEO.",
            "..OEO.OOOOOOOO.OEO..",
            "..OEOOTTBBBBBBOOEO..",
            "...OTTBBBBBBBBBBO...",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG.G.",
            "G.G..G........G.G.G.",
            ".G..G........G.G..G.",
            "G..G........G..G.G..",
    };

    private static final String[] RUN_3 = {
            ".O...O.OO..OO.O...O.",
            "ONO.ONOWYOOWYONO.ONO",
            "OEO.OEOWWOOWWOEO.OEO",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            "GG..OOOOOOOOOOOO..G.",
            "G.GG............G.G.",
            ".G..GG........G.G.G.",
            ".G.G.........G.G..G.",
            "............G..G.G..",
    };

    private static final String[] RUN_4 = {
            ".......OO..OO.......",
            "..OOO.OWYOOWYO.OOO..",
            ".OENEOOWWOOWWOOENEO.",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEO..G..G..OEEEO.",
            "..OEO.OOOOOOOO.OEO..",
            "..OEOOTTBBBBBBOOEO..",
            "...OTTBBBBBBBBBBO...",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            ".G.GOOOOOOOOOOOOG..G",
            ".G.G.G........G..G.G",
            ".G..G.G........G..G.",
            "..G.G..G........G..G",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".......OO..OO.......",
            "..OOO.OWYOOWYO.OOO..",
            ".OENEOOWWOOWWOOENEO.",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEOOOOOOOOEEEEEO",
            ".OEEEOTTBBBBBBOEEEO.",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABOEO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..G.",
            "..G.G..........G.G..",
    };

    private static final String[] SIT_SNAP = {
            "....................",
            "....................",
            "....................",
            "..............O...O.",
            ".......OO..OOONO.ONO",
            "..OOO.OWYOOWYOEO.OEO",
            ".OENEOOWWOOWWOEEOEEO",
            "OEEOEEOOO..OOOEEEEEO",
            "OEEEEEOOOOOOOOOEEEO.",
            ".OEEEOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..G.",
            "..G.G..........G.G..",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "..OOO..OO..OO..OOO..",
            ".OENEOOOOOOOOOOENEO.",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEOOOOOOOOEEEEEO",
            ".OEEEOTTBBBBBBOEEEO.",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABOEO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..G.",
            "..G.G..........G.G..",
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
            ".......OO..OO.......",
            "......OGGOOGGO......",
            "....OOTTBBBBBBOO....",
            "..OOTBBBBBBBBBBBOO..",
            ".OEOBBBABBBBABBBOEO.",
            "OEEEOBBBBBBBBBBOEEEO",
            "ONEEOLLLLNNLLLLOEENO",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            ".......OO..OO.......",
            "......OGGOOGGO......",
            "....OOTTBBBBBBOO....",
            "..OOTBBBBBBBBBBBOO..",
            "..OBBBBBBBBBBBBBBO..",
            ".OEOBBBABBBBABBBOEO.",
            "OEEEOBBBBBBBBBBOEEEO",
            "ONEEOLLLLNNLLLLOEENO",
            ".OOOOOOOOOOOOOOOOOO.",
    };

    private static final String[] JUMP_A = {
            ".O...O.OO..OO.O...O.",
            "ONO.ONOWYOOWYONO.ONO",
            "OEO.OEOWWOOWWOEO.OEO",
            "OEEOEEOOO..OOOEEOEEO",
            "OEEEEEO.G..G.OEEEEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "GGG..G........G..GGG",
            "...GG..........GG...",
            "....................",
            "....................",
    };

    private static final String[] JUMP_B = {
            "O.....OOO..OOO.....O",
            "NO...ONWYOOWYNO...ON",
            "EO...OEWWOOWWEO...OE",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            ".OEEEOOOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "G..GOOOOOOOOOOOOG..G",
            "GGG..G........G..GGG",
            "...GG..........GG...",
            "....................",
            "....................",
    };

    private static final String[] FALL_A = {
            ".......OO..OOO.....O",
            ".O...OOWWOOWWNO...ON",
            "ONO.ONOWYOOYWEO...OE",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEO.G..G..OEEEO.",
            ".OEEEOOOOOOOOO.OEO..",
            "..OEOOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBBO...",
            "..OTBBBABBBBBABBBO.G",
            ".OBBBBBBBBBBBBBBBBOG",
            ".GOABLLLLNNLLLLBAOG.",
            "G.GGOOOOOOOOOOOOG...",
            "G....G........G..G..",
            ".G..G........G....G.",
            "...G.........G......",
    };

    private static final String[] FALL_B = {
            "O.....OOO..OO.......",
            "NO...ONWWOOWWOO...O.",
            "EO...OEWYOOYWONO.ONO",
            "OEO.OEOOO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            ".OEEEO..G..G.OEEEEEO",
            "..OEO.OOOOOOOOOEEEO.",
            "..OEOOTTBBBBBBOOEO..",
            "...OTTBBBBBBBBBOEO..",
            "G.OTBBBABBBBBABBBO..",
            "GOBBBBBBBBBBBBBBBBO.",
            ".GOABLLLLNNLLLLBAOG.",
            "...GOOOOOOOOOOOOGG.G",
            "..G..G........G....G",
            ".G....G........G..G.",
            "......G.........G...",
    };

    private static final String[] HELD_A = {
            ".......OO..OOO.....O",
            "......OWWOOWWNO...ON",
            "..OOO.OWYOOYWEO...OE",
            ".OENEO.OO..OOOEO.OEO",
            "OEEOEEO.G..G.OEEOEEO",
            "OEEEEEOOOOOOOOOEEEO.",
            ".OEEEOTTBBBBBBOOEO..",
            "..OEOTBBBBBBBBBOEO..",
            "..OEOBBABBBBBABBBO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..G.",
            ".G.G............G.G.",
            ".G.G.G........G.G.G.",
            ".G.G.G........G.G.G.",
            "....................",
    };

    private static final String[] HELD_B = {
            "O.....OOO..OO.......",
            "NO...ONWWOOWWO......",
            "EO...OEWYOOYWO.OOO..",
            "OEO.OEOOO..OO.OENEO.",
            "OEEOEEO.G..G.OEEOEEO",
            ".OEEEOOOOOOOOOEEEEEO",
            "..OEOOTTBBBBBBOEEEO.",
            "..OEOTBBBBBBBBBOEO..",
            "..OTBBBABBBBBABOEO..",
            ".OBBBBBBBBBBBBBBBBO.",
            "..OABLLLLNNLLLLBAO..",
            ".G..OOOOOOOOOOOO..G.",
            ".G.G............G.G.",
            ".G.G.G........G.G.G.",
            ".G.G.G........G.G.G.",
            "....................",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 3.0, STAND_A, STAND_SNAP, STAND_A, STAND_SNAP, STAND_A, STAND_LOOK, STAND_TWITCH,
                    STAND_BLINK, STAND_A, STAND_SNAP_L, STAND_A, STAND_BOTH, STAND_A, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_SNAP, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.0, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 10, JUMP_A, JUMP_B)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Red", new Color(0xD8442E), new Color(0xF6D9B8), new Color(0xEE7E5E),
                        new Color(0xEC5A3A), new Color(0x3A0E0A), new Color(0x141418), new Color(0x7A1A12),
                        new Color(0xB0301E), new Color(0xFF9C7E)),
                new PetKind.Palette("Blue", new Color(0x4A7FB8), new Color(0xF2EEE0), new Color(0x8FB6DE),
                        new Color(0x5C93D0), new Color(0x14243A), new Color(0x141418), new Color(0xE0503A),
                        new Color(0x335F8E), new Color(0x9CC4EE)),
                new PetKind.Palette("Ghost", new Color(0xE6D2A6), new Color(0xFFF6E2), new Color(0xD8C08E),
                        new Color(0xEEDDB4), new Color(0x6A5634), new Color(0x141418), new Color(0x8A6E44),
                        new Color(0xC8AE7A), new Color(0xFFF4D6)),
                new PetKind.Palette("Purple", new Color(0x7E4FA8), new Color(0xEADCF2), new Color(0xB089D4),
                        new Color(0x9462C2), new Color(0x241434), new Color(0x141418), new Color(0xF0A040),
                        new Color(0x613A86), new Color(0xC4A0E6)));
    }
}
