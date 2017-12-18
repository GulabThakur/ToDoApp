package com.bridgeit.ToDoApp.dao;

import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author GulabThakur
 *
 */
public interface IUserDao {
	/**
	 * @param user
	 * @store the data into database
	 */
	public int register(UserModel user);

	/**
	 * @param email
	 * @param password
	 */
	public boolean login(String email, String password);

	/**
	 * @param password
	 * @param hash_psd
	 * @return return true when password will be match
	 */
	public boolean checkPsd(String password, String hash_psd);

	/**
	 * @param id
	 * @return 
	 * @this method well be using for update the password // Reset_password
	 */
	public UserModel update(UserModel user);

	/**
	 * @param email
	 * @return
	 */
	public UserModel checkExits(String email);
	/**
	 * @param id
	 * @return
	 */
	public UserModel getById(int id);
}
