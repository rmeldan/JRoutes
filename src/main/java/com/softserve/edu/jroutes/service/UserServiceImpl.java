package com.softserve.edu.jroutes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private ElementDAO<User> userDAO;

	@Override
	public void addUser(User element) throws NonUniqueException {
		userDAO.addElement(element);
	}

	@Override
	public void updateUser(User element) throws NonUniqueException {
		userDAO.updateElement(element);
	}

	@Override
	public List<User> filterUsers(List<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
			for (int j = 0; j < userList.size(); j++) {
				if (userList.get(i).getEmail().equals(userList.get(j).getEmail())
						&& i != j) {
					userList.remove(j);
				}
			}
		}

		return userList;
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllElements();
	}

	@Override
	public void deleteUser(User element) {
		userDAO.deleteElement(element);
	}

	@Override
	public List<User> getUsersByCriteria(Object... criteria) {
		return userDAO.getElementsByCriteria(criteria);
	}

	@Override
	public User getUserById(Long elementId) {
		return userDAO.getElementByID(elementId);
	}

	@Override
	public List<User> getPageOfUsers(int recordsPerPage, int currentPage) {
		// List<User> allUsers = userDAO.getAllElements();
		List<User> allUsers = filterUsers(getAllUsers());
		int listSize = allUsers.size();
		System.out.println("getPageOfUsers all size = " + listSize);
		int firstElement = currentPage * recordsPerPage;
		int lastElement = firstElement + recordsPerPage;

		if (firstElement >= listSize) {
			return new ArrayList<User>(); // empty
		}
		if (lastElement >= listSize) {
			return allUsers.subList(firstElement, listSize);
		}

		return allUsers.subList(firstElement, lastElement);
	}
}