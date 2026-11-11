// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.ColoredListCellRenderer;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.NotNull;

/** Shows a list of pets and removes the chosen one. */
public final class RemovePetAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PetsSettings settings = PetsSettings.getInstance();
        var popup = JBPopupFactory.getInstance()
                .createPopupChooserBuilder(settings.pets())
                .setTitle("Say Goodbye To")
                .setRenderer(new ColoredListCellRenderer<PetsSettings.PetEntry>() {
                    @Override
                    protected void customizeCellRenderer(@NotNull javax.swing.JList<? extends PetsSettings.PetEntry> list,
                                                         PetsSettings.PetEntry p, int index, boolean selected, boolean focus) {
                        append(p.name + "  (" + p.palette().name() + " " + p.petKind().displayName() + ")");
                    }
                })
                .setItemChosenCallback(p -> settings.removePet(p.id))
                .createPopup();
        if (e.getProject() != null) popup.showCenteredInCurrentWindow(e.getProject());
        else popup.showInFocusCenter();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(!PetsSettings.getInstance().getState().pets.isEmpty());
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
