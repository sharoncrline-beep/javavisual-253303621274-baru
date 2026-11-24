
package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;

public class MainWindow extends JFrame {

    private ArrayList<Character> characterList;

    public MainWindow(ArrayList<Character> characterList) {
        super("Character Manager v0.5");
        this.characterList = characterList;

        initComponents();

        pack();
        setMinimumSize(new Dimension(560, 380));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setBackground(AppTheme.BG_MAIN);
        setLayout(new BorderLayout());

        // NORTH: Header + Statistics Panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(AppTheme.BG_MAIN);

        northPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        northPanel.add(createStatisticsPanel(), BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // CENTER: Scrollable Character List
        add(createListArea(), BorderLayout.CENTER);

        // EAST: Role Legend
        add(createLegendPanel(), BorderLayout.EAST);

        // SOUTH: Status Bar
        add(createStatusPanel(), BorderLayout.SOUTH);
    }

    // Header
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_MAIN);
        header.setBorder(
                BorderFactory.createEmptyBorder(16, 16, 8, 16));

        JLabel title = new JLabel("Character Manager");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        JLabel count = new JLabel(characterList.size() + " karakter");
        count.setFont(AppTheme.FONT_BODY);
        count.setForeground(AppTheme.GOLD);

        header.add(title, BorderLayout.WEST);
        header.add(count, BorderLayout.EAST);

        return header;
    }

    // NORTH: Statistics Panel (GridLayout 2x2)
    private JPanel createStatisticsPanel() {
        JPanel statistics = new JPanel(new GridLayout(2, 2, 8, 6));
        statistics.setBackground(AppTheme.BG_MAIN);
        statistics.setBorder(
                BorderFactory.createEmptyBorder(4, 16, 12, 16));

        int warriorCount = countByRole(CharacterType.WARRIOR);
        int mageCount = countByRole(CharacterType.MAGE);
        int totalLevel = 0;

        for (Character c : characterList) {
            totalLevel += c.getLevel();
        }

        double averageLevel = characterList.isEmpty()
                ? 0.0
                : (double) totalLevel / characterList.size();

        statistics.add(createStatCard(
                "TOTAL CHARACTER", String.valueOf(characterList.size())));

        statistics.add(createStatCard(
                "WARRIOR", String.valueOf(warriorCount)));

        statistics.add(createStatCard(
                "MAGE", String.valueOf(mageCount)));

        statistics.add(createStatCard(
                "AVERAGE LEVEL", String.format("%.1f", averageLevel)));

        return statistics;
    }

    // Statistics Card
    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout(4, 2));
        card.setBackground(AppTheme.BG_STATUS);
        card.setBorder(
                BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel title = new JLabel(label);
        title.setFont(AppTheme.FONT_MONO);
        title.setForeground(AppTheme.TEXT_MUTED);

        JLabel number = new JLabel(value);
        number.setFont(AppTheme.FONT_TITLE);
        number.setForeground(AppTheme.TEXT_MAIN);

        card.add(title, BorderLayout.NORTH);
        card.add(number, BorderLayout.CENTER);

        return card;
    }

    // Count character by role
    private int countByRole(CharacterType type) {
        int count = 0;

        for (Character c : characterList) {
            if (c.getType() == type) {
                count++;
            }
        }

        return count;
    }

    // CENTER: Scrollable Character List
    private JScrollPane createListArea() {
        JPanel cards = new JPanel(new GridLayout(0, 1, 0, 8));
        cards.setBackground(AppTheme.BG_MAIN);

        for (Character c : characterList) {
            cards.add(createCharacterLabel(c));
        }

        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(AppTheme.BG_MAIN);
        holder.setBorder(
                BorderFactory.createEmptyBorder(8, 16, 8, 8));

        holder.add(cards, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(holder);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.BG_MAIN);
        scroll.setPreferredSize(new Dimension(460, 280));

        return scroll;
    }

    // Character Card
    private JLabel createCharacterLabel(Character c) {
        String info = c.getName() + " | " + c.getType()
                + " | Lv." + c.getLevel()
                + " | HP:" + c.getHitPoints()
                + " | ATK:" + c.getAttackPower();

        JLabel lbl = new JLabel(info);
        lbl.setFont(AppTheme.FONT_BODY);
        lbl.setForeground(AppTheme.TEXT_LIGHT);
        lbl.setBackground(AppTheme.colorFor(c.getType()));
        lbl.setOpaque(true);
        lbl.setBorder(AppTheme.CARD_BORDER);
        lbl.setToolTipText(AppTheme.describe(c.getType()));
        lbl.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return lbl;
    }

    // EAST: Role Legend
    private JPanel createLegendPanel() {
        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBackground(AppTheme.BG_MAIN);
        legend.setBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 16));

        JLabel head = new JLabel("Legenda Role");
        head.setFont(AppTheme.FONT_BODY.deriveFont(java.awt.Font.BOLD));
        head.setForeground(AppTheme.TEXT_MAIN);
        head.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        legend.add(head);
        legend.add(Box.createVerticalStrut(8));

        for (CharacterType type : CharacterType.values()) {
            JLabel chip = new JLabel(type.name());
            chip.setOpaque(true);
            chip.setBackground(AppTheme.colorFor(type));
            chip.setForeground(AppTheme.TEXT_LIGHT);
            chip.setFont(AppTheme.FONT_MONO);
            chip.setBorder(AppTheme.CARD_BORDER);
            chip.setToolTipText(AppTheme.describe(type));
            chip.setAlignmentX(JLabel.LEFT_ALIGNMENT);

            legend.add(chip);
            legend.add(Box.createVerticalStrut(6));
        }

        legend.add(Box.createVerticalGlue());

        return legend;
    }

    // SOUTH: Status Bar + Version
    private JPanel createStatusPanel() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(AppTheme.BG_STATUS);
        status.setBorder(
                BorderFactory.createEmptyBorder(6, 16, 6, 16));

        // Left side
        JPanel leftPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 16, 0));
        leftPanel.setBackground(AppTheme.BG_STATUS);

        JLabel lbl = new JLabel(
                "Total: " + characterList.size()
                + " karakter  |  Siap");

        lbl.setFont(AppTheme.FONT_MONO);
        lbl.setForeground(AppTheme.TEXT_MUTED);

        leftPanel.add(lbl);

        // Right side
        JLabel version = new JLabel("v0.5");
        version.setFont(AppTheme.FONT_MONO);
        version.setForeground(AppTheme.TEXT_MUTED);

        status.add(leftPanel, BorderLayout.WEST);
        status.add(version, BorderLayout.EAST);

        return status;
    }
}
