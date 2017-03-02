package com.softserve.edu.jroutes.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/*
 * @author Maryan
 */

@Repository("userDAO")
public class UserDAO extends ElementDAOImpl<User> {
	public UserDAO() {
		super(User.class);
	}
	
	@Override
	public User checkForUnique(User element, Session session)
			throws NonUniqueException {
		String email = element.getEmail();
		List<User> userList = session.createCriteria(User.class)
				.add(Restrictions.eq("email", email)).list();
		if (userList.size() == 0) {
			return element;
		} else {
			User user = userList.get(0);
			if (user.getId() == element.getId()) {
				user.setFirstName(element.getFirstName());
				user.setIsBlocked(element.getIsBlocked());
				user.setLastName(element.getLastName());
				user.setPassword(element.getPassword());
				user.setRoles(element.getRoles());
				return user;
			} else {
				throw new NonUniqueException("This email is exists");
			}
		}
	}

	@Override
	public List<User> getElementsByCriteria(Object... criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}