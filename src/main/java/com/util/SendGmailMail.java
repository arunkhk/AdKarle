package com.util;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class SendGmailMail {

	  final static String username = "bazaaradkarle@gmail.com";
      final static String password = "adkarlebazaar@123";

	 public static void main(String[] args) {

	      
	     
	    }
	 
	 public static void sendMail(String Subject, String msg,String recepaint) {
		   Properties prop = new Properties();
					prop.put("mail.smtp.host", "smtp.gmail.com");
			        prop.put("mail.smtp.port", "587");
			        prop.put("mail.smtp.auth", "true");
			        prop.put("mail.smtp.starttls.enable", "true"); //TLS
			        
			        Session session = Session.getInstance(prop,
			                new javax.mail.Authenticator() {
			                    protected PasswordAuthentication getPasswordAuthentication() {
			                        return new PasswordAuthentication(username, password);
			                    }
			                });

			        try {

			            Message message = new MimeMessage(session);
			            message.setFrom(new InternetAddress(username));
			            message.setRecipients(
			                    Message.RecipientType.TO,
			                    InternetAddress.parse(recepaint)
			                   // InternetAddress.parse("to_username_a@gmail.com, to_username_b@yahoo.com")
			            );
			            message.setSubject(Subject);
			            message.setText(msg);
			/*
			 * message.setText("Dear Mail Crawler," + "\n\n Please do not spam my email!");
			 */
			            Transport.send(message);
			            System.out.println(" mail sent Done");

			        } catch (MessagingException e) {
			        	 System.out.println("MessagingException "+e.toString());
			            e.printStackTrace();
			        }
	 }

}
