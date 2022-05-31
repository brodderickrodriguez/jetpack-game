package main.java.jetpackgame;

import main.java.jetpackgame.contentpanes.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Function;

public class ContentController extends JFrame implements KeyListener {
    private static ContentController currentController = null;
    private final HashSet<Integer> currentKeys = new HashSet<>();
    private final PriorityQueue<ContentPane> contentPanes = new PriorityQueue<>();
    private ContentPane currentContentPane;
    private Function<Integer, Void> keyPressedCallBack = null;

    ContentController() {
        ContentController.currentController = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("jetpack-game");
        this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
    }

    public static ContentController getCurrentController() {
        return ContentController.currentController;
    }

    public HashSet<Integer> getCurrentKeys() {
        return this.currentKeys;
    }

    public void setKeyPressedCallBack(Function<Integer, Void> callable) {
        this.keyPressedCallBack = callable;
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

            if (this.keyPressedCallBack != null) {
                this.keyPressedCallBack.apply(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.currentKeys.remove(e.getKeyCode());
    }
}

