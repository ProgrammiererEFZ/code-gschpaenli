// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: parrot. A stout perching parrot with a big hooked beak, bare eye-ring and a long tapering tail.
 * Blinks, cocks its head and "talks" (beak open) when idle, side-steps in a rocking waddle, hops with half-open wings
 * when running, flaps when jumping or falling, perches fluffed up when sitting and sleeps with its beak tucked into
 * its back.
 *
 * <p>Palette slots: B head/back, L chest/belly, E wing coverts, A flight feathers and tail tip, T tail, Y crown,
 * N beak, G feet; the eye-ring is fixed white.</p>
 */
final class PixelParrot {
    private PixelParrot() {}

    private static final String[] STAND = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_BLINK = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBBNNO..",
            ".........OBBBOONNNO.",
            ".........OBBBBBONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TALK = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOPNO.",
            "........OBBBBBBOKO..",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TILT = {
            "....................",
            "..........OOOO......",
            ".........OYYBBOO....",
            ".........OYBBBBWO...",
            ".........OBBBBWKNO..",
            ".........OBBBBBWNNO.",
            ".........OBBBBBONNO.",
            "........OBBBBBBOKNO.",
            ".......OEEEBBBBLOOO.",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TILT_BLINK = {
            "....................",
            "..........OOOO......",
            ".........OYYBBOO....",
            ".........OYBBBBBO...",
            ".........OBBBBOONO..",
            ".........OBBBBBBNNO.",
            ".........OBBBBBONNO.",
            "........OBBBBBBOKNO.",
            ".......OEEEBBBBLOOO.",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] WALK_1 = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO..OGGO...OGGO...",
    };

