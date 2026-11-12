// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.overlay;

import com.intellij.ide.DataManager;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.awt.RelativePoint;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import io.github.programmiererefz.codegschpaenli.world.Pet;
import io.github.programmiererefz.codegschpaenli.world.World;
import org.jetbrains.annotations.NotNull;

import java.awt.Point;
import java.util.function.Consumer;

/**
 * The right-click menu of a pet: pet it, play, follow, nap, stay, rename, recolour, adopt (visitors) and leave.
 * Built fresh for every click, so it only lists what makes sense for that pet right now.
 */
final class PetMenu {

    /** How long "Follow Me" lasts. */
    static final double FOLLOW_SECONDS = 20;
    static final int MAX_NAME_LENGTH = 24;

    private PetMenu() {
    }

    static void show(PetOverlay overlay, Pet pet, Point at) {
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(title(pet), build(overlay, pet),
                DataManager.getInstance().getDataContext(overlay), JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, false);
        popup.show(new RelativePoint(overlay, at));
    }

    private static String title(Pet p) {
        String what = p.palette.name() + " " + p.kind.displayName();
        return p.name + " (" + (p.visitor ? "visiting " : "") + what + ")";
    }

    private static DefaultActionGroup build(PetOverlay overlay, Pet p) {
        World world = overlay.world();
        DefaultActionGroup g = new DefaultActionGroup();
        g.add(item(overlay, p, "Pet", "Give it a pat", e -> world.pet(p)));
        if (p.leaving) {
            // already on its way out: a visitor can still be talked into staying for good
            if (p.visitor) g.add(item(overlay, p, "Adopt", "Keep this visitor as your own pet", e -> adopt(overlay, p, e.getProject())));
            return g;
        }
        g.add(item(overlay, p, "Play Ball", "Toss a ball just for this pet", e -> world.throwBallFor(p)));
        if (p.isFollowing()) {
            g.add(item(overlay, p, "Stop Following", "Let it roam on its own again", e -> world.free(p)));
        } else {
            g.add(item(overlay, p, "Follow Me", "It runs after the mouse pointer for a while",
                    e -> world.follow(p, FOLLOW_SECONDS)));
        }
        if (p.behavior == Pet.Behavior.SLEEP) {
            g.add(item(overlay, p, "Wake Up", "End the nap", e -> world.wake(p)));
        } else if (p.surface != null) {
            g.add(item(overlay, p, "Take a Nap", "Lie down and sleep for a bit", e -> world.nap(p)));
        }
        g.addSeparator();
        if (p.staying) {
            g.add(item(overlay, p, "Free to Roam", "Let it walk, jump and climb again", e -> world.free(p)));
        } else {
            g.add(item(overlay, p, "Stay", "Sit and stay put until you set it free", e -> world.stay(p)));
        }
        g.addSeparator();
        if (!p.visitor) {
            g.add(item(overlay, p, "Rename...", "Give it a new name", e -> rename(p, e.getProject())));
        }
        if (p.kind.palettes().size() > 1) g.add(colors(overlay, p));
        if (p.visitor) {
            g.add(item(overlay, p, "Adopt", "Keep this visitor as your own pet", e -> adopt(overlay, p, e.getProject())));
        }
        g.addSeparator();
        if (p.visitor) {
            g.add(item(overlay, p, "Leave", "Say goodbye: it walks off and goes away", e -> world.sendAway(p)));
        } else {
            g.add(item(overlay, p, "Leave for Now",
                    "It walks off and comes back at the next IDE start or Roll Call", e -> overlay.owner().sendHome(p.id)));
        }
        return g;
    }

    private static DefaultActionGroup colors(PetOverlay overlay, Pet p) {
        DefaultActionGroup colors = new DefaultActionGroup("Color", true);
        for (PetKind.Palette pal : p.kind.palettes()) {
            colors.add(new DumbAwareToggleAction(pal.name()) {
                @Override
                public boolean isSelected(@NotNull AnActionEvent e) {
                    return p.palette.equals(pal);
                }

                @Override
                public void setSelected(@NotNull AnActionEvent e, boolean state) {
                    if (state && overlay.world().pets().contains(p)) setColor(p, pal);
                }

                @Override
                public @NotNull ActionUpdateThread getActionUpdateThread() {
                    return ActionUpdateThread.EDT;
                }
            });
        }
        return colors;
    }

    /** A menu entry that does nothing when the pet has meanwhile left the window. */
    private static AnAction item(PetOverlay overlay, Pet p, String text, String description, Consumer<AnActionEvent> run) {
        return new DumbAwareAction(text, description, null) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                if (overlay.world().pets().contains(p)) run.accept(e);
            }

            @Override
            public @NotNull ActionUpdateThread getActionUpdateThread() {
                return ActionUpdateThread.EDT;
            }
        };
    }

    private static PetsSettings.PetEntry entry(String id) {
        for (PetsSettings.PetEntry e : PetsSettings.getInstance().getState().pets) {
            if (e.id.equals(id)) return e;
        }
        return null;
    }

    private static void rename(Pet p, Project project) {
        PetsSettings.PetEntry entry = entry(p.id);
        if (entry == null) return;
        String name = Messages.showInputDialog(project, "New name for " + entry.name + ":", "Rename Pet", null, entry.name,
                new InputValidator() {
                    @Override
                    public boolean checkInput(String s) {
                        return s != null && !s.isBlank() && s.trim().length() <= MAX_NAME_LENGTH;
                    }

                    @Override
                    public boolean canClose(String s) {
                        return checkInput(s);
                    }
                });
        if (name == null || name.trim().equals(entry.name)) return;
        entry.name = name.trim();
        PetsSettings.getInstance().fireChanged(); // every window updates the pet in place
    }

    private static void setColor(Pet p, PetKind.Palette pal) {
        PetsSettings.PetEntry entry = p.visitor ? null : entry(p.id);
        if (entry == null) {
            p.palette = pal; // a visitor: just for this visit
            return;
        }
        entry.color = pal.name();
        PetsSettings.getInstance().fireChanged();
    }

    /** The visitor stays in this window as an adopted pet (same id) and also appears in the other windows. */
    private static void adopt(PetOverlay overlay, Pet p, Project project) {
        overlay.world().adopt(p);
        PetsSettings.PetEntry entry = new PetsSettings.PetEntry(p.kind, p.palette.name(), p.name);
        entry.id = p.id;
        PetsSettings.getInstance().addPet(entry);
        NotificationGroupManager.getInstance().getNotificationGroup("Code Gschpänli")
                .createNotification("<b>" + StringUtil.escapeXmlEntities(p.name) + "</b> is staying with you now."
                        + " Right-click it to rename it.", NotificationType.INFORMATION)
                .notify(project);
    }
}
