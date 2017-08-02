package PanelsCadastro;

import Main.Main;
import javax.swing.JOptionPane;

public class PanelUsuario extends javax.swing.JPanel {
    Main lojaDB;
    public PanelUsuario(Main lojaDB) {
        this.lojaDB=lojaDB;
        initComponents();
    }
    public void insertUsuario(){
        String n=nomeField.getText();
        if(n.equals("")){
            JOptionPane.showMessageDialog(null, "O \'nome\' não pode ser vazio", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String d=descField.getText();
        String query = "INSERT INTO Usuario(Nome_Usuario, Descricao_Usuario) VALUES (\'"+
            n+"\',\'"+d+"\')";
        lojaDB.executeQuery(query);
        limparCampos();
        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "Aviso", JOptionPane.WARNING_MESSAGE);                     
    }
    void limparCampos(){
        nomeField.setText("");
        descField.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nomeField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        descField = new javax.swing.JTextField();

        jLabel1.setText("Nome Completo*: ");

        jLabel2.setText("Descrição: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                    .addComponent(nomeField))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(descField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(172, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nomeField;
    // End of variables declaration//GEN-END:variables
}
