package model;

/**
 * Representasi satu baris pesanan dalam transaksi.
 * Menyimpan referensi ke MenuItem dan jumlah yang dipesan.
 */
public class OrderItem {

    private final MenuItem menuItem;
    private int            jumlah;

    public OrderItem(MenuItem menuItem, int jumlah) {
        if (menuItem == null)  throw new IllegalArgumentException("MenuItem tidak boleh null.");
        if (jumlah   <= 0)     throw new IllegalArgumentException("Jumlah harus lebih dari 0.");

        this.menuItem = menuItem;
        this.jumlah   = jumlah;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public MenuItem getMenuItem()  { return menuItem;                      }
    public int      getJumlah()    { return jumlah;                        }
    public double   getSubtotal()  { return menuItem.getHarga() * jumlah;  }

    public void tambahJumlah(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Jumlah tambahan harus positif.");
        this.jumlah += qty;
    }

    // ── Display ───────────────────────────────────────────────────────────────

    /**
     * Format satu baris struk:
     * "BKW-001  Bakwan              x2   Rp  20.000"
     */
    public String formatStruk() {
        return String.format("  %-10s  %-22s x%-3d  Rp %,8.0f",
                menuItem.getKode(),
                menuItem.getNama(),
                jumlah,
                getSubtotal());
    }

    @Override
    public String toString() {
        return String.format("OrderItem{kode='%s', jumlah=%d, subtotal=%.0f}",
                menuItem.getKode(), jumlah, getSubtotal());
    }
}
