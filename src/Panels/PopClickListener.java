/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Soraya
 */
class PopClickListener extends MouseAdapter {
    PanelVenda panel;
    PopClickListener(PanelVenda panel){
        this.panel=panel;
    }
    @Override
    public void mousePressed(MouseEvent e){
        Robot bot;
        try {
            bot = new Robot();
            bot.mousePress(InputEvent.BUTTON1_MASK);
        } catch (AWTException ex) {
            
        }       
        if (e.isPopupTrigger())
            doPop(e);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUp menu = new PopUp(panel, e);
        menu.show(e.getComponent(), e.getX(), e.getY());        
    }   
}
