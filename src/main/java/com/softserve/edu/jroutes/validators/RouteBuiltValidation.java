package com.softserve.edu.jroutes.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.softserve.edu.jroutes.dto.SavedRouteDto;

/**
 * @author Roman
 */

@Component("routeValidation")
public class RouteBuiltValidation implements Validator {
	private static final Logger LOGGER = Logger.getLogger(RouteBuiltValidation.class);
	@Override
	public boolean supports(Class<?> clazz) {
		return SavedRouteDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SavedRouteDto savedRouteDto = (SavedRouteDto) target;
		String savedRouteValidator = savedRouteDto.getName();	
		final String savedRouteRegex = "^[A-Za-z0-9 ']{1,}$";		
		Pattern p = Pattern.compile(savedRouteRegex);
		Matcher m = p.matcher(savedRouteValidator);
		boolean b = m.matches();
		if (savedRouteValidator == "") {			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
					"routes.validationEnterName");
			LOGGER.error("Name of the route is empty");
		} else if (savedRouteValidator.length() > 30 || savedRouteValidator.length() < 3) {			
			errors.rejectValue("name", "routes.validationSymbolNumbers");
			LOGGER.error("The name of the route doesn't contain from 3 to 30 characters!");
		} else if (savedRouteValidator.charAt(0) == savedRouteValidator.toLowerCase().charAt(0)) {			
			errors.rejectValue("name", "routes.validationUpperLetter");
			LOGGER.error("The name of the route doesn't begin with a capital letter!");
		} else if (!b) {			
			errors.rejectValue("name", "routes.validationErrorEnter");
			LOGGER.error("The name of the route contains non-Latin alphabet characters");
		}		
	}
}