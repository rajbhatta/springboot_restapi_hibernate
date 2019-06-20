package com.raj.springapp.dao;

import java.util.List;

import com.raj.springapp.model.User;

public interface UserDao {
	
	boolean saveUser(User user);
	User getUser(int id);
	void deleteUser(int id);
	List<User> getUserList();

}
