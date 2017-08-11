/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailClass {

    public static void main(int numberOfRowsInDB) {
        // Recipient's email ID needs to be mentioned.
        String to = "sorayasimaosgs@gmail.com";//change accordingly  
        String from = "sorayasimaosgs@gmail.com";//change accordingly  
        String host = "smtp.gmail.com";//or IP address  
        String user = "sorayasimaosgs@gmail.com";//change accordingly  
        String pass = "261287sgs";//change accordingly  
        
        //Get the session object  
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.trust", "*");
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(properties, null);
        mailSession.setDebug(false);
        Message msg = new MimeMessage(mailSession);
        
        //compose the message  
        try {
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Backup com "+numberOfRowsInDB+" linhas no DB");
            msg.setSentDate(new Date());
            //Create a multipar message:
            //----------------------------------------
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Backup do banco de dados da loja");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment:
            //-----------------------------------------
            messageBodyPart = new MimeBodyPart();
            String filename = "src/Main/backupString.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
             // Send the complete message parts
            msg.setContent(multipart);

            // Send message  
            Transport t = mailSession.getTransport("smtp");
            t.connect(host, user, pass);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            System.out.println("message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
