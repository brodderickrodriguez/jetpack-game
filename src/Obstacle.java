import java.awt.*;

public class Obstacle extends Sprite {
    Obstacle(Rectangle bounds) {
        super(bounds);
    }


    @Override
    public void intersectedWith(Sprite other) {
        Rectangle intersection = this.getBounds().intersection(other.getBounds());

        if (this.intersectedBelow(other)) {
            other.vel[1] = 0;
            other.moveSprite(0, intersection.height);
        }
        if (this.intersectedAbove(other)) {
            other.vel[1] = 0;
            other.moveSprite(0, -intersection.height);
        }
        if (this.intersectedLeft(other)) {
            other.vel[0] = 0;
            other.moveSprite(-intersection.width, 0);
        }
        if (this.intersectedRight(other)) {
            other.vel[0] = 0;
            other.moveSprite(intersection.width, 0);
        }
    }

}
