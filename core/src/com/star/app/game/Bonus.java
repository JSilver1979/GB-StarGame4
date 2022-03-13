package com.star.app.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;

public class Bonus implements Poolable {
    private GameController gc;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float r1, g1, b1, a1;
    private float r2, g2, b2, a2;
    private int bonusAmount;
    private BonusController.BonusType bt;

    public BonusController.BonusType getBt() {
        return bt;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getBonusAmount() { return bonusAmount; }

    public Bonus(GameController gc) {
        this.gc = gc;
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
        this.r1 = 0.0f;
        this.g1 = 0.0f;
        this.b1 = 0.0f;
        this.a1 = 0.0f;
        this.r2 = 0.0f;
        this.g2 = 0.0f;
        this.b2 = 0.0f;
        this.a2 = 0.0f;
        this.bonusAmount = 0;
        this.bt = BonusController.BonusType.AMMO;
    }

    public void deactivate() {
        active = false;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < -20 || position.x > ScreenManager.SCREEN_WIDTH + 20 ||
                position.y < -20 || position.y > ScreenManager.SCREEN_HEIGHT + 20) {
            deactivate();
        }

        float bx = position.x ;
        float by = position.y ;

        for (int i = 0; i < 2; i++) {
            gc.getParticleController().setup(bx + MathUtils.random(-1, 1), by + MathUtils.random(-1, 1),
                    velocity.x * -0.1f + MathUtils.random(-5, 5), velocity.y * -0.1f + MathUtils.random(-5, 5),
                    0.01f,
                    3.0f, 3.0f,
                    r1, g1, b1, a1,
                    r2, g2, b2, a2);
        }
    }

    public void activate(float x, float y, float vx, float vy, BonusController.BonusType bt) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
        bonusAmount = MathUtils.random(10,100);
        this.bt = bt;
        switch (this.bt) {
            case AMMO:
                r1 = 0.0f;
                g1 = 0.5f;
                b1 = 1.0f;
                a1 = 1.0f;
                r2 = 0.0f;
                g2 = 0.7f;
                b2 = 1.0f;
                a2 = 0.0f;
                break;
            case HP:
                r1 = 1.0f;
                g1 = 0.0f;
                b1 = 0.0f;
                a1 = 1.0f;
                r2 = 1.0f;
                g2 = 0.0f;
                b2 = 0.0f;
                a2 = 0.0f;
                break;
            case GOLD:
                r1 = 1.0f;
                g1 = 1.5f;
                b1 = 0.0f;
                a1 = 1.0f;
                r2 = 0.5f;
                g2 = 1.0f;
                b2 = 0.0f;
                a2 = 0.0f;
                break;
        }
    }
}
