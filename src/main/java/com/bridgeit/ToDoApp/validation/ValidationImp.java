package com.bridgeit.ToDoApp.validation;

import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public class ValidationImp implements IValidation{
	public boolean isName(UserModel user) {
		if (user.getUserName().matches("[a-zA-Z]{3,}")) {
			return true;
		} else {
			return false;
		}

	}
	
// this chage for validation
	public boolean ispsd(UserModel user) {
		String psd="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
		 if(user.getConform_psd().matches(psd) && user.getPassword().matches(psd) ) {
			 return true;
			 
		}else {
		return false;
		}
     }

	public boolean isemail(UserModel user) {
		if (user.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			return true;
		} else {
			return false;
		}
	}

	public String valid(UserModel user) {
		if (!isName(user)) {
			return "Name is not correct.";
		} else if (!ispsd(user)) {
			return "Password is not correct.";
		}

		else if (!isemail(user)) {
			return "UserName is not correct";
		} else {
			return null;
		}
	}

}
