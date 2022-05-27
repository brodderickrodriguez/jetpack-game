package main.java.jetpackgame;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;


interface KeySetManager extends KeyListener {
    HashSet<Integer> currentKeys = new HashSet<>();

    @Override
    default void keyTyped(KeyEvent event) { }

    @Override
    default void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
            Game.getCurrentGame().playPause();
        else
            this.currentKeys.add(event.getKeyCode());
    }

    @Override
    default void keyReleased(KeyEvent event) {
        this.currentKeys.remove(event.getKeyCode());
    }
}

public abstract class Window extends JFrame implements KeySetManager, ActionListener {
    protected Timer timer;

    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("jetpack-game");
        this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    abstract void init();

}
