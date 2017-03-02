package com.softserve.edu.jroutes.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class AdminHomeConroller {
	@Controller
	@RequestMapping("/")
	public class AdminAuthenticationController {

		@RequestMapping(value = "/admin", method = RequestMethod.GET)
		public String welcome(ModelMap model) {
			model.addAttribute("message",
					"Maven Web Project! + Spring 3 MVC - welcome()");
			return "admin";
		}

		@RequestMapping(value = "/adminHome", method = RequestMethod.GET)
		public String adminHome(ModelMap model) {
			model.addAttribute("message",
					"Maven Web Project! + Spring 3 MVC - welcome()");
			return "adminHome";
		}

		@RequestMapping(value = "/roles", method = RequestMethod.GET)
		public String goToRole(ModelMap model) {
			model.addAttribute("message",
					"Maven Web Project! + Spring 3 MVC - welcome()");
			return "roles";
		}

	}
}
