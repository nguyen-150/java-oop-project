package com.pvz.entity;

import java.awt.*;

public class FreezeEffect extends Effect {

    private float duration;
    private float px, py, w, h;

    public FreezeEffect(float px, float py, float w, float h, float duration) {
        this.px       = px;
        this.py       = py;
        this.w        = w;
        this.h        = h;
        this.duration = duration;
    }

    @Override
    public void update(float dt) {
        timer += dt;
        if (timer >= duration) finished = true;
    }

    @Override
    public void draw(Graphics2D g) {
        float ratio = timer / duration;
        int   alpha = (int)(120 * (1 - ratio));
        // Overlay xanh băng
        g.setColor(new Color(150, 210, 255, Math.max(0, alpha)));
        g.fillRoundRect((int)px, (int)py, (int)w, (int)h, 10, 10);
        // Viền băng
        g.setColor(new Color(100, 180, 255, Math.max(0, alpha + 60)));
        g.setStroke(new BasicStroke(2));
        g.drawRoundRect((int)px, (int)py, (int)w, (int)h, 10, 10);
        g.setStroke(new BasicStroke(1));
    }
}