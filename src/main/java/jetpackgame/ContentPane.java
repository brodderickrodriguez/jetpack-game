package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;


abstract class ContentPane extends JComponent implements Comparable<ContentPane> {
    ContentPane() {
        this.setBounds(new Rectangle(0, 0, Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT));
        this.setOpaque(true);
    }

    abstract public void init();

    @Override
    public int compareTo(ContentPane o) {
        if (o == this) {
            return 1;
        }
        return 0;
    }

    public void dispose() {
        Controller.getCurrentWindow().disposeCurrentContentPane();
    }

}