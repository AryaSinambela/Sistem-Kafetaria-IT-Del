package model;

/**
 * Data pelanggan untuk satu sesi transaksi.
 */
public class Pelanggan {

    private final String nama;
    private final int    noMeja;          // 0 = take-away / delivery
    private final JenisLayanan jenis;

    public enum JenisLayanan {
        MAKAN_DI_TEMPAT,
        TAKE_AWAY,
        DELIVERY
    }

    public Pelanggan(String nama, int noMeja, JenisLayanan jenis) {
        if (nama == null || nama.isBlank()) throw new IllegalArgumentException("Nama pelanggan wajib diisi.");
        if (jenis == JenisLayanan.MAKAN_DI_TEMPAT && noMeja <= 0)
            throw new IllegalArgumentException("Nomor meja harus lebih dari 0 untuk makan di tempat.");

        this.nama   = nama.trim();
        this.noMeja = noMeja;
        this.jenis  = jenis;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String        getNama()   { return nama;   }
    public int           getNoMeja() { return noMeja; }
    public JenisLayanan  getJenis()  { return jenis;  }

    public String labelLayanan() {
        return switch (jenis) {
            case MAKAN_DI_TEMPAT -> "Makan di Tempat (Meja " + noMeja + ")";
            case TAKE_AWAY       -> "Take Away / Dijemput";
            case DELIVERY        -> "Delivery / Antar";
        };
    }

    @Override
    public String toString() {
        return String.format("Pelanggan{nama='%s', noMeja=%d, jenis=%s}", nama, noMeja, jenis);
    }
}
