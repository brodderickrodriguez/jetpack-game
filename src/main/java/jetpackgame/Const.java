package main.java.jetpackgame;

import java.awt.*;

public class Const {
    public static final int KEY_DELAY = 20;
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    public static final int PLAYER_HORIZONTAL_ACC = 2;
    public static final int PLAYER_VERTICAL_ACC = 2;
    public static final double PLAYER_HORIZONTAL_ACC_DECAY = 0.25;

    public static final double ACC_HORIZONTAL_MAX = 6;
    public static final double ACC_VERTICAL_MAX = 10;

    public static final double GRAVITY = 0.2;

    public static final int MAX_ACTOR_LIFE = 30;
    public static final int LIFE_INCREASE_DELAY = 1000;

    public static final int WORLD_WIDTH = (int)(WINDOW_WIDTH * 2);
    public static final int WORLD_HEIGHT = (int)(WINDOW_HEIGHT * 1.5);

    public static final int BORDER_DIM = 210;

    public static final int MAX_LEFT = -(Const.WORLD_WIDTH / 2) + (WINDOW_WIDTH / 2);
    public static final int MAX_RIGHT = (Const.WORLD_WIDTH / 2) + (WINDOW_WIDTH / 2);
    public static final int MAX_TOP = -(Const.WORLD_HEIGHT / 2) + (WINDOW_HEIGHT / 2);
    public static final int MAX_BOTTOM = (Const.WORLD_HEIGHT / 2) + (WINDOW_HEIGHT / 2);

    public static final int MAX_BULLET_VEL = 10;



    public static final double JETPACK_IDLE = 5e-2;
    public static final double JETPACK_FUEL_USAGE = 1e-1;

    public static final Color PLAYER_COLOR = Color.blue;
    public static final Color ENEMY_COLOR = Color.black;

    public static final int PLAYER_BULLET_DELAY = 200;
    public static final int ENEMY_BULLET_DELAY = 800;

    public static final double ACTOR_BULLET_RECOIL = 0.2;
    public static final double ACTOR_MAX_ABS_ROTATION = 0.15;

}
