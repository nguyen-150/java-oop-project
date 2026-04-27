package com.pvz.core;

import com.pvz.level.GameWorld;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static final int WIDTH  = 900;
    public static final int HEIGHT = 600;

    private final GameWorld world;

    public GamePanel(GameWorld world) {
        this.world = world;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        world.draw(g2);
    }
}