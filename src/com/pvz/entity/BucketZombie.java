package com.pvz.entity;

import java.awt.*;

public class BucketZombie extends Zombie {

    public BucketZombie(int row, float startX) {
        super(row, startX, 1100, 55f);
    }

    @Override
    public void draw(Graphics2D g, int y, int h) {
        int xi = (int) x;

        // Thân
        g.setColor(new Color(120, 180, 100));
        g.fillRect(xi + 15, y + 20, 30, 45);

        // Đầu
        g.setColor(new Color(140, 200, 120));
        g.fillOval(xi + 10, y + 8, 40, 32);

        // Xô kim loại trên đầu
        g.setColor(new Color(160, 160, 160));
        g.fillRect(xi + 8,  y + 2,  44, 20);
        g.setColor(new Color(120, 120, 120));
        g.fillRect(xi + 8,  y + 2,  44, 5);
        g.setColor(new Color(180, 180, 180));
        g.drawRect(xi + 8,  y + 2,  44, 20);

        // Mắt đỏ
        g.setColor(new Color(220, 50, 50));
        g.fillOval(xi + 16, y + 16, 8, 8);
        g.fillOval(xi + 32, y + 16, 8, 8);

        // Tay
        g.setColor(new Color(120, 180, 100));
        g.fillRect(xi, y + 25, 15, 8);

        drawHealthBar(g, xi, y, 60);
    }
}