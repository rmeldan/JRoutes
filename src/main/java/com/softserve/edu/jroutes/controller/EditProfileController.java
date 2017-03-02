package com.softserve.edu.jroutes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softserve.edu.jroutes.component.RegUserInterface;
import com.softserve.edu.jroutes.dto.UserDTO;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.UserService;
import com.softserve.edu.jroutes.validators.profileValidator;

@Controller
public class EditProfileController {
	@Autowired
	private UserService userService;
	@Autowired
	RegUserInterface obj;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(ModelMap model) {
		User user = obj.getRegisteredUserObject();
		UserDTO userDTO = new UserDTO();	
		model.addAttribute("user", user);
		model.addAttribute("actProfile", "active");
		model.addAttribute("userDTO", userDTO);
		return "profile";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String processEditProfile(
			@ModelAttribute(value = "userDTO") UserDTO userDTO,
			BindingResult result, ModelMap model) {
		
		profileValidator.validate(userDTO, result);
		if(result.hasErrors()){
			return "profile";
		}
		User user = obj.getRegisteredUserObject();
		if (userDTO.getFirstName() != "") {
			user.setFirstName(userDTO.getFirstName());
		}
		if (userDTO.getLastName() != "") {
			user.setLastName(userDTO.getLastName());
		}
		
		if (userDTO.getPassword() != "" && userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
			 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 String usrPassword = userDTO.getPassword();
			 String hashedPassword = passwordEncoder.encode(usrPassword);
			 user.setPassword(hashedPassword);
		}
		try {
			model.addAttribute("data", "Data successfully changed");
			userService.updateUser(user);
				
		} catch (NonUniqueException e) {
			e.printStackTrace();
		}
		return profile(model);

	}
}
