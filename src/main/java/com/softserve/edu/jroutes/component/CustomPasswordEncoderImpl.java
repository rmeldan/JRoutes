package com.softserve.edu.jroutes.component;

import com.softserve.edu.jroutes.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoderImpl implements CustomPasswordEncoder {

	public void encodePassword(User user) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String usrPassword = user.getPassword();
		String hashedPassword = passwordEncoder.encode(usrPassword);
		user.setPassword(hashedPassword);
	}
	

}