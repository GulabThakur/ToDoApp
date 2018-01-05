package com.bridgeit.ToDoApp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author ThakurGulab
 *
 */
public class LevelImp implements Ilabels{
	@Autowired 
	private SessionFactory sessionFactory;

	public void create(Labels label) {
		Session session =sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		Integer id= (Integer) session.save(label);
		session.close();
		transaction.commit();
		
	}

	public void update(Labels label) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.update(label);
		session.close();
		transaction.commit();
	}

	public Labels getLebel(int labelId) {
		Session session=sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		Query query=session.createQuery("from Labels where labelsId='"+labelId+"'");
		Labels label=(Labels) query.uniqueResult();
		session.close();
		transaction.commit();
		return label;
	}

	public List<Labels> getLabels(UserModel user) {
		Session session =sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		Query query=session.createQuery("from Labels where userId='"+user.getId()+"'");
		List<Labels> labels=(List<Labels>) query.list();
		session.close();
		transaction.commit();
		return labels;
	}

	public void delete(Labels label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(label);
		session.close();
		transaction.commit();
	}

}
