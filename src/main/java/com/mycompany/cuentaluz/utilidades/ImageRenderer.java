package com.mycompany.cuentaluz.utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer extends DefaultTableCellRenderer{
    private int width;
    private int height;
    public ImageRenderer(int width,int height){
        this.width=width;
        this.height=height;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        JLabel label=new JLabel();
        if(value instanceof ImageIcon){
            ImageIcon originalIcon=(ImageIcon)value;
            Image scaledImage=originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
            label.setHorizontalAlignment(JLabel.CENTER);
        }
        return label;
    }
    
    
    

    
    
    
    

    
    

    
    
    

    
    
    
    
}
