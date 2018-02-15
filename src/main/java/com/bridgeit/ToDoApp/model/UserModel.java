package com.bridgeit.ToDoApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author GulabThakur
 *
 */
@Entity(name="ToDoApp_user")
public class UserModel{
	
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", proFile=" + proFile + ", conform_psd=" + conform_psd + ", isActive=" + isActive + "]";
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String userName;
	private String email;
	private String password;
	private String proFile;
	@Transient
	private String conform_psd;
	public String getProFile() {
		return proFile;
	}
	public void setProFile(String proFile) {
		this.proFile = proFile;
	}
	private int isActive;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConform_psd() {
		return conform_psd;
	}
	public void setConform_psd(String conform_psd) {
		this.conform_psd = conform_psd;
	}
	public int isActive() {
		return isActive;
	}
	public void setActive(int isActive) {
		this.isActive = isActive;
	}
}
