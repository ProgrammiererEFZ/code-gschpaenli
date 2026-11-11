// Copyright (C) 2026 ProgrammiererEFZ
// SPDX-License-Identifier: GPL-3.0-only
// Additional permission under GNU GPL version 3 section 7: see the NOTICE file.

package io.github.programmiererefz.codegschpaenli.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBUI;
import io.github.programmiererefz.codegschpaenli.pet.PetKind;
import io.github.programmiererefz.codegschpaenli.settings.PetNames;
import io.github.programmiererefz.codegschpaenli.settings.PetsSettings;
import org.jetbrains.annotations.Nullable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/** Lets the user pick species, colour and name for a new pet, with a live preview. */
public final class AdoptPetDialog extends DialogWrapper {

    private final ComboBox<PetKind.Group> styleBox = new ComboBox<>(PetKind.Group.values());
    private final ComboBox<PetKind> kindBox = new ComboBox<>();
    private final ComboBox<PetKind.Palette> colorBox = new ComboBox<>();
    private final JBTextField nameField = new JBTextField();
    private final PetPreview preview = new PetPreview();

    public AdoptPetDialog(@Nullable Project project) {
        super(project, true);
        setTitle("Adopt a Pet");
        setOKButtonText("Adopt");
        colorBox.setRenderer(new ColoredListCellRenderer<PetKind.Palette>() {
            @Override
            protected void customizeCellRenderer(@org.jetbrains.annotations.NotNull javax.swing.JList<? extends PetKind.Palette> list,
                                                 PetKind.Palette value, int index, boolean selected, boolean focus) {
                if (value != null) append(value.name());
            }
        });
        styleBox.addActionListener(e -> onStyleChanged());
        kindBox.addActionListener(e -> onKindChanged());
        onStyleChanged();
        colorBox.addActionListener(e -> updatePreview());
        init();
    }

    private void onStyleChanged() {
        PetKind.Group g = (PetKind.Group) styleBox.getSelectedItem();
        kindBox.setModel(new DefaultComboBoxModel<>(PetKind.inGroup(g == null ? PetKind.Group.CARTOON : g).toArray(new PetKind[0])));
        onKindChanged();
    }

    private void onKindChanged() {
        PetKind kind = selectedKind();
        colorBox.setModel(new DefaultComboBoxModel<>(kind.palettes().toArray(new PetKind.Palette[0])));
        nameField.setText(PetNames.random(kind));
        updatePreview();
    }

    private PetKind selectedKind() {
        PetKind k = (PetKind) kindBox.getSelectedItem();
        return k == null ? PetKind.CAT : k;
    }

    private PetKind.Palette selectedPalette() {
        PetKind.Palette p = (PetKind.Palette) colorBox.getSelectedItem();
        return p == null ? selectedKind().palettes().get(0) : p;
    }

    private void updatePreview() {
        preview.setPet(selectedKind(), selectedPalette());
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JButton dice = new JButton("Random Name");
        dice.addActionListener(e -> nameField.setText(PetNames.random(selectedKind())));
        JPanel nameRow = new JPanel(new BorderLayout(JBUI.scale(6), 0));
        nameRow.add(nameField, BorderLayout.CENTER);
        nameRow.add(dice, BorderLayout.EAST);

        JPanel form = FormBuilder.createFormBuilder()
                .addLabeledComponent("Style:", styleBox)
                .addLabeledComponent("Species:", kindBox)
                .addLabeledComponent("Color:", colorBox)
                .addLabeledComponent("Name:", nameRow)
                .getPanel();
        JPanel root = new JPanel(new BorderLayout(JBUI.scale(12), 0));
        root.add(preview, BorderLayout.WEST);
        root.add(form, BorderLayout.CENTER);
        return root;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return nameField;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        if (nameField.getText().trim().length() > 24) {
            return new ValidationInfo("Please use a shorter name (max 24 characters)", nameField);
        }
        return null;
    }

    public PetsSettings.PetEntry toEntry() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) name = PetNames.random(selectedKind());
        return new PetsSettings.PetEntry(selectedKind(), selectedPalette().name(), name);
    }
}
