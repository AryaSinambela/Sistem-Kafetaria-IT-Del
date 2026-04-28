# 🍽 Sistem Kafetaria IT Del

> Sistem Point of Sale (POS) berbasis Java OOP untuk Kafetaria Institut Teknologi Del, Laguboti.

---

## 📋 Deskripsi

Sistem ini dibangun untuk mendukung operasional Kafetaria IT Del secara digital. Pelanggan dapat memesan makanan dan minuman melalui kode menu, memilih jenis layanan (makan di tempat, take away, atau delivery), dan mendapatkan struk pembayaran yang lengkap.

### Fitur Utama

| Fitur | Keterangan |
|---|---|
| 📖 Daftar Menu | Menampilkan semua menu dengan kode, nama, dan harga |
| 🔢 Input Kode Menu | Pemesanan menggunakan kode menu yang unik |
| 🛵 Delivery | Ongkir Rp 2.000 (gratis jika total ≥ Rp 50.000) |
| 🛍 Take Away | Diskon 10% untuk layanan take away / dijemput |
| 🎁 Paket Promo | Berbagai paket promo dengan harga spesial |
| 🧾 Struk Lengkap | Struk dengan nomor order, detail item, dan perhitungan |
| 💳 Multi-Pembayaran | Mendukung pembayaran tunai dan transfer |

---

## 🗂 Struktur Proyek

```
kafetaria-itdel/
│
├── src/
│   ├── model/                        ← Layer Model (domain & service)
│   │   ├── MenuItem.java             ← Entitas satu item menu
│   │   ├── OrderItem.java            ← Satu baris dalam pesanan
│   │   ├── Menu.java                 ← Katalog semua menu
│   │   ├── Pelanggan.java            ← Data pelanggan & jenis layanan
│   │   ├── Order.java                ← Transaksi pesanan + kalkulasi
│   │   ├── StrukPembayaran.java      ← Printer struk ke konsol
│   │   ├── MenuDisplay.java          ← Tampilan daftar menu
│   │   ├── InputValidator.java       ← Utility validasi input user
│   │   └── KafetariaService.java     ← Service layer (koordinasi bisnis)
│   │
│   └── driver/                       ← Layer Driver (UI / entry point)
│       └── KafetariaApp.java         ← Main class & alur interaksi
│
├── build/                            ← Output kompilasi (auto-generated)
├── Makefile                          ← Build automation
└── README.md                         ← Dokumentasi ini
```

---

## 📐 Desain OOP

### Diagram Kelas (Ringkas)

```
KafetariaApp (driver)
    └── uses ──→ KafetariaService
                    ├── owns ──→ Menu
                    │               └── contains ──→ MenuItem[]
                    ├── creates ──→ Order
                    │               ├── belongs to ──→ Pelanggan
                    │               └── contains ──→ OrderItem[]
                    │                               └── refs ──→ MenuItem
                    └── creates ──→ StrukPembayaran
```

### Prinsip OOP yang Diterapkan

| Prinsip | Implementasi |
|---|---|
| **Encapsulation** | Semua field `private final`, akses via getter |
| **Single Responsibility** | Setiap class punya satu tanggung jawab |
| **Separation of Concerns** | Model / Service / Driver dipisah tegas |
| **Immutability** | `MenuItem`, `Pelanggan` bersifat immutable |
| **Fail-Fast Validation** | Constructor melempar exception jika input tidak valid |

---

## 🍱 Daftar Menu

### Makanan

| Kode | Nama | Harga |
|---|---|---|
| BKW-001 | Bakwan | Rp 10.000 |
| TMP-002 | Tempe Goreng | Rp 10.000 |
| THU-003 | Tahu Goreng | Rp 10.000 |
| PSK-004 | Piscok | Rp 10.000 |
| GBN-005 | Gabin | Rp 10.000 |
| BGR-006 | Burger | Rp 10.000 |
| MGR-007 | Mie Goreng | Rp 15.000 |
| NSI-008 | Nasi Goreng | Rp 15.000 |
| STO-009 | Siomay | Rp 12.000 |
| LMP-010 | Lumpiah | Rp 10.000 |
| RTB-011 | Roti Bakar | Rp 12.000 |
| STO-012 | Stik Tahu | Rp 10.000 |

### Minuman

