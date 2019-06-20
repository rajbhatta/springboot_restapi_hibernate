package com.raj.springapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.raj.springapp.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean saveUser(User user) 
	{
		try {
			sessionFactory.getCurrentSession().persist(user);
			return true;
		}
		catch(Exception ee) {
			ee.printStackTrace();
			return false;
		}
		
	}

	@Override
	public User getUser(int id) {
		User user = (User)sessionFactory.getCurrentSession().get(User.class, id);
		return user;
	}

	@Override
	public void deleteUser(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		session.delete(user);

	}

	@Override
	public List<User> getUserList() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();	}

}
