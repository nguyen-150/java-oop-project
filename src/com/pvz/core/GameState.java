package com.pvz.core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface GameState {
    void update(float dt);
    void draw(Graphics2D g);
    void onMousePressed(MouseEvent e);
    void onKeyPressed(KeyEvent e);
}