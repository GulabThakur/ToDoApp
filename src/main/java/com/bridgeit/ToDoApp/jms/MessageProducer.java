package com.bridgeit.ToDoApp.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageProducer {
	
	@Autowired
	JmsTemplate jmsTemplate;

	public void send(String email) {
		System.out.println("email : " + email);
		jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				System.out.println("Before sending ");
				Message message = null;
				message = session.createObjectMessage(email);
				System.out.println("Message :" + message);
				return message;
			}

		});
	}

}
