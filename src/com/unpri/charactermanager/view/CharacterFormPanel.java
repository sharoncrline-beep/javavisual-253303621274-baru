package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

public class CharacterFormPanel extends JPanel {

    private final CharacterManager manager;
    private final Runnable onAdded;

    private final JTextField nameField = new JTextField(14);

    private final JComboBox<CharacterType> roleBox =
            new JComboBox<>(CharacterType.values());

    private final JSpinner levelSpinner =
            new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));

    private final JLabel messageLabel = new JLabel(" ");

    private final Border nameBorder;
    private final Border levelBorder;

    public CharacterFormPanel(CharacterManager manager, Runnable onAdded) {
        this.manager = manager;
        this.onAdded = onAdded;

        nameBorder = nameField.getBorder();
        levelBorder = levelSpinner.getBorder();

        setLayout(new BorderLayout(0, 12));
        setBackground(AppTheme.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setPreferredSize(new Dimension(250, 0));

        JLabel title = new JLabel("Tambah Karakter");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        JButton saveButton = new JButton("Simpan");
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> onSave());

        nameField.addActionListener(e -> onSave());

        JPanel rows = new JPanel(new GridLayout(0, 1, 0, 4));
        rows.setOpaque(false);

        rows.add(makeLabel("Nama Karakter", nameField));
        rows.add(nameField);

        rows.add(makeLabel("Role", roleBox));
        rows.add(roleBox);

        rows.add(makeLabel("Level (1-99)", levelSpinner));
        rows.add(levelSpinner);

        rows.add(messageLabel);
        rows.add(saveButton);

        add(title, BorderLayout.NORTH);
        add(rows, BorderLayout.CENTER);
    }

    private JLabel makeLabel(String text, java.awt.Component target) {
        JLabel label = new JLabel(text);
        label.setFont(AppTheme.FONT_BODY);
        label.setForeground(AppTheme.TEXT_MUTED);
        label.setLabelFor(target);
        return label;
    }

    private void onSave() {
        clearMessage();

        String name = nameField.getText().trim();
        String problem = validateName(name);

        if (problem != null) {
            showError(problem, nameField);
            nameField.requestFocusInWindow();
            return;
        }

        try {
            levelSpinner.commitEdit();
        } catch (ParseException | IllegalArgumentException ex) {
            showError("Level harus 1-99", levelSpinner);
            levelSpinner.requestFocusInWindow();
            return;
        }

        CharacterType role = (CharacterType) roleBox.getSelectedItem();
        int level = (Integer) levelSpinner.getValue();

        try {
            manager.addCharacter(new Character(name, role, level));
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage(), levelSpinner);
            levelSpinner.requestFocusInWindow();
            return;
        }

        nameField.setText("");
        roleBox.setSelectedIndex(0);
        levelSpinner.setValue(1);

        showSuccess("Karakter berhasil ditambahkan!");
        nameField.requestFocusInWindow();

        onAdded.run();
    }

    private String validateName(String name) {
        if (name.isEmpty()) {
            return "Nama wajib diisi";
        }

        if (name.length() < 3 || name.length() > 20) {
            return "Nama harus 3-20 karakter";
        }

        if (!name.matches("[A-Za-z ]+")) {
            return "Nama hanya huruf dan spasi";
        }

        if (manager.isNameTaken(name)) {
            return "Nama sudah dipakai";
        }

        return null;
    }

    private void showError(String message, javax.swing.JComponent source) {
        messageLabel.setText(message);
        messageLabel.setForeground(AppTheme.ERROR);
        source.setBorder(BorderFactory.createLineBorder(AppTheme.ERROR, 2));
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(AppTheme.SUCCESS);
    }

    private void clearMessage() {
        messageLabel.setText(" ");
        nameField.setBorder(nameBorder);
        levelSpinner.setBorder(levelBorder);
    }
}
