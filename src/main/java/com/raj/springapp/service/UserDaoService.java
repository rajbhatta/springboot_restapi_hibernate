package com.raj.springapp.service;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raj.springapp.dao.DaoService;
import com.raj.springapp.modal.User;

@Service
public class UserDaoService implements UserService<User> {

	@Autowired
	private DaoService userDao;
	
	@Override
	@Transactional
	public boolean save(User user) throws UserDaoException {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public User get(int id) {
		return userDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id)
	{
		userDao.delete(id);
	}

	@Override
	@Transactional
	public List<User> getList() {
		return userDao.getList();
	}

}
