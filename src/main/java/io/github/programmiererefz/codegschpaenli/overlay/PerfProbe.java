// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.scale.JBUIScale;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Development aid, inactive unless the JVM is started with {@code -Dcodegschpaenli.perf.log=<file>} (see the
 * {@code petsPerfLog} Gradle property). Runs a fixed benchmark in the sandbox IDE: after letting the IDE settle, it
 * cycles a few times through the phases below, measures what each phase costs and appends the results to the log
 * file. When it is done it restores the pets and settings it found and closes the IDE.
 *
 * <p>Timings are wall-clock time on the EDT. "repaint" is {@code paintImmediately} for the dirty areas, which includes
 * repainting the IDE components underneath the pets; "draw" is only the plugin's own {@code paintComponent}, also
 * when the IDE repaints an area under the overlay for its own reasons.
 */
final class PerfProbe {

    private static final Logger LOG = Logger.getInstance(PerfProbe.class);
    static final String LOG_FILE = System.getProperty("codegschpaenli.perf.log");
    static final boolean ENABLED = LOG_FILE != null && !LOG_FILE.isBlank();

    // ---- sections timed from PetOverlay ------------------------------------------------------------------------

    static final int SIM = 0;
    static final int SCAN = 1;
    static final int REPAINT = 2;
    static final int DRAW = 3;
    private static final String[] SECTION_NAMES = {"sim", "scan", "repaint", "draw"};
    private static final long[] sectionNanos = new long[SECTION_NAMES.length];
    private static long repaintedPixels;

    /** Start of a timed section, or 0 when measuring is off. */
    static long now() {
        return ENABLED ? System.nanoTime() : 0;
    }

    /** Adds the time since {@code since} to a section and returns the current time, for chaining sections. */
    static long lap(int section, long since) {
        if (!ENABLED) return 0;
        long n = System.nanoTime();
        sectionNanos[section] += n - since;
        return n;
    }

    static void addRepaintedArea(Rectangle r) {
        if (ENABLED) repaintedPixels += (long) r.width * r.height;
    }

    // ---- benchmark schedule ------------------------------------------------------------------------------------

    private static final long SETTLE_NANOS = Long.getLong("codegschpaenli.perf.settleSeconds", 60) * 1_000_000_000L;
    private static final long PHASE_NANOS = Long.getLong("codegschpaenli.perf.phaseSeconds", 30) * 1_000_000_000L;
    private static final long WARMUP_NANOS = 5_000_000_000L;
    /** Round 1 only warms up (the JIT compiles the paint paths then) and is left out of the averages. */
    private static final int ROUNDS = Integer.getInteger("codegschpaenli.perf.rounds", 4);

    private record Phase(String name, int pets, int frameMs) {
    }

    private static final List<Phase> PHASES = List.of(
            new Phase("off", 0, PetOverlays.FRAME_MS),
            new Phase("1 pet, 30 fps", 1, 33),
            new Phase("1 pet, 60 fps", 1, 16),
            new Phase("6 pets, 30 fps", 6, 33),
            new Phase("6 pets, 60 fps", 6, 16));

    /** Half cartoon (vector), half pixel art. */
    private static final PetKind[] KINDS = {PetKind.CAT, PetKind.PIXEL_CAT, PetKind.DOG, PetKind.PIXEL_DUCK, PetKind.FOX,
            PetKind.PIXEL_FROG};

