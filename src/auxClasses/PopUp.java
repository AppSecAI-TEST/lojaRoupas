/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import Panels.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Soraya
 */
public class PopUp extends JPopupMenu {
    JMenuItem removeItem, discountItem, verItem, updateItem;    
    Object panel;
    public PopUp(Object panel, MouseEvent e){      
        this.panel=panel;
        if(panel.getClass()==PanelVenda.class){
            vendaPop(panel, e);
        }            
        if(panel.getClass()==PanelCaixa.class){
            caixaPop(panel, e);
        } 
        if(panel.getClass()==PanelCadastro.class){
            updateCadastro(panel, e);
        } 
        if(panel.getClass()==PanelConsulta.class){
            updateConsulta(panel, e);
        }   
        if(panel.getClass()==PanelConferencia.class){
            updateConferencia(panel, e);
        }    
        if(panel.getClass()==PanelDevolucao.class){
            updateDevolucao(panel, e);
        } 
        if(panel.getClass()==PanelEstatistica.class){
            updateEstatistica(panel, e);
        }    
        show(e.getComponent(), e.getX(), e.getY());
    }
    public void vendaPop(Object panelObj, MouseEvent e){
        PanelVenda panel = (PanelVenda) panelObj;        
        verItem = new JMenuItem("Ver");
        removeItem = new JMenuItem("Remover");
        discountItem = new JMenuItem("Desconto");
        add(verItem);
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
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panel.verEvent(e);
            }
        });
    }
    public void caixaPop(Object panelObj, MouseEvent e){
        //show(e.getComponent(), e.getX(), e.getY());
        PanelCaixa panel = (PanelCaixa) panelObj;
        verItem = new JMenuItem("Ver transação");
        add(verItem);
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panel.showItem(e);
            }
        });
    }
    public void updateCadastro(Object panelObj, MouseEvent e){
        PanelCadastro panelCad = (PanelCadastro)panelObj;
        verItem = new JMenuItem("Ver");
        updateItem = new JMenuItem("Alterar");        
        add(verItem);       
        add(updateItem);
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(panelCad!=null)
                    panelCad.updateSQL(e);
            }
        });
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelCad.verEvent(e);
            }
        });
    }
    public void updateConferencia(Object panelObj, MouseEvent e){
        PanelConferencia panelConf = (PanelConferencia)panelObj;
        verItem = new JMenuItem("Ver");        
        updateItem = new JMenuItem("Alterar");
        add(verItem);
        add(updateItem);
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(panelConf!=null)
                    panelConf.updateSQL(e);
            }
        });
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelConf.verEvent(e);
            }
        });
    }
    public void updateDevolucao(Object panelObj, MouseEvent e){
        PanelDevolucao panelDevol = (PanelDevolucao)panelObj;
        verItem = new JMenuItem("Ver");       
        add(verItem);
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelDevol.verEvent(e);
            }
        });
    }
    public void updateEstatistica(Object panelObj, MouseEvent e){
        PanelEstatistica panelEstat = (PanelEstatistica)panelObj;
        verItem = new JMenuItem("Ver");       
        add(verItem);
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelEstat.verEvent(e);
            }
        });
    }
    
    public void updateConsulta(Object panelObj, MouseEvent e){
        PanelConsulta panelCons = (PanelConsulta)panelObj;
        verItem = new JMenuItem("Ver");        
        updateItem = new JMenuItem("Alterar");
        add(verItem);
        add(updateItem);
        updateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(panelCons!=null)
                    panelCons.updateSQL(e);
            }
        });
        verItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelCons.verEvent(e);
            }
        });
    }
}