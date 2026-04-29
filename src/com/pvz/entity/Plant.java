package com.pvz.entity;

import java.awt.*;

public abstract class Plant {

    protected int row, col;
    protected int hp;
    protected int maxHp;
    protected int sunCost;
    protected float attackTimer = 0f;
    protected float attackCooldown = 1.5f;

    public Plant(int row, int col, int hp, int sunCost) {
        this.row    = row;
        this.col    = col;
        this.hp     = hp;
        this.maxHp  = hp;
        this.sunCost = sunCost;
    }

    public void update(float dt) {
        attackTimer += dt;
    }

    public abstract void draw(Graphics2D g, int x, int y, int w, int h);

    // Vẽ thanh máu phía trên ô
    protected void drawHealthBar(Graphics2D g, int x, int y, int w) {
        int barH = 5;
        g.setColor(Color.RED);
        g.fillRect(x + 4, y + 2, w - 8, barH);
        g.setColor(Color.GREEN);
        int green = (int)((w - 8) * ((float) hp / maxHp));
        g.fillRect(x + 4, y + 2, green, barH);
    }

    public boolean isDead()  { return hp <= 0; }
    public void takeDamage(int dmg) { hp -= dmg; }
    public int getRow()      { return row; }
    public int getCol()      { return col; }
    public int getSunCost()  { return sunCost; }
}