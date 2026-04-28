package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Representasi satu transaksi pesanan di Kafetaria IT Del.
 * Menghitung total, diskon, dan ongkir secara otomatis.
 */
public class Order {

    // ── Konstanta ─────────────────────────────────────────────────────────────
    private static final double ONGKIR              = 2_000;
    private static final double MIN_GRATIS_ONGKIR   = 50_000;
    private static final double DISKON_TAKE_AWAY    = 0.10;   // 10 %

    // ── State ─────────────────────────────────────────────────────────────────
    private static int         nomorUrut = 1;          // counter sederhana antar sesi

    private final String        noOrder;
    private final Pelanggan     pelanggan;
    private final LocalDateTime waktu;
    private final List<OrderItem> items = new ArrayList<>();

    private MetodePembayaran metodePembayaran;

    public enum MetodePembayaran { TUNAI, TRANSFER }

    // ── Konstruktor ───────────────────────────────────────────────────────────

    public Order(Pelanggan pelanggan) {
        if (pelanggan == null) throw new IllegalArgumentException("Pelanggan tidak boleh null.");
        this.pelanggan = pelanggan;
        this.waktu     = LocalDateTime.now();
        this.noOrder   = String.format("ORD-%s-%04d",
                waktu.format(DateTimeFormatter.ofPattern("yyMMdd")),
                nomorUrut++);
    }

    // ── Manipulasi Item ───────────────────────────────────────────────────────

    /**
     * Tambah item ke pesanan. Jika kode sudah ada, jumlah ditambah.
     */
    public void tambahItem(MenuItem menuItem, int jumlah) {
        for (OrderItem oi : items) {
            if (oi.getMenuItem().getKode().equals(menuItem.getKode())) {
                oi.tambahJumlah(jumlah);
                return;
            }
        }
        items.add(new OrderItem(menuItem, jumlah));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() { return items.isEmpty(); }

    // ── Perhitungan ───────────────────────────────────────────────────────────

    public double getSubtotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public double getDiskon() {
        Pelanggan.JenisLayanan jenis = pelanggan.getJenis();
        if (jenis == Pelanggan.JenisLayanan.TAKE_AWAY ||
            jenis == Pelanggan.JenisLayanan.DELIVERY) {        // delivery tetap dapat diskon 10%
            // koreksi: take-away saja yang dapat diskon per soal
        }
        if (jenis == Pelanggan.JenisLayanan.TAKE_AWAY) {
            return getSubtotal() * DISKON_TAKE_AWAY;
        }
        return 0;
    }

    public double getOngkir() {
        if (pelanggan.getJenis() != Pelanggan.JenisLayanan.DELIVERY) return 0;
        return (getSubtotal() - getDiskon()) >= MIN_GRATIS_ONGKIR ? 0 : ONGKIR;
    }

    public double getTotal() {
        return getSubtotal() - getDiskon() + getOngkir();
    }

    // ── Getter lain ───────────────────────────────────────────────────────────

    public String           getNoOrder()         { return noOrder;          }
    public Pelanggan        getPelanggan()        { return pelanggan;        }
    public LocalDateTime    getWaktu()            { return waktu;            }
    public MetodePembayaran getMetodePembayaran() { return metodePembayaran; }

    public void setMetodePembayaran(MetodePembayaran m) {
        this.metodePembayaran = m;
    }
}
