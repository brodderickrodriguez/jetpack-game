package main.java.jetpackgame;
import main.java.jetpackgame.sprites.*;

import java.util.*;
import java.util.concurrent.Callable;

enum Levels {
    LEVEL_0,
    LEVEL_1;
}

public class GameLevelFactory {
    static final Map<Levels, Callable<Player>> levelMap = Map.of(
            Levels.LEVEL_0, GameLevelFactory::buildLevel0,
            Levels.LEVEL_1, GameLevelFactory::buildLevel1
        );

    static Player buildLevel(Levels level) {
        Callable<Player> builder = GameLevelFactory.levelMap.get(level);
        try {
            return builder.call();
        }
        catch (Exception e) {
            return null;
        }
    }

    private static Player buildLevel0() {
        Player player = new Player(100, 200);

        new Enemy(200, 200);
        new Enemy(250, 200);
        new Platform(0, 150);

        new GasCan(50, 100);


        new Portal(250, 30, 550, 880);

        Random rn = new Random();

//        for (int i = 0; i < 50; i++) {
//            double x = rn.nextDouble() * Const.WORLD_WIDTH;
//            double y = rn.nextDouble() * Const.WORLD_HEIGHT;
//            new Enemy((int)x, (int)y);
//        }

        return player;
    }

    private static Player buildLevel1() {

        new Platform(367, 379);
        new Platform(2476, 367);
        new Platform(2422, 2271);
        new Platform(2416, 3819);
        new Platform(325, 3789);
        new Platform(385, 2199);
        new Platform(1373, 843);
        new Platform(1367, 1482);
        new Platform(1343, 2777);
        new Platform(1319, 3241);
        new Platform(1295, 3976);
        new Platform(2397, 3006);
        new Platform(692, 2988);
        new Platform(1946, 1096);
        new Platform(976, 1030);
        new Enemy(2892, 259);
        new Enemy(2831, 265);
        new Enemy(2759, 271);
        new Enemy(2669, 271);
        new Enemy(2584, 271);
        new Enemy(319, 4488);
        new Enemy(439, 4500);
        new Enemy(572, 4488);
        new Enemy(704, 4500);
        new Enemy(873, 4512);
        new Enemy(2452, 4470);
        new Enemy(2584, 4464);
        new Enemy(2741, 4458);
        new Enemy(2879, 4470);
        new Enemy(2536, 3687);
        new Enemy(2729, 2162);
        new Enemy(2614, 2169);
        new Enemy(2506, 2169);
        new Enemy(500, 2102);
        new Enemy(759, 2102);
        new Enemy(927, 2892);
        new Enemy(524, 3687);
        new Enemy(524, 3687);
        new Enemy(1705, 3139);
        new Enemy(1596, 3139);
        new Enemy(1446, 3133);
        new Enemy(1542, 2644);
        new Enemy(2675, 2873);
        new Enemy(2608, 2879);
        new Enemy(747, 289);
        new Enemy(674, 289);
        new Enemy(548, 289);
        new Enemy(1584, 729);
        new Enemy(2144, 1000);
        new Enemy(1644, 1367);
        new Enemy(1156, 927);
        new Enemy(1735, 710);
        new Enemy(1494, 729);
        new GasCan(259, 4488);
        new GasCan(500, 4470);
        new GasCan(2675, 4452);
        new GasCan(3042, 4482);
        new GasCan(1711, 3868);
        new GasCan(1349, 3862);
        new GasCan(2090, 3470);
        new GasCan(2126, 2313);
        new GasCan(2138, 1674);
        new GasCan(1066, 1295);
        new GasCan(1048, 2259);
        new GasCan(457, 1144);
        new GasCan(2879, 807);
        new GasCan(488, 3374);
        new GasCan(494, 2819);
        new GasCan(2922, 1891);
        new GasCan(2867, 2783);
        new GasCan(2512, 289);
        new GasCan(433, 295);
        new GasCan(2337, 1030);
        new Enemy(1626, 4169);
        Player player = new Player(2054, 4078);
//        new Portal(3108, 265, 3120, 4530);
        new Portal(250, 30, 2050, 4078);
        return player;
    }

}
