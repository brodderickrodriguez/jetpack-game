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

    public static final int WORLD_WIDTH = (int)(WINDOW_WIDTH * 3.5);
    public static final int WORLD_HEIGHT = (int)(WINDOW_HEIGHT * 5.5);

    public static final int BORDER_DIM = 210;


    public static final int MAX_BULLET_VEL = 10;

    public static final double PARTICLE_ALPHA_DELAY = 3.0;
    public static final double PARTICLE_MAX_VEL = 4.0;
    public static final double PARTICLE_MAX_SIZE = 10.0;


    public static final double PAN_WORLD_FRAC = 0.2;


    public static final double JETPACK_IDLE = 5e-2;
    public static final double JETPACK_FUEL_USAGE = 1e-1;

    public static final Color PLAYER_COLOR = Color.blue;
    public static final Color ENEMY_COLOR = Color.black;

    public static final int PLAYER_BULLET_DELAY = 200;
    public static final int ENEMY_BULLET_DELAY = 800;
    public static final int ENEMY_VISION_RADIUS = 350;
    public static final int ENEMY_MIN_SEPARATION = 100;
    public static final int ENEMY_GIVE_UP_AFTER = 1000;

    public static final double ACTOR_BULLET_RECOIL = 0.2;
    public static final double ACTOR_MAX_ABS_ROTATION = 0.15;

    public static final double JETPACK_MAX_FUEL = 100;

    public static final int BULLET_POWER = 3;



}
