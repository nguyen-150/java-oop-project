package com.pvz.entity;

import com.pvz.level.GameWorld;
import java.awt.*;
import java.util.List;

public class CherryBomb extends Plant {

    private float fuseTimer = 2f;
    private boolean exploded = false;

    public CherryBomb(int row, int col) {
        super(row, col, 300, 150);
    }

    public void update(float dt, List<Zombie> zombies, GameWorld world) {
        super.update(dt);
        fuseTimer -= dt;
        if (fuseTimer <= 0 && !exploded) {
            exploded = true;
            explode(zombies, world);
            hp = 0; // tự hủy
        }
    }

    private void explode(List<Zombie> zombies, GameWorld world) {
        // Damage tất cả zombie trong vùng 3×3
        float cx = GameWorld.GRID_X + col * GameWorld.CELL_W + GameWorld.CELL_W / 2f;
        float cy = GameWorld.GRID_Y + row * GameWorld.CELL_H + GameWorld.CELL_H / 2f;

        for (Zombie z : zombies) {
            float zx = z.getX() + 30;
            float zy = GameWorld.GRID_Y + z.getRow() * GameWorld.CELL_H + 45f;
            double dist = Math.sqrt((zx - cx) * (zx - cx) + (zy - cy) * (zy - cy));
            if (dist < GameWorld.CELL_W * 1.8) {
                z.takeDamage(1800);
            }
        }
        world.addExplosion(cx, cy, 120f); // explosion lớn hơn
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int w, int h) {
        // Nhấp nháy đỏ khi sắp nổ
        boolean blink = (int)(fuseTimer * 4) % 2 == 0;

        // Quả cherry trái
        g.setColor(blink ? new Color(255, 50, 50) : new Color(200, 30, 30));
        g.fillOval(x + 8,  y + 25, 28, 28);

        // Quả cherry phải
        g.setColor(blink ? new Color(255, 80, 80) : new Color(220, 50, 50));
        g.fillOval(x + 28, y + 20, 28, 28);

        // Cuống
        g.setColor(new Color(60, 120, 30));
        g.setStroke(new BasicStroke(2));
        g.drawLine(x + 22, y + 25, x + 30, y + 15);
        g.drawLine(x + 42, y + 20, x + 35, y + 12);
        g.drawLine(x + 30, y + 15, x + 35, y + 12);
        g.setStroke(new BasicStroke(1));

        // Fuse timer
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        g.drawString(String.format("%.1f", Math.max(0, fuseTimer)), x + 22, y + 18);

        drawHealthBar(g, x, y, w);
    }
}