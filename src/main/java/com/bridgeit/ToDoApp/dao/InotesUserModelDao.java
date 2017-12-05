package com.bridgeit.ToDoApp.dao;

import java.util.List;

import com.bridgeit.ToDoApp.model.Notes;

/**
 * @author ThakurGulab
 *
 */
public interface InotesUserModelDao {
	/**
	 * @param note
	 * @param token 
	 * @return true when create note success
	 */
	public boolean createNote(Notes note, String token);

	/**
	 * @param id
	 * @return true when update recored belong to this id
	 */
	public boolean upadteNote(int id, Notes note_data);

	/**
	 * @param id
	 * @return true when delete recored belong to this id
	 */
	public boolean deleteNote(int id);

	/**
	 * @param id
	 * @return true when retrieve data from data base
	 */
	public Notes getNode(int id);

	/**
	 * @return
	 */
	public List<Notes> getNotes();
}
