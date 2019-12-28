package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form {
    private JComboBox schemas;
    private JComboBox tables;
    public JTable table1;
    private JPanel panel;
    private JButton SendButton;
    private JTextField subject;
    private JTextArea content;
    public static String emailAddress;
    public static String emailSubject;
    public static String emailContent;



    private JLabel label = new JLabel("sdasdsadsa");
    private Connect connect = new Connect();

    public Form() throws SQLException {
        String [] types = {"TABLE"};
        DatabaseMetaData dbmd = connect.getConnection().getMetaData();
        ResultSet[] tableResultSet = new ResultSet[1];
        ResultSet schemaResultSet = dbmd.getSchemas();
        SendButton.setVisible(false);
        subject.setVisible(false);
        content.setVisible(false);


        while(schemaResultSet.next())
            schemas.addItem(schemaResultSet.getString(1));


        schemas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tables.removeAllItems();
                String selection = (String) schemas.getSelectedItem();
                System.out.println(selection);

                System.out.println(table1.getColumnCount());
                try {
                    tableResultSet[0] = dbmd.getTables(null, selection, "%", types);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                while(true){
                    try {
                        if (!tableResultSet[0].next())
                            break;
                        tables.addItem(tableResultSet[0].getString(3));     /** wyswietlam zawartosc tabel  */
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
            }

        });


        tables.addActionListener(new ActionListener() {     /** Wypisywanie wyniku zapytania   */
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = (String) tables.getSelectedItem();
                try {
                    DefaultTableModel model = new DefaultTableModel();
                    Statement statement = connect.getConnection().createStatement();
                    String query = "SELECT * FROM " + schemas.getSelectedItem() + "." + selection;
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    ArrayList<String> l = new ArrayList<>();
                    for(int i = 1; i <= rsmd.getColumnCount(); i++)
                        model.addColumn(""+i, new String[]{rsmd.getColumnName(i)});
                    while (resultSet.next())
                    {

                        for(int i = 1; i <= rsmd.getColumnCount(); i++)
                        {

                            l.add(resultSet.getString(i));

                            //System.out.print(resultSet.getString(i) + " ");
                        }
                        model.addRow(l.toArray());
                        l.clear();
                    }

                    table1.setModel(model);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        tables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            JComboBox tables = (JComboBox) actionEvent.getSource();
            String selected = (String) tables.getSelectedItem();
                System.out.println(selected);

                //EMAIL_ADDRESS_PATTERN.matcher(email).matches()
                //String regexPattern = "^(.+)@(.+)$";

                assert selected != null;

                if(selected.equals("mail")){
                    SendButton.setVisible(true);
                    subject.setVisible(true);
                    content.setVisible(true);
                }


                /******
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                if(emailAddress.matches(regex))
                {
                    SendButton.setVisible(true);
                }

                *******/



            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                emailAddress = (String)table1.getValueAt(row, column);  // ustawiam adres email docelowy
                System.out.println(emailAddress);

            }
        });


        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    emailSubject = subject.getText().toString();
                    emailContent = content.getText().toString();
                    Main.SendMail();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    connect.close();
                    System.gc();
                }
            }
        });

    }


    public JPanel getPanel(){
        return panel;
    }
}
