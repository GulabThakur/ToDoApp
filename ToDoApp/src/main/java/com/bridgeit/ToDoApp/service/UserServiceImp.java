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

	@Override
	public void registration(UserModel user) {
		userModelDao.register(user);
	}

	@Override
	public void login(String email, String password) {
		userModelDao.login(email, password);
	}

	@Override
	public void update(String email) {
		userModelDao.update(email);
	}

}
