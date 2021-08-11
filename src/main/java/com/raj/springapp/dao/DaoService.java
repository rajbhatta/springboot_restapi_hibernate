package com.raj.springapp.dao;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import com.raj.springapp.modal.User;

public interface DaoService<T> {
	
	boolean save(T t) throws UserDaoException;
	User get(int id);
	void delete(int id);
	List<User> getList();

}
