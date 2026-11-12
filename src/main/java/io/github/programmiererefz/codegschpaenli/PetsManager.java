// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import io.github.programmiererefz.codegschpaenli.overlay.PetOverlays;
import org.jetbrains.annotations.Nullable;

/**
 * Application service that owns the animation timer and one pet overlay per project window.
 * Started lazily by {@link PetsStartupActivity} and by the plugin's actions.
 */
@Service(Service.Level.APP)
public final class PetsManager implements Disposable {

    private PetOverlays overlays;

    public static PetsManager getInstance() {
        return ApplicationManager.getApplication().getService(PetsManager.class);
    }

    /** Idempotent. Must be called on the EDT. */
    public void ensureStarted() {
        ApplicationManager.getApplication().assertIsDispatchThread();
        if (overlays != null || ApplicationManager.getApplication().isHeadlessEnvironment()) return;
        overlays = new PetOverlays(this);
        overlays.start();
    }

    public void throwBall(@Nullable Project project) {
        ensureStarted();
        if (overlays == null) return;
        IdeFrame frame = project == null ? null : WindowManager.getInstance().getIdeFrame(project);
        overlays.throwBall(frame);
    }

    /** Pets that were told to leave for now come back. */
    public void callBackPets() {
        ensureStarted();
        if (overlays != null) overlays.callBack();
    }

    @Override
    public void dispose() {
        if (overlays != null) {
            overlays.stop();
            overlays = null;
        }
    }
}
