package com.bridgeit.ToDoApp.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgeit.ToDoApp.model.UserModel;

public class SecurityImp implements IPasswordencode {
	private static int workload = 12;

	public String endode(String user) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(user, salt);
		return hashed_password;
	}

}
