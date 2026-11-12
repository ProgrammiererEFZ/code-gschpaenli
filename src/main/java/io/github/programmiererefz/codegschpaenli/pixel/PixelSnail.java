// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: snail. Palette slots: B shell, A shell spiral, T shell shine, L sealed shell lip and head
 * highlight, G soft body and eye stalks, E foot fringe, Y eyes, N slime trail.
 */
final class PixelSnail {
    private PixelSnail() {}

    private static final String[] STAND_A = {
            "............OO..OO..",
            ".....OOOO..OGGOOGGO.",
            "...OOTTBBOOOGYOOGYO.",
            "..OTBAAAABBOG...G...",
            "..OBABBBBABOG...G...",
            ".OBABBAABBABOG..G...",
            ".OBABATBABABOG.G....",
            ".OBABAABABABOOOO....",
            ".OBBABBBABABOLLGO...",
            "..OBBAAABBAOOGGGGO..",
            "...OOBBBBOOGOGGGGGO.",
            "...OGGGGGGGGGGGGGO..",
            "..OEEEEEEEEEEEEEEO..",
            "...OOOOOOOOOOOOOO...",
    };

    private static final String[] STAND_BLINK = {
            "............OO..OO..",
            ".....OOOO..OGOOOGOO.",
            "...OOTTBBOOOGGOOGGO.",
            "..OTBAAAABBOG...G...",
            "..OBABBBBABOG...G...",
            ".OBABBAABBABOG..G...",
            ".OBABATBABABOG.G....",
            ".OBABAABABABOOOO....",
            ".OBBABBBABABOLLGO...",
            "..OBBAAABBAOOGGGGO..",
            "...OOBBBBOOGOGGGGGO.",
            "...OGGGGGGGGGGGGGO..",
            "..OEEEEEEEEEEEEEEO..",
            "...OOOOOOOOOOOOOO...",
    };

    private static final String[] STAND_WAVE = {
            ".............OO.....",
            ".....OOOO...OGGO.OO.",
            "...OOTTBBOO.OGYOOGGO",
            "..OTBAAAABBO.G..OGYO",
            "..OBABBBBABO.G...G..",
            ".OBABBAABBABOG..G...",
            ".OBABATBABABOG.G....",
            ".OBABAABABABOOOO....",
            ".OBBABBBABABOLLGO...",
            "..OBBAAABBAOOGGGGO..",
            "...OOBBBBOOGOGGGGGO.",
            "...OGGGGGGGGGGGGGO..",
            "..OEEEEEEEEEEEEEEO..",
            "...OOOOOOOOOOOOOO...",
    };

    private static final String[] STAND_WAVE2 = {
            "...........OO...OO..",
            ".....OOOO.OGGO.OGGO.",
            "...OOTTBBOOGYO.OGYO.",
            "..OTBAAAABBOG...G...",
            "..OBABBBBABOG..G....",
            ".OBABBAABBABO..G....",
            ".OBABATBABABOG.G....",
            ".OBABAABABABOOOO....",
            ".OBBABBBABABOLLGO...",
            "..OBBAAABBAOOGGGGO..",
            "...OOBBBBOOGOGGGGGO.",
            "...OGGGGGGGGGGGGGO..",
            "..OEEEEEEEEEEEEEEO..",
            "...OOOOOOOOOOOOOO...",
    };

    private static final String[] WALK_1 = {
            "............OO..OO..",
            ".....OOOO..OGGOOGGO.",
            "...OOTTBBOOOGYOOGYO.",
            "..OTBAAAABBOG...G...",
            "..OBABBBBABOG...G...",
            ".OBABBAABBABOG..G...",
            ".OBABATBABABOG.G....",
            ".OBABAABABABOOOO....",
            ".OBBABBBABABOLLGO...",
            "..OBBAAABBAOOGGGGO..",
            "...OOBBBBOOGOGGGGGO.",
            "...OGGGGGGGGGGGGGO..",
            "..OEEEEEEEEEEEEEEO..",
            "N..OOOOOOOOOOOOOO...",
    };

    private static final String[] WALK_2 = {
            "....................",
            ".....OOOO......OO.OO",
            "...OOTTBBOO...OGGOGG",
            "..OTBAAAABBO..OGYOGY",
            "..OBABBBBABO...G..G.",
            ".OBABBAABBABO..G.G..",
            ".OBABATBABABO.G.G...",
            ".OBABAABABABO.OOO...",
            ".OBBABBBABABOOLLGO..",
            "..OBBAAABBAO.OGGGGO.",
            "...OOBBBBOOGGOGGGGGO",
            "...OGGGGGGGGGGGGGGO.",
            "..OEEEEEEEEEEEEEEEO.",
            "NN.OOOOOOOOOOOOOOO..",
    };

    private static final String[] WALK_3 = {
            "....................",
            "......OOOO.....OO.OO",
            "....OOTTBBOO..OGGOGG",
            "...OTBAAAABBO.OGYOGY",
            "...OBABBBBABO..G..G.",
            "..OBABBAABBABO.G.G..",
            "..OBABATBABABOG.G...",
            "..OBABAABABABOOOO...",
            "..OBBABBBABABOLLGO..",
            "...OBBAAABBAOOGGGGO.",
            "....OOBBBBOOGOGGGGGO",
            "....OGGGGGGGGGGGGGO.",
            "...OEEEEEEEEEEEEEEO.",
            "NN..OOOOOOOOOOOOOO..",
    };

