// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pet;

import java.awt.Color;
import java.util.List;

/**
 * The species of pet. Each species defines a body "rig" (proportions, ear and tail style)
 * and a set of colour variants. All art is drawn procedurally by {@link io.github.programmiererefz.codegschpaenli.render.PetRenderer}.
 */
public enum PetKind {
    CAT("Cat", new Spec(19, 10, 10.5, 6.2, 9.5, -6.5, 3.0, Ears.POINTY, Tail.THIN, 0, true, Gait.TROT),
            List.of(
                    new Palette("Ginger", c(0xE8994A), c(0xFFE6C7), c(0xE8994A), c(0xF2A7A0), c(0x7A3E12), c(0x2B1D14), c(0xE77C7C), c(0xC4702C), c(0xFFE6C7)),
                    new Palette("Black", c(0x34343C), c(0x4A4A55), c(0x34343C), c(0xC98B92), c(0x111116), c(0xE6C334), c(0xC98B92), c(0x2A2A30), c(0x34343C)),
                    new Palette("White", c(0xF5F2EC), c(0xFFFFFF), c(0xF5F2EC), c(0xF4AFB4), c(0x8C8A86), c(0x3E6FB0), c(0xEE8F98), c(0xE2DDD3), c(0xF5F2EC)),
                    new Palette("Grey", c(0x9AA1AB), c(0xD6DAE0), c(0x9AA1AB), c(0xE9A9B0), c(0x474C55), c(0x3B7A3B), c(0xD98A93), c(0x7F8792), c(0xD6DAE0)),
                    new Palette("Tuxedo", c(0x2E2E35), c(0xFFFFFF), c(0x2E2E35), c(0xE9A9B0), c(0x101014), c(0x7CC24A), c(0xE98C96), c(0x2E2E35), c(0xFFFFFF))
            )),
    DOG("Dog", new Spec(20, 11, 11.5, 6.4, 10, -6.5, 3.4, Ears.FLOPPY, Tail.WAG, 1, false, Gait.TROT),
            List.of(
                    new Palette("Brown", c(0xB27B4B), c(0xEED6B6), c(0xB27B4B), c(0x7A4E2A), c(0x4F2F17), c(0x2A1B10), c(0x2A1B10), c(0x8E5C33), c(0xEED6B6)),
                    new Palette("Golden", c(0xE5B563), c(0xFFF0CF), c(0xE5B563), c(0xC48D3E), c(0x7B5420), c(0x2A1B10), c(0x2A1B10), c(0xCF9C4A), c(0xFFF0CF)),
                    new Palette("Black", c(0x3A322E), c(0xA8805C), c(0x3A322E), c(0x2A2420), c(0x161210), c(0x2A1B10), c(0x111111), c(0x2A2420), c(0xA8805C)),
                    new Palette("Husky", c(0x8D96A3), c(0xFFFFFF), c(0xFFFFFF), c(0x6E7783), c(0x3C424B), c(0x5BA6E0), c(0x222222), c(0x6E7783), c(0xFFFFFF))
            )),
    FOX("Fox", new Spec(20, 9.5, 10.5, 5.8, 10, -6, 2.8, Ears.TALL, Tail.BUSHY, 2, false, Gait.TROT),
            List.of(
                    new Palette("Red", c(0xE36B2C), c(0xFFF6EC), c(0x4A2A1E), c(0x4A2A1E), c(0x6E2E0E), c(0x2A1B10), c(0x2A1B10), c(0xC2551C), c(0xFFF6EC)),
                    new Palette("Arctic", c(0xEEF1F5), c(0xFFFFFF), c(0xC9CFD8), c(0xA8B0BC), c(0x7D8591), c(0x2A2F38), c(0x2A2F38), c(0xD7DCE3), c(0xFFFFFF)),
                    new Palette("Silver", c(0x5D6068), c(0xD9DCE1), c(0x2C2D31), c(0x2C2D31), c(0x1C1D20), c(0xE8B33A), c(0x15161A), c(0x46484E), c(0xF5F5F5))
            )),
    BUNNY("Bunny", new Spec(15, 11, 8, 5.8, 7, -6, 3.2, Ears.LONG, Tail.PUFF, 0, false, Gait.HOP),
            List.of(
                    new Palette("White", c(0xF7F5F2), c(0xFFFFFF), c(0xF7F5F2), c(0xF6B3BC), c(0x8E8A86), c(0xC2405A), c(0xEE8F9A), c(0xE6E1DA), c(0xFFFFFF)),
                    new Palette("Brown", c(0xA98266), c(0xEADBCB), c(0xA98266), c(0xE8A9A9), c(0x52392A), c(0x241812), c(0xD98C8C), c(0x8E6A50), c(0xFFFFFF)),
                    new Palette("Grey", c(0xA4A6AC), c(0xE4E5E8), c(0xA4A6AC), c(0xE7AEB6), c(0x4D4F55), c(0x241812), c(0xD98C96), c(0x8A8C92), c(0xFFFFFF))
            )),
    PANDA("Panda", new Spec(20, 12, 11, 6.8, 9.5, -6, 4.0, Ears.ROUND, Tail.PUFF, 0, false, Gait.TROT),
            List.of(
                    new Palette("Classic", c(0xF7F7F4), c(0xFFFFFF), c(0x26262B), c(0x26262B), c(0x5A5A60), c(0x101012), c(0x26262B), c(0x26262B), c(0xF7F7F4)),
                    new Palette("Red", c(0xC9572A), c(0xF3E3D3), c(0x3A2219), c(0x3A2219), c(0x5A2A14), c(0x120A06), c(0x1A0E08), c(0x8E3A18), c(0xF3E3D3))
            )),
    RACCOON("Raccoon", new Spec(20, 10.5, 10, 6.2, 9.5, -6, 3.2, Ears.POINTY, Tail.RINGED, 2, true, Gait.TROT),
            List.of(
                    new Palette("Grey", c(0x8E9096), c(0xF2F1EE), c(0x3B3B40), c(0xE7E4DF), c(0x2A2A2E), c(0x8A5A2E), c(0x1B1B1E), c(0x34343A), c(0x34343A)),
                    new Palette("Brown", c(0x9C8572), c(0xF4EDE3), c(0x45362B), c(0xEDE3D6), c(0x2E241D), c(0x7A4E26), c(0x1E1712), c(0x3E3026), c(0x3E3026)),
                    new Palette("Blonde", c(0xD8C3A0), c(0xFFF8EC), c(0x8A7356), c(0xFFF4E2), c(0x5E4C36), c(0x6A4422), c(0x3A2C1C), c(0x7E6646), c(0x7E6646))
            )),
    HAMSTER("Hamster", new Spec(15, 11, 6.8, 6.3, 6.6, -4.2, 2.6, Ears.ROUND, Tail.STUB, 0, true, Gait.TROT),
            List.of(
                    new Palette("Golden", c(0xE3A45A), c(0xFFF4E2), c(0xF2C9A8), c(0xE9A9A0), c(0x7A4A1C), c(0x1E140C), c(0xE58A94), c(0xC98840), c(0xE3A45A)),
                    new Palette("Cream", c(0xF2DDB8), c(0xFFFBF2), c(0xF6CFC0), c(0xF0B0B0), c(0x8E7254), c(0x9E2236), c(0xE58A94), c(0xDCC39A), c(0xF2DDB8)),
                    new Palette("Winter white", c(0xB9BEC6), c(0xFFFFFF), c(0xF4D3CF), c(0xEBB1B6), c(0x4E535C), c(0x15161A), c(0xE58A94), c(0x6E737C), c(0xB9BEC6))
            )),
    PIG("Pig", new Spec(21, 12, 10, 6.4, 9.5, -5.5, 3.6, Ears.POINTY, Tail.CURLY, 3, false, Gait.TROT),
            List.of(
                    new Palette("Pink", c(0xF7B5BF), c(0xFFD9DE), c(0xF29FAC), c(0xE58494), c(0xA2465A), c(0x2A1A1E), c(0xA2465A), c(0xEF97A6), c(0xF7B5BF)),
                    new Palette("Tamworth", c(0xD9824A), c(0xEFAD7C), c(0xC46E38), c(0xA8562A), c(0x5E2C12), c(0x24160C), c(0x6E3418), c(0xE8A07A), c(0xD9824A)),
                    new Palette("Berkshire", c(0x3A3438), c(0x544C51), c(0xF2C9CF), c(0x2A2528), c(0x141113), c(0x8A5A2E), c(0x9E5262), c(0xF2BAC4), c(0x3A3438))
            )),

