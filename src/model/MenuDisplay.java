package model;

import java.util.List;

/**
 * Bertanggung jawab menampilkan daftar menu ke konsol.
 * Memisahkan presentasi dari logika bisnis.
 */
public class MenuDisplay {

    private static final int    LEBAR       = 54;
    private static final String GARIS       = "=".repeat(LEBAR);
    private static final String GARIS_TIPIS = "-".repeat(LEBAR);

    private final Menu menu;

    public MenuDisplay(Menu menu) {
        this.menu = menu;
    }

    /** Tampilkan seluruh daftar menu */
    public void tampilkanSemua() {
        System.out.println("\n" + GARIS);
        System.out.println(tengah("DAFTAR MENU KAFETARIA IT DEL"));
        System.out.println(GARIS);

        tampilkanKategori("🍽  MAKANAN", MenuItem.Kategori.MAKANAN);
        tampilkanKategori("☕  MINUMAN", MenuItem.Kategori.MINUMAN);
        tampilkanPaket();

        System.out.println(GARIS);
        System.out.println("  * Delivery: ongkir Rp 2.000 (GRATIS jika total ≥ Rp 50.000)");
        System.out.println("  * Take Away / Dijemput: diskon 10%");
        System.out.println(GARIS + "\n");
    }

    // ── Private ───────────────────────────────────────────────────────────────

    private void tampilkanKategori(String judul, MenuItem.Kategori kategori) {
        List<MenuItem> list = menu.getByKategori(kategori);
        System.out.println(GARIS_TIPIS);
        System.out.println("  " + judul);
        System.out.println(GARIS_TIPIS);
        System.out.printf("  %-10s  %-28s %s%n", "KODE", "NAMA", "HARGA");
        System.out.println(GARIS_TIPIS);
        for (MenuItem m : list) {
            System.out.println(m.formatBaris());
        }
    }

    private void tampilkanPaket() {
        List<MenuItem> pakets = menu.getByKategori(MenuItem.Kategori.PAKET);
        System.out.println(GARIS_TIPIS);
        System.out.println("  🎁  PAKET PROMO");
        System.out.println(GARIS_TIPIS);
        System.out.printf("  %-10s  %-20s %8s  %s%n", "KODE", "NAMA PAKET", "HARGA", "KETERANGAN");
        System.out.println(GARIS_TIPIS);
        for (MenuItem m : pakets) {
            System.out.printf("  %-10s  %-20s Rp %,6.0f  %s%n",
                    m.getKode(), m.getNama(), m.getHarga(), m.getDeskripsi());
        }
    }

    private String tengah(String teks) {
        int pad = (LEBAR - teks.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + teks;
    }
}
