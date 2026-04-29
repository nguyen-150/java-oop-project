package com.pvz.entity;

import java.awt.*;

public class ExplosionEffect extends Effect {

    private float radius    = 5f;
    private float maxRadius = 80f;
    private float duration  = 0.4f;

    public ExplosionEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(float dt) {
        timer  += dt;
        radius  = maxRadius * (timer / duration);
        if (timer >= duration) finished = true;
    }

    @Override
    public void draw(Graphics2D g) {
        float ratio   = timer / duration;
        int   alpha   = (int)(255 * (1 - ratio));

        // Vòng lửa ngoài
        g.setColor(new Color(255, 80, 0, Math.max(0, alpha)));
        g.fillOval((int)(x - radius), (int)(y - radius),
                (int)(radius * 2), (int)(radius * 2));

        // Vòng vàng trong
        float inner = radius * 0.6f;
        g.setColor(new Color(255, 220, 0, Math.max(0, Math.min(255, alpha + 60))));
        g.fillOval((int)(x - inner), (int)(y - inner),
                (int)(inner * 2), (int)(inner * 2));

        // Tâm trắng
        float core = radius * 0.25f;
        g.setColor(new Color(255, 255, 255, Math.max(0, Math.min(255, alpha + 100))));
        g.fillOval((int)(x - core), (int)(y - core),
                (int)(core * 2), (int)(core * 2));
    }
}