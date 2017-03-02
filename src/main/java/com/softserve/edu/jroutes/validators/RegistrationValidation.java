package com.softserve.edu.jroutes.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.softserve.edu.jroutes.dto.UserDTO;
@Component("registrationValidator")
public class RegistrationValidation {
	
	public static void validate(Object target, Errors errors) {
		
		UserDTO registration = (UserDTO) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"NotEmpty.registration.firstName",
				"User Name must not be Empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"NotEmpty.registration.lastName",
				"Last Name must not be Empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
				"NotEmpty.registration.email",
				"Email must not be Empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.registration.password",
				"Password must not be Empty.");
		
		String firstName = registration.getFirstName();
		if ((firstName.length()) > 50) {
			errors.rejectValue("firstName",
					"lengthOfUser.registration.firstName",
					"User Name must not more than 50 characters.");
		}
		
		String lastName = registration.getLastName();
		if ((lastName.length()) > 50) {
			errors.rejectValue("lastName",
					"lengthOfUser.registration.userName",
					"User Name must not more than 50 characters.");
		}
		if (!(registration.getPassword()).equals(registration
				.getConfirmPassword())) {
			errors.rejectValue("password",
					"matchingPassword.registration.password",
					"Password and Confirm Password Not match.");
		}
		
		String email = registration.getEmail();
		final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
				+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(emailRegex);
		Matcher m = p.matcher(email);
		boolean b = m.matches();
	
		if (!b && email.length() != 0) {
			errors.rejectValue("email",
					"matching.registration.email",
					"Email format is wrong");
		}
		
	}
}
