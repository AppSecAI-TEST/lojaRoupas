/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Soraya
 */
public class PopUp extends JPopupMenu {
    JMenuItem removeItem, discountItem;
    
    PanelVenda panel;
    public PopUp(PanelVenda panel, MouseEvent e){
        this.panel=panel;
        removeItem = new JMenuItem("Remover");
        discountItem = new JMenuItem("Desconto");
        add(removeItem);
        add(discountItem);
        removeItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panel.removeEvent(e);
            }
        });
        discountItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panel.discountEvent(e);
            }
        });
    }
}