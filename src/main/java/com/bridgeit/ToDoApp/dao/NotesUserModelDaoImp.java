package com.bridgeit.ToDoApp.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.ToDoApp.model.Notes;

/**
 * @author ThakurGulab
 *
 */

@Repository
@Transactional
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

	@SuppressWarnings("unused")
	public boolean deleteNote(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		@SuppressWarnings("unused")
		Notes note = (Notes) session.byId(Notes.class).load(id);
		session.delete(note);
		transaction.commit();
		session.close();
		return true;
	}

	public Notes getNode(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Notes note = (Notes) session.byId(Notes.class).load(id);
		transaction.commit();
		session.close();
		return note;
	}

	@SuppressWarnings("unchecked")
	public List<Notes> getNotes() {

		try {
			System.out.println("hellooo");
			List<Notes> list = new ArrayList<Notes>();
			list = sessionFactory.getCurrentSession().createCriteria(Notes.class).list();
			return list;
		} catch (Exception e) {
			return new ArrayList<Notes>();
		}

	}

}
