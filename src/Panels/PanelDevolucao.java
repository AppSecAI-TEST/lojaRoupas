/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Main.Main;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Soraya
 */
public class PanelDevolucao extends javax.swing.JPanel {

    /**
     * Creates new form PanelTroca
     */
    JTable tableDevol;
    Main lojaDB;
    String columnTableNames[] = new String[]{"Código", "Tipo", "Descrição", "Cliente", "Data da venda", "Valor Pago"};
    public PanelDevolucao(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
        createTable(columnTableNames); 
        JScrollPane scrollTable=new JScrollPane(tableDevol);
        tableDevolucaoPanel.removeAll();
        tableDevolucaoPanel.add(scrollTable); 
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableDevol = new JTable(model);        
        tableDevolucaoPanel.setLayout(new BoxLayout(tableDevolucaoPanel, BoxLayout.PAGE_AXIS));     
        tableDevol.setDefaultEditor(Object.class, null);
    }
    public void update(){
        setProductRegistred(true);
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key);
    }
    public void setProductRegistred(boolean flag){
        productNotRegistredCheckBox.setSelected(!flag);
        clientKeyField.setVisible(flag);
        dateKeyField.setVisible(flag);
        jLabel1.setVisible(flag);
        jLabel2.setVisible(flag);
        optionClient.setVisible(flag);
        optionDate.setVisible(flag);
        optionProduct.setVisible(flag);
        productKeyField.setVisible(flag);
        tableDevol.setVisible(flag);
        tableDevolucaoPanel.setVisible(flag);
    }    
    public void resultsOfSearch(String option, String keyOfSearch){
        Main.cleanTable(tableDevol);
        if(keyOfSearch.trim().equals("")){
            Main.cleanTable(tableDevol);
            return;
        }            
        keyOfSearch=Main.prepareToDB(keyOfSearch);
        String query="SELECT * From Transacao"; 
        ResultSet results = lojaDB.executeQuery(query);
        int width=100;
        if(results==null){
            System.out.println("Nenhum resultado da busca foi encontrado.");
            return;
        }
        try{
            int columnOfDescricaoTransaction=-1;
            int columnOfClient=-1;
            int columnOfData=-1;
            int columnOfTipoDeTransacao=-1;
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();    
            for (int i = 0; i < numberOfColumns; i++) {
                String s=metaData.getColumnName(i+1);
                if(s.equals("Descricao_Transacao"))
                    columnOfDescricaoTransaction=i;
                if(s.equals("Cliente"))
                    columnOfClient=i; 
                if(s.equals("Data_Transacao"))
                    columnOfData=i; 
                if(s.equals("TipoDeTransacao"))
                    columnOfTipoDeTransacao=i; 
            }      
            tableDevol.setModel(new DefaultTableModel(columnTableNames,0)); 
            tableDevol.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<tableDevol.getColumnCount();i++)
                tableDevol.getColumnModel().getColumn(i).setPreferredWidth(width);
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=results.getString(i+1);
                }
                String typeTransact = columns[columnOfTipoDeTransacao];
                if(typeTransact.equals("venda")==false)
                    continue;
                String descOfTransact=columns[columnOfDescricaoTransaction];
                String client=columns[columnOfClient];
                String date=columns[columnOfData];
                if(option.equals("codigoOuDescricao"))                   
                    if(columns!=null){                        
                        if(descOfTransact!=null && descOfTransact.contains(keyOfSearch))
                            addProductsOfTransaction(option, descOfTransact, keyOfSearch, client, date);
                    }
                if(option.equals("cliente")){
                    if(columns!=null){
                        
                        if(client!=null && client.contains(keyOfSearch))
                            addProductsOfTransaction(option, descOfTransact, keyOfSearch, client, date);
                    }
                }
                if(option.equals("data")){
                    if(columns!=null){                                               
                        String keyData=formatToSQL(keyOfSearch);                        
                        if(date!=null && date.contains(keyData))
                            addProductsOfTransaction(option, descOfTransact, keyOfSearch, client, date);
                    }
                }
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            exception.printStackTrace();
        } // end catch 
        tableDevolucaoPanel.setPreferredSize(new Dimension(4*width,300)); 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        optionClient = new javax.swing.JRadioButton();
        optionDate = new javax.swing.JRadioButton();
        optionProduct = new javax.swing.JRadioButton();
        clientKeyField = new javax.swing.JTextField();
        dateKeyField = new javax.swing.JTextField();
        productKeyField = new javax.swing.JTextField();
        tableDevolucaoPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        productNotRegistredCheckBox = new javax.swing.JCheckBox();

        jLabel1.setText("Localize o produto que será devolvido:  ");

        buttonGroup1.add(optionClient);
        optionClient.setText("Por cliente:  ");
        optionClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionClientActionPerformed(evt);
            }
        });

        buttonGroup1.add(optionDate);
        optionDate.setText("Pela data da venda: ");
        optionDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionDateActionPerformed(evt);
            }
        });

        buttonGroup1.add(optionProduct);
        optionProduct.setText("Por código ou descrição do produto:  ");
        optionProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionProductActionPerformed(evt);
            }
        });

        clientKeyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientKeyFieldActionPerformed(evt);
            }
        });

        dateKeyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateKeyFieldActionPerformed(evt);
            }
        });

        productKeyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productKeyFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tableDevolucaoPanelLayout = new javax.swing.GroupLayout(tableDevolucaoPanel);
        tableDevolucaoPanel.setLayout(tableDevolucaoPanelLayout);
        tableDevolucaoPanelLayout.setHorizontalGroup(
            tableDevolucaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableDevolucaoPanelLayout.setVerticalGroup(
            tableDevolucaoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );

        jLabel2.setText("Clique no produto a ser devolvido:");

        productNotRegistredCheckBox.setText("devolução de produto não cadastrado");
        productNotRegistredCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNotRegistredCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productNotRegistredCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(optionDate)
                            .addComponent(optionClient)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(optionProduct)
                                .addGap(73, 73, 73)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(productKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(clientKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2))
                        .addContainerGap(82, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableDevolucaoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optionProduct)
                    .addComponent(productKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optionClient)
                    .addComponent(clientKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optionDate)
                    .addComponent(dateKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableDevolucaoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productNotRegistredCheckBox)
                .addContainerGap(110, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private String formatToSQL(String s){
        if(s.contains("-")) //YYYY-MM-DD
            return s;
        else if(s.contains("/")) //23/04/1997
        { 
            String[] separated=s.split("/");
            if(separated.length==1)
                return s;
            if(separated.length==2)
                return separated[1]+"-"+separated[0];            
            if(separated.length==3)
                return separated[2]+"-"+separated[1]+"-"+separated[0];
        }
        return s;
    }
    
    private void addProductsOfTransaction(String option, String descOfTransact, String key, String client, String date){
        //if option is "codigoOuDescricao", add only some itens
        //else add all itens
        String split3[]= descOfTransact.split("###");
        String products = split3[1];
        String[] split2 = products.split("##");
        int len2=split2.length;
        date=Main.SqlDateToNormalFormat(date);
        for(int i=0;i<len2;i++){
            String product = split2[i];
            String elemOfProduct[]=product.split("#");
            String column[]=new String[columnTableNames.length];
            column[0] = elemOfProduct[0];
            column[1] = elemOfProduct[1];
            column[2] = elemOfProduct[2];
            Double desc = Double.parseDouble(elemOfProduct[4]);
            Double value = Double.parseDouble(elemOfProduct[5]);
            column[3] = client;
            column[4] = date;
            column[5] = Main.twoDig(value-desc);
            if(option.equals("codigoOuDescricao")) 
                if(column[0].equals(key.trim())==false && column[2].contains(key)==false &&
                        key.contains(column[2])==false)
                    continue;
            lojaDB.addRow(column, tableDevol);
        }        
    }
    private void optionClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionClientActionPerformed
        update();
    }//GEN-LAST:event_optionClientActionPerformed
    
    private void clientKeyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientKeyFieldActionPerformed
        optionClient.setSelected(true);
        optionClientActionPerformed(null);
    }//GEN-LAST:event_clientKeyFieldActionPerformed

    private void dateKeyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateKeyFieldActionPerformed
        optionDate.setSelected(true);
        optionDateActionPerformed(null);
    }//GEN-LAST:event_dateKeyFieldActionPerformed

    private void optionDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionDateActionPerformed
        update();
    }//GEN-LAST:event_optionDateActionPerformed

    private void optionProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionProductActionPerformed
        update();
    }//GEN-LAST:event_optionProductActionPerformed

    private void productKeyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productKeyFieldActionPerformed
        optionProduct.setSelected(true);
        optionProductActionPerformed(null);
    }//GEN-LAST:event_productKeyFieldActionPerformed

    private void productNotRegistredCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productNotRegistredCheckBoxActionPerformed
        if(productNotRegistredCheckBox.isSelected())
            setProductRegistred(false);
        else
            update();
    }//GEN-LAST:event_productNotRegistredCheckBoxActionPerformed
    public String getKey(){
        if(optionProduct.isSelected())
            return Main.prepareToDB(productKeyField.getText());
        if(optionClient.isSelected())
            return Main.prepareToDB(clientKeyField.getText());
        if(optionDate.isSelected())
            return Main.prepareToDB(dateKeyField.getText());
        return null;
    }
    public String getOption(){
        if(optionProduct.isSelected())
            return "codigoOuDescricao";
        if(optionClient.isSelected())
            return "cliente";
        if(optionDate.isSelected())
            return "data";
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField clientKeyField;
    private javax.swing.JTextField dateKeyField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton optionClient;
    private javax.swing.JRadioButton optionDate;
    private javax.swing.JRadioButton optionProduct;
    private javax.swing.JTextField productKeyField;
    private javax.swing.JCheckBox productNotRegistredCheckBox;
    private javax.swing.JPanel tableDevolucaoPanel;
    // End of variables declaration//GEN-END:variables
}
