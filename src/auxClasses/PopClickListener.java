/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import Main.Main;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Soraya
 */
public class PopClickListener extends MouseAdapter {
    Object panel;
    JTable table;
    boolean firstEntry = true;
    public PopClickListener(Object panel, JTable table){
        this.panel=panel;
        this.table=table;
    }
    @Override
    public void mousePressed(MouseEvent e){       
        if (e.isPopupTrigger())
            doPop(e);        
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);       
    }

    private void doPop(MouseEvent e){
        int row = table.rowAtPoint(e.getPoint());
        table.setRowSelectionInterval(row, row);
        PopUp menu = new PopUp(panel, e);
    }   
}
