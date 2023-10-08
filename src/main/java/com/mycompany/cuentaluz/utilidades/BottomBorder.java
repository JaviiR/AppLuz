package com.mycompany.cuentaluz.utilidades;

import javax.swing.border.Border;

import java.awt.*;


public class BottomBorder implements Border {
    private int thickness;
    private Color color;

    public BottomBorder(int thickness, Color color) {
        this.thickness = thickness;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.fillRect(x, y + height - thickness, width, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, thickness, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}
