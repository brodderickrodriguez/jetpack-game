package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;

public class SubView extends JLabel {
    SubView(Rectangle bounds) {
        this.setBounds(bounds);
        this.setBackground(Color.orange);
        this.setOpaque(true);
    }

    public void centerOnParentView() {
        Component parent = this.getParent();
        double diffX = (double)((parent.getX() + parent.getWidth()) - (this.getX() + this.getWidth()));
        double diffY = (double)((parent.getY() + parent.getHeight()) - (this.getY() + this.getHeight()));

        int x = (int)((diffX / 2) + parent.getX());
        int y = (int)((diffY / 2) + parent.getY());

        Rectangle thisBounds = this.getBounds();
        thisBounds.x = x;
        thisBounds.y = y;
        this.setBounds(thisBounds);
    }

    public void centerComponent(Component c) {
        Component parent = c.getParent();
        double diffX = (double)((parent.getX() + parent.getWidth()) - (c.getX() + c.getWidth()));
        double diffY = (double)((parent.getY() + parent.getHeight()) - (c.getY() + c.getHeight()));

        int x = (int)((diffX / 2) + parent.getX());
        int y = (int)((diffY / 2) + parent.getY());

        Rectangle thisBounds = c.getBounds();
        thisBounds.x = x;
        thisBounds.y = y;
        this.setBounds(thisBounds);
    }


    private JLabel makeLabel(String text, int y, int fontSize) {
        JLabel label = new JLabel();
        label.setBounds(new Rectangle(0, y, this.getWidth(), 70));
        label.setText(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font("default", Font.BOLD, fontSize));
        return label;
    }


    public void centerChildBetweenPoints(Component child, int minX, int minY, int maxX, int maxY) {
        int x = ((maxX - minX) / 2) + minX;
        int y = ((maxY - minY) / 2) + minY;

        Rectangle thisBounds = child.getBounds();
        thisBounds.x = x;
        thisBounds.y = y;
        child.setBounds(thisBounds);
    }

}
