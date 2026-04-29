package com.pvz.entity;

import java.awt.*;

public class Projectile {

    private int     row;
    private float   x;
    private float   speed  = 300f;
    private int     damage;
    private boolean hit    = false;

    public Projectile(int row, float startX, int damage) {
        this.row    = row;
        this.x      = startX;
        this.damage = damage;
    }

    public void update(float dt) {
        x += speed * dt;
    }

    public void draw(Graphics2D g, int cellY, int cellH) {
        g.setColor(new Color(80, 200, 80));
        g.fillOval((int)x, cellY + cellH/2 - 8, 16, 16);
        g.setColor(new Color(40, 140, 40));
        g.drawOval((int)x, cellY + cellH/2 - 8, 16, 16);
    }

    public boolean isOffScreen() { return x > 950; }
    public boolean isHit()       { return hit; }
    public void    markHit()     { hit = true; }
    public int     getRow()      { return row; }
    public float   getX()        { return x; }
    public int     getDamage()   { return damage; }
}