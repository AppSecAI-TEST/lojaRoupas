/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import Panels.PanelConsulta;
import Panels.PanelDevolucao;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author Andre Simao
 */
public class AuxFieldCreditDevol implements DocumentListener {
    Object parentPanel;
    String tip="";
    public AuxFieldCreditDevol(Object parentPanel){
        this.parentPanel=parentPanel;
    }
    public AuxFieldCreditDevol(Object parentPanel, String tip){
        this.parentPanel=parentPanel;
        this.tip=tip;
    }
    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        doIt(documentEvent);
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        doIt(documentEvent);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        doIt(documentEvent);
    }

    private void doIt(DocumentEvent documentEvent) {          
        if(parentPanel.getClass()==ConfirmaVendaPanel.class)
            ((ConfirmaVendaPanel)parentPanel).updateFaltaPagarLabel();
        if(parentPanel.getClass()==PanelConsulta.class)
            ((PanelConsulta)parentPanel).search();
        if(parentPanel.getClass()==PanelDevolucao.class){
            PanelDevolucao panelDev = ((PanelDevolucao)parentPanel);
            panelDev.search();
            panelDev.pressButtonWithTip(tip);
        }
        
//        DocumentEvent.EventType type = documentEvent.getType();
//        String typeString = null;
//        if (type.equals(DocumentEvent.EventType.CHANGE)) {
//            typeString = "Change";
//        } else if (type.equals(DocumentEvent.EventType.INSERT)) {
//            typeString = "Insert";
//        } else if (type.equals(DocumentEvent.EventType.REMOVE)) {
//            typeString = "Remove";
//        }
//        System.out.print("Type : " + typeString);
//        Document source = documentEvent.getDocument();
//        int length = source.getLength();
//        System.out.println("Length: " + length);
    }

}
