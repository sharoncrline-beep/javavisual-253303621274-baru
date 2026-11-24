
package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

public class MainWindow extends JFrame {

    private final CharacterManager manager;

    private JPanel cards;
    private JPanel statistics;

    private JLabel countLabel;
    private JLabel statusLabel;

    public MainWindow(CharacterManager manager) {
        super("Character Manager v0.6");

        this.manager = manager;

        initComponents();

        pack();
        setMinimumSize(new Dimension(850, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setBackground(AppTheme.BG_MAIN);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(AppTheme.BG_MAIN);

        northPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        statistics = createStatisticsPanel();
        northPanel.add(statistics, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        add(new CharacterFormPanel(manager, this::refreshList),
                BorderLayout.WEST);

        add(createListArea(), BorderLayout.CENTER);
        add(createLegendPanel(), BorderLayout.EAST);
        add(createStatusPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        panel.setBackground(AppTheme.BG_MAIN);

        JLabel title = new JLabel("Character Manager");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        countLabel = new JLabel();
        countLabel.setFont(AppTheme.FONT_BODY);
        countLabel.setForeground(AppTheme.TEXT_MUTED);

        panel.add(title);
        panel.add(countLabel);

        updateCount();

        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.setBackground(AppTheme.BG_MAIN);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));

        refreshStatistics(panel);

        return panel;
    }

    private void refreshStatistics(JPanel panel) {
        panel.removeAll();

        panel.add(createStatCard(
                "Total Character",
                String.valueOf(manager.getTotalCharacters())));

        panel.add(createStatCard(
                "Warrior",
                String.valueOf(countByRole(CharacterType.WARRIOR))));

        panel.add(createStatCard(
                "Mage",
                String.valueOf(countByRole(CharacterType.MAGE))));

        panel.add(createStatCard(
                "Average Level",
                String.format("%.1f", manager.getAverageLevel())));

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout(4, 4));
        card.setBackground(AppTheme.BG_STATUS);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.TEXT_MUTED),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.FONT_BODY);
        titleLabel.setForeground(AppTheme.TEXT_MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(AppTheme.FONT_TITLE);
        valueLabel.setForeground(AppTheme.GOLD);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private int countByRole(CharacterType type) {
        int count = 0;

        for (Character c : manager.getAllCharacters()) {
            if (c.getType() == type) {
                count++;
            }
        }

        return count;
    }

    private JScrollPane createListArea() {
        cards = new JPanel(new GridLayout(0, 1, 0, 8));
        cards.setBackground(AppTheme.BG_MAIN);
        cards.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        refreshCards();

        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(AppTheme.BG_MAIN);
        holder.add(cards, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(holder);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.BG_MAIN);
        scrollPane.setPreferredSize(new Dimension(460, 280));

        return scrollPane;
    }

    private void refreshCards() {
        cards.removeAll();

        for (Character c : manager.getAllCharacters()) {
            cards.add(createCharacterLabel(c));
        }

        cards.revalidate();
        cards.repaint();
    }

    private JLabel createCharacterLabel(Character c) {
        JLabel label = new JLabel(
                c.getName()
                + "  |  " + c.getType()
                + "  |  Lv. " + c.getLevel()
                + "  |  HP: " + c.getHitPoints()
                + "  |  ATK: " + c.getAttackPower());

        label.setFont(AppTheme.FONT_BODY);
        label.setForeground(AppTheme.TEXT_MAIN);
        label.setOpaque(true);
        label.setBackground(AppTheme.BG_STATUS);

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.TEXT_MUTED),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return label;
    }

    private JPanel createLegendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppTheme.BG_MAIN);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 16));

        JLabel title = new JLabel("Role Legend");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));

        for (CharacterType type : CharacterType.values()) {
            JLabel label = new JLabel(type.name());
            label.setFont(AppTheme.FONT_BODY);
            label.setForeground(AppTheme.colorFor(type));

            JLabel description = new JLabel(AppTheme.describe(type));
            description.setFont(AppTheme.FONT_BODY);
            description.setForeground(AppTheme.TEXT_MUTED);

            panel.add(label);
            panel.add(description);
            panel.add(Box.createVerticalStrut(8));
        }

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.BG_STATUS);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        statusLabel = new JLabel();
        statusLabel.setFont(AppTheme.FONT_MONO);
        statusLabel.setForeground(AppTheme.TEXT_MUTED);

        JLabel version = new JLabel("v0.6");
        version.setFont(AppTheme.FONT_MONO);
        version.setForeground(AppTheme.TEXT_MUTED);

        panel.add(statusLabel, BorderLayout.WEST);
        panel.add(version, BorderLayout.EAST);

        updateStatus();

        return panel;
    }

    private void refreshList() {
        refreshCards();
        updateCount();
        updateStatus();
        refreshStatistics(statistics);
    }

    private void updateCount() {
        if (countLabel != null) {
            countLabel.setText(
                    "(" + manager.getTotalCharacters() + " karakter)");
        }
    }

    private void updateStatus() {
        if (statusLabel != null) {
            statusLabel.setText(
                    "Total: " + manager.getTotalCharacters()
                    + " karakter  |  Siap");
        }
    }
}
