// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

/** When the plugin is installed or updated without an IDE restart, start the pets in already-open windows. */
public final class PetsPluginListener implements DynamicPluginListener {
    static final String PLUGIN_ID = "io.github.programmiererefz.codegschpaenli";

    @Override
    public void pluginLoaded(@NotNull IdeaPluginDescriptor pluginDescriptor) {
        if (PLUGIN_ID.equals(pluginDescriptor.getPluginId().getIdString())) {
            ApplicationManager.getApplication().invokeLater(() -> PetsManager.getInstance().ensureStarted());
        }
    }
}
