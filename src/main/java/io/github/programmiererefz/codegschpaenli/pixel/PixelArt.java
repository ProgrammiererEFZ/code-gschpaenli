// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.pixel;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.pet.PetKind.Palette;

import java.util.List;

/** Registry of the pixel-art species: sprite sheets and colour palettes. */
public final class PixelArt {

    private PixelArt() {
    }

    /** The sprite sheet of a pixel-art species, or null for cartoon species. */
    public static PixelSheet sheet(PetKind kind) {
        return switch (kind.name()) {
            case "PIXEL_CAT" -> PixelCat.SHEET;
            case "PIXEL_DOG" -> PixelDog.SHEET;
            case "PIXEL_DUCK" -> PixelDuck.SHEET;
            case "PIXEL_FROG" -> PixelFrog.SHEET;
            case "PIXEL_HEDGEHOG" -> PixelHedgehog.SHEET;
            case "PIXEL_PENGUIN" -> PixelPenguin.SHEET;
            case "PIXEL_MOUSE" -> PixelMouse.SHEET;
            case "PIXEL_TURTLE" -> PixelTurtle.SHEET;
            case "PIXEL_OWL" -> PixelOwl.SHEET;
            case "PIXEL_SQUIRREL" -> PixelSquirrel.SHEET;
            case "PIXEL_LADYBUG" -> PixelLadybug.SHEET;
            case "PIXEL_SEAL" -> PixelSeal.SHEET;
            case "PIXEL_CRAB" -> PixelCrab.SHEET;
            default -> null;
        };
    }

    public static List<Palette> catPalettes() {
        return PixelCat.palettes();
    }

    public static List<Palette> dogPalettes() {
        return PixelDog.palettes();
    }

    public static List<Palette> duckPalettes() {
        return PixelDuck.palettes();
    }

    public static List<Palette> frogPalettes() {
        return PixelFrog.palettes();
    }

    public static List<Palette> hedgehogPalettes() {
        return PixelHedgehog.palettes();
    }

    public static List<Palette> penguinPalettes() {
        return PixelPenguin.palettes();
    }

    public static List<Palette> mousePalettes() {
        return PixelMouse.palettes();
    }

    public static List<Palette> turtlePalettes() {
        return PixelTurtle.palettes();
    }

    public static List<Palette> owlPalettes() {
        return PixelOwl.palettes();
    }

    public static List<Palette> squirrelPalettes() {
        return PixelSquirrel.palettes();
    }

    public static List<Palette> ladybugPalettes() {
        return PixelLadybug.palettes();
    }

    public static List<Palette> sealPalettes() {
        return PixelSeal.palettes();
    }

    public static List<Palette> crabPalettes() {
        return PixelCrab.palettes();
    }
}
