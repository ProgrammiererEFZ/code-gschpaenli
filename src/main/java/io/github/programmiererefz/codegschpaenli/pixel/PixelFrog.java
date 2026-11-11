// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;
import java.awt.Color;
import java.util.List;

/** Original pixel art: frog. */
final class PixelFrog {
    private PixelFrog() {}

    private static final String[] STAND_A = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOOOO..",
            "...OBBBBBBBBLLLLLO..",
            "..OBBBAAABBLLLLLO...",
            "..OBBABBBABLLLLO....",
            "..OBABBBBBALLLOGO...",
            "..OABBBBBAOOOOOGO...",
            "..OGGGGGGGGGO.OGGO..",
            "...OOOOOOOOOO.OOOO..",
    };

    private static final String[] STAND_THROAT = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOOOO..",
            "...OBBBBBBBBLLLLLLO.",
            "..OBBBAAABBLLLLLLO..",
            "..OBBABBBABLLLLLO...",
            "..OBABBBBBALLLOGO...",
            "..OABBBBBAOOOOOGO...",
            "..OGGGGGGGGGO.OGGO..",
            "...OOOOOOOOOO.OOOO..",
    };

    private static final String[] STAND_BLINK = {
            ".............OOO....",
            "..........OOOBBBO...",
            ".........OOOOOOOO...",
            ".......OOOBBOBBBOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOOOO..",
            "...OBBBBBBBBLLLLLO..",
            "..OBBBAAABBLLLLLO...",
            "..OBBABBBABLLLLO....",
            "..OBABBBBBALLLOGO...",
            "..OABBBBBAOOOOOGO...",
            "..OGGGGGGGGGO.OGGO..",
            "...OOOOOOOOOO.OOOO..",
    };

    private static final String[] STAND_TONGUE = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOONNN.",
            "...OBBBBBBBBLLLLLON.",
            "..OBBBAAABBLLLLLO...",
            "..OBBABBBABLLLLO....",
            "..OBABBBBBALLLOGO...",
            "..OABBBBBAOOOOOGO...",
            "..OGGGGGGGGGO.OGGO..",
            "...OOOOOOOOOO.OOOO..",
    };

    private static final String[] CROUCH = {
            "....................",
            "....................",
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "...OOBBBBBBBBBBPBBO.",
            "..OBBBABBBBBOOOOOO..",
            "..OBBBAAABBLLLLLLO..",
            ".OBBABBBABLLLLLLO...",
            ".OBABBBBBALLLLOGO...",
            ".OGGGGGGGGGGOOOGGO..",
            "..OOOOOOOOOOO.OOOO..",
    };

    private static final String[] TAKEOFF = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            "......OBBABBBBBBBBO.",
            ".....OBBBBBBBBBPBBO.",
            "....OBABBBBBOOOOOO..",
            "...OBBBBBBBLLLLLO...",
            "...OBBBBBLLLLLOGO...",
            "..OBBABBLLLLOOGGO...",
            "..OGBBBOOOOOO.OO....",
            "..OGGO..............",
            "..OGO...............",
            ".OGGGO..............",
    };

    private static final String[] AIR = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "...OOBBBBBBBBBBPBBO.",
            "..OBBBABBBBBOOOOOO..",
            ".OBBBBBBBBBLLLLLO...",
            ".OGGBBABBLLLLLLOGO..",
            ".OGGGOOOLLLLLOOGGO..",
            ".OOGGGOOOOOOO.OOO...",
            "...OOOO.............",
            "....................",
            "....................",
    };

    private static final String[] LEAP = {
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOOOO..",
            ".OOOBBBBBBBLLLLLO...",
            ".GGGOOBBBLLLLLOGGO..",
            ".OOOGGOOOOOOOOGGO...",
            "....OO........OO....",
            "....................",
            "....................",
            "....................",
    };

    private static final String[] LAND = {
            "....................",
            ".............OOO....",
            "..........OOOYKWO...",
            ".........OKWOYKKO...",
            ".......OOOYYOYYYOO..",
            "...OO.OBBABBBBBBBBO.",
            "..OGGOBBBBBBBBBPBBO.",
            "..OGGBBABBBBOOOOOO..",
            "...OOBBBBBBLLLLLO...",
            "....OBBBBLLLLLLO....",
            ".....OBBBLLLLLOGO...",
            "......OOOOOOOOOGO...",
            "..............OGGO..",
            "..............OOOO..",
    };

    private static final String[] SIT_A = {
            ".............OOO....",
            "..........OOOBBBO...",
            ".........OBBOYKWO...",
            ".......OOOKWOYKKOO..",
            "......OBBABBBBBBBBO.",
            ".....OBBBBBBBBBPBBO.",
            ".....OBABBBBOOOOOO..",
            "....OBBBBBBBLLLLLO..",
            "....OBBAAABLLLLLO...",
            "...OBBABBBALLLLO....",
            "...OBABBBBALLLOGO...",
            "...OABBBBAOOOOOGO...",
            "...OGGGGGGGGO.OGGO..",
            "....OOOOOOOOO.OOOO..",
    };

    private static final String[] SIT_CROAK = {
            ".............OOO....",
            "..........OOOBBBO...",
            ".........OBBOYKWO...",
            ".......OOOKWOYKKOO..",
            "......OBBABBBBBBBBO.",
            ".....OBBBBBBBBBPBBO.",
            ".....OBABBBBOOOOOO..",
            "....OBBBBBBBOLLLLLO.",
            "....OBBAAABOLLLWLLO.",
            "...OBBABBBAOLLLLLLO.",
            "...OBABBBBAOOLLLLO..",
            "...OABBBBAOOOOOOGO..",
            "...OGGGGGGGGO.OGGO..",
            "....OOOOOOOOO.OOOO..",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".............OOO....",
            "..........OOOBBBO...",
            ".........OBBOOOOO...",
            ".......OOOBBOBBBOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOOOOO..",
            "..OBBAAABBBLLLLLLO..",
            ".OBBABBBAGGGLLLLGGO.",
            "..OOOOOOOOOOOOOOOO..",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".............OOO....",
            "..........OOOBBBO...",
            ".........OBBOOOOO...",
            "......OOOOBBOBBBOO..",
            "....OOBBBABBBBBBBBO.",
            "...OBBBBBBBBBBBPBBO.",
            "..OBBBABBBBBOOOOOO..",
            "..OBBAAABBBLLLLLLO..",
            ".OBBABBBAGGGLLLLGGO.",
            "..OOOOOOOOOOOOOOOO..",
    };

    private static final String[] FALL_A = {
            ".............OOO....",
            "..........OOOWWWO...",
            ".........OWWOWKWO...",
            ".......OOOKWOWWWOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOONNO..",
            "...OBBBBBBBBLOOOO...",
            "..OBBBAAABBLLLLO....",
            "..OBBABBBALLLLLO....",
            ".OGOABBBBALLLLOGO...",
            ".OGGOOOOOOOOOOOGGO..",
            ".OGGGO.........OGGO.",
            "..OOO..........OOO..",
    };

    private static final String[] FALL_B = {
            ".............OOO....",
            "..........OOOWWWO...",
            ".........OWWOWKWO...",
            ".......OOOKWOWWWOO..",
            ".....OOBBABBBBBBBBO.",
            "....OBBBBBBBBBBPBBO.",
            "...OBBABBBBBOOONNO..",
            "...OBBBBBBBBLOOOO...",
            ".OOOBBAAABBLLLLOOO..",
            ".OGOBABBBALLLLLOGO..",
            "..OGOBBBBALLLLOGO...",
            "..OGOOOOOOOOOOOGO...",
            "...OGO.......OGO....",
            "....O.........O.....",
    };

    private static final String[] HELD_A = {
            ".............OOO....",
            "..........OOOWWWO...",
            ".........OWWOWKWO...",
            ".......OOOKWOWWWOO..",
            "......OBBABBBBBBBBO.",
            "......OBBBBBBBBPBBO.",
            "......OBABBBOOONNO..",
            "......OBBBBLLOOOO...",
            "......OBBBALLLLO....",
            ".....OBBBBBLLLLOGO..",
            ".....OBABBBALLLOGO..",
            ".....OGOOOOOGOOOGO..",
            ".....OGGO..OGGO.OGO.",
            "......OO....OO...O..",
    };

    private static final String[] HELD_B = {
            ".............OOO....",
            "..........OOOWWWO...",
            ".........OWWOWKWO...",
            ".......OOOKWOWWWOO..",
            "......OBBABBBBBBBBO.",
            "......OBBBBBBBBPBBO.",
            "......OBABBBOOONNO..",
            "......OBBBBLLOOOO...",
            "......OBBBALLLLO....",
            ".....OBBBBBLLLLOGO..",
            "...OOOBABBBALLLOGO..",
            "..OGGOOOOOOGOOOOGO..",
            "..OGO.....OGGO..OO..",
            "...O.......OO.......",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 14)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_THROAT, STAND_A, STAND_THROAT, STAND_A, STAND_BLINK,
                    STAND_A, STAND_THROAT, STAND_A, STAND_TONGUE)
            .anim(Pose.WALK, 0, CROUCH, TAKEOFF, AIR, LAND)
            .anim(Pose.RUN, 0, CROUCH, TAKEOFF, LEAP, LAND)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_CROAK, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, LEAP)
            .anim(Pose.FALL, 6, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Green", new Color(0x6BBF59), new Color(0xE4F2B0), new Color(0x4A963F),
                        new Color(0x3F8A3A), new Color(0x1F3A1C), new Color(0xE8B730), new Color(0xE8586F),
                        new Color(0x3F8A3A), new Color(0xA6E08C)),
                new PetKind.Palette("Tree frog", new Color(0x4FD16A), new Color(0xF5F0D0), new Color(0xFF8A2B),
                        new Color(0x2E9E4E), new Color(0x163B22), new Color(0xD8352A), new Color(0xFF6F8A),
                        new Color(0x2E9E4E), new Color(0x9CF0A8)),
                new PetKind.Palette("Blue dart", new Color(0x2F7BE0), new Color(0x7FB8FF), new Color(0x1F4FA8),
                        new Color(0x0E1A33), new Color(0x101A3A), new Color(0x3A4A78), new Color(0xFF6F8A),
                        new Color(0x0E1A33), new Color(0x8FCBFF)),
                new PetKind.Palette("Strawberry", new Color(0xE0393E), new Color(0xF07A6A), new Color(0x2F5FD0),
                        new Color(0x9E1F26), new Color(0x3A0E12), new Color(0x5A3A48), new Color(0xFF9EB0),
                        new Color(0x9E1F26), new Color(0xFF8C84)));
    }
}
