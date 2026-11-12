// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;

import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/** Keeps one {@link PetOverlay} attached to every open project window and drives the animation. */
public final class PetOverlays {

    static final int FRAME_MS = 16; // ~60 fps
    private static final int IDLE_MS = 500; // nothing to animate: just keep watching for new windows
    private static final long SYNC_EVERY_NANOS = 1_000_000_000L;

    private final Disposable parent;
    private final Map<JRootPane, PetOverlay> overlays = new IdentityHashMap<>();
    private Timer timer;
    private int frameMs = FRAME_MS;
    private long lastNanos;
    private int frameCounter;
    private long lastSync;
    private long seed = 0x5EED;
    private final DebugSnapshots debug = new DebugSnapshots();
    private final PerfProbe perf = new PerfProbe();
    private final TimerResolution resolution = new TimerResolution();
    /** Adopted pets sent home from their menu; they stay away until the next IDE start or a roll call. */
    private final Set<String> away = new HashSet<>();

    public PetOverlays(Disposable parent) {
        this.parent = parent;
    }

    public void start() {
        PetsSettings.getInstance().ensureFirstRunPet();
        DebugSnapshots.seedPets();
        ApplicationManager.getApplication().getMessageBus().connect(parent)
                .subscribe(PetsSettings.CHANGED, (Runnable) this::onSettingsChanged);
        timer = new Timer(FRAME_MS, e -> tick());
        timer.setCoalesce(true);
        timer.start();
        syncFrames();
    }

    public void stop() {
        if (timer != null) timer.stop();
        timer = null;
        resolution.setHigh(false);
        overlays.values().forEach(PetOverlay::detach);
        overlays.clear();
    }

    /** Frame interval while something moves. Only changed by {@link PerfProbe} to compare frame rates. */
    void setFrameMs(int ms) {
        frameMs = ms;
    }

    private void tick() {
        perf.beginTick();
        long now = System.nanoTime();
        double dt = lastNanos == 0 ? frameMs / 1000.0 : (now - lastNanos) / 1e9;
        lastNanos = now;
        frameCounter++;
        if (now - lastSync >= SYNC_EVERY_NANOS) {
            lastSync = now;
            syncFrames();
        }
        boolean busy = false;
        for (PetOverlay o : overlays.values()) {
            o.tick(Math.min(dt, 0.1));
            busy |= o.isBusy();
        }
        int delay = busy ? frameMs : IDLE_MS;
        if (timer != null && timer.getDelay() != delay) {
            timer.setDelay(delay);
            lastNanos = 0; // don't simulate the idle gap as one big step
        }
        resolution.setHigh(busy);
        debug.onFrame(frameCounter, overlays.values());
        perf.endTick(this, busy);
    }

    private void onSettingsChanged() {
        syncFrames();
        PetsSettings settings = PetsSettings.getInstance();
        for (PetOverlay o : overlays.values()) o.applySettings(settings.getState(), settings.size());
    }

    /** Attaches overlays to new project windows and detaches them from closed ones. */
    private void syncFrames() {
        PetsSettings settings = PetsSettings.getInstance();
        PetsSettings.StateBean st = settings.getState();
        // without anything to show, stay out of the window entirely (an overlay disables Swing's optimised drawing)
        boolean enabled = st.enabled && (!st.pets.isEmpty() || st.visitorsEnabled);
        Set<JRootPane> live = new HashSet<>();
        if (enabled) {
            for (IdeFrame frame : WindowManager.getInstance().getAllProjectFrames()) {
                JRootPane rp = rootPaneOf(frame);
                if (rp == null || !rp.isDisplayable()) continue;
                live.add(rp);
                if (!overlays.containsKey(rp)) {
                    PetOverlay o = new PetOverlay(rp, seed++, this);
                    overlays.put(rp, o);
                    o.applySettings(settings.getState(), settings.size());
                }
            }
        }
        overlays.entrySet().removeIf(e -> {
            if (!live.contains(e.getKey())) {
                e.getValue().detach();
                return true;
            }
            return false;
        });
    }

    private static JRootPane rootPaneOf(IdeFrame frame) {
        JComponent c = frame.getComponent();
        if (c == null) return null;
        if (c instanceof JRootPane rp) return rp;
        return SwingUtilities.getRootPane(c);
    }

    /** An adopted pet walks off and goes away in every window, until the next IDE start or {@link #callBack()}. */
    void sendHome(String id) {
        away.add(id);
        for (PetOverlay o : overlays.values()) o.sendAway(id);
    }

    boolean isAway(String id) {
        return away.contains(id);
    }

    /** Brings back the pets that were sent home. */
    public void callBack() {
        if (away.isEmpty()) return;
        away.clear();
        onSettingsChanged();
    }

    /** Throws a ball in the given window, or in the focused project window when null. */
    public void throwBall(IdeFrame frame) {
        syncFrames();
        PetOverlay target = null;
        if (frame != null) target = overlays.get(rootPaneOf(frame));
        if (target == null) {
            Window active = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
            for (PetOverlay o : overlays.values()) {
                if (SwingUtilities.getWindowAncestor(o.rootPane()) == active) target = o;
            }
        }
        if (target == null && !overlays.isEmpty()) target = overlays.values().iterator().next();
        if (target != null) target.throwBall();
    }
}
