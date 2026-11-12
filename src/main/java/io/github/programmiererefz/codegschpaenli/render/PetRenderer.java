// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.render;

import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.pet.PetKind.Palette;
import io.github.programmiererefz.codegschpaenli.pet.PetKind.Spec;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Draws pets procedurally with Java2D. All geometry is expressed in "units" in a pet-local space whose
 * origin is on the ground, horizontally centred under the body, with the pet facing +x (right).
 * Negative y is up. A pet is about {@link #UNIT_LENGTH} units long.
 *
 * <p>Each pose builds two groups of parts (far/back parts and near/front parts). A group is drawn by first
 * painting every part in the outline colour with a wide stroke and then filling every part with its own colour,
 * which leaves a clean silhouette outline without lines between overlapping parts.</p>
 */
public final class PetRenderer {

    /** Nominal length of a pet in units, used to convert the configured pixel size into a scale. */
    public static final double UNIT_LENGTH = 34;
    /** Nominal height of a pet in units (ears included), used for hit boxes and dirty regions. */
    public static final double UNIT_HEIGHT = 30;

    private static final float OUTLINE = 2.0f;

    /** Per-frame animation input. */
    public record State(Pose pose, double time, double gaitPhase, double tilt, boolean blink, boolean happy,
                        boolean scared) {
    }

    private record Part(Shape shape, Color fill) {
    }

    private record Leg(double hipX, double hipY, double angle, double length, boolean far) {
    }

    private PetRenderer() {
    }

    /**
     * Paints a pet.
     *
     * @param g          target graphics (not modified)
     * @param kind       species
     * @param pal        colour palette
     * @param s          animation state
     * @param x          x of the pet's centre in g's coordinates
     * @param groundY    y of the surface the pet stands on, in g's coordinates
     * @param pxPerUnit  scale
     * @param faceRight  whether the pet looks to the right
     */
    public static void paint(Graphics2D g, PetKind kind, Palette pal, State s, double x, double groundY,
                             double pxPerUnit, boolean faceRight) {
        if (kind.isPixel()) {
            io.github.programmiererefz.codegschpaenli.pixel.PixelRenderer.paint(g, kind, pal, s, x, groundY, pxPerUnit * UNIT_LENGTH, faceRight);
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.translate(x, groundY);
            if (s.pose() == Pose.CLIMB || s.pose() == Pose.CLIMB_DOWN) {
                // Climbing: (x, groundY) is on the wall; the walking rig is turned so the feet touch the wall and
                // the head points up (or down). faceRight means the wall is to the pet's right.
                boolean down = s.pose() == Pose.CLIMB_DOWN;
                g2.rotate(faceRight ? -Math.PI / 2 : Math.PI / 2);
                g2.scale(faceRight != down ? pxPerUnit : -pxPerUnit, pxPerUnit);
                drawStanding(g2, kind, pal, new State(Pose.WALK, s.time(), s.gaitPhase(), 0, s.blink(), s.happy(), false));
                return;
            }
            g2.scale(faceRight ? pxPerUnit : -pxPerUnit, pxPerUnit);
            switch (s.pose()) {
                case SIT -> drawSitting(g2, kind, pal, s);
                case SLEEP -> drawLying(g2, kind, pal, s);
                default -> drawStanding(g2, kind, pal, s);
            }
        } finally {
            g2.dispose();
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // Poses
    // ------------------------------------------------------------------------------------------------------------

    private static void drawStanding(Graphics2D g, PetKind kind, Palette p, State s) {
        Spec sp = kind.spec();
        Pose pose = s.pose();
        double ph = s.gaitPhase();
        boolean moving = pose == Pose.WALK || pose == Pose.RUN;
        boolean hop = sp.gait() == PetKind.Gait.HOP;

        double bob;
        if (moving && hop) {
            bob = -Math.abs(Math.sin(ph)) * (pose == Pose.RUN ? 5 : 3);
        } else if (moving) {
            bob = Math.sin(ph * 2) * (pose == Pose.RUN ? 0.9 : 0.5);
        } else {
            bob = Math.sin(s.time() * 2.2) * 0.25; // breathing
        }

        double bodyCX = 0;
        double bodyCY = -sp.bodyLift() + bob;
        double hipY = bodyCY + sp.bodyH() * 0.22;
        double legLen = sp.bodyLift() - sp.bodyH() * 0.22;
        double fx = sp.bodyW() / 2 - 4;
        double bx = -sp.bodyW() / 2 + 4;

        double[] ang = legAngles(pose, ph, hop, s.time());
        List<Leg> legs = List.of(
                new Leg(fx + 1.6, hipY, ang[1], legLen, true),
                new Leg(bx + 1.6, hipY, ang[3], legLen, true),
                new Leg(fx, hipY, ang[0], legLen, false),
                new Leg(bx, hipY, ang[2], legLen, false));

        AffineTransform saved = g.getTransform();
        double tilt = s.tilt();
        if (pose == Pose.HELD) tilt += Math.sin(s.time() * 3) * 0.12;
        if (tilt != 0) g.rotate(tilt, bodyCX, bodyCY);

        double headX = bodyCX + sp.headDX();
        double headY = bodyCY + sp.headDY() + (moving ? Math.sin(ph * 2 + 0.6) * 0.35 : 0);

        List<Part> back = new ArrayList<>();
        List<Part> front = new ArrayList<>();
        List<Part> nearLegs = new ArrayList<>(); // drawn after the belly and coat details, which would cover them
        for (Leg l : legs) {
            Part part = new Part(legShape(l, sp.legW()), l.far() ? shade(p.leg(), 0.82) : p.leg());
            (l.far() ? back : nearLegs).add(part);
        }

        Point2D tailBase = new Point2D.Double(bodyCX - sp.bodyW() / 2 + 1.5, bodyCY - sp.bodyH() * 0.15);
        addTail(back, sp, p, s, tailBase, TailMode.UP);

        Ellipse2D body = new Ellipse2D.Double(bodyCX - sp.bodyW() / 2, bodyCY - sp.bodyH() / 2, sp.bodyW(), sp.bodyH());
        front.add(0, new Part(body, p.body()));

        Head head = new Head(headX, headY, sp.headR());
        List<Part> over = new ArrayList<>();
        addEars(back, front, over, sp, p, head, pose == Pose.RUN || pose == Pose.FALL || pose == Pose.JUMP, s, false);
        front.add(new Part(head.circle(), p.body()));
        addSnoutParts(front, sp, p, head);
        front.addAll(over);

        group(g, back, p.outline());
        group(g, front, p.outline());

        drawBelly(g, body, p);
        drawCoatDetails(g, kind, p, body, head);
        group(g, nearLegs, p.outline());
        drawFace(g, kind, p, head, s, false);

        g.setTransform(saved);
    }

    private static void drawSitting(Graphics2D g, PetKind kind, Palette p, State s) {
        Spec sp = kind.spec();
        double breath = Math.sin(s.time() * 2.2) * 0.25;
        double bx = -1.5;
        double torsoH = sp.bodyW() * 0.78;
        double torsoW = sp.bodyH() * 1.05;
        double torsoCY = -torsoH / 2 - 1.5 + breath;

        List<Part> back = new ArrayList<>();
        List<Part> front = new ArrayList<>();

        Point2D tailBase = new Point2D.Double(bx - torsoW * 0.35, -2.2);
        addTail(back, sp, p, s, tailBase, TailMode.GROUND);

        // far front leg
        back.add(new Part(legShape(new Leg(bx + 2.2, torsoCY + 2, 0, -(torsoCY + 2), true), sp.legW()), shade(p.leg(), 0.82)));

        Shape torso = rotated(new Ellipse2D.Double(bx - torsoW / 2, torsoCY - torsoH / 2, torsoW, torsoH), -0.32, bx, torsoCY);
        front.add(new Part(torso, p.body()));
        Shape haunch = new Ellipse2D.Double(bx - 6.5, -8.5, 10, 8.5);
        front.add(new Part(haunch, p.body()));
        front.add(new Part(new Ellipse2D.Double(bx - 1, -2.6, 5.5, 2.6), p.leg()));
        // near front leg: drawn after the belly, which would cover it
        List<Part> nearLeg = List.of(new Part(legShape(new Leg(bx + 4, torsoCY + 2, 0, -(torsoCY + 2), false), sp.legW()), p.leg()));

        double headY = torsoCY - torsoH / 2 - sp.headR() * 0.35 + breath * 0.5;
        Head head = new Head(bx + 4.2, headY, sp.headR());
        List<Part> over = new ArrayList<>();
        addEars(back, front, over, sp, p, head, false, s, false);
        front.add(new Part(head.circle(), p.body()));
        addSnoutParts(front, sp, p, head);
        front.addAll(over);

        group(g, back, p.outline());
        group(g, front, p.outline());

        // belly, in a frame centred on the torso
        fillWithin(g, AffineTransform.getTranslateInstance(bx, torsoCY), new WithinKey(Within.SIT_BELLY, torsoW, torsoH),
                () -> new Ellipse2D.Double(0.5, -torsoH * 0.25, torsoW * 0.75, torsoH * 0.9),
                () -> rotated(new Ellipse2D.Double(-torsoW / 2, -torsoH / 2, torsoW, torsoH), -0.32, 0, 0),
                p.belly());
        group(g, nearLeg, p.outline());

        drawFace(g, kind, p, head, s, false);
    }

    private static void drawLying(Graphics2D g, PetKind kind, Palette p, State s) {
        Spec sp = kind.spec();
        double breath = Math.sin(s.time() * 1.6) * 0.45;
        double bodyH = sp.bodyH() * 0.82 + breath;
        double bodyW = sp.bodyW() + 2;
        Ellipse2D body = new Ellipse2D.Double(-bodyW / 2, -bodyH - 0.2, bodyW, bodyH);

        List<Part> back = new ArrayList<>();
        List<Part> front = new ArrayList<>();
        front.add(new Part(body, p.body()));

        Point2D tailBase = new Point2D.Double(-bodyW / 2 + 1.5, -1.6);
        if (sp.tail() == PetKind.Tail.THIN || sp.tail() == PetKind.Tail.BUSHY || sp.tail() == PetKind.Tail.RINGED) {
            addTail(front, sp, p, s, tailBase, TailMode.WRAP);
        } else {
            addTail(back, sp, p, s, tailBase, TailMode.WRAP);
        }

        front.add(new Part(new Ellipse2D.Double(bodyW / 2 - 2.5, -2.6, 6, 2.6), p.leg()));
        Head head = new Head(bodyW / 2 - 2.2, -sp.headR() + 0.2, sp.headR() * 0.97);
        List<Part> over = new ArrayList<>();
        addEars(back, front, over, sp, p, head, false, s, true);
        front.add(new Part(head.circle(), p.body()));
        addSnoutParts(front, sp, p, head);
        front.addAll(over);

        group(g, back, p.outline());
        group(g, front, p.outline());

        // Shaded back. Breathing changes the body's height, which a translation cannot follow, so the shading is cached
        // for heights 1/8 unit apart and stretched to the current one around the body's bottom edge (y = -0.2): that
        // fits the body outline exactly and moves the inner edge of the shading by less than 1/500 unit.
        double cachedH = sp.bodyH() * 0.82 + Math.rint(breath * 8) / 8;
        AffineTransform place = AffineTransform.getTranslateInstance(0, -0.2);
        place.scale(1, bodyH / cachedH);
        fillWithin(g, place, new WithinKey(Within.LIE_SHADE, bodyW, cachedH),
                () -> new Ellipse2D.Double(-bodyW / 2, -cachedH * 1.4 + 0.2, bodyW, cachedH * 0.8),
                () -> new Ellipse2D.Double(-bodyW / 2, -cachedH, bodyW, cachedH),
                shade(p.body(), 0.92));

        drawFace(g, kind, p, head, s, true);
    }

    // ------------------------------------------------------------------------------------------------------------
    // Rig pieces
    // ------------------------------------------------------------------------------------------------------------

    /** Returns leg angles: near front, far front, near back, far back (positive = foot swings backwards). */
    private static double[] legAngles(Pose pose, double ph, boolean hop, double time) {
        switch (pose) {
            case WALK, RUN -> {
                double amp = pose == Pose.RUN ? 0.75 : 0.42;
                if (hop) {
                    double a = Math.sin(ph) * amp;
                    return new double[]{-a, -a * 0.9, a * 1.2, a};
                }
                return new double[]{
                        Math.sin(ph) * amp,
                        Math.sin(ph + Math.PI) * amp,
                        Math.sin(ph + Math.PI) * amp,
                        Math.sin(ph) * amp};
            }
            case JUMP -> {
                return new double[]{-0.85, -0.65, 0.85, 0.65};
            }
            case FALL -> {
                double w = Math.sin(time * 18) * 0.25;
                return new double[]{-0.5 + w, -0.35 - w, 0.5 - w, 0.35 + w};
            }
            case HELD -> {
                double w = Math.sin(time * 4) * 0.15;
                return new double[]{0.1 + w, 0.15 - w, -0.1 - w, -0.05 + w};
            }
            default -> {
                return new double[]{0, 0, 0, 0};
            }
        }
    }

    private static Shape legShape(Leg l, double w) {
        // a leg is a capsule from the hip down to the ground, rotated around the hip
        double len = l.length() / Math.max(0.6, Math.cos(l.angle()));
        RoundRectangle2D r = new RoundRectangle2D.Double(-w / 2, -w / 2, w, len + w / 2, w, w);
        AffineTransform t = new AffineTransform();
        t.translate(l.hipX(), l.hipY());
        t.rotate(l.angle());
        return t.createTransformedShape(r);
    }

    private enum TailMode { UP, GROUND, WRAP }

    private static void addTail(List<Part> parts, Spec sp, Palette p, State s, Point2D b, TailMode mode) {
        double t = s.time();
        double wag;
        if (sp.tail() == PetKind.Tail.WAG) {
            wag = s.happy() ? Math.sin(t * 16) * 2.6 : Math.sin(t * 4) * 1.0;
        } else {
            wag = Math.sin(t * (s.happy() ? 6 : 1.7)) * (s.happy() ? 2.0 : 1.2);
        }
        double x = b.getX();
        double y = b.getY();
        switch (sp.tail()) {
            case THIN -> {
                Path2D path = new Path2D.Double();
                path.moveTo(x, y);
                switch (mode) {
                    case UP -> path.curveTo(x - 5, y, x - 7.5, y - 6, x - 5.5 + wag, y - 11);
                    case GROUND -> path.curveTo(x - 4, y + 2, x - 9, y + 2, x - 10.5 + wag * 0.4, y - 3.5);
                    case WRAP -> path.curveTo(x - 3, y + 1.6, x + 6, y + 1.8, x + 13 + wag * 0.3, y + 0.6);
                }
                parts.add(new Part(stroke(path, 2.4f), p.body()));
            }
            case WAG -> {
                Path2D path = new Path2D.Double();
                path.moveTo(x, y);
                switch (mode) {
                    case UP -> path.quadTo(x - 4, y - 0.5, x - 5.5 + wag, y - 6.5);
                    case GROUND -> path.quadTo(x - 3, y + 1.5, x - 7 + wag * 0.3, y + 0.5);
                    case WRAP -> path.quadTo(x - 3, y + 0.5, x - 5 + wag * 0.2, y + 0.6);
                }
                parts.add(new Part(stroke(path, 2.8f), p.body()));
            }
            case BUSHY, RINGED -> {
                BushyTail tail = bushyTail(mode, wag, sp.tail() == PetKind.Tail.RINGED);
                AffineTransform at = AffineTransform.getTranslateInstance(x, y);
                parts.add(new Part(at.createTransformedShape(tail.tail()), p.body()));
                parts.add(new Part(at.createTransformedShape(tail.tip()), p.tailTip()));
                for (Shape band : tail.bands()) {
                    parts.add(new Part(at.createTransformedShape(band), p.accent()));
                }
            }
            case PUFF -> parts.add(new Part(new Ellipse2D.Double(x - 3.2, y - 2.6, 5.2, 5.2), p.tailTip()));
            case STUB -> parts.add(new Part(new Ellipse2D.Double(x - 2.2, y - 1.6, 3.2, 3.0), p.tailTip()));
            case CURLY -> {
                // a little corkscrew that springs about its base
                Path2D path = new Path2D.Double();
                path.moveTo(x, y);
                double dy = mode == TailMode.GROUND ? 1.5 : 0;
                path.curveTo(x - 2.6, y - 0.4 + dy, x - 4.6, y - 2.8 + dy, x - 3.0, y - 4.2 + dy);
                path.curveTo(x - 1.6, y - 5.2 + dy, x - 0.6, y - 3.4 + dy, x - 1.9, y - 2.6 + dy);
                path.curveTo(x - 2.9, y - 2.0 + dy, x - 4.4, y - 3.0 + dy, x - 5.2, y - 4.0 + dy);
                Shape curl = rotated(stroke(path, 1.5f), wag * 0.07, x, y);
                parts.add(new Part(curl, p.tailTip()));
            }
        }
    }

    /** A bushy tail, its differently coloured tip and, for ringed tails, its stripes, relative to the tail base. */
    private record BushyTail(Shape tail, Shape tip, List<Shape> bands) {
    }

    private record BushyTailKey(TailMode mode, long wagSteps, boolean ringed) {
    }

    /** Wag positions per unit for bushy tails: 1/64 unit is far below a pixel even on large pets and HiDPI screens. */
    private static final double BUSHY_WAG_STEPS = 64;
    private static final Map<BushyTailKey, BushyTail> BUSHY_TAILS = new ConcurrentHashMap<>();

    /**
     * The bushy tail for a wag offset. Its tip is the circle around the end of the tail intersected with the tail, an
     * expensive {@link Area} operation, so both are cached. The wag varies continuously and is rounded to
     * {@link #BUSHY_WAG_STEPS} positions per unit to make the cache work; that moves the tail by less than 1/100 of a
     * unit, which no screen shows.
     */
    private static BushyTail bushyTail(TailMode mode, double wag, boolean ringed) {
        return BUSHY_TAILS.computeIfAbsent(new BushyTailKey(mode, Math.round(wag * BUSHY_WAG_STEPS), ringed), k -> {
            double w = k.wagSteps() / BUSHY_WAG_STEPS;
            Path2D path = new Path2D.Double();
            path.moveTo(0, 0);
            Point2D tip;
            switch (k.mode()) {
                case UP -> {
                    tip = new Point2D.Double(-11.5, 0.5 + w * 0.6);
                    path.quadTo(-6, 2.5, tip.getX(), tip.getY());
                }
                case GROUND -> {
                    tip = new Point2D.Double(-10 + w * 0.3, 0);
                    path.quadTo(-5, 1.5, tip.getX(), tip.getY());
                }
                default -> {
                    tip = new Point2D.Double(14 + w * 0.2, 0.4);
                    path.curveTo(-4, 0.8, 5, 1.4, tip.getX(), tip.getY());
                }
            }
            Shape tail = stroke(path, 5.4f);
            double r = 3.4;
            List<Shape> bands = k.ringed()
                    ? List.of(bandAcross(path, 0.38, 1.9, tail), bandAcross(path, 0.62, 1.9, tail))
                    : List.of();
            return new BushyTail(tail, intersect(new Ellipse2D.Double(tip.getX() - r, tip.getY() - r, 2 * r, 2 * r), tail), bands);
        });
    }

    /** A stripe of the given width across {@code path} at fraction {@code f} of its length, clipped to {@code clip}. */
    private static Shape bandAcross(Path2D path, double f, double width, Shape clip) {
        List<double[]> pts = new ArrayList<>();
        java.awt.geom.PathIterator it = path.getPathIterator(null, 0.05);
        double[] c = new double[6];
        while (!it.isDone()) {
            it.currentSegment(c);
            pts.add(new double[]{c[0], c[1]});
            it.next();
        }
        double total = 0;
        for (int i = 1; i < pts.size(); i++) total += Point2D.distance(pts.get(i - 1)[0], pts.get(i - 1)[1], pts.get(i)[0], pts.get(i)[1]);
        double want = total * f;
        double run = 0;
        double px = pts.get(0)[0], py = pts.get(0)[1], ang = 0;
        for (int i = 1; i < pts.size(); i++) {
            double[] a = pts.get(i - 1), b = pts.get(i);
            double d = Point2D.distance(a[0], a[1], b[0], b[1]);
            ang = Math.atan2(b[1] - a[1], b[0] - a[0]);
            if (run + d >= want && d > 0) {
                double t = (want - run) / d;
                px = a[0] + (b[0] - a[0]) * t;
                py = a[1] + (b[1] - a[1]) * t;
                break;
            }
            run += d;
            px = b[0];
            py = b[1];
        }
        Shape band = rotated(new java.awt.geom.Rectangle2D.Double(px - width / 2, py - 6, width, 12), ang, px, py);
        java.awt.geom.Area a = new java.awt.geom.Area(band);
        a.intersect(new java.awt.geom.Area(clip));
        return a;
    }

    private record Head(double x, double y, double r) {
        Ellipse2D circle() {
            return new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        }
    }

    private static void addEars(List<Part> back, List<Part> front, List<Part> over, Spec sp, Palette p, Head h, boolean flap,
                                State s, boolean lying) {
        double flick = Math.max(0, Math.sin(s.time() * 0.9) - 0.96) * 12; // occasional twitch
        double r = h.r();
        switch (sp.ears()) {
            case POINTY, TALL -> {
                double tall = sp.ears() == PetKind.Ears.TALL ? 6.0 : 4.6;
                for (int i = 0; i < 2; i++) {
                    boolean far = i == 0;
                    double ex = h.x() + (far ? -2.8 : 1.6);
                    double tipShift = far ? -1.2 : -0.4 + (lying ? -1.4 : 0) - flick * 0.15;
                    Path2D ear = new Path2D.Double();
                    ear.moveTo(ex - 2.7, h.y() - r * 0.55);
                    ear.lineTo(ex + 2.5, h.y() - r * 0.72);
                    ear.lineTo(ex + tipShift, h.y() - r - tall + (lying ? 1.2 : 0));
                    ear.closePath();
                    Color outer = far ? shade(p.body(), 0.9) : p.body();
                    (far ? back : front).add(new Part(ear, outer));
                    if (!far) {
                        Path2D inner = new Path2D.Double();
                        inner.moveTo(ex - 1.2, h.y() - r * 0.72);
                        inner.lineTo(ex + 1.2, h.y() - r * 0.8);
                        inner.lineTo(ex + tipShift * 0.8, h.y() - r - tall * 0.62 + (lying ? 1 : 0));
                        inner.closePath();
                        front.add(new Part(inner, p.ear()));
                    }
                }
            }
            case FLOPPY -> {
                double swing = flap ? Math.sin(s.time() * 14) * 0.35 : 0;
                Shape ear = rotated(new Ellipse2D.Double(h.x() - 5.2, h.y() - r * 0.7, 4.4, 8.2),
                        0.38 + swing + (lying ? 0.4 : 0), h.x() - 3, h.y() - r * 0.6);
                over.add(new Part(ear, p.ear())); // hangs over the head
            }
            case LONG -> {
                for (int i = 0; i < 2; i++) {
                    boolean far = i == 0;
                    double ex = h.x() + (far ? -2.2 : 0.6);
                    double rot = (lying ? -1.25 : -0.28) + (far ? -0.18 : 0) + (flap ? Math.sin(s.time() * 10 + i) * 0.12 : 0)
                            - (far ? 0 : flick * 0.05);
                    double ey = h.y() - r + 1;
                    Shape ear = rotated(new Ellipse2D.Double(ex - 1.8, ey - 10.5, 3.6, 11.5), rot, ex, ey);
                    (far ? back : front).add(new Part(ear, far ? shade(p.body(), 0.9) : p.body()));
                    if (!far) {
                        front.add(new Part(rotated(new Ellipse2D.Double(ex - 0.8, ey - 9, 1.6, 8.5), rot, ex, ey), p.ear()));
                    }
                }
            }
            case ROUND -> {
                back.add(new Part(new Ellipse2D.Double(h.x() - 3.6 - 2.5, h.y() - r - 1.2, 5, 5), p.ear()));
                front.add(new Part(new Ellipse2D.Double(h.x() + 1.6 - 2.5, h.y() - r - 1.6, 5, 5), p.ear()));
            }
        }
    }

    private static void addSnoutParts(List<Part> front, Spec sp, Palette p, Head h) {
        switch (sp.snout()) {
            case 1 -> front.add(new Part(new Ellipse2D.Double(h.x() + h.r() * 0.45, h.y() + 0.2, 5.4, 4.0), p.belly()));
            case 2 -> {
                Path2D m = new Path2D.Double();
                m.moveTo(h.x() + 1.5, h.y() - 0.2);
                m.quadTo(h.x() + h.r() + 2.5, h.y() + 0.6, h.x() + h.r() + 3.6, h.y() + 2.0);
                m.quadTo(h.x() + h.r() + 1.5, h.y() + 4.4, h.x() + 1.5, h.y() + 4.2);
                m.closePath();
                front.add(new Part(m, p.belly()));
            }
            case 3 -> front.add(new Part(new Ellipse2D.Double(h.x() + h.r() - 1.4, h.y() - 0.3, 4.4, 4.0), p.accent()));
            default -> {
            }
        }
    }

    private static void drawBelly(Graphics2D g, Ellipse2D body, Palette p) {
        if (p.belly().equals(p.body())) return;
        double w = body.getWidth();
        double h = body.getHeight();
        fillWithin(g, bodyFrame(body), new WithinKey(Within.BELLY, w, h),
                () -> new Ellipse2D.Double(w * 0.18, h / 2 + h * 0.08, w * 0.72, h * 0.8),
                () -> new Ellipse2D.Double(0, 0, w, h), p.belly());
    }

    /** Local frame for shapes that follow the body: origin at the top left corner of the body's bounds. */
    private static AffineTransform bodyFrame(Ellipse2D body) {
        return AffineTransform.getTranslateInstance(body.getX(), body.getY());
    }

    private static void drawCoatDetails(Graphics2D g, PetKind kind, Palette p, Ellipse2D body, Head h) {
        if (kind == PetKind.CAT && !p.accent().equals(p.body()) && !"Tuxedo".equals(p.name())) {
            Graphics2D gc = (Graphics2D) g.create();
            gc.clip(body);
            gc.setColor(p.accent());
            gc.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double top = body.getY();
            for (int i = 0; i < 3; i++) {
                double sx = body.getCenterX() - 5 + i * 4;
                Path2D st = new Path2D.Double();
                st.moveTo(sx - 1, top - 0.5);
                st.quadTo(sx + 0.8, top + 2, sx, top + 4);
                gc.draw(st);
            }
            gc.dispose();
            g.setColor(p.accent());
            g.setStroke(new BasicStroke(0.9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new java.awt.geom.Line2D.Double(h.x() - 0.6, h.y() - h.r() + 0.6, h.x() - 0.2, h.y() - h.r() + 2.4));
            g.draw(new java.awt.geom.Line2D.Double(h.x() + 1.2, h.y() - h.r() + 0.5, h.x() + 1.2, h.y() - h.r() + 2.2));
        }
        double w = body.getWidth();
        double bh = body.getHeight();
        if (kind == PetKind.PANDA) {
            fillWithin(g, bodyFrame(body), new WithinKey(Within.PANDA_PATCH, w, bh),
                    () -> new Ellipse2D.Double(w * 0.52, -2, w * 0.3, bh + 4),
                    () -> new Ellipse2D.Double(0, 0, w, bh), p.accent());
        }
        if (kind == PetKind.DOG && "Husky".equals(p.name())) {
            fillWithin(g, bodyFrame(body), new WithinKey(Within.HUSKY_COAT, w, bh),
                    () -> new Ellipse2D.Double(-2, bh / 2 + 0.5, w + 4, bh),
                    () -> new Ellipse2D.Double(0, 0, w, bh), p.belly());
        }
    }

    private static void drawFace(Graphics2D g, PetKind kind, Palette p, Head h, State s, boolean sleeping) {
        Spec sp = kind.spec();
        double x = h.x();
        double y = h.y();
        double r = h.r();

        // light lower face / muzzle
        if (!p.belly().equals(p.body())) {
            fillWithin(g, AffineTransform.getTranslateInstance(x, y), new WithinKey(Within.MUZZLE, r, 0),
                    () -> new Ellipse2D.Double(r * 0.05, r * 0.2, r * 1.5, r * 1.1),
                    () -> new Ellipse2D.Double(-r, -r, 2 * r, 2 * r), p.belly());
        }

        // panda eye patches
        if (kind == PetKind.PANDA) {
            g.setColor(p.accent());
            g.fill(rotated(new Ellipse2D.Double(x + 0.3 - 2.1, y - 3.2, 4.2, 5.2), 0.45, x + 0.3, y - 0.6));
            g.fill(rotated(new Ellipse2D.Double(x + 3.7 - 1.8, y - 3.0, 3.6, 4.6), -0.35, x + 3.7, y - 0.7));
        }
        // raccoon bandit mask
        if (kind == PetKind.RACCOON) {
            fillWithin(g, AffineTransform.getTranslateInstance(x, y), new WithinKey(Within.RACCOON_MASK, r, 0),
                    () -> rotated(new Ellipse2D.Double(-3.2, -2.9, r + 5.6, 3.4), 0.08, 1, -1.2),
                    () -> new Ellipse2D.Double(-r, -r, 2 * r, 2 * r), p.accent());
        }

        // eyes (three-quarter view: two eyes towards the front of the face)
        boolean closed = sleeping || s.blink() || s.happy();
        double e1x = x + 0.4, e1y = y - 0.9;
        double e2x = x + 3.6, e2y = y - 1.1;
        if (closed) {
            g.setColor(p.outline());
            g.setStroke(new BasicStroke(0.75f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (double[] e : new double[][]{{e1x, e1y, 1.1}, {e2x, e2y, 0.95}}) {
                Path2D arc = new Path2D.Double();
                if (s.happy() && !sleeping) {
                    arc.moveTo(e[0] - e[2], e[1] + 0.4);
                    arc.quadTo(e[0], e[1] - 1.2, e[0] + e[2], e[1] + 0.4); // ^ ^
                } else {
                    arc.moveTo(e[0] - e[2], e[1]);
                    arc.quadTo(e[0], e[1] + 1.0, e[0] + e[2], e[1]); // u u
                }
                g.draw(arc);
            }
        } else {
            double big = s.scared() ? 1.35 : 1.0;
            Color eye = kind == PetKind.PANDA ? Color.WHITE : p.eye();
            fillEye(g, e1x, e1y, 1.15 * big, eye, p.outline());
            fillEye(g, e2x, e2y, 1.0 * big, eye, p.outline());
        }

        // nose
        g.setColor(p.nose());
        switch (sp.snout()) {
            case 1 -> g.fill(new Ellipse2D.Double(x + r * 0.45 + 3.9, y + 0.0, 2.2, 1.7));
            case 2 -> g.fill(new Ellipse2D.Double(x + r + 2.6, y + 1.1, 1.8, 1.5));
            case 3 -> {
                g.fill(new Ellipse2D.Double(x + r + 0.2, y + 1.0, 0.9, 1.4));
                g.fill(new Ellipse2D.Double(x + r + 1.6, y + 1.0, 0.9, 1.4));
            }
            default -> {
                Path2D n = new Path2D.Double();
                n.moveTo(x + r - 1.4, y + 1.0);
                n.lineTo(x + r + 0.1, y + 1.0);
                n.lineTo(x + r - 0.6, y + 1.9);
                n.closePath();
                g.fill(n);
            }
        }

        // blush
        Composite c = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g.setColor(new Color(0xFF7A8A));
        g.fill(new Ellipse2D.Double(x + 0.2, y + 1.2, 2.4, 1.3));
        g.setComposite(c);

        if (sp.whiskers()) {
            g.setColor(withAlpha(p.outline(), 150));
            g.setStroke(new BasicStroke(0.35f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double wx = x + r - 0.8;
            double wy = y + 1.7;
            g.draw(new java.awt.geom.Line2D.Double(wx, wy, wx + 3.6, wy - 0.8));
            g.draw(new java.awt.geom.Line2D.Double(wx, wy + 0.4, wx + 3.8, wy + 0.6));
        }
    }

    private static void fillEye(Graphics2D g, double x, double y, double r, Color iris, Color outline) {
        g.setColor(outline);
        g.fill(new Ellipse2D.Double(x - r, y - r * 1.15, 2 * r, 2 * r * 1.15));
        if (!iris.equals(outline)) {
            g.setColor(iris);
            g.fill(new Ellipse2D.Double(x - r * 0.72, y - r * 0.85, 2 * r * 0.72, 2 * r * 0.85));
            g.setColor(outline);
            g.fill(new Ellipse2D.Double(x - r * 0.35, y - r * 0.6, 2 * r * 0.35, 2 * r * 0.7));
        }
        g.setColor(Color.WHITE);
        g.fill(new Ellipse2D.Double(x + r * 0.05, y - r * 0.85, r * 0.6, r * 0.6));
    }

    // ------------------------------------------------------------------------------------------------------------
    // Overlays (drawn in screen space, unflipped)
    // ------------------------------------------------------------------------------------------------------------

    /** Floating hearts above a pet that is being petted. {@code age} runs from 0 to 1. */
    public static void paintHeart(Graphics2D g, double cx, double cy, double size, double age) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            float alpha = (float) Math.max(0, Math.min(1, 1 - age));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            double s = size;
            double x = cx;
            double y = cy - age * size * 2.5;
            GeneralPath h = new GeneralPath();
            h.moveTo(x, y + s * 0.35);
            h.curveTo(x - s * 0.1, y + s * 0.1, x - s * 0.6, y - s * 0.05, x - s * 0.5, y - s * 0.3);
            h.curveTo(x - s * 0.4, y - s * 0.55, x - s * 0.05, y - s * 0.5, x, y - s * 0.25);
            h.curveTo(x + s * 0.05, y - s * 0.5, x + s * 0.4, y - s * 0.55, x + s * 0.5, y - s * 0.3);
            h.curveTo(x + s * 0.6, y - s * 0.05, x + s * 0.1, y + s * 0.1, x, y + s * 0.35);
            h.closePath();
            g2.setColor(new Color(0xE8475F));
            g2.fill(h);
            g2.setColor(new Color(0x9E2236));
            g2.setStroke(new BasicStroke((float) Math.max(1, s * 0.08)));
            g2.draw(h);
        } finally {
            g2.dispose();
        }
    }

    /** Little "z" letters drifting up from a sleeping pet. */
    public static void paintZzz(Graphics2D g, double x, double y, double size, double time, Color color) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            for (int i = 0; i < 3; i++) {
                double t = (time * 0.45 + i / 3.0) % 1.0;
                float alpha = (float) (Math.sin(t * Math.PI));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, alpha)));
                g2.setColor(color);
                float fs = (float) (size * (0.55 + t * 0.5));
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, fs));
                g2.drawString("z", (float) (x + t * size * 1.2), (float) (y - t * size * 2.2));
            }
        } finally {
            g2.dispose();
        }
    }

    /** A small rounded name tag. */
    public static void paintNameTag(Graphics2D g, String name, double cx, double bottomY, Color bg, Color fg) {
        if (name == null || name.isBlank()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Font f = g2.getFont().deriveFont(Font.PLAIN, Math.max(9f, g2.getFont().getSize2D() - 2f));
            g2.setFont(f);
            FontMetrics fm = g2.getFontMetrics();
            int w = fm.stringWidth(name) + 8;
            int h = fm.getHeight();
            int x = (int) Math.round(cx - w / 2.0);
            int y = (int) Math.round(bottomY - h);
            g2.setColor(bg);
            g2.fillRoundRect(x, y, w, h, h, h);
            g2.setColor(fg);
            g2.drawString(name, x + 4, y + fm.getAscent());
        } finally {
            g2.dispose();
        }
    }

    /** A ball for pets to chase. */
    public static void paintBall(Graphics2D g, double cx, double cy, double r, double spin) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Ellipse2D ball = new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r);
            g2.setColor(new Color(0xE5484D));
            g2.fill(ball);
            g2.clip(ball);
            g2.setColor(new Color(0xFFD6D6));
            g2.setStroke(new BasicStroke((float) (r * 0.35)));
            double off = Math.sin(spin) * r * 0.8;
            g2.draw(new Ellipse2D.Double(cx - r * 0.35 + off, cy - r * 1.3, r * 0.7, r * 2.6));
            g2.setClip(null);
            g2.setColor(new Color(0x8C1D22));
            g2.setStroke(new BasicStroke((float) Math.max(1, r * 0.18)));
            g2.draw(ball);
        } finally {
            g2.dispose();
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------------------------------------------------

    private static void group(Graphics2D g, List<Part> parts, Color outline) {
        g.setColor(outline);
        g.setStroke(new BasicStroke(OUTLINE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Part p : parts) {
            g.fill(p.shape());
            g.draw(p.shape());
        }
        for (Part p : parts) {
            g.setColor(p.fill());
            g.fill(p.shape());
        }
    }

    /** What a {@link WithinKey} describes; each constant belongs to one {@link #fillWithin} call site. */
    private enum Within { BELLY, PANDA_PATCH, HUSKY_COAT, MUZZLE, RACCOON_MASK, SIT_BELLY, LIE_SHADE }

    /** Identifies the two local shapes of a {@link #fillWithin} call: the call site plus the sizes they are made of. */
    private record WithinKey(Within what, double a, double b) {
    }

    private static final Map<WithinKey, Shape> WITHIN = new ConcurrentHashMap<>();

    /**
     * Fills {@code shape} restricted to {@code clip}; uses area intersection so edges stay antialiased.
     *
     * <p>{@link Area} operations cost far more than drawing, so the intersection is computed once, in a local frame,
     * and cached: the two suppliers build the shapes in that frame and may only depend on what {@code key} holds.
     * {@code place} maps the local frame to the current user space; parts of a pet only move rigidly from frame to
     * frame (breathing, bobbing, walking), so this is mostly a translation and the result is the same as intersecting
     * in place.</p>
     */
    private static void fillWithin(Graphics2D g, AffineTransform place, WithinKey key, Supplier<Shape> shape,
                                   Supplier<Shape> clip, Color color) {
        Shape local = WITHIN.computeIfAbsent(key, k -> intersect(shape.get(), clip.get()));
        AffineTransform saved = g.getTransform();
        g.transform(place);
        g.setColor(color);
        g.fill(local);
        g.setTransform(saved);
    }

    private static Area intersect(Shape a, Shape b) {
        Area area = new Area(a);
        area.intersect(new Area(b));
        return area;
    }

    private static Shape stroke(Shape path, float width) {
        return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND).createStrokedShape(path);
    }

    private static Shape rotated(Shape s, double angle, double cx, double cy) {
        return AffineTransform.getRotateInstance(angle, cx, cy).createTransformedShape(s);
    }

    static Color shade(Color c, double f) {
        return new Color(clamp(c.getRed() * f), clamp(c.getGreen() * f), clamp(c.getBlue() * f), c.getAlpha());
    }

    private static Color withAlpha(Color c, int a) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
    }

    private static int clamp(double v) {
        return (int) Math.max(0, Math.min(255, Math.round(v)));
    }
}
