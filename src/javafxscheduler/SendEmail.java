/***
 * This class is a blueprint to send email reminder. 
 */

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
    /** Fields **/
    private String emailTo; 
    private String emailFromUser = "kaboomcasta1@gmail.com";
    private String emailFromPass = "say2tome";

    /** Constructor **/
    public SendEmail (String to) {
        emailTo = to; 
    }
    
    /** Setters and Getters **/
   public String getEmailTo() {    
        return emailTo;
   }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailFromUser() {
        return emailFromUser;
    }

    public void setEmailFromUser(String emailFromUser) {
        this.emailFromUser = emailFromUser;
    }

    public String getEmailFromPass() {
        return emailFromPass;
    }
    
    public void setEmailFromPass(String emailFromPass) {
        this.emailFromPass = emailFromPass;
    }
    
    /********************************** METHODS **********************************/
    /**
     * This sends email to the address with a message.
     * @param message: The message in the email.
     * @param toEmailAddress: the email address sending to. 
     */
    public void send (String message, String toEmailAddress) {
        try { 
            //Contents
            String subject = "Reminder";
            boolean sessionDebug = false;
            
            // Assuming you are sending email from localhost
            String host = "smtp.gmail.com";
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
            
            msg.setFrom(new InternetAddress(emailFromUser));
            InternetAddress[] address = {new InternetAddress(emailTo)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);
            
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, emailFromUser, emailFromPass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            
            System.out.println("Send sucessfully");
            
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}