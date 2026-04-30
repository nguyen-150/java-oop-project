package com.pvz.core;

import java.awt.*;
import java.awt.event.*;
import com.pvz.util.SaveManager;

public class GameOverState implements GameState {

    private final Game game;
    private final boolean win;

    public GameOverState(Game game, boolean win) {
        this.game = game;
        this.win  = win;
        if (!win) SaveManager.deleteSave();  // thua → xóa save
    }

    @Override
    public void update(float dt) {}

    @Override
    public void draw(Graphics2D g) {
        // Nền
        g.setColor(win ? new Color(20, 80, 20) : new Color(80, 10, 10));
        g.fillRect(0, 0, 900, 600);

        // Tiêu đề
        String title   = win ? "YOU WIN!" : "GAME OVER";
        Color  titleC  = win ? new Color(255, 220, 0) : new Color(255, 60, 60);
        g.setColor(titleC);
        g.setFont(new Font("Arial", Font.BOLD, 80));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (900 - fm.stringWidth(title)) / 2, 260);

        // Subtitle
        String sub = win ? "All zombies defeated!" : "The zombies ate your brains!";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 26));
        fm = g.getFontMetrics();
        g.drawString(sub, (900 - fm.stringWidth(sub)) / 2, 320);

        // Nút Play Again
        g.setColor(new Color(50, 150, 50));
        g.fillRoundRect(300, 380, 300, 60, 15, 15);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString("PLAY AGAIN", 365, 420);

        // Nút Menu
        g.setColor(new Color(80, 80, 180));
        g.fillRoundRect(300, 460, 300, 60, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("MAIN MENU", 368, 500);

        // Hướng dẫn
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Press R to restart  |  Press M for menu", 320, 545);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (x >= 300 && x <= 600 && y >= 380 && y <= 440) {
            game.setState(new PlayingState(game));
        }
        if (x >= 300 && x <= 600 && y >= 460 && y <= 520) {
            game.setState(new MenuState(game));
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) game.setState(new PlayingState(game));
        if (e.getKeyCode() == KeyEvent.VK_M) game.setState(new MenuState(game));
    }
}