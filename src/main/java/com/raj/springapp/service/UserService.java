package com.raj.springapp.service;

import java.util.List;

import com.raj.springapp.model.User;

public interface UserService {

	boolean saveUser(User user);
	User getUser(int id);
	void deleteUser(int id);
	List<User> getUserList();
	
}
