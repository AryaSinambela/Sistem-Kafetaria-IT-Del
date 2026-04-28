package model;

import java.util.Optional;

/**
 * Layer layanan (service) yang mengkoordinasikan
 * operasi bisnis antara Menu, Order, dan Pelanggan.
 *
 * Memisahkan logika bisnis dari interaksi UI (driver).
 */
public class KafetariaService {

    private final Menu menu;

    public KafetariaService() {
        this.menu = new Menu();
        menu.inisialisasiMenu();
    }

    public Menu getMenu() { return menu; }

    /**
     * Buat order baru untuk seorang pelanggan.
     */
    public Order buatOrder(Pelanggan pelanggan) {
        return new Order(pelanggan);
    }

    /**
     * Tambah item ke order berdasarkan kode menu.
     * @return true jika berhasil, false jika kode tidak ditemukan.
     */
    public boolean tambahItemKeOrder(Order order, String kodeMenu, int jumlah) {
        Optional<MenuItem> opt = menu.cariByKode(kodeMenu);
        if (opt.isEmpty()) return false;
        order.tambahItem(opt.get(), jumlah);
        return true;
    }

    /**
     * Finalisasi order dengan metode pembayaran.
     * @return StrukPembayaran yang siap dicetak.
     */
    public StrukPembayaran finalisasiOrder(Order order, Order.MetodePembayaran metode) {
        order.setMetodePembayaran(metode);
        return new StrukPembayaran(order);
    }

    /**
     * Validasi apakah kode menu ada dalam katalog.
     */
    public boolean kodeValid(String kode) {
        return menu.tersedia(kode);
    }

    /**
     * Hitung estimasi harga untuk informasi pelanggan.
     */
    public String infoHargaItem(String kode) {
        return menu.cariByKode(kode)
                .map(m -> String.format("%-28s Rp %,.0f", m.getNama(), m.getHarga()))
                .orElse("Kode tidak ditemukan.");
    }
}
