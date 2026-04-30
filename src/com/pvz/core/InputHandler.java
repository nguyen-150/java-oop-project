package com.pvz.core;

import com.pvz.level.GameWorld;
import java.awt.event.*;

public class InputHandler extends MouseAdapter implements KeyListener {

    private final GameWorld world;

    public InputHandler(GameWorld world) {
        this.world = world;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        world.handleClick(e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> world.togglePause();
            case KeyEvent.VK_1      -> world.selectPlant(0);
            case KeyEvent.VK_2      -> world.selectPlant(1);
            case KeyEvent.VK_3      -> world.selectPlant(2);
            case KeyEvent.VK_4      -> world.selectPlant(3);
            case KeyEvent.VK_5      -> world.selectPlant(4);
            case KeyEvent.VK_6      -> world.selectPlant(5);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}