    private static final com.sun.management.ThreadMXBean THREADS =
            (com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean();
    private static final com.sun.management.OperatingSystemMXBean OS =
            (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static final CompilationMXBean JIT = ManagementFactory.getCompilationMXBean();

    private long firstTick;
    private boolean editorOpened;
    private PetsSettings.StateBean original;
    private List<PetsSettings.PetEntry> benchPets;
    private int step = -1; // index into rounds * PHASES; -1 while settling
    private long phaseStart;
    private Sample sample;
    private boolean finished;
    private final Map<String, List<Sample>> results = new LinkedHashMap<>();

    private long tickStart;
    private long tickAlloc;

    /** One measured phase. */
    private static final class Sample {
        final String phase;
        final int frameMs;
        long wall0, cpu0, edtCpu0, jit0, gcCount0, gcMillis0, pixels0;
        final long[] sections0 = new long[SECTION_NAMES.length];
        long wall, cpu, edtCpu, jit, gcCount, gcMillis, pixels;
        final long[] sections = new long[SECTION_NAMES.length];
        long frames, tickNanos, allocBytes, lastFrameStart;
        LongList ticks = new LongList();
        LongList intervals = new LongList();

        Sample(String phase, int frameMs) {
            this.phase = phase;
            this.frameMs = frameMs;
        }

        /** Called on the EDT, so the thread CPU time is the EDT's. */
        void begin() {
            wall0 = System.nanoTime();
            cpu0 = OS.getProcessCpuTime();
            edtCpu0 = THREADS.getCurrentThreadCpuTime();
            jit0 = JIT.getTotalCompilationTime();
            gcCount0 = gcCount();
            gcMillis0 = gcMillis();
            pixels0 = repaintedPixels;
            System.arraycopy(sectionNanos, 0, sections0, 0, sections0.length);
        }

        void end() {
            wall = System.nanoTime() - wall0;
            cpu = OS.getProcessCpuTime() - cpu0;
            edtCpu = THREADS.getCurrentThreadCpuTime() - edtCpu0;
            jit = JIT.getTotalCompilationTime() - jit0;
            gcCount = gcCount() - gcCount0;
            gcMillis = gcMillis() - gcMillis0;
            pixels = repaintedPixels - pixels0;
            for (int i = 0; i < sections.length; i++) sections[i] = sectionNanos[i] - sections0[i];
        }

        double seconds() {
            return wall / 1e9;
        }
    }

    // ---- hooks from PetOverlays.tick() -------------------------------------------------------------------------

    void beginTick() {
        if (!ENABLED || finished) return;
        tickStart = System.nanoTime();
        tickAlloc = THREADS.getCurrentThreadAllocatedBytes();
    }

    void endTick(PetOverlays overlays, boolean busy) {
        if (!ENABLED || finished) return;
        long end = System.nanoTime();
        if (sample != null) {
            if (busy) {
                long dur = end - tickStart;
                sample.frames++;
                sample.tickNanos += dur;
                sample.ticks.add(dur);
                sample.allocBytes += THREADS.getCurrentThreadAllocatedBytes() - tickAlloc;
                if (sample.lastFrameStart != 0) sample.intervals.add(tickStart - sample.lastFrameStart);
                sample.lastFrameStart = tickStart;
            }
        }
        try {
            advance(overlays, end);
        } catch (RuntimeException e) {
            LOG.warn("Code Gschpänli perf probe failed", e);
            finished = true;
        }
    }

    private void advance(PetOverlays overlays, long now) {
        if (firstTick == 0) {
            firstTick = now;
            PetsSettings s = PetsSettings.getInstance();
            original = s.copyState();
            benchPets = new ArrayList<>();
            for (int i = 0; i < KINDS.length; i++) {
                PetKind k = KINDS[i];
                benchPets.add(new PetsSettings.PetEntry(k, k.palettes().get(0).name(), k.displayName() + " " + (i + 1)));
            }
            apply(overlays, PHASES.get(0)); // settle with the pets off
            write("# Code Gschpänli perf run, " + LocalDateTime.now().withNano(0));
        }
        if (!editorOpened) editorOpened = openSomeFile();

        if (step < 0) {
            if (now - firstTick < SETTLE_NANOS) return;
            writeEnvironment();
            startStep(overlays, 0, now);
            return;
        }
        if (sample == null && now - phaseStart >= WARMUP_NANOS) {
            Phase ph = currentPhase();
            sample = new Sample(ph.name(), ph.frameMs());
            sample.begin();
        }
        if (now - phaseStart < PHASE_NANOS) return;

        if (sample != null) {
            sample.end();
            int round = step / PHASES.size() + 1;
            boolean warmUp = round == 1 && ROUNDS > 1;
            if (!warmUp) results.computeIfAbsent(sample.phase, k -> new ArrayList<>()).add(sample);
            write(String.format("round %d%s  %s", round, warmUp ? " (warm-up)" : "", line(List.of(sample))));
        }
        if (step + 1 < ROUNDS * PHASES.size()) {
            startStep(overlays, step + 1, now);
        } else {
            finish(overlays);
        }
    }

    private Phase currentPhase() {
        return PHASES.get(step % PHASES.size());
    }

    private void startStep(PetOverlays overlays, int newStep, long now) {
        step = newStep;
        phaseStart = now;
        sample = null;
        apply(overlays, currentPhase());
    }

    private void apply(PetOverlays overlays, Phase ph) {
        PetsSettings s = PetsSettings.getInstance();
        PetsSettings.StateBean st = s.getState();
        st.visitorsEnabled = false;
        st.enabled = ph.pets() > 0;
        st.pets = new ArrayList<>(benchPets.subList(0, ph.pets()));
        overlays.setFrameMs(ph.frameMs());
        s.fireChanged();
    }

    private void finish(PetOverlays overlays) {
        finished = true;
        StringBuilder sb = new StringBuilder("\n## Averages over rounds 2..").append(ROUNDS).append("\n\n");
        sb.append("| phase | fps | frame interval p50 / p95 / max | tick avg / p95 / max | tick share | sim | scan |"
                + " repaint | draw (all) | repainted px / frame | tick alloc | EDT CPU | process CPU | JIT | GC |\n");
        sb.append("|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|\n");
        for (Map.Entry<String, List<Sample>> e : results.entrySet()) {
            sb.append("| ").append(e.getKey()).append(" | ").append(row(e.getValue())).append(" |\n");
        }
        sb.append("\nShares are of wall-clock time. EDT CPU = all work on the UI thread, process CPU = the whole IDE"
                        + " process, both in % of one core (machine has ")
                .append(Runtime.getRuntime().availableProcessors()).append(" logical cores). JIT = compiler time.\n");
        write(sb.toString());
        PetsSettings.getInstance().update(original);
        overlays.setFrameMs(PetOverlays.FRAME_MS);
        write("done, settings restored");
        ApplicationManager.getApplication().invokeLater(() -> ApplicationManager.getApplication().exit(true, true, false));
    }

    // ---- reporting ---------------------------------------------------------------------------------------------

    private String line(List<Sample> samples) {
        return String.format("%-15s %s", samples.get(0).phase, row(samples).replace(" | ", "  "));
    }

    private String row(List<Sample> samples) {
        double secs = 0, frames = 0, tickNanos = 0, alloc = 0, cpu = 0, edtCpu = 0, jitMillis = 0, gcCount = 0,
                gcMillis = 0, pixels = 0;
        double[] sections = new double[SECTION_NAMES.length];
        LongList ticks = new LongList();
        LongList intervals = new LongList();
        for (Sample s : samples) {
            secs += s.seconds();
            frames += s.frames;
            tickNanos += s.tickNanos;
            alloc += s.allocBytes;
            cpu += s.cpu;
            edtCpu += s.edtCpu;
            jitMillis += s.jit;
            gcCount += s.gcCount;
            gcMillis += s.gcMillis;
            pixels += s.pixels;
            for (int i = 0; i < sections.length; i++) sections[i] += s.sections[i];
            ticks.addAll(s.ticks);
            intervals.addAll(s.intervals);
        }
        double wallNanos = secs * 1e9;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.1f", frames / secs)).append(" | ");
        sb.append(intervals.size() == 0 ? "-" : String.format("%.1f / %.1f / %.1f ms", ms(intervals.percentile(50)),
                ms(intervals.percentile(95)), ms(intervals.max()))).append(" | ");
        sb.append(ticks.size() == 0 ? "-" : String.format("%.2f / %.2f / %.1f ms", ms(tickNanos / frames),
                ms(ticks.percentile(95)), ms(ticks.max()))).append(" | ");
        sb.append(pct(tickNanos / wallNanos)).append(" | ");
        for (int i = 0; i < sections.length; i++) sb.append(pct(sections[i] / wallNanos)).append(" | ");
        sb.append(frames == 0 ? "-" : String.format("%.0f k", pixels / frames / 1000)).append(" | ");
        sb.append(String.format("%.0f KB/s", alloc / secs / 1024)).append(" | ");
        sb.append(String.format("%.1f%%", edtCpu / wallNanos * 100)).append(" | ");
        sb.append(String.format("%.1f%%", cpu / wallNanos * 100)).append(" | ");
        sb.append(String.format("%.1f%%", jitMillis / (secs * 1000) * 100)).append(" | ");
        sb.append(String.format("%.1f/min, %.0f ms/min", gcCount / secs * 60, gcMillis / secs * 60));
        return sb.toString();
    }

    private static String pct(double fraction) {
        return String.format("%.2f%%", fraction * 100);
    }

    private static double ms(double nanos) {
        return nanos / 1e6;
    }

    private void writeEnvironment() {
        StringBuilder sb = new StringBuilder();
        sb.append("IDE ").append(ApplicationInfo.getInstance().getFullApplicationName())
                .append(", Java ").append(System.getProperty("java.runtime.version"))
                .append(", ").append(System.getProperty("os.name")).append(' ').append(System.getProperty("os.version"))
                .append(", ").append(Runtime.getRuntime().availableProcessors()).append(" logical cores\n");
        try {
            var dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
            sb.append("screen ").append(dm.getWidth()).append('x').append(dm.getHeight()).append(" @ ")
                    .append(dm.getRefreshRate()).append(" Hz, scale ").append(JBUIScale.sysScale()).append('\n');
        } catch (RuntimeException e) {
            sb.append("screen: unknown\n");
        }
        for (IdeFrame f : WindowManager.getInstance().getAllProjectFrames()) {
            var c = f.getComponent();
            if (c != null) sb.append("window ").append(c.getWidth()).append('x').append(c.getHeight()).append('\n');
        }
        sb.append("pet size ").append(PetsSettings.getInstance().size()).append(", names ")
                .append(PetsSettings.getInstance().getState().showNames ? "on" : "off").append('\n');
        sb.append(String.format("settle %ds, phases of %ds (first 5s discarded), %d rounds (round 1 = warm-up)%n",
                SETTLE_NANOS / 1_000_000_000L, PHASE_NANOS / 1_000_000_000L, ROUNDS));
        write(sb.toString());
    }

    private void write(String text) {
        LOG.info(text);
        try {
            Files.writeString(Path.of(LOG_FILE), text + "\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.warn("Code Gschpänli perf log could not be written", e);
        }
    }

    // ---- helpers -----------------------------------------------------------------------------------------------

    /** Opens a file of the project in the editor, so the pets walk over real editor content. */
    private static boolean openSomeFile() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        if (projects.length == 0) return false;
        for (Project p : projects) {
            VirtualFile dir = ProjectUtil.guessProjectDir(p);
            if (dir == null) continue;
            for (VirtualFile f : dir.getChildren()) {
                if (!f.isDirectory()) {
                    FileEditorManager.getInstance(p).openFile(f, true);
                    break;
                }
            }
        }
        return true;
    }

    private static long gcCount() {
        long n = 0;
        for (GarbageCollectorMXBean b : ManagementFactory.getGarbageCollectorMXBeans()) n += Math.max(0, b.getCollectionCount());
        return n;
    }

    private static long gcMillis() {
        long n = 0;
        for (GarbageCollectorMXBean b : ManagementFactory.getGarbageCollectorMXBeans()) n += Math.max(0, b.getCollectionTime());
        return n;
    }

    /** Minimal growable list of longs. */
    private static final class LongList {
        private long[] a = new long[256];
        private int n;

        void add(long v) {
            if (n == a.length) a = Arrays.copyOf(a, n * 2);
            a[n++] = v;
        }

        void addAll(LongList o) {
            for (int i = 0; i < o.n; i++) add(o.a[i]);
        }

        int size() {
            return n;
        }

        double percentile(int p) {
            long[] s = Arrays.copyOf(a, n);
            Arrays.sort(s);
            return s[Math.min(n - 1, (int) Math.ceil(p / 100.0 * n) - 1)];
        }

        double max() {
            long m = 0;
            for (int i = 0; i < n; i++) m = Math.max(m, a[i]);
            return m;
        }
    }
}
