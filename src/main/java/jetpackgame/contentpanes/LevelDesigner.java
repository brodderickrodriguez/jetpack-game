package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.Const;
import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.SpriteManager;
import main.java.jetpackgame.sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Optional;


enum ModState {
    ADD_PLATFORM,
    ADD_PLAYER,
    ADD_PORTAL,
    ADD_ENEMY,
    ADD_GAS_CAN;

    static ModState getModStateFromKeyCode(int keyCode) {
        ModState state;

        switch (keyCode) {
            case KeyEvent.VK_P -> state = ModState.ADD_PLATFORM;
            case KeyEvent.VK_M -> state = ModState.ADD_PLAYER;
            case KeyEvent.VK_O -> state = ModState.ADD_PORTAL;
            case KeyEvent.VK_E -> state = ModState.ADD_ENEMY;
            case KeyEvent.VK_G -> state = ModState.ADD_GAS_CAN;
            default -> state = null;
        }

        return state;
    }
}

public class LevelDesigner extends ContentPane implements MouseListener, MouseMotionListener, ActionListener {
    private Timer timer;
    private ModState modState = ModState.ADD_PLATFORM;
    private Sprite currentSprite = null;
    private double zoomDelta;

    @Override
    public void init() {
        WorldBorder.makeBorders();
        SpriteManager.update();

        this.zoomDelta = this.getZoomOutDelta();
        this.zoomOut();
        this.centerContents();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.timer = new Timer(Const.KEY_DELAY, this);
        this.timer.start();
//        ContentController.getCurrentController().setKeyPressedCallBack(this::keyEvent);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        HashSet<Integer> keys = ContentController.getCurrentController().getCurrentKeys();

        if (keys.size() == 1) {
            int key = keys.iterator().next();
            ModState newModState = ModState.getModStateFromKeyCode(key);

            if (newModState != this.modState) {
                this.modState = ModState.getModStateFromKeyCode(key);
                System.out.println("setting mod state to: " + this.modState);
            }
        }
    }

    @Override
    public void escKeyPressed() {
        ContentController.setCurrentContentPaneStatic(new MainMenuContentPane());
    }

    @Override
    public void enterKeyPressed() {
        System.out.println("pressed enter");
    }

    @Override
    public void dispose() {

    }

    double getZoomOutDelta() {
        double denom = (double) Const.WORLD_HEIGHT + (2 * Const.BORDER_DIM);
        return (double)Const.WINDOW_HEIGHT / denom;
    }

    void updateComponentSize(Component c) {
        Rectangle bounds = c.getBounds();
        bounds.x *= this.zoomDelta;
        bounds.y *= this.zoomDelta;
        bounds.width *= this.zoomDelta;
        bounds.height *= this.zoomDelta;
        c.setBounds(bounds);
    }

    void zoomOut() {
        for (Sprite sprite: SpriteManager.getEverySprite()) {
            this.updateComponentSize(sprite);

            for (Component c: sprite.getComponents()) {
                this.updateComponentSize(c);
            }

        }
    }

    private void toStd() {
        System.out.println("private static Player buildLevel--() {");
        for (Component c: this.getComponents()) {
            if (c instanceof Sprite) {
                System.out.println("\t" + ((Sprite)c).getInstantiationAsString(1 / this.zoomDelta));

            }
        }
        System.out.println("\treturn player;\n}");
    }

    Sprite getSpriteForCurrentModState(int x, int y) {
        Sprite result = null;
        switch (this.modState) {
            case ADD_PLATFORM -> result = new Platform(x, y);
            case ADD_PLAYER -> result = new Player(x, y);
//            case ADD_PORTAL -> result = new Portal(x, y, x, y);
            case ADD_ENEMY -> result = new Enemy(x, y);
            case ADD_GAS_CAN -> result = new GasCan(x, y);
        }
        return result;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (int)(e.getX() * (1 / this.zoomDelta));
        int y = (int)(e.getY() * (1 / this.zoomDelta));

        this.currentSprite = this.getSpriteForCurrentModState(x, y);
        this.currentSprite.repaint();

        if (this.currentSprite == null) {
            return;
        }

        this.currentSprite.setBackground(this.currentSprite.getMainColor());
        this.updateComponentSize(this.currentSprite);
        this.add(this.currentSprite);
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.currentSprite == null) {
            return;
        }

        this.currentSprite.setXY(e.getX(), e.getY());
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
