package com.bridgeit.ToDoApp.service;

import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public interface IuserService {
	/**
	 * @param user
	 * @this registration  into data base
	 */
	public void registration(UserModel user);

	/**
	 * @param email
	 * @param password
	 * @this method using for login user
	 */
	public void login(String email, String password);

	/**
	 * @param email
	 * @this method will be using for update 
	 */
	public void update(String email);
}
