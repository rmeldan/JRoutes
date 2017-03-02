/*
 * SecurityRoleController
 * 
 * version 0.0.1
 * 
 * 05.04.2014
 * 
 * Controller class to handle requests that belongs to security role
 * 
 */

package com.softserve.edu.jroutes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softserve.edu.jroutes.dto.SecurityRoleDTO;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;

/**
 * @author Yuriy
 */

@Controller
@RequestMapping("/securityRoles")
public class SecurityRoleController {

	@Autowired
	private SecurityRoleService securityRoleService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityRoleVaidator securityRoleValidator;

	/**
	 * Handles request for list of roles
	 * 
	 * @param model
	 * @return body 'roles'
	 */
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String showRoles(ModelMap model) {
		List<SecurityRole> roles = securityRoleService.getAllSecurityRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("actRoles", "active");

		return "roles";
	}

	/**
	 * Handles request for list of roles (with parameters)
	 * 
	 * @param message
	 *            - message to send to jsp page
	 * @param type
	 *            - type of message, which is sending
	 * @param model
	 * @return body 'roles'
	 */
	@RequestMapping(value = "/roles", params = { "message", "type" }, method = RequestMethod.GET)
	public String showRolesWithPar(
			@RequestParam(value = "message") String message,
			@RequestParam(value = "type") String type, ModelMap model) {
		List<SecurityRole> roles = securityRoleService.getAllSecurityRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("roleMessage", message);
		model.addAttribute("messageType", type);
		model.addAttribute("actRoles", "active");

		return "roles";
	}

	/**
	 * Handles request to add new SecurityRole
	 * 
	 * @param securityRole
	 * @param model
	 * @return redirect to roles page
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addRole(@ModelAttribute SecurityRoleDTO securityRole,
			ModelMap model, BindingResult result) {
		SecurityRole role = new SecurityRole();
		role.setName(securityRole.getName());

		securityRoleValidator.validate(securityRole, result);
		if (result.hasErrors()) {
			System.out.println("SECURITY_ROLE SERVER VALIDATION FAILS!");
			model.addAttribute("message",
					"Server validation fails: " + result.getAllErrors());
			model.addAttribute("type", "ERROR");
			return "redirect:/securityRoles/roles";
		}

		try {
			securityRoleService.addSecurityRole(role);
			model.addAttribute("message",
					"Security role was successfuly added.");
			model.addAttribute("type", "INFO");
		} catch (NonUniqueException e) {
			model.addAttribute("message", "Cannot add role: " + e.getMessage());
			model.addAttribute("type", "ERROR");
		}

		return "redirect:/securityRoles/roles";
	}

	/**
	 * Handles request for edit form
	 * 
	 * @param id
	 *            - role id, which will be edited in form
	 * @param model
	 * @return edit role page
	 */
	@RequestMapping(value = "/editForm/{id}", method = RequestMethod.GET)
	public String editForm(@PathVariable Long id, ModelMap model) {
		SecurityRole role = securityRoleService.getSecurityRoleById(id);
		model.addAttribute("role", role);

		return "editSecurityRole";
	}

	/**
	 * Handles request for edit role
	 * 
	 * @param securityRole
	 *            - new version of SecurityRole
	 * @param model
	 * @return roles page
	 */
	@RequestMapping(value = "editForm/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute SecurityRoleDTO securityRole,
			ModelMap model, BindingResult result) {
		SecurityRole role = securityRoleService
				.getSecurityRoleById(securityRole.getId());
		role.setName(securityRole.getName());
		
		securityRoleValidator.validate(securityRole, result);
		if (result.hasErrors()) {
			System.out.println("SECURITY_ROLE SERVER VALIDATION FAILS!");
			model.addAttribute("message",
					"Server validation fails: " + result.getAllErrors());
			model.addAttribute("type", "ERROR");
			return "redirect:/securityRoles/roles";
		}
		
		try {
			securityRoleService.updateSecurityRole(role);
			model.addAttribute("message", "Role was successfuly edited.");
			model.addAttribute("type", "INFO");
		} catch (NonUniqueException e) {
			model.addAttribute("message", "Cant edit role: " + e.getMessage());
			model.addAttribute("type", "ERROR");
		}

		return "redirect:/securityRoles/roles";
	}

	/**
	 * Handles request for deleting SecurityRole
	 * 
	 * @param id
	 *            - id of SecurityRole to delete
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deletingSecurityRole/{id}", method = RequestMethod.GET)
	public String deleteCountry(@PathVariable(value = "id") Long id,
			ModelMap model) {
		SecurityRole role = securityRoleService.getSecurityRoleById(id);	
		String deletingResult = null;
		String messageType = null;
		try {
			deletingResult = "Security role was successfully deleted";
			messageType = "INFO";
			securityRoleService.deleteSecurityRole(role);
		} catch (Exception e) {
			deletingResult = "SecurityRole was not deleted: somebody use this role";
			messageType = "ERROR";
		}
		model.addAttribute("message", deletingResult);
		model.addAttribute("type", messageType);

		return "redirect:/securityRoles/roles";
	}

}