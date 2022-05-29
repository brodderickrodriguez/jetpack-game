package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;

public class PausedGameView extends SubView {
    PausedGameView() {
        super(new Rectangle(0, 0, 390, (int)(Const.WINDOW_HEIGHT / 1.25)));
        this.setBackground(Color.darkGray);

        MapView mapView = new MapView(500);
        mapView.update();
        this.add(mapView);

        Rectangle bounds = mapView.getBounds();
        bounds.x += 20;
        bounds.y += 100;
        mapView.setBounds(bounds);

        JLabel pausedLabel = this.makeLabel("paused", 0, 36);
        JLabel descLabel =  this.makeLabel("(esc. to resume; q to quit)", 36, 12);
        this.add(pausedLabel, 0);
        this.add(descLabel, 0);


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


}
