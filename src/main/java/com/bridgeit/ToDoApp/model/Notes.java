package com.bridgeit.ToDoApp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author ThakurGulab
 *
 */
@Entity(name = "Note_user")
public class Notes implements Serializable {
	/**
	 * @category Universal Identifier when Implement Serializable interface it use
	 *           for manage serializable interface
	 */
	private static final long serialVersionUID = 1L;
	
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
	private String reminder;
	
	@JoinTable(name = "NoteLabel", joinColumns = {@JoinColumn(name = "noteId")}, inverseJoinColumns = {@JoinColumn(name = "labelId")})
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Labels> labels;
	
	
	public Set<Labels> getLabels() {
		return labels;
	}

	public void setLabels(Set<Labels> labels) {
		this.labels = labels;
	}

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="user_id")
	private UserModel user;
	private long usr_id;
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	// this property use for Collaborator
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "collaborator", joinColumns = @JoinColumn(name = "note_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<UserModel> collaboratorSet = new HashSet<UserModel>();

	// here i am take as properties for image
	@Lob
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private String imageNote;

	public String getImageNote() {
		return imageNote;
	}

	public void setImageNote(String imageNote) {
		this.imageNote = imageNote;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
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

	public Set<UserModel> getCollaboratorSet() {
		return collaboratorSet;
	}

	public void setCollaboratorSet(Set<UserModel> collaboratorSet) {
		this.collaboratorSet = collaboratorSet;
	}
}
