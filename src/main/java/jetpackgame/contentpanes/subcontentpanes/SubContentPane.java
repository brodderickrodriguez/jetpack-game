package main.java.jetpackgame.contentpanes.subcontentpanes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SubContentPane extends JLabel implements ActionListener {
    public abstract void init();
    public void update() { }

    @Override
    public void actionPerformed(ActionEvent e) { }
}
