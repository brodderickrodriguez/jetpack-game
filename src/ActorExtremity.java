import javax.swing.*;
import java.awt.*;

class ActorExtremity extends JLabel {
    Actor actor;
    ActorExtremity(Actor actor) {
        this.actor = actor;
        this.setOpaque(true);
    }

    void update() { }
}


class ActorBody extends ActorExtremity {
    ActorBody(Actor actor, Color color) {
        super(actor);
        this.setBackground(color);
        this.setBounds(new Rectangle(9, 24, 24, 50));
    }
}

class LegSeperator extends ActorExtremity {
    LegSeperator(Actor actor) {
        super(actor);
        this.setBounds(new Rectangle(20, 54, 2, 20));
        this.setBackground(Color.white);
    }
}


class LifeIndicator extends ActorExtremity {
    private final Actor actor;
    private final Color color;

    LifeIndicator(Actor actor, Color color) {
        super(actor);
        this.setBounds(new Rectangle(9, 0, 24, 24));
        this.setBackground(null);
        this.color = color;
        this.actor = actor;
    }

    @Override
    void update() {
        this.paintComponent(this.getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.actor.life <= 0)
            return;

        double angle = (1 - ((double)this.actor.life / (double)Const.MAX_ACTOR_LIFE)) * 360;

        g.setColor(this.color);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 0, 360);

        g.setColor(Color.gray);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 90, -(int)angle);
    }
}


class JetPack extends ActorExtremity {
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

        int pcX = this.actor.getX() + this.getX();
        int pcY = this.actor.getY() + this.getY() + this.getHeight() - 2;

        this.setBounds(jetPackBounds);
        new ParticleCloud(pcX, pcY, color);
    }
}
