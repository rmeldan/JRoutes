package com.softserve.edu.jroutes.component;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.service.UserService;
@Component
public class RegisteredUserObject implements RegUserInterface {
    @Autowired
	private UserService userservice;
	private List<User> users;
   
	public User getRegisteredUserObject()  {
		if(users == null) {
			users = userservice.getAllUsers();
		}
		User user = null;
		String email = (String) SecurityContextHolder.getContext()
				.getAuthentication().getName();
		for (User value : users) {
			if (value.getEmail().equals(email)) {
				user = value;
				break;
			}
		}
		return user;
	}
}
