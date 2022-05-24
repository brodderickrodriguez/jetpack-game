import java.awt.*;


class JetPack extends BodyPart {
    JetPack(Player player) {
        super(player);
        this.setBackground(Color.gray);
        this.setBounds(new Rectangle(3, 30, 7, 20));
    }

    @Override
    void update() {
        super.update();
        Rectangle jetPackBounds = this.getBounds();
        Color color = new Color(226, 88, 34);

        if (this.actor.getDirection() == 1)
            jetPackBounds.x = 3;
        else
            jetPackBounds.x = 32;

        int pcX = (int)(this.actor.getX() + this.getX());
        int pcY = (int)(this.actor.getY() + this.getY() + this.getHeight() - 2);

        this.setBounds(jetPackBounds);
        new ParticleCloud(pcX, pcY, color);
    }
}



class Player extends Actor {
    JetPack jetPack;

    Player(int x, int y) {
        super(x, y, Color.blue);
        this.jetPack = new JetPack(this);
        this.add(jetPack, 0);
    }

    @Override
    void update() {
        super.update();
        this.jetPack.update();
    }
}

