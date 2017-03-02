package com.softserve.edu.jroutes.component;

import com.softserve.edu.jroutes.entity.*;

public interface CustomPasswordEncoder {
	
	/**
	 * Method for password encoding by using BCryptPasswordEncoder
	 */
	public void encodePassword(User user);
	
}
