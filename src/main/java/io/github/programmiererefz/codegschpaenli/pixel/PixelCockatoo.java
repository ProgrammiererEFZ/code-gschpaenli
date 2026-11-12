// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: cockatoo. A stocky parrot with a swept-back crest that fans up whenever it gets excited: when it
 * "talks", hops, jumps, falls or is picked up. Blinks and cocks its head when idle, side-steps in a rocking waddle,
 * perches fluffed up when sitting and sleeps with its head sunk and its beak tucked into its chest.
 *
 * <p>Palette slots: T head, Y crest, B back and tail, L chest, E wing coverts, A flight feathers and tail tip,
 * N beak, G feet; the eye-ring is fixed white.</p>
 */
final class PixelCockatoo {
    private PixelCockatoo() {}

    private static final String[] STAND = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_BLINK = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTTNNO..",
            ".........OTTTOONNNO.",
            ".........OTTTTTONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_CREST = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TALK = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOPNO.",
            "........OBTTTTTOKO..",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TILT = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOO......",
            ".......OOYYYTTOO....",
            ".........OTTTTTWO...",
            ".........OTTTTWKNO..",
            ".........OTTTTTWNNO.",
            ".........OTTTTTONNO.",
            "........OBTTTTTOKNO.",
            "......OEEEEBBBLLOOO.",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] STAND_TILT_BLINK = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOO......",
            ".......OOYYYTTOO....",
            ".........OTTTTTTO...",
            ".........OTTTTOONO..",
            ".........OTTTTTTNNO.",
            ".........OTTTTTONNO.",
            "........OBTTTTTOKNO.",
            "......OEEEEBBBLLOOO.",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] WALK_1 = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO..OGGO...OGGO...",
    };

    private static final String[] WALK_2 = {
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO.....OGO.OGGO...",
            "........OGGO........",
    };

    private static final String[] WALK_3 = {
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO...OGGOOGGO.....",
    };

    private static final String[] WALK_4 = {
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            "........OBTTTTTOONO.",
            "......OEEEEBBBLLOO..",
            ".....OAEEEEEBBLLLO..",
            "....OAAEEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGGO.OGO....",
            ".............OGGO...",
    };

    private static final String[] RUN_1 = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            "..OOO....OTTTTWONNO.",
            ".OAAAOO..OTTTTTOKNO.",
            ".OAAEEEOOBTTTTTOONO.",
            "..OAAEEEEOOBBBLLOO..",
            "...OAAEEEEEOBBLLLO..",
            "....OOAAEEEOBLLLLLO.",
            "...OBBOOOOOBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO...OGGOOGGO.....",
    };

    private static final String[] RUN_2 = {
            "..OO..OYOO..........",
            ".OAAO.OYYYOOOOO.....",
            ".OAAEO.OOYYYTTTOO...",
            ".OAAEEO..OTTTTWNNO..",
            ".OAAEEEO.OTTTWKNNNO.",
            "..OAAEEEOOTTTTWONNO.",
            "..OAAEEEEOTTTTTOKNO.",
            "...OAAEEEEOTTTTOONO.",
            "....OOAEEEOBBBLLOO..",
            ".....OOOOOBBBBLLLO..",
            "....OBBBBBBBBLLLLLO.",
            "...OBBBBBBBBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGO.OGO.....",
            "........OG...OG.....",
    };

    private static final String[] RUN_3 = {
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            "..OOO....OTTTWKNNNO.",
            ".OAAAOO..OTTTTWONNO.",
            ".OAAEEEOOOTTTTTOKNO.",
            "..OAAEEEEOOTTTTOONO.",
            "...OAAEEEEEOBBLLOO..",
            "....OOAAEEEOBBLLLO..",
            "....OBOOOOOBBLLLLLO.",
            "...OBBBBBBBBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO.....OGOOGO.....",
            "....................",
    };

    private static final String[] RUN_4 = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTTWONNO.",
            ".........OTTTTTOKNO.",
            ".....OOOOOOTTTTOONO.",
            "....OEEEEEEOBBLLOO..",
            "..OOAEEEEEOBBBLLLO..",
            ".OAAAAEEOOBBBLLLLLO.",
            "OAAAAOOOBBBBBLLLLLO.",
            "OAAOOOBBBBBBBLLLLO..",
            "OOOBO.OOOOOOOOOOO...",
            "OAAO...OGGO.OGGO....",
    };

    private static final String[] SIT_A = {
            "....................",
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            "........OTTTTTWONNO.",
            ".......OTTTTTTTOKNO.",
            "......OEETTTTTTOONO.",
            ".....OEEEEEBBBLLOO..",
            "....OAEEEEEEBLLLLLO.",
            "...OAAAEEEEBBLLLLLO.",
            "..OBAAAAEEBBLLLLLLO.",
            ".OBBOOOOOOOOOOOOOO..",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTTNNO..",
            ".........OTTTOONNNO.",
            "........OTTTTTTONNO.",
            ".......OTTTTTTTOKNO.",
            "......OEETTTTTTOONO.",
            ".....OEEEEEBBBLLOO..",
            "....OAEEEEEEBLLLLLO.",
            "...OAAAEEEEBBLLLLLO.",
            "..OBAAAAEEBBLLLLLLO.",
            ".OBBOOOOOOOOOOOOOO..",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] SIT_HALF = {
            "....................",
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTONNO..",
            ".........OTTTWKNNNO.",
            "........OTTTTTWONNO.",
            ".......OTTTTTTTOKNO.",
            "......OEETTTTTTOONO.",
            ".....OEEEEEBBBLLOO..",
            "....OAEEEEEEBLLLLLO.",
            "...OAAAEEEEBBLLLLLO.",
            "..OBAAAAEEBBLLLLLLO.",
            ".OBBOOOOOOOOOOOOOO..",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] SIT_FLUFF = {
            "....................",
            ".......O............",
            "......OYOO..........",
            "......OYYYOOOOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            ".........OTTTWKNNNO.",
            "........OTTTTTWONNO.",
            ".......OTTTTTTTOKNO.",
            "......OEETTTTTTOONO.",
            "....OOEEEEEBBBLLOO..",
            "...OAEEEEEEEBLLLLLO.",
            "..OAAAEEEEBBBLLLLLLO",
            ".OBAAAAEEBBBLLLLLLLO",
            ".OBBOOOOOOOOOOOOOO..",
            "OAAO....OGGOOGGO....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "..........OOO.......",
            "........OOYYYOOO....",
            ".........OYYTTTTTO..",
            ".........OTTWOOTTO..",
            "........OTTTTTTONNO.",
            ".......OEETTTTTONNO.",
            "......OEEEEEBBLLONO.",
            ".....OAEEEEEBLLLLOO.",
            "....OAAAEEEEBLLLLLO.",
            "...OBAAAAEEBBLLLLLO.",
            "..OBBOAAAABBBLLLLO..",
            ".OBO.OOOOOOOOOOOO...",
            "OAO.....OGGOOGGO....",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "..........OOO.......",
            "........OOYYYOOO....",
            ".........OYYTTTTTO..",
            ".........OTTWOOTTO..",
            "........OTTTTTTONNO.",
            ".......OEETTTTTONNO.",
            "......OEEEEEBBLLONO.",
            "....OOAEEEEEBLLLLLO.",
            "...OAAAAEEEEBLLLLLLO",
            "..OBBAAAAEEBBLLLLLLO",
            ".OBBOOAAAABBBLLLLLO.",
            ".OBO.OOOOOOOOOOOO...",
            "OAO.....OGGOOGGO....",
    };

    private static final String[] JUMP_A = {
            "..OO....O.OO........",
            ".OAAO..OYOYYOO......",
            ".OAAEOOYYYYYYOO.....",
            ".OAAEEOOOYYYTTTOO...",
            ".OAAEEEO.OTTTTWNNO..",
            "..OAAEEEOOTTTWKNNNO.",
            "..OAAEEEEOTTTTWONNO.",
            "...OAAEEEEOTTTTOKNO.",
            "....OOAEEEOTTTTOONO.",
            "......OOOOBBBBLLOO..",
            ".....OBBBBBBBBLLLO..",
            "....OBBBBBBBBLLLLLO.",
            "...OBBBBBBBBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGG.OGG.....",
    };

    private static final String[] FALL_A = {
            "..OO....O.OO........",
            ".OAAO..OYOYYOO......",
            ".OAAEOOYYYYYYOO.....",
            ".OAAEEOOOYYYTTTOO...",
            ".OAAEEEO.OTTTWWNNO..",
            "..OAAEEEOOTTTWKNNNO.",
            "..OAAEEEEOTTTWWONNO.",
            "...OAAEEEEOTTTTOKNO.",
            "....OOAEEEOTTTTOONO.",
            "......OOOOBBBBLLOO..",
            ".....OBBBBBBBBLLLO..",
            "....OBBBBBBBBLLLLLO.",
            "...OBBBBBBBBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGG.OGG.....",
    };

    private static final String[] FALL_B = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTWWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTWWONNO.",
            ".........OTTTTTOKNO.",
            ".....OOOOOOTTTTOONO.",
            "....OEEEEEEOBBLLOO..",
            "..OOAEEEEEOBBBLLLO..",
            ".OAAAAEEOOBBBLLLLLO.",
            "OAAAAOOOBBBBBLLLLLO.",
            "OAAOOOBBBBBBBLLLLO..",
            "OOOBO.OOOOOOOOOOO...",
            "OAAO....OGG.OGG.....",
    };

    private static final String[] HELD_A = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTTWNNO..",
            "..OOO....OTTTWKNNNO.",
            ".OAAAOO..OTTTTWONNO.",
            ".OAAEEEOOOTTTTTOKNO.",
            "..OAAEEEEOOTTTTOONO.",
            "...OAAEEEEEOBBLLOO..",
            "....OOAAEEEOBBLLLO..",
            "....OBOOOOOBBLLLLLO.",
            "...OBBBBBBBBBLLLLLO.",
            "..OBBOBBBBBBBLLLLO..",
            ".OBBO.OOOOOOOOOOO...",
            "OAAO....OGG.OGG.....",
    };

    private static final String[] HELD_B = {
            "........O.OO........",
            ".......OYOYYOO......",
            "......OYYYYYYOO.....",
            ".......OOYYYTTTOO...",
            ".........OTTTWWNNO..",
            ".........OTTTWKNNNO.",
            ".........OTTTWWONNO.",
            ".........OTTTTTOKNO.",
            ".....OOOOOOTTTTOONO.",
            "....OEEEEEEOBBLLOO..",
            "..OOAEEEEEOBBBLLLO..",
            ".OAAAAEEOOBBBLLLLLO.",
            "OAAAAOOOBBBBBLLLLLO.",
            "OAAOOOBBBBBBBLLLLO..",
            "OOOBO.OOOOOOOOOOO...",
            "OAAO....OGG.OGG.....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND, STAND, STAND_BLINK, STAND, STAND_CREST, STAND_TALK, STAND_CREST, STAND_TALK,
                    STAND_CREST, STAND, STAND_TILT, STAND_TILT, STAND_TILT_BLINK, STAND_TILT, STAND, STAND_BLINK)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_BLINK, SIT_A, SIT_FLUFF, SIT_FLUFF, SIT_A, SIT_HALF, SIT_HALF, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    // body (back, tail), belly, leg, ear (wing coverts), outline, eye (crest), nose (beak), accent (flight feathers),
    // tailTip (head)
    static List<PetKind.Palette> palettes() {
        return List.of(
                new PetKind.Palette("Sulphur-crested", new Color(0xF2F2EC), new Color(0xFFFFFF), new Color(0x4E4E56),
                        new Color(0xE4E7EA), new Color(0x2E323C), new Color(0xF5CF2A), new Color(0x5A5A64),
                        new Color(0xB4BCC6), new Color(0xF7F7F2)),
                new PetKind.Palette("Galah", new Color(0xA9ABB2), new Color(0xE8668A), new Color(0x8A8A92),
                        new Color(0x9A9DA5), new Color(0x3A2A32), new Color(0xF7CAD6), new Color(0xEDE4D6),
                        new Color(0x7C7F87), new Color(0xE8668A)),
                new PetKind.Palette("Pink", new Color(0xF6E2DE), new Color(0xF4B6A6), new Color(0x6E6A70),
                        new Color(0xF2EDEA), new Color(0x5A3A38), new Color(0xE8503A), new Color(0xEDE4D6),
                        new Color(0xD2C0BC), new Color(0xF4B6A6)),
                new PetKind.Palette("Gang-gang", new Color(0x5E636C), new Color(0x7A8089), new Color(0x4A4A50),
                        new Color(0x6E747E), new Color(0x1E2024), new Color(0xD8343A), new Color(0xCFC6B8),
                        new Color(0x4C5058), new Color(0xD8343A)));
    }
}
