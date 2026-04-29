package com.pvz.entity;

import java.awt.*;
import java.util.List;

public class Sunflower extends Plant {

    private float sunTimer = 0f;
    private static final float SUN_INTERVAL = 5f; // tạo sun mỗi 5 giây

    public Sunflower(int row, int col) {
        super(row, col, 300, 50);
    }

    public int updateAndGetSun(float dt) {
        super.update(dt);
        sunTimer += dt;
        if (sunTimer >= SUN_INTERVAL) {
            sunTimer = 0;
            return 25; // trả về lượng sun tạo ra
        }
        return 0;
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Cánh hoa
        g.setColor(new Color(255, 200, 0));
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);
            int cx = x + w/2 + (int)(20 * Math.cos(angle));
            int cy = y + h/2 + (int)(20 * Math.sin(angle));
            g.fillOval(cx - 8, cy - 8, 16, 16);
        }

        // Nhụy hoa
        g.setColor(new Color(200, 140, 0));
        g.fillOval(x + w/2 - 16, y + h/2 - 16, 32, 32);

        // Mắt
        g.setColor(Color.WHITE);
        g.fillOval(x + w/2 - 10, y + h/2 - 8, 9, 9);
        g.fillOval(x + w/2 + 1,  y + h/2 - 8, 9, 9);
        g.setColor(Color.BLACK);
        g.fillOval(x + w/2 - 8,  y + h/2 - 6, 5, 5);
        g.fillOval(x + w/2 + 3,  y + h/2 - 6, 5, 5);

        drawHealthBar(g, x, y, w);
    }
}