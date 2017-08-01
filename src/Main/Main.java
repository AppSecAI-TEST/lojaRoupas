package Main;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class Main {
    // JDBC driver name and database URL 
    PathConnection pathConnection;
    JTabbedPaneFrame tabbedPane;    
    public boolean caixaAberto;
    static DecimalFormat t=new DecimalFormat("0.00");
    public HashMap<String,String> caixaMap=new HashMap();
    Main() {        
        System.out.print(Main.isTimeValid("2:24:30"));
        pathConnection = getPathConnection("CriancaBonitaDB");
        tabbedPane = new JTabbedPaneFrame(this);
        tabbedPane.setVisible(true);
        tabbedPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane.setSize(700,700);
        // connect to database books and query database 
        while(tabbedPane.isEnabled()){}       
        close_connection(pathConnection.connection, pathConnection.statement);        
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
    public static boolean isDoubleValid(String doubleS){
        try{
            formatDoubleString(doubleS);
        }
        catch(Exception e){return false;}
        return true;
    }
    public static double formatDoubleString(String doubleS) 
    {        
        doubleS=doubleS.trim();
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
    public void setDataHoraPanelVenda(){
        if(tabbedPane==null)
            return;
        tabbedPane.setDataHoraPanelVenda();
    }
    public void setBooleanCaixaAberto(){
        if(tabbedPane!=null)
            tabbedPane.setBooleanCaixaAberto();
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
            System.out.println(exception);
        } // end catch
       
    }    
    public void addInCaixaMap(String key, String value){
        caixaMap.put(key, value);
    }
    public String getOfCaixa(String mykey){
        for(String key: caixaMap.keySet()){
            if(mykey.equals(key))
                return caixaMap.get(key);
        }
        System.out.println("Chave não encontrada em caixaMap");
        return null;
    }
    public void updateTable(boolean flagSelect, ResultSet results, JTable table, String tableName, JPanel tablePanel){
        if(flagSelect==false)            
            results =executeQuery("SELECT * FROM "+tableName);
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
                addRow(columns, table);
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            System.out.println(exception);
        } // end catch 
        int numCol=table.getColumnCount();
        tablePanel.setPreferredSize(new Dimension(numCol*width,300));
    }
    public void addRow(String[] row, JTable table){
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.addRow(row);
    }  
    public void removeRow(int row, JTable table){
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        model.removeRow(row);
    }  
    public ResultSet executeQuery(String query){
        query=prepareToDB(query);
        Statement statement=pathConnection.statement;
        try {
            String operacao = query.split(" ")[0];
            // query database with insert
            operacao =  operacao.toLowerCase();
            if(operacao.equals("insert") || operacao.equals("delete") || operacao.equals("update"))
            {
                System.out.println("Operacao realizada com sucesso");
                statement.executeUpdate(query);
                return null;
            }
            if(operacao.equals("select") )
                return statement.executeQuery(query);                     
            return null;
        } catch (Exception exception) {
            System.out.println("Erro na execução do query: "+query);   
            System.out.println(exception.toString());
            return null;
        } // end catch
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
            System.out.println(exception);
        }
        try{
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "andregsimao", "020410");

            // create Statement for querying database
            statement = connection.createStatement();
        } catch (SQLException exception) {            
            System.out.println("O banco de dados \""+ dataBaseName+"\" não foi encontrado");  
            System.out.println(exception);
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
            System.out.println(exception);
        } // end catch 
    }
    public static void main(String args[]) {
        Main main = new Main();
    } // end main
    public static boolean isEmpty(String s){
        if(s.trim().equals(""))
            return true;
        return false;
    }
    public static String prepareToDB(String query){
        query=query.toLowerCase();
        String before[] = new String[]{"á", "à", "ã", "â", "ï", "è", "é", "ë", "ê", "î", "í", "ó", "õ", "ô", "ú", "ù", "û", "ç" };
        String after[] = new String[] {"a", "a", "a", "a", "i", "e", "e", "e", "e", "i", "i", "o", "o", "o", "u", "u", "u", "c" };
        for(int i=0;i<before.length;i++){
            query=query.replaceAll(before[i], after[i]);
        }
        return query;        
    }
    public static String twoDig(double d){
        return t.format(d);
    }
    public static void cleanTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }
    public static String formatStringToSql(String columnType, String columnValue)
    {
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
    public static void p(String s){
        System.out.println(s);
    }
    public static String SqlDateToNormalFormat(String dataInSqlFormat){
        String[] separated=dataInSqlFormat.split("-");
        return  separated[2]+"/"+separated[1]+"/"+separated[0];
    }
    public void updateTable(JTable table, JPanel tablePanel, String tableName, boolean flagSelect, ResultSet results){
        if(flagSelect==false)            
            results =executeQuery("SELECT * FROM "+ tableName);
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
                addRow(columns, table);
            } 
        }
        catch (Exception exception) {
            System.out.println("Erro ao montar a tabela");
            System.out.println(exception);
        } // end catch 
        int numCol=table.getColumnCount();
        tablePanel.setPreferredSize(new Dimension(numCol*width,300));
    }
    
    class PathConnection {
        Connection connection; // manages connection
        Statement statement;

        PathConnection (Connection connect, Statement stat)        
        {
            connection=connect; // manages connection
            statement=stat;
        }
    }
}  // end class DisplayAuthors

