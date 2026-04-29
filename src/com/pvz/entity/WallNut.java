package com.pvz.entity;

import java.awt.*;

public class WallNut extends Plant {

    public WallNut(int row, int col) {
        super(row, col, 4000, 50);
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Thân — hình elip nâu
        float ratio = (float) hp / maxHp;
        Color brown = ratio > 0.5f
                ? new Color(180, 120, 60)
                : new Color(140, 80, 30);    // tối hơn khi máu thấp

        g.setColor(brown);
        g.fillOval(x + 8, y + 10, w - 16, h - 20);

        // Đường nứt khi HP thấp
        if (ratio < 0.66f) {
            g.setColor(new Color(80, 40, 10));
            g.setStroke(new BasicStroke(2));
            g.drawLine(x + w/2 - 5, y + 20, x + w/2,     y + 40);
            g.drawLine(x + w/2,     y + 40, x + w/2 + 8,  y + 55);
            g.setStroke(new BasicStroke(1));
        }

        // Mắt
        g.setColor(Color.WHITE);
        g.fillOval(x + 20, y + 28, 10, 10);
        g.fillOval(x + 42, y + 28, 10, 10);
        g.setColor(Color.BLACK);
        g.fillOval(x + 22, y + 30, 6, 6);
        g.fillOval(x + 44, y + 30, 6, 6);

        drawHealthBar(g, x, y, w);
    }
}