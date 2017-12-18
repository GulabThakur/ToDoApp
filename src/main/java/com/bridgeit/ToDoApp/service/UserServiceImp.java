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

	
	public boolean login(String email, String password) {
		return userModelDao.login(email, password);
	}

	
	public UserModel update(UserModel user) {
		UserModel user1=userModelDao.update(user);
		return user1;
	}


	public UserModel getDataByEmail(String email) {
		return userModelDao.checkExits(email);
	}


	
	public UserModel getDataById(int id) {
		
		return userModelDao.getById(id);
	}



	

}
