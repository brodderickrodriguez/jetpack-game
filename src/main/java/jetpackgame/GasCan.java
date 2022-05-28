package main.java.jetpackgame;
import javax.swing.*;
import java.awt.*;


public class GasCan extends Sprite {
    GasCan(int x, int y) {
        super(new Rectangle(x, y, 30, 36), Color.red);
        this.setBackground(Color.red);
    }


    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Player) {
            Game.getCurrentGame().getPlayer().refillFuel();
            SpriteManager.removeSprite(this);
        }

        if (other instanceof Bullet) {
            new ParticleCloud(this.getCenter().x, this.getCenter().y, Util.flameColors());
            SpriteManager.removeSprite(this);
        }

    }

}
