/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eleanalysis;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Yan
 */
public class SampleSQL {
    Connection connection;
    Statement statement;
    File dbFile;
    
    SampleSQL(File dbFile){
        this.dbFile = dbFile;  
        connection = null;
        statement = null;
        try { 
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toString()+".db");
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            
            String sql = "CREATE TABLE IF NOT EXISTS SAMPLELIBRARY" + 
                    "(NAME TEXT NOT NULL," + 
                    "FIRSTELEMENT TEXT NOT NULL," + 
                    "SECONDELEMENT TEXT NOT NULL," +
                    "DATA TEXT NOT NULL," +
                    "ISWP INT NOT NULL)";
            //System.out.println("Creating DB: " +sql);
            statement.executeUpdate(sql);
            
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error " + e);
        }
    }
    SampleSQL(){
        this(new File("main"));
    }
    public void addSampleToDatabase(Sample s, int key){
          String isWP = s.isWP()?"1":"0";
          String sql = "INSERT INTO SAMPLELIBRARY" + 
                  "( NAME,FIRSTELEMENT,SECONDELEMENT,DATA,ISWP) " + 
                  "VALUES('" +s.getName()+ "','"+ s.getFirstElementName() +
                  "','"+ s.getSecondElementName()+"','" +stringifySample(s)+ "'," +
                  isWP + ");";
          //System.out.println(sql);
        try {
            statement.executeUpdate(sql);
            /*statement.close();
            connection.commit();
            connection.close();*/
        } catch (SQLException ex) {
            Logger.getLogger(SampleSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    private String stringifySample(Sample sam){
        StringBuilder sb =  new StringBuilder();
        sam.getArrayCopy().stream().forEach((e) -> {
            sb.append(e.getName()).append(",").append(e.getConc()).append(";");
        }); 
        return sb.toString();
    }
    List <Sample> queriedList(Sample searchKey){
        List<Sample> query = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT name,data, isWP FROM SAMPLELIBRARY WHERE FIRSTELEMENT='"
                    + searchKey.getFirstElementName() + "';");
            while(rs.next()){
                String samName = rs.getString("NAME");
                List<Element> data = stringToArray(rs.getString("DATA"));
                boolean isWP = rs.getInt("ISWP") == 1;
                query.add(new Sample(data,samName,isWP));
            }
            rs = statement.executeQuery("SELECT * FROM SAMPLELIBRARY WHERE SECONDELEMENT='"
                    + searchKey.getFirstElementName() + "';");
            while(rs.next()){
                String samName = rs.getString("NAME");
                List<Element> data = stringToArray(rs.getString("DATA"));
                boolean isWP = rs.getInt("ISWP") == 1;
                query.add(new Sample(data,samName,isWP));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SampleSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return query;
    }
    private static List<Element> stringToArray(String data){
        List<Element> array = new ArrayList<>();
        int i = 0;
        while(i < data.length()){
            StringBuilder elName = new StringBuilder();
            StringBuilder conc = new StringBuilder();
            while(i < data.length() && data.charAt(i)!=',' ){
                elName.append(data.charAt(i++));
            }
           
            i++; // skip over ','
            while(i < data.length() && data.charAt(i)!=';' ){
                conc.append(data.charAt(i++));
            }
            i++; // skip over ';'
            System.out.println("Adding " + elName.toString()+ "/" + conc.toString());
            array.add(new Element(elName.toString(),Double.parseDouble(conc.toString()),true));
        }
        return array;
    }
    
    @Override
    protected void finalize() throws Exception {
        try {
            super.finalize();
            statement.close();
            connection.commit();
            connection.close();
        } catch (Throwable ex) {
            Logger.getLogger(SampleSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void main(String args[]){
//        File dbFile = new File("C:\\Users\\Yan\\Documents\\NetBeansProjects\\EleAnalysis\\library\\main");
//        SampleSQL s = new SampleSQL(dbFile);
//        String samName = "test Sample";
//        List <Element> array = new ArrayList<>();
//        array.add(new Element("Fe", 70, true));
//        array.add(new Element("Na", 30, true));
//        List <Element> array2 = new ArrayList<>();
//        array2.add(new Element("Fe", 70, true));
//        array2.add(new Element("Zn", 30, true));
//        List <Element> array3 = new ArrayList<>();
//        array3.add(new Element("Fe", 70, true));
//        array3.add(new Element("Mg", 30, true));
//        Sample sam = new Sample(array, samName, true);
//        Sample sam2 = new Sample(array2, "CSB", true);
//        Sample sam3 = new Sample(array3, "Brool Story Co", true);
//        s.addSampleToDatabase(sam,4);
//         s.addSampleToDatabase(sam2,5);
//         s.addSampleToDatabase(sam3,6);
//    }
//    
//    
    
}
