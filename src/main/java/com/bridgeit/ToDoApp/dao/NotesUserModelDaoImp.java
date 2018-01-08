package com.bridgeit.ToDoApp.dao;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
		session.clear();
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
			 long userId=user.getId();
			 Session session=sessionFactory.openSession();
			 String hql = "FROM Note_user N WHERE N.usr_id = :usr_id";
			 Query query = session.createQuery(hql);
			 query.setParameter("usr_id",userId);
			 List<Notes> results = query.list();
			 System.out.println(results.size());
			 Criteria criteria=session.createCriteria(Notes.class);
			 criteria.createAlias("collaboratorSet", "user");
			 criteria.add(Restrictions.eq("user.id", user.getId()));
			 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			 List<Notes> notes1 = criteria.list();
			 results.addAll(notes1);
			 return results;
	}

}
