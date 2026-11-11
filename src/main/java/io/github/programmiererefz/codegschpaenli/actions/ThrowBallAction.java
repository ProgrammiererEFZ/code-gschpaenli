// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import io.github.programmiererefz.codegschpaenli.PetsManager;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.NotNull;

/** Throws a ball across the current IDE window; pets chase it. */
public final class ThrowBallAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PetsManager.getInstance().throwBall(e.getProject());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PetsSettings.StateBean s = PetsSettings.getInstance().getState();
        e.getPresentation().setEnabled(s.enabled && !s.pets.isEmpty());
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
