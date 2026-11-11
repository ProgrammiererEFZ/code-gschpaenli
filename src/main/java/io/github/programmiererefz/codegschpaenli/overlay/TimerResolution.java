// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.SystemInfo;
import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Asks Windows for 1 ms timer resolution while pets are animating. Without it, timed waits in the JVM (and so
 * {@code javax.swing.Timer}) only wake up on the ~15.6 ms system clock tick unless something else in the IDE happens
 * to raise the resolution, so a 16 ms frame randomly takes 16 or 31 ms and 60 fps stutters. Released again whenever
 * the animation idles. Does nothing on other systems or if the call is unavailable.
 */
final class TimerResolution {

    private static final Logger LOG = Logger.getInstance(TimerResolution.class);

    @SuppressWarnings("UnusedReturnValue")
    private interface WinMM extends Library {
        int timeBeginPeriod(int periodMs);

        int timeEndPeriod(int periodMs);
    }

    private static WinMM winmm;
    private static boolean unavailable = !SystemInfo.isWindows;

    private boolean raised;

    /** Requests (true) or releases (false) 1 ms resolution; repeated calls with the same value do nothing. */
    void setHigh(boolean high) {
        if (high == raised) return;
        WinMM w = winmm();
        if (w == null) return;
        if (high) {
            w.timeBeginPeriod(1);
        } else {
            w.timeEndPeriod(1);
        }
        raised = high;
    }

    private static WinMM winmm() {
        if (winmm == null && !unavailable) {
            try {
                winmm = Native.load("winmm", WinMM.class);
            } catch (Throwable t) { // UnsatisfiedLinkError and friends: just keep the coarse timer
                unavailable = true;
                LOG.info("Code Gschpänli: timer resolution can't be raised, animation may be less smooth", t);
            }
        }
        return winmm;
    }
}
