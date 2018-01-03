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
	
	/*public Note shareNote(String email, int noteId, int userId) {
		if(userDao.emailValidaton(email)) {
			Note note=noteDao.read(noteId);
			User user=userDao.getUserByEmailId(email);
			Set<User> userSet=new HashSet<>();
			userSet.add(user);
			note.setSharedUser(userSet);
			noteDao.update(note);
			return note;
		}else {
			return null;
		}*/



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

}
