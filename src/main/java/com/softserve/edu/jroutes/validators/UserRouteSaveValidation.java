package com.softserve.edu.jroutes.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.edu.jroutes.dto.RouteConnectPointDto;

@Component("UserRouteSaveValidation")
public class UserRouteSaveValidation implements Validator {

    @Override
    public boolean supports(Class<?> klass) {
        return RouteConnectPointDto.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        boolean isValid = true;
        RouteConnectPointDto routeDTO = (RouteConnectPointDto) target;
        String routeName = routeDTO.getName();
        
        if (routeName=="") {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                    "NotEmpty.routeDTO.name",
                    "Route name should not be empty");
                isValid = false;
            }
        if ( routeName.length()>30 && isValid) {
            errors.rejectValue("name",
                    "lengthOfRoute.routeDTO.name",
                    "Route name should not be more than 30 characters");
            isValid = false;
        }
        
        routeName = routeDTO.getName();
        final String routeRegex = "^[A-Za-z ']{3,}$";
     
        Pattern p = Pattern.compile(routeRegex);
        Matcher m = p.matcher(routeName);
        boolean b = m.matches();
    
        if (!b && isValid ) {
            errors.rejectValue("name",
                    "matching.routeDTO.name",
                    "Route name format is wrong");
        }
        
    }

}
