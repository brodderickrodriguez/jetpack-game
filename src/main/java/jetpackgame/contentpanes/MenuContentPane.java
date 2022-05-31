package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.*;
import main.java.jetpackgame.sprites.*;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public abstract class MenuContentPane extends ContentPane implements ActionListener {
    private MenuView menu = new MenuView();
    private Player player = new Player(50, Const.WINDOW_HEIGHT / 2);
    private GasCan gasCan;
    private Timer timer;

    MenuContentPane() {
        this.timer = new Timer(Const.KEY_DELAY, this);
        this.gasCan = this.getNewGasCan();
    }

    @Override
    public void dispose() {
        this.timer.stop();
        this.timer = null;
        this.player = null;
        this.gasCan = null;
        super.dispose();
    }

    private GasCan getNewGasCan() {
        Random r = new Random();
        return new GasCan(r.nextInt(Const.WINDOW_WIDTH), r.nextInt(Const.WINDOW_HEIGHT));
    }

    @Override
    public void init() {
        this.add(this.menu, 0);

        this.menu.init();
        this.timer.start();
    }

    public MenuView getMenu() {
        return this.menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.player.updateSpriteVel();
        this.player.fireBullet();
        SpriteManager.update();

        if (Math.abs(this.player.getCenter().x - this.gasCan.getCenter().x) > 10) {
            int direction = this.player.getDirectionTo(this.gasCan);
            this.player.headInDirection(direction);
        }

        if (this.gasCan.getCenter().y < this.player.getCenter().y) {
            this.player.goUp();
        }

        if (this.player.intersects(this.gasCan)) {
            this.remove(this.gasCan);
            this.gasCan = this.getNewGasCan();
            this.player.refillFuel();
            this.repaint();
        }

        this.setComponentZOrder(this.menu, 0);
    }
}
