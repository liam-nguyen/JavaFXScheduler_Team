package javafxscheduler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

   public static void main(String [] args) {    
       try {
           String to   = "ducchinh0806@gmail.com";
           String user = "kaboomcasta1@gmail.com";
           
           // Assuming you are sending email from localhost
           String host = "smtp.gmail.com";
           String pass = "say2tome";
           
           String subject = "Testing"; 
           String messageText = "This is Testing Email"; 
           boolean sessionDebug = false;

           // Get system props
           Properties props = System.getProperties();
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.host", host);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");
           props.put("mail.smtp.starttls.required", "true");
           
           java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                      
           Session mailSession = Session.getDefaultInstance(props, null);
           
           mailSession.setDebug(sessionDebug);
           Message msg = new MimeMessage (mailSession); 
           msg.setFrom(new InternetAddress(user));
           InternetAddress[] address = {new InternetAddress(to)}; 
           msg.setRecipients(Message.RecipientType.TO, address);
           msg.setSubject(subject); 
           msg.setSentDate(new Date()); 
           msg.setText(messageText);
           
           Transport transport = mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close(); 
           System.out.println("Send sucessfully");
       } catch (MessagingException ex) {
           Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}