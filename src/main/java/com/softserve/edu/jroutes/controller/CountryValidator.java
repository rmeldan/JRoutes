package com.softserve.edu.jroutes.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.edu.jroutes.dto.CountryDTO;

@Component("countryValidator")
public class CountryValidator implements Validator{

	@Override
	public boolean supports(Class<?> klass) {
		
		return CountryDTO.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
	
		boolean isValid = true;
		CountryDTO countryDTO = (CountryDTO) target;
		String country = countryDTO.getName();
		
		if (country=="") {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"NotEmpty.countryDTO.name",
				"Country name should not be empty");
			isValid = false;
		}
		
		
		if ( country.length()>30 && isValid) {
			errors.rejectValue("name",
					"lengthOfCountry.countryDTO.name",
					"Country name should not be more than 30 characters");
			isValid = false;
		}
		
		if(country!=""){
			char lowerCaseTest = country.toLowerCase().charAt(0);
			if(country.charAt(0)==lowerCaseTest && isValid){
				errors.rejectValue("name",
						"caseCountry.countryDTO.name",
					"Country name should be capitalized");
				isValid = false;
			}
		}
		country = countryDTO.getName();
		final String countryRegex = "^[A-Za-z ']{3,}$";
     
		Pattern p = Pattern.compile(countryRegex);
		Matcher m = p.matcher(country);
		boolean b = m.matches();
	
		if (!b && isValid ) {
			errors.rejectValue("name",
					"matching.countryDTO.name",
					"Country name format is wrong");
		}
		
		
		
		
	}
	
	

}
