package AdminDBPackage;


import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre Simao
 */
public class InterfaceGUI extends javax.swing.JFrame {
    JTextField []queryTextField;
    JLabel []labelCol;
    JTable table;
    String columnType[];
    HashMap<String,Integer> primaryKeyValues=new HashMap();
    ClasseAdmin locadora;
    String tableNames[]=new String[]{"Venda","Cliente", "Fornecedor","Usuario","TabelaDeTransacoes","TipoMercadoria", "Mercadoria", "Caixa"};
    boolean firstCreation=true;
    /**
     * Creates new form NewJFrame
     */
    public InterfaceGUI(ClasseAdmin locadora) {
       initComponents();
       this.locadora=locadora;
    }
    protected void setPrimaryKeyInitialValues(){
        try{
            for(int i=0;i<tableNames.length;i++)
            {
                String query="select max("+getPrimaryKey(tableNames[i])+") from " +tableNames[i];                
                ResultSet results = locadora.executeQuery(query);
                results.next();
                String max=results.getString(1); //System.out.println(results.getString(1));
                if(max==null)
                    primaryKeyValues.put(tableNames[i], 0);
                else
                    primaryKeyValues.put(tableNames[i], Integer.parseInt(max));
            }
        }
        catch(Exception e){
            System.out.println("Erro na aquisicao das chaves primarias");
        }
        /*primaryKeyValues.put("Aluguel", 0);
        primaryKeyValues.put("Cliente", 0);
        primaryKeyValues.put("FilmeCadastrado", 0);
        primaryKeyValues.put("Fornecedor", 0);
        primaryKeyValues.put("Reserva", 0);
        primaryKeyValues.put("SolicitacaoDeFilmes", 0);
        primaryKeyValues.put("SugestaoFilme", 0);*/     
    }
    protected JRadioButton getInsertButton(){
        return insertRadioButton;
    }
    public void createColumnsPaneAndTable(){
        try{
            ResultSet result =locadora.executeQuery("SELECT * FROM "+getTableName());
            statusLabel.setForeground(Color.lightGray);
            taskStatusLabel.setForeground(Color.lightGray);
            //locadora.printResults(result);
            ResultSetMetaData metaData = result.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Box columnsBox=Box.createVerticalBox();
            Box columnBox;
            String []columnNames=new String[numberOfColumns];
            queryTextField=new JTextField[numberOfColumns];
            labelCol=new JLabel[numberOfColumns];       
            columnType=new String[numberOfColumns];
            for (int i = 0; i < numberOfColumns; i++) {                
                columnNames[i]=metaData.getColumnName(i+1); 
                columnBox=createBoxOfQuery(metaData, i);               
                columnsBox.add(columnBox);
                columnType[i]=metaData.getColumnClassName(i+1);
                //System.out.println(columnType[i]);
            }       
            columnsPanel.removeAll();
            columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.PAGE_AXIS)); 
            JScrollPane scrollColumns=new JScrollPane(columnsBox);
            columnsPanel.add(scrollColumns);
            createTable(columnNames); 
            JScrollPane scrollTable=new JScrollPane(table);
            tablePanel.removeAll();
            tablePanel.add(scrollTable); 
            doCurrentButton();
        }
        catch (Exception exception) {
            System.out.println("Erro ao passar dados da tabela para o painel das colunas");  
            System.out.println(exception);
        } // end catch
        if(firstCreation)
        {
            this.setBounds(5, 5, 1200, 700);            
            firstCreation=false;
        }
        this.setVisible(true);
    }
    private void doCurrentButton(){
        statusLabel.setText("status");
        if(updateRadioButton.isSelected())
            updateRadioButton.doClick();
        if(selectRadioButton.isSelected())
            selectRadioButton.doClick();
        if(deleteRadioButton.isSelected())
            deleteRadioButton.doClick();
        if(insertRadioButton.isSelected())
            insertRadioButton.doClick();
    }
    private Box createBoxOfQuery(ResultSetMetaData metaData, int i) throws SQLException{
        Box columnBox=Box.createHorizontalBox();
        labelCol[i]=new JLabel(" "+metaData.getColumnName(i+1)+" ");
        labelCol[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
        labelCol[i].setPreferredSize(new Dimension(200, 20));
        columnBox.add(labelCol[i]);
        queryTextField[i]=new JTextField("",20);
        columnBox.add(queryTextField[i]);
        return columnBox;
    }
    private void createTable(String[] columnNames){
        DefaultTableModel model;      
        model = new DefaultTableModel(columnNames,0);
        //model.addRow(columnNames);
        table = new JTable(model);        
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.PAGE_AXIS));         
        updateTable(false,null);            
    }
    private void addRow(String[] row){
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.addRow(row);
    }  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        selectRadioButton = new javax.swing.JRadioButton();
        deleteRadioButton = new javax.swing.JRadioButton();
        insertRadioButton = new javax.swing.JRadioButton();
        tableComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        executeButton = new javax.swing.JButton();
        whereLabel = new javax.swing.JLabel();
        whereTextField = new javax.swing.JTextField();
        columnsPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        updateRadioButton = new javax.swing.JRadioButton();
        colunaAuxLabel = new javax.swing.JLabel();
        taskLabel = new javax.swing.JLabel();
        taskTextField = new javax.swing.JTextField();
        taskStatusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonGroup1.add(selectRadioButton);
        selectRadioButton.setText("SELECT");
        selectRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(deleteRadioButton);
        deleteRadioButton.setText("DELETE");
        deleteRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(insertRadioButton);
        insertRadioButton.setText("INSERT");
        insertRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRadioButtonActionPerformed(evt);
            }
        });

        tableComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Venda", "Cliente", "Fornecedor", "Usuario", "TabelaDeTransacoes", "TipoMercadoria", "Mercadoria", "Caixa" }));
        tableComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Table");

        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        whereLabel.setText("Where:");

        javax.swing.GroupLayout columnsPanelLayout = new javax.swing.GroupLayout(columnsPanel);
        columnsPanel.setLayout(columnsPanelLayout);
        columnsPanelLayout.setHorizontalGroup(
            columnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );
        columnsPanelLayout.setVerticalGroup(
            columnsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        tablePanel.setMaximumSize(new java.awt.Dimension(10000, 10000));
        tablePanel.setMinimumSize(new java.awt.Dimension(0, 0));
        tablePanel.setName(""); // NOI18N
        tablePanel.setPreferredSize(new java.awt.Dimension(900, 300));

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );

        statusLabel.setText("status do preenchimento");

        buttonGroup1.add(updateRadioButton);
        updateRadioButton.setText("UPDATE");
        updateRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRadioButtonActionPerformed(evt);
            }
        });

        colunaAuxLabel.setText("Colunas");

        taskLabel.setText("Task:");

        taskStatusLabel.setText("Status da tarefa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(tableComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectRadioButton)
                                    .addComponent(deleteRadioButton)
                                    .addComponent(insertRadioButton)
                                    .addComponent(updateRadioButton)
                                    .addComponent(executeButton))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(columnsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(colunaAuxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(whereLabel)
                                            .addComponent(taskLabel)
                                            .addComponent(whereTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                                            .addComponent(taskTextField)))))
                            .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(taskStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(9, 9, 9)
                                .addComponent(tableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectRadioButton)
                                    .addComponent(colunaAuxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(deleteRadioButton)
                                .addGap(4, 4, 4)
                                .addComponent(insertRadioButton)))
                        .addGap(3, 3, 3)
                        .addComponent(updateRadioButton)
                        .addGap(18, 18, 18)
                        .addComponent(executeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(taskLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(taskTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(whereLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(whereTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(columnsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(taskStatusLabel)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private String getPrimaryKey(String nameTable){
        return "ID_"+nameTable;
        
//        switch(nameTable){
//            case "Aluguel":
//                return "ID_Aluguel";
//            case "Cliente":
//                return "ID_Cliente";
//            case "FilmeCadastrado":
//                return "ID_Filme";
//            case "Fornecedor":
//                return "ID_Fornecedor";
//            case "Reserva":
//                return "ID_Reserva";
//            case "SolicitacaoDeFilmes":
//                return "ID_Solicitacao";
//            case "SugestaoFilme":
//                return "ID_SugFilmes";                   
//        }
//        System.out.println("Nao foi encontrada chave primaria para a tabela "+nameTable);
//        return null;
    }
    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        if(insertRadioButton.isSelected())    
            insertAction();  
        else if(deleteRadioButton.isSelected())
            deleteAction();       
        else if(selectRadioButton.isSelected())        
            selectAction();
        else if(updateRadioButton.isSelected())        
            updateAction();
    }//GEN-LAST:event_executeButtonActionPerformed
    private boolean formatInColumnsCorrect(){
        for(int i=0;i<queryTextField.length;i++){
            if(isValidContentToColumn(queryTextField[i].getText(),i)==false)
                return false;
        }
        return true;
    }
    private int getIndexOfColumn(String nameColumn){
        int i;
        for(i=0;i<labelCol.length;i++)
        {
            if(labelCol[i].getText().trim().equals(nameColumn))
                return i;
        }
        System.out.println("O indice da chave primaria nao foi encontrado.");
        return -1;
    }
    private void tableComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableComboBoxActionPerformed
        createColumnsPaneAndTable(); 
        updateTable(false,null);
    }//GEN-LAST:event_tableComboBoxActionPerformed
    private void selectRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectRadioButtonActionPerformed
        setPrimaryKeyAndWhere(true);
        setTaskStatusLabel("");
    }//GEN-LAST:event_selectRadioButtonActionPerformed
    private void deleteRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRadioButtonActionPerformed
        setPrimaryKeyAndWhere(true);
        updateTable(false,null);        
        setTaskStatusLabel("");
    }//GEN-LAST:event_deleteRadioButtonActionPerformed
    private void insertRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertRadioButtonActionPerformed
        setPrimaryKeyAndWhere(false);
        updateTable(false,null);        
        setTaskStatusLabel("");
    }//GEN-LAST:event_insertRadioButtonActionPerformed
    private void updateRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateRadioButtonActionPerformed
        setPrimaryKeyAndWhere(false);
        updateTable(false,null);        
        setTaskStatusLabel("");        
        setFillEmpty();
    }//GEN-LAST:event_updateRadioButtonActionPerformed
    private void setFillEmpty(){
        for(int i=0; i<queryTextField.length;i++)
            queryTextField[i].setText("");
    }
    private void setPrimaryKeyAndWhere(boolean enable)
    {
        String tableName=getTableName();
        String primaryKey=getPrimaryKey(tableName);
        int index=getIndexOfColumn(primaryKey);                       
        columnsPanel.setVisible(!enable);
        statusLabel.setVisible(!enable);        
        colunaAuxLabel.setVisible(!enable);
        queryTextField[index].setVisible(enable);
        labelCol[index].setVisible(enable);  
        if(deleteRadioButton.isSelected())
        {
            taskLabel.setVisible(false);
            taskTextField.setVisible(false);
            whereTextField.setVisible(true);
            whereLabel.setText("DELETE FROM TABLE WHERE:");
            whereLabel.setVisible(true); 
        }
        else if(updateRadioButton.isSelected())
        {
            taskLabel.setVisible(false);
            taskTextField.setVisible(false);
            whereTextField.setVisible(true);
            whereLabel.setText("UPDATE SELECTED SET FROM TABLE WHERE:");
            whereLabel.setVisible(true); 
        }
        else
        {
            taskLabel.setText(getTaskName());            
            whereLabel.setText("WHERE:");   
            whereLabel.setVisible(enable);   
            taskLabel.setVisible(enable);
            taskTextField.setVisible(enable);
            whereTextField.setVisible(enable);
        }
    }
    private String getTaskName(){
        if(updateRadioButton.isSelected())
            return "UPDATE SET:";
        if(selectRadioButton.isSelected())
            return "SELECT:";
        if(deleteRadioButton.isSelected())
            return "DELETE:";
        return "TASK";
    }
    protected String getTableName(){
        return (String) tableComboBox.getModel().getSelectedItem(); 
    }
    private void updateTable(boolean flagSelect, ResultSet results){
        if(flagSelect==false)            
            results =locadora.executeQuery("SELECT * FROM "+getTableName());
        int width=100;
        if(results==null)
        {
            System.out.println("Erro ao imprimir os dados, o query não foi executado corretamente.");
            return;
        }
        try{
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            //System.out.println("Table: "+getTableName());
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
            }            
            table.setModel(new DefaultTableModel(nameColumns,0)); 
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<table.getColumnCount();i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(width);
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=results.getString(i+1);
                }
                addRow(columns);
            } // end while
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            exception.printStackTrace();
        } // end catch 
        int numCol=table.getColumnCount();
        tablePanel.setPreferredSize(new Dimension(numCol*width,300));
    }
    private String formatStringToSql(String columnValue, int i)
    {
        if(columnType[i].equals("java.lang.Boolean")){
            columnValue=columnValue.toLowerCase();
            if(columnValue.equals("false")||columnValue.equals("0"))
                return "FALSE";
            if(columnValue.equals("true")||columnValue.equals("1"))
                return "TRUE";            
        }
        if(columnType[i].equals("java.lang.String"))
        {
            columnValue=columnValue.replace("\\", "\\\\");
            columnValue=columnValue.replace("'", "\'");
            return "\'"+columnValue+"\'";
        }
        if(columnType[i].equals("java.sql.Date"))
        {
            if(columnValue.contains("-")) //YYYY-MM-DD
                return "\'"+columnValue+"\'";
            else if(columnValue.contains("/")) //23/04/1997
            { 
                String[] separated=columnValue.split("/");
                return "\'"+separated[2]+"-"+separated[1]+"-"+separated[0]+"\'";
            }
        }  
        if(columnType[i].equals("java.sql.Time")) //HH-MM-SS
        {
            columnValue="\""+columnValue;
            if(columnValue.split(":").length==2)
                columnValue+=":00";
            columnValue+="\"";
        }  
        return columnValue;
    }
    private boolean isValidContentToColumn(String contentColumn, int i){
        if(contentColumn.isEmpty())
            return true;
        if(columnType[i].equals("java.lang.Boolean")){
            if(contentColumn.toLowerCase().equals("false")==true || contentColumn.toLowerCase().equals("true")==true)
                return true;
            if(contentColumn.equals("0")==true || contentColumn.equals("1")==true)
                return true;
            return false;
        }        
        if(columnType[i].equals("java.sql.Date"))
        {
            return isDateValid(contentColumn); 
        }
        if(columnType[i].equals("java.math.BigDecimal"))
        {
            try{
                Double.parseDouble(contentColumn);
            }
            catch(Exception e){
                return false;
            }
            return true;
        }
        if(columnType[i].equals("java.lang.Integer"))
        {
            try{
                Integer.parseInt(contentColumn);
            }
            catch(Exception e){
                return false;
            }
            return true;
        }
        return true;
    }
    public static boolean isDateValid(String date) 
    {        
        if(date.contains("/"))
        {
            try{
                String[] separated=date.split("/"); //24/06/1992   
                date= separated[1]+"-"+separated[0]+"-"+separated[2];
            }
            catch(Exception e)
            {
                System.out.println(e);
                return false;
            }
        }
        //if format yyyy-MM-dd
        String []dateSplit=date.split("-");
        if(date.split("-")[0].length()==4)
            date=dateSplit[1]+"-"+dateSplit[2]+"-"+dateSplit[0];
        String DATE_FORMAT = "MM-dd-yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    protected void setTaskStatusLabel(String text){
        taskStatusLabel.setText(text);
    }
    private void updateAction(){   
        if(formatInColumnsCorrect()==false)
        {
            statusLabel.setText("Revise os formatos dos dados introduzidos nas colunas");
            return;
        }
        statusLabel.setText("");
        boolean firstCondition=true;
        String tableName=getTableName();        
        String query="UPDATE "+tableName+" SET ";
        for (int i=0;i<queryTextField.length;i++)
        {   
            if(queryTextField[i].getText().isEmpty() ==false)
            {                                       
                if(firstCondition)                                   
                    firstCondition=false;
                else
                    query+=",";       
                query+=labelCol[i].getText()+" = ";
                query+=formatStringToSql(queryTextField[i].getText(), i);
            }     
        }    
        if(firstCondition){
            statusLabel.setText("Preencha pelo menos uma coluna");
            return;
        }
        else
        {
            if(whereTextField.getText().isEmpty()==false)               
                query+=" WHERE "+whereTextField.getText();
        }        
        taskStatusLabel.setText("Atualizacao enviada ao banco de dados");
        locadora.executeQuery(query);  
        updateTable(false,null);
    }
    private void deleteAction(){
        String tableName=getTableName();        
        String query="DELETE FROM "+tableName+" WHERE "+whereTextField.getText();
        System.out.println(query);        
        taskStatusLabel.setText("Exclusao enviada ao banco de dados");
        locadora.executeQuery(query);          
        updateTable(false,null);
    }
    private void selectAction(){
        String tableName=getTableName();        
        String query="SELECT ";
        if(taskTextField.getText().isEmpty())
            query+="*";
        else
            query+=taskTextField.getText();               
        query+=" FROM "+tableName;
        if(whereTextField.getText().isEmpty()==false)               
            query+=" WHERE "+whereTextField.getText();
        System.out.println(query);        
        taskStatusLabel.setText("Selecao enviada ao banco de dados");
        ResultSet results = locadora.executeQuery(query);  
        updateTable(true,results);
    }
    private int getValuePrimaryKey(String tableName){
        primaryKeyValues.replace(tableName,primaryKeyValues.get(tableName)+1);
        return primaryKeyValues.get(tableName);
    }
    private void insertAction(){
        if(formatInColumnsCorrect()==false)
        {
            statusLabel.setText("Revise os formatos dos dados introduzidos nas colunas");
            return;
        }
        statusLabel.setText("");
        boolean firstCondidition=true;
        String tableName=getTableName();        
        String query="INSERT INTO "+tableName+"("+this.getPrimaryKey(getTableName());
        int numNotEmptyAndValid=0;       
        String columnValues="";
        for (int i=0;i<queryTextField.length;i++)
        {   
            if(queryTextField[i].getText().isEmpty() ==false)
            {                       
                numNotEmptyAndValid++;                
                query+=",";      
                if(firstCondidition)                                   
                {
                    columnValues+=getValuePrimaryKey(getTableName());
                    firstCondidition=false;
                }                          
                columnValues+=",";
                query+=labelCol[i].getText(); 
                columnValues+=formatStringToSql(queryTextField[i].getText(), i);
            }     
        }    
        query+=") VALUES(";
        query+=columnValues+")";
        //ResultSet result =locadora.executeQuery(query);
        System.out.println(query);        
        taskStatusLabel.setText("Insercao enviada ao banco de dados");
        locadora.executeQuery(query);  
        updateTable(false,null);
    }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel columnsPanel;
    private javax.swing.JLabel colunaAuxLabel;
    private javax.swing.JRadioButton deleteRadioButton;
    private javax.swing.JButton executeButton;
    private javax.swing.JRadioButton insertRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton selectRadioButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JComboBox<String> tableComboBox;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel taskLabel;
    private javax.swing.JLabel taskStatusLabel;
    private javax.swing.JTextField taskTextField;
    private javax.swing.JRadioButton updateRadioButton;
    private javax.swing.JLabel whereLabel;
    private javax.swing.JTextField whereTextField;
    // End of variables declaration//GEN-END:variables
}
