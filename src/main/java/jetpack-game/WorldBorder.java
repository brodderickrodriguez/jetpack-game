import java.awt.*;


public class WorldBorder extends Obstacle {
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
        return new WorldBorder[]{left, right, top, bottom};
    }

}

