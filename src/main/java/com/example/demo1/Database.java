package com.example.demo1;
import  java.sql.*;

public class Database {
    public static Connection connectionDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/Employe", "root", "");
            return connect;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
