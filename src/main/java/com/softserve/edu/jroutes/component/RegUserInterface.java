package com.softserve.edu.jroutes.component;

import org.springframework.stereotype.Component;

import com.softserve.edu.jroutes.entity.User;
@Component
public interface RegUserInterface {
	public User getRegisteredUserObject();

	
}
