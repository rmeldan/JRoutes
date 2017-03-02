package com.softserve.edu.jroutes.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.edu.jroutes.dto.SecurityRoleDTO;

@Component("securityRoleValidator")
public class SecurityRoleVaidator implements Validator {

	@Override
	public boolean supports(Class<?> klass) {
		return SecurityRoleDTO.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SecurityRoleDTO role = (SecurityRoleDTO) target;
		String roleName = role.getName();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"NotEmpty.SecurityRole.name",
				"Security role name must not be empty.");

		if ((roleName.length()) > 50) {
			errors.rejectValue("name", "TooLong.SecurityRole.name",
					"Security role must not have more than 50 characters.");
		}

		if ((roleName.length()) < 2) {
			errors.rejectValue("name", "TooShort.SecurityRole.name",
					"Security role must not have less than 2 characters.");
		}

		final String securityRoleRegex = "^[A-Za-z0-9][A-Za-z0-9_'\'-]*[A-Za-z0-9]$";
		Pattern pattern = Pattern.compile(securityRoleRegex);
		Matcher matcher = pattern.matcher(roleName);

		if (!matcher.matches()) {
			errors.rejectValue("name", "BadFormat.SecurityRole.name",
					"Security role name can contain characters, numbers symbols <->, <_> only.");
		}
	}
}
