/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxPanels;

import javax.swing.JFrame;

/**
 *
 * @author Andre Simao
 */
public class MyFrame extends JFrame{
    public void exitProcedure(){
        dispose();
        setEnabled(false);
    }
}
