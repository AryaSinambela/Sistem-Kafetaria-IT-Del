package model;

/**
 * Representasi satu item menu di Kafetaria IT Del.
 * Menyimpan informasi kode, nama, harga, dan kategori makanan/minuman.
 */
public class MenuItem {

    public enum Kategori {
        MAKANAN, MINUMAN, PAKET
    }

    private final String kode;
    private final String nama;
    private final double harga;
    private final Kategori kategori;
    private final String deskripsi;

    public MenuItem(String kode, String nama, double harga, Kategori kategori, String deskripsi) {
        if (kode == null || kode.isBlank())   throw new IllegalArgumentException("Kode item tidak boleh kosong.");
        if (nama == null || nama.isBlank())   throw new IllegalArgumentException("Nama item tidak boleh kosong.");
        if (harga < 0)                        throw new IllegalArgumentException("Harga tidak boleh negatif.");

        this.kode      = kode.toUpperCase();
        this.nama      = nama;
        this.harga     = harga;
        this.kategori  = kategori;
        this.deskripsi = deskripsi;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String   getKode()      { return kode;      }
    public String   getNama()      { return nama;       }
    public double   getHarga()     { return harga;      }
    public Kategori getKategori()  { return kategori;   }
    public String   getDeskripsi() { return deskripsi;  }

    // ── Display ───────────────────────────────────────────────────────────────

    /**
     * Format satu baris menu: "BKW-001  Bakwan                Rp  10.000"
     */
    public String formatBaris() {
        return String.format("  %-10s  %-28s Rp %,8.0f", kode, nama, harga);
    }

    @Override
    public String toString() {
        return String.format("MenuItem{kode='%s', nama='%s', harga=%.0f, kategori=%s}",
                kode, nama, harga, kategori);
    }
}
