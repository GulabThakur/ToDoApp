package com.bridgeit.ToDoApp.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.email.IEmail;
import com.bridgeit.ToDoApp.model.EmailSet;

public class MessageCounsumer implements MessageListener {
   
	@Autowired
	private IEmail email;

	public void onMessage(Message message) {
		try {
			System.out.println("In consumer");
			ObjectMessage msg = (ObjectMessage) message;
			EmailSet mail = (EmailSet) msg.getObject();
			email.registration(mail.getEmail(), mail.getToken(), "7024082813");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
