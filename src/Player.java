import java.awt.*;


class Player extends Actor {
    Player(int x, int y) {
        super(x, y, Color.blue);
        this.addExtremity(new JetPack(this));
    }
}

