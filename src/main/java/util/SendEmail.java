package util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
*
* @author eleon
*/
public class SendEmail {
    
    private static final String SERVER_HOST = "smtp.hostinger.com"; //"mail.softpoint.com.py";
    private static final String SERVER_PORT = "465";
    private static final String SERVER_SSL = "true";
    private static final String MAIL_FROM = "facturacion@elcacique.com.py"; // "eleon@softpoint.com.py"; 
    
    //---
    private Session session;
    private MimeMessage msg;
    
    
    /**
    * 
    * @param mailTo
    * @param emialSubject
    * @param emailBody
    * @return String Status
    */
    public String enviarEmail ( String mailTo, String emailSubject, String emailBody, List<String> atachment) {
        
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SERVER_HOST);
        prop.put("mail.smtp.port", SERVER_PORT);
        prop.put("mail.smtp.ssl.enable", SERVER_SSL);
        //prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.auth", "true");

        session = Session.getInstance(prop, new javax.mail.Authenticator() 
        {
        
             protected PasswordAuthentication getPasswordAuthentication() 
             {
                return new PasswordAuthentication(MAIL_FROM, "Fact2022ra*");
             }
             
        });
        
        session.setDebug(true);
        
            try {
                
                msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(MAIL_FROM));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
                msg.setSubject(emailSubject);
                
                if ( atachment != null ) {
                    Multipart multipart = new MimeMultipart();
                    @SuppressWarnings("rawtypes")
					Iterator itr = atachment.iterator();
                    
                    while (itr.hasNext()) 
                    {
                        String x = (String) itr.next();
                        if ( x != null  && x.length() > 0) {
                            MimeBodyPart atch = new MimeBodyPart();
                            DataSource ds = new  FileDataSource(x);
                            atch.setDataHandler(new DataHandler(ds));
                            atch.setFileName(new File(x).getName());
                            multipart.addBodyPart(atch);
                        }
                    }
                    
                    if ( emailBody != null ) {
                        MimeBodyPart msgText = new MimeBodyPart();
                        msgText.setText(emailBody);
                        multipart.addBodyPart(msgText);

                    }

                    msg.setContent(multipart);
                           
                }
                
                Transport.send(msg);
                
                
            } catch (MessagingException ex) {
                Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
                return ex.getMessage().toString();
            }
        
        return "OK";
    }
}
