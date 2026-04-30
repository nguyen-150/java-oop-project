package com.pvz.entity;

import java.awt.*;

public class PoleVaultZombie extends Zombie {

    private boolean hasVaulted = false;
    private float   originalSpeed;

    public PoleVaultZombie(int row, float startX) {
        super(row, startX, 500, 100f);
        this.originalSpeed = speed;
    }

    public boolean hasVaulted() { return hasVaulted; }

    public void vault(float newX) {
        this.x         = newX;
        this.hasVaulted = true;
        this.speed     = 70f;  // chậm lại sau khi nhảy
    }

    @Override
    public void draw(Graphics2D g, int y, int h) {
        int xi = (int) x;

        // Thân
        g.setColor(new Color(100, 160, 80));
        g.fillRect(xi + 15, y + 20, 28, 45);

        // Đầu
        g.setColor(new Color(120, 180, 100));
        g.fillOval(xi + 10, y + 8, 38, 30);

        // Gậy nhảy sào (nếu chưa nhảy)
        if (!hasVaulted) {
            g.setColor(new Color(139, 90, 43));
            g.setStroke(new BasicStroke(3));
            g.drawLine(xi + 5, y + 10, xi + 5, y + h - 5);
            g.setStroke(new BasicStroke(1));
        }

        // Mắt
        g.setColor(new Color(220, 50, 50));
        g.fillOval(xi + 15, y + 15, 8, 8);
        g.fillOval(xi + 30, y + 15, 8, 8);

        // Tay
        g.setColor(new Color(100, 160, 80));
        g.fillRect(xi, y + 22, 15, 8);

        drawHealthBar(g, xi, y, 60);
    }
}