| Kode | Nama | Harga |
|---|---|---|
| KPC-101 | Kopi Cappuccino | Rp 10.000 |
| SDB-102 | Soda Bahagia | Rp 10.000 |
| THS-103 | Teh Susu | Rp 10.000 |
| JUS-104 | Jus Alpukat | Rp 12.000 |
| AIR-105 | Air Mineral | Rp 5.000 |
| KLM-106 | Kopi Lemon | Rp 12.000 |
| MCT-107 | Matcha Latte | Rp 15.000 |
| CMC-108 | Coklat Panas | Rp 10.000 |

### Paket Promo

| Kode | Nama Paket | Harga | Isi |
|---|---|---|---|
| PKT-P01 | Promo Hemat | Rp 25.000 | Mie/Nasi Goreng + Minuman |
| PKT-P02 | Promo Ultah | Rp 30.000 | Burger + 2 Gorengan + Minuman + Cake |
| PKT-P03 | Promo Sarapan | Rp 20.000 | Roti Bakar + Teh/Kopi |
| PKT-P04 | Promo Mahasiswa | Rp 22.000 | Nasi/Mie + Gorengan + Air Mineral |

---

## ⚙️ Aturan Bisnis

```
Delivery:
  - Ongkos kirim     = Rp 2.000
  - Gratis ongkir    = jika subtotal ≥ Rp 50.000

Take Away / Dijemput:
  - Diskon           = 10% dari subtotal

Makan di Tempat:
  - Tanpa biaya tambahan
  - Wajib memilih nomor meja (1–30)
```

---

## 🚀 Cara Menjalankan

### Prasyarat
- Java JDK 14+ (menggunakan switch expression)
- GNU Make

### Perintah

```bash
# Clone / ekstrak proyek
cd kafetaria-itdel

# Kompilasi
make compile

# Jalankan langsung
make run

# Kompilasi + Jalankan (sekaligus)
make

# Buat file JAR
make jar

# Jalankan dari JAR
make run-jar

# Bersihkan hasil build
make clean

# Tampilkan bantuan
make help
```

### Tanpa Makefile (manual)

```bash
mkdir -p build
javac -d build -sourcepath src src/model/*.java src/driver/KafetariaApp.java
java -cp build driver.KafetariaApp
```

---

## 🖥 Contoh Output

```
======================================================
      SISTEM KAFETARIA IT DEL  –  v1.0
      Institut Teknologi Del, Laguboti
======================================================

  DATA PELANGGAN
  ────────────────────────────────────────────────────
  Nama Pelanggan : Budi Santoso
  Jenis Layanan:
    1. Makan di Tempat
    2. Take Away / Dijemput  (diskon 10%)
    3. Delivery / Antar      (ongkir Rp 2.000)
  Pilih (1-3) : 1
  Nomor Meja (1-30) : 5

  Kode Menu : BKW-001
  Jumlah    : 2
  ✓  Bakwan                           Rp 10.000  x2 ditambahkan.

  Kode Menu : KPC-101
  Jumlah    : 1
  ✓  Kopi Cappuccino                  Rp 10.000  x1 ditambahkan.

  Kode Menu : SELESAI

======================================================
           KAFETARIA IT DEL
        Institut Teknologi Del
      Laguboti, Toba Samosir
------------------------------------------------------
  No. Order : ORD-260428-0001
  Tanggal   : 28-04-2026 14:35:22
  Pelanggan : Budi Santoso
  Layanan   : Makan di Tempat (Meja 5)
------------------------------------------------------
  KODE        NAMA ITEM              QTY    SUBTOTAL
------------------------------------------------------
  BKW-001     Bakwan                 x2     Rp  20.000
  KPC-101     Kopi Cappuccino        x1     Rp  10.000
------------------------------------------------------
  Subtotal                           Rp  30.000
======================================================
  TOTAL                              Rp  30.000
======================================================
  Pembayaran                              TUNAI
------------------------------------------------------
       Terima kasih sudah mampir!
    Semangat belajar, Mahasiswa IT Del!
======================================================
```

---

## 👨‍💻 Pengembang

| | |
|---|---|
| **Institusi** | Institut Teknologi Del |
| **Program** | Sistem Informasi / Teknik Informatika |
| **Versi** | 1.0.0 |
| **Bahasa** | Java 14+ |

---

## 📝 Lisensi

Proyek ini dikembangkan untuk keperluan akademik Institut Teknologi Del.
