package com.softserve.edu.jroutes.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.softserve.edu.jroutes.dto.UserDTO;

@Component("profileValidator")
public class profileValidator {
	public static void validate(Object target, Errors errors) {
		UserDTO registration = (UserDTO) target;
		if (!(registration.getPassword()).equals(registration
				.getConfirmPassword())) {
			errors.rejectValue("password",
					"matchingPassword.registration.password",
					"Password and Confirm Password Not match.");
		}
	}
}
