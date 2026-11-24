import javax.swing.SwingUtilities;
import com.unpri.charactermanager.view.MainWindow;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.model.CharacterManager;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Character Manager v0.2 — CRUD Test ===\n");

        CharacterManager manager = new CharacterManager();

        // 1. Tambah beberapa karakter
        manager.addCharacter(new Character("Arjuna", CharacterType.WARRIOR, 5));
        manager.addCharacter(new Character("Merlin", CharacterType.MAGE, 3));
        manager.addCharacter(new Character("Athena", CharacterType.ARCHER, 4));
        manager.addCharacter(new Character("Loki", CharacterType.MAGE, 7));
        manager.addCharacter(new Character("Thor", CharacterType.WARRIOR, 8));

        // 2. Coba tambah DUPLIKAT nama — seharusnya DITOLAK
        System.out.println();
        manager.addCharacter(new Character("Arjuna", CharacterType.HEALER, 1));

        // 3. Tampilkan total karakter
        System.out.println("\nTotal karakter saat ini: " + manager.getTotalCharacters());

        // 4. Cari karakter
        System.out.println("\n--- Pencarian ---");
        Character hasil = manager.findByName("Merlin");
        System.out.println(hasil != null ? "Ditemukan: " + hasil : "Tidak ditemukan");
        
        Character tidakAda = manager.findByName("Zeus");
        System.out.println(tidakAda != null ? "Ditemukan: " + tidakAda : "Zeus tidak ditemukan (sesuai harapan)");

        // 5. Hapus karakter
        System.out.println("\n--- Penghapusan ---");
        manager.removeCharacter("Athena");
        System.out.println("Total setelah hapus: " + manager.getTotalCharacters());

        // 6. Tampilkan semua karakter yang tersisa
        System.out.println("\n--- Daftar Karakter Tersisa ---");
        for (Character c : manager.getAllCharacters()) {
            System.out.println(c);
        }
        // 7. Statistik rata-rata level
        System.out.println("\n--- Rata-rata Level ---");
        System.out.println("Average Level: " + manager.getAverageLevel());

        // 8. Cari karakter berdasarkan tipe
        System.out.println("\n--- Cari Berdasarkan Tipe: WARRIOR ---");
        java.util.ArrayList<Character> warriors =
        manager.findByType(CharacterType.WARRIOR);

        for (Character c : warriors) {
        System.out.println(c);
        }
        // 9. Uji rata-rata pada list kosong
        System.out.println("\n--- Uji List Kosong ---");
        CharacterManager managerKosong = new CharacterManager();
        System.out.println("Average Level (kosong): " + managerKosong.getAverageLevel());

        System.out.println("Total Warrior: " + warriors.size());
           // ===== EKSPERIMEN: Object Reference =====
        System.out.println("\n--- Eksperimen Reference ---");

        Character asli = manager.findByName("Arjuna");
        Character duplikatReferensi = asli;             // menyalin REFERENSI, bukan membuat object baru

        System.out.println("Level Arjuna sebelum: " + asli.getLevel());
        duplikatReferensi.setLevel(99);                  // mengubah LEWAT variabel lain

        System.out.println("Level Arjuna sesudah (lewat 'asli'): " + asli.getLevel());
        System.out.println("asli == duplikatReferensi? " + (asli == duplikatReferensi));

        // Bandingkan dengan getAllCharacters() yang mengembalikan SALINAN LIST:
        java.util.ArrayList<Character> salinanList = manager.getAllCharacters();
        salinanList.add(new Character("Hantu", CharacterType.MAGE, 1));   // tambah ke SALINAN list

        System.out.println("Total di manager asli: " + manager.getTotalCharacters());
        System.out.println("Total di salinan list : " + salinanList.size());
        System.out.println("\n--- Uji Rename Character ---");

        // Rename berhasil
        manager.renameCharacter("Merlin", "Merlin Supreme");

        // Cek nama lama
        System.out.println("Cari Merlin: " + manager.findByName("Merlin"));

        // Cek nama baru
        System.out.println("Cari Merlin Supreme: " + manager.findByName("Merlin Supreme"));

        // Rename dengan nama yang sudah dipakai
        manager.renameCharacter("Arjuna", "Merlin Supreme");

        // Rename karakter yang tidak ditemukan
        manager.renameCharacter("Zeus", "Poseidon");
        // ===== AP12: Menampilkan GUI v0.4 =====
        CharacterManager guiManager = new CharacterManager();

        guiManager.addCharacter(new Character("Arjuna", CharacterType.WARRIOR, 5));
        guiManager.addCharacter(new Character("Merlin", CharacterType.MAGE, 3));
        guiManager.addCharacter(new Character("Athena", CharacterType.ARCHER, 4));
        guiManager.addCharacter(new Character("Gatotkaca", CharacterType.WARRIOR, 7));
        guiManager.addCharacter(new Character("Morgana", CharacterType.MAGE, 1));
        guiManager.addCharacter(new Character("Robin Hood", CharacterType.ARCHER, 6));
        guiManager.addCharacter(new Character("Bima", CharacterType.WARRIOR, 8));
        guiManager.addCharacter(new Character("Srikandi", CharacterType.ARCHER, 2));

        SwingUtilities.invokeLater(() -> {
        MainWindow window = new MainWindow(
            guiManager.getAllCharacters()
    );
     window.setVisible(true);
    });
}
}