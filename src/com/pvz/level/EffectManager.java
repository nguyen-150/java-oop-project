package com.pvz.level;

import com.pvz.entity.Effect;
import com.pvz.entity.SunDropEffect;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EffectManager {

    private final List<Effect>       effects  = new ArrayList<>();
    private final List<SunDropEffect> sunDrops = new ArrayList<>();

    public void add(Effect e)          { effects.add(e); }
    public void addSunDrop(SunDropEffect s) { sunDrops.add(s); }

    public void update(float dt) {
        effects.removeIf(Effect::isFinished);
        effects.forEach(e -> e.update(dt));

        sunDrops.removeIf(Effect::isFinished);
        sunDrops.forEach(s -> s.update(dt));
    }

    public void draw(Graphics2D g) {
        sunDrops.forEach(s -> s.draw(g));
        effects.forEach(e -> e.draw(g));
    }

    // Trả về số sun thu được khi click
    public int collectSunAt(int px, int py) {
        for (SunDropEffect s : sunDrops) {
            if (!s.canCollect()) continue;
            float dx = s.getDrawX() - px;
            float dy = s.getDrawY() - py;
            if (dx * dx + dy * dy < 30 * 30) {
                s.collect();
                return 25;
            }
        }
        return 0;
    }

    public List<SunDropEffect> getSunDrops() { return sunDrops; }
}