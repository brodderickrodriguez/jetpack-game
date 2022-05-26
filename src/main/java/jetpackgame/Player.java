package main.java.jetpackgame;


class Player extends Actor {
    private final JetPack jetpack;
    Player(int x, int y) {
        super(x, y, Const.PLAYER_COLOR);
        this.bulletDelay = Const.PLAYER_BULLET_DELAY;
        this.jetpack = new JetPack(this);
        this.addExtremity(this.jetpack);
    }

    void goUp() {
        this.jetpack.goUp();
    }

    void fireBullet() {
        this.fireBullet(Const.PLAYER_COLOR);
    }

    void refillFuel() {
        this.jetpack.refillFuel();
    }

    void moveLeft() {
        this.modifyVelX(-Const.PLAYER_HORIZONTAL_ACC);
    }

    void moveRight() {
        this.modifyVelX(Const.PLAYER_HORIZONTAL_ACC);
    }

}

