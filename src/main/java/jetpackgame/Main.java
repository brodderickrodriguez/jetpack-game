package main.java.jetpackgame;


public class Main {
    public static void main(String[] args) throws Exception {
        Window window = new Window();
        window.queueContentPane(new PreviewLevel(Levels.LEVEL_0));
        window.queueContentPane(new Game(Levels.LEVEL_0));
        window.startNextContentPane();

    }
}
