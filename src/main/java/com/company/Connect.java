package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connect {

    public Connection connection;

    public Connect(){
        connection = makeConnection();
    }

    public Connection getConnection(){
        return (connection);
    }

    public void close(){
        try{
            connection.close();
        }catch(SQLException sqle) {
            System.err.println("Błąd przy zamykaniu połączenia: " + sqle);
        }
    }

    public Connection makeConnection(){
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/data.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dbname = properties.getProperty("dbName");
        String user = properties.getProperty("dbUser");
        String pass = properties.getProperty("dbPass");

        String driver = "org.postgresql.Driver";
        String host = "195.150.230.210:5434";
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url,user, pass);
            return(connection);
        }

        catch (ClassNotFoundException cnfe){
            System.err.println("Blad ladowania sterownika " + cnfe);
            return (null);
        }

        catch(SQLException sqle){
            System.err.println("Blad przy nawiazywaniu polaczeia: " + sqle);
            return null;
        }finally{
            System.gc();
        }

    }
}
