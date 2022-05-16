import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;

enum Levels {
    LEVEL_0;
}

public class GameLevelFactory {
    static final Map<Levels, Callable<Player>> levelMap = Map.of(
            Levels.LEVEL_0, GameLevelFactory::buildLevel0
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
        Player player = new Player(100, 0);

        new Enemy(200, 0);
        new Platform(0, 150);

        Random rn = new Random();

        for (int i = 0; i < 10; i++) {
            double x = rn.nextDouble() * Const.WORLD_WIDTH;
            double y = rn.nextDouble() * Const.WORLD_HEIGHT;
            new Enemy((int)x, (int)y);
        }

        return player;
    }
}
