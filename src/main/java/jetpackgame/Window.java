package main.java.jetpackgame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Window extends JFrame implements KeyListener {
    private static Window currentWindow = null;
    private final HashSet<Integer> currentKeys = new HashSet<>();
    private final PriorityQueue<ContentPane> contentPanes = new PriorityQueue<>();
    private ContentPane currentContentPane;

    Window() {
        Window.currentWindow = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("jetpack-game");
        this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    public static Window getCurrentWindow() {
        return Window.currentWindow;
    }

    public HashSet<Integer> getCurrentKeys() {
        return this.currentKeys;
    }

    public void queueContentPane(ContentPane contentPane) {
        this.contentPanes.add(contentPane);
    }

    public void disposeCurrentContentPane() {
        if (this.currentContentPane == null) {
            return;
        }

        SpriteManager.removeAllSprites();
        SpriteManager.update();

        this.contentPanes.poll();
        this.remove(this.currentContentPane);
        this.currentContentPane = null;

        this.repaint();
        this.startNextContentPane();
    }

    public void startNextContentPane() {
        if (this.contentPanes.size() == 0) {
            return;
        }

        this.currentContentPane = this.contentPanes.peek();
        this.add(this.currentContentPane);
        SpriteManager.setCurrentContentPane(this.currentContentPane);
        SpriteManager.update();
        this.currentContentPane.init();
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.getCurrentGame().playPause();
        } else {
            this.currentKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.currentKeys.remove(e.getKeyCode());
    }
}

