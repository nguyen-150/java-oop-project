package com.pvz.entity;

import java.awt.*;
import java.util.List;

public class PeaShooter extends Plant {

    public PeaShooter(int row, int col) {
        super(row, col, 300, 100);
        this.attackCooldown = 1.5f;
    }

    public void update(float dt, List<Projectile> projectiles, boolean zombieInRow) {
        super.update(dt);
        if (zombieInRow && attackTimer >= attackCooldown) {
            attackTimer = 0;
            float startX = com.pvz.level.GameWorld.GRID_X
                    + (col + 1) * com.pvz.level.GameWorld.CELL_W - 10f;
            projectiles.add(new Projectile(row, startX, 20));
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Thân cây — hình tròn xanh lá
        g.setColor(new Color(50, 180, 50));
        g.fillOval(x + 10, y + 20, w - 20, h - 30);

        // Nòng bắn — hình chữ nhật
        g.setColor(new Color(30, 140, 30));
        g.fillRect(x + w/2, y + h/2 - 6, w/2 - 5, 12);

        // Mắt
        g.setColor(Color.WHITE);
        g.fillOval(x + 18, y + 28, 12, 12);
        g.setColor(Color.BLACK);
        g.fillOval(x + 21, y + 31, 6, 6);

        drawHealthBar(g, x, y, w);
    }
}