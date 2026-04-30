package com.pvz.entity;

import java.awt.*;

public class SnowProjectile extends Projectile {

    public SnowProjectile(int row, float startX, int damage) {
        super(row, startX, damage);
    }

    @Override
    public void draw(Graphics2D g, int cellY, int cellH) {
        // Đạn màu xanh băng
        g.setColor(new Color(150, 220, 255));
        g.fillOval((int)getX(), cellY + cellH/2 - 8, 16, 16);
        g.setColor(new Color(80, 180, 220));
        g.drawOval((int)getX(), cellY + cellH/2 - 8, 16, 16);
        // Hoa tuyết nhỏ ở giữa
        g.setColor(Color.WHITE);
        g.fillOval((int)getX() + 4, cellY + cellH/2 - 4, 8, 8);
    }

    public boolean isFreeze() { return true; }
}