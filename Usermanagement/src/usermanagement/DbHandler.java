/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usermanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dilshan
 */
public class DbHandler {
    Connection connection;
    Statement statement;
    ResultSet rs;

    public DbHandler() {
        try {
             Class.forName("org.sqlite.JDBC");
    connection =DriverManager.getConnection("jdbc:sqlite:usermanagemrnt.db");
            System.out.println("connected");
        } catch (Exception e) {
            System.out.println("error Connect"+e);
        }
   
    
    
    }
    public  void CreateTables(){
        try {
               statement=connection.createStatement();
               String query="CREATE TABLE if not exists users ( " +
"  id Integer PRIMARY KEY AUTOINCREMENT," +
"  name varchar(50) NOT NULL unique ," +
"  city varchar(50) NOT NULL" +

")";
               statement.execute(query);
               System.out.println("Table Ctraeted");
        } catch (Exception e) {
            System.out.println("error table create "+ e);
        }
     
        
    }
    public Boolean addUser(String name, String city ){
        try {
             PreparedStatement preparedStatement =connection.prepareStatement("Insert into users values(?,?,?)");
             preparedStatement.setString(1,null );
             preparedStatement.setString(2,name );
             preparedStatement.setString(3,city );
             preparedStatement.execute();
             System.out.println("add user");
             return true;
        } catch (Exception e) {
            System.out.println("add user error "+e);
            return false;
        }
       
        
        
    }
    public ResultSet getUsers(){
     
        try {
             statement=connection.createStatement();
            rs=statement.executeQuery("Select * from users ");
           return rs;
        } catch (Exception e) {
            System.out.println("get user error "+e);
            return null;
        }
         
        
    }
    
    public Boolean DeleteUser(String x){
        try {
            PreparedStatement preparedStatement =connection.prepareStatement("DELETE FROM users WHERE id=? ");
            preparedStatement.setString(1, x);
            preparedStatement.executeUpdate();
            System.out.println("Delete user");
             return true;
        } catch (Exception e) {
              System.out.println("Delete user error"+e);
              return false;
        }
       
    }
    
     public Boolean UpdateUser(String x,String name,String city){
        try {
            PreparedStatement preparedStatement =connection.prepareStatement("Update users set name=?,city=? WHERE id=? ");
            preparedStatement.setString(1, name);
             preparedStatement.setString(2, city);
              preparedStatement.setString(3, x);
            preparedStatement.executeUpdate();
            System.out.println("Update user");
             return true;
        } catch (Exception e) {
              System.out.println("Update user error"+e);
              return false;
        }
       
    }
    
    
    
    
}
