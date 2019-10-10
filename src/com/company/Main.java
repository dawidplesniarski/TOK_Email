package com.company;

//import sun.misc.FormattedFloatingDecimal;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {




        Connect connect = new Connect();
        Statement statement = connect.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM dziekanat.studenci");

        JFrame frame = new JFrame();
        GUI form = new GUI();



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(form.getPanel());

        frame.pack();

        frame.setVisible(true);




    }
}