    private static final String[] WALK_4 = {
            "............OO..OO..",
            "......OOOO.OGGOOGGO.",
            "....OOTTBBOOGYOOGYO.",
            "...OTBAAAABBO...G...",
            "...OBABBBBABO...G...",
            "..OBABBAABBABO..G...",
            "..OBABATBABABO.G....",
            "..OBABAABABABOOO....",
            "..OBBABBBABABOLGO...",
            "...OBBAAABBAOGGGGO..",
            "....OOBBBBOOOGGGGGO.",
            "....OGGGGGGGGGGGGO..",
            "...OEEEEEEEEEEEEEO..",
            ".N..OOOOOOOOOOOOO...",
    };

    private static final String[] RUN_1 = {
            "....................",
            ".....OOOO......OO.OO",
            "...OOTTBBOO...OGGOGG",
            "..OTBAAAABBO..OGYOGY",
            "..OBABBBBABO...G..G.",
            ".OBABBAABBABO..G.G..",
            ".OBABATBABABO.G.G...",
            ".OBABAABABABO.OOO...",
            ".OBBABBBABABOOLLGO..",
            "..OBBAAABBAO.OGGGGO.",
            "...OOBBBBOOGGOGGGGGO",
            "..OGGGGGGGGGGGGGGGO.",
            ".OEEEEEEEEEEEEEEEEO.",
            "N.OOOOOOOOOOOOOOOO..",
    };

    private static final String[] RUN_2 = {
            "....................",
            "......OOOO...OO.....",
            "....OOTTBBOOOGGO....",
            "...OTBAAAABBOGYO....",
            "NNNOBABBBBABO.G.....",
            "..OBABBAABBABO.G....",
            "..OBABATBABABOG.G...",
            "NNOBABAABABABOOOO...",
            "..OBBABBBABABOLLGO..",
            "...OBBAAABBAOOGGGGO.",
            "NNNGOOBBBBOOGOGGGGGO",
            ".OGGGGGGGGGGGGGGGGO.",
            "OEEEEEEEEEEEEEEEEEO.",
            ".OOOOOOOOOOOOOOOOO..",
    };

    private static final String[] RUN_3 = {
            "....................",
            ".......OOOO..OO.....",
            ".....OOTTBBOOGGO....",
            "....OTBAAAABBOYO....",
            "....OBABBBBABOG.....",
            "...OBABBAABBABOG....",
            "...OBABATBABABO.G...",
            "...OBABAABABABOOO...",
            "...OBBABBBABABOLGO..",
            "....OBBAAABBAOGGGGO.",
            ".....OOBBBBOOOGGGGGO",
            "....OGGGGGGGGGGGGGO.",
            "...OEEEEEEEEEEEEEEO.",
            "NNN.OOOOOOOOOOOOOO..",
    };

    private static final String[] RUN_4 = {
            "....................",
            ".......OOOO...OO.OO.",
            ".....OOTTBBOOOGGOGGO",
            "....OTBAAAABBOGYOGYO",
            "....OBABBBBABOG..G..",
            "...OBABBAABBABO.G...",
            "...OBABATBABABOG....",
            "...OBABAABABABOO....",
            "...OBBABBBABABOGO...",
            "....OBBAAABBAOGGGO..",
            ".....OOBBBBOOGGGGGO.",
            "....OGGGGGGGGGGGGO..",
            "...OEEEEEEEEEEEEEO..",
            ".NN.OOOOOOOOOOOOO...",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            ".....OOOO...........",
            "...OOTTBBOO.........",
            "..OTBAAAABBO.OO.OO..",
            "..OBABBBBABOOGGOGGO.",
            ".OBABBAABBABOGYOGYO.",
            ".OBABATBABABOG..G...",
            ".OBABAABABABOG..G...",
            ".OBBABBBABABOOOOO...",
            "..OBBAAABBAOOLLGGGO.",
            "...OOGGGGGGGGGGGGGO.",
            "...OEEEEEEEEEEEEEEO.",
            "....OOOOOOOOOOOOOO..",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            ".....OOOO...........",
            "...OOTTBBOO.........",
            "..OTBAAAABBO.OO.OO..",
            "..OBABBBBABOOGOOGOO.",
            ".OBABBAABBABOGGOGGO.",
            ".OBABATBABABOG..G...",
            ".OBABAABABABOG..G...",
            ".OBBABBBABABOOOOO...",
            "..OBBAAABBAOOLLGGGO.",
            "...OOGGGGGGGGGGGGGO.",
            "...OEEEEEEEEEEEEEEO.",
            "....OOOOOOOOOOOOOO..",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            ".......OOOO.........",
            ".....OOTTBBOO.......",
            "....OTBAAAABBO......",
            "....OBABBBBABO......",
            "...OBABBAABBABO.....",
            "...OBABATBABABO.....",
            "...OBABAABABABO.....",
            "...OBBABBBABABO.....",
            "....OBBAAABBAO......",
            ".....OLLLLLLO.......",
            "......OOOOOO........",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            ".......OOOO.........",
            ".....OOTTBBOO.......",
            "....OTBAAAABBO......",
            "....OBABBBBABO......",
            "...OBABBAABBABO.....",
            "...OBABATBABABO.....",
            "...OBABAABABABO.....",
            "...OBBABBBABABOO....",
            "....OBBAAABBAOONO...",
            ".....OLLLLLLO..O....",
            "......OOOOOO........",
    };

