// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: a chubby, fuzzy bee. Palette slots: B fur and stripes, A dark bands and head, E tail,
 * L wing membrane, T wing edge, G legs and antennae, N stinger, Y eye.
 */
final class PixelBee {
    private PixelBee() {}

    private static final String[] STAND_A = {
            "....TTTT............",
            "...TLLLLT.TTT....G.G",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G.O..G.O...G..O..",
    };

    private static final String[] STAND_B = {
            "....................",
            ".................G.G",
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G.O..G.O...G..O..",
    };

    private static final String[] STAND_TWITCH = {
            "....TTTT............",
            "...TLLLLT.TTT.....GG",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G.O..G.O...G..O..",
    };

    private static final String[] STAND_PERK = {
            "................G.G.",
            "................G.G.",
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G.O..G.O...G..O..",
    };

    private static final String[] STAND_BLINK = {
            "....TTTT............",
            "...TLLLLT.TTT....G.G",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAAAO",
            ".OEEEAABBAABBBOAATTO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G.O..G.O...G..O..",
    };

    private static final String[] WALK_1 = {
            "....................",
            ".................G.G",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...G..O.G..O...G.O..",
    };

    private static final String[] WALK_2 = {
            "....................",
            "..................GG",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....GO...GO...GO....",
    };

    private static final String[] WALK_3 = {
            "....................",
            ".................G.G",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "...O.G..O.G...O.G...",
    };

    private static final String[] WALK_4 = {
            "....................",
            "..................GG",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....OG...OG...OG....",
    };

    private static final String[] RUN_1 = {
            "...TLLLLT.TTT...G.G.",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "TT.OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".TT..OOOOOOOO..OOO..",
            "...G.O..G.O..G.O....",
            "..G.O..G.O..G.O.....",
            "....................",
    };

    private static final String[] RUN_2 = {
            "................G.G.",
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "T..OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            "TT...OOOOOOOO..OOO..",
            "...G.O..G.O..G.O....",
            "..G.O..G.O..G.O.....",
            "....................",
    };

    private static final String[] RUN_3 = {
            "...TLLLLT.TTT...G.G.",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "TT.OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".TT..OOOOOOOO..OOO..",
            "...G.O..G.O..G.O....",
            "..G.O..G.O..G.O.....",
            "....................",
    };

    private static final String[] RUN_4 = {
            "................G.G.",
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "T..OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            "TT...OOOOOOOO..OOO..",
            "...G.O..G.O..G.O....",
            "..G.O..G.O..G.O.....",
            "....................",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            ".................G.G",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....GO...GO...GO....",
    };

    private static final String[] SIT_TWITCH = {
            "....................",
            "....................",
            "..................GG",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....GO...GO...GO....",
    };

    private static final String[] SIT_BUZZ = {
            "....................",
            "....TTTT............",
            "...TLLLLT.TTT....G.G",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....GO...GO...GO....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            ".................G.G",
            "................G.G.",
            "...TTTTTTTT.....GG..",
            "..TLLLLLLLLTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAAAO",
            ".OEEEAABBAABBBOAATTO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....GO...GO...GO....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "...TTTTTTTT.....GGG.",
            "..TLLLLLLLLTT...G..G",
            "...OOAABBAABBO.OOO..",
            "..OEEAABBAABBBOAAAO.",
            "NOEEEAABBAABBBOAAAAO",
            ".OEEEAABBAABBBOAAAAO",
            "..OEEAABBAABBBOAATTO",
            "...OOAABBAABBOOAPAAO",
            ".....OOOOOOOO.OAAAO.",
            "....GO...GO....OOO..",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "...TTTTTTTT.........",
            "..TLLLLLLLLTT...GGG.",
            "..TLLLLLLLLTT...G..G",
            "...OOAABBAABBO.OOO..",
            "..OEEAABBAABBBOAAAO.",
            "NOEEEAABBAABBBOAAAAO",
            ".OEEEAABBAABBBOAAAAO",
            "..OEEAABBAABBBOAATTO",
            "...OOAABBAABBOOAPAAO",
            ".....OOOOOOOO.OAAAO.",
            "....GO...GO....OOO..",
    };

    private static final String[] JUMP_A = {
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....G.O..G.O..G.O...",
            "....................",
            "....................",
    };

    private static final String[] JUMP_B = {
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....G.O..G.O..G.O...",
            "....................",
            "....................",
    };

    private static final String[] FALL_A = {
            "...TLLLLT.TTT...G.G.",
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....G.O..G.O..G.O...",
            "....................",
    };

    private static final String[] FALL_B = {
            "................G.G.",
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....G.O..G.O..G.O...",
            "....................",
    };

    private static final String[] HELD_A = {
            "...TLLLLLTLLLT..G.G.",
            "....TLLLLTLLLT..GG..",
            ".....TTTTTTTT..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "....G.O..G.O..G.O...",
            "....G.O..G.O..G.O...",
            "....................",
            "....................",
    };

    private static final String[] HELD_B = {
            ".TTTTT..........G.G.",
            "TLLLLLTTTTT.....GG..",
            ".TTLLLLLLLLTO..OOO..",
            "...OOAABBAABBOOAAAO.",
            "..OEEAABBAABBBOAAAAO",
            "NOEEEAABBAABBBOAAWYO",
            ".OEEEAABBAABBBOAAYYO",
            "..OEEAABBAABBBOAPAAO",
            "...OOAABBAABBOOAAAO.",
            ".....OOOOOOOO..OOO..",
            "...G.O..G.O..G.O....",
            "..G.O..G.O..G.O.....",
            "....................",
            "....................",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 6, STAND_A, STAND_B, STAND_A, STAND_B, STAND_TWITCH, STAND_B, STAND_A, STAND_B,
                    STAND_PERK, STAND_A, STAND_B, STAND_A, STAND_BLINK, STAND_B)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 2.5, SIT_A, SIT_A, SIT_TWITCH, SIT_A, SIT_BUZZ, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 1.0, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 14, JUMP_A, JUMP_B)
            .anim(Pose.FALL, 14, FALL_A, FALL_B)
            .anim(Pose.HELD, 12, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        // name, body B, light L (wing membrane), legs G, secondary E (tail), outline O, eye Y, nose N (stinger),
        // accent A (dark bands and head), tailTip T (wing edge)
        return List.of(
                new PetKind.Palette("Bumblebee", new Color(0xF6C833), new Color(0xE6F2FA), new Color(0x7A6E74),
                        new Color(0xF7F1E3), new Color(0x15110E), new Color(0x0A0A0C), new Color(0x8A7A70),
                        new Color(0x3C3438), new Color(0x9DB8CC)),
                new PetKind.Palette("Honeybee", new Color(0xE3A33A), new Color(0xEEF2E6), new Color(0x8A6440),
                        new Color(0xB86B22), new Color(0x1E140C), new Color(0x120C08), new Color(0x9A7650),
                        new Color(0x5A3A20), new Color(0xA8B4A8)),
                new PetKind.Palette("Orange-tailed", new Color(0xF4C531), new Color(0xE8F0FA), new Color(0x7A6E74),
                        new Color(0xEE6E22), new Color(0x15110E), new Color(0x0A0A0C), new Color(0x8A6A58),
                        new Color(0x3C3438), new Color(0x9FB4CE)),
                new PetKind.Palette("Blue carpenter", new Color(0x3F78DA), new Color(0xE6DEF6), new Color(0x6E6E8A),
                        new Color(0x2A55AE), new Color(0x0E0E16), new Color(0x08080C), new Color(0x787896),
                        new Color(0x30303E), new Color(0x9C8EC8)));
    }
}
