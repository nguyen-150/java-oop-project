package com.pvz.util;

import java.io.*;
import java.nio.file.*;

public class SaveManager {

    private static final String SAVE_DIR  = "userData";
    private static final String SAVE_FILE = "userData/save.txt";

    // ── Save ────────────────────────────────────────────────

    public static void save(int wave, int sun, int score) {
        try {
            Files.createDirectories(Paths.get(SAVE_DIR));
            String data = wave + "\n" + sun + "\n" + score;
            Files.writeString(Paths.get(SAVE_FILE), data);
            System.out.println("Game saved!");
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    // ── Load ────────────────────────────────────────────────

    public static int[] load() {
        try {
            String content = Files.readString(Paths.get(SAVE_FILE));
            String[] lines = content.split("\n");
            int wave  = Integer.parseInt(lines[0].trim());
            int sun   = Integer.parseInt(lines[1].trim());
            int score = Integer.parseInt(lines[2].trim());
            System.out.println("Game loaded! Wave=" + wave + " Sun=" + sun);
            return new int[]{wave, sun, score};
        } catch (IOException e) {
            System.out.println("No save file found, starting new game.");
            return null;  // không có file save
        }
    }

    // ── Delete ──────────────────────────────────────────────

    public static void deleteSave() {
        try {
            Files.deleteIfExists(Paths.get(SAVE_FILE));
            System.out.println("Save deleted.");
        } catch (IOException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }

    public static boolean hasSave() {
        return Files.exists(Paths.get(SAVE_FILE));
    }
}