package main.java.jetpackgame.sprites;

import main.java.jetpackgame.*;

public class Player extends Actor {
    private final JetPack jetpack;
    public Player(int x, int y) {
        super(x, y, Const.PLAYER_COLOR);
        this.setBulletDelay(Const.PLAYER_BULLET_DELAY);
        this.jetpack = new JetPack(this);
        this.addExtremity(this.jetpack);
    }

    @Override
    public String getInstantiationAsString(double delta) {
        String r = super.getInstantiationAsString(delta);
        return "Player player = " + r;
    }

    @Override
    public void update() {
        super.update();
    }


    public void goUp() {
        this.jetpack.goUp();
    }

    public void fireBullet() {
        this.fireBullet(Const.PLAYER_COLOR);
    }

    public void refillFuel() {
        this.jetpack.refillFuel();
    }

    public void goLeft() {
        this.modifyVelX(-Const.PLAYER_HORIZONTAL_ACC);
    }

    public void goRight() {
        this.modifyVelX(Const.PLAYER_HORIZONTAL_ACC);
    }

}

