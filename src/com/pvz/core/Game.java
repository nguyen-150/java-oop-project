package com.pvz.core;

public class Game implements Runnable {

    private static final int    TARGET_FPS   = 60;
    private static final double NS_PER_FRAME = 1_000_000_000.0 / TARGET_FPS;

    private GamePanel panel;
    private GameState currentState;
    private Thread    gameThread;
    private boolean   running = false;

    public Game() {
        panel = new GamePanel(this);
        new GameWindow(panel);
        panel.setFocusable(true);
        setState(new MenuState(this));   // bắt đầu ở Menu
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getState() {
        return currentState;
    }

    public void start() {
        running    = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double lastTime = System.nanoTime();
        double delta    = 0;

        while (running) {
            double now = System.nanoTime();
            delta += (now - lastTime) / NS_PER_FRAME;
            lastTime = now;

            while (delta >= 1) {
                if (currentState != null)
                    currentState.update((float)(1.0 / TARGET_FPS));
                delta--;
            }

            panel.repaint();

            try { Thread.sleep(1); } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}