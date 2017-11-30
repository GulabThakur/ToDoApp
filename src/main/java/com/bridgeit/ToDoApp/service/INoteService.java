package com.bridgeit.ToDoApp.service;

import java.util.List;

import com.bridgeit.ToDoApp.model.Notes;

/**
 * @author ThakurGulab
 *
 */
public interface INoteService {
	/**
	 * @param note
	 * @return true when data will be store inside data base
	 */
	public boolean create_note(Notes note);

	/**
	 * @param id
	 * @param note
	 * @this note will have data that you want update
	 * @return TRUE when data will be update that related this @param id
	 */
	public boolean update_note(int id, Notes note);

	/**
	 * @param id
	 * @return true when delete particular data will be deleted
	 */
	public boolean delete_note(int id);

	/**
	 * @param id
	 * @return true when data will be fetch from data base
	 */
	public Notes get_note(int id);

	/**
	 * @return all note will be return
	 */
	public List<Notes> allNotes();
}
