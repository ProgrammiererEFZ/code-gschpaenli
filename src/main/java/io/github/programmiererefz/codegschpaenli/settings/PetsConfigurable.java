// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import io.github.programmiererefz.codegschpaenli.ui.AdoptPetDialog;
import io.github.programmiererefz.codegschpaenli.ui.PetPreview;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

/** Settings | Tools | Code Gschpänli. */
public final class PetsConfigurable implements Configurable {

    private JBCheckBox enabled;
    private ComboBox<PetsSettings.Size> size;
    private JSlider speed;
    private JSlider activity;
    private JBCheckBox showNames;
    private JBCheckBox pauseWhenInactive;
    private JBCheckBox onStatusBar;
    private JBCheckBox onToolWindows;
    private JBCheckBox onEditorTabs;
    private JBCheckBox onToolbars;
    private JBCheckBox onPanels;
    private JBCheckBox visitorsEnabled;
    private JSpinner visitorsMin;
    private JSpinner visitorsMax;
    private JBCheckBox visitorsCartoon;
    private JBCheckBox visitorsPixel;
    private Runnable syncVisitorUi = () -> {
    };
    private CollectionListModel<PetsSettings.PetEntry> petsModel;
    private PetPreview preview;

    @Override
    public @Nls String getDisplayName() {
        return "Code Gschpänli";
    }

