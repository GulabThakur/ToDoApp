package com.bridgeit.ToDoApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.dao.InotesUserModelDao;
import com.bridgeit.ToDoApp.model.Notes;

/**
 * @author ThakurGulab
 *
 */
public class NodeServiceImp implements INoteService {
	@Autowired
	private InotesUserModelDao noteDao;

	public boolean create_note(Notes note) {
		return noteDao.createNote(note);
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

	public List<Notes> allNotes() {
		return noteDao.getNotes();
	}

}
