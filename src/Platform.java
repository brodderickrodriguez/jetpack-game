import java.awt.*;

class Platform extends Sprite {
    Platform(int x, int y) {
        super(new Rectangle(x, y, 500, 40));
        this.setBackground(Color.gray);
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Player || other instanceof Enemy) {

            if (this.intersectedAbove(other)) {
                Rectangle intersection = this.getBounds().intersection(other.getBounds());
                other.vel[1] = 0;
                other.moveSprite(0, -intersection.height);
            }
        }
    }

    @Override
    void update() {
        super.update();

    }

}
