package com.company;

//import sun.misc.FormattedFloatingDecimal;

import javax.swing.*;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connect connect = new Connect();
        Statement statement = connect.getConnection().createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM dziekanat.studenci");


            try{
                JFrame frame = new JFrame();
                Form form = new Form();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setContentPane(form.getPanel());

                frame.pack();

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                rs.close();
                statement.close();
                connect.close();
                System.gc();
            }


    }

    public static void SendMail() throws SQLException {
        final String username = "testowyzych@gmail.com";
        final String password = "zaq1@WSX";

        Form form = new Form();


        Connect connect = new Connect();
        Statement emails = connect.getConnection().createStatement();


        ResultSet adressRS = emails.executeQuery("SELECT * FROM dziekanat.mail");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("testowyzych@gmail.com"));
            /*
            InternetAddress[] address = new InternetAddress[email.size()];
            for (int i = 0; i < email.size(); i++) {
                address[i] = new InternetAddress((String) email.get(i));
            }

            message.setRecipients(Message.RecipientType.TO, address);
            */


            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(String.valueOf(Form.emailAddress))
            );

            System.out.println("Klasa Main"+Form.emailAddress);

            message.setSubject(Form.emailSubject);
            message.setText(Form.emailContent);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }finally {
            connect.close();
            emails.close();
            adressRS.close();
        }

    }
}