package com.bridgeit.ToDoApp.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author ThakurGulab
 *
 */
public class EmailImp implements IEmail {
	@Autowired
	EmailProperties emailService;
	
	public String registration(String emailAddress, String token) {
		final String from = emailService.getEmail();
		final String psd=emailService.getPassword();
		System.out.println("Emaild::"+emailService.toString());
		if(emailService.getEmailAddress() != null && emailService.getEmailAddress() != "") {
			emailAddress = emailService.getEmailAddress(); 
		}
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, psd);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			message.setSubject("Docment");
			message.setText(token);
			Transport.send(message);
			System.out.println();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return "sucessfull";

	}

	public String forgotPassword(String email, String token) {
		return null;
	}

}