    // Pixel-art group: hand-made sprite sheets in io.github.programmiererefz.codegschpaenli.pixel
    PIXEL_CAT("Cat", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.catPalettes()),
    PIXEL_DOG("Dog", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.dogPalettes()),
    PIXEL_DUCK("Duck", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.duckPalettes()),
    PIXEL_FROG("Frog", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.frogPalettes()),
    PIXEL_HEDGEHOG("Hedgehog", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.hedgehogPalettes()),
    PIXEL_PENGUIN("Penguin", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.penguinPalettes()),
    PIXEL_MOUSE("Mouse", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.mousePalettes()),
    PIXEL_TURTLE("Turtle", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.turtlePalettes()),
    PIXEL_SEAL("Seal", Group.PIXEL, null, io.github.programmiererefz.codegschpaenli.pixel.PixelArt.sealPalettes());

    /** The two art styles: smooth vector "cartoon" pets and hand-made pixel-art pets. */
    public enum Group {
        CARTOON("Cartoon"), PIXEL("Pixel art");

        public final String label;

        Group(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum Ears { POINTY, TALL, FLOPPY, LONG, ROUND }

    /** RINGED is a bushy tail with accent-coloured bands; STUB is a tiny body-coloured nub; CURLY is a pig's corkscrew. */
    public enum Tail { THIN, WAG, BUSHY, PUFF, RINGED, STUB, CURLY }

    public enum Gait { TROT, HOP }

    /**
     * Rig proportions in "units" (cartoon pets only; null for pixel-art pets). A pet is roughly 32 units long; the renderer scales units to pixels.
     *
     * @param snout 0 = none, 1 = round muzzle, 2 = pointy muzzle, 3 = flat pig snout (accent colour, nose = nostrils)
     */
    public record Spec(double bodyW, double bodyH, double bodyLift, double headR, double headDX, double headDY,
                       double legW, Ears ears, Tail tail, int snout, boolean whiskers, Gait gait) {
    }

    /**
     * @param leg     colour of legs and paws
     * @param ear     outer ear colour (for pointy ears this is the inner-ear colour)
     * @param accent  stripes / patches / eye masks
     * @param tailTip colour of the tail tip (bushy tails) or puff
     */
    public record Palette(String name, Color body, Color belly, Color leg, Color ear, Color outline, Color eye,
                          Color nose, Color accent, Color tailTip) {
    }

    private final String displayName;
    private final Group group;
    private final Spec spec;
    private final List<Palette> palettes;

    PetKind(String displayName, Spec spec, List<Palette> palettes) {
        this(displayName, Group.CARTOON, spec, palettes);
    }

    PetKind(String displayName, Group group, Spec spec, List<Palette> palettes) {
        this.displayName = displayName;
        this.group = group;
        this.spec = spec;
        this.palettes = palettes;
    }

    public Group group() {
        return group;
    }

    public boolean isPixel() {
        return group == Group.PIXEL;
    }

    public static List<PetKind> inGroup(Group g) {
        List<PetKind> out = new java.util.ArrayList<>();
        for (PetKind k : values()) if (k.group == g) out.add(k);
        return out;
    }

    public String displayName() {
        return displayName;
    }

    public Spec spec() {
        return spec;
    }

    public List<Palette> palettes() {
        return palettes;
    }

    public Palette palette(String name) {
        for (Palette p : palettes) {
            if (p.name().equalsIgnoreCase(name)) return p;
        }
        return palettes.get(0);
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static PetKind parse(String s) {
        for (PetKind k : values()) {
            if (k.name().equalsIgnoreCase(s)) return k;
        }
        for (PetKind k : values()) {
            if (k.displayName.equalsIgnoreCase(s)) return k;
        }
        return CAT;
    }

    private static Color c(int rgb) {
        return new Color(rgb);
    }
}
