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
    private final JLabel fuelIndicator = new JLabel();
    private double fuelLevel = 100;

    JetPack(Player player) {
        super(player);
        this.setBackground(Color.gray);
        this.setBounds(new Rectangle(3, 30, 7, 20));
        this.addFuelIndicator();
    }

    void addFuelIndicator() {
        this.fuelIndicator.setBounds(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
        this.fuelIndicator.setBackground(Color.orange);
        this.fuelIndicator.setOpaque(true);
        this.add(this.fuelIndicator, 0);
    }

    void updateFuelIndicator() {
        this.fuelLevel -= Const.JETPACK_IDLE;

        if (this.fuelLevel <= 0) {
            return;
        }

        int maxHeight = this.getHeight();
        double fuelPercent = this.fuelLevel / 100.0;
        int newHeight = (int)(maxHeight * fuelPercent);

        Rectangle bounds = this.fuelIndicator.getBounds();
        bounds.height = newHeight;
        bounds.y = this.getHeight() - newHeight;
        this.fuelIndicator.setBounds(bounds);

        int colorRed, colorGreen = 0;

        if (fuelPercent > 0.50) {
            colorGreen = 255;
            colorRed = (int)((1 - fuelPercent) * 255.0) * 2;
        } else {
            colorRed = 255;
            colorGreen = (int)(fuelPercent * 255.0) * 2;
        }

        this.fuelIndicator.setBackground(new Color(colorRed, colorGreen, 0));
    }

    @Override
    void update() {
        super.update();
        this.updateFuelIndicator();
        Rectangle jetPackBounds = this.getBounds();

        Color[] colors = {
                new Color(255, 100, 52),
                new Color(238, 115, 52),
                new Color(238, 145, 55),
                new Color(245, 187, 72),
                new Color(249, 222, 100),
        };

        if (this.actor.getDirection() == 1)
            jetPackBounds.x = 3;
        else
            jetPackBounds.x = 32;

        int pcX = this.actor.getX() + this.getX();
        int pcY = this.actor.getY() + this.getY() + this.getHeight() - 2;

        this.setBounds(jetPackBounds);

        if (this.fuelLevel > 0) {
            new ParticleCloud(pcX, pcY, colors);
        }
    }

    void goUp() {
        if (this.fuelLevel > 0) {
            Game.getCurrentGame().getPlayer().modifyVelY(-Const.PLAYER_VERTICAL_ACC);
            this.fuelLevel -= Const.JETPACK_FUEL_USAGE;
        }

    }

}