    @Override
    public @Nullable JComponent createComponent() {
        enabled = new JBCheckBox("Show pets");
        size = new ComboBox<>(PetsSettings.Size.values());
        speed = slider(25, 250, 100, 25, "Slow", "Fast");
        activity = slider(0, 100, 50, 10, "Lazy", "Hyper");
        showNames = new JBCheckBox("Show name tags above pets");
        pauseWhenInactive = new JBCheckBox("Pause animation while the IDE window is not focused");
        onStatusBar = new JBCheckBox("Status bar");
        onToolWindows = new JBCheckBox("Tool window headers and stripes");
        onEditorTabs = new JBCheckBox("Editor tab bars");
        onToolbars = new JBCheckBox("Toolbars and navigation bar");
        onPanels = new JBCheckBox("Other panel edges (splitters, consoles, editors)");

        visitorsEnabled = new JBCheckBox("Let random visitors drop by, stay a while and leave again");
        visitorsMin = new JSpinner(new SpinnerNumberModel(1, 0, 20, 1));
        visitorsMax = new JSpinner(new SpinnerNumberModel(3, 0, 20, 1));
        // keep min <= max while the user edits either spinner
        visitorsMin.addChangeListener(e -> {
            if ((int) visitorsMin.getValue() > (int) visitorsMax.getValue()) visitorsMax.setValue(visitorsMin.getValue());
        });
        visitorsMax.addChangeListener(e -> {
            if ((int) visitorsMax.getValue() < (int) visitorsMin.getValue()) visitorsMin.setValue(visitorsMax.getValue());
        });
        visitorsCartoon = new JBCheckBox("Cartoon pets");
        visitorsPixel = new JBCheckBox("Pixel-art pets");
        JPanel visitorRange = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, JBUI.scale(4), 0));
        visitorRange.add(new JBLabel("Between"));
        visitorRange.add(visitorsMin);
        visitorRange.add(new JBLabel("and"));
        visitorRange.add(visitorsMax);
        visitorRange.add(new JBLabel("visitors at a time (use the same number for a fixed count)"));
        JPanel visitorKinds = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, JBUI.scale(4), 0));
        visitorKinds.add(new JBLabel("Visitors can be:"));
        visitorKinds.add(visitorsCartoon);
        visitorKinds.add(visitorsPixel);
        syncVisitorUi = () -> {
            boolean on = visitorsEnabled.isSelected();
            for (java.awt.Component c : visitorRange.getComponents()) c.setEnabled(on);
            for (java.awt.Component c : visitorKinds.getComponents()) c.setEnabled(on);
        };
        visitorsEnabled.addActionListener(e -> syncVisitorUi.run());

        petsModel = new CollectionListModel<>();
        JBList<PetsSettings.PetEntry> list = new JBList<>(petsModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ColoredListCellRenderer<PetsSettings.PetEntry>() {
            @Override
            protected void customizeCellRenderer(@org.jetbrains.annotations.NotNull javax.swing.JList<? extends PetsSettings.PetEntry> l,
                                                 PetsSettings.PetEntry p, int index, boolean selected, boolean focus) {
                append(p.name + "  (" + p.palette().name() + " " + p.petKind().displayName()
                        + (p.petKind().isPixel() ? ", pixel art" : "") + ")");
            }
        });
        preview = new PetPreview();
        list.addListSelectionListener(e -> {
            PetsSettings.PetEntry sel = list.getSelectedValue();
            if (sel != null) preview.setPet(sel.petKind(), sel.palette());
        });
        JPanel listPanel = ToolbarDecorator.createDecorator(list)
                .setAddAction(b -> {
                    AdoptPetDialog d = new AdoptPetDialog(null);
                    if (d.showAndGet()) {
                        petsModel.add(d.toEntry());
                        list.setSelectedIndex(petsModel.getSize() - 1);
                    }
                })
                .setRemoveAction(b -> {
                    int i = list.getSelectedIndex();
                    if (i >= 0) petsModel.remove(i);
                })
                .disableUpDownActions()
                .setPreferredSize(JBUI.size(320, 140))
                .createPanel();
        JPanel petsRow = new JPanel(new BorderLayout(JBUI.scale(10), 0));
        petsRow.add(listPanel, BorderLayout.CENTER);
        petsRow.add(preview, BorderLayout.EAST);

        JPanel surfaces = new JPanel(new java.awt.GridLayout(0, 2, JBUI.scale(8), 0));
        surfaces.add(onStatusBar);
        surfaces.add(onToolWindows);
        surfaces.add(onEditorTabs);
        surfaces.add(onToolbars);
        surfaces.add(onPanels);

        JBLabel hint = new JBLabel("Click a pet to pet it, drag it to pick it up and throw it.");
        hint.setForeground(UIUtil.getContextHelpForeground());

        JPanel panel = FormBuilder.createFormBuilder()
                .addComponent(enabled)
                .addLabeledComponent("Size:", size)
                .addLabeledComponent("Speed:", speed)
                .addLabeledComponent("Activity:", activity)
                .addComponent(showNames)
                .addComponent(pauseWhenInactive)
                .addSeparator()
                .addLabeledComponent("Pets walk on:", surfaces, true)
                .addSeparator()
                .addComponent(visitorsEnabled)
                .addComponent(visitorRange)
                .addComponent(visitorKinds)
                .addSeparator()
                .addLabeledComponent("Your pets:", petsRow, true)
                .addComponent(hint)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
        reset();
        return panel;
    }

    private static JSlider slider(int min, int max, int value, int ticks, String lo, String hi) {
        JSlider s = new JSlider(min, max, value);
        s.setMajorTickSpacing(ticks);
        s.setPaintTicks(true);
        Hashtable<Integer, JComponent> labels = new Hashtable<>();
        labels.put(min, new JBLabel(lo));
        labels.put(max, new JBLabel(hi));
        s.setLabelTable(labels);
        s.setPaintLabels(true);
        return s;
    }

    private PetsSettings.StateBean fromUi() {
        PetsSettings.StateBean s = PetsSettings.getInstance().copyState();
        s.enabled = enabled.isSelected();
        PetsSettings.Size sz = (PetsSettings.Size) size.getSelectedItem();
        s.size = (sz == null ? PetsSettings.Size.SMALL : sz).name();
        s.speed = speed.getValue();
        s.activity = activity.getValue();
        s.showNames = showNames.isSelected();
        s.pauseWhenInactive = pauseWhenInactive.isSelected();
        s.walkOnStatusBar = onStatusBar.isSelected();
        s.walkOnToolWindows = onToolWindows.isSelected();
        s.walkOnEditorTabs = onEditorTabs.isSelected();
        s.walkOnToolbars = onToolbars.isSelected();
        s.walkOnPanels = onPanels.isSelected();
        s.visitorsEnabled = visitorsEnabled.isSelected();
        s.visitorsMin = (int) visitorsMin.getValue();
        s.visitorsMax = (int) visitorsMax.getValue();
        s.visitorsCartoon = visitorsCartoon.isSelected();
        s.visitorsPixel = visitorsPixel.isSelected();
        List<PetsSettings.PetEntry> pets = new ArrayList<>();
        for (PetsSettings.PetEntry p : petsModel.getItems()) pets.add(p.copy());
        s.pets = pets;
        return s;
    }

    @Override
    public boolean isModified() {
        PetsSettings.StateBean cur = PetsSettings.getInstance().getState();
        PetsSettings.StateBean ui = fromUi();
        return cur.enabled != ui.enabled
                || !Objects.equals(cur.size, ui.size)
                || cur.speed != ui.speed
                || cur.activity != ui.activity
                || cur.showNames != ui.showNames
                || cur.pauseWhenInactive != ui.pauseWhenInactive
                || cur.walkOnStatusBar != ui.walkOnStatusBar
                || cur.walkOnToolWindows != ui.walkOnToolWindows
                || cur.walkOnEditorTabs != ui.walkOnEditorTabs
                || cur.walkOnToolbars != ui.walkOnToolbars
                || cur.walkOnPanels != ui.walkOnPanels
                || cur.visitorsEnabled != ui.visitorsEnabled
                || cur.visitorsMin != ui.visitorsMin
                || cur.visitorsMax != ui.visitorsMax
                || cur.visitorsCartoon != ui.visitorsCartoon
                || cur.visitorsPixel != ui.visitorsPixel
                || !ids(cur.pets).equals(ids(ui.pets));
    }

    private static List<String> ids(List<PetsSettings.PetEntry> pets) {
        List<String> ids = new ArrayList<>();
        for (PetsSettings.PetEntry p : pets) ids.add(p.id + "|" + p.kind + "|" + p.color + "|" + p.name);
        return ids;
    }

    @Override
    public void apply() {
        PetsSettings.getInstance().update(fromUi());
    }

    @Override
    public void reset() {
        PetsSettings settings = PetsSettings.getInstance();
        PetsSettings.StateBean s = settings.getState();
        enabled.setSelected(s.enabled);
        size.setSelectedItem(settings.size());
        speed.setValue(s.speed);
        activity.setValue(s.activity);
        showNames.setSelected(s.showNames);
        pauseWhenInactive.setSelected(s.pauseWhenInactive);
        onStatusBar.setSelected(s.walkOnStatusBar);
        onToolWindows.setSelected(s.walkOnToolWindows);
        onEditorTabs.setSelected(s.walkOnEditorTabs);
        onToolbars.setSelected(s.walkOnToolbars);
        onPanels.setSelected(s.walkOnPanels);
        visitorsEnabled.setSelected(s.visitorsEnabled);
        visitorsMin.setValue(Math.max(0, Math.min(20, s.visitorsMin)));
        visitorsMax.setValue(Math.max(0, Math.min(20, Math.max(s.visitorsMin, s.visitorsMax))));
        visitorsCartoon.setSelected(s.visitorsCartoon);
        visitorsPixel.setSelected(s.visitorsPixel);
        syncVisitorUi.run();
        List<PetsSettings.PetEntry> pets = new ArrayList<>();
        for (PetsSettings.PetEntry p : s.pets) pets.add(p.copy());
        petsModel.replaceAll(pets);
        if (!pets.isEmpty()) preview.setPet(pets.get(0).petKind(), pets.get(0).palette());
    }

    @Override
    public void disposeUIResources() {
        petsModel = null;
        preview = null;
    }
}
