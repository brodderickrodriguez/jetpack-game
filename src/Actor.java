import java.awt.*;

public class Actor extends Sprite {

    Actor(Rectangle bounds) {
        super(bounds);
    }

    @Override
    void update() {
        super.update();

        this.vel[1] += Const.GRAVITY;

        if (this.vel[0] > 0) {
            this.vel[0] -= Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }
        if (this.vel[0] < 0) {
            this.vel[0] += Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }

    }
}
