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
import io.github.programmiererefz.codegschpaenli.ui.AdoptPetDialog;
import org.jetbrains.annotations.NotNull;

/** Opens the adopt dialog and adds the new pet to every IDE window. */
public final class AdoptPetAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AdoptPetDialog dialog = new AdoptPetDialog(e.getProject());
        if (dialog.showAndGet()) {
            PetsSettings settings = PetsSettings.getInstance();
            if (!settings.getState().enabled) settings.getState().enabled = true;
            settings.addPet(dialog.toEntry());
            PetsManager.getInstance().ensureStarted();
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
