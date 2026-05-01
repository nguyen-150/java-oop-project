package com.pvz.util;

import java.io.*;
import java.nio.file.*;

public class SaveManager {

    private static final String SAVE_DIR  = "userData";
    private static final String SAVE_FILE = "userData/save.txt";

    // ── Save ────────────────────────────────────────────────

    public static void save(int wave, int sun) {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
            String data = wave + "\n" + sun;
            Files.writeString(Paths.get(SAVE_FILE), data);
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    // ── Load ────────────────────────────────────────────────

    public static int[] load() {
        try {
            String content = Files.readString(Paths.get(SAVE_FILE));
            String[] lines = content.split("\n");
            int wave = Integer.parseInt(lines[0].trim());
            int sun = Integer.parseInt(lines[1].trim());
            return new int[]{wave, sun};
        } catch (IOException e) {
            return null;
        }
    }


        // ── Delete ──────────────────────────────────────────────

    public static void deleteSave() {
        try {
            Files.deleteIfExists(Paths.get(SAVE_FILE));
        } catch (IOException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    public static boolean hasSave() {
        return Files.exists(Paths.get(SAVE_FILE));
    }
}