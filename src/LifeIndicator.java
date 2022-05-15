import java.awt.*;

public class LifeIndicator extends Sprite {
    private final Actor actor;

    LifeIndicator(Actor actor) {
        super(new Rectangle(actor.getX(), actor.getY() - actor.getWidth(), actor.getWidth(), actor.getWidth()));
        this.actor = actor;
        this.setStickyTo(this.actor, 0, -actor.getWidth());
        this.setBackground(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.actor.life <= 0)
            return;

        double angle = (1 - ((double)this.actor.life / (double)Const.MAX_ACTOR_LIFE)) * 360;

        g.setColor(Color.BLUE);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 0, 360);

        g.setColor(Color.gray);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 90, -(int)angle);
    }
}
