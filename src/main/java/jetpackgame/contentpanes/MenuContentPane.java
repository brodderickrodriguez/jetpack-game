package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.Const;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;
import java.awt.event.*;
import javax.swing.Timer;

public abstract class MenuContentPane extends ContentPane implements ActionListener {
    private final MenuView menu = new MenuView();
//    private final Player player = new Player(Const.WINDOW_WIDTH / 2, Const.WINDOW_HEIGHT / 2);
    private final Timer timer;

    MenuContentPane() {
        this.timer = new Timer(Const.KEY_DELAY, this);
    }

    @Override
    public void init() {
//        this.add(this.player);

        this.add(this.menu, 0);

        this.menu.init();

    }

    public MenuView getMenu() {
        return this.menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        this.player.refillFuel();
//        this.player.update();
    }
}