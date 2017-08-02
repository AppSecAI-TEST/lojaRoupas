/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Main.Main;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Soraya
 */
public class PanelCaixa extends javax.swing.JPanel {

    /**
     * Creates new form PanelCaixa
     */
    JTable tableCaixa;
    Main lojaDB;
    public PanelCaixa(Main lojaDB) {
        this.lojaDB = lojaDB;
        initComponents();
        createTable(new String[]{"ID do Caixa","Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"}); 
        JScrollPane scrollTable=new JScrollPane(tableCaixa);
        tableCaixaPanel.removeAll();
        tableCaixaPanel.add(scrollTable); 
        update();
    }
    private void resetLabels(){
        abrirFecharCaixaButton.setText("Abrir caixa");
        dataAberturaField.setText("");
        horaAberturaField.setText("");
        adicionadoField.setText("");        
        dataAberturaField.setEditable(true);
        horaAberturaField.setEditable(true);
        adicionadoField.setEditable(true);
        retiradoLabel.setText("");    
        vendasDevLabel.setText("");
        idCaixaLabel.setText("");
    }
    private void setLabels(Double valueVendasDev){
        abrirFecharCaixaButton.setText("Fechar caixa");
        dataAberturaField.setText(lojaDB.caixaMap.get("Data_Abertura"));
        horaAberturaField.setText(lojaDB.caixaMap.get("Hora_Abertura"));
        adicionadoField.setText(lojaDB.caixaMap.get("Adicionado"));
        dataAberturaField.setEditable(false);
        horaAberturaField.setEditable(false);
        adicionadoField.setEditable(false);
        retiradoLabel.setText(lojaDB.caixaMap.get("Retirado"));  
        idCaixaLabel.setText(lojaDB.caixaMap.get("ID_Caixa")); 
        vendasDevLabel.setText(Main.twoDig(valueVendasDev));
        lojaDB.setDataHoraPanels();
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableCaixa = new JTable(model);        
        tableCaixaPanel.setLayout(new BoxLayout(tableCaixaPanel, BoxLayout.PAGE_AXIS));     
        tableCaixa.setDefaultEditor(Object.class, null);
    }
    public void setLabelsCaixa(){
      if(lojaDB.caixaAberto==false){
        resetLabels();        
        return;
      }
      Double valueVendasDev = calculateVendasDev();
      setLabels(valueVendasDev);
      
    }
    private Double calculateVendasDev() {
        //CODE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return 187.45;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        abrirFecharCaixaButton = new javax.swing.JButton();
        verCaixaAtualButton = new javax.swing.JButton();
        adicionarDinheiroButton = new javax.swing.JButton();
        retirarDinheiroButton = new javax.swing.JButton();
        tableCaixaPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        vendasDevLabel = new javax.swing.JLabel();
        retiradoLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        idCaixaLabel = new javax.swing.JLabel();
        dataAberturaField = new javax.swing.JTextField();
        horaAberturaField = new javax.swing.JTextField();
        adicionadoField = new javax.swing.JTextField();

        abrirFecharCaixaButton.setText("Abrir caixa");
        abrirFecharCaixaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirFecharCaixaButtonActionPerformed(evt);
            }
        });

        verCaixaAtualButton.setText("Ver caixa atual");

        adicionarDinheiroButton.setText("Adicionar ao caixa ");
        adicionarDinheiroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarDinheiroButtonActionPerformed(evt);
            }
        });

        retirarDinheiroButton.setText("Retirar do caixa");
        retirarDinheiroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retirarDinheiroButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tableCaixaPanelLayout = new javax.swing.GroupLayout(tableCaixaPanel);
        tableCaixaPanel.setLayout(tableCaixaPanelLayout);
        tableCaixaPanelLayout.setHorizontalGroup(
            tableCaixaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableCaixaPanelLayout.setVerticalGroup(
            tableCaixaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        jLabel1.setText("Vendas/Devoluções:  ");

        jLabel2.setText("Data Abertura: ");

        jLabel3.setText("Hora Abertura");

        jLabel5.setText("Adicionado ao caixa: ");

        jLabel6.setText("Retirado do caixa: ");

        vendasDevLabel.setText("0.00");

        retiradoLabel.setText("0.00");

        jLabel4.setText("Identificador do caixa: ");

        idCaixaLabel.setText("0");

        dataAberturaField.setText(" ");

        horaAberturaField.setText(" ");

        adicionadoField.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableCaixaPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dataAberturaField)
                            .addComponent(vendasDevLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(horaAberturaField))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(retiradoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idCaixaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(adicionadoField))
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abrirFecharCaixaButton)
                        .addGap(18, 18, 18)
                        .addComponent(verCaixaAtualButton)
                        .addGap(18, 18, 18)
                        .addComponent(adicionarDinheiroButton)
                        .addGap(18, 18, 18)
                        .addComponent(retirarDinheiroButton)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abrirFecharCaixaButton)
                    .addComponent(verCaixaAtualButton)
                    .addComponent(adicionarDinheiroButton)
                    .addComponent(retirarDinheiroButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(vendasDevLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(dataAberturaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(horaAberturaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(adicionadoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(retiradoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(idCaixaLabel))))
                .addGap(21, 21, 21)
                .addComponent(tableCaixaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void adicionarDinheiroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarDinheiroButtonActionPerformed
        if(lojaDB.caixaAberto==false){
            JOptionPane.showMessageDialog(adicionarDinheiroButton, "Antes abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        String input = JOptionPane.showInputDialog(adicionarDinheiroButton, "Digite o valor a incluir no caixa para troco!", "Incluir trocado no caixa", JOptionPane.WARNING_MESSAGE);            
        if (input==null)
            return;
        Double trocado;
        if(Main.isDoubleValid(input)==false){
            JOptionPane.showMessageDialog(adicionarDinheiroButton, "Formato inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        trocado=Main.formatDoubleString(input);
        addOrRemoveMoney("add", trocado);
        update();
    }//GEN-LAST:event_adicionarDinheiroButtonActionPerformed
    
    private void retirarDinheiroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retirarDinheiroButtonActionPerformed
        if(lojaDB.caixaAberto==false){
            JOptionPane.showMessageDialog(retirarDinheiroButton, "Antes abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        String input = JOptionPane.showInputDialog(retirarDinheiroButton, "Digite o valor a ser retirado do caixa!", "Retirar dinheiro do caixa", JOptionPane.WARNING_MESSAGE);            
        if (input==null)
            return;
        Double trocado;
        input = input.trim();
        input = input.replace(",", ".");
        try{
            trocado=Double.parseDouble(input);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(retirarDinheiroButton, "Formato inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        addOrRemoveMoney("remove", trocado);
        update();
    }//GEN-LAST:event_retirarDinheiroButtonActionPerformed

    private void abrirFecharCaixaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirFecharCaixaButtonActionPerformed
        if(lojaDB.caixaAberto){
            int answ = JOptionPane.showConfirmDialog(abrirFecharCaixaButton, "Tem certeza que quer fechar o caixa?", "Tela de confirmação", JOptionPane.OK_CANCEL_OPTION);
            if(answ!=0)
                return;
            
        }
        if(lojaDB.caixaAberto==false){
            if(isValidEntry()==false){
                JOptionPane.showMessageDialog(abrirFecharCaixaButton, "Digite os campos corretamente para abrir o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }            
            abrirCaixa();   
        }
    }//GEN-LAST:event_abrirFecharCaixaButtonActionPerformed
    private boolean isValidEntry(){
        String data = dataAberturaField.getText();
        String hora = horaAberturaField.getText();
        String adicionado  = adicionadoField.getText();
        if(Main.isDateValid(data)==false)
            return false;
        if(Main.isTimeValid(hora)==false)
            return false;
        if(Main.isDoubleValid(adicionado)==false)
            return false;
        return true;
    }
        
    public void abrirCaixa(){
        String data = dataAberturaField.getText();
        String hora = horaAberturaField.getText();
        String adicionado  = adicionadoField.getText();
        data = Main.formatStringToSql("Date", data);
        hora = Main.formatStringToSql("Time", hora);
        adicionado = Double.toString(Main.formatDoubleString(adicionado));
        String query = "insert into caixa(Status, Data_Abertura, Hora_Abertura, Adicionado, Retirado) Values ('aberto', "+ data +", "+hora+", "+adicionado+", 0)";
        lojaDB.executeQuery(query);
        update();
    }
    private void addOrRemoveMoney(String command, Double value){
        Double initialValue;
        String query;
        query="UPDATE Caixa SET ";
        if(command.equals("add")){
            query+="Adicionado = ";
            initialValue = Double.parseDouble(lojaDB.getOfCaixa("Adicionado"));
            query += Main.twoDig(value+initialValue);
        }
        else if(command.equals("remove")){
            query+="Retirado = ";
            initialValue = Double.parseDouble(lojaDB.getOfCaixa("Retirado"));
            query += Main.twoDig(value+initialValue);
        }          
        query+=" where ID_Caixa = "+ lojaDB.getOfCaixa("ID_Caixa");
        lojaDB.executeQuery(query);
    }
    public void update(){
        lojaDB.setBooleanCaixaAberto();
        setLabelsCaixa();
        lojaDB.updateTable(tableCaixa, tableCaixaPanel,"Transacao", false, null);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirFecharCaixaButton;
    private javax.swing.JTextField adicionadoField;
    private javax.swing.JButton adicionarDinheiroButton;
    private javax.swing.JTextField dataAberturaField;
    private javax.swing.JTextField horaAberturaField;
    private javax.swing.JLabel idCaixaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel retiradoLabel;
    private javax.swing.JButton retirarDinheiroButton;
    private javax.swing.JPanel tableCaixaPanel;
    private javax.swing.JLabel vendasDevLabel;
    private javax.swing.JButton verCaixaAtualButton;
    // End of variables declaration//GEN-END:variables
}
