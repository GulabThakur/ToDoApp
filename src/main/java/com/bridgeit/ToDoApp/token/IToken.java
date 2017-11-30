package com.bridgeit.ToDoApp.token;

/**
 * @author ThakurGulab
 *
 */
public interface IToken {
	/**
	 * @return token
	 */
	public String genratedToken(int id);

	/**
	 * @return id 
	 */
	public int varifyToken(String token);
}
