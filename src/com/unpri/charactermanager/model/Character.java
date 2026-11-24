package com.unpri.charactermanager.model;

public class Character {

    private String name;
    private CharacterType type;
    private int level;
    private int hitPoints;
    private int attackPower;

    public Character(String name, CharacterType type, int level) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }

        if (type == null) {
            throw new IllegalArgumentException("Role wajib dipilih");
        }

        if (level < 1 || level > 99) {
            throw new IllegalArgumentException("Level harus 1-99");
        }

        // Mini-Challenge: aturan khusus MAGE di Model
        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException(
                "MAGE maksimal level 60"
            );
        }

        this.name = name;
        this.type = type;
        this.level = level;

        calculateStats();
    }

    private void calculateStats() {

        switch (type) {

            case WARRIOR:
                hitPoints = 100 + (level * 20);
                attackPower = 15 + (level * 5);
                break;

            case MAGE:
                hitPoints = 70 + (level * 10);
                attackPower = 25 + (level * 8);
                break;

            case ARCHER:
                hitPoints = 80 + (level * 12);
                attackPower = 20 + (level * 6);
                break;

            case HEALER:
                hitPoints = 90 + (level * 15);
                attackPower = 10 + (level * 3);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttackPower() {
        return attackPower;
    }
}