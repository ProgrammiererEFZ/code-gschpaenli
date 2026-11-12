// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: ladybug. Palette slots: B shell, A spots, T shell shine, E head, L pronotum patches,
 * G legs and antennae, N wing membrane.
 */
final class PixelLadybug {
    private PixelLadybug() {}

    private static final String[] STAND_A = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G.E..G.E..G.E....",
    };

    private static final String[] STAND_TWITCH = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...GG.",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G.E..G.E..G.E....",
    };

    private static final String[] STAND_PERK = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO..G..G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G.E..G.E..G.E....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEOOO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G.E..G.E..G.E....",
    };

    private static final String[] WALK_1 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G...E.G.G...E....",
    };

    private static final String[] WALK_2 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...GG.",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "....G.E.G.E..GE.....",
    };

    private static final String[] WALK_3 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            ".....G.G...E.G......",
    };

    private static final String[] WALK_4 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...GG.",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G..E.G.E.G.E.....",
    };

    private static final String[] RUN_1 = {
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO.G.G..",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G...E.G..G..E....",
            "..G.....E.GG....E...",
    };

    private static final String[] RUN_2 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO.G.G..",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "....G.E.G.E..GE.....",
    };

    private static final String[] RUN_3 = {
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO.G.G..",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            ".....G.G...E.GE.....",
            "....E.G.....EEG.....",
    };

    private static final String[] RUN_4 = {
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO.G.G..",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "...G..E.G.E.G.E.....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "...GG...GG...GG.....",
    };

    private static final String[] SIT_TWITCH = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...GG.",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "...GG...GG...GG.....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEOOO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "...GG...GG...GG.....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO......",
            "..OTBBBBBAABBBO.GGG.",
            ".OTBAABBBBBBBBOOOOOG",
            ".OBBAABBBBAABBOLEOOO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO......",
            "..OTBBBBBAABBBO.....",
            ".OTBAABBBBBBBBO.GGGG",
            ".OBBAABBBBAABBOOOOO.",
            ".OBBBBBBBBAABBOLEOOO",
            ".OBBBBBBBBAABBOLEEEO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
    };

    private static final String[] JUMP_A = {
            ".NNN................",
            "NNNNN.O.............",
            "NLNNNNTOOOOO........",
            ".NLNNNNTBBBBOO......",
            "..NLLNNNBAABBO......",
            ".OTBNNNNBAABBBO..G.G",
            ".OBBAABBBBBBBBO.G.G.",
            ".OBBBBBBBBAABBOOOOO.",
            "..OBBBABBBAABBOLEWYO",
            "..OOOOOABBBBBBOLEEEO",
            "..OEEEEOOOOOOOOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "....G.E.G.E..G.E....",
    };

    private static final String[] JUMP_B = {
            "....................",
            "......O.............",
            "....OOTOOOOO........",
            "...OTTBTBBBBOO......",
            "..OTBBBBBAABBO......",
            ".OTBAABBBAABBBO..G.G",
            ".OBBAABBBBBBBBO.G.G.",
            ".OBBBBNNBBAABBOOOOO.",
            "..OBNNNNNBAABBOLEWYO",
            "..NNNNNNNBBBBBOLEEEO",
            "NNNLLLNNOOOOOOOEEENO",
            "NNNNNNOOOOOOOOOOOOO.",
            ".NNNG.E.G.E.G.E.....",
            "...G.E..G.E.G.E.....",
    };

    private static final String[] FALL_A = {
            "....................",
            "...G.......G........",
            "....G...G...G.......",
            ".....G...G...G......",
            ".....G...G...G......",
            "...OOOOOOOOOOOOOOOO.",
            "..OBBBAABBBBBBOEEENO",
            ".OBBBBBBBBAABBOLEWWO",
            ".OBBAABBBBAABBOLEWYO",
            ".OTBAABBBBBBBBOOOOOG",
            "..OTBBBBBAABBBO.GGG.",
            "...OTTBBBAABBO......",
            "....OOTTBBBBOO......",
            "......OOOOOO........",
    };

    private static final String[] FALL_B = {
            "....................",
            ".......G.......G....",
            "......G...G...G.....",
            ".....G...G...G......",
            ".....G...G...G......",
            "...OOOOOOOOOOOOOOOO.",
            "..OBBBAABBBBBBOEEENO",
            ".OBBBBBBBBAABBOLEWWO",
            ".OBBAABBBBAABBOLEWYO",
            ".OTBAABBBBBBBBOOOOO.",
            "..OTBBBBBAABBBO.GGGG",
            "...OTTBBBAABBO......",
            "....OOTTBBBBOO......",
            "......OOOOOO........",
    };

    private static final String[] HELD_A = {
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO..G..G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEWWO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "....G.E.G.E.G.E.....",
            "...G...E.G..G..E....",
    };

    private static final String[] HELD_B = {
            "....................",
            "....................",
            "......OOOOOO........",
            "....OOTTBBBBOO......",
            "...OTTBBBAABBO...G.G",
            "..OTBBBBBAABBBO.G.G.",
            ".OTBAABBBBBBBBOOOOO.",
            ".OBBAABBBBAABBOLEWYO",
            ".OBBBBBBBBAABBOLEWWO",
            "..OBBBAABBBBBBOEEENO",
            "...OOOOOOOOOOOOOOOO.",
            "....G.E.G.E.G.E.....",
            "....G.E.G.E.G.E.....",
            ".....G.G...E.G......",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_TWITCH, STAND_A, STAND_PERK, STAND_A, STAND_A, STAND_BLINK, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_TWITCH, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.0, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 14, JUMP_A, JUMP_B)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Red", new Color(0xE0342C), new Color(0xF4EEE4), new Color(0x6E6876),
                        new Color(0x3A3540), new Color(0x16121A), new Color(0x141418), new Color(0xC9CED8),
                        new Color(0x1C181F), new Color(0xFF8A70)),
                new PetKind.Palette("Yellow", new Color(0xF2C230), new Color(0xF8F2E0), new Color(0x6E6876),
                        new Color(0x3A3540), new Color(0x2A1E0C), new Color(0x141418), new Color(0xD6D2C4),
                        new Color(0x2A2420), new Color(0xFFF0A0)),
                new PetKind.Palette("Orange", new Color(0xF07A22), new Color(0xF8EEDC), new Color(0x6E6876),
                        new Color(0x3A3540), new Color(0x2A140A), new Color(0x141418), new Color(0xD2CCC4),
                        new Color(0x2A1C18), new Color(0xFFC08A)),
                new PetKind.Palette("Black", new Color(0x2E2A33), new Color(0xF4EEE4), new Color(0x6E6876),
                        new Color(0x3A3540), new Color(0x0E0C10), new Color(0x141418), new Color(0xC9CED8),
                        new Color(0xE0342C), new Color(0x6A6475)));
    }
}
