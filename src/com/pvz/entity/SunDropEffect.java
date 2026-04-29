package com.pvz.entity;

import java.awt.*;

public class SunDropEffect extends Effect {

    private float targetY;
    private float speed = 80f;
    private boolean collected = false;
    private float collectTimer = 0f;
    private static final float COLLECT_TIMEOUT = 6f; // tự thu sau 6 giây

    public SunDropEffect(float x, float startY, float targetY) {
        this.x       = x;
        this.y       = startY;
        this.targetY = targetY;
    }

    @Override
    public void update(float dt) {
        if (!collected) {
            if (y < targetY) y += speed * dt;
            collectTimer += dt;
            if (collectTimer >= COLLECT_TIMEOUT) finished = true;
        } else {
            timer += dt;
            if (timer >= 0.3f) finished = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (finished) return;
        float scale = collected ? (1f + timer * 3f) : 1f;
        int   alpha = collected ? (int)(255 * (1 - timer / 0.3f)) : 255;
        int   size  = (int)(36 * scale);

        // Hào quang
        g.setColor(new Color(255, 220, 0, Math.max(0, alpha / 3)));
        g.fillOval((int)(x - size * 0.8f), (int)(y - size * 0.8f),
                (int)(size * 1.6f), (int)(size * 1.6f));

        // Mặt trời
        g.setColor(new Color(255, 210, 0, Math.max(0, alpha)));
        g.fillOval((int)(x - size/2), (int)(y - size/2), size, size);

        // Mắt
        if (!collected) {
            g.setColor(new Color(180, 120, 0, Math.max(0, alpha)));
            g.fillOval((int)(x - 8), (int)(y - 6), 6, 6);
            g.fillOval((int)(x + 2),  (int)(y - 6), 6, 6);
        }
    }

    public boolean isCollected()  { return collected; }
    public void collect()         { collected = true; timer = 0; }
    public boolean canCollect()   { return !collected && y >= targetY - 5; }
    public float getDrawX()       { return x; }
    public float getDrawY()       { return y; }
}