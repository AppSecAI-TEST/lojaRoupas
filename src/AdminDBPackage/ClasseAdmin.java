package AdminDBPackage;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
public class ClasseAdmin {
    // JDBC driver name and database URL 
    PathConnection pathConnection;
    InterfaceGUI interfaceGUI;
    ClasseAdmin() {        
        interfaceGUI = new InterfaceGUI(this);
        pathConnection = getPathConnection("CriancaBonitaDB");
        interfaceGUI.setPrimaryKeyInitialValues();
        // connect to database books and query database   
        interfaceGUI.createColumnsPaneAndTable(); 
        interfaceGUI.getInsertButton().doClick();
        while(interfaceGUI.isEnabled()){}       
        close_connection(pathConnection.connection, pathConnection.statement);        
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
            System.out.println("Table: "+interfaceGUI.getTableName());
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
    protected ResultSet executeQuery(String query){
        Statement statement=pathConnection.statement;
        try {
            String operacao = query.split(" ")[0];
            // query database with insert
            operacao =  operacao.toLowerCase();
            if(operacao.equals("insert") || operacao.equals("delete") || operacao.equals("update"))
            {
                interfaceGUI.setTaskStatusLabel("Operacao realizada com sucesso");
                statement.executeUpdate(query);
                return null;
            }
            if(operacao.equals("select") )
            {
                if(query.contains("*")==false)
                    interfaceGUI.setTaskStatusLabel("Consulta realizada com sucesso");
                return statement.executeQuery(query);
            }            
            return null;
        } catch (Exception exception) {
            System.out.println("Erro na execução do query: "+query);   
            interfaceGUI.setTaskStatusLabel(exception.toString());
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
            connection
                    = DriverManager.getConnection(DATABASE_URL, "andregsimao", "020410");

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
        new ClasseAdmin();
    } // end main
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

