/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cuentaluz.utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.color.ColorSpace;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ALEX
 */
public class EstadoRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        if (value.toString().equals("1")) {
            label.setBackground(Color.GREEN);
        }else{
            label.setBackground(Color.RED);
        }

        return label;
    }

}
