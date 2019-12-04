package edu.jsu.mcis.cs425.project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Database {

    
    public Connection getConnection() {
        Connection conn = null;
        try {
            
           Context  envContext = new InitialContext();
           Context initContext  = (Context)envContext.lookup("java:/comp/env");
           DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
           conn = ds.getConnection();
            
        }
        
        catch (Exception e) {
            e.printStackTrace();
        } 
        
        return conn;
    }
    
    public HashMap getUserInfo(String username){ 
        HashMap<String, String> search = null;
    
        try{
               Connection conn = getConnection();
               
               String query = "SELECT * FROM user WHERE username = ?";
               PreparedStatement pstatement = conn.prepareStatement(query);
               pstatement.setString(1, username);
               
               boolean hasresults = pstatement.execute();
               if( hasresults){
                   ResultSet resultset = pstatement.getResultSet();
                   if(resultset.next()){
                   
                       search = new HashMap<>();
                       
                       String displayname = resultset.getString("displayname");
                       String id = String.valueOf(resultset.getInt("id"));
                       search.put("id", id);
                       search.put("displayname",displayname);

                       
                   }
               }
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
        return search; 
    }
        
    }