package com.bridgeit.ToDoApp.model;

/**
 * @author ThakurGulab
 *
 */
public class Response {
	private String message;
	private Object object;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
