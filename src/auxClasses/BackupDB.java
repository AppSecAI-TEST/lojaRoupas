package auxClasses;

import Main.Main;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre Simao
 */
public class BackupDB {
    Statement statement;
    Connection connection;
    HashSet <String>tableNamesSet=new HashSet();
    public BackupDB(Main.PathConnection pathConn){
        statement=pathConn.statement;
        connection=pathConn.connection;
        String backup = generateBackupString();
        //Main.p(backup);
        String queries = recoverDB(backup);
        Main.p(queries);
    }
    public String generateBackupString(){
        String backupString="Para fazer o caractere separador ¥ clique 'alt' e '-'\n\n";
        setTableNames();
        for(String tableName: tableNamesSet){
            String query = "Select * from "+tableName;  
            String stringOfTable = mountStringOfTable(tableName); 
            backupString+= stringOfTable;   
        }         
        return backupString;
    }
    private String mountStringOfTable(String tableName){
        String tableString="¥¥¥¥\n\n";
        ResultSet resultSet =Main.executeQuery(statement, "SELECT * FROM "+ tableName);
        int width=100;
        if(resultSet==null){
            tableString+= "Nenhum resultado na tabela "+tableName;
            return tableString;
        }
        try{
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            tableString+="Nome da tabela: \n¥¥\n"+ tableName+"\n¥¥¥\n";            
            tableString+="\nNome e tipo das colunas:\n¥¥";
            String nameColumns[]=new String[numberOfColumns];      
            for (int i = 0; i < numberOfColumns; i++) {
                nameColumns[i]=metaData.getColumnName(i+1);
                String type = metaData.getColumnClassName(i+1);
                tableString+="\n"+nameColumns[i]+"¥"+type+"\n¥¥";               
            }            
            tableString+="¥\n\n";  
            tableString+="Valor das colunas: \n¥¥";
            StringBuffer aux=new StringBuffer();
            while (resultSet.next()) {
                String columns[]=new String[numberOfColumns];
                aux.append("\n");
                for (int i = 0; i < numberOfColumns; i++) {
                    columns[i]=resultSet.getString(i+1);
                    if(columns[i]!=null && columns[i].isEmpty())
                        aux.append(" "+"¥");
                    else
                        aux.append(columns[i]+"¥");
                }
                aux.append("¥");
            } 
            tableString+=aux;
            tableString+="\n\n";
        }
        catch (Exception exception) {
            System.out.println("Erro ao coletar a tabela "+ tableName);
            exception.printStackTrace();
        } // end catch 
        return tableString;
    } 
    private void setTableNames(){
        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);            
            while (rs.next()){
                String s = rs.getString(3);
                tableNamesSet.add(s);
            } 
              
        }catch(Exception e){
            e.printStackTrace();
            Main.p("Erro ao coletar o nome das tabelas no banco de dados");
        }
    }
    public static void main(String s[]){
        new Main(false);
    }
    public String recoverDB(String backup){
        String queriesOfAllTables="";
        String[] split4=backup.split("¥¥¥¥");
        queriesOfAllTables+="-- "+split4[0];
        int len4=split4.length;
        int numTables=len4-1;
        for(int i=1;i<len4;i++){
            String tabelaCompleta=split4[i];
            String queriesOfTable = getQueriesOfTabelaCompleta(tabelaCompleta);  
            queriesOfAllTables += queriesOfTable;   
            queriesOfAllTables+="\n";
        }
        return queriesOfAllTables;
    }
    private String getQueriesOfTabelaCompleta(String tabelaCompleta){
        String queries="";
        String[] split3 = tabelaCompleta.split("¥¥¥");   
        String tableNameWithTrash=split3[0];
        String tableName= tableNameWithTrash.split("¥¥")[1];
        tableName = tableName.trim();
        
        queries +="-- tabela: "+tableName+"\n";
        //--------------------------------------------------------
        String nameAndTypeOfColumnsWithTrash=split3[1];
        NamesAndTypes namesAndTypes = getNamesAndTypes(nameAndTypeOfColumnsWithTrash);
        String nameColumn[] = namesAndTypes.names;
        String type[] = namesAndTypes.types;
        //--------------------------------------------------------
        String valuesOfColumnsWithTrash=split3[2];
        LinkedList<String[]> listOfArrayValues= getValues(valuesOfColumnsWithTrash, type);
        //--------------------------------------------------------
        StringBuffer aux= new StringBuffer();
        for(String[] arrayValues: listOfArrayValues){            
            aux.append("INSERT INTO "+tableName+" (");
            int numCols = nameColumn.length;
            for(int i=0;i<numCols;i++){
                aux.append(nameColumn[i]);
                if(i!=numCols-1)        
                    aux.append(", ");
                else
                    aux.append(")");
            }
            aux.append(" VALUES (");
            for(int i=0;i<numCols;i++){                
                                    
                aux.append(arrayValues[i]);
                if(i!=numCols-1)        
                    aux.append(", ");
                else
                    aux.append(")");
            }
            aux.append(";\n");
        }
        queries+=aux;
        return queries;        
    }
    private NamesAndTypes getNamesAndTypes(String nameAndTypeOfColumnsWithTrash){
        NamesAndTypes namesAndTypes = new NamesAndTypes();
        String split2[] = nameAndTypeOfColumnsWithTrash.split("¥¥");
        int len2 = split2.length;
        int numCols= len2-1;
        String names[]=new String[numCols];
        String types[]=new String[numCols];
        for(int i=1;i<len2;i++){            
            String nameAndType = split2[i];
            String [] split1 = nameAndType.split("¥"); 
            names[i-1]=split1[0].trim();
            types[i-1]=split1[1].trim();            
        }       
        namesAndTypes.names=names;
        namesAndTypes.types=types;   
        return namesAndTypes;
    }
    private LinkedList<String[]> getValues(String valuesOfColumnsWithTrash, String type[]){
        String[] split2 = valuesOfColumnsWithTrash.split("¥¥");
        LinkedList<String[]> listOfRows =new LinkedList();
        int len2 = split2.length;
        for(int i=1;i<len2-1;i++){            
            String valuesOfRows = split2[i];
            String[] rowArray = valuesOfRows.split("¥");
            rowArray = formatValues(type, rowArray);
            listOfRows.add(rowArray);
        }
        return listOfRows;
    }
    private String[] formatValues(String[] type, String[] arrayValues){
        int len = arrayValues.length;
        for(int i=0;i<len;i++)
            arrayValues[i]=formatValue(type[i], arrayValues[i]);
        return arrayValues;
    }        
    private String formatValue(String type, String columnValue){
        columnValue=columnValue.trim(); 
        if(columnValue.equals("null"))
            return columnValue;
        switch(type){
            case "java.lang.String":                
                columnValue=columnValue.replace("\\", "\\\\");
                columnValue=columnValue.replace("'", "\'");
                return "\'"+columnValue+"\'";            
            case "java.sql.Date":
                return "\'"+columnValue+"\'";            
            case "java.sql.Time":
                return "\'"+columnValue+"\'";
        }
        return columnValue;
    }
    class NamesAndTypes{
        String names[];
        String types[];        
    }
}
