package Main;
import auxClasses.RequestFocusListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.net.InetAddress;
import java.sql.DatabaseMetaData;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;

import javax.swing.table.DefaultTableModel;
public class Main {
    // JDBC driver name and database URL 
    PathConnection pathConnection;
    JTabbedPaneFrame tabbedFrame;    
    public boolean caixaAberto;
    static DecimalFormat t=new DecimalFormat("0.00");
    public HashMap<String,String> caixaMap=new HashMap();
    static long time = -1;
    static DateAndHour myDateAndHour;
    static int secondsToWaitTimeServer = 1;
    public Main() {   
        pathConnection = getPathConnection("CriancaBonitaDB");        
        //new GenerateBackupOrRecoverDB(this).recoverDB();       
        if(askPassword(null, false)==false)   {
            close_connection(pathConnection.connection, pathConnection.statement);
            System.exit(0);
        }             
        tabbedFrame = new JTabbedPaneFrame(this);
        tabbedFrame.setVisible(true);
        tabbedFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        tabbedFrame.setSize(700,700);
        tabbedFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if(caixaAberto==false)
                    System.exit(0);
                String[] options=new String[]{"Desligar assim mesmo", "Voltar"};
                String message = "O caixa ainda está aberto, aconselha-se fechá-lo antes de desligar o programa";
                int reply = JOptionPane.showOptionDialog(null, message, "Aviso",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                //0 - confirm      1 - nao confirma
                if(reply==0)
                    System.exit(0);                    
            }
        });
        // connect to database books and query database 
        while(tabbedFrame.isEnabled()){}       
        close_connection(pathConnection.connection, pathConnection.statement);        
    }
    public DatabaseMetaData getMetaData(){
        try {
            return pathConnection.connection.getMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean askPassword(Component c, boolean flagAdmin ){
        String title = "Senha necessária";
        String message = "Digite a senha: ";
        if(flagAdmin){
            title = "Senha de administrador necessária";
            message = "Digite a senha de administrador: ";
        }
        JPasswordField jpf = new JPasswordField(24);
        JLabel jl = new JLabel(message);
        Box box = Box.createHorizontalBox();
        box.add(jl);
        box.add(jpf);
        jpf.addAncestorListener( new RequestFocusListener() );
        int answ = JOptionPane.showConfirmDialog(null, box, title, JOptionPane.OK_CANCEL_OPTION);
        if (answ != JOptionPane.OK_OPTION) 
          return false;
        char[] triedChars = jpf.getPassword();
        String tried =new String(triedChars);
        String query = "Select Tipo_Senha from Senha WHERE String_Senha = \'"+tried+"\'";
        String Tipo_Senha = getUniqueValueOfQuery(query);
        if(Tipo_Senha==null){
            JOptionPane.showMessageDialog(c, "Senha incorreta");
            return false;
        }            
        if(Tipo_Senha.equals("1"))
            return true;
        if(Tipo_Senha.equals("2")){
            if(flagAdmin){
                JOptionPane.showMessageDialog(c, "A senha de administrador não é essa!");
                return false;
            }
            else
                return true;
        }
        Main.p("Não era pra chegar aqui");
        return false;
    }
    public void setTabbedPaneVisible(boolean flag){
        tabbedFrame.setVisible(flag);
    }
    public void setConfirmaVendaPanelVisible(boolean flag){
        tabbedFrame.setConfirmaVendaPanelVisible(flag);
    }
    public static int getIndexOfTabWithName(JTabbedPane tabs, String name){
        int len = tabs.getTabCount();
        int index = -1;
        for(int i=0;i<len;i++)
            if(tabs.getTitleAt(i).equals(name))
                return i;
        return -1;
    }
    public static boolean isDateValid(String date) {        
        if(date.contains("/"))
        {
            try{
                String[] separated=date.split("/"); //24/06/1992   
                date= separated[1]+"-"+separated[0]+"-"+separated[2];
            }
            catch(Exception e)
            {
                e.printStackTrace(); 
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
            e.printStackTrace(); 
            return false;
        }
    }
    public static boolean isDoubleValid(String doubleS){
        try{
            formatDoubleString(doubleS);
        }
        catch(Exception e){return false;}
        return true;
    }
    public static boolean isIntegerValid(String intS){
        if(intS.trim().equals(""))
            return true;
        int len = intS.length();
        for(int i=0;i<len;i++){
            if(Character.isDigit(intS.charAt(i))==false)
                return false;
        }
        return true;
    }
    public static boolean isIntegerValid(String[] intS){
        int len = intS.length;
        for(int i=0;i<len;i++){
            if(isIntegerValid(intS[i])==false)
                return false;
        }
        return true;
    }
    public static String validateDoubleString(String doubleS){        
        return Double.toString(formatDoubleString(doubleS));
    }
    public static double formatDoubleString(String doubleS){        
        if(doubleS==null)
            return 0;
        doubleS=doubleS.trim();
        if(doubleS.equals("")||doubleS.equals("null")||doubleS.equals("NULL"))
            return 0;
        doubleS=doubleS.replace(",", ".");
        return Double.parseDouble(doubleS); 
    }   
    public static boolean isTimeValid(String time){   
        String[] timeSplit = time.split(":");
        if(timeSplit.length!=2 && timeSplit.length!=3)
            return false;
        try{ 
            int hour = Integer.parseInt(timeSplit[0]);
            if(hour < 0 || hour >= 24)
                return false;
            int min = Integer.parseInt(timeSplit[1]);
            if(min < 0 || min >=60)
                return false;
            if(timeSplit.length==3){
                int sec = Integer.parseInt(timeSplit[2]);
                if(sec<0 || sec>=60)
                    return false;                
            }            
        }
        catch(Exception e){
            return false;
        }         
        return true;
    }    
    public void setDataHoraPanels(){
        if(tabbedFrame==null)
            return;
        tabbedFrame.setDataHoraPanels();
    }
    public void setBooleanCaixaAberto(){
        if(tabbedFrame!=null)
            tabbedFrame.setBooleanCaixaAberto();
    }
    void printResults(ResultSet results){
        if(results==null)
        {
            System.out.println("Erro ao imprimir os dados, o query não foi executado corretamente.");
            return;
        }
        try{
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();
            while (results.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    System.out.printf("%-8s\t", results.getObject(i));
                }
                System.out.println();
            } // end while
        }
        catch (Exception exception) {
            System.out.println("Erro em coletar dados da tabela ");  
            exception.printStackTrace(); 
        } // end catch
       
    }    
    public void addInCaixaMap(String key, String value){
        Main.p(key+" "+value);
        caixaMap.put(key, value);
    }
    public String getOfCaixa(String mykey){
        Main.p("myKey: "+mykey);
        
        for(String key: caixaMap.keySet()){
            Main.p(key);
            if(mykey.equals(key))
                return caixaMap.get(key);
        }
        Main.p("saiu: "+mykey);
        Exception e=new Exception();
        e.printStackTrace();
        System.out.println("Chave não encontrada em caixaMap");
        return null;
    }
    private static synchronized void setDataAndHourFields(JTextField dataField, JTextField horaField, DateAndHour dateAndHour){
        myDateAndHour=dateAndHour;
        if(dateAndHour.isServerHour){
            dataField.setEditable(false);
            horaField.setEditable(false);
        }
        else{
            dataField.setEditable(true);
            horaField.setEditable(true);
        }
        dataField.setText(dateAndHour.date);
        horaField.setText(dateAndHour.hour);    
    }
    public static void setDateAndHour(JTextField dataField, JTextField horaField){
        if(time!=-1)
            if(System.currentTimeMillis()-time<secondsToWaitTimeServer*1000)
                setDataAndHourFields(dataField, horaField, myDateAndHour);
        time=System.currentTimeMillis();
        DateAndHour dateAndHour=new DateAndHour();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String shakeDate=dateFormat.format(date);
        String[]split = shakeDate.split("/");
        dateAndHour.date=split[2]+"/"+split[1]+"/"+split[0];
        dateAndHour.hour=hourFormat.format(date);
        dateAndHour.isServerHour=false;
        setDataAndHourFields(dataField, horaField, dateAndHour); 
        getDateAndHourServerThread("time-a.nist.gov", dataField, horaField);
        getDateAndHourServerThread("time-b.nist.gov", dataField, horaField);
        getDateAndHourServerThread("time-c.nist.gov", dataField, horaField);
        getDateAndHourServerThread("time-d.nist.gov", dataField, horaField);        
    }
    private static void getDateAndHourServerThread(String TIME_SERVER, JTextField dataField, JTextField horaField){
        new Thread(){
            public void run(){
                getDateAndHourServer(TIME_SERVER, dataField, horaField);
            }
        }.start();        
    }
    private static void getDateAndHourServer(String TIME_SERVER, JTextField dataField, JTextField horaField){
        DateAndHour dateAndHour=null;  
        long timeAux=System.currentTimeMillis();
        try{  
            dateAndHour=new DateAndHour();
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getReturnTime();
            Date time = new Date(returnTime);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");        
            dateAndHour.isServerHour=true;
            String shakeDate=dateFormat.format(time);
            String[]split = shakeDate.split("/");
            dateAndHour.date=split[2]+"/"+split[1]+"/"+split[0];
            dateAndHour.hour=hourFormat.format(time);
        }
        catch(Exception e){
            e.printStackTrace();
            Main.p("Clique com o botão direito em libraries e em Add JAR/Folder");
            Main.p("Ou ligue a internet");
            dateAndHour=null;

        }
        if(System.currentTimeMillis()-timeAux<secondsToWaitTimeServer*1000-200)
            if(dateAndHour!=null)
                setDataAndHourFields(dataField, horaField, dateAndHour);
    }
    public void addRow(String[] row, JTable table){
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.addRow(row);
    }  
    public void removeRow(int row, JTable table){
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.removeRow(row);
    } 
    public static ResultSet executeQuery (Statement statement, String query) throws Exception{
//        try {
            String operacao = query.split(" ")[0];
            // query database with insert
            operacao =  operacao.toLowerCase();
            if(operacao.equals("insert") || operacao.equals("delete") || operacao.equals("update"))
            {
                //System.out.println("Operacao realizada com sucesso");
                statement.executeUpdate(query);
                return null;
            }
            if(operacao.equals("select") )
                return statement.executeQuery(query);                     
            return null;
//        } catch (Exception exception) {
//            System.out.println("Erro na execução do query: "+query);   
//            exception.printStackTrace();
//            return null;
//        } // end catch
    }    
    public void executeUpdate(String query){
        Statement statement=pathConnection.statement;
        try {
            statement.executeUpdate(query);  
        } catch (Exception exception) {
            System.out.println("Erro na execução do query: "+query);   
            exception.printStackTrace();
        } // end catch
    }        
    public ResultSet executeQuery(String query) throws Exception{
        //query=prepareToDB(query);
        Statement statement=pathConnection.statement;
        return Main.executeQuery(statement, query);        
    }
    public void updateSQL(JTable table, String tableName, int row, int col){
        if(tableName.equals("Penduras"))
            tableName = "Cliente";
        if(row<0 || col<0)
            return;
        String nameColumn = table.getColumnName(col);   
        if(isEditableColumn(tableName, nameColumn)==false){
            JOptionPane.showMessageDialog(table, "Esse campo não é editável", "Aviso", JOptionPane.WARNING_MESSAGE);                            
            return;
        }
        int colOfKey = Main.getIndexColumnWithColumnName(table, "ID");
        String primaryKey = table.getValueAt(row, colOfKey).toString();
        String newValue = JOptionPane.showInputDialog(table, "Digite o novo valor");
        if(newValue==null)
            return;
        if(isValidInput(table, newValue, nameColumn)==false){
            return;
        }
        if(nameColumn.equals("Saldo")){
            String message="Para um melhor controle do caixa, aconselha-se alterar o saldo do \ncliente mediante adição ou retirada do caixa na aba \'Caixa\'.\n"
                    + "Deseja realmente continuar?";
            String options[]=new String[]{"Entendo o risco e quero mudar mesmo assim", "voltar"};
            
            int answ = JOptionPane.showOptionDialog(table, message, "Atenção!!!", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (answ != 0) 
                    return;
        }
        if(nameColumn.equals("Status"))
            newValue=newValue.toLowerCase();
        if(nameColumn.equals("Tamanho_est"))
            newValue=newValue.toUpperCase();
        
        if(nameColumn.equals("Preço")==false && nameColumn.equals("Saldo")==false)
            newValue="\'"+newValue+"\'";
        String dangerColumns[]=new String[]{"Status", "Preço"};
        if(contains(dangerColumns, nameColumn)){
            int answ = JOptionPane.showConfirmDialog(table, "Alterar essa valor pode ser perigoso, deseja alterar assim mesmo?", "Cuidado!", JOptionPane.OK_CANCEL_OPTION);
            if (answ != 0) 
                return;
            if(askPassword(null, true)==false)   
                return;
        }
        String adminColumns[]=new String[]{"Nome", "CPF", "Saldo", "Telefone_Celular1", "Usuario", "Descricao", "TipoMercadoria"};
        if(contains(adminColumns, nameColumn))
            if(askPassword(null, true)==false)   
                return;
        String query ="UPDATE "+tableName+" SET "+ returnToSQLNameCol(tableName, nameColumn)+" = "+ newValue+" WHERE ID_"+tableName+" = "+primaryKey;
        try{
            executeQuery(query);
        }catch(Exception e){
            JOptionPane.showMessageDialog(table, "Erro ao tentar mandar ao banco de dados. Use um valor válido", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    private String returnToSQLNameCol(String tableName, String nameCol){
        String [] columnsToComplete =new String[]{"Nome", "Endereco", "Email", "CPF", "Descricao", "Saldo"};
        if(contains(columnsToComplete, nameCol))
            return nameCol+"_"+tableName;
        if(nameCol.equals("Preço"))   
            return "Preco_Merc";   
        return nameCol;
    }    
    public static int numberOfChars(String s, char c){
        if(s==null)
            return 0;
        int count =0;
        int len =s.length();
        for(int i=0;i<len;i++)
            if(s.charAt(i)==c)
                count++;        
        return count;
    }
    private boolean isValidInput(JTable table, String newValue, String nameColumn){        
        if(nameColumn.equals("Tamanho")){
            if(newValue.length()>3 || Main.isIntegerValid(newValue)==false){
                JOptionPane.showMessageDialog(table, "O tamanho deve ser um número e de até 3 dígitos", "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        if(nameColumn.equals("Tamanho_est")){
            String estimativas[]=new String[]{"-", "PPP", "PP", "P", "M", "G", "GG", "GGG"};
            String teste = newValue.toUpperCase();
            if(contains(estimativas, teste)==false){
                JOptionPane.showMessageDialog(table, "A estimativa deve ser PPP, PP, P, M, G, GG, GGG ou -", "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        if(nameColumn.equals("Status")){
            String optionsStatus[]=new String[]{"no estoque", "vendido", "encomendado"};
            String teste = newValue.toLowerCase();
            if(contains(optionsStatus, teste)==false){
                JOptionPane.showMessageDialog(table, "O Status deve ser \'no estoque\', \'vendido\' ou \'encomendado\'", "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        if(nameColumn.equals("Nome") ){ 
            if(newValue.equals("")) {
                JOptionPane.showMessageDialog(null, "O nome não pode ser vazio", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }
            if(newValue.split(" ").length<2){
                JOptionPane.showMessageDialog(table, "Deve cadastrar pelo menos um sobrenome", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
                return false;
            }
            Object hasClient = getColumnWithColumnKey("Cliente", "Nome_Cliente", stringToSql(newValue), "*");;
            if(hasClient != null){
                JOptionPane.showMessageDialog(null, "Esse nome já está registrado", "Aviso", JOptionPane.WARNING_MESSAGE);   
                return false;
            }           
            return true;
        }
        if(nameColumn.equals("Descricao") || nameColumn.equals("Descricao")){            
            if(newValue.trim().equals("")){
                JOptionPane.showMessageDialog(table, "A descricao nao pode ser vazia", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
                return false;
            }
            return true;
        }
        if(nameColumn.equals("Preço") ){            
            if(Main.isDoubleValid(newValue)==false){
                JOptionPane.showMessageDialog(table, "Valor inválido", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
                return false;
            }
            return true;
        }
        if(nameColumn.equals("Saldo") ){            
            if(Main.isDoubleValid(newValue)==false){
                JOptionPane.showMessageDialog(table, "Valor inválido", "Aviso", JOptionPane.WARNING_MESSAGE);                                 
                return false;
            }
            return true;
        }
        if(nameColumn.equals("TipoMercadoria") ){ 
            String query = "Select ID_TipoMercadoria FROM TipoMercadoria where Descricao_TipoMercadoria =\'"+newValue+"\'";
            if(getUniqueValueOfQuery(query)==null){
                JOptionPane.showMessageDialog(table, "Tipo inválido", "Aviso", JOptionPane.WARNING_MESSAGE);    
                return false;
            }               
            return true;
        }
        return true;
    }
    private boolean isEditableColumn(String tableName, String nameColumn){
        if(tableName.equals("Cliente")||tableName.equals("Fornecedor")){
            String [] editableColumns =new String[]{"Nome", "Telefone_Celular1", "Telefone_Celular2", 
                "Telefone_Fixo", "Endereco", "Email", "CPF", "Descricao", "Saldo"};
            if(contains(editableColumns, nameColumn))
                return true;
        }      
        if(tableName.equals("Usuario")){
            String [] editableColumns =new String[]{"Nome", "Descricao"};
            if(contains(editableColumns, nameColumn))
                return true;
        }        
        if(tableName.equals("Mercadoria")){
            String [] editableColumns =new String[]{"TipoMercadoria", "Status", "Descricao", "Tamanho_est", "Tamanho", "Observacao", "Preço"};
            if(contains(editableColumns, nameColumn))
                return true;
        }     
        return false;
    }
    private boolean contains(String[] values, String s){
        for(int i=0;i<values.length;i++)
            if(values[i].equals(s))
                return true;
        return false;
    }
    public static String elemOfTable(JTable table, int row, int col){
        Object elemObj = table.getValueAt(row, col);
        if(elemObj==null)
            return "";
        String elem = elemObj.toString();
        elem = elem.replaceAll("#", "-");
        elem = elem.replaceAll("\"", "-");
        elem = elem.replaceAll("\'", "-");
        if(elem.equals(""))
            return " ";
        return elem;
    }
    private PathConnection getPathConnection(String dataBaseName) {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/"+dataBaseName+"?autoReconnect=true&useSSL=false";
        Connection connection = null; // manages connection
        Statement statement = null; // query statement      
        try {
            Class.forName(JDBC_DRIVER); // load database driver class
        }
        catch(ClassNotFoundException exception){
            System.out.println("Aperte o botao direito em Libraries e adicione o jar mysql-connector-java");
            exception.printStackTrace();
        }
        try{
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "andregsimao", "020410");

            // create Statement for querying database
            statement = connection.createStatement();
        } catch (SQLException exception) {            
            System.out.println("O banco de dados \""+ dataBaseName+"\" não foi encontrado");  
            exception.printStackTrace();
        } // end catch
        return new PathConnection(connection, statement);
    }    
    final void close_connection(Connection connection, Statement statement) {
        try {
            statement.close();
            connection.close();
        } // end try                                               
        catch (Exception exception) {
            System.out.println("Erro ao tentar fechar a conexão");
            exception.printStackTrace();
        } // end catch 
    }
    public static void formattedMessage(Component c, String message,String title, int option){
        Font original = UIManager.getFont("Label.font");
        UIManager.put("Label.font", new Font("monospaced", Font.PLAIN, 12)); // specify your monospaced font here
        JOptionPane.showMessageDialog(c, message,title, option);
        UIManager.put("Label.font", original); 
    }
    public int getNumberColumnsOfQuery(String query){        
        int numMercadorias=0;
        try{
            ResultSet results = executeQuery(query);
            results.last();            
            numMercadorias = results.getRow();
        }catch(Exception e){e.printStackTrace();}
        return numMercadorias;
    }
    public String getUniqueValueOfQuery(String query){
        String obj=null;
        try{            
            ResultSet results = executeQuery(query);
            results.next();            
            obj = results.getString(1);
        }catch(Exception e){}
        return obj;
    }
    public static void verEvent(JTable table, int row, int col){
        if (row < 0 || col < 0) 
            return;                    
        int nRows = table.getRowCount();
        int nCols = table.getColumnCount();
        String message="";
        for(int j=0;j<nCols;j++){
            Object value = table.getValueAt(row, j);
            if(value!=null)
                message+=table.getColumnName(j)+":   "+table.getValueAt(row, j)+"\n";
        }                    
        JOptionPane.showMessageDialog(table, message);
    }  
    public static void main(String args[]) {
        Main main = new Main();
    } // end main
    public static boolean isEmpty(String s){
        if(s.trim().equals(""))
            return true;
        return false;
    }
    public static String prepareToSearch(String query){
        if(query==null)
            return null;
        query=query.toLowerCase();
        String before[] = new String[]{"á", "à", "ã", "â", "ï", "è", "é", "ë", "ê", "î", "í", "ó", "õ", "ô", "ú", "ù", "û", "ç" };
        String after[] = new String[] {"a", "a", "a", "a", "i", "e", "e", "e", "e", "i", "i", "o", "o", "o", "u", "u", "u", "c" };
        for(int i=0;i<before.length;i++){
            query=query.replaceAll(before[i], after[i]);
        }
        return query.trim();        
    }
    public static String twoDig(double d){
        return t.format(d);
    }
    public static void cleanTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    public static String stringToSql(String columnValue){
        columnValue=columnValue.replace("\\", "\\\\");
        columnValue=columnValue.replaceAll("'", "\\'");
        return "\'"+columnValue+"\'";
    }
    public static String formatStringToSql(String columnType, String columnValue){
        if(columnType.equals("Boolean")){
            columnValue=columnValue.toLowerCase();
            if(columnValue.equals("false")||columnValue.equals("0"))
                return "FALSE";
            if(columnValue.equals("true")||columnValue.equals("1"))
                return "TRUE";            
        }
        if(columnType.equals("String"))
        {
            columnValue=columnValue.replace("\\", "\\\\");
            columnValue=columnValue.replace("'", "\'");
            return "\'"+columnValue+"\'";
        }
        if(columnType.equals("Date"))
        {
            if(columnValue.contains("-")) //YYYY-MM-DD
                return "\'"+columnValue+"\'";
            else if(columnValue.contains("/")) //23/04/1997
            { 
                String[] separated=columnValue.split("/");
                return "\'"+separated[2]+"-"+separated[1]+"-"+separated[0]+"\'";
            }
        }  
        if(columnType.equals("Time")) //HH-MM-SS
        {
            columnValue="\""+columnValue;
            if(columnValue.split(":").length==2)
                columnValue+=":00";
            columnValue+="\"";
        }  
        return columnValue;
    }
    public static void p(Object s){
        System.out.println(s);
    }
    public static void p(Object[] array){
        String s="";
        for(int i=0;i<array.length;i++)
            s+=i+": "+array[i]+" \n";
        Main.p(s);
    }
    public static String SqlDateToNormalFormat(String dataInSqlFormat){
        if(dataInSqlFormat==null)
            return null;
        String[] separated=dataInSqlFormat.split("-");
        try{
            return  separated[2]+"/"+separated[1]+"/"+separated[0];
        }catch(Exception e){
            return "";
        }
    }
    public String getCaixaEmDinheiroCalculado(){
        double caixaEmDinheiro = 0;
        if(caixaAberto==false){
            System.out.println("Não há caixa aberto.");
            return "0";
        }
        
        try{
            ResultSet results =executeQuery("SELECT * FROM Transacao where ID_Caixa = " + this.getOfCaixa("ID_Caixa"));
            if(results==null){
                System.out.println("Não há transações registradas no Caixa atual.");
                return "0";
            }
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            int iDinheiro = -1;
            //System.out.println("Table: "+getTableName());
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String s = nameColumns[i];
                if(s.equals("Dinheiro"))
                    iDinheiro =i;
            }            
            while (results.next()) {
                String col;                
                for (int i = 0; i < numberOfColumns; i++) {
                    col=results.getString(i+1);
                    if(i==iDinheiro)
                        caixaEmDinheiro+=Main.formatDoubleString(col);
                }
            }
        }         
        catch (Exception exception) {
            System.out.println("Erro ao calcular o total do caixa em dinheiro");
            exception.printStackTrace();
        } // end catch         
        return Main.twoDig(caixaEmDinheiro);
    }
    public String getMessageCaixaEmDinheiroCalculado(JTable tableCaixa){
        String message ="";
        double adicionadoCaixa=0, retiradoCaixa=0;
        double pagamentoPendurasDinheiro=0, resgateSaldoDinheiro=0;
        double vendasDinheiro=0, devolucoesDinheiro=0;                  
        for(int row=0;row<tableCaixa.getRowCount();row++){
            int indexColumnTipo = Main.getIndexColumnWithColumnName(tableCaixa, "Tipo");
            String tipo = tableCaixa.getValueAt(row, indexColumnTipo).toString();
            int indexColumnDinheiro = Main.getIndexColumnWithColumnName(tableCaixa, "Dinheiro");
            if (tipo.equals("venda")) 
                vendasDinheiro+= Main.formatDoubleString(tableCaixa.getValueAt(row, indexColumnDinheiro).toString());
            if (tipo.equals("devolucao"))               
                devolucoesDinheiro += Main.formatDoubleString(tableCaixa.getValueAt(row, indexColumnDinheiro).toString());                
            if (tipo.equals("caixa")) {
                int indexColumnObs = Main.getIndexColumnWithColumnName(tableCaixa, "Observacao");                
                double dinheiro = Main.formatDoubleString(tableCaixa.getValueAt(row, indexColumnDinheiro).toString());
                Object obs = tableCaixa.getValueAt(row, indexColumnObs);
                if(obs==null){
                    if(dinheiro<0)
                        retiradoCaixa+=dinheiro;
                    else
                        adicionadoCaixa+=dinheiro;                    
                }
                else{
                    if(dinheiro<0)
                        resgateSaldoDinheiro+=dinheiro;                    
                    else
                        pagamentoPendurasDinheiro+=dinheiro;                    
                }                    
            }            
        }
        message+="Adição ao caixa:                              +"+Main.twoDig(Math.abs(adicionadoCaixa));
        message+="\nPagamento de penduras em dinheiro:            +"+Main.twoDig(Math.abs(pagamentoPendurasDinheiro));        
        message+="\nVendas em dinheiro:                           +"+Main.twoDig(Math.abs(vendasDinheiro));
        message+="\nRetirada do caixa:                            -"+Main.twoDig(Math.abs(retiradoCaixa));
        message+="\nResgate em dinheiro de créditos de clientes:  -"+Main.twoDig(Math.abs(resgateSaldoDinheiro));
        message+="\nDevoluções em dinheiro:                       -"+Main.twoDig(Math.abs(devolucoesDinheiro));
        double t = adicionadoCaixa +pagamentoPendurasDinheiro +retiradoCaixa +resgateSaldoDinheiro+
                vendasDinheiro+ devolucoesDinheiro;
        message+="\n\nTotal em dinheiro:                             "+Main.twoDig(t);
        return message;
    }
    public double calculateVendasDev(){  
        double vendasDev = 0;
        if(caixaAberto==false){
            System.out.println("Não há caixa aberto.");
            return 0;
        }        
        try{
            ResultSet results =executeQuery("SELECT * FROM Transacao where ID_Caixa = " + this.getOfCaixa("ID_Caixa"));
            if(results==null){
                System.out.println("Não há transações registradas no Caixa atual.");
                return 0;
            }
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            int iTipo = -1;
            int iDinheiro = -1;
            int iCartao = -1;
            int iFiado = -1;
            int iSaldoCliente = -1;    
            //System.out.println("Table: "+getTableName());
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String s = nameColumns[i];
                switch(s){
                    case "Tipo_Transacao":
                        iTipo =i;
                        break;
                    case "Dinheiro":
                        iDinheiro =i;
                        break;
                    case "Cartao":
                        iCartao = i;
                        break;
                    case "Fiado":
                        iFiado = i;
                        break;
                    case "Com_SaldoCliente":
                        iSaldoCliente = i;
                        break;
                }
            }            
            while (results.next()) {
                String col;
                boolean ignoreRow=false;
                for (int i = 0; i < numberOfColumns && ignoreRow==false; i++) {
                    col=results.getString(i+1);
                    if(i==iTipo)
                        if(col.equals("venda")==false && col.equals("devolucao")==false){
                            ignoreRow=true;
                            continue;
                        }
                    if(i==iDinheiro||i==iCartao||i==iFiado||i==iSaldoCliente)
                        vendasDev+=Main.formatDoubleString(col);
                }
            }
        }         
        catch (Exception exception) {
            System.out.println("Erro ao calcular as vendas/devoluções");
            exception.printStackTrace();
        } // end catch         
        return vendasDev;
    }
    public void updateTable(JTable table, JPanel tablePanel, String tableName, boolean flagSelect, ResultSet results){        
        int width=100;
        try{
            if(flagSelect==false)            
                results =executeQuery("SELECT * FROM "+ tableName);
            
            if(results==null){
                System.out.println("Erro ao imprimir os dados, o query não foi executado corretamente.");
                return;
            }
            ResultSetMetaData metaData = results.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            int iDesc=-1;
            HashSet<Integer> iData = new HashSet();
            //System.out.println("Table: "+getTableName());
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String s = nameColumns[i];
                if(s.equals("Descricao_Transacao")) iDesc = i;
                String first4 = s.substring(0, Math.min(s.length(), 4));                
                if(first4.equals("Data"))
                    iData.add(i);
                nameColumns[i] = Main.getOff_NameTable(nameColumns[i], tableName);
                nameColumns[i] = changeSomeNamesOfColumns(nameColumns[i]);
            }            
            table.setModel(new DefaultTableModel(nameColumns,0));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0;i<table.getColumnCount();i++){
                if(i==0 || (i==1 && tableName.equals("Transacao"))) width=width/2;
                table.getColumnModel().getColumn(i).setPreferredWidth(width);
                if(i==0 || (i==1 && tableName.equals("Transacao"))) width=width*2;
            }
            while (results.next()) {
                String columns[]=new String[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=results.getString(i+1);
                    if(iData.contains(i))
                        columns[i]=Main.SqlDateToNormalFormat(columns[i]);
                    if(i==iDesc)
                        columns[i]=formatDesc(columns[i]);
                }
                addRow(columns, table);
            } 
            int numCol=table.getColumnCount();
            if(tableName.equals("Transacao"))
                tablePanel.setPreferredSize(new Dimension(numCol*width-width,300)); 
            else
                tablePanel.setPreferredSize(new Dimension(numCol*width-width/2,300)); 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            exception.printStackTrace();
        } // end catch 
        
    } 
    public String changeSomeNamesOfColumns(String col){
        if(col.equals("ID_Caixa"))
            return "Caixa";
        if(col.equals("Com_SaldoCliente"))
            return "No saldo do cliente";
        if(col.equals("Preco_Merc"))
            return "Preço";
        return col;
    }
    public String formatDesc(String desc){
        if(desc==null)
            return desc;
        String split3[]= desc.split("###");
        if(split3.length<=1)
            return desc;        
        String descricao="";
        String products = split3[1];
        String[] split2 = products.split("##");
        int len2=split2.length;
        for(int i=0;i<len2;i++){
            String product = split2[i];            
            //Main.p(desc);
            String elemOfProduct[]=product.split("#");
            descricao+=elemOfProduct[1];
            double desconto = Main.formatDoubleString(elemOfProduct[4]);
            double valor = Main.formatDoubleString(elemOfProduct[5]);
            descricao+="("+Main.twoDig(valor-desconto)+")";
            if(i!=len2-1)
                descricao+=",";
        }
        return descricao;  
    }
    public static int getIndexInTableWithCod(JTable table, String cod){
        int colOfKey = Main.getIndexColumnWithColumnName(table, "ID");
        if(colOfKey==-1)
            colOfKey = Main.getIndexColumnWithColumnName(table, "Código");
        for(int i=0;i<table.getRowCount();i++)
            if(table.getValueAt(i, colOfKey).toString().equals(cod))
                return i;
        return -1;
    }
    public static String getOff_NameTable(String nameCol, String nameTable){
        if(nameCol.contains("_"+nameTable)){
            int len = nameCol.length();
            nameCol=nameCol.substring(0, len - nameTable.length()-1);
        }
        if(nameCol.equals("Preco_Merc"))
            return "Preço";
        return nameCol;
    }
    public static String[] getOff_NameTable(String[] nameCol, String nameTable){
        int len = nameCol.length;
        for(int i=0;i<len;i++)
            nameCol[i]=getOff_NameTable(nameCol[i], nameTable); 
        return nameCol;
    }
    public void setComboBox(String tableName, String columnName, JComboBox box){
        String query="SELECT * From "+tableName;        
        try{
            ResultSet results = executeQuery(query);
            int numberOfColumns=-1, columnOfDescricao=-1;
            LinkedList <String> list =new LinkedList(); 
            String s;
            if(results==null)
            {
                System.out.println("Nenhum resultado da busca foi encontrado.");
                return;
            }
            ResultSetMetaData metaData = results.getMetaData();
            numberOfColumns = metaData.getColumnCount();
            columnOfDescricao=-1;
            
            for (int i = 0; i < numberOfColumns; i++) {
                s=metaData.getColumnName(i+1);   
                if(s.equals(columnName))
                    columnOfDescricao = i;               
            } 
            for (int i=0;results.next();i++) {                
                list.add(results.getString(columnOfDescricao+1));
            }
            int len=list.size();
            String arrayS[]=new String[len+1];
            arrayS[0]="Escolha";            
            for(int i=1; i<len+1;i++)
                arrayS[i]=list.get(i-1);            
            box.setModel(new javax.swing.DefaultComboBoxModel<>(arrayS));
        }
        catch (Exception exception) {
            System.out.println("Erro ao pegar os tipos de mercadorias");
            exception.printStackTrace();
        } // end catch  
        
    }
    public static String getChoosedComboBox(JComboBox box){
        return (String) box.getModel().getSelectedItem(); 
    }
    public void addSaldoCliente(String nameClient, String creditToAdd){
        double credit = Double.parseDouble(creditToAdd);
        String saldoS = getColumnWithColumnKey("Cliente", "Nome_Cliente", "\'"+nameClient+"\'", "Saldo_Cliente");
        double saldo;
        try{
            saldo = Double.parseDouble(saldoS);
        }catch(Exception e){
            saldo =0;
        }
        saldo +=credit;
        saldoS=Main.twoDig(saldo);
        String query = "UPDATE Cliente SET Saldo_Cliente = "+saldoS+" WHERE Nome_Cliente = \'"+nameClient+"\'";
        try{            
            executeQuery(query);
        }catch(Exception e){}
    }
    public static int getIndexColumnWithColumnName(JTable table, String columnName){
        for(int i=0;i<table.getColumnCount();i++)
            if(table.getColumnName(i).equals(columnName))
                return i;
        return -1;
    }
    public String getColumnWithColumnKey(String tableName, String nameColumnKey, String columnKey, String nameColumn){       
        String query = "Select "+nameColumn+" from "+tableName+" where "+nameColumnKey+"= "+columnKey;        
        try{            
            ResultSet results = executeQuery(query);
            if(results==null){
                System.out.println("Nenhum resultado da busca foi encontrado.");
                System.out.println("Query: "+query);
                return null;
            }
            if(results.next())
                return results.getString(1);       
            else
                return null;
        }
        catch (Exception exception) {
            System.out.println("Erro ao pegar ao consultar a coluna");
            exception.printStackTrace();
            return null;
        } // end catch        
    }
    public String getColumnWithPrimaryKey(String tableName, String primaryKey, String nameColumn){
        return getColumnWithColumnKey(tableName, "ID_"+tableName, primaryKey, nameColumn);        
    }
    public void runBackup(){
        Main main = this;
        new Thread(){
            public void run(){
                new GenerateBackupOrRecoverDB(main).generateBackup();
            }
        }.start();
    }
    public class PathConnection {
        public Connection connection; // manages connection
        public Statement statement;

        PathConnection (Connection connect, Statement stat)        
        {
            connection=connect; // manages connection
            statement=stat;
        }
    }
    static class DateAndHour{
        String date;
        String hour;
        boolean isServerHour=false;
    }
}  

