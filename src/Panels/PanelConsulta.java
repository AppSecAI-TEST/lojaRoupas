/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Main.Main;
import auxClasses.AuxFieldCreditDevol;
import auxClasses.PopClickListener;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Soraya
 */
public class PanelConsulta extends javax.swing.JPanel {

    /**
     * Creates new form PanelConsulta
     */
    JTable tableConsulta;
    boolean firstCreation=true;
    Main lojaDB;
    public PanelConsulta(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
        createTable(new String[]{"ID do Caixa","Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"}); 
        JScrollPane scrollTable=new JScrollPane(tableConsulta);
        tableConsultaPanel.removeAll();
        tableConsultaPanel.add(scrollTable); 
        procuraFieldActionPerformed(null);
        AuxFieldCreditDevol documentListener = new AuxFieldCreditDevol(this);
        procuraField.getDocument().addDocumentListener(documentListener);        
    }
    public void search(){
        String key = getKey();
        key = Main.prepareToSearch(key);
        resultsOfSearch(key); 
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableConsulta = new JTable(model);        
        tableConsultaPanel.setLayout(new BoxLayout(tableConsultaPanel, BoxLayout.PAGE_AXIS));     
        tableConsulta.setDefaultEditor(Object.class, null);
        tableConsulta.addMouseListener(new PopClickListener(this, tableConsulta));
    }
    public void updateSQL(MouseEvent evt){
        int row = tableConsulta.rowAtPoint(evt.getPoint());
        int col = tableConsulta.columnAtPoint(evt.getPoint());        
        lojaDB.updateSQL(tableConsulta, getTableName(), row, col);
        update();
    }
    public void update(){
        boolean flagVisible = getTableName().equals("Mercadoria");
        estoqueButton.setVisible(flagVisible);
        flagVisible = getTableName().equals("Mercadoria")||getTableName().equals("Cliente")||getTableName().equals("Penduras");
        somarButton.setVisible(flagVisible);
        String key = getKey();
        key = Main.prepareToSearch(key);
        resultsOfSearch(key); 
    }
    public void verEvent(MouseEvent evt){
        int row = tableConsulta.rowAtPoint(evt.getPoint());
        int col = tableConsulta.columnAtPoint(evt.getPoint());        
        Main.verEvent(tableConsulta, row, col);        
    }
    protected String getTableName(){
        return (String) tableNameComboBox.getModel().getSelectedItem(); 
    }
    public String getKey(){
        return procuraField.getText();
    }
    public void resultsOfSearch(String keyOfSearch){
        keyOfSearch=Main.prepareToSearch(keyOfSearch);
        String tableName=getTableName();
        String query;
        if(tableName.equals("Penduras"))
            query="SELECT * From Cliente"; 
        else
            query="SELECT * From "+tableName; 
        ResultSet results = lojaDB.executeQuery(query);
        int numCol=1;
        int width=100;
        if(results==null)
        {
            System.out.println("Nenhum resultado da busca foi encontrado.");
            return;
        }
        try{
            Set <Integer>columnsToSearch=new HashSet();
            int columnOfSaldo=-1;
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String s=nameColumns[i];
                if(tableName.equals("Penduras")){
                    if(s.equals("Saldo_Cliente"))
                        columnOfSaldo = i;
                    if(s.equals("ID_Cliente")|| s.equals("Nome_Cliente") || s.equals("Observacao")
                            || s.equals("Descricao_Cliente") || s.equals("CPF_Cliente") )
                        columnsToSearch.add(i);
                }
                else
                {
                    if(s.equals("ID_"+tableName)|| s.equals("Descricao_"+tableName) || s.equals("TipoMercadoria")||
                        s.equals("Observacao")||s.equals("Nome_"+tableName)|| s.equals("CPF_"+tableName) || s.equals("Status"))
                    columnsToSearch.add(i);
                }                
            }      
            if(tableName.equals("Penduras"))
                tableConsulta.setModel(new DefaultTableModel(Main.getOff_NameTable(nameColumns, "Cliente"),0)); 
            else
                tableConsulta.setModel(new DefaultTableModel(Main.getOff_NameTable(nameColumns, tableName),0)); 
            tableConsulta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<tableConsulta.getColumnCount();i++){
                if(i==0) width=width/2;
                tableConsulta.getColumnModel().getColumn(i).setPreferredWidth(width);
                if(i==0) width=width*2;
            }
               
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                String originalColumns[]=new String[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    originalColumns[i]=results.getString(i+1);
                    columns[i]=Main.prepareToSearch(originalColumns[i]);
                }
                boolean encontrou=false;
                if(tableName.equals("Penduras")){
                    int i= columnOfSaldo;
                    if(columns==null || columns[i]==null || columns[i].equals("0") || 
                            columns[i].equals("") || columns[i].equals("0.00") ||
                            columns[i].equals("0,00"))
                        continue;
                }
                for (int i:columnsToSearch)
                {
                    //System.out.println("Col: "+i+" - "+nameColumns[i]);                    
                    if(columns!=null && columns[i]!=null && columns[i].contains(keyOfSearch))
                        //System.out.println("encontrou");
                        encontrou=true;
                }
                if(encontrou)
                {
                    numCol++;
                    lojaDB.addRow(originalColumns, tableConsulta);
                }
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            exception.printStackTrace();
        } // end catch 
        tableConsultaPanel.setPreferredSize(new Dimension(numCol*width-width/2,300)); 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tableConsultaPanel = new javax.swing.JPanel();
        tableNameComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        procuraField = new javax.swing.JTextField();
        estoqueButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        somarButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Consulta: ");

        javax.swing.GroupLayout tableConsultaPanelLayout = new javax.swing.GroupLayout(tableConsultaPanel);
        tableConsultaPanel.setLayout(tableConsultaPanelLayout);
        tableConsultaPanelLayout.setHorizontalGroup(
            tableConsultaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableConsultaPanelLayout.setVerticalGroup(
            tableConsultaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        tableNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mercadoria", "Cliente", "Fornecedor", "Usuario", "Penduras", "TipoMercadoria" }));
        tableNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableNameComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Procurar por nome ou Identificador: ");

        procuraField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuraFieldActionPerformed(evt);
            }
        });

        estoqueButton.setText("estoque total");
        estoqueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estoqueButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Resultados: ");

        somarButton.setText("Somar");
        somarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                somarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableConsultaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tableNameComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(procuraField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(estoqueButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(somarButton)))
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tableNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estoqueButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(procuraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(somarButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableConsultaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void tableNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableNameComboBoxActionPerformed
        update();
    }//GEN-LAST:event_tableNameComboBoxActionPerformed

    private void procuraFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuraFieldActionPerformed
        update();
    }//GEN-LAST:event_procuraFieldActionPerformed

    private void estoqueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estoqueButtonActionPerformed
        String query = "SELECT ID_Mercadoria FROM Mercadoria WHERE Status= \'no estoque\'";
        int numMercadoriasNoEstoque=lojaDB.getNumberColumnsOfQuery(query);
        query = "SELECT SUM(Preco_Merc) FROM Mercadoria WHERE Status= \'no estoque\'";
        double valorNoEstoque=Main.formatDoubleString(lojaDB.getUniqueValueOfQuery(query));        
        //-----------------------------------------------------------------------
        query = "SELECT ID_Mercadoria FROM Mercadoria WHERE Status= \'encomendado\'";
        int numMercadoriasEncomendadas=lojaDB.getNumberColumnsOfQuery(query);
        query = "SELECT SUM(Preco_Merc) FROM Mercadoria WHERE Status= \'encomendado\'";
        double valorEncomendadas=Main.formatDoubleString(lojaDB.getUniqueValueOfQuery(query));  
        //-----------------------------------------------------------------------
        query = "SELECT ID_Mercadoria FROM Mercadoria WHERE Status= \'vendido\'";
        int numMercadoriasVendidas=lojaDB.getNumberColumnsOfQuery(query);
        query = "SELECT SUM(Preco_Merc) FROM Mercadoria WHERE Status= \'vendido\'";
        double valorVendidas=Main.formatDoubleString(lojaDB.getUniqueValueOfQuery(query));  
        //-----------------------------------------------------------------------     
        query = "SELECT SUM(VendasDevolucoes) FROM Caixa";
        double vendasDevol=Main.formatDoubleString(lojaDB.getUniqueValueOfQuery(query));
        //-----------------------------------------------------------------------
        //-----------------------------------------------------------------------        
        String message = "Mercadorias no estoque: ";
        message+=      "\n    Número de mercadorias: "+numMercadoriasNoEstoque;
        message+=      "\n    Valor: "+valorNoEstoque;
        message +="\n\nMercadorias vendidas: ";
        message+=      "\n    Número de mercadorias: "+numMercadoriasVendidas;
        message+=      "\n    Valor: "+valorVendidas;
        message +="\n\nMercadorias encomendadas: ";
        message+=      "\n    Número de mercadorias: "+numMercadoriasEncomendadas;
        message+=      "\n    Valor: "+valorEncomendadas;
        message += "\n\nValor total de vendas e devoluções: "+Main.twoDig(vendasDevol);
        Main.formattedMessage(estoqueButton, message,"Informações relativas a todo o período da loja", JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_estoqueButtonActionPerformed

    private void somarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_somarButtonActionPerformed
        int len = tableConsulta.getRowCount();
        if(len==0){
            JOptionPane.showMessageDialog(somarButton, "Tabela vazia! Consulte algo válido", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        if(getTableName().equals("Mercadoria")){
            double preco=0;            
            int colOfPreco = Main.getIndexColumnWithColumnName(tableConsulta, "Preço");
            for(int i=0;i<len;i++)
                preco+=Main.formatDoubleString(Main.elemOfTable(tableConsulta, i, colOfPreco));
            String message = "Preço total das mercadorias dos resultados abaixo:  "+Main.twoDig(preco);
            message +=     "\nNúmero de mercadorias encontradas: "+len;
            Main.formattedMessage(somarButton, message, "Informações relativas aos resultados abaixo", JOptionPane.INFORMATION_MESSAGE);  
        }
        if(getTableName().equals("Penduras")||getTableName().equals("Cliente")){
            double preco=0;            
            int colOfSaldo = Main.getIndexColumnWithColumnName(tableConsulta, "Saldo");
            for(int i=0;i<len;i++)
                preco+=Main.formatDoubleString(Main.elemOfTable(tableConsulta, i, colOfSaldo));
            String message = "Soma dos saldos dos clientes abaixo:  "+Main.twoDig(preco);
            message +=     "\nNúmero de clientes encontrados: "+len;
            Main.formattedMessage(somarButton, message, "Informações relativas aos resultados abaixo", JOptionPane.INFORMATION_MESSAGE);  
        }
        
    }//GEN-LAST:event_somarButtonActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton estoqueButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField procuraField;
    private javax.swing.JButton somarButton;
    private javax.swing.JPanel tableConsultaPanel;
    private javax.swing.JComboBox<String> tableNameComboBox;
    // End of variables declaration//GEN-END:variables
}
