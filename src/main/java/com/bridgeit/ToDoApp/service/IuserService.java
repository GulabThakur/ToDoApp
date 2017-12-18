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
	public int registration(UserModel user);

	/**
	 * @param email
	 * @param password
	 * @this method using for login user
	 */
	public boolean login(String email, String password);

	/**
	 * @param id
	 * @return 
	 * @this method will be using for update 
	 */
	public UserModel update(UserModel user);
	/**
	 * @param email
	 * @return
	 */
	public UserModel getDataByEmail(String email);
	/**
	 * @param id
	 * @return
	 */
	public UserModel getDataById(int id);
}