    private static final String[] JUMP_A = {
            "....................",
            "....................",
            ".......OOOO.........",
            ".....OOTTBBOO.......",
            "....OTBAAAABBO......",
            "....OBABBBBABO......",
            "...OBABBAABBABOOO...",
            "...OBABATBABABOGGO..",
            "...OBABAABABABOGYO..",
            "...OBBABBBABABOG....",
            "....OBBAAABBAOG.....",
            ".....OLLLLLLO.......",
            "......OOOOOO........",
            "....................",
    };

    private static final String[] FALL_A = {
            "....................",
            "....................",
            "........OOOO........",
            "......OOTTBBOO......",
            ".....OTBAAAABBO.....",
            ".....OBABBBBABO.....",
            "....OBABBAABBABO....",
            "....OBABATBABABO....",
            "....OBABAABABABO....",
            "....OBBABBBABABO....",
            ".....OBBAAABBAO.....",
            "......OLLLLLLO......",
            ".......OOOOOO.......",
            "....................",
    };

    private static final String[] FALL_B = {
            "....................",
            ".......OOOO.........",
            "......OBBBBOO.......",
            ".....OBBAAABTO......",
            "....OLBABBBABO......",
            "....OLABAABBATO.....",
            "....OLABATABATO.....",
            "....OLABBBABABO.....",
            "....OLBAAABBABO.....",
            "....OLBBBBBABO......",
            ".....OAAAAABBO......",
            "......OBBBBOO.......",
            ".......OOOO.........",
            "....................",
    };

    private static final String[] HELD_A = {
            "....OOOO............",
            "..OOTTBBOO..........",
            ".OTBAAAABBO.........",
            ".OBABBBBABO.........",
            "OBABBAABBABO........",
            "OBABATBABABO........",
            "OBABAABABABO.OO.....",
            "OBBABBBABABOOGGOOO..",
            ".OBBAAABBAOGOGYOGGO.",
            "..OOBBBBOOOGGOGOGYO.",
            "....OOOO..OLGGG.G...",
            "...........OGGGG....",
            "............OGGGGO..",
            ".............OOOO...",
    };

    private static final String[] HELD_B = {
            "....OOOO............",
            "..OOTTBBOO..........",
            ".OTBAAAABBO.........",
            ".OBABBBBABO.........",
            "OBABBAABBABO........",
            "OBABATBABABO..OO....",
            "OBABAABABABO.OGGO...",
            "OBBABBBABABO.OGYO...",
            ".OBBAAABBAOGO.G.....",
            "..OOBBBBOOOGGOG.....",
            "....OOOO..OLGGG..OO.",
            "...........OGGGGOGGO",
            "............OGGGOGYO",
            ".............OOOO...",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_WAVE, STAND_WAVE2, STAND_A, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.0, SIT_A, SIT_A, SIT_A, SIT_BLINK)
            .anim(Pose.SLEEP, 0.8, SLEEP_A, SLEEP_A, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        // Palette(name, body=B shell, belly=L lip, leg=G soft body, ear=E foot fringe, outline=O, eye=Y,
        //         nose=N slime, accent=A spiral, tailTip=T shine)
        return List.of(
                new PetKind.Palette("Garden", new Color(0xB07A45), new Color(0xE9D9B6), new Color(0xB9AA92),
                        new Color(0x8E7F6A), new Color(0x2E1F12), new Color(0x161210), new Color(0x8FB8C4),
                        new Color(0x6A4222), new Color(0xE0B27A)),
                new PetKind.Palette("Banded", new Color(0xEACB55), new Color(0xF6EBC2), new Color(0xC9B99A),
                        new Color(0x9E8F74), new Color(0x3A2A12), new Color(0x161210), new Color(0x9CC3CC),
                        new Color(0x5A3A1C), new Color(0xFFF0A6)),
                new PetKind.Palette("Pink", new Color(0xE58FAE), new Color(0xFBE0EA), new Color(0xD9C7CF),
                        new Color(0xB59AA6), new Color(0x4A1E30), new Color(0x1E1418), new Color(0xB9D8E6),
                        new Color(0xA8476E), new Color(0xFFC6D8)),
                new PetKind.Palette("Blue-grey", new Color(0x7F93A6), new Color(0xDCE5EC), new Color(0xA8ADB4),
                        new Color(0x81878F), new Color(0x1E2832), new Color(0x101418), new Color(0xA8D6E0),
                        new Color(0x47596C), new Color(0xC7D6E2)));
    }
}