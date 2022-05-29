package main.java.jetpackgame;

import java.awt.*;
import java.awt.event.*;


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

public class LevelDesigner extends ContentPane implements MouseListener, MouseMotionListener {
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
        Controller.getCurrentController().setKeyPressedCallBack(this::keyEvent);


    }

    double getZoomOutDelta() {
        double denom = (double)Const.WORLD_HEIGHT + (2 * Const.BORDER_DIM);
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

    public Void keyEvent(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            this.toStd();
            return null;
        }
        ModState newState = ModState.getModStateFromKeyCode(keyCode);

        if (newState != null) {
            this.modState = newState;
            System.out.println("setting mod state to: " + this.modState);
        }
        return null;
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
