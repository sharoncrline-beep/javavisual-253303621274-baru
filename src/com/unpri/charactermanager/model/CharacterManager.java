package com.unpri.charactermanager.model;

import java.util.ArrayList;

/**
 * Mengelola koleksi Character: tambah, cari, hapus, dan statistik dasar.
 * Menerapkan Composition: CharacterManager "has-a" ArrayList<Character>.
 */
public class CharacterManager {

    private ArrayList<Character> characterList;

    public CharacterManager() {
        this.characterList = new ArrayList<>();   // list kosong saat manager pertama dibuat
    }

    /**
     * Menambahkan karakter baru ke dalam list.
     * Menolak jika sudah ada karakter dengan nama yang sama (cek via equals()).
     * @return true jika berhasil ditambahkan, false jika ditolak karena duplikat
     */
    public boolean addCharacter(Character character) {
        if (findByName(character.getName()) != null) {   // cek dulu apakah nama sudah ada
            System.out.println("[GAGAL] Karakter dengan nama '" + character.getName() + "' sudah ada!");
            return false;
        }

        characterList.add(character);
        System.out.println("[SUKSES] Karakter '" + character.getName() + "' berhasil ditambahkan.");
        return true;
    }

    /**
     * Mencari karakter berdasarkan nama.
     * @return object Character jika ditemukan, null jika tidak ada
     */
    public Character findByName(String name) {
        for (Character c : characterList) {         // iterasi setiap elemen di list
            if (c.getName().equals(name)) {          // bandingkan ISI nama, bukan ==
                return c;                             // langsung kembalikan referensi yang ditemukan
            }
        }

        return null;                                  // tidak ditemukan setelah seluruh list diperiksa
    }

    /**
     * Menghapus karakter dari list berdasarkan nama.
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    public boolean removeCharacter(String name) {
        Character target = findByName(name);

        if (target == null) {
            System.out.println("[GAGAL] Karakter '" + name + "' tidak ditemukan.");
            return false;
        }

        characterList.remove(target);                 // ArrayList.remove(Object) memakai equals() di balik layar
        System.out.println("[SUKSES] Karakter '" + name + "' berhasil dihapus.");
        return true;
    }

    public int getTotalCharacters() {
        return characterList.size();
    }

    /**
     * Mengembalikan SALINAN list, bukan referensi asli —
     * agar pemanggil tidak bisa mengubah data internal manager secara langsung.
     */
    public ArrayList<Character> getAllCharacters() {
        return new ArrayList<>(characterList);        // defensive copy — lihat Halaman 7 untuk eksperimen
    }

    /**
     * Menghitung rata-rata level seluruh karakter dalam list.
     * Jika list kosong, mengembalikan 0 agar tidak terjadi pembagian dengan nol.
     */
    public double getAverageLevel() {
        if (characterList.isEmpty()) {
            return 0;                                // list kosong, rata-rata = 0
        }

        int totalLevel = 0;

        for (Character c : characterList) {
            totalLevel += c.getLevel();              // menjumlahkan level setiap karakter
        }

        return (double) totalLevel / characterList.size(); // menghitung rata-rata level
    }

    /**
     * Mencari karakter berdasarkan tipe (CharacterType).
     * Mengembalikan list berisi seluruh karakter yang memiliki tipe sesuai.
     * @return ArrayList<Character> berisi karakter dengan tipe yang cocok
     */
    public ArrayList<Character> findByType(CharacterType type) {
        ArrayList<Character> hasil = new ArrayList<>();

        for (Character c : characterList) {         // memeriksa setiap karakter dalam list
            if (c.getType() == type) {              // membandingkan tipe karakter
                hasil.add(c);                        // menambahkan karakter yang cocok
            }
        }

        return hasil;                                 // mengembalikan list hasil pencarian
    }
        /**
     * Mengubah nama karakter dengan validasi agar tidak terjadi nama duplikat.
     * @return true jika nama berhasil diubah, false jika validasi gagal
     */
    public boolean renameCharacter(String oldName, String newName) {
        Character target = findByName(oldName);

        // Validasi: karakter lama harus ditemukan
        if (target == null) {
            System.out.println("[GAGAL] Karakter '" + oldName + "' tidak ditemukan.");
            return false;
        }

        // Validasi: nama baru tidak boleh kosong
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("[GAGAL] Nama baru tidak boleh kosong.");
            return false;
        }

        // Validasi: nama baru tidak boleh dimiliki karakter lain
        Character existing = findByName(newName);

        if (existing != null && existing != target) {
            System.out.println("[GAGAL] Nama '" + newName + "' sudah digunakan.");
            return false;
        }

        target.setName(newName);

        System.out.println("[SUKSES] Nama karakter '" + oldName
                + "' berhasil diubah menjadi '" + newName + "'.");
        return true;
    }

}   // kurung kurawal penutup class CharacterManager