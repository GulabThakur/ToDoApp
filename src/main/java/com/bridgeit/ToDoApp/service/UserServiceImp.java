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


	public void registration(UserModel user) {
		userModelDao.register(user);
	}

	
	public void login(String email, String password) {
		userModelDao.login(email, password);
	}

	
	public void update(String email) {
		userModelDao.update(email);
	}

}
