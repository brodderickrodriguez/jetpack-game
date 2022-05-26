package main.java.jetpackgame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Particle extends Sprite {
    int alpha = 255;
    int size;
    Color color;

    Particle(int x, int y, int size, double[] vel, Color color) {
        super(new Rectangle(x, y, size, size));
        this.setBackground(color);
        this.setVel(vel);
        this.size = size;
        this.color = color;
    }

    @Override
    void update() {
        super.update();
        Rectangle bounds = this.getBounds();

        if (this.alpha == 0 || bounds.width == 0) {
            SpriteManager.removeSprite(this);
            return;
        }

        if (this.alpha > 0) {
            this.alpha -= Math.min(Const.PARTICLE_ALPHA_DELAY, this.alpha);
        }
        if (bounds.width > 0) {
            bounds.width -= 1;
            bounds.height -= 1;
        }

        this.setBounds(bounds);
        this.setBackground(new Color(
                this.color.getRed(),
                this.color.getGreen(),
                this.color.getBlue(),
                this.alpha));
    }
}

public class ParticleCloud {
    ArrayList<Particle> particles = new ArrayList<>();
    ParticleCloud(int x, int y, Color[] colors) {
        Random rn = new Random();
        int halfParticleSize = (int)(Const.PARTICLE_MAX_SIZE / 2);

        for (int i = 0; i < 10; i++) {
            int size = rn.nextInt(halfParticleSize) + halfParticleSize;
            int colorIdx = rn.nextInt(colors.length);
            double[] vel = {
                    Util.randNorm() * Const.PARTICLE_MAX_VEL,
                    Util.randNorm() * Const.PARTICLE_MAX_VEL
            };
            this.particles.add(new Particle(x, y, size, vel, colors[colorIdx]));
        }
    }

    ParticleCloud(int x, int y, Color color) {
        this(x, y, new Color[]{color});
    }

}
