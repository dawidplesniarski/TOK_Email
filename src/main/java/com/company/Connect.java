package com.company;

import java.sql.*;

public class Connect {
    public String driver = "org.postgresql.Driver";
    public String host = "195.150.230.210:5434";
    public String dbname = "2019_plesniarski_dawid";
    public String user = "2019_plesniarski_dawid";
    public String url = "jdbc:postgresql://" + host + "/" + dbname;
    private String pass = "12345";
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
        }
        catch(SQLException sqle)
        {
            System.err.println("Błąd przy zamykaniu połączenia: " + sqle);
        }
    }

    public Connection makeConnection(){
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
        }finally {
            System.gc();
        }

    }
}
