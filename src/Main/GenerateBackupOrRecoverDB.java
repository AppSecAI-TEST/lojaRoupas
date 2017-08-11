package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre Simao
 */
public class GenerateBackupOrRecoverDB {
    HashSet <String>tableNamesSet=new HashSet();    
    boolean isEmptyDB=true;
    Main lojaDB;
    public GenerateBackupOrRecoverDB(Main lojaDB){
        this.lojaDB=lojaDB;
    }
    public void generateBackup(){
        String backup= generateBackupString();
        if(isEmptyDB)
            JOptionPane.showMessageDialog(null, "Banco de dados vazio! O backup não foi realizado");
        else{
            generateFile(backup, "src/Main/backupString.txt");            
            MailClass.main(getCurrentTotalRows());
            JOptionPane.showMessageDialog(null, "Backup realizado corretamente");
            try {
                Files.delete(Paths.get("src/Main/backupString.txt"));
            } catch (IOException ex) {
                Main.p("Erro ao deletar o arquivo");
            }            
        }           
    }
    public void generateFile(String backup, String fileName){
        try{
            PrintWriter pw = new PrintWriter(fileName);  
            pw.printf(backup);
            pw.flush();
            pw.close();
        }catch(Exception e){
            e.printStackTrace();
            Main.p("Erro ao gerar o backup");
        }        
    }
    public void recoverDB(){            
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------
        try{             
            String backupString = getFileString("src/Main/backupString.txt");
            String backupSql=getQueriesOfBackup(backupString);            
            String message = "Tem certeza que deseja recuperar os dados?";        
            int answ = JOptionPane.showConfirmDialog(null, message, "Confirmação", JOptionPane.OK_CANCEL_OPTION);
            if (answ != JOptionPane.OK_OPTION) 
                return;        
            if(lojaDB.askPassword(null, true)==false)   
                return;            
            getFormatAndExecuteQueryFileString("src/Main/query.sql");
            generateFile(backupSql, "src/Main/backup.sql");            
            executeQueriesOfSqlFile("src/Main/backup.sql");
            Files.delete(Paths.get("src/Main/backupString.txt"));
            Files.delete(Paths.get("src/Main/backup.sql"));            
            JOptionPane.showMessageDialog(null, "Recuperação feita com sucesso!");
        }catch(FileNotFoundException e){
            String message="Coloque o arquivo backupString.txt em  src/Main/";
            message+="\nDepois esse arquivo é deletado por segurança.\n Para prevenir, faça um novo backup antes defazer essa recuperação.";
            message+="\nEntrar no novo email da mamae (sorayasimaosgs@gmail.com) com (261287sgs) para pegar esse arquivo.";            
            JOptionPane.showMessageDialog(null, message);
            e.printStackTrace();
        }catch(Exception e){
            Main.p("Erro ao tentar fazer o backup");
            e.printStackTrace();
        }               
    }
    public void getFormatAndExecuteQueryFileString(String url){
        File file = new File(url);
        try {
            Scanner scanner = new Scanner(file);
            String currentLine="";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.trim().length()>2)
                    if(line.trim().substring(0,2).equals("--") )
                        continue;                
                if(line.isEmpty()==false)
                    currentLine+=" "+line;
                if(line.contains(";")){       
                    //Main.p(currentLine);
                    lojaDB.executeUpdate(currentLine);
                    currentLine="";
                }                
            }
        } catch(Exception e) { 
            e.printStackTrace();
        }
    }    
    public String getFileString(String url)throws FileNotFoundException{
        File file = new File(url);
        StringBuffer fileString=new StringBuffer();
        Scanner scanner=null;
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) 
            fileString.append(scanner.nextLine());            
        scanner.close();        
        return new String(fileString);
    }
    public void executeQueriesOfSqlFile(String url){
        File file = new File(url);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.trim().isEmpty()==false){
                    //Main.p(line);
                    lojaDB.executeQuery(line );  
                }
            }
            scanner.close();
        } catch(Exception e) { 
            e.printStackTrace();
        }        
    }
    public String generateBackupString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        Date dateHour = new Date();
        String shakeDate=dateFormat.format(dateHour);
        String[] split = shakeDate.split("/");
        String date=split[2]+"/"+split[1]+"/"+split[0];
        String hour=hourFormat.format(dateHour);
        String backupString ="Gerado em "+date+" ("+hour+")\n";        
        backupString+="Para fazer o caractere separador ¥ clique 'alt' e '-'\n";
        backupString+="Para fazer o backup, coloque o arquivo backupString.txt em  src/Main/, e"
                +     "\nexecute o método recoverDB da classe GenerateBackupOrRecoverDB. (BASTA DESCOMENTAR UMA LINHA NO CONSTRUTOR DE Main) \n"+
                     "\nDepois esse arquivo é deletado por segurança. Portanto, salve-o em outro lugar"+
                "\nEntrar no novo email da mamae (sorayasimaosgs@gmail.com) com (261287sgs) para pegar esse arquivo";
        setTableNames();
        for(String tableName: tableNamesSet){ 
            String stringOfTable = mountStringOfTable(tableName); 
            backupString+= stringOfTable;   
        }         
        return backupString;
    }
    private String mountStringOfTable(String tableName){
        String tableString="¥¥¥¥\n\n";
        ResultSet resultSet=null;
        try{
            resultSet =lojaDB.executeQuery("SELECT * FROM "+ tableName);
        }catch(Exception e){e.printStackTrace();}
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
            boolean emptyTable=true;
            while (resultSet.next()) {
                emptyTable=false;
                isEmptyDB=false;
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
            if(emptyTable)
                aux.append("@@");
            int len = aux.length();
            if(emptyTable==false)
                aux=aux.replace(len-2, len, "");
            tableString+=aux;
            tableString+="\n\n";
        }
        catch (Exception exception) {
            System.out.println("Erro ao coletar a tabela "+ tableName);
            exception.printStackTrace();
        } // end catch 
        return tableString;
    } 
    private int getCurrentTotalRows(){
        setTableNames();
        ResultSet resultSet=null;
        int numberOfRows=0;
        for(String tableName: tableNamesSet){
            try{
                resultSet =lojaDB.executeQuery("SELECT * FROM "+ tableName);
            }catch(Exception e){e.printStackTrace();}
            numberOfRows+=lojaDB.getNumberRowsOfQuery(resultSet);
        }
        return numberOfRows;       
    }
    private void setTableNames(){
        try{
            DatabaseMetaData md = lojaDB.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);            
            while (rs.next()){
                String s = rs.getString(3);
                if(s.toLowerCase().equals("senha")==false)
                    tableNamesSet.add(s);
            } 
              
        }catch(Exception e){
            e.printStackTrace();
            Main.p("Erro ao coletar o nome das tabelas no banco de dados");
        }
    }
    public String getQueriesOfBackup(String backup){
        String queriesOfAllTables="";
        String[] split4=backup.split("¥¥¥¥");
        queriesOfAllTables+="-- "+split4[0]+"\n";
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
                String c = nameColumn[i];
                if(isValidSituation(c, arrayValues[i], tableName)==false)
                    continue;
                aux.append(c);        
                aux.append(", ");
            }
            int len = aux.length();
            aux=aux.replace(len-2, len, "");
            aux.append(") VALUES (");
            for(int i=0;i<numCols;i++){                
                String v = arrayValues[i];
                if(isValidSituation(nameColumn[i], v, tableName)==false)
                    continue;    
                aux.append(v);    
                aux.append(", ");
            }
            len = aux.length();
            aux=aux.replace(len-2, len, "");
            aux.append(");\n");
        }
        queries+=aux;
        return queries;        
    }
    private boolean isValidSituation(String columnName, String value, String tableName){
        if(columnName.equals("ID_Mercadoria"))
            return true;
        if(value.equals("null")||value.equals("")||value.equals("\'\'"))
            return false;
        if(columnName.toLowerCase().equals("id_"+tableName))
            return false;
        return true;
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
        if(split2[1].trim().equals("@@")) //sinal que a tabela está vazia
            return listOfRows;        
        int len2 = split2.length;
        for(int i=1;i<len2;i++){            
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
