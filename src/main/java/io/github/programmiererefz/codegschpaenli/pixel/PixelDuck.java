// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;
import java.awt.Color;
import java.util.List;

/** Original pixel art: duck (small waddling duck with orange bill and feet). */
final class PixelDuck {
    private PixelDuck() {}

    // ---- STAND ----
    private static final String[] STAND_A = {
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };
    private static final String[] STAND_TAIL = {
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "...O......OEEEENO...",
            "...OBO....OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };
    private static final String[] STAND_BLINK = {
            "....................",
            "...........OOOO.....",
            "..........OTEEEO....",
            "..........OEEOOOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };

    // ---- WALK (contact, passing, contact, passing) ----
    private static final String[] WALK_1 = {
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO...OGO....",
            ".......OGGGO.OGGGO..",
    };
    private static final String[] WALK_2 = {
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".........OGO.OGO....",
            ".........OGO.OGGO...",
            ".........OGGGO......",
    };
    private static final String[] WALK_3 = {
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "...O......OEEEENO...",
            "...OBO....OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO...OGO....",
            ".......OGGGO.OGGGO..",
    };
    private static final String[] WALK_4 = {
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO..OGO.....",
            ".......OGGO.OGO.....",
            "............OGGGO...",
    };

    // ---- RUN (contact with folded wing, airborne with wing up) ----
    private static final String[] RUN_1 = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..O.......OEEEENO...",
            "..OBO.....OEEEEO....",
            "..OBBOOOOOBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "......OGO.....OGO...",
            "......OGGGO...OGGO..",
    };
    private static final String[] RUN_2 = {
            "....................",
            "....OO.....OOOO.....",
            "....OAO...OTEYWO....",
            ".....OAO..OEEYYOOO..",
            ".....OAAO.OEEEENNO..",
            "..O...OAAOOEEEENO...",
            "..OBO.OAAAOEEEEO....",
            "..OBBOOAAABBBBBLO...",
            "...OBBBBAABBBBBLO...",
            "...OBBBBBBBBBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO...OGO....",
            "......OGO.....OGGO..",
            ".....OGGO...........",
    };
    private static final String[] RUN_3 = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..O.......OEEEENO...",
            "..OBO.....OEEEEO....",
            "..OBBOOOOOBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBAAAABBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO...OGO....",
            ".......OGGGO.OGGGO..",
    };
    private static final String[] RUN_4 = {
            "....................",
            "....OO.....OOOO.....",
            "....OAO...OTEYWO....",
            ".....OAO..OEEYYOOO..",
            ".....OAAO.OEEEENNO..",
            "..O...OAAOOEEEENO...",
            "..OBO.OAAAOEEEEO....",
            "..OBBOOAAABBBBBLO...",
            "...OBBBBAABBBBBLO...",
            "...OBBBBBBBBBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "........OGOOGO......",
            "........OGGOGGO.....",
            "....................",
    };

