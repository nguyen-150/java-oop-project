package com.pvz.entity;

import java.awt.*;

public class Zombie {

    protected int row;
    protected float x;          // vị trí pixel (float để di chuyển mượt)
    protected int hp;
    protected int maxHp;
    protected float speed;      // pixel/giây
    protected int damage = 100;
    protected float attackTimer = 0f;
    protected float attackCooldown = 1f;
    protected boolean eating = false;

    private float freezeTimer = 0f;
    private float originalSpeed;

    public void freeze(float duration) {
        if (freezeTimer <= 0) originalSpeed = speed;
        speed       = originalSpeed * 0.3f;
        freezeTimer = duration;
    }

    public Zombie(int row, float startX, int hp, float speed) {
        this.row   = row;
        this.x     = startX;
        this.hp    = hp;
        this.maxHp = hp;
        this.speed = speed;
    }

    public void update(float dt) {
        if (freezeTimer > 0) {
            freezeTimer -= dt;
            if (freezeTimer <= 0) speed = originalSpeed;
        }
        if (!eating) {
            x -= speed * dt;    // đi từ phải sang trái
        } else {
            attackTimer += dt;
        }
    }

    public void draw(Graphics2D g, int y, int h) {
        int xi = (int) x;

        // Thân — hình chữ nhật xanh lá nhạt
        g.setColor(new Color(120, 180, 100));
        g.fillRect(xi + 15, y + 15, 30, 50);

        // Đầu
        g.setColor(new Color(140, 200, 120));
        g.fillOval(xi + 10, y + 5, 40, 35);

        // Mắt đỏ
        g.setColor(new Color(220, 50, 50));
        g.fillOval(xi + 16, y + 14, 8, 8);
        g.fillOval(xi + 32, y + 14, 8, 8);

        // Tay duỗi ra trước
        g.setColor(new Color(120, 180, 100));
        g.fillRect(xi, y + 20, 15, 8);

        // Thanh máu
        drawHealthBar(g, xi, y, 60);
    }

    protected void drawHealthBar(Graphics2D g, int x, int y, int w) {
        g.setColor(Color.RED);
        g.fillRect(x, y - 8, w, 5);
        g.setColor(new Color(0, 200, 0));
        int green = (int)(w * ((float) hp / maxHp));
        g.fillRect(x, y - 8, green, 5);
    }

    public boolean isDead()             { return hp <= 0; }
    public void takeDamage(int dmg)     { hp -= dmg; }
    public void setEating(boolean b)    { eating = b; }
    public boolean isEating()           { return eating; }
    public float getX()                 { return x; }
    public int getRow()                 { return row; }
    public int getDamage()              { return damage; }
    public float getAttackCooldown()    { return attackCooldown; }
    public float getAttackTimer()       { return attackTimer; }
    public void resetAttackTimer()      { attackTimer = 0; }
}