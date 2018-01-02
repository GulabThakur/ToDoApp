package com.bridgeit.ToDoApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.InotesUserModelDao;
import com.bridgeit.ToDoApp.model.Notes;
import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public class NodeServiceImp implements INoteService {
	@Autowired
	private InotesUserModelDao noteDao;

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
		Notes notes=noteDao.getNode(noteId);
		return notes;
	}

}