    // ---- SIT ----
    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OTEYWO....",
            "..........OEEYYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "...OBBAAAABBBLLLO...",
            "....OBBBBBBBLLLLO...",
            ".....OOOOOOOOGGGO...",
    };
    private static final String[] SIT_B = {
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OTEEEO....",
            "..........OEEOOOOO..",
            "..........OEEEENNO..",
            "...O......OEEEENO...",
            "...OBO....OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "...OBBAAAABBBLLLO...",
            "....OBBBBBBBLLLLO...",
            ".....OOOOOOOOGGGO...",
    };

    // ---- SLEEP (head turned back, bill tucked onto the back) ----
    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "..........OOOO......",
            ".........OEETEO.....",
            ".......OOEOOEEEO....",
            "...O..ONNEEEEEEO....",
            "...OBOOOOEEEEEEOO...",
            "...OBBBBBBBBBBBBO...",
            "...OBBAAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOOOOOOOOOO....",
    };
    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "..........OOOO......",
            ".........OEETEO.....",
            ".......OOEOOEEEO....",
            "...O..ONNEEEEEEO....",
            "...OBOOOOEEEEEEOO...",
            "...OBBBBBBBBBBBBO...",
            "...OBBBBBBBBBBBBO...",
            "...OBBAAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOOOOOOOOOO....",
    };

    // ---- JUMP ----
    private static final String[] JUMP_A = {
            "....................",
            "...........OOOO.....",
            "....OO....OTEYWO....",
            "....OAO...OEEYYOOO..",
            ".....OAO..OEEEENNO..",
            ".....OAAO.OEEEENO...",
            "...O..OAAOOEEEEO....",
            "...OBOOAAAOBBBBO....",
            "...OBBOAAABBBBBLO...",
            "...OBBBBBBBBBBBLO...",
            "...OBBBBBBBBBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGGOOGGO.....",
            "....................",
    };

    // ---- FALL (startled, wings flapping, legs splayed) ----
    private static final String[] FALL_UP = {
            "....................",
            "...........OOOO.....",
            "....OO....OEEWWO....",
            "....OAO...OEEWYOOO..",
            ".....OAO..OEEEENNO..",
            ".....OAAO.OEEEENO...",
            "...O..OAAOOEEEEO....",
            "...OBOOAAAOBBBBO....",
            "...OBBOAAABBBBBLO...",
            "...OBBBBBBBBBBBLO...",
            "...OBBBBBBBBBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            ".......OGO..OGO.....",
            "......OGGO..OGGO....",
    };
    private static final String[] FALL_DOWN = {
            "....................",
            "...........OOOO.....",
            "..........OEEWWO....",
            "..........OEEWYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OAAAAAABBLLLO...",
            "....OAAAAAOBLLLO....",
            ".....OAAOOOOOOO.....",
            "......OOGO..OGO.....",
            "......OGGO..OGGO....",
    };

    // ---- HELD (dangling, flapping, kicking) ----
    private static final String[] HELD_A = {
            "....................",
            "...........OOOO.....",
            "....OO....OEEWWO....",
            "....OAO...OEEWYOOO..",
            ".....OAO..OEEEENNO..",
            ".....OAAO.OEEEENO...",
            "...O..OAAOOEEEEO....",
            "...OBOOAAAOBBBBO....",
            "...OBBOAAABBBBBLO...",
            "...OBBBBBBBBBBBLO...",
            "...OBBBBBBBBBBLLO...",
            "....OBBBBBBBBLLLO...",
            ".....OOBBBBBLLLO....",
            ".......OOOOOOOO.....",
            "........OGO.OGO.....",
            "........OGGOOGGO....",
    };
    private static final String[] HELD_B = {
            "....................",
            "...........OOOO.....",
            "..........OEEWWO....",
            "..........OEEWYOOO..",
            "..........OEEEENNO..",
            "..........OEEEENO...",
            "...O......OEEEEO....",
            "...OBO...OBBBBBO....",
            "...OBBOOOBBBBBBLO...",
            "...OBBBAAAABBBBLO...",
            "...OBAAAAAABBBLLO...",
            "....OAAAAAABBLLLO...",
            "....OAAAAAOBLLLO....",
            ".....OAAOOOOOOO.....",
            ".......OGO.OGO......",
            ".......OGGOOGGO.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_TAIL, STAND_A, STAND_A, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.0, SIT_A, SIT_B)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 4, JUMP_A)
            .anim(Pose.FALL, 6, FALL_UP, FALL_DOWN)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                // name, body, belly, leg, ear (head), outline, eye, nose (bill), accent (wing), tailTip (highlight)
                new PetKind.Palette("Duckling", new Color(0xFFD84A), new Color(0xFFF0A0), new Color(0xFF9B26),
                        new Color(0xFFD84A), new Color(0x4B3412), new Color(0x1F1A17), new Color(0xF57F17),
                        new Color(0xE9B52A), new Color(0xFFF7CF)),
                new PetKind.Palette("White", new Color(0xF3F1EC), new Color(0xFFFFFF), new Color(0xFF9A35),
                        new Color(0xF3F1EC), new Color(0x3A3E48), new Color(0x1B1C22), new Color(0xF7A440),
                        new Color(0xCDC8BE), new Color(0xFFFFFF)),
                new PetKind.Palette("Mallard", new Color(0xB7B1A5), new Color(0x8E4B2E), new Color(0xFF8C2E),
                        new Color(0x2F8F5B), new Color(0x1F2620), new Color(0x101412), new Color(0xE9C23F),
                        new Color(0x7A7468), new Color(0x6FD39A)),
                new PetKind.Palette("Brown", new Color(0xB08257), new Color(0xD8BB8E), new Color(0xF09133),
                        new Color(0xA07650), new Color(0x3A2716), new Color(0x18120D), new Color(0xE58E2E),
                        new Color(0x7E5735), new Color(0xE3C9A0)));
    }
}
