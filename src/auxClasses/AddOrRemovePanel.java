/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxClasses;

import Main.Main;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 *
 * @author Andre Simao
 */
public class AddOrRemovePanel extends javax.swing.JPanel {
    Main lojaDB;
    public AddOrRemovePanel(Main lojaDB, String label, String valField, String dField, String hField, boolean selected, int indexSelected) {
        this.lojaDB=lojaDB;
        initComponents();
        mainLabel.setText(label);
        mainField.setText(valField);
        dataField.setText(dField);
        horaField.setText(hField);
        clientBox.setVisible(false);
        clientLabel.setVisible(false);
        saldoClientLabel.setVisible(false);
        setPreferredSize(new Dimension(400,200));
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clientBox);
        clientBox.setToolTipText("Se não houver o cliente, cadastre um novo na aba \'Cadastro\' e tente novamente");
        clientBox.addActionListener (new ActionListener () { 
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSaldoCliente();                            
            }
        });
        if(selected){
            clientBox.setVisible(true);
            clientLabel.setVisible(true);
            saldoClientLabel.setVisible(true);
            clientBox.setSelectedIndex(indexSelected);
            recebimento_retirarSaldoCheckBox.setSelected(true);
        }
        if(dField==null)
            Main.setDateAndHour(dataField, horaField);
    }
    public void setToolTip(String text){
        recebimento_retirarSaldoCheckBox.setToolTipText(text);
    }
    public boolean isSaldoUtilizadoValido(String saldoUtilizadoS, String option){
        String saldoDoClienteS = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+clientBox.getSelectedItem()+"\'", "Saldo_Cliente");
        double saldoDoCliente = Main.formatDoubleString(saldoDoClienteS);
        double saldoUtilizado = Main.formatDoubleString(saldoUtilizadoS);
        
        if(saldoDoCliente<0 && option.equals("retirar")){
            JOptionPane.showMessageDialog(null, "O cliente está DEVENDO "+
                    saldoDoClienteS+" \nPortanto, aperte o botão de \'Adicionar ao caixa\' para pagamento!", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }            
        if(saldoDoCliente>0 && option.equals("adicionar")){
            JOptionPane.showMessageDialog(null, "O cliente está com "+saldoDoClienteS+" de crédito para compras futuras "
                    +" \nPortanto, aperte o botão de \'Retirar do caixa\' para retirada desses créditos!", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }
        if(saldoDoCliente>0 && option.equals("retirar")){
            if(saldoUtilizado>saldoDoCliente){
                JOptionPane.showMessageDialog(null, "O cliente possui apenas "+saldoDoClienteS+" de créditos por devoluções", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }   
        }                      
        if(option.equals("retirar") && saldoDoCliente<saldoUtilizado){
            JOptionPane.showMessageDialog(null, "O cliente não possui essa quantia de crédito", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }
            
        return true;
    }
    
    public void updateSaldoCliente(){
        saldoClientLabel.setText("saldo: -");
        try{
            String creditoPorCliente = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+clientBox.getSelectedItem()+"\'", "Saldo_Cliente");
            if(Main.formatDoubleString(creditoPorCliente)!=0)
                saldoClientLabel.setText("saldo: "+creditoPorCliente);
        }catch(Exception ex){}   
    }         
    public String getData(){
        return dataField.getText();
    }
    public String getValue(){
        return mainField.getText();
    }
    public String getHora(){
        return horaField.getText();
    }
    public void setTextOfRecebimento_retirarLabel(String newLabel){
        recebimento_retirarSaldoCheckBox.setText(newLabel);
    }
    public boolean isClientCheckBoxSelected(){
        return recebimento_retirarSaldoCheckBox.isSelected();
    }
    public int getSelectedIndex(){
        return clientBox.getSelectedIndex();
    }
    public String getClient(){
        return Main.getChoosedComboBox(clientBox);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        dataField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        horaField = new javax.swing.JTextField();
        mainField = new javax.swing.JTextField();
        mainLabel = new javax.swing.JLabel();
        recebimento_retirarSaldoCheckBox = new javax.swing.JCheckBox();
        clientLabel = new javax.swing.JLabel();
        clientBox = new javax.swing.JComboBox<>();
        saldoClientLabel = new javax.swing.JLabel();

        jLabel1.setText("Data: ");

        jLabel2.setText("Hora: ");

        mainLabel.setText("Valor: ");

        recebimento_retirarSaldoCheckBox.setText("recebimento_retirarSaldo");
        recebimento_retirarSaldoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recebimento_retirarSaldoCheckBoxActionPerformed(evt);
            }
        });

        clientLabel.setText("Cliente: ");

        clientBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        saldoClientLabel.setText("saldo: -");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(mainLabel)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mainField, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .addComponent(horaField)
                            .addComponent(dataField)))
                    .addComponent(recebimento_retirarSaldoCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clientLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saldoClientLabel))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(horaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mainField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recebimento_retirarSaldoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientLabel)
                    .addComponent(clientBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saldoClientLabel)
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void recebimento_retirarSaldoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recebimento_retirarSaldoCheckBoxActionPerformed
        if(recebimento_retirarSaldoCheckBox.isSelected()){
            clientBox.setVisible(true);
            clientLabel.setVisible(true);
            saldoClientLabel.setVisible(true);
        }else{
            clientBox.setVisible(false);
            clientLabel.setVisible(false);
            saldoClientLabel.setVisible(false);            
        }
    }//GEN-LAST:event_recebimento_retirarSaldoCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> clientBox;
    private javax.swing.JLabel clientLabel;
    private javax.swing.JTextField dataField;
    private javax.swing.JTextField horaField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField mainField;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JCheckBox recebimento_retirarSaldoCheckBox;
    private javax.swing.JLabel saldoClientLabel;
    // End of variables declaration//GEN-END:variables
}
