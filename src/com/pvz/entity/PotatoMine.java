package com.pvz.entity;

import com.pvz.level.GameWorld;
import java.awt.*;
import java.util.List;

public class PotatoMine extends Plant {

    private float armTimer  = 3f;    // thời gian chuẩn bị
    private boolean armed   = false;
    private boolean exploded = false;

    public PotatoMine(int row, int col) {
        super(row, col, 300, 25);
    }

    public void update(float dt, List<Zombie> zombies, GameWorld world) {
        super.update(dt);
        if (!armed) {
            armTimer -= dt;
            if (armTimer <= 0) armed = true;
            return;
        }
        // Kích hoạt khi zombie đứng lên
        for (Zombie z : zombies) {
            if (z.getRow() != row || exploded) continue;
            float zx  = z.getX() + 30;
            float minX = GameWorld.GRID_X + col * GameWorld.CELL_W;
            float maxX = minX + GameWorld.CELL_W;
            if (zx >= minX && zx <= maxX) {
                exploded = true;
                z.takeDamage(1800);
                float cx = GameWorld.GRID_X + col * GameWorld.CELL_W + GameWorld.CELL_W / 2f;
                float cy = GameWorld.GRID_Y + row * GameWorld.CELL_H + GameWorld.CELL_H / 2f;
                world.addExplosion(cx, cy, 60f);
                hp = 0;
                break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        if (!armed) {
            // Chưa kích hoạt — nằm dưới đất
            g.setColor(new Color(139, 100, 60));
            g.fillOval(x + 15, y + h - 35, w - 30, 25);
            g.setColor(new Color(180, 140, 80));
            g.fillOval(x + 20, y + h - 40, w - 40, 22);
            // Đếm ngược
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString(String.format("%.1f", Math.max(0, armTimer)), x + 28, y + h - 22);
        } else {
            // Đã kích hoạt — nổi lên
            g.setColor(new Color(139, 100, 60));
            g.fillOval(x + 10, y + 15, w - 20, h - 25);
            // Mắt cảnh báo
            g.setColor(new Color(255, 50, 50));
            g.fillOval(x + 18, y + 28, 12, 12);
            g.fillOval(x + 38, y + 28, 12, 12);
            g.setColor(Color.BLACK);
            g.fillOval(x + 21, y + 31,  6,  6);
            g.fillOval(x + 41, y + 31,  6,  6);
            // Chữ !
            g.setColor(new Color(255, 50, 50));
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("!", x + w/2 - 4, y + 55);
        }
        drawHealthBar(g, x, y, w);
    }
}