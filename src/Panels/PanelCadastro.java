/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Main.Main;
import PanelsCadastro.*;
import auxClasses.ConfirmaVendaPanel;
import auxClasses.PopClickListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 *
 * @author Soraya
 */
public class PanelCadastro extends javax.swing.JPanel {

    PanelDevolucao panelDev;    
    JTable tableCadastro = new JTable();
    Main lojaDB;
    JTextField []queryTextField;
    JLabel []labelCol;
    String columnType[];
    boolean firstCreation=true;
    PanelMercadoria panelMercadoria;
    PanelCliente_Fornecedor panelCliente, panelFornecedor;
    PanelUsuario panelUsuario;
    ConfirmaVendaPanel panelConfirmaVenda;
    public PanelCadastro(Main lojaDB) {
        initComponents();        
        this.lojaDB=lojaDB;
        createTable(new String[]{"ID do Caixa","Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"}); 
        JScrollPane scrollTable=new JScrollPane(tableCadastro);
        tableCadastroPanel.removeAll();
        tableCadastroPanel.add(scrollTable); 
        panelCliente = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Cliente", null, panelCliente, "Cliente" );
        panelMercadoria = new PanelMercadoria(lojaDB);         
        tabbedPaneCadastro.addTab( "Mercadoria", null, panelMercadoria, "Mercadoria" );        
        panelFornecedor = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Fornecedor", null, panelFornecedor, "Fornecedor" ); 
        panelUsuario = new PanelUsuario(lojaDB); 
        tabbedPaneCadastro.addTab( "Usuário", null, panelUsuario, "Usuário" );              
        tableCadastro.setDefaultEditor(Object.class, null);
        tableCadastro.addMouseListener(new PopClickListener(this, tableCadastro));
    }    
    public ResultSet executeQuery(String query){
        return lojaDB.executeQuery(query);
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableCadastro = new JTable(model);        
        tableCadastroPanel.setLayout(new BoxLayout(tableCadastroPanel, BoxLayout.PAGE_AXIS)); 
        tableCadastro.setDefaultEditor(Object.class, null);
    }
    protected String getTableName(){
        String tableName=tabbedPaneCadastro.getTitleAt(tabbedPaneCadastro.getSelectedIndex());
        if(tableName.equals("Usuário"))
            tableName="Usuario";
        return tableName;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableCadastroPanel = new javax.swing.JPanel();
        inserirButton = new javax.swing.JButton();
        tabbedPaneCadastro = new javax.swing.JTabbedPane();

        javax.swing.GroupLayout tableCadastroPanelLayout = new javax.swing.GroupLayout(tableCadastroPanel);
        tableCadastroPanel.setLayout(tableCadastroPanelLayout);
        tableCadastroPanelLayout.setHorizontalGroup(
            tableCadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableCadastroPanelLayout.setVerticalGroup(
            tableCadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        inserirButton.setText("Inserir");
        inserirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirButtonActionPerformed(evt);
            }
        });

        tabbedPaneCadastro.setMinimumSize(new java.awt.Dimension(360, 180));
        tabbedPaneCadastro.setPreferredSize(new java.awt.Dimension(360, 180));
        tabbedPaneCadastro.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneCadastroStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tableCadastroPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(tabbedPaneCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inserirButton)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(inserirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPaneCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(tableCadastroPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void update(){        
        lojaDB.updateTable(tableCadastro, tableCadastroPanel, getTableName(), false, null);
    }
    public void updateSQL(MouseEvent evt){
        int row = tableCadastro.rowAtPoint(evt.getPoint());
        int col = tableCadastro.columnAtPoint(evt.getPoint());
        lojaDB.updateSQL(tableCadastro, getTableName(), row, col);
        update();
    }    
    private void tabbedPaneCadastroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneCadastroStateChanged
        update();
    }//GEN-LAST:event_tabbedPaneCadastroStateChanged
    public void verEvent(MouseEvent evt){
        int row = tableCadastro.rowAtPoint(evt.getPoint());
        int col = tableCadastro.columnAtPoint(evt.getPoint());        
        Main.verEvent(tableCadastro, row, col);        
    }
    private void inserirButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        String nameClient=null;
        switch(getTableName()){
            case "Cliente":
                nameClient = panelCliente.insertCliente_Fornecedor("Cliente");
                break;
            case "Mercadoria":
                panelMercadoria.insertMercadoria();
                break;
            case "Fornecedor":
                panelFornecedor.insertCliente_Fornecedor("Fornecedor");
                break;
            case "Usuario":
                panelUsuario.insertUsuario();
                break;
            default:                
                break;                
        }
        update();
        if(nameClient!=null){
            if(panelDev!=null)
                panelDev.returnVisible(nameClient);
            if(panelConfirmaVenda!=null)
                panelConfirmaVenda.returnVisible(nameClient);
        }
                
    }
    public PanelCadastro(Main lojaDB, PanelDevolucao panelDev){
        initComponents();        
        this.lojaDB=lojaDB;
        createTable(new String[]{"ID do Caixa","Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"}); 
        JScrollPane scrollTable=new JScrollPane(tableCadastro);
        tableCadastroPanel.removeAll();
        tableCadastroPanel.add(scrollTable); 
        panelCliente = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Cliente", null, panelCliente, "Cliente" );
        panelMercadoria = new PanelMercadoria(lojaDB);         
        tabbedPaneCadastro.addTab( "Mercadoria", null, panelMercadoria, "Mercadoria" );        
        panelFornecedor = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Fornecedor", null, panelFornecedor, "Fornecedor" ); 
        panelUsuario = new PanelUsuario(lojaDB); 
        tabbedPaneCadastro.addTab( "Usuário", null, panelUsuario, "Usuário" );              
        tableCadastro.setDefaultEditor(Object.class, null);
        //-----------------------------------------------------------------------------------
        this.panelDev=panelDev;        
        tabbedPaneCadastro.setEnabled(false);
    }
    public PanelCadastro(Main lojaDB, ConfirmaVendaPanel panelConfirmaVenda){
        initComponents();        
        this.lojaDB=lojaDB;
        createTable(new String[]{"ID do Caixa","Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"}); 
        JScrollPane scrollTable=new JScrollPane(tableCadastro);
        tableCadastroPanel.removeAll();
        tableCadastroPanel.add(scrollTable); 
        panelCliente = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Cliente", null, panelCliente, "Cliente" );
        panelMercadoria = new PanelMercadoria(lojaDB);         
        tabbedPaneCadastro.addTab( "Mercadoria", null, panelMercadoria, "Mercadoria" );        
        panelFornecedor = new PanelCliente_Fornecedor(lojaDB); 
        tabbedPaneCadastro.addTab( "Fornecedor", null, panelFornecedor, "Fornecedor" ); 
        panelUsuario = new PanelUsuario(lojaDB); 
        tabbedPaneCadastro.addTab( "Usuário", null, panelUsuario, "Usuário" );              
        tableCadastro.setDefaultEditor(Object.class, null);
        //-----------------------------------------------------------------------------------
        this.panelConfirmaVenda=panelConfirmaVenda;        
        tabbedPaneCadastro.setEnabled(false);
    }
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton inserirButton;
    private javax.swing.JTabbedPane tabbedPaneCadastro;
    private javax.swing.JPanel tableCadastroPanel;
    // End of variables declaration//GEN-END:variables
}
