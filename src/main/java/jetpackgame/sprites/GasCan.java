package main.java.jetpackgame.sprites;

import main.java.jetpackgame.*;
import main.java.jetpackgame.contentpanes.Game;

import java.awt.*;


public class GasCan extends Sprite {
    public GasCan(int x, int y) {
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
