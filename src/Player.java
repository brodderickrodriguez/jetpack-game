import java.awt.*;
import java.awt.geom.AffineTransform;


class Player extends Actor {

    Player(int x, int y) {
        super(new Rectangle(x, y, 25, 50));

        this.bulletDelay = 200;
        new JetPack(this);

        new LifeIndicator(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double maxAbsTheta = 0.5;
        double theta = (this.vel[0] / Const.ACC_HORIZONTAL_MAX) * maxAbsTheta;

        AffineTransform transform = new AffineTransform();
        transform.rotate(theta);
//        ((Graphics2D) g).transform(transform);

        super.paintComponent(g);
    }

}


class JetPack extends Sprite {
    Player player;
    JetPack(Player player) {
        super(new Rectangle(0, 0, 10, 20));
        this.setBackground(Color.gray);
        this.player = player;
    }

    @Override
    void update() {
        super.update();
        Rectangle playerBounds = this.player.getBounds();
        Rectangle jetPackBounds = this.getBounds();

        int xLoc;

        if (this.player.direction == 1) {
            xLoc = playerBounds.x - jetPackBounds.width;
        }
        else {
            xLoc = playerBounds.x + playerBounds.width;
        }

        jetPackBounds.x = xLoc;
        jetPackBounds.y = playerBounds.y + (int)(playerBounds.width / 2.5);

        this.setBounds(jetPackBounds);

        Color color = new Color(226, 88, 34);
        new ParticleCloud(jetPackBounds.x + (jetPackBounds.width / 2) - 2, jetPackBounds.y + jetPackBounds.height, color);


    }
}
