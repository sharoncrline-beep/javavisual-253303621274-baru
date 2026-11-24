package com.unpri.charactermanager.model;

import java.util.ArrayList;

public class CharacterManager {

    private ArrayList<Character> characterList;

    // Aturan tambahan 1: jumlah karakter maksimal
    private static final int MAX_CHARACTERS = 20;

    // Aturan tambahan 2: jumlah maksimal karakter setiap role
    private static final int MAX_PER_ROLE = 5;

    public CharacterManager() {
        this.characterList = new ArrayList<>();
    }

    public void addCharacter(Character character) {

        if (character == null) {
            throw new IllegalArgumentException(
                "Character tidak boleh null"
            );
        }

        // Validasi maksimal karakter dalam daftar
        if (characterList.size() >= MAX_CHARACTERS) {
            throw new IllegalArgumentException(
                "Daftar karakter sudah penuh (maksimal 20)"
            );
        }

        // Validasi maksimal karakter pada setiap role
        if (countByRole(character.getType()) >= MAX_PER_ROLE) {
            throw new IllegalArgumentException(
                "Role " + character.getType()
                + " sudah mencapai batas maksimal 5 karakter"
            );
        }

        characterList.add(character);
    }

    public ArrayList<Character> getAllCharacters() {
        return characterList;
    }

    public boolean isNameTaken(String name) {
        for (Character c : getAllCharacters()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeCharacter(Character character) {
        characterList.remove(character);
    }

    public double getAverageLevel() {
        if (characterList.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (Character c : characterList) {
            total += c.getLevel();
        }

        return (double) total / characterList.size();
    }

    public int getTotalCharacters() {
        return characterList.size();
    }

    public int countByRole(CharacterType type) {
        int count = 0;

        for (Character c : characterList) {
            if (c.getType() == type) {
                count++;
            }
        }

        return count;
    }
}