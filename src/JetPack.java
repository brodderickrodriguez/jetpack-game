import java.awt.*;

public class JetPack extends Sprite {
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
