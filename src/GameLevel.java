import java.util.ArrayList;

public class GameLevel {
    private final int worldWidth;
    private final int worldHeight;

    private final ArrayList<Sprite> sprites;

    GameLevel(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.sprites = new ArrayList<>();
    }

    void addPlatform(int x, int y) {
        Platform platform = new Platform(x, y);
        this.sprites.add(platform);
    }

    void addEnemy(int x, int y, int skill) {

    }

    void addFuelTank(int x, int y) {

    }

    void addGoal(int x, int y) {

    }

    void addPrincess(int x, int y) {

    }

    ArrayList<Sprite> getSprites() {
        return this.sprites;
    }

}
