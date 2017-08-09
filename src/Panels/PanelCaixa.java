/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Main.Main;
import auxClasses.AbrirCaixaPanel;
import auxClasses.AddOrRemovePanel;
import auxClasses.FechamentoCaixaPanel;
import auxClasses.PopClickListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class PanelCaixa extends javax.swing.JPanel {

    JTable tableCaixa;
    Main lojaDB;

    public PanelCaixa(Main lojaDB) {
        this.lojaDB = lojaDB;
        initComponents();
        createTable(new String[]{"ID do Caixa", "Tipo de Transacao", "ID da transacao", "Descricao", "Valor total"});
        JScrollPane scrollTable = new JScrollPane(tableCaixa);
        tableCaixaPanel.removeAll();
        tableCaixaPanel.add(scrollTable);
        somenteCaixaAtualCheckBox.setSelected(true);
        update();
    }

    private void resetLabels() {
        abrirFecharCaixaButton.setText("Abrir caixa");
        dataAberturaLabel.setText("");
        horaAberturaLabel.setText("");
        adicionadoLabel.setText("");
        retiradoLabel.setText("");
        vendasDevLabel.setText("");
        idCaixaLabel.setText("");
    }

    private void setLabels(Double valueVendasDev) {
        abrirFecharCaixaButton.setText("Fechar caixa");
        dataAberturaLabel.setText(Main.SqlDateToNormalFormat(lojaDB.caixaMap.get("Data_Abertura")));
        horaAberturaLabel.setText(lojaDB.caixaMap.get("Hora_Abertura"));
        adicionadoLabel.setText(lojaDB.caixaMap.get("Adicionado"));
        retiradoLabel.setText(lojaDB.caixaMap.get("Retirado"));
        idCaixaLabel.setText(lojaDB.caixaMap.get("ID_Caixa"));
        vendasDevLabel.setText(Main.twoDig(valueVendasDev));
        lojaDB.setDataHoraPanels();
    }

    public void showItem(MouseEvent evt) {
        int row = tableCaixa.rowAtPoint(evt.getPoint());
        int col = tableCaixa.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            int colOfKey = Main.getIndexColumnWithColumnName(tableCaixa, "ID");
            String idTransacao = tableCaixa.getValueAt(row, colOfKey).toString();
            int indexColumnTipo = Main.getIndexColumnWithColumnName(tableCaixa, "Tipo");
            String tipo = tableCaixa.getValueAt(row, indexColumnTipo).toString();
            String formatDesc = "";
            int indexColumnDinheiro, indexColumnSaldo, indexColumnDesc;

            if (tipo.equals("venda")) {
                String descricao = lojaDB.getColumnWithColumnKey("transacao", "ID_Transacao", idTransacao, "Descricao_Transacao");
                formatDesc += PanelVenda.createStringOfTransaction(descricao);
            }
            if (tipo.equals("devolucao")) {
                formatDesc += "devolução:\n\n";
                indexColumnDesc = Main.getIndexColumnWithColumnName(tableCaixa, "Descricao");
                indexColumnDinheiro = Main.getIndexColumnWithColumnName(tableCaixa, "Dinheiro");
                indexColumnSaldo = Main.getIndexColumnWithColumnName(tableCaixa, "No saldo do cliente");
                Object descObj = tableCaixa.getValueAt(row, indexColumnDesc);
                double dinheiro = Main.formatDoubleString(tableCaixa.getValueAt(row, indexColumnDinheiro).toString());
                double comSaldoCliente = Main.formatDoubleString(tableCaixa.getValueAt(row, indexColumnSaldo).toString());                    
                if (descObj != null) {
                    String desc = descObj.toString();
                    if (Main.isIntegerValid(desc)) {
                        String id_produto = desc;
                        String tipoProduto = lojaDB.getColumnWithColumnKey("Mercadoria", "ID_Mercadoria", id_produto, "TipoMercadoria");
                        String descricaoProduto = lojaDB.getColumnWithColumnKey("Mercadoria", "ID_Mercadoria", id_produto, "Descricao_Mercadoria");
                        formatDesc += "   Código da mercadoria: " + id_produto + "\n";
                        formatDesc += "   Tipo: " + tipoProduto + "\n";
                        formatDesc += "   Descrição: " + descricaoProduto + "\n\n";
                    }                    
                }
                else{
                    formatDesc += "   Produto: não informado na devolução\n\n";
                }
                if (dinheiro == 0) 
                    formatDesc += "   Forma de devolução: EM CRÉDITO PARA COMPRAS FUTURAS , valor: " + Main.twoDig(comSaldoCliente).replace("-", "");
                if (comSaldoCliente == 0) 
                    formatDesc += "   Forma de devolução: EM DINHEIRO , valor: " + Main.twoDig(dinheiro).replace("-", "") + "\n\n";
                
            }
            if (tipo.equals("caixa")) {
                indexColumnDinheiro = Main.getIndexColumnWithColumnName(tableCaixa, "Dinheiro");
                indexColumnDesc = Main.getIndexColumnWithColumnName(tableCaixa, "Descricao");
                int indexColumnObs = Main.getIndexColumnWithColumnName(tableCaixa, "Observacao");
                int indexColumnClient = Main.getIndexColumnWithColumnName(tableCaixa, "Cliente");
                String dinheiro = tableCaixa.getValueAt(row, indexColumnDinheiro).toString();
                String descricao = tableCaixa.getValueAt(row, indexColumnDesc).toString();
                Object obs = tableCaixa.getValueAt(row, indexColumnObs);
                if(obs==null)
                    formatDesc += descricao + " , valor: " + dinheiro.replace("-", "");
                else{
                    String observacao = obs.toString();
                    formatDesc += observacao + " , valor: " + dinheiro.replace("-", "");
                    Object clientObj = tableCaixa.getValueAt(row, indexColumnClient);
                    if(clientObj!=null){                        
                        String client = clientObj.toString();                
                        formatDesc += " ( "+client+" )";
                    }
                }
            }
            JOptionPane.showMessageDialog(tableCaixa, formatDesc);
        }
    }

    private void createTable(String[] columnNames) {
        DefaultTableModel model;
        model = new DefaultTableModel(columnNames, 0);
        //model.addRow(columnNames);
        tableCaixa = new JTable(model);
        tableCaixaPanel.setLayout(new BoxLayout(tableCaixaPanel, BoxLayout.PAGE_AXIS));
        tableCaixa.setDefaultEditor(Object.class, null);
        tableCaixa.addMouseListener(new PopClickListener(this));
    }

    public void setLabelsCaixa() {
        if (lojaDB.caixaAberto == false) {
            resetLabels();
            return;
        }
        Double valueVendasDev = lojaDB.calculateVendasDev();
        setLabels(valueVendasDev);
    }
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
        somenteCaixaAtualCheckBox = new javax.swing.JCheckBox();
        dataAberturaLabel = new javax.swing.JLabel();
        horaAberturaLabel = new javax.swing.JLabel();
        adicionadoLabel = new javax.swing.JLabel();

        abrirFecharCaixaButton.setText("Abrir caixa");
        abrirFecharCaixaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirFecharCaixaButtonActionPerformed(evt);
            }
        });

        verCaixaAtualButton.setText("Ver caixa atual");
        verCaixaAtualButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verCaixaAtualButtonActionPerformed(evt);
            }
        });

        adicionarDinheiroButton.setText("Adicionar ao caixa/ Recebimento de penduras");
        adicionarDinheiroButton.setToolTipText("Também usado se o cliente esteja devendo 20 reais e queira pagar uma parte");
        adicionarDinheiroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarDinheiroButtonActionPerformed(evt);
            }
        });

        retirarDinheiroButton.setText("Retirar do caixa/Retirada de créditos para compras futuras");
        retirarDinheiroButton.setToolTipText("Também usado se o cliente tem 20 reais de crédito e queira resgatar em dinheiro parte desse crédito");
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
            .addGap(0, 179, Short.MAX_VALUE)
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

        somenteCaixaAtualCheckBox.setText("Mostrar somente transações do caixa atual ");
        somenteCaixaAtualCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                somenteCaixaAtualCheckBoxActionPerformed(evt);
            }
        });

        dataAberturaLabel.setText(" ");

        horaAberturaLabel.setText(" ");

        adicionadoLabel.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableCaixaPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(somenteCaixaAtualCheckBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(abrirFecharCaixaButton)
                                .addGap(18, 18, 18)
                                .addComponent(verCaixaAtualButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adicionarDinheiroButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(vendasDevLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(dataAberturaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(horaAberturaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(retiradoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                    .addComponent(idCaixaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(adicionadoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(retirarDinheiroButton))
                        .addGap(0, 133, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abrirFecharCaixaButton)
                    .addComponent(verCaixaAtualButton)
                    .addComponent(adicionarDinheiroButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(retirarDinheiroButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(vendasDevLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(dataAberturaLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(horaAberturaLabel)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(adicionadoLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(retiradoLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(idCaixaLabel)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(somenteCaixaAtualCheckBox)
                .addGap(26, 26, 26)
                .addComponent(tableCaixaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void adicionarDinheiroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarDinheiroButtonActionPerformed
        addOrRemoveActionPerformed("adicionar", "Adição de dinheiro ao caixa");
    }//GEN-LAST:event_adicionarDinheiroButtonActionPerformed
    private void addOrRemoveActionPerformed(String option, String label) {
        if (lojaDB.caixaAberto == false) {
            JOptionPane.showMessageDialog(null, "Antes abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AddOrRemovePanel addPanel = new AddOrRemovePanel(lojaDB, "Valor a " + option, "", null, null, false, 0);
        addPanel.setPreferredSize(new Dimension(400,200));
        setTextOfRecebimento_retirarLabel(addPanel, option);
        String data = "", hora = "", value = "";
        boolean selected=false;
        int indexSelected=0;
        int result = JOptionPane.showConfirmDialog(null, addPanel,
                label, JOptionPane.OK_CANCEL_OPTION);
        while (result == JOptionPane.OK_OPTION) {
            value = addPanel.getValue();
            data = addPanel.getData();
            hora = addPanel.getHora();            
            selected = addPanel.isClientCheckBoxSelected();
            indexSelected = addPanel.getSelectedIndex();
            if (isValidFields(addPanel, option)) {
                break;
            }
            addPanel = new AddOrRemovePanel(lojaDB, "Valor a " + option, value, data, hora, selected, indexSelected);
            setTextOfRecebimento_retirarLabel(addPanel, option);
            result = JOptionPane.showConfirmDialog(null, addPanel,
                    label, JOptionPane.OK_CANCEL_OPTION);
        }
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        Double trocado = Main.formatDoubleString(value);;
        JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);
        addOrRemoveMoney(option, trocado, data, hora, selected, addPanel.getClient());
        update();
    }
    private void setTextOfRecebimento_retirarLabel(AddOrRemovePanel panel, String option){
        if(option.equals("adicionar")){
            panel.setTextOfRecebimento_retirarLabel("Recebimento de cliente que comprou fiado");
            panel.setToolTip("ou recebimento para crédito em compras futuras");
        }            
        if(option.equals("retirar")){
            panel.setTextOfRecebimento_retirarLabel("Resgate em dinheiro de um cliente com créditos de devoluções");        
            panel.setToolTip("No caso de um cliente que possui créditos por devoluções e quer resgatar esses créditos em dinheiro");
        }
    }
    private void retirarDinheiroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retirarDinheiroButtonActionPerformed
        addOrRemoveActionPerformed("retirar", "Retirada de dinheiro do caixa");        
    }//GEN-LAST:event_retirarDinheiroButtonActionPerformed
    private boolean isValidFields(AddOrRemovePanel panel, String option) {
        String input = panel.getValue();
        String data = panel.getData();
        String hora = panel.getHora();
        if (Main.isDoubleValid(input) == false || Main.formatDoubleString(input) < 0) {
            JOptionPane.showMessageDialog(null, "Valor inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isDateValid(data) == false) {
            JOptionPane.showMessageDialog(null, "Data inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isTimeValid(hora) == false) {
            JOptionPane.showMessageDialog(null, "Hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(panel.isClientCheckBoxSelected()){
            if(panel.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null, "Selecione um cliente ou desmarque a opção de recebimento/retirada de saldo do cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if(panel.isSaldoUtilizadoValido(input, option)==false)
                return false;
        }
        return true;
    }
    
    private void abrirFecharCaixaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirFecharCaixaButtonActionPerformed
        if (lojaDB.caixaAberto) {
            int answ = JOptionPane.showConfirmDialog(abrirFecharCaixaButton, "Tem certeza que quer fechar o caixa?", "Tela de confirmação", JOptionPane.OK_CANCEL_OPTION);
            if (answ != 0) {
                return;
            }
            fecharCaixa();
            return;
        }
        if (lojaDB.caixaAberto == false) {
            abrirCaixa();
            return;
        }
    }//GEN-LAST:event_abrirFecharCaixaButtonActionPerformed
    private void fecharCaixa() {
        String caixaCalculado = lojaDB.getCaixaEmDinheiroCalculado();
        FechamentoCaixaPanel fecharCaixaPanel = new FechamentoCaixaPanel(caixaCalculado, "", null, "", "");
        String caixaInformado = "", data = "", hora = "", obs = "";
        int result = JOptionPane.showConfirmDialog(null, fecharCaixaPanel,
                "Fechar caixa", JOptionPane.OK_CANCEL_OPTION);
        while (result == JOptionPane.OK_OPTION) {
            obs = fecharCaixaPanel.getObservacao();
            data = fecharCaixaPanel.getData();
            hora = fecharCaixaPanel.getHora();
            caixaInformado = fecharCaixaPanel.getCaixaInformado();
            if (isValidFechamento(fecharCaixaPanel)) {
                break;
            }
            fecharCaixaPanel = new FechamentoCaixaPanel(caixaCalculado, caixaInformado, data, hora, obs);
            result = JOptionPane.showConfirmDialog(null, fecharCaixaPanel,
                    "Fechar caixa", JOptionPane.OK_CANCEL_OPTION);
        }
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        obs = fecharCaixaPanel.getObservacao();
        data = fecharCaixaPanel.getData();
        data = completeDate(data);
        hora = fecharCaixaPanel.getHora();
        caixaInformado = fecharCaixaPanel.getCaixaInformado();
        data = Main.formatStringToSql("Date", data);
        hora = Main.formatStringToSql("Time", hora);
        double caixaInf = Main.formatDoubleString(caixaInformado);
        double caixaCalc = Main.formatDoubleString(caixaCalculado);
        String quebraDeCaixa = Main.twoDig(caixaInf - caixaCalc);
        String query = "UPDATE Caixa SET Status = 'fechado', Data_Fechamento = " + data + " , Hora_Fechamento = " + hora
                + " , FinalInformado = " + caixaInformado + " , Observacao = \'" + obs + "\' , QuebraDeCaixa = "
                + quebraDeCaixa + " where ID_Caixa = " + lojaDB.getOfCaixa("ID_Caixa");
        //Main.p(query);
        lojaDB.executeQuery(query);
        update();
    }
    private String completeDate(String date) {
        String dataAbertura = Main.SqlDateToNormalFormat(lojaDB.getOfCaixa("Data_Abertura"));
        String anoAbertura = dataAbertura.split("/")[2];
        date = date.trim();
        String[] split = date.split("/");
        if (split.length == 2) {
            return date + "/" + anoAbertura;
        }
        return date;
    }
    public boolean isValidFechamento(FechamentoCaixaPanel panel) {
        String caixaInformado = panel.getCaixaInformado();
        String data = panel.getData();
        data = completeDate(data);
        String hora = panel.getHora();
        if (Main.isDoubleValid(caixaInformado) == false || Main.formatDoubleString(caixaInformado) < 0) {
            JOptionPane.showMessageDialog(null, "Valor inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isDateValid(data) == false) {
            JOptionPane.showMessageDialog(null, "Data inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isTimeValid(hora) == false) {
            JOptionPane.showMessageDialog(null, "Hora inválida!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    private void verCaixaAtualButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verCaixaAtualButtonActionPerformed
        if (lojaDB.caixaAberto == false) {
            JOptionPane.showMessageDialog(null, "Antes abra o caixa!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        somenteCaixaAtualCheckBox.setSelected(true);
        update();
        String caixaCalculado = lojaDB.getMessageCaixaEmDinheiroCalculado(tableCaixa);        
        Font original = UIManager.getFont("Label.font");
        UIManager.put("Label.font", new Font("monospaced", Font.PLAIN, 12)); // specify your monospaced font here
        JOptionPane.showMessageDialog(verCaixaAtualButton, caixaCalculado,"Detalhamento do caixa", JOptionPane.INFORMATION_MESSAGE);
        UIManager.put("Label.font", original);        
    }//GEN-LAST:event_verCaixaAtualButtonActionPerformed

    private void somenteCaixaAtualCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_somenteCaixaAtualCheckBoxActionPerformed
        update();
    }//GEN-LAST:event_somenteCaixaAtualCheckBoxActionPerformed
    private boolean isValidEntry(AbrirCaixaPanel abrirPanel) {
        String data = abrirPanel.getData();
        String hora = abrirPanel.getHora();
        String value = abrirPanel.getValue();
        if (Main.isDateValid(data) == false) {
            JOptionPane.showMessageDialog(abrirFecharCaixaButton, "Digite uma data válida no campo abaixo (formato DD/MM/AAAA)", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isTimeValid(hora) == false) {
            JOptionPane.showMessageDialog(abrirFecharCaixaButton, "Digite uma hora válida no campo abaixo (formato HH:MM)", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Main.isDoubleValid(value) == false || Main.formatDoubleString(value) < 0) {
            JOptionPane.showMessageDialog(null, "Valor inválido!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void abrirCaixa() {
        AbrirCaixaPanel abrirPanel = new AbrirCaixaPanel("", null, null);
        String data = "", hora = "", value = "";
        int result = JOptionPane.showConfirmDialog(null, abrirPanel,
                "Abrir caixa", JOptionPane.OK_CANCEL_OPTION);
        while (result == JOptionPane.OK_OPTION) {
            value = abrirPanel.getValue();
            data = abrirPanel.getData();
            hora = abrirPanel.getHora();            
            if (isValidEntry(abrirPanel)) {
                break;
            }
            abrirPanel = new AbrirCaixaPanel(value, data, hora);
            result = JOptionPane.showConfirmDialog(null, abrirPanel,
                    "Abrir caixa", JOptionPane.OK_CANCEL_OPTION);
        }
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        value = Main.validateDoubleString(value);       
        data = Main.formatStringToSql("Date", data);
        hora = Main.formatStringToSql("Time", hora);
        
        JOptionPane.showMessageDialog(null, "Caixa aberto com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);
        String query = "insert into caixa(Status, Data_Abertura, Hora_Abertura, Adicionado, Retirado) Values ('aberto', " + data + ", " + hora + ", " + value + ", 0)";
        lojaDB.executeQuery(query);
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------    
        update();
        String query2 = "INSERT into Transacao(Tipo_Transacao, Dinheiro, Data_Transacao, Hora_Transacao, "
                + "Descricao_Transacao, ID_Caixa) VALUES (\"caixa\"," + value + "," + data + "," + hora + ",\"adição ao caixa\"," + lojaDB.getOfCaixa("ID_Caixa") + ")";
        lojaDB.executeQuery(query2);
        update();
    }

    private void addOrRemoveMoney(String command, Double value, String data, String hora, boolean clientSelected, String nameClient) {
        Double initialValue;
        String query;
        query = "UPDATE Caixa SET ";
        if (command.equals("adicionar")) {
            query += "Adicionado = ";
            initialValue = Double.parseDouble(lojaDB.getOfCaixa("Adicionado"));
            query += Main.twoDig(value + initialValue);
        } else if (command.equals("retirar")) {
            query += "Retirado = ";
            initialValue = Double.parseDouble(lojaDB.getOfCaixa("Retirado"));
            query += Main.twoDig(value + initialValue);
        }
        query += " where ID_Caixa = " + lojaDB.getOfCaixa("ID_Caixa");
        lojaDB.executeQuery(query);
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------     
        data = Main.formatStringToSql("Date", data);
        hora = Main.formatStringToSql("Time", hora);
        
        String query2 = "INSERT into Transacao(Tipo_Transacao, Dinheiro, Data_Transacao, Hora_Transacao, Descricao_Transacao, ID_Caixa";
        if(clientSelected) 
            query2+= ", Observacao, Cliente)";
        else
            query2+= ")";
        String tot = Main.twoDig(value);
        if (command.equals("adicionar")) {
            query2 += " VALUES ("
                    + "\"caixa\"," + tot + "," + data + "," + hora + ",\"adição ao caixa\"," + lojaDB.getOfCaixa("ID_Caixa"); 
            if(clientSelected)
                query2+=", \'Recebimento de compras fiado\',\' "
                        + nameClient+"\')";
            else
                query2+=")";
        } else if (command.equals("retirar")) {
            query2 += " VALUES ("
                    + "\"caixa\", -" + tot + "," + data + "," + hora + ",\"retirada do caixa\"," + lojaDB.getOfCaixa("ID_Caixa");
            if(clientSelected)
                query2+=", \'Retirada em dinheiro de saldo de cliente com créditos para compras\',\'"
                        + nameClient+"\')";
            else
                query2+=")";
        }
        lojaDB.executeQuery(query2);
        //---------------------------        
        String val = Main.twoDig(value);
        if(command.equals("retirar")) 
            val="-"+val;
        lojaDB.addSaldoCliente(nameClient, val);
    }

    public void update() {
        lojaDB.setBooleanCaixaAberto();
        setLabelsCaixa();
        if(somenteCaixaAtualCheckBox.isSelected()){
            ResultSet results = lojaDB.executeQuery("SELECT * FROM Transacao where ID_Caixa = " + lojaDB.getOfCaixa("ID_Caixa")+" ORDER BY Data_Transacao DESC, Hora_Transacao DESC");
            lojaDB.updateTable(tableCaixa, tableCaixaPanel, "Transacao", true, results);
        }
        else{
            ResultSet results = lojaDB.executeQuery("SELECT * FROM Transacao ORDER BY Data_Transacao DESC, Hora_Transacao DESC");            
            lojaDB.updateTable(tableCaixa, tableCaixaPanel, "Transacao", true, results);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirFecharCaixaButton;
    private javax.swing.JLabel adicionadoLabel;
    private javax.swing.JButton adicionarDinheiroButton;
    private javax.swing.JLabel dataAberturaLabel;
    private javax.swing.JLabel horaAberturaLabel;
    private javax.swing.JLabel idCaixaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel retiradoLabel;
    private javax.swing.JButton retirarDinheiroButton;
    private javax.swing.JCheckBox somenteCaixaAtualCheckBox;
    private javax.swing.JPanel tableCaixaPanel;
    private javax.swing.JLabel vendasDevLabel;
    private javax.swing.JButton verCaixaAtualButton;
    // End of variables declaration//GEN-END:variables
}
