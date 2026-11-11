// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.actions;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.text.StringUtil;
import io.github.programmiererefz.codegschpaenli.settings.PetNames;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.NotNull;

/** Every pet introduces itself in a notification. */
public final class RollCallAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StringBuilder sb = new StringBuilder();
        for (PetsSettings.PetEntry p : PetsSettings.getInstance().pets()) {
            sb.append("<b>").append(StringUtil.escapeXmlEntities(p.name)).append("</b> (")
                    .append(p.palette().name()).append(' ').append(p.petKind().displayName()).append("): ")
                    .append(StringUtil.escapeXmlEntities(PetNames.greeting(p.petKind()))).append("<br>");
        }
        if (sb.isEmpty()) sb.append("No pets yet. Use Tools | Code Gschpänli | Adopt a Pet...");
        NotificationGroupManager.getInstance().getNotificationGroup("Code Gschpänli")
                .createNotification("Roll call", sb.toString(), NotificationType.INFORMATION)
                .notify(e.getProject());
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
