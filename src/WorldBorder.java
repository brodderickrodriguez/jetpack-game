import java.awt.*;


public class WorldBorder extends Sprite {
    WorldBorder(Rectangle bounds) {
        super(bounds);
        this.setBackground(Color.black);
    }

    public static WorldBorder[] makeBorders() {
        int leftLoc = Const.MAX_LEFT - Const.BORDER_DIM;
        WorldBorder left = new WorldBorder(new Rectangle(leftLoc, Const.MAX_TOP, Const.BORDER_DIM, Const.WORLD_HEIGHT));
        WorldBorder right = new WorldBorder(new Rectangle(Const.MAX_RIGHT, Const.MAX_TOP, Const.BORDER_DIM, Const.WORLD_HEIGHT));
        WorldBorder top = new WorldBorder(new Rectangle(leftLoc, Const.MAX_TOP - Const.BORDER_DIM, Const.WORLD_WIDTH + (Const.BORDER_DIM * 2), Const.BORDER_DIM));
        WorldBorder bottom = new WorldBorder(new Rectangle(leftLoc, Const.MAX_BOTTOM, Const.WORLD_WIDTH + (Const.BORDER_DIM * 2), Const.BORDER_DIM));

        System.out.println(leftLoc + ", " + Const.MAX_BOTTOM);

        return new WorldBorder[]{left, right, top, bottom};
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Sprite) {
            Rectangle intersection = this.getBounds().intersection(other.getBounds());

            if (this.intersectedBelow(other)) {
                other.vel[1] = 0;
                other.moveSprite(0, intersection.height);
            }

            if (this.intersectedAbove(other)) {
                other.vel[1] = 0;
                other.moveSprite(0, -intersection.height);
            }

            if (this.intersectedLeft(other)) {
                other.vel[0] = 0;
                other.moveSprite(-intersection.width, 0);
            }

            if (this.intersectedRight(other)) {
                other.vel[0] = 0;
                other.moveSprite(intersection.width, 0);
            }

        }

    }
}

