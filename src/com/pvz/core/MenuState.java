package com.pvz.core;

import java.awt.*;
import java.awt.event.*;
import com.pvz.util.SaveManager;

public class MenuState implements GameState {

    private final Game game;
    private int hoveredButton = -1; // 0=Play, 1=Quit

    private final boolean hasSave;

    public MenuState(Game game) {
        this.game    = game;
        this.hasSave = SaveManager.hasSave();
    }

    @Override
    public void update(float dt) {}

    @Override
    public void draw(Graphics2D g) {
        // Nền gradient xanh lá
        g.setColor(new Color(60, 120, 40));
        g.fillRect(0, 0, 900, 600);

        // Trang trí cỏ phía dưới
        g.setColor(new Color(80, 160, 50));
        for (int i = 0; i < 900; i += 40) {
            g.fillRect(i, 520, 20, 80);
            g.fillRect(i + 10, 530, 20, 70);
        }

        // Tiêu đề
        g.setColor(new Color(255, 220, 0));
        g.setFont(new Font("Arial", Font.BOLD, 64));
        drawShadowText(g, "Plants vs Zombies", 90, 160, new Color(100, 60, 0));

        g.setColor(new Color(200, 255, 100));
        g.setFont(new Font("Arial", Font.BOLD, 22));
        drawShadowText(g, "Java Edition", 370, 210, new Color(50, 80, 20));

        // Nút Play (đổi vị trí xuống)
        drawButton(g, "NEW GAME", 300, 290, 300, 55,
                hoveredButton == 0,
                new Color(50, 180, 50), new Color(30, 140, 30));

        // Nút Continue — chỉ hiện nếu có save
        if (hasSave) {
            drawButton(g, "CONTINUE", 300, 360, 300, 55,
                    hoveredButton == 2,
                    new Color(50, 120, 200), new Color(30, 80, 160));
        }

        // Nút Quit
        drawButton(g, "QUIT", 350, 435, 200, 55,
                hoveredButton == 1,
                new Color(200, 60, 60), new Color(160, 30, 30));

        // Credits
        g.setColor(new Color(200, 255, 150));
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Click PLAY GAME or press ENTER to start", 300, 470);
    }

    private void drawButton(Graphics2D g, String text, int x, int y,
                            int w, int h, boolean hovered, Color normal, Color dark) {
        g.setColor(hovered ? normal.brighter() : normal);
        g.fillRoundRect(x, y, w, h, 15, 15);
        g.setColor(dark);
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(x, y, w, h, 15, 15);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (w - fm.stringWidth(text)) / 2;
        int ty = y + (h + fm.getAscent() - fm.getDescent()) / 2;
        g.drawString(text, tx, ty);
    }

    private void drawShadowText(Graphics2D g, String text, int x, int y, Color shadow) {
        Color original = g.getColor();
        g.setColor(shadow);
        g.drawString(text, x + 3, y + 3);
        g.setColor(original);
        g.drawString(text, x, y);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();

        // New Game
        if (x >= 300 && x <= 600 && y >= 290 && y <= 345) {
            SaveManager.deleteSave();
            game.setState(new PlayingState(game));
        }
        // Continue
        if (hasSave && x >= 300 && x <= 600 && y >= 360 && y <= 415) {
            PlayingState ps = new PlayingState(game);
            ps.getWorld().loadGame();
            game.setState(ps);
        }
        // Quit
        if (x >= 350 && x <= 550 && y >= 435 && y <= 490) {
            System.exit(0);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            game.setState(new PlayingState(game));
        }
    }
}