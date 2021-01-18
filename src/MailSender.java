/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;
/**
 *
 * @author Clrkz
 */
public class MailSender {
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        // TODO code application logic here
        String username = "143Clarkz@gmail.com";
        String password = "09105242584Cat12345";
        String recipientEmail = "bytehax@gmail.com";
        //String filename = "a.jpg";
        String message = "Paste this code: ";
        sendEmail(username,password,recipientEmail,message);
    }
*/
    public static void sendEmail(String username, String password, String recipientEmail, String messagecontent) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("from@no-spam.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject("CDPO Reset Password");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messagecontent, "text/plain");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
/*
            // Attachment
            if (filename != null) {
                MimeBodyPart attachment = new MimeBodyPart();
//                DataSource source = new FileDataSource(FILENAME);
//                attachment.setDataHandler(new DataHandler(source));
//                attachment.setFileName(source.getName());
                attachment.attachFile(filename);
                multipart.addBodyPart(attachment);
            }
*/
            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Message to " + recipientEmail + " sent successfully.");
        //} catch (MessagingException | IOException e) {
          } catch (MessagingException  e) {   
            throw new RuntimeException(e);
        }
    }
}


