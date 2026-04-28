# ══════════════════════════════════════════════════════════
#  Makefile – Sistem Kafetaria IT Del
#  Institut Teknologi Del
# ══════════════════════════════════════════════════════════

# ── Konfigurasi ───────────────────────────────────────────
JAVAC       := javac
JAVA        := java
SRC_DIR     := src
BUILD_DIR   := build
JAR_NAME    := kafetaria-itdel.jar
MAIN_CLASS  := driver.KafetariaApp

# ── Source files ──────────────────────────────────────────
MODEL_SRC   := $(SRC_DIR)/model/MenuItem.java    \
               $(SRC_DIR)/model/OrderItem.java   \
               $(SRC_DIR)/model/Menu.java        \
               $(SRC_DIR)/model/Pelanggan.java   \
               $(SRC_DIR)/model/Order.java       \
               $(SRC_DIR)/model/StrukPembayaran.java \
               $(SRC_DIR)/model/MenuDisplay.java \
               $(SRC_DIR)/model/InputValidator.java  \
               $(SRC_DIR)/model/KafetariaService.java

DRIVER_SRC  := $(SRC_DIR)/driver/KafetariaApp.java

ALL_SRC     := $(MODEL_SRC) $(DRIVER_SRC)

# ── Targets ───────────────────────────────────────────────

.PHONY: all compile run jar clean help

## all: compile dan jalankan aplikasi
all: compile run

## compile: kompilasi semua source Java
compile:
	@echo "╔══ Kompilasi Kafetaria IT Del ══╗"
	@mkdir -p $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) -sourcepath $(SRC_DIR) $(ALL_SRC)
	@echo "✓  Kompilasi berhasil → $(BUILD_DIR)/"

## run: jalankan aplikasi (wajib compile dahulu)
run:
	@echo "╔══ Menjalankan Kafetaria IT Del ══╗"
	$(JAVA) -cp $(BUILD_DIR) $(MAIN_CLASS)

## jar: buat file JAR executable
jar: compile
	@echo "╔══ Membuat JAR ══╗"
	@echo "Main-Class: $(MAIN_CLASS)" > $(BUILD_DIR)/MANIFEST.MF
	jar cfm $(JAR_NAME) $(BUILD_DIR)/MANIFEST.MF -C $(BUILD_DIR) .
	@echo "✓  JAR dibuat → $(JAR_NAME)"
	@echo "   Jalankan dengan: java -jar $(JAR_NAME)"

## run-jar: jalankan dari file JAR
run-jar: jar
	$(JAVA) -jar $(JAR_NAME)

## clean: hapus hasil build
clean:
	@echo "╔══ Membersihkan build ══╗"
	rm -rf $(BUILD_DIR) $(JAR_NAME)
	@echo "✓  Build directory dihapus."

## help: tampilkan daftar perintah
help:
	@echo ""
	@echo "  Sistem Kafetaria IT Del – Makefile Commands"
	@echo "  ──────────────────────────────────────────"
	@grep -E '^## ' Makefile | sed 's/## /  make /'
	@echo ""
