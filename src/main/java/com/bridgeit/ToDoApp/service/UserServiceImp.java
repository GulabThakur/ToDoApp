package com.bridgeit.ToDoApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.IUserDao;
import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public class UserServiceImp implements IuserService {

	@Autowired
	private IUserDao userModelDao;


	public int registration(UserModel user) {
		return userModelDao.register(user);
	}

	
	public void login(String email, String password) {
		userModelDao.login(email, password);
	}

	
	public UserModel update(int id) {
		UserModel user=userModelDao.update(id);
		return user;
	}

}
