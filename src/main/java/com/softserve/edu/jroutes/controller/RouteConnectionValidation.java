package com.softserve.edu.jroutes.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.softserve.edu.jroutes.dto.RouteConnectionDTO;
import com.softserve.edu.jroutes.exception.InvalidDateException;

@Component("routeConnectionValidation")
public class RouteConnectionValidation {
	public boolean supports(Class<?> klass) {
		return RouteConnectionDTO.class.isAssignableFrom(klass);
	}

	private final String dateWrongFormat = "Please enter a valid date format (XXd XXh XXm)";
	private final String numberWrongFormat = "Please enter a valid value";
	private final String numberOutOfRange = "Value is too big or less than zero";
	private final String emptyField = "Field must not be empty";
	private final String equalCities = "Cities should not be the same";
	private static final String MINUTE_PATTERN = "[0-5]?[0-9]m";
	private static final String HOUR_PATTERN = "([01]?[0-9]|2[0-3])h";
	private static final String DAY_PATTERN = "([012]?[0-9]|3[0])d";
	private Pattern pattern;
	private Matcher matcher;
	private final int maxTime = 120000;
	private Long time = null;
	private Long price = null;

	public void validate(Object target, Errors errors) {

		RouteConnectionDTO routeConnection = (RouteConnectionDTO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "routePointA", "",
				emptyField);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "routePointB", "",
				emptyField);
		if (routeConnection.getRoutePointA() != null
				&& routeConnection.getRoutePointA() == routeConnection
						.getRoutePointB()) {
			errors.rejectValue("routePointA", "", equalCities);
		}

		// Time validating
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "time", "",
				emptyField);
		if (routeConnection.getTime() != null) {
			try {
				validateDate(routeConnection.getTime());
			} catch (InvalidDateException e) {
				errors.rejectValue("time", "", dateWrongFormat);
			}
		}

		// Price validating
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "",
				emptyField);

		try {
			price = Long.parseLong(routeConnection.getPrice());
			if (price < 0 || price > Long.MAX_VALUE) {
				errors.rejectValue("price", "", numberOutOfRange);
			}
		} catch (NumberFormatException exception) {
			if (routeConnection.getPrice() != "") {
				errors.rejectValue("price", "", numberWrongFormat);
			}
		}

	}

	private void validateDate(String date) throws InvalidDateException {
		String[] array = date.split(" ");
		if (array.length == 1) {
			pattern = Pattern.compile(MINUTE_PATTERN);
		} else if (array.length == 2) {
			pattern = Pattern.compile(HOUR_PATTERN + "\\s" + MINUTE_PATTERN);
		} else if (array.length == 3) {
			pattern = Pattern.compile(DAY_PATTERN + "\\s" + HOUR_PATTERN
					+ "\\s" + MINUTE_PATTERN);
		} else {
			throw new InvalidDateException();
		}
		matcher = pattern.matcher(date);
		if (matcher.matches() == false) {
			throw new InvalidDateException();
		}
	}

}
