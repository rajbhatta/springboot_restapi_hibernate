package com.raj.springapp.dao;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.raj.springapp.modal.User;

@Repository
public class UserDaoImpl implements DaoService<User> {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean save(User user) throws UserDaoException {
		try {
			sessionFactory.getCurrentSession().persist(user);
			return true;
		}
		catch(Exception ee) {
			throw new UserDaoException("UNABLE TO SAVE THE USER TO DATABASE ",ee);
		}
		
	}

	@Override
	public User get(int id) {
		User user = (User)sessionFactory.getCurrentSession().get(User.class, id);
		return user;
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		session.delete(user);

	}

	@Override
	public List<User> getList() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();	}

}
