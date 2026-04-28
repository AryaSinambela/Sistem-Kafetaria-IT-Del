package model;

import java.util.*;

/**
 * Katalog menu Kafetaria IT Del.
 * Menyimpan semua MenuItem yang tersedia dan menyediakan
 * operasi pencarian berdasarkan kode atau kategori.
 */
public class Menu {

    private final Map<String, MenuItem> items = new LinkedHashMap<>();

    /**
     * Daftarkan semua menu yang tersedia.
     * Dipanggil sekali saat sistem diinisialisasi.
     */
    public void inisialisasiMenu() {
        // ── MAKANAN ──────────────────────────────────────────────────────────
        tambah(new MenuItem("BKW-001", "Bakwan",              10_000, MenuItem.Kategori.MAKANAN, "Gorengan bakwan jagung crispy"));
        tambah(new MenuItem("TMP-002", "Tempe Goreng",        10_000, MenuItem.Kategori.MAKANAN, "Tempe goreng tepung renyah"));
        tambah(new MenuItem("THU-003", "Tahu Goreng",         10_000, MenuItem.Kategori.MAKANAN, "Tahu goreng crispy isi"));
        tambah(new MenuItem("PSK-004", "Piscok",              10_000, MenuItem.Kategori.MAKANAN, "Pisang coklat goreng crispy"));
        tambah(new MenuItem("GBN-005", "Gabin",               10_000, MenuItem.Kategori.MAKANAN, "Roti gabin panggang isi keju"));
        tambah(new MenuItem("BGR-006", "Burger",              10_000, MenuItem.Kategori.MAKANAN, "Burger ayam dengan sayuran segar"));
        tambah(new MenuItem("MGR-007", "Mie Goreng",          15_000, MenuItem.Kategori.MAKANAN, "Mie goreng spesial bumbu pedas"));
        tambah(new MenuItem("NSI-008", "Nasi Goreng",         15_000, MenuItem.Kategori.MAKANAN, "Nasi goreng telur dengan acar"));
        tambah(new MenuItem("STO-009", "Siomay",              12_000, MenuItem.Kategori.MAKANAN, "Siomay ayam dengan bumbu kacang"));
        tambah(new MenuItem("LMP-010", "Lumpiah",             10_000, MenuItem.Kategori.MAKANAN, "Lumpia goreng isi sayuran"));
        tambah(new MenuItem("RTB-011", "Roti Bakar",          12_000, MenuItem.Kategori.MAKANAN, "Roti bakar isi coklat / keju"));
        tambah(new MenuItem("STO-012", "Stik Tahu",           10_000, MenuItem.Kategori.MAKANAN, "Tahu goreng stik crispy pedas"));

        // ── MINUMAN ──────────────────────────────────────────────────────────
        tambah(new MenuItem("KPC-101", "Kopi Cappuccino",     10_000, MenuItem.Kategori.MINUMAN, "Kopi cappuccino susu lembut"));
        tambah(new MenuItem("SDB-102", "Soda Bahagia",        10_000, MenuItem.Kategori.MINUMAN, "Soda segar campuran sirup"));
        tambah(new MenuItem("THS-103", "Teh Susu",            10_000, MenuItem.Kategori.MINUMAN, "Teh susu hangat / dingin"));
        tambah(new MenuItem("JUS-104", "Jus Alpukat",         12_000, MenuItem.Kategori.MINUMAN, "Jus alpukat susu segar"));
        tambah(new MenuItem("AIR-105", "Air Mineral",          5_000, MenuItem.Kategori.MINUMAN, "Air mineral 600ml"));
        tambah(new MenuItem("KLM-106", "Kopi Lemon",          12_000, MenuItem.Kategori.MINUMAN, "Kopi dingin perasan lemon"));
        tambah(new MenuItem("MCT-107", "Matcha Latte",        15_000, MenuItem.Kategori.MINUMAN, "Minuman matcha susu premium"));
        tambah(new MenuItem("CMC-108", "Coklat Panas",        10_000, MenuItem.Kategori.MINUMAN, "Minuman coklat panas creamy"));

        // ── PAKET PROMO ──────────────────────────────────────────────────────
        tambah(new MenuItem("PKT-P01", "Promo Hemat",         25_000, MenuItem.Kategori.PAKET,
                "Mie/Nasi Goreng + Minuman pilihan (hemat Rp5.000)"));
        tambah(new MenuItem("PKT-P02", "Promo Ultah",         30_000, MenuItem.Kategori.PAKET,
                "Burger + 2 Gorengan + Minuman + 1 Slice Cake"));
        tambah(new MenuItem("PKT-P03", "Promo Sarapan",       20_000, MenuItem.Kategori.PAKET,
                "Roti Bakar + Teh/Kopi (hemat Rp2.000)"));
        tambah(new MenuItem("PKT-P04", "Promo Mahasiswa",     22_000, MenuItem.Kategori.PAKET,
                "Nasi/Mie Goreng + Gorengan + Air Mineral"));
    }

    // ── CRUD ─────────────────────────────────────────────────────────────────

    public void tambah(MenuItem item) {
        items.put(item.getKode(), item);
    }

    public Optional<MenuItem> cariByKode(String kode) {
        return Optional.ofNullable(items.get(kode.toUpperCase()));
    }

    public List<MenuItem> getByKategori(MenuItem.Kategori kategori) {
        List<MenuItem> hasil = new ArrayList<>();
        for (MenuItem m : items.values()) {
            if (m.getKategori() == kategori) hasil.add(m);
        }
        return Collections.unmodifiableList(hasil);
    }

    public Collection<MenuItem> semuaItem() {
        return Collections.unmodifiableCollection(items.values());
    }

    public boolean tersedia(String kode) {
        return items.containsKey(kode.toUpperCase());
    }
}
