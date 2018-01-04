package com.bridgeit.ToDoApp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.IUserDao;
import com.bridgeit.ToDoApp.dao.InotesUserModelDao;
import com.bridgeit.ToDoApp.model.Notes;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.validation.IValidation;

/**
 * @author ThakurGulab
 *
 */
public class NodeServiceImp implements INoteService {
	@Autowired
	private InotesUserModelDao noteDao;
	@Autowired
	private IValidation valid;
	@Autowired
	private IUserDao userModelDao;

	public boolean create_note(Notes note, String token) {
		return noteDao.createNote(note, token);
	}

	public boolean update_note(int id, Notes note) {
		return noteDao.upadteNote(id, note);
	}

	public boolean delete_note(int id) {
		return noteDao.deleteNote(id);
	}

	public Notes get_note(int id) {
		Notes note = noteDao.getNode(id);
		return note;
	}
	

	public List<Notes> allNotes(UserModel user) {
		System.out.println(user.getUserName());
		return noteDao.getNotes(user);
	}

	public Notes shareNote(String email, int noteId, int id) {
		UserModel user=userModelDao.checkExits(email);
		if(user!=null) {
			Notes notes=noteDao.getNode(noteId);
			Set<UserModel> userSet=new HashSet<UserModel>();
			userSet=notes.getCollaboratorSet();
			userSet.add(user);
			notes.setCollaboratorSet(userSet);
			noteDao.upadteNote(noteId, notes);
			return notes;
		}
		
		return null;
	}

	public boolean removeShareUser(int id, String emailId) {
			UserModel user =userModelDao.checkExits(emailId);
			if(user!=null) {
				Notes notes=noteDao.getNode(id);
				Set<UserModel> userSet= new HashSet<UserModel>();
				userSet=notes.getCollaboratorSet();
				userSet.remove(user);
				notes.setCollaboratorSet(userSet);
				noteDao.upadteNote(id, notes);
				return true;
			}
			return false;
		
	}

}
