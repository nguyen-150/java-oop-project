package com.pvz.level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    public static final int ROWS   = 5;
    public static final int COLS   = 9;
    public static final int CELL_W = 80;
    public static final int CELL_H = 90;
    public static final int GRID_X = 60;
    public static final int GRID_Y = 80;

    private final List<Object> plants  = new ArrayList<>();
    private final List<Object> zombies = new ArrayList<>();

    private boolean paused = false;
    private int selectedPlantIndex = -1;

    // ── Update ──────────────────────────────────────────────

    public void update(float dt) {
        if (paused) return;
        // Giai đoạn 2 sẽ thêm logic entity vào đây
    }

    // ── Draw ────────────────────────────────────────────────

    public void draw(Graphics2D g) {
        drawBackground(g);
        drawGrid(g);
        drawDebugInfo(g);
    }

    private void drawBackground(Graphics2D g) {
        // Nền cỏ
        g.setColor(new Color(106, 168, 79));
        g.fillRect(0, 0, 900, 600);

        // Dải đất phía dưới
        g.setColor(new Color(160, 120, 60));
        g.fillRect(0, 555, 900, 45);
    }

    private void drawGrid(Graphics2D g) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int x = GRID_X + c * CELL_W;
                int y = GRID_Y + r * CELL_H;

                // Màu xen kẽ cho ô lưới
                if ((r + c) % 2 == 0) {
                    g.setColor(new Color(120, 180, 90, 80));
                } else {
                    g.setColor(new Color(90, 150, 60, 80));
                }
                g.fillRect(x, y, CELL_W, CELL_H);

                // Viền ô
                g.setColor(new Color(0, 0, 0, 40));
                g.drawRect(x, y, CELL_W, CELL_H);
            }
        }
    }

    private void drawDebugInfo(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Plants vs Zombies", 10, 20);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("ESC = Pause  |  1/2/3 = Select Plant  |  Click = Place", 10, 40);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, 900, 600);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("PAUSED", 350, 300);
        }
    }

    // ── Input handlers ──────────────────────────────────────

    public void handleClick(int px, int py) {
        int col = (px - GRID_X) / CELL_W;
        int row = (py - GRID_Y) / CELL_H;
        boolean inBounds = row >= 0 && row < ROWS && col >= 0 && col < COLS;
        if (inBounds && selectedPlantIndex >= 0) {
            System.out.println("Place plant " + selectedPlantIndex
                    + " at row=" + row + " col=" + col);
        }
    }

    public void togglePause()          { paused = !paused; }
    public void selectPlant(int index) { selectedPlantIndex = index; }
}