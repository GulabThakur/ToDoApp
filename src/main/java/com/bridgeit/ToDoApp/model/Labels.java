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

	// merge for use noorg.hibernate.NonUniqueObjectException: for solve
	@JsonIgnore
	@JoinColumn(name="userId")
    @ManyToOne(cascade=CascadeType.MERGE) 
	private UserModel userId;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Labels label=(Labels) obj;
		if(label.labelsId==this.getLabelsId())
			return true;
		else
			return false;
	}
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
