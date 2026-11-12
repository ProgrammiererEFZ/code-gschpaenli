// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.render.Pose;

import java.awt.Color;
import java.util.List;

/**
 * Original pixel art: chicken (plump hen with a red comb and wattle, upturned tail feathers and yellow legs). Pecks
 * at the ground, blinks and looks up when idle, bobs its head while walking, flaps its wings when running, jumping
 * or falling and roosts with its legs tucked under, dozing with its head sunk into its shoulders.
 */
final class PixelChicken {
    private PixelChicken() {}


    // ---- STAND (pecking, looking up, blink) ----
    private static final String[] STAND_A = {
            "....................",
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBBBBO...",
            "..OTTO....OBBYKBOO..",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBEO..",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };
    private static final String[] STAND_BLINK = {
            "....................",
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBBBBO...",
            "..OTTO....OBBOOBOO..",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBEO..",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };
    private static final String[] STAND_LOOK = {
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..........OEEEEEO...",
            "..OO......OBBBBBO...",
            "..OTO.....OBBYKBOO..",
            "..OTTO....OBBBBBNNO.",
            "..OTATO...OBBBBBEO..",
            "..OTTATO..OBBBBBEO..",
            "..OTTTTOOOOBBBBBBO..",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "........OGO.OGO.....",
            "........OGGGOGGGO...",
    };
    private static final String[] PECK_A = {
            "....................",
            "....................",
            "....................",
            "............OO.OO...",
            ".OO........OEEOEEO..",
            ".OTO.......OEEEEEO..",
            ".OTTO......OBBBBBO..",
            ".OTATO.....OBBYKBOO.",
            ".OTTATO...OOBBBBBNNO",
            ".OTTTBBBBBBBBBBBBEO.",
            ".OTTBBBAAAABBBBBBEO.",
            "..OTBAAAABBBBLLLO...",
            "...OOBBBBBBBLLLO....",
            ".....OOOOOOOOOO.....",
            ".......OGO.OGO......",
            ".......OGGGOGGGO....",
    };
    private static final String[] PECK_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            ".OO.................",
            ".OTO................",
            ".OTTO...............",
            ".OTATO..............",
            ".OTTATOOOOOOOO......",
            ".OTTTBBBBBBBBBOO..O.",
            ".OTTBBBAAAABBBBBBOEO",
            "..OTBAAAABBBBBBBBBEO",
            "...OOBBBBBBLLBBYKBEO",
            ".....OOOOOOOOEBBBBO.",
            ".......OGO.OGOENNO..",
            ".......OGGGOGGGONO..",
    };

    // ---- WALK (contact with head thrust forward, passing) ----
    private static final String[] WALK_1 = {
            "....................",
            "............OO.OO...",
            "...........OEEOEEO..",
            "..OO.......OEEEEEO..",
            "..OTO......OBBBBBO..",
            "..OTTO.....OBBYKBOO.",
            "..OTATO....OBBBBBNNO",
            "..OTTATO...OBBBBBEO.",
            "..OTTTTOOOOOBBBBBEO.",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".......OGO...OGO....",
            ".......OGGGO.OGGGO..",
    };
    private static final String[] WALK_2 = {
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBBBBO...",
            "..OTTO....OBBYKBOO..",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBEO..",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".........OGO.OGO....",
            ".........OGO.OGGO...",
            ".........OGGGO......",
    };
    private static final String[] WALK_3 = {
            "....................",
            "............OO.OO...",
            "...........OEEOEEO..",
            "..OO.......OEEEEEO..",
            "..OTO......OBBBBBO..",
            "..OTTO.....OBBYKBOO.",
            "..OTATO....OBBBBBNNO",
            "..OTTATO...OBBBBBEO.",
            "..OTTTTOOOOOBBBBBEO.",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".......OGO...OGO....",
            ".......OGGGO.OGGGO..",
    };
    private static final String[] WALK_4 = {
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBBBBO...",
            "..OTTO....OBBYKBOO..",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBEO..",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBLLO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "........OGO.OGO.....",
            "........OGO.OGO.....",
            "............OGGGO...",
    };

    // ---- RUN (wings beating down and up) ----
    private static final String[] RUN_1 = {
            "....................",
            "....................",
            "............OO.OO...",
            "..OO.......OEEOEEO..",
            "..OTO......OEEEEEO..",
            "..OTTO.....OBBBBBO..",
            "..OTATO....OBBYKBOO.",
            "..OTTATO...OBBBBBNNO",
            "..OTTTTOOOOOBBBBBEO.",
            "..OTTTBBBBBBBBBBBEO.",
            "..OTTBBBBBBBBBBLLO..",
            "...OTOOOOOBBBBLLLO..",
            "...OAAAAAAOBBLLLO...",
            "....OAAAAAOOOOOO....",
            ".....OOGOO....OGO...",
            ".....OGGGO....OGGGO.",
    };
    private static final String[] RUN_2 = {
            "....................",
            "....OO......OO.OO...",
            "..OOAAO....OEEOEEO..",
            "..OAAAAO...OEEEEEO..",
            "..OAAAAAO..OBBBBBO..",
            "..OAAAAAAO.OBBYKBOO.",
            "..OOAAAAAOOOBBBBBNNO",
            "..OTOAAAAOOOBBBBBEO.",
            "..OTTOAAAOOOBBBBBEO.",
            "..OTTBBOAOBBBBBLLLO.",
            "...OTBBBOBBBBBBLLLO.",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".........OGOOGO.....",
            "........OGO..OGO....",
            "........OGGO.OGGGO..",
    };
    private static final String[] RUN_3 = {
            "....................",
            "....................",
            "............OO.OO...",
            "..OO.......OEEOEEO..",
            "..OTO......OEEEEEO..",
            "..OTTO.....OBBBBBO..",
            "..OTATO....OBBYKBOO.",
            "..OTTATO...OBBBBBNNO",
            "..OTTTTOOOOOBBBBBEO.",
            "..OTTTBBBBBBBBBBBEO.",
            "..OTTBBBBBBBBBBLLO..",
            "...OTOOOOOBBBBLLLO..",
            "...OAAAAAAOBBLLLO...",
            "....OAAAAAOOOOOO....",
            ".....OAOGO...OGO....",
            "......OOGGGO.OGGGO..",
    };
    private static final String[] RUN_4 = {
            "....................",
            "....OO......OO.OO...",
            "..OOAAO....OEEOEEO..",
            "..OAAAAO...OEEEEEO..",
            "..OAAAAAO..OBBBBBO..",
            "..OAAAAAAO.OBBYKBOO.",
            "..OOAAAAAOOOBBBBBNNO",
            "..OTOAAAAOOOBBBBBEO.",
            "..OTTOAAAOOOBBBBBEO.",
            "..OTTBBOAOBBBBBLLLO.",
            "...OTBBBOBBBBBBLLLO.",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".........OGOOGO.....",
            "........OGO..OGO....",
            "........OGGO.OGGGO..",
    };

    // ---- SIT (roosting, legs tucked away) ----
    private static final String[] SIT_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OO.OO....",
            "..OO......OEEOEEO...",
            "..OTO.....OEEEEEO...",
            "..OTTO....OBBBBBO...",
            "..OTATO...OBBYKBOO..",
            "..OTTATO..OBBBBBNNO.",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBBEO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "...OBBBBBBBBBLLLLO..",
            "....OOOOOOOOOOOOO...",
    };
    private static final String[] SIT_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "...........OO.OO....",
            "..OO......OEEOEEO...",
            "..OTO.....OEEEEEO...",
            "..OTTO....OBBBBBO...",
            "..OTATO...OBBOOBOO..",
            "..OTTATO..OBBBBBNNO.",
            "..OTTTTOOOOBBBBBEO..",
            "..OTTTBBBBBBBBBBEO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "...OBBBBBBBBBLLLLO..",
            "....OOOOOOOOOOOOO...",
    };

    // ---- SLEEP (roosting, head sunk in, breathing) ----
    private static final String[] SLEEP_A = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "..OO................",
            "..OTO.......OO.OO...",
            "..OTTO.....OEEOEEO..",
            "..OTATO....OEEEEEO..",
            "..OTTATO..OBBBBBBO..",
            "..OTTTTOOOOBOOBBBO..",
            "..OTTTBBBBOBBBBBBO..",
            "..OTTBBBAAAABBBLLO..",
            "...OTBAAAABBBBLLLO..",
            "...OBBBBBBBBBLLLLO..",
            "....OOOOOOOOOOOOO...",
    };
    private static final String[] SLEEP_B = {
            "....................",
            "....................",
            "....................",
            "....................",
            "....................",
            "..OO................",
            "..OTO...............",
            "..OTTO......OO.OO...",
            "..OTATO....OEEOEEO..",
            "..OTTATO...OEEEEEO..",
            "..OTTTTOOOOBBBBBBO..",
            "..OTTTBBBBOBOOBBBO..",
            "..OTTBBBAAOBBBBBBO..",
            "...OTBAAAABBBBLLLO..",
            "...OBBBBBBBBBLLLLO..",
            "....OOOOOOOOOOOOO...",
    };

    // ---- JUMP ----
    private static final String[] JUMP_A = {
            "............OO.OO...",
            "....OO.....OEEOEEO..",
            "..OOAAO....OEEEEEO..",
            "..OAAAAO...OBBBBBO..",
            "..OAAAAAO..OBBYKBOO.",
            "..OAAAAAAO.OBBBBBNNO",
            "..OOAAAAAOOOBBBBBEO.",
            "..OTOAAAAOOOBBBBBEO.",
            "..OTTOAAAOOBBBBBLLO.",
            "..OTTBBOAOBBBBBLLLO.",
            "...OTBBBOBBBBBBLLLO.",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "....................",
            ".........OGOOGO.....",
            ".........OGGOGGO....",
    };

    // ---- FALL (frantic flapping, alarmed) ----
    private static final String[] FALL_UP = {
            "....................",
            "...........OO.OO....",
            "....OO....OEEOEEO...",
            "..OOAAO...OEEEEEO...",
            "..OAAAAO..OBBWWBO...",
            "..OAAAAAO.OBBWKBOOO.",
            "..OAAAAAAOOBBBBBNNO.",
            "..OOAAAAAOOBBBBBOOO.",
            "..OTOAAAAOOBBBBBENO.",
            "..OTTOAAAOOBBBBBEOO.",
            "..OTTBBOAOBBBBBLLLO.",
            "...OTBBBOBBBBBBLLLO.",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            "........OGO..OGO....",
            ".......OGGO..OGGO...",
    };
    private static final String[] FALL_DOWN = {
            "....................",
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBWWBO...",
            "..OTTO....OBBWKBOOO.",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBOOO.",
            "..OTTTTOOOOBBBBBENO.",
            "..OTTTBBBBOBBBBBEO..",
            "..OTTBBBBBBBBBBLLO..",
            "...OTOOOOOBBBBLLLO..",
            "...OAAAAAAOBBLLLO...",
            "....OAAAAAOOOOOO....",
            ".....OAAOGO..OGO....",
            "......OOGGO..OGGO...",
    };

    // ---- HELD (flapping, legs dangling) ----
    private static final String[] HELD_A = {
            "....................",
            "...........OO.OO....",
            "....OO....OEEOEEO...",
            "..OOAAO...OEEEEEO...",
            "..OAAAAO..OBBWWBO...",
            "..OAAAAAO.OBBWKBOOO.",
            "..OAAAAAAOOBBBBBNNO.",
            "..OOAAAAAOOBBBBBOOO.",
            "..OTOAAAAOOBBBBBENO.",
            "..OTTOAAAOOBBBBBEOO.",
            "..OTTBBOAOBBBBBLLLO.",
            "...OTBBBOBBBBBBLLLO.",
            "....OOBBBBBBBLLLO...",
            "......OOOOOOOOOO....",
            ".........OGOOGO.....",
            ".........OGOOGO.....",
    };
    private static final String[] HELD_B = {
            "....................",
            "...........OO.OO....",
            "..........OEEOEEO...",
            "..OO......OEEEEEO...",
            "..OTO.....OBBWWBO...",
            "..OTTO....OBBWKBOOO.",
            "..OTATO...OBBBBBNNO.",
            "..OTTATO..OBBBBBOOO.",
            "..OTTTTOOOOBBBBBENO.",
            "..OTTTBBBBOBBBBBEO..",
            "..OTTBBBBBBBBBBLLO..",
            "...OTOOOOOBBBBLLLO..",
            "...OAAAAAAOBBLLLO...",
            "....OAAAAAOOOOOO....",
            ".....OAAOGO..OGO....",
            "......OOGGO..OGGO...",
    };

    static final PixelSheet SHEET = PixelSheet.builder(20, 16)
            .anim(Pose.STAND, 2.5, STAND_A, STAND_A, STAND_BLINK, STAND_A, PECK_A, PECK_B, PECK_A, PECK_B, STAND_A,
                    STAND_LOOK, STAND_LOOK, STAND_A, STAND_BLINK, STAND_A)
            .anim(Pose.WALK, 0, WALK_1, WALK_2, WALK_3, WALK_4)
            .anim(Pose.RUN, 0, RUN_1, RUN_2, RUN_3, RUN_4)
            .anim(Pose.SIT, 0.8, SIT_A, SIT_A, SIT_A, SIT_B)
            .anim(Pose.SLEEP, 1.2, SLEEP_A, SLEEP_B)
            .anim(Pose.JUMP, 4, JUMP_A)
            .anim(Pose.FALL, 8, FALL_UP, FALL_DOWN)
            .anim(Pose.HELD, 7, HELD_A, HELD_B)
            .build();

    static List<PetKind.Palette> palettes() {
        return List.of(
                // name, body, belly, leg, ear (comb + wattle), outline, eye, nose (beak), accent (wing), tailTip (tail)
                new PetKind.Palette("White", new Color(0xF4F1EA), new Color(0xFFFFFF), new Color(0xF2B33D),
                        new Color(0xE23B3B), new Color(0x4A4038), new Color(0xD9822B), new Color(0xF2B33D),
                        new Color(0xD8D1C3), new Color(0xE9E4D9)),
                new PetKind.Palette("Brown hen", new Color(0xB5642F), new Color(0xD99A5B), new Color(0xE9B44C),
                        new Color(0xD8312F), new Color(0x3E2213), new Color(0xE58A2A), new Color(0xEFC15A),
                        new Color(0x86441F), new Color(0x3B3A35)),
                new PetKind.Palette("Black", new Color(0x3F4350), new Color(0x585D6B), new Color(0x8C8A84),
                        new Color(0xE53935), new Color(0x0E0F13), new Color(0xE8A33A), new Color(0xA6A49C),
                        new Color(0x2E4A48), new Color(0x2F5E50)),
                new PetKind.Palette("Buff", new Color(0xE8B866), new Color(0xF6D9A0), new Color(0xF0C46A),
                        new Color(0xDB3B32), new Color(0x5A3A14), new Color(0xD9822B), new Color(0xF3CF7A),
                        new Color(0xC98F3E), new Color(0xD29A45)));
    }
}
