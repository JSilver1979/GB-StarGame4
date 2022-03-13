package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.star.app.game.helpers.ObjectPool;
import com.star.app.screen.utils.Assets;

import java.util.Random;

public class BonusController extends ObjectPool<Bonus> {
    private TextureRegion bulletTexture;
    private GameController gc;

    public enum BonusType {
        HP,
        GOLD,
        AMMO
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(gc);
    }

    public BonusController(GameController gc) {
        this.gc = gc;
        this.bulletTexture = Assets.getInstance().getAtlas().findRegion("bullet");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Bonus b = activeList.get(i);
            batch.draw(bulletTexture, b.getPosition().x - 16, b.getPosition().y - 16);
        }
    }

    public void setup(float x, float y, float vx, float vy){
        int chanceToBonus = MathUtils.random(0,100);
        if (chanceToBonus > 80) {
            getActiveElement().activate(x, y, vx, vy, BonusType.values()[new Random().nextInt(BonusType.values().length)]);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
