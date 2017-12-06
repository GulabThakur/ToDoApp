package com.bridgeit.ToDoApp.model;

import java.io.Serializable;

/**
 * @author ThakurGulab
 *
 */
public class EmailSet implements Serializable {

	private static final long serialVersionUID = -4064355262538594091L;
	private String email;
	private String token;
	private EmailSet object;

	public EmailSet getObject() {
		return object;
	}

	public void setObject(EmailSet object) {
		this.object = object;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
