package main.java.jetpackgame;

import main.java.jetpackgame.contentpanes.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class ContentController extends JFrame implements KeyListener {
    private static ContentController currentController = null;
    private final HashSet<Integer> currentKeys = new HashSet<>();
    private ContentPane currentContentPane;

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

    public static void setCurrentContentPaneStatic(ContentPane c) {
        ContentController.getCurrentController().setCurrentContentPane(c);
    }

    public void setCurrentContentPane(ContentPane c) {
        if (this.currentContentPane != null) {
            this.disposeCurrentContentPane();
        }

        this.currentContentPane = c;
        this.add(this.currentContentPane);
        SpriteManager.setCurrentContentPane(this.currentContentPane);
        SpriteManager.update();
        this.currentContentPane.init();
        this.repaint();
    }

    private void disposeCurrentContentPane() {
        this.currentContentPane.dispose();

        SpriteManager.removeAllSprites();
        SpriteManager.update();

        this.remove(this.currentContentPane);
        this.currentContentPane = null;

        this.repaint();
    }

    public HashSet<Integer> getCurrentKeys() {
        return this.currentKeys;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (this.currentContentPane != null) {
                this.currentContentPane.escKeyPressed();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.currentContentPane != null) {
                this.currentContentPane.enterKeyPressed();
            }
        } else {
            this.currentKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.currentKeys.remove(e.getKeyCode());
    }
}

