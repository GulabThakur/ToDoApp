package com.bridgeit.ToDoApp.dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.ToDoApp.model.Notes;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.token.IToken;

/**
 * @author ThakurGulab
 *
 */

@Repository
@Transactional
public class NotesUserModelDaoImp implements InotesUserModelDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private IToken token;

	public boolean createNote(Notes note, String token2) {
		int id = token.varifyToken(token2);
		if (id > 0) {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String currentTime = dateFormat.format(cal.getTime());
			note.setCurrenTime(currentTime);
			note.setUpdateTime(currentTime);
			note.setUsr_id(id);
			session.save(note);
			transaction.commit();
			session.close();
			return true;
		}
		return false;

	}

	public boolean upadteNote(int id, Notes note_data) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String update_time = dateFormat.format(cal.getTime());
		note_data.setUpdateTime(update_time);
		session.update(note_data);
		transaction.commit();
		session.close();
		return true;

	}

	public boolean deleteNote(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
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
	public List<Notes> getNotes(UserModel user) {
			 System.out.println(user.getId());
		try {

			List<Notes> list = new ArrayList<Notes>();
			list = sessionFactory.getCurrentSession().createCriteria(Notes.class).add(Restrictions.eq("usr_id", user.getId())).list();
			System.out.println(list.size());
			return list;
		} catch (Exception e) {
			return new ArrayList<Notes>();
		}

	}

}
