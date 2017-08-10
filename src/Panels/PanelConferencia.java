/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Main.Main;
import Panels.PanelVenda.ConsultaMercadoria;
import auxClasses.PopClickListener;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Soraya
 */
public class PanelConferencia extends javax.swing.JPanel {

    /**
     * Creates new form PanelConferencia
     */
    JTable tableConf, tableNotRegistred;
    Main lojaDB;
    HashSet <String> idSetProductsConferred=new HashSet();
    HashSet <String> idSetProductsNotRegistred=new HashSet();
    
    public PanelConferencia(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();; 
        String cols[]=new String[]{"Código", "Tipo","Descrição", "Tamanho", "Desconto", "Valor"};
        createTables(cols); 
        JScrollPane scrollTable=new JScrollPane(tableConf);
        tableConferenciaPanel.removeAll();
        tableConferenciaPanel.add(scrollTable); 
        //----------------------------------------
        JScrollPane scrollTableNotRegistred=new JScrollPane(tableNotRegistred);
        tableNotRegistredPanel.removeAll();
        tableNotRegistredPanel.add(scrollTableNotRegistred); 
        tableConf.addMouseListener(new PopClickListener(this, tableConf));
    }
    private void createTables(String cols[]){
        DefaultTableModel model1, model2;      
        model1 = new DefaultTableModel(cols,0);        
        tableConf = new JTable(model1);        
        tableConferenciaPanel.setLayout(new BoxLayout(tableConferenciaPanel, BoxLayout.PAGE_AXIS));     
        tableConf.setDefaultEditor(Object.class, null);
        model2 = new DefaultTableModel(new String[]{"Código"},0);        
        tableNotRegistred = new JTable(model2);    
        tableNotRegistredPanel.setLayout(new BoxLayout(tableNotRegistredPanel, BoxLayout.PAGE_AXIS));     
        tableNotRegistred.setDefaultEditor(Object.class, null);
    }
    public void update(){        
        updateTableNotRegistred();
        updateTableConferencia();
    }
    private void updateTableNotRegistred(){
        Main.cleanTable(tableNotRegistred);
        HashSet <String> auxSet=new HashSet();
        for(String code: idSetProductsNotRegistred){
            if(lojaDB.getColumnWithPrimaryKey("Mercadoria", code, "*")==null)
                lojaDB.addRow(new String[]{code}, tableNotRegistred);
            else{
                auxSet.add(code);
                idSetProductsConferred.add(code);
            }
        }
        for(String code: auxSet){
            idSetProductsNotRegistred.remove(code);
        }
    }
    private void updateTableConferencia(){
        Main.cleanTable(tableConf);
        ResultSet results =lojaDB.executeQuery("SELECT * FROM Mercadoria Where Status = \'no estoque\'");
        int width=100;
        if(results==null){
            System.out.println("Erro ao imprimir os dados, o query não foi executado corretamente.");
            return;
        }
        try{
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            int iId=-1;
            //System.out.println("Table: "+getTableName());
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String s = nameColumns[i];
                if(s.equals("ID_Mercadoria")) iId = i;   
                nameColumns[i] = Main.getOff_NameTable(nameColumns[i], "Mercadoria");
                nameColumns[i] = lojaDB.changeSomeNamesOfColumns(nameColumns[i]);
            }            
            tableConf.setModel(new DefaultTableModel(nameColumns,0));
            tableConf.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<tableConf.getColumnCount();i++){
                if(i==0 ) width=width/2;
                tableConf.getColumnModel().getColumn(i).setPreferredWidth(width);
                if(i==0) width=width*2;
            }
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                boolean flagConferred=false;
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=results.getString(i+1);
                    if(i==iId)
                        if(idSetProductsConferred.contains(columns[i]))
                            flagConferred=true;                            
                }
                if(flagConferred==false)
                    lojaDB.addRow(columns, tableConf);
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            exception.printStackTrace();
        } // end catch 
        int numCol=tableConf.getColumnCount();
        tableConferenciaPanel.setPreferredSize(new Dimension(numCol*width-width/2,300)); 
    } 
    public void updateSQL(MouseEvent evt){
        int row = tableConf.rowAtPoint(evt.getPoint());
        int col = tableConf.columnAtPoint(evt.getPoint());        
        lojaDB.updateSQL(tableConf, "Mercadoria", row, col);
        update();
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
        barCodeField = new javax.swing.JTextField();
        tableConferenciaPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tableNotRegistredPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        jLabel1.setText("Código de barras: ");

        barCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barCodeFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tableConferenciaPanelLayout = new javax.swing.GroupLayout(tableConferenciaPanel);
        tableConferenciaPanel.setLayout(tableConferenciaPanelLayout);
        tableConferenciaPanelLayout.setHorizontalGroup(
            tableConferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableConferenciaPanelLayout.setVerticalGroup(
            tableConferenciaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );

        jLabel2.setText("Mercadorias ainda não encontradas: ");
        jLabel2.setToolTipText("mercadorias possivelmente perdidas");

        javax.swing.GroupLayout tableNotRegistredPanelLayout = new javax.swing.GroupLayout(tableNotRegistredPanel);
        tableNotRegistredPanel.setLayout(tableNotRegistredPanelLayout);
        tableNotRegistredPanelLayout.setHorizontalGroup(
            tableNotRegistredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableNotRegistredPanelLayout.setVerticalGroup(
            tableNotRegistredPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setText("Códigos de mercadorias NÃO CADASTRADAS: ");
        jLabel3.setToolTipText("mercadorias guardadas numa pilha para posterior cadastro");

        jCheckBox1.setText("conferência incompleta");
        jCheckBox1.setToolTipText("Considera que o código nunca foi utilizado em outra mercadoria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableConferenciaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(barCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1)
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableNotRegistredPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(barCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableConferenciaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 111, Short.MAX_VALUE))
                    .addComponent(tableNotRegistredPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void verEvent(MouseEvent evt){
        int row = tableConf.rowAtPoint(evt.getPoint());
        int col = tableConf.columnAtPoint(evt.getPoint());        
        Main.verEvent(tableConf, row, col);        
    }
    private void barCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barCodeFieldActionPerformed
        String barCode =barCodeField.getText().trim();
        barCodeField.setText(barCode);
        if(barCode.equals("")){
            JOptionPane.showMessageDialog(barCodeField, "Preencha o código da mercadoria", "Aviso", JOptionPane.WARNING_MESSAGE);
            barCodeField.setText("");
            return;
        }
        if(Main.isIntegerValid(barCode)==false){
            JOptionPane.showMessageDialog(barCodeField, "O código deve conter apenas números!", "Aviso", JOptionPane.WARNING_MESSAGE);
            barCodeField.setText("");
            return;
        }
        ConsultaMercadoria consulta = PanelVenda.getConfirmMessage(lojaDB, barCodeField.getText().trim());
        if(consulta==null){
            if(idSetProductsNotRegistred.contains(barCode)==false){
                JOptionPane.showMessageDialog(barCodeField, "COLOQUE ESSE PRODUTO na pilha de produtos não cadastrados", "Produto não registrado!", JOptionPane.WARNING_MESSAGE);
                idSetProductsNotRegistred.add(barCode);            
                lojaDB.addRow(new String[]{barCode}, tableNotRegistred);
                barCodeField.setText("");
                return;
            }
            else{
                JOptionPane.showMessageDialog(barCodeField, "Esse produto é para estar na pilha de produtos não registrados", "Já está na pilha de produtos não registrados!", JOptionPane.WARNING_MESSAGE);
                barCodeField.setText("");
                return;
            }
        }        
        String messageWithProduct= consulta.confirmMessage;
        Object[] options = { "Mesmo produto do acima informado", "Produto diferente do acima" };            
        //Está no banco de dados, vamos conferir se o mesmo produto do Banco de dados
        if(jCheckBox1.isSelected()==false){
            String message1="O produto está registrado como: \n\n";
            message1+=messageWithProduct+"\n COMPARE o produto com o acima informado:";
            int reply1 = JOptionPane.showOptionDialog(barCodeField, message1, "Conferência de produto",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            //0 - mesmo produto      1 - Produto diferente
            if(reply1==1){
                JOptionPane.showMessageDialog(barCodeField, "TROQUE O CÓDIGO DE BARRAS e PASSE O PRODUTO NOVAMENTE!", "Código já utilizado em outro produto!", JOptionPane.WARNING_MESSAGE);
                barCodeField.setText("");
                return;
            }
            if(reply1!=0){
                barCodeField.setText("");
                return;
            }
            //---------------------------------------------------------- 
            //----------------------------------------------------------
            String status = consulta.mercadoriaMap.get("Status");
            if(status.equals("no estoque")==false){
                barCodeField.setText("");
                idSetProductsConferred.add(barCode);
                String query = "UPDATE Mercadoria SET Status= \'no estoque\' WHERE ID_Mercadoria = "+barCode;
                lojaDB.executeQuery(query);
                return; //nao precisa mais fazer nada, pois já o produto já foi adicionado ao idSetProductsConferred
            }
        }        
        //O produto está no DB e confere com o registrado!
        //Verificar se o status confere
        
        if(idSetProductsConferred.contains(barCode)){
            JOptionPane.showMessageDialog(barCodeField, "Produto já conferido!", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return; 
        }
        //está no bando de dados e "no estoque". Logo ou está no idSetProductsConferred não está nele.
        int indexInTable = Main.getIndexInTableWithCod(tableConf, barCode);
        if(indexInTable!=-1){       
            idSetProductsConferred.add(barCode);
            lojaDB.removeRow(indexInTable, tableConf);          
        }
        else
            System.out.println("Produto com código "+barCode+" não foi encontrado");
        barCodeField.setText("");
    }//GEN-LAST:event_barCodeFieldActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barCodeField;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel tableConferenciaPanel;
    private javax.swing.JPanel tableNotRegistredPanel;
    // End of variables declaration//GEN-END:variables
}