    private static final String[] WALK_2 = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO.....OGO.OGGO...",
            "........OGGO........",
    };

    private static final String[] WALK_3 = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO...OGGOOGGO.....",
    };

    private static final String[] WALK_4 = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            "........OBBBBBBOONO.",
            ".......OEEEBBBBLOO..",
            "......OEEEEEBBLLO...",
            ".....OAEEEEEBLLLO...",
            "....OAAAEEEBLLLLO...",
            "...OTAAAAEBBLLLO....",
            "..OTTOAAAABBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGGO.OGO....",
            ".............OGGO...",
    };

    private static final String[] RUN_1 = {
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            "..OOO....OBBBBWONNO.",
            ".OAAAOO..OBBBBBOKNO.",
            ".OAAEEEOOBBBBBBOONO.",
            "..OAAEEEEOOBBBBLOO..",
            "...OAAEEEEEOBBLLO...",
            "....OOAAEEEOBLLLO...",
            "....OBOOOOOBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO...OGGOOGGO.....",
    };

    private static final String[] RUN_2 = {
            ".OAAO.....OYYBBOO...",
            ".OAAEO...OYBBBWNNO..",
            ".OAAEEO..OBBBWKNNNO.",
            ".OAAEEEO.OBBBBWONNO.",
            "..OAAEEEOOBBBBBOKNO.",
            "..OAAEEEEOBBBBBOONO.",
            "...OAAEEEEOBBBBLOO..",
            "....OOAEEEOBBBLLO...",
            ".....OOOOOBBBLLLO...",
            "....OBBBBBBBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGO.OGO.....",
            "........OG...OG.....",
    };

    private static final String[] RUN_3 = {
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            "..OOO....OBBBWKNNNO.",
            ".OAAAOO..OBBBBWONNO.",
            ".OAAEEEOOOBBBBBOKNO.",
            "..OAAEEEEOOBBBBOONO.",
            "...OAAEEEEEOBBBLOO..",
            "....OOAAEEEOBBLLO...",
            ".....OOOOOOBBLLLO...",
            "....OBBBBBBBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO.....OGOOGO.....",
            "....................",
            "....................",
    };

    private static final String[] RUN_4 = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBBWONNO.",
            ".........OBBBBBOKNO.",
            ".....OOOOOOBBBBOONO.",
            "....OEEEEEEOBBBLOO..",
            "..OOAEEEEEOBBBLLO...",
            ".OAAAAEEOOBBBLLLO...",
            "OAAAAOOOBBBBLLLLO...",
            "OAAOOBBBBBBBLLLO....",
            "OOOTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            ".......OGGO.OGGO....",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            "........OBBBBBWONNO.",
            ".......OBBBBBBBOKNO.",
            "......OEEBBBBBBOONO.",
            ".....OEEEEEBBBLLLO..",
            "....OAEEEEEEBLLLLO..",
            "...OAAAEEEEBLLLLLO..",
            "..OTAAAAEEBBLLLLLO..",
            ".OTTOAAAABBBLLLLO...",
            "OTTO.OOOOOOOOOOO....",
            "OAO....OGGOOGGO.....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBBNNO..",
            ".........OBBBOONNNO.",
            "........OBBBBBBONNO.",
            ".......OBBBBBBBOKNO.",
            "......OEEBBBBBBOONO.",
            ".....OEEEEEBBBLLLO..",
            "....OAEEEEEEBLLLLO..",
            "...OAAAEEEEBLLLLLO..",
            "..OTAAAAEEBBLLLLLO..",
            ".OTTOAAAABBBLLLLO...",
            "OTTO.OOOOOOOOOOO....",
            "OAO....OGGOOGGO.....",
    };

    private static final String[] SIT_HALF = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBONNO..",
            ".........OBBBWKNNNO.",
            "........OBBBBBWONNO.",
            ".......OBBBBBBBOKNO.",
            "......OEEBBBBBBOONO.",
            ".....OEEEEEBBBLLLO..",
            "....OAEEEEEEBLLLLO..",
            "...OAAAEEEEBLLLLLO..",
            "..OTAAAAEEBBLLLLLO..",
            ".OTTOAAAABBBLLLLO...",
            "OTTO.OOOOOOOOOOO....",
            "OAO....OGGOOGGO.....",
    };

    private static final String[] SIT_FLUFF = {
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            ".........OBBBWKNNNO.",
            "........OBBBBBWONNO.",
            ".......OBBBBBBBOKNO.",
            "......OEEBBBBBBOONO.",
            "....OOEEEEEBBBLLLO..",
            "...OAEEEEEEBBLLLLLO.",
            "..OAAAEEEEBBLLLLLLO.",
            "..OTAAAAEEBBLLLLLLO.",
            ".OTTOAAAABBBLLLLLO..",
            "OTTO.OOOOOOOOOOO....",
            "OAO....OGGOOGGO.....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBBBBO..",
            ".........OBBWOOBBO..",
            "........OBBBBBBONNO.",
            ".......OEEBBBBBONNO.",
            "......OEEEEEBBLLONO.",
            ".....OAEEEEEBLLLLOO.",
            "....OAAAEEEEBLLLLLO.",
            "...OTAAAAEEBBLLLLLO.",
            "..OTTOAAAABBBLLLLO..",
            ".OTO.OOOOOOOOOOOO...",
            "OAO.....OGGOOGGO....",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBBBBO..",
            ".........OBBWOOBBO..",
            "........OBBBBBBONNO.",
            ".......OEEBBBBBONNO.",
            "......OEEEEEBBLLONO.",
            "....OOAEEEEEBLLLLLO.",
            "...OAAAAEEEEBLLLLLLO",
            "..OTTAAAAEEBBLLLLLLO",
            ".OTTOOAAAABBBLLLLLO.",
            ".OTO.OOOOOOOOOOOO...",
            "OAO.....OGGOOGGO....",
    };

    private static final String[] JUMP_A = {
            ".OAAO......OOOO.....",
            ".OAAEO....OYYBBOO...",
            ".OAAEEO..OYBBBWNNO..",
            ".OAAEEEO.OBBBWKNNNO.",
            "..OAAEEEOOBBBBWONNO.",
            "..OAAEEEEOBBBBBOKNO.",
            "...OAAEEEEOBBBBOONO.",
            "....OOAEEEOBBBBLOO..",
            "......OOOOBBBBLLO...",
            ".....OBBBBBBBLLLO...",
            "....OBBBBBBBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGG.OGG.....",
    };

    private static final String[] FALL_A = {
            ".OAAO......OOOO.....",
            ".OAAEO....OYYBBOO...",
            ".OAAEEO..OYBBWWNNO..",
            ".OAAEEEO.OBBBWKNNNO.",
            "..OAAEEEOOBBBWWONNO.",
            "..OAAEEEEOBBBBBOKNO.",
            "...OAAEEEEOBBBBOONO.",
            "....OOAEEEOBBBBLOO..",
            "......OOOOBBBBLLO...",
            ".....OBBBBBBBLLLO...",
            "....OBBBBBBBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGG.OGG.....",
    };

    private static final String[] FALL_B = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBWWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBWWONNO.",
            ".........OBBBBBOKNO.",
            ".....OOOOOOBBBBOONO.",
            "....OEEEEEEOBBBLOO..",
            "..OOAEEEEEOBBBLLO...",
            ".OAAAAEEOOBBBLLLO...",
            "OAAAAOOOBBBBLLLLO...",
            "OAAOOBBBBBBBLLLO....",
            "OOOTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGG.OGG.....",
    };

    private static final String[] HELD_A = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBBWNNO..",
            "..OOO....OBBBWKNNNO.",
            ".OAAAOO..OBBBBWONNO.",
            ".OAAEEEOOOBBBBBOKNO.",
            "..OAAEEEEOOBBBBOONO.",
            "...OAAEEEEEOBBBLOO..",
            "....OOAAEEEOBBLLO...",
            ".....OOOOOOBBLLLO...",
            "....OBBBBBBBLLLLO...",
            "...OTBBBBBBBLLLO....",
            "..OTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGG.OGG.....",
    };

    private static final String[] HELD_B = {
            "...........OOOO.....",
            "..........OYYBBOO...",
            ".........OYBBWWNNO..",
            ".........OBBBWKNNNO.",
            ".........OBBBWWONNO.",
            ".........OBBBBBOKNO.",
            ".....OOOOOOBBBBOONO.",
            "....OEEEEEEOBBBLOO..",
            "..OOAEEEEEOBBBLLO...",
            ".OAAAAEEOOBBBLLLO...",
            "OAAAAOOOBBBBLLLLO...",
            "OAAOOBBBBBBBLLLO....",
            "OOOTTOBBBBBBLLLO....",
            ".OTTO.OOOOOOOOO.....",
            "OAAO....OGO.OGO.....",
            "........OGG.OGG.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND, STAND, STAND_BLINK, STAND, STAND_TALK, STAND, STAND_TALK, STAND, STAND,
                    STAND_TILT, STAND_TILT, STAND_TILT_BLINK, STAND_TILT, STAND, STAND, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_BLINK, SIT_A, SIT_FLUFF, SIT_FLUFF, SIT_A, SIT_HALF, SIT_HALF, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    // body, belly, leg, ear (wing coverts), outline, eye (crown), nose (beak), accent (flight feathers), tailTip (tail)
    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Scarlet", new Color(0xD8262E), new Color(0xE8452E), new Color(0x6E6A70),
                        new Color(0xF5C12A), new Color(0x3A0E10), new Color(0xEE5A2A), new Color(0xEDE3CF),
                        new Color(0x2F6FD0), new Color(0xC9202A)),
                new PetKind.Palette("Green", new Color(0x3FA34A), new Color(0x7CCB5A), new Color(0x8A8A88),
                        new Color(0x2F8A3E), new Color(0x123A1A), new Color(0xF2D33A), new Color(0xB9AFA0),
                        new Color(0x2C5CA8), new Color(0x5DB84A)),
                new PetKind.Palette("Grey", new Color(0x8C8F96), new Color(0xB4B7BD), new Color(0x55575C),
                        new Color(0x777A81), new Color(0x1C1D20), new Color(0xA4A7AD), new Color(0x46474E),
                        new Color(0x55585F), new Color(0xD8323A)),
                new PetKind.Palette("Blue and gold", new Color(0x2A7FD4), new Color(0xF4B92A), new Color(0x3A3A40),
                        new Color(0x2A8ADF), new Color(0x0E2240), new Color(0x4CB848), new Color(0x44444C),
                        new Color(0x1F4F9E), new Color(0x2A7FD4)));
    }
}
