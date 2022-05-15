import java.awt.*;


class Player extends Actor {

    JetPack jetPack;

    Player(int x, int y) {
        super(new Rectangle(x, y, 25, 50));

        this.jetPack = new JetPack(this);

    }



}