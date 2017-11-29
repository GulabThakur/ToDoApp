package com.bridgeit.ToDoApp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.ToDoApp.model.Notes;

/**
 * @author ThakurGulab
 *
 */

public class NotesUserModelDaoImp implements InotesUserModelDao {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean createNote(Notes note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(note);
		transaction.commit();
		session.close();
		return true;
	}

	public boolean upadteNote(int id, Notes note_data) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Notes note = (Notes) session.byId(Notes.class).load(id);
		note.setCurrenTime(note_data.getCurrenTime());
		note.setDescription(note_data.getDescription());
		note.setUpdateTime(note_data.getUpdateTime());
		note.setUsr_id(note_data.getUsr_id());
		session.update(note);
		transaction.commit();
		session.close();
		return true;

	}

	public boolean deleteNote(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Notes note = (Notes) session.byId(Notes.class).load(id);
		session.delete(note);
		transaction.commit();
		session.close();
		return true;
	}

	public Notes getNode(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Notes note = (Notes) session.byId(Notes.class).load(id);
		transaction.commit();
		session.close();
		return note;
	}

}
