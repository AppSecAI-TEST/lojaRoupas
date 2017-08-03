package auxPanels;

import Main.Main;
import Panels.PanelCadastro;
import Panels.PanelVenda;
import java.awt.Dimension;
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
    public ConfirmaVendaPanel(Main lojaDB, PanelVenda panelVenda, String desc){
        initComponents();        
        this.lojaDB=lojaDB;
        this.panelVenda=panelVenda; 
        descricaoVenda=desc;
        total_a_pagarLabel.setText(getTot_a_pagar(desc));
        lojaDB.setComboBox("Usuario", "Nome_Usuario", vendedorBox);
        lojaDB.setComboBox("Cliente", "Nome_Cliente", clienteInBox);
        dinheiroLabel.setVisible(true);
        dinheiroField.setVisible(true);
        cartaoLabel.setVisible(false);
        cartaoField.setVisible(false);
        fiadoLabel.setVisible(false);
        fiadoField.setVisible(false);
        creditoDevolLabel.setVisible(false);
        creditoDevolField.setVisible(false);
        changeClient("field");
        dinheiroCheckBox.setSelected(true);
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
        creditoDevolField = new javax.swing.JTextField();
        clientInFieldLabel = new javax.swing.JLabel();
        clienteInField = new javax.swing.JTextField();
        clienteInBox = new javax.swing.JComboBox<>();
        concluirButton = new javax.swing.JButton();
        total_a_pagarLabel = new javax.swing.JLabel();
        addClientButton = new javax.swing.JButton();

        jLabel1.setText("Vendedor: ");

        vendedorBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Forma de pagamento: ");

        dinheiroLabel.setText("Valor em Dinheiro: ");

        cartaoLabel.setText("Valor no cartão: ");

        fiadoLabel.setText("Valor fiado: ");

        jLabel6.setText("Total a pagar: ");

        creditoDevolLabel.setText("Valor utilizando o crédito por devolução do cliente: ");

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

        creditoDevolCheckBox.setText("crédito por devolução");
        creditoDevolCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditoDevolCheckBoxActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(vendedorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(creditoDevolCheckBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total_a_pagarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(creditoDevolLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(creditoDevolField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(horaField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dataField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clientInFieldLabel)
                                    .addComponent(clientInBoxLabel))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(clienteInBox, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(addClientButton))
                                    .addComponent(clienteInField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(obsField, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(concluirButton)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dinheiroLabel)
                                    .addComponent(cartaoLabel)
                                    .addComponent(fiadoLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fiadoField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cartaoField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dinheiroField, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 28, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vendedorBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(total_a_pagarLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dinheiroCheckBox)
                    .addComponent(cartaoCheckBox)
                    .addComponent(fiadoCheckBox)
                    .addComponent(creditoDevolCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dinheiroField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dinheiroLabel))
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
                    .addComponent(creditoDevolLabel)
                    .addComponent(creditoDevolField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(addClientButton))
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
                .addContainerGap(19, Short.MAX_VALUE))
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
//        if(isValidSitution()==false)            
//            return;
//        String data = dataField.getText();
//        String hora = horaField.getText();
//        String client = clientField.getText();
//        data = Main.formatStringToSql("Date", data);
//        hora = Main.formatStringToSql("Time", hora);
//        int answ = JOptionPane.showConfirmDialog(concluirVendaButton, "Tem certeza que deseja concluir a venda?", "Tela de confirmação", JOptionPane.OK_CANCEL_OPTION);
//            if(answ!=0)
//                return;            
//        int len = tableVenda.getRowCount();
//        double subTotalValue=0;
//        double totalDiscount=0;
//        String descricao="venda###";
//        for(int i=0;i<len;i++){
//            String barCode = elemOfTable(i, 0);
//            String query1 = "UPDATE Mercadoria SET Status= \'vendido\' WHERE ID_Mercadoria = "+barCode;
//            lojaDB.executeQuery(query1);                
//            descricao+=elemOfTable(i, 0)+"#";            
//            descricao+=elemOfTable(i, 1)+"#";
//            descricao+=elemOfTable(i, 2)+"#";
//            descricao+=elemOfTable(i, 3)+"#";
//            descricao+=elemOfTable(i, 4)+"#";
//            descricao+=elemOfTable(i, 5)+"##";
//            subTotalValue+=Double.parseDouble(tableVenda.getValueAt(i, 5).toString());
//            totalDiscount+=Double.parseDouble(tableVenda.getValueAt(i, 4).toString());
//        }
//        descricao+="#";
//        String subTotalValueSt=Double.toString(subTotalValue);
//        String totalDiscountSt=Double.toString(totalDiscount);
//        String totalValueSt=Double.toString(subTotalValue-totalDiscount);
//        descricao+=subTotalValueSt+"###";
//        descricao+=totalDiscountSt+"###";
//        descricao+=totalValueSt;
//        
//        String query;
//        if(totalDiscount>0)
//            query = "INSERT into Transacao(Tipo_de_Transacao, Valor_em_Dinheiro, Data_Transacao, Hora_Transacao, "
//                + "Descricao_Transacao, ID_Caixa, Observacao, Cliente) VALUES ("
//                    + "\"venda\","+ totalValueSt+","+data+","+hora+",\""+
//                    descricao+"\","+lojaDB.getOfCaixa("ID_Caixa")+",\"Desconto: "+totalDiscountSt
//                    + "\",\""+client+"\")";
//        else
//            query = "INSERT into Transacao(Tipo_de_Transacao, Valor_em_Dinheiro, Data_Transacao, Hora_Transacao, "
//                + "Descricao_Transacao, ID_Caixa, Cliente) VALUES ("
//                    + "\"venda\","+ totalValueSt+","+data+","+hora+",\""+
//                    descricao+"\","+lojaDB.getOfCaixa("ID_Caixa")+ ",\""+client +"\")";
//        lojaDB.executeQuery(query);
//        JOptionPane.showMessageDialog(concluirVendaButton, "Venda realizada com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);            
//        clean();    

//-------------------------------------------

//        REALIZAR ESSAS ATUALIZACOES NO DB:
//        if(totalDiscount>0)
//            query = "INSERT into Transacao(Tipo_de_Transacao, Valor_em_Dinheiro, Data_Transacao, Hora_Transacao, "
//                + "Descricao_Transacao, ID_Caixa, Observacao, Cliente) VALUES ("
//                    + "\"venda\","+ totalValueSt+","+data+","+hora+",\""+
//                    descricao+"\","+lojaDB.getOfCaixa("ID_Caixa")+",\"Desconto: "+totalDiscountSt
//                    + "\",\""+client+"\")";
//        else
//            query = "INSERT into Transacao(Tipo_de_Transacao, Valor_em_Dinheiro, Data_Transacao, Hora_Transacao, "
//                + "Descricao_Transacao, ID_Caixa, Cliente) VALUES ("
//                    + "\"venda\","+ totalValueSt+","+data+","+hora+",\""+
//                    descricao+"\","+lojaDB.getOfCaixa("ID_Caixa")+ ",\""+client +"\")";
//        lojaDB.executeQuery(query);
//            String barCode = elemOfTable(i, 0);
//            String query1 = "UPDATE Mercadoria SET Status= \'vendido\' WHERE ID_Mercadoria = "+barCode;
//            lojaDB.executeQuery(query1);                



        panelVenda.returnVisible();
    }//GEN-LAST:event_concluirButtonActionPerformed

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
        creditoDevolField.setVisible(flag);
        creditoDevolLabel.setVisible(flag);
    }
    private void setFiado(boolean flag){
        fiadoField.setVisible(flag);
        fiadoLabel.setVisible(flag);
    }
    private void dinheiroCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dinheiroCheckBoxActionPerformed
        if(dinheiroCheckBox.isSelected()){
            dinheiroField.setVisible(true);
            dinheiroLabel.setVisible(true);
        }
        else{
            dinheiroField.setVisible(false);
            dinheiroLabel.setVisible(false);
        } 
    }//GEN-LAST:event_dinheiroCheckBoxActionPerformed

    private void cartaoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartaoCheckBoxActionPerformed
        if(cartaoCheckBox.isSelected()){
            cartaoField.setVisible(true);
            cartaoLabel.setVisible(true);
        }
        else{
            cartaoField.setVisible(false);
            cartaoLabel.setVisible(false);
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
    private javax.swing.JTextField creditoDevolField;
    private javax.swing.JLabel creditoDevolLabel;
    private javax.swing.JTextField dataField;
    private javax.swing.JCheckBox dinheiroCheckBox;
    private javax.swing.JTextField dinheiroField;
    private javax.swing.JLabel dinheiroLabel;
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
    private javax.swing.JLabel total_a_pagarLabel;
    private javax.swing.JComboBox<String> vendedorBox;
    // End of variables declaration//GEN-END:variables
}
