package com.bridgeit.ToDoApp.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.bridgeit.ToDoApp.model.EmailSet;

public class MessageProducer {
	
	@Autowired
	JmsTemplate jmsTemplate;

	public void send(final EmailSet data) {
		System.out.println("email : " + data);
		jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				System.out.println("Before sending ");
				Message message = null;
				message = session.createObjectMessage(data);
				System.out.println("Message :" + message);
				return message;
			}

		});
	}

}
