package com.bridgeit.ToDoApp.validation;

import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public interface IValidation {
	/**
	 * @param user
	 * @return
	 */
	public boolean isName(UserModel user);

	/**
	 * @param user
	 * @return
	 */
	public boolean ispsd(UserModel user);

	/**
	 * @param user
	 * @return
	 */
	public boolean isemail(UserModel user);

	/**
	 * @param user
	 * @return
	 */
	public String valid(UserModel user);
}
