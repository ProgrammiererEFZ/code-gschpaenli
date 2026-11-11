// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/** Original pixel art: hedgehog. */
final class PixelHedgehog {
    private PixelHedgehog() {}

    private static final String[] STAND_A = {
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO....OGGO....",
    };

    private static final String[] STAND_B = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBBBTBBABBBBOEO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO....OGGO....",
    };

    private static final String[] STAND_C = {
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLLLLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO....OGGO....",
    };

    private static final String[] WALK_A = {
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "...OGGO......OGGO...",
    };

    private static final String[] WALK_B = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            ".....OGGO..OGGO.....",
            ".....OGGO..OGGO.....",
    };

    private static final String[] WALK_C = {
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            ".......OGGOGGO......",
    };

    private static final String[] WALK_D = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            ".....OGGO..OGGO.....",
            ".....OGGO..OGGO.....",
    };

    private static final String[] RUN_A = {
            "....................",
            "...O...O...O........",
            "...OTOOOTOOOTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOOO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            ".OGGO.........OGGO..",
    };

    private static final String[] RUN_B = {
            "...O...O...O........",
            "...OTOOOTOOOTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOOO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO....OGGO....",
            "....OGGO....OGGO....",
    };

    private static final String[] RUN_C = {
            "....................",
            "...O...O...O........",
            "...OTOOOTOOOTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOOO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "......OGGOOGGO......",
    };

    private static final String[] RUN_D = {
            "...O...O...O........",
            "...OTOOOTOOOTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOOO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO....OGGO....",
            "....OGGO....OGGO....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLYWLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLGGLO..",
            "....OOOOOOOOOOOOO...",
    };

    private static final String[] SIT_B = {
            "....................",
            "....................",
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLLLLLO",
            ".OTBBABBABBBBOLYYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLGGLO..",
            "....OOOOOOOOOOOOO...",
    };

    private static final String[] SLEEP_A = {
            "....................",
            ".......O..O.........",
            ".....OOTOOTO.O......",
            "....OTOBTTBTOTO.....",
            "...OTBTABBBBTATO....",
            "..OOTABBBBBABBAOO...",
            ".OTTBBBBBABBABBTTO..",
            "..OTBBBABBABBBBBTO..",
            ".OTBBABBABBBLLLLO...",
            "..OTBBABBBLLYYLLNO..",
            "..OTABBBBBLLLPLLO...",
            "...OOTBBAGGLLLLO....",
            ".....OOBBALLOOO.....",
            ".......OOOOO........",
    };

    private static final String[] SLEEP_B = {
            ".......O..O.........",
            ".....OOTOOTO.O......",
            "....OTOBTTBTOTO.....",
            "...OTBTABBBBTATO....",
            "..OOTABBBBBABBAOO...",
            ".OTBBABBBBBABBABTO..",
            ".OTTBBBBBABBABBTTO..",
            "..OTBBBABBABBBBBTO..",
            ".OTBBABBABBBLLLLO...",
            "..OTBBABBBLLYYLLNO..",
            "..OTABBBBBLLLPLLO...",
            "...OOTBBAGGLLLLO....",
            ".....OOBBALLOOO.....",
            ".......OOOOO........",
    };

    private static final String[] JUMP_A = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLWYLLO",
            ".OTBBABBABBBBOLWYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "..OGGO........OGGO..",
            "..OGGO........OGGO..",
    };

    private static final String[] FALL_A = {
            "......O...O...O.....",
            "...O.OTO.OTO.OTO....",
            "..OTO.OTOOTOOTO.....",
            "...OTOTBTTBTTTO.....",
            "..OOTBBABBBBBATOO...",
            ".OTTBABBBBBABBATTO..",
            ".OOTBBBBBABBABBBOO..",
            "OTTBBBBABBABBBBBTTO.",
            ".OOBBABBABBBLWYLOO..",
            ".OTABBABBBLLLWYLNO..",
            "OTTBABBBBLLLLPNOO...",
            "OGGOOTBBALLLLLOGGO..",
            ".OO..OTBBALLOO.OO...",
            "......OOOOOO........",
    };

    private static final String[] FALL_B = {
            "........O...O.......",
            ".....O.OTO.OTO.O....",
            "...OOTOOTOOTO.OTO...",
            "..OTTOOBBTBBTOTO....",
            "...OTBTABBBBBTTOO...",
            ".OOOTABBBBBABBATTO..",
            "OTTTBBBBBABBABBBTO..",
            ".OOTBBBABBABBBBBTO..",
            "OTTBBABBABBBLWYLO...",
            ".OOTBBABBBLLLWYLNO..",
            ".OTBABBBBLLLLPNOO...",
            ".OGGOTBBALLLLLOGOO..",
            "..OOOTOBBALLOO.OGGO.",
            ".....O.OOOOO....OO..",
    };

    private static final String[] HELD_A = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLWYLLO",
            ".OTBBABBABBBBOLWYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "..OGGO......OGGO....",
            "..OGGO.......OO.....",
    };

    private static final String[] HELD_B = {
            "....O...O...O.......",
            "...OTO.OTO.OTOO.....",
            "..OBBBOBBBOBBBBOO...",
            ".OTBABTABBTBBAOEO...",
            "OTABBABBBBBABBOLLO..",
            ".OTABBBBBABBABOLLLO.",
            "OTBBBBBABBABBOLWYLLO",
            ".OTBBABBABBBBOLWYLLN",
            "OTBABBABBBBBABOLPLO.",
            ".OBBABBBBBABBOLLLLO.",
            "..OOTBBBABBAOLLLLO..",
            "....OOOOOOOOOOOOO...",
            "....OGGO.....OGGO...",
            ".....OO......OGGO...",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_B, STAND_A, STAND_C)
            .anim(Pose.WALK, 0, WALK_A, WALK_B, WALK_C, WALK_D)
            .anim(Pose.RUN, 0, RUN_A, RUN_B, RUN_C, RUN_D)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_A, SIT_B)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 4, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Brown", new Color(0x8A6242), new Color(0xF0D9B5), new Color(0xC99784),
                        new Color(0xE8A0A0), new Color(0x2A1C14), new Color(0x17110E), new Color(0x2A1C14),
                        new Color(0x5E3F2A), new Color(0xD9B890)),
                new PetKind.Palette("Albino", new Color(0xDCCBB0), new Color(0xFFF4EA), new Color(0xF0B4AE),
                        new Color(0xF4A3AA), new Color(0x5A4640), new Color(0xB0283C), new Color(0xD9707E),
                        new Color(0xB5A083), new Color(0xFFFBF2)),
                new PetKind.Palette("Dark", new Color(0x4C4752), new Color(0xD9CFC2), new Color(0xA88C88),
                        new Color(0xC98C8C), new Color(0x141116), new Color(0x0D0B0E), new Color(0x141116),
                        new Color(0x2F2B35), new Color(0xC2BBC8)),
                new PetKind.Palette("Cinnamon", new Color(0xB8764A), new Color(0xFBE6CC), new Color(0xD9A08A),
                        new Color(0xEFA3A0), new Color(0x3A2014), new Color(0x1A0F0A), new Color(0x3A2014),
                        new Color(0x8A5232), new Color(0xF2C79B)));
    }
}
