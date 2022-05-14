import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;

enum Levels {
    LEVEL_0;
}

public class GameLevelFactory {
    static final Map<Levels, Callable<Player>> levelMap = Map.of(
            Levels.LEVEL_0, GameLevelFactory::buildLevel0
        );

    static Player buildLevel(Levels level) throws Exception {
        Callable<Player> builder = GameLevelFactory.levelMap.get(level);
        return builder.call();
    }

    private static Player buildLevel0() {
        GameLevel level = new GameLevel(Const.WORLD_WIDTH, Const.WORLD_HEIGHT);


        Player player = new Player(100, 00);


        new Enemy(200, 100);
        new Platform(0, 150);



        return player;
    }
}
