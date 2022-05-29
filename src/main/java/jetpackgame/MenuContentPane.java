package main.java.jetpackgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class MenuContentPane extends ContentPane implements ActionListener {
    private final MenuView menu = new MenuView();
    private final Player player = new Player(Const.WINDOW_WIDTH / 2, Const.WINDOW_HEIGHT / 2);
    private Timer timer = null;

    MenuContentPane() {
        this.timer = new Timer(Const.KEY_DELAY, this);
    }

    @Override
    public void init() {
        this.add(this.player);

//        this.add(this.menu, 0);
        this.menu.init();
//        this.repaint();

        this.timer.start();



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("action");
    }
}
