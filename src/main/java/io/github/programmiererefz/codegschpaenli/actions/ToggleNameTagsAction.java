// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.NotNull;

/** Shows or hides the name tags above the pets. */
public final class ToggleNameTagsAction extends ToggleAction implements DumbAware {
    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return PetsSettings.getInstance().getState().showNames;
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        PetsSettings settings = PetsSettings.getInstance();
        settings.getState().showNames = state;
        settings.fireChanged();
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
