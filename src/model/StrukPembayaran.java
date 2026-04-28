package model;

import java.time.format.DateTimeFormatter;

/**
 * Mencetak struk pembayaran ke konsol.
 * Bertanggung jawab hanya pada presentasi / formatting.
 */
public class StrukPembayaran {

    private static final int    LEBAR       = 54;
    private static final String GARIS       = "=".repeat(LEBAR);
    private static final String GARIS_TIPIS = "-".repeat(LEBAR);

    private final Order order;

    public StrukPembayaran(Order order) {
        if (order == null) throw new IllegalArgumentException("Order tidak boleh null.");
        this.order = order;
    }

    /** Cetak struk lengkap ke System.out */
    public void cetak() {
        StringBuilder sb = build();
        System.out.println(sb);
    }

    /** Kembalikan struk sebagai String (untuk keperluan test / log) */
    public String sebagaiTeks() {
        return build().toString();
    }

    // ── Private builder ───────────────────────────────────────────────────────

    private StringBuilder build() {
        StringBuilder sb = new StringBuilder();
        Pelanggan p = order.getPelanggan();

        sb.append("\n").append(GARIS).append("\n");
        sb.append(tengah("KAFETARIA IT DEL")).append("\n");
        sb.append(tengah("Institut Teknologi Del")).append("\n");
        sb.append(tengah("Laguboti, Toba Samosir")).append("\n");
        sb.append(GARIS_TIPIS).append("\n");

        // ── Info transaksi ────────────────────────────────────────────────
        sb.append(String.format("  No. Order : %-34s%n", order.getNoOrder()));
        sb.append(String.format("  Tanggal   : %-34s%n",
                order.getWaktu().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
        sb.append(String.format("  Pelanggan : %-34s%n", p.getNama()));
        sb.append(String.format("  Layanan   : %-34s%n", p.labelLayanan()));

        sb.append(GARIS_TIPIS).append("\n");
        sb.append(String.format("  %-10s  %-22s %-4s  %-10s%n",
                "KODE", "NAMA ITEM", "QTY", "SUBTOTAL"));
        sb.append(GARIS_TIPIS).append("\n");

        // ── Daftar item ───────────────────────────────────────────────────
        for (OrderItem oi : order.getItems()) {
            sb.append(oi.formatStruk()).append("\n");
        }

        sb.append(GARIS_TIPIS).append("\n");

        // ── Kalkulasi ─────────────────────────────────────────────────────
        sb.append(kananKiri("  Subtotal", formatRp(order.getSubtotal()))).append("\n");

        if (order.getDiskon() > 0) {
            sb.append(kananKiri("  Diskon Take Away (10%)", "- " + formatRp(order.getDiskon()))).append("\n");
        }

        if (order.getOngkir() > 0) {
            sb.append(kananKiri("  Ongkos Kirim", "+ " + formatRp(order.getOngkir()))).append("\n");
        } else if (order.getPelanggan().getJenis() == Pelanggan.JenisLayanan.DELIVERY) {
            sb.append(kananKiri("  Ongkos Kirim", "GRATIS")).append("\n");
        }

        sb.append(GARIS).append("\n");
        sb.append(kananKiri("  TOTAL", formatRp(order.getTotal()))).append("\n");
        sb.append(GARIS).append("\n");

        // ── Pembayaran ────────────────────────────────────────────────────
        if (order.getMetodePembayaran() != null) {
            String metode = order.getMetodePembayaran() == Order.MetodePembayaran.TRANSFER
                    ? "TRANSFER (BCA / BRI / BNI)" : "TUNAI";
            sb.append(kananKiri("  Pembayaran", metode)).append("\n");
        }

        sb.append(GARIS_TIPIS).append("\n");
        sb.append(tengah("Terima kasih sudah mampir!")).append("\n");
        sb.append(tengah("Semangat belajar, Mahasiswa IT Del!")).append("\n");
        sb.append(GARIS).append("\n");

        return sb;
    }

    // ── Util ──────────────────────────────────────────────────────────────────

    private String tengah(String teks) {
        int pad = (LEBAR - teks.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + teks;
    }

    private String kananKiri(String kiri, String kanan) {
        int spasi = LEBAR - kiri.length() - kanan.length();
        return kiri + " ".repeat(Math.max(1, spasi)) + kanan;
    }

    private String formatRp(double nilai) {
        return String.format("Rp %,.0f", nilai);
    }
}
