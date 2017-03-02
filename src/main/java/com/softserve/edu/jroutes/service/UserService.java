package com.softserve.edu.jroutes.service;

import java.util.List;

import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;

public interface UserService {
	void addUser(User user) throws NonUniqueException;

	void updateUser(User user) throws NonUniqueException;

	List<User> getAllUsers();
	
	List<User> filterUsers(List<User> userList);

	void deleteUser(User user);

	List<User> getUsersByCriteria(Object... criteria);
	
	List<User> getPageOfUsers(int recordsPerPage, int currentPage);

	User getUserById(Long userId);
}
