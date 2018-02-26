package com.bridgeit.ToDoApp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.IUserDao;
import com.bridgeit.ToDoApp.dao.Ilabels;
import com.bridgeit.ToDoApp.dao.InotesUserModelDao;
import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.Notes;
import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public class NodeServiceImp implements INoteService {
	@Autowired
	private InotesUserModelDao noteDao;
	
	// @Autowired
	// private IValidation valid;
	@Autowired
	private IUserDao userModelDao;

	@Autowired
	Ilabels levelDao;

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
		return noteDao.getNotes(user);
	}

	public Notes shareNote(String email, int noteId, int id) {
		UserModel user = userModelDao.checkExits(email);
		if (user != null) {
			Notes notes = noteDao.getNode(noteId);
			Set<UserModel> userSet = new HashSet<UserModel>();
			userSet = notes.getCollaboratorSet();
			userSet.add(user);
			notes.setCollaboratorSet(userSet);
			noteDao.upadteNote(noteId, notes);
			return notes;
		}

		return null;
	}

	public boolean removeShareUser(int id, String emailId) {
		UserModel user = userModelDao.checkExits(emailId);
		Notes notes = noteDao.getNode(id);
		Set<UserModel> userSet = new HashSet<UserModel>();
		userSet = notes.getCollaboratorSet();
		userSet.remove(user);
		for (UserModel s : userSet) {
			if (s.equals(user)) {
				userSet.remove(s);
				break;
			}
		}
		System.out.println("after Remove :" + userSet.size());
		notes.setCollaboratorSet(userSet);
		noteDao.upadteNote(id, notes);

		return true;
	}

	public void addLavel(int noteId, int lableId) {
		Notes notes = noteDao.getNode(noteId);
		Labels labels = levelDao.getLebel(lableId);
		Set<Labels> setLable = new HashSet<>();
		setLable = notes.getLabels();
		setLable.add(labels);
		notes.setLabels(setLable);
		noteDao.upadteNote(noteId, notes);
	}

	public void deleteLable(int noteId, int lableId) {
		Notes notes = noteDao.getNode(noteId);
		Labels labels = levelDao.getLebel(lableId);
		Set<Labels> setLable = new HashSet<>();
		setLable = notes.getLabels();
		for (Labels lable : setLable) {
			if (lable.equals(labels)) {
				setLable.remove(lable);
				break;
			}
		}
		notes.setLabels(setLable);
		noteDao.upadteNote(noteId, notes);
	}

}
