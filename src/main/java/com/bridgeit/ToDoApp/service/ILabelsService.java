package com.bridgeit.ToDoApp.service;

import java.util.List;

import com.bridgeit.ToDoApp.model.Labels;

/**
 * @author ThakurGulab
 *
 */
public interface ILabelsService {
		
	public void createService(Labels label ,String token);
	public void updateService(Labels label, String token);
	public List<Labels> getLabelsService(String token);
    public void deleteService(Labels label);
}
