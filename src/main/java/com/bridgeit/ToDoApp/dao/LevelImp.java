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
		session.save(label);
	}

	public void update(Labels label) {
		Session session=sessionFactory.openSession();
		System.out.println("value......................................."+label.getLabelsName());
		Transaction transaction=session.beginTransaction();
		session.update(label);
		transaction.commit();
		session.close();
		
	}

	public Labels getLebel(int labelId) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Labels where labelsId='"+labelId+"'");
		Labels label=(Labels) query.uniqueResult();
		return label;
	}

	public List<Labels> getLabels(UserModel user) {
		Session session =sessionFactory.openSession();
		Query query=session.createQuery("from Labels where userId='"+user.getId()+"'");
		@SuppressWarnings("unchecked")
		List<Labels> labels=(List<Labels>) query.list();
		return labels;
	}

	public void delete(Labels label) {
		Session session = sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		session.delete(label);
		transaction.commit();
		session.close();
		
	}

}
