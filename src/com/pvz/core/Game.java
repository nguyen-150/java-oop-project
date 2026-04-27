package com.pvz.core;

import com.pvz.level.GameWorld;

public class Game implements Runnable {

    private static final int TARGET_FPS = 60;
    private static final double NS_PER_FRAME = 1_000_000_000.0 / TARGET_FPS;

    private GamePanel panel;
    private GameWorld world;
    private Thread gameThread;
    private boolean running = false;

    public Game() {
        world = new GameWorld();
        panel = new GamePanel(world);
        new GameWindow(panel);
        InputHandler input = new InputHandler(world);
        panel.addMouseListener(input);
        panel.addKeyListener(input);
        panel.setFocusable(true);
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            double now = System.nanoTime();
            delta += (now - lastTime) / NS_PER_FRAME;
            lastTime = now;

            while (delta >= 1) {
                update((float)(1.0 / TARGET_FPS));
                delta--;
            }

            render();

            try { Thread.sleep(1); } catch (InterruptedException ignored) {}
        }
    }

    private void update(float dt) { world.update(dt); }
    private void render() { panel.repaint(); }

    public static void main(String[] args) {
        new Game().start();
    }
}