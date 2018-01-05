package com.bridgeit.ToDoApp.dao;

import java.util.List;

import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public interface Ilabels {
		
	public void create(Labels label);
	public void update(Labels label);
	public Labels getLebel(int labelId);
	public List<Labels> getLabels(UserModel user);
    public void delete(Labels label);
}
