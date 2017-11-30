package com.bridgeit.ToDoApp.email;

/**
 * @author ThakurGualb
 *
 */
public interface IEmail {
	/**
	 * @param string
	 * @return
	 */
	public String registration(String user ,String token,String psd);
	/**
	 * @param email
	 * @return
	 */
	public String forgotPassword(String email ,String token); 
}
