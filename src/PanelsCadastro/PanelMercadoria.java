/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PanelsCadastro;

import Main.Main;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author Soraya
 */
public class PanelMercadoria extends javax.swing.JPanel {
    Main lojaDB;
    public PanelMercadoria(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
        lojaDB.setComboBox("TipoMercadoria", "Descricao_TipoMercadoria", tipoMercadoriaBox);
    }
    public void insertMercadoria(){
        if(isValidEntry()==false)
            return;
        System.out.println("Entrada válida");
        String n=barCodeField.getText();
        String m=tipoMercadoriaBox.getSelectedItem().toString();
        String e=estimativaBox.getSelectedItem().toString();
        String d=descField.getText();
        String tam=tamField.getText();
        String preco=precoField.getText();
        preco=preco.replace(",", ".");
        String obs=obsField.getText();
        String status=statusBox.getSelectedItem().toString();
        
        String query = "INSERT INTO Mercadoria(ID_Mercadoria, Descricao_Mercadoria, Observacao, TipoMercadoria, Tamanho, Tamanho_est, Status, Preco_Merc)"+
        " VALUES (\'"+n+"\',\'"+d+"\', \'"+obs+"\', \'"+m+"\', \'"+tam+"\', \'"+e+"\',\'"+status+"\',"+preco+")";
        try{
            lojaDB.executeQuery(query);  
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);  
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao tentar adicionar ao banco de dados, há algo inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);  
            ex.printStackTrace();
        }
        
        limparCampos();   
                           
    }
    void limparCampos(){
        descField.setText("");
        tamField.setText("");
        precoField.setText("");
        obsField.setText("");
        barCodeField.setText("");
        tipoMercadoriaBox.setSelectedIndex(0);
        estimativaBox.setSelectedIndex(0);                
    }
    boolean isValidEntry(){
        String n=barCodeField.getText();
        if(n.equals("")) return false;
        if(Main.isIntegerValid(n)==false){
            JOptionPane.showMessageDialog(null, "O código deve ser um número", "Aviso", JOptionPane.WARNING_MESSAGE);                                     
            return false;
        }
        boolean hasEntry=false;
        try{
            ResultSet results = lojaDB.executeQuery("Select * from Mercadoria where ID_Mercadoria = "+n); 
            if(results.next()){
                JOptionPane.showMessageDialog(null, "Esse código de barras já foi cadastrado", "Aviso", JOptionPane.WARNING_MESSAGE);                     
                hasEntry=true;
            }
        }catch(Exception e){}
        if(hasEntry)
            return false;       
        String d=descField.getText();
        if(d.equals("")){
            JOptionPane.showMessageDialog(null, "A descrição não pode estar vazia", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }        
        int m = tipoMercadoriaBox.getSelectedIndex();
        String e=estimativaBox.getSelectedItem().toString();
        if(m==0){
            JOptionPane.showMessageDialog(null, "Escolha o tipo de mercadoria", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String tam=tamField.getText();
        if(tam.length()>3){
            JOptionPane.showMessageDialog(null, "O tamanho pode ser somente até um número de 3 dígitos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try{
            Integer.parseInt(tam);
        }
        catch(Exception ex){
            if(tam.equals("")==false){
                JOptionPane.showMessageDialog(null, "O tamanho não pode conter letras", "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
                
        }        
        String preco=precoField.getText();
        preco=preco.replace(",", ".");
        try{
            Double.parseDouble(preco);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Preencha o preço corretamente", "Aviso", JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
            return false;
        }
        return true;        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        precoField = new javax.swing.JTextField();
        obsField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tamField = new javax.swing.JTextField();
        tipoMercadoriaBox = new javax.swing.JComboBox<>();
        estimativaBox = new javax.swing.JComboBox<>();
        statusBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        descField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        barCodeField = new javax.swing.JTextField();

        jLabel3.setText("Preço*: ");

        jLabel4.setText("Observação: ");

        jLabel5.setText("Status: ");

        jLabel7.setText("Estimativa tamanho: ");

        jLabel1.setText("Tipo Mercadoria*: ");
        jLabel1.setToolTipText("Obrigatório selecionar (Selecione com cuidado)");

        precoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoFieldActionPerformed(evt);
            }
        });

        obsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                obsFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Tamanho: ");
        jLabel2.setToolTipText("Deve ser um número de até 3 dígitos");

        tamField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamFieldActionPerformed(evt);
            }
        });

        tipoMercadoriaBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        estimativaBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "PPP", "PP", "P", "M", "G", "GG", "GGG" }));

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "no estoque", "encomendado" }));

        jLabel6.setText("Descricao*: ");
        jLabel6.setToolTipText("Campo mais importante");

        jLabel8.setText("Código de barras*: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(precoField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tamField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estimativaBox, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(obsField)
                        .addComponent(tipoMercadoriaBox, 0, 200, Short.MAX_VALUE)
                        .addComponent(statusBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(barCodeField)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(barCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tipoMercadoriaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(descField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(precoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(estimativaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tamField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(obsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void precoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precoFieldActionPerformed

    private void obsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_obsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obsFieldActionPerformed

    private void tamFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tamFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tamFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barCodeField;
    private javax.swing.JTextField descField;
    private javax.swing.JComboBox<String> estimativaBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField obsField;
    private javax.swing.JTextField precoField;
    private javax.swing.JComboBox<String> statusBox;
    private javax.swing.JTextField tamField;
    private javax.swing.JComboBox<String> tipoMercadoriaBox;
    // End of variables declaration//GEN-END:variables
}
