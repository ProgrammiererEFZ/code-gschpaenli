// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import io.github.programmiererefz.codegschpaenli.PetsManager;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.NotNull;

/** Turns random visitors (pets that come and go on their own) on or off. */
public final class ToggleVisitorsAction extends ToggleAction implements DumbAware {
    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return PetsSettings.getInstance().getState().visitorsEnabled;
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        PetsSettings settings = PetsSettings.getInstance();
        settings.getState().visitorsEnabled = state;
        if (state) settings.getState().enabled = true;
        settings.fireChanged();
        PetsManager.getInstance().ensureStarted();
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
