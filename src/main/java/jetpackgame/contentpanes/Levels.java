package main.java.jetpackgame.contentpanes;

public enum Levels {
    LEVEL_0(0),
    LEVEL_1(1);

    final int levelInt;
    Levels(int i) {
        this.levelInt = i;
    }

    public static Levels get(int i) {
        for (Levels level: Levels.values()) {
            if (level.levelInt == i) {
                return level;
            }
        }
        return null;
    }

}
