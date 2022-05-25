import java.awt.*;


class Player extends Actor {
    private final JetPack jetpack;
    Player(int x, int y) {
        super(x, y, Color.blue);

        this.jetpack = new JetPack(this);
        this.addExtremity(this.jetpack);
    }

    void goUp() {
        this.jetpack.goUp();
    }

    void fireBullet() {
        this.fireBullet(Color.blue);
    }
}

