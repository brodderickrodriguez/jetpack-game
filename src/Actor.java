import java.awt.*;

public class Actor extends Sprite {

    Actor(Rectangle bounds) {
        super(bounds, true, true);
    }

    @Override
    void update() {
        super.update();

        this.vel[1] += Const.GRAVITY;

        if (this.vel[0] > 0) {
            this.vel[0] -= Const.PLAYER_HORIZONTAL_ACC_DECAY;
            this.direction = 1;
        }
        if (this.vel[0] < 0) {
            this.vel[0] += Const.PLAYER_HORIZONTAL_ACC_DECAY;
            this.direction = -1;
        }

        this.vel[0] = Util.clamp(this.vel[0], -Const.ACC_HORIZONTAL_MAX, Const.ACC_HORIZONTAL_MAX);
        this.vel[1] = Util.clamp(this.vel[1], -Const.ACC_VERTICAL_MAX, Const.ACC_VERTICAL_MAX);


    }
}
