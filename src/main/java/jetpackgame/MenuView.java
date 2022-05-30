package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.function.Function;


public class MenuView extends JLabel implements ActionListener {
    private static final String FONT_NAME = "default";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int TEXT_ALIGNMENT = SwingConstants.CENTER;
    private static final Color PRIMARY_COLOR = Color.darkGray;
    private static final Color SECONDARY_COLOR = Color.white;
    private static final int PERIMETER = 10;
    private final HashMap<JButton, Function<Void, Void>> buttonActionMap = new HashMap<>();

    MenuView() {
        super();
        this.setBounds(0, 0, 400, 0);
        this.setBackground(MenuView.PRIMARY_COLOR);
        this.setOpaque(true);
    }

    public void init() {
        this.adjustMenuComponentBounds();
        this.centerOnParentView();
    }

    private void adjustMenuComponentBounds() {
        int currentY = PERIMETER;

        for (Component c: this.getComponents()) {
            Rectangle cBounds = c.getBounds();
            cBounds.y = currentY;
            c.setBounds(cBounds);
            currentY += c.getHeight();
        }

        if (this.getParent() != null) {
            if (currentY > this.getParent().getHeight()) {
                System.out.println("warning - MenuView components wont fit in menu view.");
            }

            Rectangle thisBounds = this.getBounds();
            thisBounds.height = currentY + PERIMETER;
            this.setBounds(thisBounds);
        }
    }


    public void centerOnParentView() {
        if (this.getParent() == null) {
            return;
        }

        Component parent = this.getParent();
        double diffX = (parent.getX() + parent.getWidth()) - (this.getX() + this.getWidth());
        double diffY = (parent.getY() + parent.getHeight()) - (this.getY() + this.getHeight());

        int x = (int)((diffX / 2) + parent.getX());
        int y = (int)((diffY / 2) + parent.getY());

        Rectangle thisBounds = this.getBounds();
        thisBounds.x = x;
        thisBounds.y = y;
        this.setBounds(thisBounds);
    }

    public void addLabel(String text, int fontSize) {
        JLabel label = new JLabel();
        label.setBounds(new Rectangle(0, 0, this.getWidth(), fontSize));
        label.setText(text);
        label.setHorizontalAlignment(MenuView.TEXT_ALIGNMENT);
        label.setForeground(MenuView.SECONDARY_COLOR);
        label.setFont(new Font(MenuView.FONT_NAME, MenuView.FONT_STYLE, fontSize));
        this.add(label);
    }

    public void addButton(String text, int fontSize, Function<Void, Void> action) {
        JButton button = new JButton();
        button.setBounds(new Rectangle(PERIMETER, 50, this.getWidth() - (PERIMETER * 2), fontSize + 5));
        button.setText(text);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setForeground(MenuView.PRIMARY_COLOR);
        button.setFont(new Font(MenuView.FONT_NAME, MenuView.FONT_STYLE, fontSize));
        button.addActionListener(this);
        this.buttonActionMap.put(button, action);
        button.setFocusable(false);
        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();

            if (this.buttonActionMap.containsKey(source)) {
                Function<Void, Void> func = this.buttonActionMap.get(source);

                if (func != null) {
                    func.apply(null);
                }

                source.setFocusable(false);

            }
        }
    }
}
