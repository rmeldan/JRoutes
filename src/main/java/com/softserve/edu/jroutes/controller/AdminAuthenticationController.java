package com.softserve.edu.jroutes.controller;

import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.service.ElementService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class AdminAuthenticationController {
	@Autowired
	@Qualifier("countryService")
	private ElementService<Country> countryService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String welcome(){
		return "admin";
	}

	@RequestMapping(value = "/adminHome", method = RequestMethod.GET)
	public String adminHome(ModelMap model) {
		return "adminHome";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String users(ModelMap model) {
		model.addAttribute("actUsers", "active");
		return "users";
	}

}
