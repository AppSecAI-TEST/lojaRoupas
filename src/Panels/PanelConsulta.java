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
        tableConsulta.addMouseListener(new PopClickListener(this));
    }
    public void updateSQL(MouseEvent evt){
        int row = tableConsulta.rowAtPoint(evt.getPoint());
        int col = tableConsulta.columnAtPoint(evt.getPoint());        
        lojaDB.updateSQL(tableConsulta, getTableName(), row, col);
        update();
    }
    public void update(){
        String key = getKey();
        key = Main.prepareToSearch(key);
        resultsOfSearch(key); 
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
            .addGap(0, 221, Short.MAX_VALUE)
        );

        tableNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mercadoria", "Cliente", "Fornecedor", "Usuario", "Penduras", "TipoMercadoria" }));
        tableNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableNameComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Procurar por nome ou Identificador: ");

        procuraField.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                procuraFieldInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        procuraField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuraFieldActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tableNameComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(procuraField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 64, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tableNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(procuraField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableConsultaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void tableNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableNameComboBoxActionPerformed
        procuraFieldActionPerformed(null);
    }//GEN-LAST:event_tableNameComboBoxActionPerformed

    private void procuraFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuraFieldActionPerformed
        resultsOfSearch(procuraField.getText());
    }//GEN-LAST:event_procuraFieldActionPerformed

    private void procuraFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_procuraFieldInputMethodTextChanged
        Main.p("teste");
    }//GEN-LAST:event_procuraFieldInputMethodTextChanged
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField procuraField;
    private javax.swing.JPanel tableConsultaPanel;
    private javax.swing.JComboBox<String> tableNameComboBox;
    // End of variables declaration//GEN-END:variables
}
