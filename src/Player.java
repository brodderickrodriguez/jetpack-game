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