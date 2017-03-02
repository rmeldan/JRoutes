package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.edu.jroutes.dto.UserDTO;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.*;
import com.softserve.edu.jroutes.validators.RegistrationValidation;
import com.softserve.edu.jroutes.component.CustomPasswordEncoder;

;

@Controller
@RequestMapping("/")
public class RegistrationController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityRoleService securityRoleService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registraion(ModelMap model) {

		UserDTO user = new UserDTO();
		model.addAttribute("user", user);
		return "registrationform";
	}

	@RequestMapping(value = "/registrationUser", method = RequestMethod.POST)
	public String processRegistration(
			@ModelAttribute(value = "user") UserDTO user, BindingResult result) {

		RegistrationValidation.validate(user, result);
		if (result.hasErrors()) {
			return "registrationform";
		}
		User userReg = new User();
		userReg.setFirstName(user.getFirstName());
		userReg.setLastName(user.getLastName());
		userReg.setEmail(user.getEmail());

		List<SecurityRole> list = (List<SecurityRole>) securityRoleService
				.getAllSecurityRoles();
		Set<SecurityRole> st = new HashSet<SecurityRole>();
		Iterator<SecurityRole> it = list.iterator();
		SecurityRole q = new SecurityRole();
		while (it.hasNext()) {
			q = it.next();
			if (q.getName().equals("ROLE_USER")) {
				st.add(q);
			}
		}
		userReg.setRoles(st);
	
		 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 String usrPassword = user.getPassword();
		 String hashedPassword = passwordEncoder.encode(usrPassword);
		 userReg.setPassword(hashedPassword);

		try {
			userService.addUser(userReg);
		} catch (NonUniqueException e) {
			result.rejectValue("email", "error.email",
					"An account already exists for this email.");
			if (result.hasErrors())
				return "registrationform";
		}
		return "registrationsuccess";
	}

}
