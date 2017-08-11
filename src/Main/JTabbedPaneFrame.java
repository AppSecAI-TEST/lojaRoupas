package Main;

import Panels.*;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JTabbedPaneFrame extends JFrame {

    JTabbedPane tabbedPane;
    PanelVenda panelVenda; 
    PanelDevolucao panelDevolucao;
    PanelCaixa panelCaixa;
    PanelConsulta panelConsulta; 
    PanelCadastro panelCadastro; 
    PanelEstatistica panelEstat;
    PanelConferencia panelConf;
    String tableNames[] = new String[]{"Venda", "Cliente", "Fornecedor", "Usuario", "TabelaDeTransacoes", "TipoMercadoria", "Mercadoria", "Caixa"};
    Main lojaDB;
    private int currentTabIndex = 0, previousTabIndex = 0;
    public JTabbedPaneFrame(Main lojaDB) {
        super("Controle de Estoque");
        this.lojaDB = lojaDB;
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                previousTabIndex = currentTabIndex;
                currentTabIndex = tabbedPane.getSelectedIndex();
                Component component = tabbedPane.getSelectedComponent();                
                if(component.equals(panelConsulta))
                    panelConsulta.update();              
                if(component.equals(panelCaixa))
                    panelCaixa.update();  
                if(component.equals(panelCadastro))
                    panelCadastro.update();
                if(component.equals(panelConf))
                    panelConf.update();
                if(component.equals(panelVenda))
                    panelVenda.update();
                if(component.equals(panelDevolucao))
                    panelDevolucao.update();
                if(currentTabIndex==Main.getIndexOfTabWithName(tabbedPane, "Estatísticas")){
                    panelEstat.clean();
                    if(lojaDB.askPassword(null, true)==false)
                        tabbedPane.setSelectedIndex(previousTabIndex);
                    else
                        panelEstat.update();
                }
                    
            }
        });
        panelVenda = new PanelVenda(lojaDB);
        setBooleanCaixaAberto();
        tabbedPane.addTab("Venda", null, panelVenda, "Venda");

        panelDevolucao = new PanelDevolucao(lojaDB);
        tabbedPane.addTab("Devolução/Troca", null, panelDevolucao, "Devolução");

        panelCaixa = new PanelCaixa(lojaDB); // create third panel
        panelCaixa.setLabelsCaixa();
        tabbedPane.addTab("Caixa", null, panelCaixa, "Caixa");

        panelConsulta = new PanelConsulta(lojaDB); // create second panel
        tabbedPane.addTab("Consulta", null, panelConsulta, "Consulta");
        
        panelCadastro = new PanelCadastro(lojaDB); // create second panel
        tabbedPane.addTab("Cadastro", null, panelCadastro, "Cadastro");

        panelEstat = new PanelEstatistica(lojaDB);
        tabbedPane.addTab("Estatísticas", null, panelEstat, "Estatísticas");

        panelConf = new PanelConferencia(lojaDB);
        tabbedPane.addTab("Conferência", null, panelConf, "Conferência");

        add(tabbedPane); // add JTabbedPane to frame     
    }
    public void setConfirmaVendaPanelVisible(boolean flag){
        panelVenda.setConfirmaVendaPanelVisible(flag);
    }
    public String getTabName(){
        return tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
    }
    public void setDataHoraPanels() {
        if(panelDevolucao !=null)
            panelDevolucao.setDataHoraPanelDevol();
        
    }

    private void setCaixaAberto(boolean flag) {
        lojaDB.caixaAberto = flag;
        panelVenda.setStatusCaixaLabel(flag);
    }

    public void setBooleanCaixaAberto() {        
        boolean flagAberto = false;
        ResultSet results=null;
        try {
            results = lojaDB.executeQuery("Select * from Caixa where Status = \'aberto\'");
            if (results.next()) {
                flagAberto = true;
            }
        } catch (Exception e) {
            flagAberto = false;
        }
        if (flagAberto == false) {
            //System.out.println("Não há caixa aberto.");
            setCaixaAberto(false);
            return;
        }
        setCaixaAberto(true);
        //System.out.println("Há caixa aberto.");
        try {
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String nameColumn[] = new String[numberOfColumns];
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumn[i] = metaData.getColumnName(i + 1);
            }
            while (true) {
                for (int i = 0; i < numberOfColumns; i++) {
                    nameColumn[i] = changeNameColumn(nameColumn[i]);
                    if (nameColumn[i] != null) {
                        lojaDB.addInCaixaMap(nameColumn[i], results.getString(i + 1));
                    }
                }
                if (results.next() == false) {
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.println("Erro ao tentar coletar os dados do caixa");
            exception.printStackTrace();
        } // end catch        
    }

    private String changeNameColumn(String nameColumn) {
        if (nameColumn == null) {
            return null;
        }
        switch (nameColumn) {
            case "ID_Caixa":
                return "ID_Caixa";
            case "Data_Abertura":
                return "Data_Abertura";
            case "Hora_Abertura":
                return "Hora_Abertura";
            case "Adicionado":
                return "Adicionado";
            case "Retirado":
                return "Retirado";
            default:
                return null;
        }
    }

    public static void main(String args[]) {
        new Main();
    }

} // end class JTabbedPaneFrame
