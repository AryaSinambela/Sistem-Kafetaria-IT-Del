package model;

import java.util.Scanner;

/**
 * Utility class untuk validasi dan pembacaan input dari pengguna.
 * Menghindari duplikasi logika input-handling di driver.
 */
public class InputValidator {

    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Baca string tidak kosong dari stdin.
     * Akan terus meminta input hingga tidak kosong.
     */
    public String bacaString(String prompt) {
        String input = "";
        while (input.isBlank()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isBlank()) System.out.println("  [!] Input tidak boleh kosong. Coba lagi.");
        }
        return input;
    }

    /**
     * Baca integer dalam rentang [min, max].
     */
    public int bacaInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(raw);
                if (val < min || val > max) {
                    System.out.printf("  [!] Masukkan angka antara %d dan %d.%n", min, max);
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input harus berupa angka.");
            }
        }
    }

    /**
     * Baca pilihan menu dari daftar pilihan yang valid.
     * Pilihan tidak case-sensitive.
     */
    public String bacaPilihan(String prompt, String... pilihan) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            for (String p : pilihan) {
                if (p.equalsIgnoreCase(input)) return p.toUpperCase();
            }
            System.out.print("  [!] Pilihan tidak valid. Opsi: ");
            System.out.println(String.join(" / ", pilihan));
        }
    }

    /**
     * Baca 'Y' atau 'N', kembalikan true untuk Y.
     */
    public boolean bacaYN(String prompt) {
        String p = bacaPilihan(prompt + " (Y/N): ", "Y", "N");
        return p.equals("Y");
    }
}
