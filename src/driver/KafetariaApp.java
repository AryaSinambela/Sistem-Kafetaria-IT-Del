package driver;

import model.*;

import java.util.Scanner;


public class KafetariaApp {

    // ── Konstanta tampilan ─────────────────────────────────────────────────────
    private static final String GARIS       = "=".repeat(54);
    private static final String GARIS_TIPIS = "-".repeat(54);

    // ── Komponen utama ─────────────────────────────────────────────────────────
    private final KafetariaService service;
    private final Scanner          sc;
    private final InputValidator   validator;

    public KafetariaApp() {
        this.service   = new KafetariaService();
        this.sc        = new Scanner(System.in);
        this.validator = new InputValidator(sc);
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    public static void main(String[] args) {
        new KafetariaApp().jalankan();
    }

    // ── Alur utama ────────────────────────────────────────────────────────────

    private void jalankan() {
        tampilkanBanner();

        boolean lanjut = true;
        while (lanjut) {
            prosesTransaksi();
            lanjut = validator.bacaYN("\n  Transaksi baru");
        }

        System.out.println("\n" + GARIS);
        System.out.println("  Sistem ditutup. Terima kasih, sampai jumpa!");
        System.out.println(GARIS + "\n");
        sc.close();
    }

    // ── Proses satu transaksi ─────────────────────────────────────────────────

    private void prosesTransaksi() {
        // 1) Data pelanggan
        Pelanggan pelanggan = inputDataPelanggan();

        // 2) Buat order
        Order order = service.buatOrder(pelanggan);

        // 3) Tampilkan menu
        MenuDisplay display = new MenuDisplay(service.getMenu());
        display.tampilkanSemua();

        // 4) Loop input pesanan
        inputPesanan(order);

        if (order.isEmpty()) {
            System.out.println("  [!] Tidak ada item dipesan. Transaksi dibatalkan.");
            return;
        }

        // 5) Tampilkan ringkasan sementara
        tampilkanRingkasan(order);

        // 6) Konfirmasi
        if (!validator.bacaYN("  Lanjutkan pembayaran")) {
            System.out.println("  Transaksi dibatalkan.");
            return;
        }

        // 7) Metode pembayaran
        Order.MetodePembayaran metode = inputMetodePembayaran();

        // 8) Cetak struk
        StrukPembayaran struk = service.finalisasiOrder(order, metode);
        struk.cetak();
    }

    // ── Sub-proses: input data pelanggan ─────────────────────────────────────

    private Pelanggan inputDataPelanggan() {
        System.out.println("\n" + GARIS);
        System.out.println("  DATA PELANGGAN");
        System.out.println(GARIS_TIPIS);

        String nama = validator.bacaString("  Nama Pelanggan : ");

        System.out.println("\n  Jenis Layanan:");
        System.out.println("    1. Makan di Tempat");
        System.out.println("    2. Take Away / Dijemput  (diskon 10%)");
        System.out.println("    3. Delivery / Antar      (ongkir Rp 2.000, gratis ≥ Rp 50.000)");

        int pilihanJenis = validator.bacaInt("  Pilih (1-3) : ", 1, 3);

        Pelanggan.JenisLayanan jenis = switch (pilihanJenis) {
            case 1 -> Pelanggan.JenisLayanan.MAKAN_DI_TEMPAT;
            case 2 -> Pelanggan.JenisLayanan.TAKE_AWAY;
            default -> Pelanggan.JenisLayanan.DELIVERY;
        };

        int noMeja = 0;
        if (jenis == Pelanggan.JenisLayanan.MAKAN_DI_TEMPAT) {
            noMeja = validator.bacaInt("  Nomor Meja (1-30) : ", 1, 30);
        }

        return new Pelanggan(nama, noMeja, jenis);
    }

    // ── Sub-proses: input pesanan ─────────────────────────────────────────────

    private void inputPesanan(Order order) {
        System.out.println("\n" + GARIS);
        System.out.println("  INPUT PESANAN");
        System.out.println("  Masukkan kode menu (contoh: BKW-001).");
        System.out.println("  Ketik 'SELESAI' jika sudah, 'MENU' untuk tampilkan ulang.");
        System.out.println(GARIS_TIPIS);

        MenuDisplay display = new MenuDisplay(service.getMenu());

        while (true) {
            System.out.print("  Kode Menu : ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("SELESAI")) break;

            if (input.equals("MENU")) {
                display.tampilkanSemua();
                continue;
            }

            if (input.isBlank()) continue;

            if (!service.kodeValid(input)) {
                System.out.println("  [!] Kode '" + input + "' tidak ditemukan. Cek kembali daftar menu.");
                continue;
            }

            int jumlah = validator.bacaInt("  Jumlah (1-20) : ", 1, 20);
            boolean ok = service.tambahItemKeOrder(order, input, jumlah);

            if (ok) {
                System.out.println("  ✓  " + service.infoHargaItem(input) + "  x" + jumlah + " ditambahkan.");
            }

            System.out.println("  (Ketik kode lain atau 'SELESAI' untuk lanjut)");
        }
    }

    // ── Sub-proses: ringkasan sementara ──────────────────────────────────────

    private void tampilkanRingkasan(Order order) {
        System.out.println("\n" + GARIS);
        System.out.println("  RINGKASAN PESANAN");
        System.out.println(GARIS_TIPIS);
        for (OrderItem oi : order.getItems()) {
            System.out.println(oi.formatStruk());
        }
        System.out.println(GARIS_TIPIS);
        System.out.printf("  %-38s Rp %,.0f%n", "Subtotal",   order.getSubtotal());
        if (order.getDiskon() > 0)
            System.out.printf("  %-38s -Rp %,.0f%n", "Diskon Take Away (10%)", order.getDiskon());
        if (order.getPelanggan().getJenis() == Pelanggan.JenisLayanan.DELIVERY) {
            if (order.getOngkir() > 0)
                System.out.printf("  %-38s Rp %,.0f%n", "Ongkos Kirim", order.getOngkir());
            else
                System.out.printf("  %-38s %s%n", "Ongkos Kirim", "GRATIS");
        }
        System.out.println(GARIS);
        System.out.printf("  %-38s Rp %,.0f%n", "TOTAL", order.getTotal());
        System.out.println(GARIS);
    }

    // ── Sub-proses: metode pembayaran ─────────────────────────────────────────

    private Order.MetodePembayaran inputMetodePembayaran() {
        System.out.println("\n  Metode Pembayaran:");
        System.out.println("    1. Tunai");
        System.out.println("    2. Transfer (BCA / BRI / BNI)");
        int p = validator.bacaInt("  Pilih (1-2) : ", 1, 2);
        return p == 1 ? Order.MetodePembayaran.TUNAI : Order.MetodePembayaran.TRANSFER;
    }

    // ── Banner ────────────────────────────────────────────────────────────────

    private void tampilkanBanner() {
        System.out.println("\n" + GARIS);
        System.out.println("  ██╗████████╗    ██████╗ ███████╗██╗     ");
        System.out.println("  ██║╚══██╔══╝    ██╔══██╗██╔════╝██║     ");
        System.out.println("  ██║   ██║       ██║  ██║█████╗  ██║     ");
        System.out.println("  ██║   ██║       ██║  ██║██╔══╝  ██║     ");
        System.out.println("  ██║   ██║       ██████╔╝███████╗███████╗");
        System.out.println("  ╚═╝   ╚═╝       ╚═════╝ ╚══════╝╚══════╝");
        System.out.println(GARIS_TIPIS);
        System.out.println("      SISTEM KAFETARIA IT DEL  –  v1.0");
        System.out.println("      Institut Teknologi Del, Laguboti");
        System.out.println(GARIS + "\n");
    }
}
