package com.pvz.core;

import javax.swing.*;

public class GameWindow {

    public GameWindow(GamePanel panel) {
        JFrame frame = new JFrame("Plants vs Zombies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}