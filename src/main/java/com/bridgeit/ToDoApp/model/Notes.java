package com.bridgeit.ToDoApp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author ThakurGulab
 *
 */
@Entity(name = "Note_user")
public class Notes implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String currenTime;
	private String updateTime;
	private boolean isArchive;
	private boolean isPin;
	private boolean isTrash;
	private String color;
	private Date reminder;
	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean getArchive() {
		return isArchive;
	}

	public boolean getPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean getTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

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

	public String getCurrenTime() {
		return currenTime;
	}

	public void setCurrenTime(String currentTime) {
		this.currenTime = currentTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String currentTime) {
		this.updateTime = currentTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
