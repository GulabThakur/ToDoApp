package com.bridgeit.ToDoApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.Ilabels;
import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.token.IToken;

/**
 * @author ThakurGulab
 *
 */
public class LablesServiceImp  implements ILabelsService{
	@Autowired
	Ilabels levelDao;
	@Autowired 
	IuserService userModelService;
	@Autowired
	IToken token;
	
	public void createService(Labels label ,String token1) {
		int userId=token.varifyToken(token1);
		UserModel user=userModelService.getDataById(userId);
		label.setUserId(user);
		levelDao.create(label);
	}

	public void updateService(Labels label ,String token1) {
		int userId=token.varifyToken(token1);
		UserModel user=userModelService.getDataById(userId);
		label.setUserId(user);
		levelDao.update(label);
	}

	

	public List<Labels> getLabelsService(String token1) {
		int userId=token.varifyToken(token1);
		UserModel user =userModelService.getDataById(userId);
		return levelDao.getLabels(user);
	}

	public void deleteService(Labels label) {
		levelDao.delete(label);
	}

	
}
