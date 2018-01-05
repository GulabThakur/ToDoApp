package com.bridgeit.ToDoApp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ThakurGulab
 */
@Entity
public class Labels {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int labelsId; 
	private String labelsName;
	private UserModel userId;
	
	@JsonIgnore
	@JoinColumn(name="userId")
    @ManyToOne(cascade=CascadeType.ALL)
	public int getLabelsId() {
		return labelsId;
	}
	public void setLabelsId(int labelsId) {
		this.labelsId = labelsId;
	}
	public String getLabelsName() {
		return labelsName;
	}
	public void setLabelsName(String labelsName) {
		this.labelsName = labelsName;
	}
	public UserModel getUserId() {
		return userId;
	}
	public void setUserId(UserModel userId) {
		this.userId = userId;
	}
	
}
