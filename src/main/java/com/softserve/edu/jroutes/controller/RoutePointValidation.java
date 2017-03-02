package com.softserve.edu.jroutes.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.edu.jroutes.dto.RoutePointDTO;

@Component("routePointValidator")
public class RoutePointValidation implements Validator {
	@Override
	public boolean supports(Class<?> classObj) {
		return RoutePointDTO.class.isAssignableFrom(classObj);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoutePointDTO routePoint = (RoutePointDTO) target;
		String name = routePoint.getName();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"NotEmpty.RoutePoint.name",
				"RoutePoint name must not be empty.");

		if ((name.length()) > 50) {
			errors.rejectValue("name", "TooLong.RoutePoint.name",
					"City must not have more than 50 characters.");
		}

		if ((name.length()) < 2) {
			errors.rejectValue("name", "TooShort.RoutePoint.name",
					"City must not have less than 2 characters.");
		}

		final String regex = "^[A-Za-z0-9][A-Za-z0-9_'\'-]*[A-Za-z0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);

		if (!matcher.matches()) {
			errors.rejectValue("name", "BadFormat.SecurityRole.name",
					"City name can contain characters, numbers symbols <->, <_> only.");
		}
	}

}
