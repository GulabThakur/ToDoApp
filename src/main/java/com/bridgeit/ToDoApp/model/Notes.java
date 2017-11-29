package com.bridgeit.ToDoApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author ThakurGulab
 *
 */
@Entity(name = "Note_user")
public class Notes implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private long currenTime;
	private long updateTime;
	private long usr_id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCurrenTime() {
		return currenTime;
	}

	public void setCurrenTime(long currentTime) {
		this.currenTime = currentTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long currentTime) {
		this.updateTime = currentTime;
	}
}
