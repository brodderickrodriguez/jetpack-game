package main.java.jetpackgame;
import javax.swing.*;
import java.awt.*;


class PortalEntrance extends Sprite {
    private final int CHECKER_SIZE = 20;
    private final Portal parent;
    boolean canTransport = true;

    PortalEntrance(int x, int y, Portal parent, Color colorA, Color colorB) {
        super(new Rectangle(x, y, 80, 120));
        this.parent = parent;
        this.setOpaque(true);
        this.setBackground(colorA);
        int colorCounter = 0;
        Color[] colors = new Color[]{colorA, colorB};

        for (int j = 0; j < this.getWidth(); j += this.CHECKER_SIZE) {
            Util.WrapAroundGenerator<Color> colorAlt = new Util.WrapAroundGenerator<>(colors, colorCounter++);

            for (int i = 0; i < this.getHeight(); i += this.CHECKER_SIZE) {
                JLabel checker = this.getNewChecker(j, i, colorAlt.getNext());
                this.add(checker, 0);
            }
        }
    }

    JLabel getNewChecker(int x, int y, Color color) {
        JLabel checker = new JLabel();
        checker.setOpaque(true);
        checker.setBounds(x, y, this.CHECKER_SIZE, this.CHECKER_SIZE);
        checker.setBackground(color);
        return checker;
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (!(other instanceof Player)) {
            return;
        }

        if (this.otherSpriteConfinedWithinThisBounds(other)) {
            this.parent.playerCollidedWith(this);
            this.canTransport = false;
        } else {
            this.canTransport = true;
        }
    }
}

public class Portal {
    private final PortalEntrance portalA, portalB;

    Portal(int portalAX, int portalAY, int portalBX, int portalBY) {
        Color colorA = Util.randomColor();
        Color colorB = Util.randomColor();
        this.portalA = new PortalEntrance(portalAX, portalAY, this, colorA, colorB);
        this.portalB = new PortalEntrance(portalBX, portalBY, this, colorA, colorB);
    }

    void playerCollidedWith(PortalEntrance entrance) {
        if (!entrance.canTransport) {
            return;
        }

        PortalEntrance destination;

        if (entrance == portalA) {
            destination = portalB;
        } else {
            destination = portalA;
        }

        Player player = Game.getCurrentGame().getPlayer();
        Point destinationCenter = destination.getCenter();
        int x = destinationCenter.x - (player.getBounds().width / 2);
        int y = destinationCenter.y - (player.getBounds().height / 2);

        player.setXY(x, y);
        Game.getCurrentGame().centerPlayer();
        player.poofAnimation();
        destination.canTransport = false;
    }
}
