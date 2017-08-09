package auxClasses;

import Main.Main;
import Panels.PanelCadastro;
import Panels.PanelVenda;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Andre Simao
 */
public class ConfirmaVendaPanel extends javax.swing.JPanel {
    Main lojaDB;
    PanelVenda panelVenda;
    String descricaoVenda;
    MyFrame frameNewClient;   
    double totDouble;
    public ConfirmaVendaPanel(Main lojaDB, PanelVenda panelVenda, String desc){
        initComponents();           
        this.lojaDB=lojaDB;
        this.panelVenda=panelVenda; 
        descricaoVenda=desc;
        String tot = getTot_a_pagar(desc);
        totDouble = Main.formatDoubleString(tot);
        total_a_pagarLabel.setText(tot);
        dinheiroField.setText(tot);
        lojaDB.setComboBox("Usuario", "Nome_Usuario", vendedorBox);
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clienteInBox);
        dinheiroLabel.setVisible(true);
        dinheiroField.setVisible(true);
        cartaoLabel.setVisible(false);
        cartaoField.setVisible(false);
        fiadoLabel.setVisible(false);
        fiadoField.setVisible(false);
        creditoDevolLabel.setVisible(false);
        saldoUtilizadoField.setVisible(false);
        saldoClientLabel.setVisible(false);
        changeClient("field");
        dinheiroCheckBox.setSelected(true);
        AuxFieldCreditDevol documentListener = new AuxFieldCreditDevol(this);
        dinheiroField.getDocument().addDocumentListener(documentListener);
        cartaoField.getDocument().addDocumentListener(documentListener);
        fiadoField.getDocument().addDocumentListener(documentListener);
        saldoUtilizadoField.getDocument().addDocumentListener(documentListener);
        clienteInBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                saldoClientLabel.setText("saldo: -");
                try{
                    String creditoPorCliente = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+clienteInBox.getSelectedItem()+"\'", "Saldo_Cliente");
                    if(Main.formatDoubleString(creditoPorCliente)!=0)
                        saldoClientLabel.setText("saldo: "+creditoPorCliente);
                }catch(Exception ex){}                
            }
        });        
        Main.setDataAndHourFields(dataField, horaField);
    }
    public void updateFaltaPagarLabel(){
        Double t=0.0, s=0.0, d=0.0, c=0.0, f=0.0;
        try{
            t = Main.formatDoubleString(total_a_pagarLabel.getText());
            s = Main.formatDoubleString(saldoUtilizadoField.getText());
            d = Main.formatDoubleString(dinheiroField.getText());
            c = Main.formatDoubleString(cartaoField.getText());
            f = Main.formatDoubleString(fiadoField.getText());   
        }
        catch(Exception e){                     
            faltaPagarValueLabel.setText("Há valores inválidos");
            return;
        }
        if(t<0||s<0||d<0||c<0||f<0)
            faltaPagarValueLabel.setText("Há valores inválidos");
        else{
            faltaPagarValueLabel.setText(Main.twoDig(t-s-d-c-f));
            if(t-s-d-c-f<0){                
                double troco = -1 *(t-s-d-c-f);
                if(troco < d && d !=0)
                    faltaPagarValueLabel.setText("Troco: "+Main.twoDig(troco));
                else{
                    faltaPagarValueLabel.setText("Valor a mais: "+Main.twoDig(troco));
                }
            }
        }
            
    }
    public String getTot_a_pagar(String desc){
        return desc.split("###")[4];
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        vendedorBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        dinheiroLabel = new javax.swing.JLabel();
        cartaoLabel = new javax.swing.JLabel();
        fiadoLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        creditoDevolLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dataField = new javax.swing.JTextField();
        horaField = new javax.swing.JTextField();
        clientInBoxLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        obsField = new javax.swing.JTextField();
        dinheiroCheckBox = new javax.swing.JCheckBox();
        cartaoCheckBox = new javax.swing.JCheckBox();
        fiadoCheckBox = new javax.swing.JCheckBox();
        creditoDevolCheckBox = new javax.swing.JCheckBox();
        dinheiroField = new javax.swing.JTextField();
        cartaoField = new javax.swing.JTextField();
        fiadoField = new javax.swing.JTextField();
        clientInFieldLabel = new javax.swing.JLabel();
        clienteInField = new javax.swing.JTextField();
        clienteInBox = new javax.swing.JComboBox<>();
        concluirButton = new javax.swing.JButton();
        total_a_pagarLabel = new javax.swing.JLabel();
        addClientButton = new javax.swing.JButton();
        faltaPagarLabel = new javax.swing.JLabel();
        faltaPagarValueLabel = new javax.swing.JLabel();
        saldoUtilizadoField = new javax.swing.JFormattedTextField();
        saldoClientLabel = new javax.swing.JLabel();

        jLabel1.setText("Vendedor: ");

        vendedorBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Forma de pagamento*: ");

        dinheiroLabel.setText("Valor em Dinheiro: ");

        cartaoLabel.setText("Valor no cartão: ");

        fiadoLabel.setText("Valor fiado: ");

        jLabel6.setText("Total a pagar: ");

        creditoDevolLabel.setText("Utilizar do saldo do cliente: ");

        jLabel8.setText("Data*: ");

        clientInBoxLabel.setText("Cliente*: ");

        jLabel10.setText("Hora*: ");

        jLabel11.setText("Observação: ");

        dinheiroCheckBox.setText("dinheiro");
        dinheiroCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dinheiroCheckBoxActionPerformed(evt);
            }
        });

        cartaoCheckBox.setText("cartão");
        cartaoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartaoCheckBoxActionPerformed(evt);
            }
        });

        fiadoCheckBox.setText("fiado");
        fiadoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiadoCheckBoxActionPerformed(evt);
            }
        });

        creditoDevolCheckBox.setText("saldo do cliente");
        creditoDevolCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditoDevolCheckBoxActionPerformed(evt);
            }
        });

        dinheiroField.setText("0.00");

        cartaoField.setText("0.00");

        fiadoField.setText("0.00");

        clientInFieldLabel.setText("Cliente: ");

        clienteInBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        concluirButton.setText("Concluir venda");
        concluirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concluirButtonActionPerformed(evt);
            }
        });

        total_a_pagarLabel.setText("0.00");

        addClientButton.setText("adicionar cliente ");
        addClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClientButtonActionPerformed(evt);
            }
        });

        faltaPagarLabel.setText("Falta pagar: ");

        faltaPagarValueLabel.setText("0.00");

        saldoUtilizadoField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        saldoUtilizadoField.setText("0.00");

        saldoClientLabel.setText("saldo: -");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientInFieldLabel)
                            .addComponent(clientInBoxLabel))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clienteInField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(clienteInBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saldoClientLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addClientButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dinheiroCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cartaoCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fiadoCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(creditoDevolCheckBox))
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(horaField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obsField, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(concluirButton)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(dinheiroLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cartaoLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(vendedorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(66, 66, 66))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(fiadoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(creditoDevolLabel)
                                            .addComponent(faltaPagarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(faltaPagarValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(saldoUtilizadoField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fiadoField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cartaoField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dinheiroField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(total_a_pagarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(133, 133, 133)))))
                        .addGap(0, 51, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vendedorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dinheiroCheckBox)
                    .addComponent(cartaoCheckBox)
                    .addComponent(fiadoCheckBox)
                    .addComponent(creditoDevolCheckBox))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(total_a_pagarLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dinheiroLabel)
                    .addComponent(dinheiroField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cartaoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cartaoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fiadoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fiadoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldoUtilizadoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creditoDevolLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(faltaPagarLabel)
                    .addComponent(faltaPagarValueLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(horaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientInBoxLabel)
                    .addComponent(clienteInBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addClientButton)
                    .addComponent(saldoClientLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientInFieldLabel)
                    .addComponent(clienteInField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(obsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(concluirButton)
                .addContainerGap(41, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void changeClient(String option){
        if(option.equals("box")){
            clientInBoxLabel.setVisible(true);
            clienteInBox.setVisible(true);            
            addClientButton.setVisible(true);
            clientInFieldLabel.setVisible(false);
            clienteInField.setVisible(false);
        }
        if(option.equals("field")){
            clientInBoxLabel.setVisible(false);
            clienteInBox.setVisible(false);            
            addClientButton.setVisible(false);
            clientInFieldLabel.setVisible(true);
            clienteInField.setVisible(true);
        }    
    }
    
    private void concluirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concluirButtonActionPerformed
        if(isValidSitution()==false)            
            return;
        String valor_em_dinheiro = getOffTroco();                
        String valor_em_cartao = Main.validateDoubleString(cartaoField.getText());
        String valor_fiado =Main.validateDoubleString(fiadoField.getText());
        String valor_com_saldo = Main.validateDoubleString(saldoUtilizadoField.getText());
        
        String data = dataField.getText();
        String hora = horaField.getText();        
        data = Main.formatStringToSql("Date", data);
        hora = Main.formatStringToSql("Time", hora);
        String client;
        if(fiadoCheckBox.isSelected()||creditoDevolCheckBox.isSelected()){
            client=Main.getChoosedComboBox(clienteInBox);
            if(fiadoCheckBox.isSelected())
                lojaDB.addSaldoCliente(client,"-"+valor_fiado);
            if(creditoDevolCheckBox.isSelected())
                lojaDB.addSaldoCliente(client,"-"+valor_com_saldo);
        }
        else
            client = clienteInField.getText();
        String vendedor = Main.getChoosedComboBox(vendedorBox);
        if(vendedorBox.getSelectedIndex()==0)
            vendedor="";
        String totalDiscount=getDiscount();
        String query;
        if(Main.formatDoubleString(totalDiscount)>0)
           query = "INSERT into Transacao(Tipo_Transacao, Vendedor, Dinheiro, Cartao, Fiado, Com_SaldoCliente, Data_Transacao, Hora_Transacao, "
               + "Descricao_Transacao, ID_Caixa, Observacao, Cliente) VALUES ("
                   + "\"venda\",\""+vendedor+"\","+ valor_em_dinheiro+","+valor_em_cartao+","+valor_fiado+","+valor_com_saldo+","+data+","+hora+",\""+
                   descricaoVenda+"\","+lojaDB.getOfCaixa("ID_Caixa")+",\"Desconto: "+Main.validateDoubleString(totalDiscount)
                   + "\",\""+client+"\")";
        else
           query = "INSERT into Transacao(Tipo_Transacao, Vendedor, Dinheiro, Cartao, Fiado, Com_SaldoCliente, Data_Transacao, Hora_Transacao, "
               + "Descricao_Transacao, ID_Caixa, Cliente) VALUES ("
                   + "\"venda\",\""+vendedor+"\","+ valor_em_dinheiro+","+valor_em_cartao+","+valor_fiado+","+valor_com_saldo+","+data+","+hora+",\""+
                   descricaoVenda+"\","+lojaDB.getOfCaixa("ID_Caixa")+ ",\""+client +"\")";
        lojaDB.executeQuery(query);
        
        updateStatusProducts(); 

        JOptionPane.showMessageDialog(concluirButton, "Venda realizada com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);            
        panelVenda.returnVisible();
    }//GEN-LAST:event_concluirButtonActionPerformed
    private String getOffTroco(){
        Double t=0.0, s=0.0, d=0.0, c=0.0, f=0.0;        
        t = Main.formatDoubleString(total_a_pagarLabel.getText());
        s = Main.formatDoubleString(saldoUtilizadoField.getText());
        d = Main.formatDoubleString(dinheiroField.getText());
        c = Main.formatDoubleString(cartaoField.getText());
        f = Main.formatDoubleString(fiadoField.getText());   
        Main.validateDoubleString(dinheiroField.getText());
        if(t-s-d-c-f<0){
            double troco = -1 *(t-s-d-c-f);
            if(troco < d){
                return Main.twoDig(d - troco);
            }
        }
        return Main.twoDig(d);
    }
    private void updateStatusProducts(){
        Main.p(descricaoVenda);
        String split3[]= descricaoVenda.split("###");
        String products = split3[1];
        String[] split2 = products.split("##");
        int len2=split2.length;
        String barCode;
        for(int i=0;i<len2;i++){
            String product = split2[i];
            String elemOfProduct[]=product.split("#");            
            barCode = elemOfProduct[0];
            String query1 = "UPDATE Mercadoria SET Status= \'vendido\' WHERE ID_Mercadoria = "+barCode;
            lojaDB.executeQuery(query1);  
        }
    }
    private String getDiscount(){
        String split3[]= descricaoVenda.split("###");
        return split3[3];
    }
    private boolean isValidSitution(){
        String data = dataField.getText();
        String hora = horaField.getText();
        if(Main.isDateValid(data)==false ){
            JOptionPane.showMessageDialog(null, "Data inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return false;
        }    
        if(Main.isTimeValid(hora)==false){
            JOptionPane.showMessageDialog(null, "Hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);            
            return false;
        }           
        if(clienteInBox.getSelectedIndex()==0){
            if(fiadoCheckBox.isSelected()){
                JOptionPane.showMessageDialog(null, "Selecione um cliente para cobrança fiado", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }
            if(creditoDevolCheckBox.isSelected()){
                JOptionPane.showMessageDialog(null, "Selecione um cliente para utilizar seus créditos por devolução", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }                
        }
        if(Main.isDoubleValid(dinheiroField.getText())==false || Main.isDoubleValid(cartaoField.getText())==false){
            JOptionPane.showMessageDialog(null, "Digite valores válidos", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }
        if(Main.isDoubleValid(saldoUtilizadoField.getText())==false || Main.isDoubleValid(fiadoField.getText())==false){
            JOptionPane.showMessageDialog(null, "Digite valores válidos", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }        
        if(isSaldoUtilizadoValido(saldoUtilizadoField.getText())==false)
            return false;
        double d = Main.formatDoubleString(dinheiroField.getText());
        double c = Main.formatDoubleString(cartaoField.getText());
        double f = Main.formatDoubleString(fiadoField.getText());
        double s = Main.formatDoubleString(saldoUtilizadoField.getText());
        if(totDouble-s-d-c-f<0){
            double troco = -1 *(totDouble-s-d-c-f);
            if(troco >= d || d==0){
                JOptionPane.showMessageDialog(null, "Não se pode dar troco para valores pagos em cartão ou em saldo do cliente", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }
        }
        else if(Math.abs(totDouble-s -c-f-d)>0.05){
            JOptionPane.showMessageDialog(null, "Os valores não somam o necessário para a compra", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }  
        if(d<0||c<0||f<0||s<0){
            JOptionPane.showMessageDialog(null, "Os valores devem ser positivos", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }      
        return true;       
    }
    private boolean isSaldoUtilizadoValido(String saldoUtilizadoS){
        String saldoDoClienteS = lojaDB.getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+clienteInBox.getSelectedItem()+"\'", "Saldo_Cliente");
        double saldoDoCliente = Main.formatDoubleString(saldoDoClienteS);
        double saldoUtilizado = Main.formatDoubleString(saldoUtilizadoS);
        if(fiadoCheckBox.isSelected())
            return true;
        if(saldoDoCliente==0 && saldoUtilizado>0){
            JOptionPane.showMessageDialog(null, "O cliente não possui saldo", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }            
        if(saldoUtilizado>saldoDoCliente){
            if(saldoDoCliente<0)
                JOptionPane.showMessageDialog(null, "O cliente não possui créditos por devoluções, pois está devendo. Selecione a opção fiado", "Aviso", JOptionPane.WARNING_MESSAGE);   
            else
                JOptionPane.showMessageDialog(null, "O cliente possui apenas "+saldoDoClienteS+" de créditos por devoluções", "Aviso", JOptionPane.WARNING_MESSAGE);   
            return false;
        }         
        return true;
    }
    private void fiadoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiadoCheckBoxActionPerformed
        if(evt!=null && fiadoCheckBox.isSelected())
            changeClient("box");
        if(creditoDevolCheckBox.isSelected()){
            creditoDevolCheckBox.setSelected(false);
            setCreditoDevol(false);
        }
        if(fiadoCheckBox.isSelected())
            setFiado(true);
        else
            setFiado(false);
        if(fiadoCheckBox.isSelected()==false && creditoDevolCheckBox.isSelected()==false)
            changeClient("field");
    }//GEN-LAST:event_fiadoCheckBoxActionPerformed
    private void creditoDevolCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditoDevolCheckBoxActionPerformed
        if(evt!=null && creditoDevolCheckBox.isSelected())
            changeClient("box");
            if(fiadoCheckBox.isSelected()){
                fiadoCheckBox.setSelected(false);
                setFiado(false);
            }
        if(creditoDevolCheckBox.isSelected())
            setCreditoDevol(true);
        else
            setCreditoDevol(false);
        if(fiadoCheckBox.isSelected()==false && creditoDevolCheckBox.isSelected()==false)
            changeClient("field");
    }//GEN-LAST:event_creditoDevolCheckBoxActionPerformed
    private void setCreditoDevol(boolean flag){
        saldoUtilizadoField.setVisible(flag);
        creditoDevolLabel.setVisible(flag);    
        saldoUtilizadoField.setText("0.00");        
        saldoClientLabel.setVisible(flag);
        updateFaltaPagarLabel();
    }
    private void setFiado(boolean flag){
        fiadoField.setVisible(flag);
        fiadoLabel.setVisible(flag);
        fiadoField.setText("0.00");               
        saldoClientLabel.setVisible(flag);
        updateFaltaPagarLabel();
    }
    private void dinheiroCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinheiroCheckBoxActionPerformed
        updateFaltaPagarLabel();
        if(dinheiroCheckBox.isSelected()){
            dinheiroField.setVisible(true);
            dinheiroLabel.setVisible(true);
            dinheiroField.setText("0.00");
        }
        else{
            dinheiroField.setVisible(false);
            dinheiroLabel.setVisible(false);
            dinheiroField.setText("0.00");
        } 
    }//GEN-LAST:event_dinheiroCheckBoxActionPerformed
    private void cartaoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartaoCheckBoxActionPerformed
        updateFaltaPagarLabel();
        if(cartaoCheckBox.isSelected()){
            cartaoField.setVisible(true);
            cartaoLabel.setVisible(true);
            cartaoField.setText("0.00");
        }
        else{
            cartaoField.setVisible(false);
            cartaoLabel.setVisible(false);
            cartaoField.setText("0.00");
        } 
    }//GEN-LAST:event_cartaoCheckBoxActionPerformed
    private void addClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClientButtonActionPerformed
        PanelCadastro cadastroClient = new PanelCadastro(lojaDB, this);        
        lojaDB.setConfirmaVendaPanelVisible(false);
        frameNewClient = new MyFrame();
        frameNewClient.add(cadastroClient);
        frameNewClient.setVisible(true);
        frameNewClient.setSize(new Dimension(500, 700));
        frameNewClient.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameNewClient.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                lojaDB.setConfirmaVendaPanelVisible(true);
                frameNewClient.exitProcedure();
            }
        });
    
    }//GEN-LAST:event_addClientButtonActionPerformed
    public void returnVisible(String newClient){
        lojaDB.setConfirmaVendaPanelVisible(true);
        frameNewClient.setEnabled(false);
        frameNewClient.dispose();        
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clienteInBox);
        clienteInBox.setSelectedItem(newClient);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addClientButton;
    private javax.swing.JCheckBox cartaoCheckBox;
    private javax.swing.JTextField cartaoField;
    private javax.swing.JLabel cartaoLabel;
    private javax.swing.JLabel clientInBoxLabel;
    private javax.swing.JLabel clientInFieldLabel;
    private javax.swing.JComboBox<String> clienteInBox;
    private javax.swing.JTextField clienteInField;
    private javax.swing.JButton concluirButton;
    private javax.swing.JCheckBox creditoDevolCheckBox;
    private javax.swing.JLabel creditoDevolLabel;
    private javax.swing.JTextField dataField;
    private javax.swing.JCheckBox dinheiroCheckBox;
    private javax.swing.JTextField dinheiroField;
    private javax.swing.JLabel dinheiroLabel;
    private javax.swing.JLabel faltaPagarLabel;
    private javax.swing.JLabel faltaPagarValueLabel;
    private javax.swing.JCheckBox fiadoCheckBox;
    private javax.swing.JTextField fiadoField;
    private javax.swing.JLabel fiadoLabel;
    private javax.swing.JTextField horaField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField obsField;
    private javax.swing.JLabel saldoClientLabel;
    private javax.swing.JFormattedTextField saldoUtilizadoField;
    private javax.swing.JLabel total_a_pagarLabel;
    private javax.swing.JComboBox<String> vendedorBox;
    // End of variables declaration//GEN-END:variables

}
