package com.mycompany.cuentaluz.utilidades;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BordeRedondeado extends JLabel {
    private int radioEsquinas;

    public BordeRedondeado(String texto, int radioEsquinas) {
        super(texto);
        this.radioEsquinas = radioEsquinas;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Redondear las esquinas del JLabel
        RoundRectangle2D.Double shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radioEsquinas, radioEsquinas);
        g2d.setClip(shape);

        super.paintComponent(g2d);
        g2d.dispose();
    }

    
    
}