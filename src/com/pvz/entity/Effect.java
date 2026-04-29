package com.pvz.entity;

import java.awt.*;

public abstract class Effect {
    protected float x, y;
    protected float timer   = 0f;
    protected boolean finished = false;

    public abstract void update(float dt);
    public abstract void draw(Graphics2D g);
    public boolean isFinished() { return finished; }
}