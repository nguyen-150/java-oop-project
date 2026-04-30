package com.pvz.entity;

import com.pvz.level.GameWorld;
import java.awt.*;
import java.util.List;

public class SnowPea extends Plant {

    public SnowPea(int row, int col) {
        super(row, col, 300, 175);
        this.attackCooldown = 1.5f;
    }

    public void update(float dt, List<Projectile> projectiles, boolean zombieInRow) {
        super.update(dt);
        if (zombieInRow && attackTimer >= attackCooldown) {
            attackTimer = 0;
            float startX = GameWorld.GRID_X + (col + 1) * GameWorld.CELL_W - 10f;
            projectiles.add(new SnowProjectile(row, startX, 20));
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Thân — xanh dương nhạt
        g.setColor(new Color(80, 180, 220));
        g.fillOval(x + 10, y + 20, w - 20, h - 30);

        // Nòng bắn
        g.setColor(new Color(50, 140, 190));
        g.fillRect(x + w/2, y + h/2 - 6, w/2 - 5, 12);

        // Hoa tuyết trang trí
        g.setColor(new Color(200, 240, 255));
        g.fillOval(x + 18, y + 30, 10, 10);
        g.fillOval(x + 30, y + 22, 8,  8);

        // Mắt
        g.setColor(Color.WHITE);
        g.fillOval(x + 18, y + 28, 12, 12);
        g.setColor(Color.BLACK);
        g.fillOval(x + 21, y + 31,  6,  6);

        drawHealthBar(g, x, y, w);
    }
}