// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.messages.Topic;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Tag;
import com.intellij.util.xmlb.annotations.XCollection;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Application-wide pet settings and the list of adopted pets. Persisted in {@code code-gschpaenli.xml}. */
@Service(Service.Level.APP)
@State(name = "CodeGschpaenliSettings", storages = @Storage("code-gschpaenli.xml"))
public final class PetsSettings implements PersistentStateComponent<PetsSettings.StateBean> {

    /** Fired on the application message bus whenever settings or the pet list change. */
    public static final Topic<Runnable> CHANGED = new Topic<>("Code Gschpänli settings changed", Runnable.class);

    public enum Size {
        TINY("Tiny", 28), SMALL("Small", 36), MEDIUM("Medium", 46), LARGE("Large", 60);

        public final String label;
        public final int px;

        Size(String label, int px) {
            this.label = label;
            this.px = px;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    @Tag("pet")
    public static final class PetEntry {
        public String id = UUID.randomUUID().toString();
        public String kind = PetKind.CAT.name();
        public String color = "";
        public String name = "";

        public PetEntry() {
        }

        public PetEntry(PetKind kind, String color, String name) {
            this.kind = kind.name();
            this.color = color;
            this.name = name;
        }

        public PetKind petKind() {
            return PetKind.parse(kind);
        }

        public PetKind.Palette palette() {
            return petKind().palette(color);
        }

        public PetEntry copy() {
            PetEntry e = new PetEntry();
            e.id = id;
            e.kind = kind;
            e.color = color;
            e.name = name;
            return e;
        }
    }

    public static final class StateBean {
        public boolean enabled = true;
        public String size = Size.SMALL.name();
        /** Movement speed multiplier in percent. */
        public int speed = 100;
        /** How active pets are, 0..100. */
        public int activity = 50;
        public boolean showNames = false;
        /** Only animate while the IDE window is focused (saves CPU). */
        public boolean pauseWhenInactive = true;
        public boolean walkOnStatusBar = true;
        public boolean walkOnToolWindows = true;
        public boolean walkOnEditorTabs = true;
        public boolean walkOnToolbars = true;
        public boolean walkOnPanels = true;
        public boolean firstRun = true;

        /** Random visitors that come and go on their own, in addition to the adopted pets. On by default, 1 to 3 at a time. */
        public boolean visitorsEnabled = true;
        public int visitorsMin = 1;
        public int visitorsMax = 3;
        public boolean visitorsCartoon = true;
        public boolean visitorsPixel = true;

        @XCollection(propertyElementName = "pets")
        public List<PetEntry> pets = new ArrayList<>();
    }

    private StateBean state = new StateBean();

    public static PetsSettings getInstance() {
        return ApplicationManager.getApplication().getService(PetsSettings.class);
    }

    @Override
    public @NotNull StateBean getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull StateBean loaded) {
        StateBean fresh = new StateBean();
        XmlSerializerUtil.copyBean(loaded, fresh);
        if (fresh.pets == null) fresh.pets = new ArrayList<>();
        state = fresh;
    }

    @Override
    public void noStateLoaded() {
        state = new StateBean();
    }

    /**
     * Gives first-time users a cat so they immediately see what the plugin does.
     */
    public void ensureFirstRunPet() {
        if (state.firstRun) {
            state.firstRun = false;
            if (state.pets.isEmpty()) {
                state.pets.add(new PetEntry(PetKind.CAT, "Ginger", PetNames.random(PetKind.CAT)));
            }
        }
    }

    public Size size() {
        try {
            return Size.valueOf(state.size);
        } catch (IllegalArgumentException e) {
            return Size.SMALL;
        }
    }

    public List<PetEntry> pets() {
        return List.copyOf(state.pets);
    }

    public void addPet(PetEntry entry) {
        state.pets.add(entry);
        fireChanged();
    }

    public void removePet(String id) {
        state.pets.removeIf(p -> p.id.equals(id));
        fireChanged();
    }

    public void removeAllPets() {
        state.pets.clear();
        fireChanged();
    }

    public void setEnabled(boolean enabled) {
        state.enabled = enabled;
        fireChanged();
    }

    public void update(StateBean newState) {
        StateBean copy = new StateBean();
        XmlSerializerUtil.copyBean(newState, copy);
        List<PetEntry> pets = new ArrayList<>();
        for (PetEntry p : newState.pets) pets.add(p.copy());
        copy.pets = pets;
        state = copy;
        fireChanged();
    }

    /** A detached copy of the current state, for editing in the settings UI. */
    public StateBean copyState() {
        StateBean copy = new StateBean();
        XmlSerializerUtil.copyBean(state, copy);
        List<PetEntry> pets = new ArrayList<>();
        for (PetEntry p : state.pets) pets.add(p.copy());
        copy.pets = pets;
        return copy;
    }

    public void fireChanged() {
        ApplicationManager.getApplication().getMessageBus().syncPublisher(CHANGED).run();
    }
}
