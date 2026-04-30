package com.pvz.level;

import com.pvz.entity.ExplosionEffect;
import com.pvz.entity.FreezeEffect;
import com.pvz.entity.SunDropEffect;
import com.pvz.entity.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private final EffectManager effectManager = new EffectManager();
    private float sunDropTimer = 0f;
    private float sunDropDelay = 7f;  // sun rơi mỗi 7 giây

    public static final int ROWS   = 5;
    public static final int COLS   = 9;
    public static final int CELL_W = 80;
    public static final int CELL_H = 90;
    public static final int GRID_X = 60;
    public static final int GRID_Y = 60;

    private final Plant[][]        grid        = new Plant[ROWS][COLS];
    private final List<Zombie>     zombies     = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();

    private int     sun           = 150;
    private int     selectedPlant = -1;
    private boolean paused        = false;
    private float   spawnTimer    = 0f;
    private float   spawnDelay    = 5f;

    private final com.pvz.core.Game game;
    private int     wave          = 1;
    private int     zombiesLeft   = 10;
    private int     zombiesKilled = 0;
    private float   waveBreak     = 0f;
    private boolean waveBreaking  = false;

    public GameWorld(com.pvz.core.Game game) {
        this.game = game;
    }

    // ── Update ──────────────────────────────────────────────

    public void update(float dt) {
        if (paused) return;
        updatePlants(dt);
        updateZombies(dt);
        updateProjectiles(dt);
        checkCollisions();
        spawnZombies(dt);
        checkWinLose();
        effectManager.update(dt);
        spawnSunDrop(dt);
    }

    private void updatePlants(float dt) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Plant p = grid[r][c];
                if (p == null) continue;

                if (p instanceof Sunflower sf) {
                    sun += sf.updateAndGetSun(dt);
                } else if (p instanceof PeaShooter ps) {
                    ps.update(dt, projectiles, hasZombieInRow(r, c));
                } else if (p instanceof SnowPea sp) {
                    sp.update(dt, projectiles, hasZombieInRow(r, c));
                } else if (p instanceof CherryBomb cb) {
                    cb.update(dt, zombies, this);
                } else if (p instanceof PotatoMine pm) {
                    pm.update(dt, zombies, this);
                } else {
                    p.update(dt);
                }

                if (p.isDead()) grid[r][c] = null;
            }
        }
    }

    private void updateZombies(float dt) {
        for (Zombie z : zombies) {
            int col = pixelToCol(z.getX());
            Plant plantAhead = (col >= 0 && col < COLS) ? grid[z.getRow()][col] : null;

            if (plantAhead != null) {
                z.setEating(true);
                z.update(dt);
                if (z.getAttackTimer() >= z.getAttackCooldown()) {
                    plantAhead.takeDamage(z.getDamage());
                    z.resetAttackTimer();
                    if (plantAhead.isDead()) {
                        grid[z.getRow()][col] = null;
                        z.setEating(false);
                    }
                }
            } else {
                z.setEating(false);
                z.update(dt);
            }
        }
        List<Zombie> toRemove = zombies.stream()
                .filter(Zombie::isDead).toList();
        toRemove.forEach(z -> effectManager.add(
                new ExplosionEffect(z.getX() + 30, GRID_Y + z.getRow() * CELL_H + 45)));
        zombies.removeIf(Zombie::isDead);
    }
    private void checkWinLose() {
        // Thua: zombie qua màn hình trái
        for (Zombie z : zombies) {
            if (z.getX() < GRID_X - 60) {
                game.setState(new com.pvz.core.GameOverState(game, false));
                return;
            }
        }
        // Thắng wave: hết zombie + không còn zombie nào trên màn
        if (zombiesLeft <= 0 && zombies.isEmpty() && !waveBreaking) {
            if (wave >= 3) {
                game.setState(new com.pvz.core.GameOverState(game, true));
            } else {
                waveBreaking = true;
            }
        }
    }
    private void updateProjectiles(float dt) {
        projectiles.forEach(p -> p.update(dt));
        projectiles.removeIf(p -> p.isOffScreen() || p.isHit());
    }

    private void checkCollisions() {
        for (Projectile proj : projectiles) {
            if (proj.isHit()) continue;
            for (Zombie z : zombies) {
                if (z.getRow() != proj.getRow()) continue;
                float zombieLeft  = z.getX();
                float zombieRight = z.getX() + 60;
                if (proj.getX() >= zombieLeft && proj.getX() <= zombieRight) {
                    z.takeDamage(proj.getDamage());
                    proj.markHit();
                    if (proj instanceof SnowProjectile) {
                        z.freeze(3f);
                    }
                    break;
                }
            }
        }
    }

    private void spawnZombies(float dt) {
        if (waveBreaking) {
            waveBreak += dt;
            if (waveBreak >= 5f) {  // nghỉ 5 giây giữa wave
                waveBreaking = false;
                waveBreak    = 0;
                nextWave();
            }
            return;
        }
        if (zombiesLeft <= 0) return;
        spawnTimer += dt;
        if (spawnTimer >= spawnDelay) {
            spawnTimer = 0;
            int row = (int)(Math.random() * ROWS);
            zombies.add(new BasicZombie(row, 920f));
            zombiesLeft--;
        }
    }

    private void nextWave() {
        wave++;
        zombiesLeft  = 5 + wave * 3;   // wave càng cao càng nhiều zombie
        spawnDelay   = Math.max(1.5f, 5f - wave * 0.3f);  // spawn nhanh hơn
        zombiesKilled = 0;
    }

    private void spawnSunDrop(float dt) {
        sunDropTimer += dt;
        if (sunDropTimer >= sunDropDelay) {
            sunDropTimer = 0;
            float x       = 100 + (float)(Math.random() * 700);
            float targetY = 100 + (float)(Math.random() * 350);
            effectManager.addSunDrop(new SunDropEffect(x, -30, targetY));
        }
    }

    public void addExplosion(float cx, float cy, float maxRadius) {
        effectManager.add(new ExplosionEffect(cx, cy, maxRadius));
    }



    // ── Draw ────────────────────────────────────────────────

    public void draw(Graphics2D g) {
        drawBackground(g);
        drawGrid(g);
        drawPlants(g);
        drawProjectiles(g);
        drawZombies(g);
        effectManager.draw(g);
        drawHUD(g);
        if (paused) drawPauseOverlay(g);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(new Color(106, 168, 79));
        g.fillRect(0, 0, 900, 600);
        g.setColor(new Color(160, 120, 60));
        g.fillRect(0, 555, 900, 45);
    }

    private void drawGrid(Graphics2D g) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int x = GRID_X + c * CELL_W;
                int y = GRID_Y + r * CELL_H;
                g.setColor((r + c) % 2 == 0
                        ? new Color(120, 180, 90, 80)
                        : new Color(90, 150, 60, 80));
                g.fillRect(x, y, CELL_W, CELL_H);
                g.setColor(new Color(0, 0, 0, 40));
                g.drawRect(x, y, CELL_W, CELL_H);
            }
        }
    }

    private void drawPlants(Graphics2D g) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] != null) {
                    int x = GRID_X + c * CELL_W;
                    int y = GRID_Y + r * CELL_H;
                    grid[r][c].draw(g, x, y, CELL_W, CELL_H);
                }
            }
        }
    }

    private void drawProjectiles(Graphics2D g) {
        for (Projectile p : projectiles) {
            int y = GRID_Y + p.getRow() * CELL_H;
            p.draw(g, y, CELL_H);
        }
    }

    private void drawZombies(Graphics2D g) {
        for (Zombie z : zombies) {
            int y = GRID_Y + z.getRow() * CELL_H;
            z.draw(g, y, CELL_H);
        }
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, 900, 55);

        // Sun counter
        g.setColor(new Color(255, 220, 0));
        g.fillOval(10, 8, 36, 36);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(String.valueOf(sun), 54, 32);

        // Seed packets
        String[] names  = {"PeaShooter", "Sunflower", "WallNut", "SnowPea", "CherryBomb", "PotatoMine"};
        Color[]  colors = {
                new Color(50,180,50), new Color(255,200,0), new Color(180,120,60),
                new Color(80,180,220), new Color(220,50,50), new Color(139,100,60)
        };
        int[] costs = {100, 50, 50, 175, 150, 25};

        for (int i = 0; i < 6; i++) {
            int px = 120 + i * 75;
            if (selectedPlant == i) {
                g.setColor(new Color(255, 255, 0, 180));
                g.fillRoundRect(px - 3, 3, 71, 49, 8, 8);
            }
            g.setColor(sun >= costs[i] ? colors[i] : colors[i].darker().darker());
            g.fillRoundRect(px, 6, 65, 43, 8, 8);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 9));
            g.drawString(names[i], px + 3, 20);
            g.drawString(costs[i] + " sun", px + 3, 40);
        }

        g.setColor(new Color(255, 255, 255, 180));
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("1/2/3 = chon cay  |  Click = dat  |  ESC = pause", 380, 32);

        // Wave indicator
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Wave: " + wave + "/3", 780, 32);
        g.drawString("Zombies: " + (zombiesLeft + zombies.size()), 780, 48);

        if (waveBreaking) {
            g.setColor(new Color(255, 220, 0));
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Wave " + wave + " cleared!  Next wave in 5s...", 220, 340);
        }
    }

    private void drawPauseOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 900, 600);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 56));
        g.drawString("PAUSED", 320, 310);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Nhan ESC de tiep tuc", 340, 350);
    }

    // ── Input ────────────────────────────────────────────────

    public void handleClick(int px, int py) {
        // Thu sun trước
        int gained = effectManager.collectSunAt(px, py);
        if (gained > 0) {
            sun += gained;
            return;
        }

        if (py < 55) {
            for (int i = 0; i < 3; i++) {
                int x = 120 + i * 80;
                if (px >= x && px <= x + 68) {
                    selectedPlant = i;
                    return;
                }
            }
            return;
        }
        int col = (px - GRID_X) / CELL_W;
        int row = (py - GRID_Y) / CELL_H;
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return;
        if (grid[row][col] != null) return;

        int[] costs = {100, 50, 50, 175, 150, 25};
        if (selectedPlant < 0 || sun < costs[selectedPlant]) return;

        switch (selectedPlant) {
            case 0 -> grid[row][col] = new PeaShooter(row, col);
            case 1 -> grid[row][col] = new Sunflower(row, col);
            case 2 -> grid[row][col] = new WallNut(row, col);
            case 3 -> grid[row][col] = new SnowPea(row, col);
            case 4 -> grid[row][col] = new CherryBomb(row, col);
            case 5 -> grid[row][col] = new PotatoMine(row, col);
        }
        sun -= costs[selectedPlant];
    }

    public void togglePause()      { paused = !paused; }
    public void selectPlant(int i) { selectedPlant = i; }

    // ── Helpers ──────────────────────────────────────────────

    private boolean hasZombieInRow(int row, int fromCol) {
        float minX = GRID_X + fromCol * (float) CELL_W;
        for (Zombie z : zombies) {
            if (z.getRow() == row && z.getX() > minX) return true;
        }
        return false;
    }

    private int pixelToCol(float x) {
        return (int)((x - GRID_X) / CELL_W);
    }
}