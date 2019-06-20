package com.raj.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raj.springapp.dao.UserDao;
import com.raj.springapp.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional
	public boolean saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User getUser(int id) {
		return userDao.getUser(id);
	}

	@Override
	@Transactional
	public void deleteUser(int id)
	{
		userDao.deleteUser(id);
	}

	@Override
	@Transactional
	public List<User> getUserList() {
		return userDao.getUserList();
	}

}
