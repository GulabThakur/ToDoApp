package com.bridgeit.ToDoApp.dao;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgeit.ToDoApp.model.UserModel;

/**
 * @author GulabThakur
 *
 */
public class UserDaoImp implements IUserDao{
	
	@Autowired
	private IUserDao userModelDao;
	@Autowired
	private SessionFactory sessionFactory;
	public void register(UserModel user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		session.close();
		}

	public void login(String email, String password) {
		Session session = sessionFactory.openSession();
		//String persion = null;
		Criteria criteria = session.createCriteria(UserModel.class);
		Criterion emial1 = Restrictions.eq("email", email);
		criteria.add(emial1);
		UserModel user = (UserModel) criteria.uniqueResult();
		Transaction transaction = session.beginTransaction();
		session.close();
		if (user != null) {

			boolean status = userModelDao.checkPsd(password, user.getPassword());
			if (status) {
			//	persion = user.getId();
				//return persion;
			} else {
				//persion = "password wrong";
				
				//return persion;
			}
		}
	  //	return persion;
	}

	public boolean checkPsd(String password, String hash_psd) {
		boolean password_verified = false;

		if (null == password || !hash_psd.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password, hash_psd);

		return (password_verified);
	}

	
	public void update(String email) {
		// TODO Auto-generated method stub
		
	}

}
