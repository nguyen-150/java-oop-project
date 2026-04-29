package com.pvz.core;

import com.pvz.level.GameWorld;
import java.awt.*;
import java.awt.event.*;

public class PlayingState implements GameState {

    private final Game      game;
    private final GameWorld world;

    public PlayingState(Game game) {
        this.game  = game;
        this.world = new GameWorld(game);
    }

    @Override
    public void update(float dt) {
        world.update(dt);
    }

    @Override
    public void draw(Graphics2D g) {
        world.draw(g);
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        world.handleClick(e.getX(), e.getY());
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> world.togglePause();
            case KeyEvent.VK_1      -> world.selectPlant(0);
            case KeyEvent.VK_2      -> world.selectPlant(1);
            case KeyEvent.VK_3      -> world.selectPlant(2);
        }
    }
}