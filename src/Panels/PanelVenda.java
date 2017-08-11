package Panels;

import auxClasses.ConfirmaVendaPanel;
import Main.Main;
import auxClasses.MyFrame;
import auxClasses.PopClickListener;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelVenda extends javax.swing.JPanel {
    JTable tableVenda;
    Main lojaDB;    
    MyFrame frameConfirma;
    public PanelVenda(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();          
        createTable(new String[]{"Código", "Tipo","Descrição", "Tamanho", "Desconto", "Valor"}); 
        JScrollPane scrollTable=new JScrollPane(tableVenda);
        tableVendasPanel.removeAll();
        tableVendasPanel.add(scrollTable);         
        tableVenda.addMouseListener(new PopClickListener(this, tableVenda));
    }
    public void setConfirmaVendaPanelVisible(boolean flag){
        if(frameConfirma!=null)
            frameConfirma.setVisible(flag);
    }
    public void removeEvent(MouseEvent evt){
        int row = tableVenda.rowAtPoint(evt.getPoint());
        int col = tableVenda.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) 
        {            
            updateResultLabels(row, null, "remove");            
            int colOfDiscount = Main.getIndexColumnWithColumnName(tableVenda, "Desconto");
            Double descontoAntigo = Double.parseDouble(tableVenda.getValueAt(row, colOfDiscount).toString());
            updateResultLabels(row, -1*descontoAntigo, "discount");                
            lojaDB.removeRow(row, tableVenda);
        } 
    }
    public void setStatusCaixaLabel(boolean flag){
        if(flag==false)
            statusCaixaLabel.setText("CAIXA FECHADO");
        else
            statusCaixaLabel.setText("CAIXA ABERTO");
    }
    public void discountEvent(MouseEvent evt){
        int row = tableVenda.rowAtPoint(evt.getPoint());
        int col = tableVenda.columnAtPoint(evt.getPoint());
        Double discount=0.0;
        if (row >= 0 && col >= 0){        
            int colOfValue = Main.getIndexColumnWithColumnName(tableVenda, "Valor");
            Double value = Double.parseDouble(tableVenda.getValueAt(row, colOfValue).toString());
            String discountString = JOptionPane.showInputDialog("Digite o valor do desconto ou a porcentagem do desconto. (Ex: 20 ou 10%)");
            if(Main.isDoubleValid(discountString.replace("%", ""))==false)
                return;
            discount = getDiscount(discountString, value);            
            if(discount!=null && discount<=value)
            {                
                int colOfDiscount = Main.getIndexColumnWithColumnName(tableVenda, "Desconto");
                Double descontoAntigo = Double.parseDouble(tableVenda.getValueAt(row, colOfDiscount).toString());
                tableVenda.setValueAt(Main.twoDig(discount), row, colOfDiscount);
                updateResultLabels(row, -1*descontoAntigo, "discount");
                updateResultLabels(row, discount, "discount");
            }            
            else{
                JOptionPane.showMessageDialog(tableVenda, "Digite um valor válido e menor que o valor do produto!", "Aviso", JOptionPane.WARNING_MESSAGE);            
                return;
            }
        } 
    }
    public Double getDiscount(String discountString, Double value){
        if(discountString==null){
            return null;
        }
        discountString = discountString.trim();
        Double discount;
        try{
            if(discountString.contains("%")){
                discount = Double.parseDouble(discountString.replace("%", ""));
                discount *= value/100;
            }                
            else{
                discount = Double.parseDouble(discountString);
            }
                
        }catch(Exception e){
            discount = null;
        }
        return discount;
    }
    private void updateResultLabels(int row, Double preco, String operation){
        double subTotal = Double.parseDouble(subTotalLabel.getText());
        double total = Double.parseDouble(totalLabel.getText());
        double discount = Double.parseDouble(discountLabel.getText());
        //--------------------------------------------------------------------------        
        int colOfValue = Main.getIndexColumnWithColumnName(tableVenda, "Valor");
        double value = Double.parseDouble(tableVenda.getValueAt(row, colOfValue).toString());        
        switch(operation){
            case "remove":
                subTotalLabel.setText(Main.twoDig(subTotal-value));
                totalLabel.setText(Main.twoDig(total-value));
                break;
            case "add":
                subTotalLabel.setText(Main.twoDig(subTotal+preco));
                totalLabel.setText(Main.twoDig(total+preco));
                break;
            case "discount":
                discountLabel.setText(Main.twoDig(discount+preco));
                totalLabel.setText(Main.twoDig(total-preco));
                break;                
        }
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        tableVenda = new JTable(model);        
        tableVendasPanel.setLayout(new BoxLayout(tableVendasPanel, BoxLayout.PAGE_AXIS));     
        tableVenda.setDefaultEditor(Object.class, null);
    }
    private void productConfirmed(HashMap<String, String> mapProduct){
        String cod=mapProduct.get("Código da mercadoria");
        String tip=mapProduct.get("Tipo");
        String descricao=mapProduct.get("Descrição");
        String est=mapProduct.get("Estimativa do tamanho");
        String prec=mapProduct.get("Preço");
        String[] row=new String[]{cod, tip, descricao, est, "0.00",  prec};
        lojaDB.addRow(row, tableVenda);
        updateResultLabels(0, Double.parseDouble(prec), "add");
    }
    public static ConsultaMercadoria getConfirmMessage(Main lojaDB, String barCode){
        //System.out.println(barCode);
        
        String message="";
        HashMap <String, String> mercadoriaMap=new HashMap();
        
        try{
            ResultSet results = lojaDB.executeQuery("Select * from Mercadoria where ID_Mercadoria = "+barCode);
            if(results==null){
                System.out.println("Nenhum resultado da busca foi encontrado.");
                return null;
            }
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String nameColumn[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumn[i]=metaData.getColumnName(i+1);               
            }      
            while (results.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    nameColumn[i]=changeNameColumn(nameColumn[i]);
                    if(nameColumn[i]!=null)
                    {
                        mercadoriaMap.put(nameColumn[i], results.getString(i+1));
                        if(nameColumn[i].equals("Status")==false)
                            message+="   "+nameColumn[i]+": "+results.getString(i+1)+"\n";
                    }
                }                
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a janela de confirmação");
            exception.printStackTrace();
        } // end catch        
        if(message.equals("")) return null;
        ConsultaMercadoria consulta=new ConsultaMercadoria();
        consulta.confirmMessage=message;
        consulta.mercadoriaMap=mercadoriaMap;
        return consulta;
    }
    private static String changeNameColumn(String nameColumn){
        if(nameColumn==null)
            return null;
        switch(nameColumn){
            case "ID_Mercadoria":
                return "Código da mercadoria";
            case "Descricao_Mercadoria":
                return "Descrição";
            case "TipoMercadoria":
                return "Tipo";
            case "Tamanho_est":
                return "Estimativa do tamanho";
            case "Preco_Merc":
                return "Preço";
            case "Status":
                return "Status";
            default:
                return null;
        }
    }    
    public void update(){
        barCodeField.requestFocus();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        barCodeField = new javax.swing.JTextField();
        tableVendasPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        concluirVendaButton = new javax.swing.JButton();
        subTotalLabel = new javax.swing.JLabel();
        discountLabel = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        statusCaixaLabel = new javax.swing.JLabel();

        jLabel2.setText("Código de barras: ");

        barCodeField.setToolTipText("código de barras da mercadoria a ser vendida");
        barCodeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barCodeFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tableVendasPanelLayout = new javax.swing.GroupLayout(tableVendasPanel);
        tableVendasPanel.setLayout(tableVendasPanelLayout);
        tableVendasPanelLayout.setHorizontalGroup(
            tableVendasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        tableVendasPanelLayout.setVerticalGroup(
            tableVendasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );

        jLabel1.setText("Subtotal: ");

        jLabel3.setText("Desconto: ");

        jLabel4.setText("Total: ");

        concluirVendaButton.setText("Finalizar Venda  ");
        concluirVendaButton.setToolTipText("O desconto deve ser dado ANTES de apertar esse botão");
        concluirVendaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concluirVendaButtonActionPerformed(evt);
            }
        });

        subTotalLabel.setText("0");

        discountLabel.setText("0");

        totalLabel.setText("0");

        statusCaixaLabel.setText("Status Caixa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableVendasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barCodeField, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(statusCaixaLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(concluirVendaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(subTotalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(discountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(barCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusCaixaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableVendasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(concluirVendaButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(subTotalLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(discountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(totalLabel))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void barCodeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barCodeFieldActionPerformed
        if(lojaDB.caixaAberto==false){
            JOptionPane.showMessageDialog(barCodeField, "Antes da venda, deve-se abrir o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return;
        }    
        barCodeField.setText(barCodeField.getText().trim());
        if(barCodeField.getText().equals("")){
            JOptionPane.showMessageDialog(barCodeField, "Preencha o código da mercadoria", "Aviso", JOptionPane.WARNING_MESSAGE);            
            barCodeField.setText("");
            return;
        }       
        if(Main.isIntegerValid(barCodeField.getText())==false){
            JOptionPane.showMessageDialog(barCodeField, "O código deve conter apenas números!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            barCodeField.setText("");
            return;
        }          
        if(hasCodeInTable(tableVenda, barCodeField.getText())){
            JOptionPane.showMessageDialog(barCodeField, "Esse produto já foi registrado nessa venda!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            barCodeField.setText("");
            return;
        }     
        ConsultaMercadoria consulta = getConfirmMessage(lojaDB, barCodeField.getText().trim());
        if(consulta==null){
            JOptionPane.showMessageDialog(barCodeField, "Não há produto registrado com esse código!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            barCodeField.setText("");
            return;
        }
        String status = consulta.mercadoriaMap.get("Status");        
        Object[] options = { "Confirmar", "Não confirma" };
        if(status.equals("no estoque")==false){
            String message1="O produto está registrado como \'"+status+"\' , "
                    + "confirmar ele na venda mesmo assim?";
            int reply1 = JOptionPane.showOptionDialog(barCodeField, message1, "Produto com status inválido",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            //0 - confirm      1 - nao confirma
            if(reply1==1){
                System.out.println("Mercadoria não confirmada");
                barCodeField.setText("");
                return;
            }                     
        }
        String message= consulta.confirmMessage; 
        int reply = JOptionPane.showOptionDialog(null, message, "Conferência de Produto",
            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        //0 - confirm      1 - nao confirma
        if(reply==1)
            System.out.println("Mercadoria não confirmada");
        else //reply=0
            productConfirmed(consulta.mercadoriaMap);            
        barCodeField.setText("");
    }//GEN-LAST:event_barCodeFieldActionPerformed
    public static boolean hasCodeInTable(JTable table, String code){
        int len = table.getRowCount();        
        int colOfKey= Main.getIndexColumnWithColumnName(table, "Código");
        for(int i=0;i<len;i++){
            if(Main.elemOfTable(table, i, colOfKey).equals(code))
                return true;
        }
        return false;
    }
    private void concluirVendaButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        if(isValidSitution()==false)            
            return;
        int answ = JOptionPane.showConfirmDialog(concluirVendaButton, "Tem certeza que deseja concluir a venda?", "Tela de confirmação", JOptionPane.OK_CANCEL_OPTION);
        if(answ!=0)
            return; 
        String descricaoVenda = mountDescricaoVenda(); 
        ConfirmaVendaPanel confirmaVendaPanel = new ConfirmaVendaPanel(lojaDB, this, descricaoVenda);        
        lojaDB.setConfirmaVendaPanelVisible(false);
        lojaDB.setTabbedPaneVisible(false);
        frameConfirma = new MyFrame();
        frameConfirma.add(confirmaVendaPanel);
        frameConfirma.setVisible(true);
        frameConfirma.setSize(new Dimension(500, 700));
        frameConfirma.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameConfirma.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                lojaDB.setTabbedPaneVisible(true);
                frameConfirma.exitProcedure();
            }
        });
                        
        
    }
    public String mountDescricaoVenda(){
        int len = tableVenda.getRowCount();
        double subTotalValue=0;
        double totalDiscount=0;
        String descricao="venda###";
        int colOfKey = Main.getIndexColumnWithColumnName(tableVenda, "Código");
        int colOfType = Main.getIndexColumnWithColumnName(tableVenda, "Tipo");
        int colOfDesc = Main.getIndexColumnWithColumnName(tableVenda, "Descrição");
        int colOfSize = Main.getIndexColumnWithColumnName(tableVenda, "Tamanho");
        int colOfDiscount = Main.getIndexColumnWithColumnName(tableVenda, "Desconto");
        int colOfValue = Main.getIndexColumnWithColumnName(tableVenda, "Valor");
        for(int i=0;i<len;i++){            
            descricao+=Main.elemOfTable(tableVenda, i, colOfKey)+"#";            
            descricao+=Main.elemOfTable(tableVenda, i, colOfType)+"#";
            descricao+=Main.elemOfTable(tableVenda, i, colOfDesc)+"#";
            descricao+=Main.elemOfTable(tableVenda, i, colOfSize)+"#";
            descricao+=Main.elemOfTable(tableVenda, i, colOfDiscount)+"#";
            descricao+=Main.elemOfTable(tableVenda, i, colOfValue)+"##";            
            subTotalValue+=Double.parseDouble(tableVenda.getValueAt(i, colOfValue).toString());
            totalDiscount+=Double.parseDouble(tableVenda.getValueAt(i, colOfDiscount).toString());
        }
        descricao+="#";
        String subTotalValueSt=Double.toString(subTotalValue);
        String totalDiscountSt=Double.toString(totalDiscount);
        String totalValueSt=Double.toString(subTotalValue-totalDiscount);
        descricao+=subTotalValueSt+"###";
        descricao+=totalDiscountSt+"###";
        descricao+=totalValueSt;
        return descricao;
    }
    public void returnVisible(){
        lojaDB.setTabbedPaneVisible(true);
        frameConfirma.setEnabled(false);
        frameConfirma.dispose();        
        clean();  
    }    
    private void clean(){
        Main.cleanTable(tableVenda);
//        horaField.setText("");
//        clientField.setText("");
        subTotalLabel.setText("0");
        totalLabel.setText("0");
        this.discountLabel.setText("0");
    }
    public void verEvent(MouseEvent evt){
        int row = tableVenda.rowAtPoint(evt.getPoint());
        int col = tableVenda.columnAtPoint(evt.getPoint());        
        Main.verEvent(tableVenda, row, col);        
    }
    public static String createStringOfTransaction(String root){
        String split3[]= root.split("###");
        String typeOfTransaction=split3[0];
        if(typeOfTransaction.equals("venda"))
            return createStringOfVenda(split3);
        return null;
    }
    public static String createStringOfVenda(String split3[]){        
        String descricao=split3[0]+":\n";
        String products = split3[1];
        String[] split2 = products.split("##");
        int len2=split2.length;
        for(int i=0;i<len2;i++){
            String product = split2[i];
            String elemOfProduct[]=product.split("#");
            descricao+="   Código da mercadoria: "+ elemOfProduct[0]+"\n";
            descricao+="      Tipo: "+ elemOfProduct[1]+"\n";
            descricao+="      Descrição: "+ elemOfProduct[2]+"\n";
            descricao+="      Tamanho: "+ elemOfProduct[3]+"\n";
            descricao+="      Desconto: "+ Main.validateDoubleString(elemOfProduct[4])+"\n";
            descricao+="      Valor: "+ elemOfProduct[5]+"\n\n";
        }
        descricao+="SubTotal: "+ split3[2]+"\n";
        descricao+="Desconto: "+ split3[3]+"\n";
        descricao+="Total:    "+ split3[4]+"\n";
        return descricao;        
    }    
    private boolean isValidSitution(){
        if(lojaDB.caixaAberto==false){
            JOptionPane.showMessageDialog(concluirVendaButton, "Abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return false;
        }    
        if(tableVenda.getRowCount()==0){
            JOptionPane.showMessageDialog(concluirVendaButton, "Adicione pelo menos um produto na venda!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return false;
        }
        return true;
            
    } 
    static class ConsultaMercadoria{
        String confirmMessage;
        HashMap <String, String> mercadoriaMap;
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barCodeField;
    private javax.swing.JButton concluirVendaButton;
    private javax.swing.JLabel discountLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel statusCaixaLabel;
    private javax.swing.JLabel subTotalLabel;
    private javax.swing.JPanel tableVendasPanel;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables
        
}
