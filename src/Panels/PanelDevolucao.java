/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import auxClasses.MyFrame;
import Main.Main;
import auxClasses.AuxFieldCreditDevol;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelDevolucao extends javax.swing.JPanel {
    JTable tableDevol;
    Main lojaDB;
    MyFrame frameNewClient;
    String columnTableNames[] = new String[]{"Código", "Tipo", "Descrição", "Cliente", "Data da venda", "Desconto dado", "Valor Pago"};
    public PanelDevolucao(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
        createTable(columnTableNames); 
        JScrollPane scrollTable=new JScrollPane(tableDevol);
        tableDevolucaoPanel.removeAll();
        tableDevolucaoPanel.add(scrollTable); 
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clientInBox);
        clientInBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                updateSaldoCliente();                            
            }
        });
        AuxFieldCreditDevol documentProduct = new AuxFieldCreditDevol(this, "product");
        productKeyField.getDocument().addDocumentListener(documentProduct);
        AuxFieldCreditDevol documentClient = new AuxFieldCreditDevol(this, "client");
        clientKeyField.getDocument().addDocumentListener(documentClient);
        AuxFieldCreditDevol documentDate = new AuxFieldCreditDevol(this, "date");
        dateKeyField.getDocument().addDocumentListener(documentDate);           
        Main.setDataAndHourFields(dataField, horaField);
    }
    public void pressButtonWithTip(String tip){
        switch(tip){
            case "product":
                optionProduct.setSelected(true);
                break;
            case "client":
                optionClient.setSelected(true);
                break;
            case "date":
                optionDate.setSelected(true);
                break;
                
        }
    }
    public void updateSaldoCliente(){
        saldoClientLabel.setText("saldo: -");
        try{
            String creditoPorCliente = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+clientInBox.getSelectedItem()+"\'", "Saldo_Cliente");
            if(Main.formatDoubleString(creditoPorCliente)!=0)
                saldoClientLabel.setText("saldo: "+creditoPorCliente);
        }catch(Exception ex){}   
    }         
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableDevol = new JTable(model);        
        tableDevolucaoPanel.setLayout(new BoxLayout(tableDevolucaoPanel, BoxLayout.PAGE_AXIS));     
        tableDevol.setDefaultEditor(Object.class, null);
        tableDevolucaoPanel.setPreferredSize(new Dimension(420,100));
    }
    public void search(){
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key); 
    }
    public void update(){
        setProductRegistred(true);
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key);           
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clientInBox);
        tableDevolucaoPanel.setPreferredSize(new Dimension(420,100));
        Main.setDataAndHourFields(dataField, horaField);
    }
    public void setProductRegistred(boolean flag){
        productNotRegistredCheckBox.setSelected(!flag);  
        jLabel4.setVisible(!flag);
        valorField.setVisible(!flag);
        //------------------------------------
        //------------------------------------
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
        //------------------------------------
        //------------------------------------      
        inMoneyOption.setSelected(true);             
        inMoneyOptionActionPerformed(null);  
    }    
    public void resultsOfSearch(String option, String keyOfSearch){
        Main.cleanTable(tableDevol);
        if(keyOfSearch.trim().equals("")){
            Main.cleanTable(tableDevol);
            return;
        }            
        keyOfSearch=Main.prepareToSearch(keyOfSearch);
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
                if(s.equals("Tipo_Transacao"))
                    columnOfTipoDeTransacao=i; 
            }      
            tableDevol.setModel(new DefaultTableModel(columnTableNames,0)); 
            tableDevol.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<tableDevol.getColumnCount();i++){
                if(i==0) width=width/2;
                tableDevol.getColumnModel().getColumn(i).setPreferredWidth(width);
                if(i==0) width=width*2;
            }                
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=results.getString(i+1);
                    columns[i]=Main.prepareToSearch(columns[i]);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
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
        jLabel3 = new javax.swing.JLabel();
        clientInBox = new javax.swing.JComboBox<>();
        addClientButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        valorField = new javax.swing.JTextField();
        concluirButton = new javax.swing.JButton();
        inCreditOption = new javax.swing.JRadioButton();
        inMoneyOption = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        clientInField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dataField = new javax.swing.JTextField();
        horaField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        motivoField = new javax.swing.JTextField();
        saldoClientLabel = new javax.swing.JLabel();

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
            .addGap(0, 80, Short.MAX_VALUE)
        );

        jLabel2.setText("Clique no produto a ser devolvido:");

        productNotRegistredCheckBox.setText("devolução de produto não cadastrado");
        productNotRegistredCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNotRegistredCheckBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Cliente*: ");

        clientInBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addClientButton.setText("cadastrar novo cliente ");
        addClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClientButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Valor do produto*: ");

        valorField.setText(" ");

        concluirButton.setText("Concluir devolução");
        concluirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concluirButtonActionPerformed(evt);
            }
        });

        buttonGroup2.add(inCreditOption);
        inCreditOption.setText("Devolver em crédito para compras futuras");
        inCreditOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inCreditOptionActionPerformed(evt);
            }
        });

        buttonGroup2.add(inMoneyOption);
        inMoneyOption.setText("Devolver em dinheiro");
        inMoneyOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inMoneyOptionActionPerformed(evt);
            }
        });

        jLabel5.setText("Cliente: ");

        jLabel6.setText("Data*: ");

        jLabel7.setText("Hora*: ");

        jLabel8.setText("Motivo: ");

        saldoClientLabel.setText("saldo: -");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableDevolucaoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(productNotRegistredCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                    .addComponent(jLabel2)))
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(clientInBox, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saldoClientLabel)
                                .addGap(18, 18, 18)
                                .addComponent(addClientButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valorField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inCreditOption)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clientInField, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(motivoField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(concluirButton))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(horaField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dataField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))))
                            .addComponent(inMoneyOption)
                            .addComponent(jLabel6))
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productNotRegistredCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(tableDevolucaoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inMoneyOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inCreditOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(clientInBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addClientButton)
                    .addComponent(saldoClientLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(clientInField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(horaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(motivoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(concluirButton)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            column[5] = Main.twoDig(desc);
            column[6] = Main.twoDig(value-desc);
            column[0]=Main.prepareToSearch(column[0]);
            column[2]=Main.prepareToSearch(column[2]);            
            if(option.equals("codigoOuDescricao")) 
                if(column[0].equals(key.trim())==false && column[2].contains(key)==false &&
                        key.contains(column[2])==false)
                    continue;
            lojaDB.addRow(column, tableDevol);
        }        
    }
    private void optionClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionClientActionPerformed
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key);
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
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key);
    }//GEN-LAST:event_optionDateActionPerformed
    private void optionProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionProductActionPerformed
        String key = getKey();
        if(key!=null)
            resultsOfSearch(getOption(), key);
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
    private void inCreditOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inCreditOptionActionPerformed
        if(inCreditOption.isSelected()){
            clientInBox.setVisible(true);
            clientInField.setVisible(false);
            jLabel5.setVisible(false);
            addClientButton.setVisible(true);
            jLabel3.setVisible(true);
            saldoClientLabel.setVisible(true);
        }
    }//GEN-LAST:event_inCreditOptionActionPerformed
    private void addClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClientButtonActionPerformed
        PanelCadastro cadastroClient = new PanelCadastro(lojaDB, this);        
        lojaDB.setTabbedPaneVisible(false);
        frameNewClient = new MyFrame();
        frameNewClient.add(cadastroClient);
        frameNewClient.setVisible(true);
        frameNewClient.setSize(new Dimension(500, 700));
        frameNewClient.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameNewClient.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                lojaDB.setTabbedPaneVisible(true);
                frameNewClient.exitProcedure();
            }
        });
    }//GEN-LAST:event_addClientButtonActionPerformed
    public void returnVisible(String newClient){
        lojaDB.setTabbedPaneVisible(true);
        frameNewClient.setEnabled(false);
        frameNewClient.dispose();        
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clientInBox);
        clientInBox.setSelectedItem(newClient);
        //CODE HERE!!!!!!!!!!
        //update comboBox with new client
        //set comboBox to the new client
    }
    private void inMoneyOptionActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if(inMoneyOption.isSelected())
            clientInBox.setVisible(false);
            addClientButton.setVisible(false);
            jLabel3.setVisible(false);
            saldoClientLabel.setVisible(false);
            clientInField.setVisible(true);
            jLabel5.setVisible(true);
    }
    private void concluirButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        if(lojaDB.caixaAberto==false){
            JOptionPane.showMessageDialog(concluirButton, "Abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }           
        if(productNotRegistredCheckBox.isSelected()==false){
            //product choosed in table
            if(isValidSituationRegistred()==false)
                return;
            int r = tableDevol.getSelectedRow();            
            int colOfKey = Main.getIndexColumnWithColumnName(tableDevol, "Código");
            String barCode =tableDevol.getValueAt(r, colOfKey).toString();            
            int colOfPaidValue = Main.getIndexColumnWithColumnName(tableDevol, "Valor Pago");
            String tot = Main.validateDoubleString(tableDevol.getValueAt(r, colOfPaidValue).toString());
            String data = dataField.getText();
            String hora = horaField.getText();
            String motivo = motivoField.getText();
            String client="";
            if(inMoneyOption.isSelected())
                client = clientInField.getText();
            if(inCreditOption.isSelected())
                client = Main.getChoosedComboBox(clientInBox); 
            if(Main.isDateValid(data)==false || Main.isTimeValid(hora)==false){
                JOptionPane.showMessageDialog(concluirButton, "Data ou hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);            
                return;
            }
            data = Main.formatStringToSql("Date", data);
            hora = Main.formatStringToSql("Time", hora);
            String query1 = "UPDATE Mercadoria SET Status = \'no estoque\' WHERE ID_Mercadoria ="+barCode;                          
            lojaDB.executeQuery(query1);
            String query2 ="";
            if(inMoneyOption.isSelected())
                query2 = "INSERT into Transacao(Tipo_Transacao, Dinheiro, Com_SaldoCliente, Data_Transacao, Hora_Transacao, "
                + "Descricao_Transacao, ID_Caixa, Observacao, Cliente) VALUES ("
                + "\"devolucao\", -"+ tot+",0.00,"+data+","+hora+",\""+
                barCode+"\","+lojaDB.getOfCaixa("ID_Caixa")+",\"Motivo: "+motivo
                + "\",\""+client+"\")";
            if(inCreditOption.isSelected())
                query2 = "INSERT into Transacao(Tipo_Transacao, Dinheiro, Com_SaldoCliente, Data_Transacao, Hora_Transacao, "
                + "Descricao_Transacao, ID_Caixa, Observacao, Cliente) VALUES ("
                + "\"devolucao\", 0.00, -"+ tot+","+data+","+hora+",\""+
                barCode+"\","+lojaDB.getOfCaixa("ID_Caixa")+",\"Motivo: "+motivo
                + "\",\""+client+"\")";
                             
            lojaDB.executeQuery(query2); 
            if(inMoneyOption.isSelected())
                JOptionPane.showMessageDialog(null, "Devolução concluida com sucesso", "Aviso", JOptionPane.WARNING_MESSAGE);                                             
            if(inCreditOption.isSelected()){
                JOptionPane.showMessageDialog(null, "Devolução e atualização de saldo do cliente concluídos com sucesso ", "Aviso", JOptionPane.WARNING_MESSAGE);
                lojaDB.addSaldoCliente(client, tot);
            }      
            updateSaldoCliente();
            horaField.setText("");
            motivoField.setText("");
        }
        if(productNotRegistredCheckBox.isSelected()){
            if(isValidSituationNotRegistred()==false)
                return;
            String tot = Main.validateDoubleString(valorField.getText());
            String data = dataField.getText();
            String hora = horaField.getText();
            String motivo = motivoField.getText();
            String client="";
            if(inMoneyOption.isSelected())
                client = clientInField.getText();
            if(inCreditOption.isSelected())
                client = Main.getChoosedComboBox(clientInBox); 
            if(Main.isDateValid(data)==false || Main.isTimeValid(hora)==false){
                JOptionPane.showMessageDialog(concluirButton, "Data ou hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);            
                return;
            }
            tot=tot.trim().replace(",", ".");
            if(Main.isDoubleValid(tot)==false){
                JOptionPane.showMessageDialog(concluirButton, "Digite um valor válido", "Aviso", JOptionPane.WARNING_MESSAGE);            
                return;
            }
            data = Main.formatStringToSql("Date", data);
            hora = Main.formatStringToSql("Time", hora);
            String query = "INSERT into Transacao(Tipo_Transacao, Dinheiro, Com_SaldoCliente, Data_Transacao, Hora_Transacao, "
                + "ID_Caixa, Observacao, Cliente) VALUES ("
                    + "\"devolucao\", -"+ tot+",0.00, "+data+","+hora+
                    ","+lojaDB.getOfCaixa("ID_Caixa")+",\"Motivo: "+motivo+
                     "\",\""+client+"\")";            
            lojaDB.executeQuery(query); 
            if(inMoneyOption.isSelected())
                JOptionPane.showMessageDialog(null, "Devolução concluida com sucesso", "Aviso", JOptionPane.WARNING_MESSAGE);                                             
            if(inCreditOption.isSelected()){
                JOptionPane.showMessageDialog(null, "Devolução e atualização de saldo do cliente concluídos com sucesso ", "Aviso", JOptionPane.WARNING_MESSAGE);
                lojaDB.addSaldoCliente(client, tot);
            }                           
        }
        

    }
    public boolean isValidSituationRegistred(){
        int[] rows = tableDevol.getSelectedRows();
        if(rows==null || rows.length!=1){
            JOptionPane.showMessageDialog(null, "Selecione uma única mercadoria na tabela", "Aviso", JOptionPane.WARNING_MESSAGE);                                             
            return false;
        }
        if(inCreditOption.isSelected()){
            if(clientInBox.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Selecione um cliente para devolução por crédito em compras futuras", "Aviso", JOptionPane.WARNING_MESSAGE);                                             
                return false;
            }            
        }   
        String data = dataField.getText();
        String hora = horaField.getText();
        if(Main.isDateValid(data)==false || Main.isTimeValid(hora)==false){
            JOptionPane.showMessageDialog(concluirButton, "Data ou hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return false;
        }
        return true;
    }
    public void setDataHoraPanelDevol(){
        if(Main.isEmpty(dataField.getText()))
            dataField.setText(Main.SqlDateToNormalFormat(lojaDB.getOfCaixa("Data_Abertura")));
    }
    public boolean isValidSituationNotRegistred(){
        return true;
    }
    public String getKey(){
        if(optionProduct.isSelected())
            return Main.prepareToSearch(productKeyField.getText());
        if(optionClient.isSelected())
            return Main.prepareToSearch(clientKeyField.getText());
        if(optionDate.isSelected())
            return Main.prepareToSearch(dateKeyField.getText());
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
    private javax.swing.JButton addClientButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> clientInBox;
    private javax.swing.JTextField clientInField;
    private javax.swing.JTextField clientKeyField;
    private javax.swing.JButton concluirButton;
    private javax.swing.JTextField dataField;
    private javax.swing.JTextField dateKeyField;
    private javax.swing.JTextField horaField;
    private javax.swing.JRadioButton inCreditOption;
    private javax.swing.JRadioButton inMoneyOption;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField motivoField;
    private javax.swing.JRadioButton optionClient;
    private javax.swing.JRadioButton optionDate;
    private javax.swing.JRadioButton optionProduct;
    private javax.swing.JTextField productKeyField;
    private javax.swing.JCheckBox productNotRegistredCheckBox;
    private javax.swing.JLabel saldoClientLabel;
    private javax.swing.JPanel tableDevolucaoPanel;
    private javax.swing.JTextField valorField;
    // End of variables declaration//GEN-END:variables
}
