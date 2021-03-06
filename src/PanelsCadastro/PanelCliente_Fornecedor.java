/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelsCadastro;

import Main.Main;
import javax.swing.JOptionPane;

/**
 *
 * @author Soraya
 */
public class PanelCliente_Fornecedor extends javax.swing.JPanel {

    /**
     * Creates new form PanelMercadoria
     */
    Main lojaDB;
    public PanelCliente_Fornecedor(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cel1Field = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cpfField = new javax.swing.JTextField();
        cel2Field = new javax.swing.JTextField();
        fixoField = new javax.swing.JTextField();
        endField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        descField = new javax.swing.JTextField();

        jLabel1.setText("Nome Completo*:");
        jLabel1.setToolTipText("Pelo menos um sobrenome");

        jLabel2.setText("Cel 1:");
        jLabel2.setToolTipText("O mais importante dos telefones");

        cel1Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cel1FieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Cel 2:");

        jLabel4.setText("Fixo: ");

        jLabel5.setText("Endereço:");

        jLabel6.setText("Email: ");

        jLabel7.setText("CPF: ");

        cel2Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cel2FieldActionPerformed(evt);
            }
        });

        fixoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixoFieldActionPerformed(evt);
            }
        });

        jLabel8.setText("Descrição:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(descField, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(cpfField)
                    .addComponent(cel1Field)
                    .addComponent(cel2Field)
                    .addComponent(fixoField)
                    .addComponent(endField)
                    .addComponent(emailField)
                    .addComponent(nomeField))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cpfField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cel1Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cel2Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fixoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(endField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(descField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cel1FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cel1FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cel1FieldActionPerformed

    private void cel2FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cel2FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cel2FieldActionPerformed

    private void fixoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fixoFieldActionPerformed
    public String insertCliente_Fornecedor(String nameTable){
        if(isValidEntry()==false)
            return null;
        String n=nomeField.getText();
        String cpf=cpfField.getText();
        if(Main.isEmpty(cpf))
            cpf="";
        String cel1=cel1Field.getText();
        String cel2=cel2Field.getText();
        String fixo=fixoField.getText();
        String end=endField.getText();
        String email=emailField.getText();
        String desc=descField.getText();
        Object hasClient = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", Main.stringToSql(n), "*");;
        if(hasClient != null){
            JOptionPane.showMessageDialog(null, "Esse nome já está registrado", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return null;
        }                        
        String query="";
        if(nameTable.equals("Cliente"))
            query = "INSERT INTO Cliente(Nome_Cliente, Telefone_Celular1, Telefone_Celular2, Telefone_Fixo, Endereco_Cliente, Email_Cliente, CPF_Cliente, Descricao_Cliente)"+
        " VALUES ("+Main.stringToSql(n)+", "+Main.stringToSql(cel1)+", "+Main.stringToSql(cel2)+", "+Main.stringToSql(fixo)+", "+Main.stringToSql(end)+","+
                    Main.stringToSql(email)+","+Main.stringToSql(cpf)+","+Main.stringToSql(desc)+")";
        if(nameTable.equals("Fornecedor"))
            query = "INSERT INTO Fornecedor(Nome_Fornecedor, Telefone_Celular1, Telefone_Celular2, Telefone_Fixo, Endereco_Fornecedor, Email_Fornecedor, CPF_Fornecedor, Descricao_Fornecedor)"+
        " VALUES ("+Main.stringToSql(n)+", "+Main.stringToSql(cel1)+", "+Main.stringToSql(cel2)+", "+Main.stringToSql(fixo)+", "+Main.stringToSql(end)+
                    ","+Main.stringToSql(email)+","+Main.stringToSql(cpf)+","+Main.stringToSql(desc)+")";
        try{
            lojaDB.executeQuery(query);
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro interno no banco de dados! Tente novamente com dados válidos!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        
        return n;
    }
    boolean isValidEntry(){
        String n=nomeField.getText();
        if(n.equals("")){
            JOptionPane.showMessageDialog(null, "O \'nome\' não pode estar vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
            return false;
        }
        if(n.split(" ").length<2){
            JOptionPane.showMessageDialog(null, "Deve cadastrar pelo menos um sobrenome", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
            return false;
        }
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cel1Field;
    private javax.swing.JTextField cel2Field;
    private javax.swing.JTextField cpfField;
    private javax.swing.JTextField descField;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField endField;
    private javax.swing.JTextField fixoField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField nomeField;
    // End of variables declaration//GEN-END:variables
}
