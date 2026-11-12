// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: fruit bat. Stands wrapped in its wings like a cloak (blinks, twitches an ear, glances about),
 * crawls on its wing-thumbs and feet, runs as a low flapping glide, sits and sleeps snug in its wings and spreads
 * them wide when jumping, falling or held.
 */
final class PixelBat {
    private PixelBat() {}

    private static final String[] STAND = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "...OBBYYBBBYYBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "...OEAOLLLLLOAGO....",
            "..OEEAEOBBBOEAEEO...",
            "..OEEEAEOBOEAEEEO...",
            "..OEEEEAEOEAEEEEO...",
            "...OEOEOEOEOEOEO....",
            "......OGGOOGGO......",
    };

    private static final String[] STAND_BLINK = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBBBBBBBBBOO....",
            "...OBBOOBBBOOBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "...OEAOLLLLLOAGO....",
            "..OEEAEOBBBOEAEEO...",
            "..OEEEAEOBOEAEEEO...",
            "..OEEEEAEOEAEEEEO...",
            "...OEOEOEOEOEOEO....",
            "......OGGOOGGO......",
    };

    private static final String[] STAND_EAR = {
            "....OO......OO......",
            "....OTO....OTO......",
            "....OTTO.OTTTO......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "...OBBYYBBBYYBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "...OEAOLLLLLOAGO....",
            "..OEEAEOBBBOEAEEO...",
            "..OEEEAEOBOEAEEEO...",
            "..OEEEEAEOEAEEEEO...",
            "...OEOEOEOEOEOEO....",
            "......OGGOOGGO......",
    };

    private static final String[] STAND_LOOK = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBWYBBBWYBOO....",
            "...OBBYYBBBYYBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "...OEAOLLLLLOAGO....",
            "..OEEAEOBBBOEAEEO...",
            "..OEEEAEOBOEAEEEO...",
            "..OEEEEAEOEAEEEEO...",
            "...OEOEOEOEOEOEO....",
            "......OGGOOGGO......",
    };

    private static final String[] WALK_1 = {
            "....................",
            "....................",
            "....................",
            "......OO.....OO.....",
            "..OO..OTO...OTO.....",
            "..OGO.OTTO.OTTO.....",
            ".OEAEOOTTTOTTTO.....",
            ".OEEAEOBTBBBBTBO....",
            "OEEEEOBBBBBBBBBBO...",
            "OEEEEOBBYWBBBYWBOO..",
            "OEEEEOBBYYBBBYYBTTNO",
            ".OEEEEOBBBBBBPPBTTO.",
            "..OOOEEEEOBOAOOWOWO.",
            "...OEEEEEOOOAO......",
            "...OOOOOOOOOAO......",
            "...OGGO.OGOGGO......",
    };

    private static final String[] WALK_2 = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OO.....OO.....",
            "..OO..OTO...OTO.....",
            "..OGO.OTTO.OTTO.....",
            ".OEAEOOTTTOTTTO.....",
            ".OEEAEOBTBBBBTBO....",
            "OEEEEOBBBBBBBBBBO...",
            "OEEEEOBBYWBBBYWBOO..",
            "OEEEEOBBYYBBBYYBTTNO",
            ".OEEEEOBBBBBBPPBTTO.",
            "..OOEEEEEOOAOBOWOWO.",
            "...OOOOOOOAO........",
            "....OGGOOOGGO.......",
    };

    private static final String[] WALK_3 = {
            "....................",
            "....................",
            "....................",
            "......OO.....OO.....",
            "..OO..OTO...OTO.....",
            "..OGO.OTTO.OTTO.....",
            ".OEAEOOTTTOTTTO.....",
            ".OEEAEOBTBBBBTBO....",
            "OEEEEOBBBBBBBBBBO...",
            "OEEEEOBBYWBBBYWBOO..",
            "OEEEEOBBYYBBBYYBTTNO",
            ".OEEEEOBBBBBBPPBTTO.",
            "..OOOEEEEOBOAOOWOWO.",
            "...OEEEEEOOOAO......",
            "...OOOOOOOOOAO......",
            "...OGGO.OGOGGO......",
    };

    private static final String[] WALK_4 = {
            "....................",
            "....................",
            "....................",
            "....................",
            "......OO.....OO.....",
            "..OO..OTO...OTO.....",
            "..OGO.OTTO.OTTO.....",
            ".OEAEOOTTTOTTTO.....",
            ".OEEAEOBTBBBBTBO....",
            "OEEEEOBBBBBBBBBBO...",
            "OEEEEOBBYWBBBYWBOO..",
            "OEEEEOBBYYBBBYYBTTNO",
            ".OEEEEOBBBBBBPPBTTO.",
            "..OOEEEEEOOOOAOWOWO.",
            "...OOOOOOOO..OAO....",
            "....OGGOOGGO.OGGO...",
    };

    private static final String[] RUN_1 = {
            "....................",
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "OO.OBBYWBBBYWBOO..OO",
            "OGOOBBYYBBBYYBTTNOGO",
            "OAAAOBBBBBBPPBTTOAAO",
            "OEAEEOLLLLLLOWOWOAEO",
            "OEAEEOLLLLLLOAEEEAEO",
            "OEEAEEOBBBBOOAEEAEEO",
            ".OEAEEOOOOOOOAEEAEO.",
            "..OOEOOGOOGOOEOEOO..",
            "....................",
    };

    private static final String[] RUN_2 = {
            "....................",
            "....OO.....OO.......",
            "OO..OTO...OTO.....OO",
            "OGO.OTTO.OTTO....OGO",
            "OAAOOTTTOTTTO...OAAO",
            "OEAAOBTBBBBTBO.OAAEO",
            "OEEOBBBBBBBBBBOAAEEO",
            "OEAOBBYWBBBYWBOOEAEO",
            "OEEOBBYYBBBYYBTTNOEO",
            ".OEEOBBBBBBPPBTTOEO.",
            "..OEEOLLLLLLOWOWOO..",
            "...OOOLLLLLLOOEOO...",
            "......OBBBBO........",
            "......OOOOOO........",
            "......OGOOGO........",
            "....................",
    };

    private static final String[] RUN_3 = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "OO.OBBYYBBBYYBTTNOOO",
            "OGOOOBBBBBBPPBTTOOGO",
            "OAAAOOLLLLLLOWOWOAAO",
            "OEAEEOLLLLLLOOAEEAEO",
            "OEAEEEOBBBBOOAEEEAEO",
            "OEEAEEOOOOOOOAEEAEEO",
            ".OEAEEOGOOGOOAEEAEO.",
            "..OOEOEO....OEOEOO..",
            "....................",
    };

    private static final String[] RUN_4 = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "...OBBYYBBBYYBTTNO..",
            "OOOOOBBBBBBPPBTTOOOO",
            "OGAAAOLLLLLLOWOWOAGO",
            ".OEEEOLLLLLLOAEEEEO.",
            "..OAEEOBBBBOOEEEAO..",
            "..OEAEOOOOOOOEEAEO..",
            "...OEAOGOOGOOEAEO...",
            "....OEOO....OOEO....",
            ".....OO......OO.....",
    };

    private static final String[] JUMP_A = {
            "....OO.....OO.......",
            "OO..OTO...OTO.....OO",
            "OGO.OTTO.OTTO....OGO",
            "OAAOOTTTOTTTO...OAAO",
            "OEAAOBTBBBBTBO.OAAEO",
            "OEEOBBBBBBBBBBOAAEEO",
            "OEAOBBYWBBBYWBOOEAEO",
            "OEEOBBYYBBBYYBTTNOEO",
            ".OEEOBBBBBBPPBTTOEO.",
            "..OEEOLLLLLLOWOWOO..",
            "...OOOLLLLLLOOEOO...",
            "......OBBBBO........",
            "......OOOOOO........",
            "......OGOOGO........",
            "....................",
            "....................",
    };

    private static final String[] FALL_A = {
            "....................",
            "OO..OO.....OO.....OO",
            "OGO.OTO...OTO....OGO",
            "OAAOOTTO.OTTO...OAAO",
            "OEAAOTTTOTTTO..OAAEO",
            "OEEAOBTBBBBTBOOAAEEO",
            "OEAOBBBBBBBBBBOEEAEO",
            "OEEOBBWYBBBWYBOOAEEO",
            ".OEOBBYYBBBYYBTTNOO.",
            "..OEOBBBBBBPPBTTOO..",
            "...OOOLLLLLLOWOWO...",
            ".....OLLLLLLO.......",
            "......OBBBBO........",
            "......OOOOOO........",
            "......OGOOGO........",
            "....................",
    };

    private static final String[] FALL_B = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBWYBBBWYBOO....",
            "OO.OBBYYBBBYYBTTNOOO",
            "OGOOOBBBBBBPPBTTOOGO",
            "OAAAOOLLLLLLOWOWOAAO",
            "OEAEEOLLLLLLOOAEEAEO",
            "OEAEEEOBBBBOOAEEEAEO",
            "OEEAEEOOOOOOOAEEAEEO",
            ".OEAEEOGOOGOOAEEAEO.",
            "..OOEOEO....OEOEOO..",
            "....................",
    };

    private static final String[] HELD_A = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "OO.OBBYYBBBYYBTTNOOO",
            "OGOOOBBBBBBPPBTTOOGO",
            "OAAAOOLLLLLLOWOWOAAO",
            "OEAEEOLLLLLLOOAEEAEO",
            "OEAEEEOBBBBOOAEEEAEO",
            "OEEAEEOOOOOOOAEEAEEO",
            ".OEAEEOGOOGOOAEEAEO.",
            "..OOEOEO....OEOEOO..",
            "....................",
    };

    private static final String[] HELD_B = {
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBBBBBBBBBOO....",
            "...OBBOOBBBOOBTTNO..",
            "OO..OBBBBBBPPBTTO.OO",
            "OGOO.OLLLLLLOWOWOOGO",
            "OAAAOOLLLLLLO.OOAAAO",
            "OEAEEAOBBBBOOOAEEAEO",
            "OEAEEEOOOOOOOAEEEAEO",
            "OEEAEEOGOOGOOAEEAEEO",
            ".OEAEEAO....OAEEAEO.",
            "..OOEOEO....OEOEOO..",
    };

    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBYWBBBYWBOO....",
            "...OBBYYBBBYYBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "..OEAEOLLLLOEAEO....",
            "..OEEAEOOOOEAEEO....",
            "..OEEEAEEEEAEEEO....",
            "...OOOOOOOOOOOO.....",
    };

    private static final String[] SIT_BLINK = {
            "....................",
            "....................",
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBBBBBBBBBOO....",
            "...OBBOOBBBOOBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "..OEAEOLLLLOEAEO....",
            "..OEEAEOOOOEAEEO....",
            "..OEEEAEEEEAEEEO....",
            "...OOOOOOOOOOOO.....",
    };

    private static final String[] SIT_LOOK = {
            "....................",
            "....................",
            "....OO.....OO.......",
            "....OTO...OTO.......",
            "....OTTO.OTTO.......",
            "....OTTTOTTTO.......",
            "....OBTBBBBTBO......",
            "...OBBBBBBBBBBO.....",
            "...OBBWYBBBWYBOO....",
            "...OBBYYBBBYYBTTNO..",
            "....OBBBBBBPPBTTO...",
            "...OGOLLLLLLOWOWO...",
            "..OEAEOLLLLOEAEO....",
            "..OEEAEOOOOEAEEO....",
            "..OEEEAEEEEAEEEO....",
            "...OOOOOOOOOOOO.....",
    };

    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "...OOO.....OOO......",
            "...OTTOO.OOTTO......",
            "....OBTBOBTBO.......",
            "....OBBBBBBBBO......",
            "...OBBBBBBBBBBOO....",
            "...OBBOOBBBOOBTTNO..",
            "..OGOOBBOOBPOOGOO...",
            ".OEAEEOOEEOOEEAEO...",
            ".OEEAEEEAEEEEAEEO...",
            ".OEEEAEEAEEEAEEEO...",
            ".OEEEEAEAEEAEEEEO...",
            "..OOOOOOOOOOOOOO....",
    };

    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "...OOO.....OOO......",
            "...OTTOO.OOTTO......",
            "....OBTBOBTBO.......",
            "....OBBBBBBBBO......",
            "...OBBBBBBBBBBOO....",
            "..OGOOOOOOBOOOGONO..",
            ".OEAEEOOEEOOEEAEO...",
            ".OEEAEEEAEEEEAEEO...",
            ".OEEEAEEAEEEAEEEO...",
            ".OEEEEAEAEEAEEEEO...",
            "..OOOOOOOOOOOOOO....",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND, STAND, STAND, STAND_BLINK, STAND, STAND_EAR, STAND, STAND, STAND_LOOK,
                    STAND_LOOK, STAND, STAND_BLINK, STAND, STAND_EAR, STAND_EAR, STAND)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 1.5, SIT_A, SIT_A, SIT_A, SIT_BLINK, SIT_A, SIT_LOOK, SIT_LOOK, SIT_A)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 1, JUMP_A)
            .anim(Pose.FALL, 8, FALL_A, FALL_B)
            .anim(Pose.HELD, 4, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        // slots: body B, mantle L, claws/feet G, wing membrane E, outline O, eye Y, nose N, wing bones A, inner ear/muzzle T
        return List.of(
                new PetKind.Palette("Fruit bat", new Color(0x8C5634), new Color(0xD9A04A), new Color(0x9A7058),
                        new Color(0x4E3226), new Color(0x1E120C), new Color(0x1A0E08), new Color(0x2A1810),
                        new Color(0x8A6248), new Color(0xB07A5E)),
                new PetKind.Palette("Black", new Color(0x544E5C), new Color(0x8A8290), new Color(0x8A8290),
                        new Color(0x3C3744), new Color(0x0E0D10), new Color(0xE8A632), new Color(0x1A181C),
                        new Color(0x6E6878), new Color(0x8E6E7C)),
                new PetKind.Palette("Grey", new Color(0x8A8E96), new Color(0xCDD0D6), new Color(0xB4AAAA),
                        new Color(0x5C606A), new Color(0x24262C), new Color(0x1A1A20), new Color(0x3A3036),
                        new Color(0x868A94), new Color(0xD8A8B0)),
                new PetKind.Palette("Ghost", new Color(0xF2F0EC), new Color(0xFFF6E0), new Color(0xF0B8C0),
                        new Color(0xE2D4D8), new Color(0x7A7078), new Color(0x2A1E24), new Color(0xE88A9A),
                        new Color(0xC8B0B8), new Color(0xF4A8B8)));
    }
}
