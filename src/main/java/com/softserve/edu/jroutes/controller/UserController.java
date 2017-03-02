package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.softserve.edu.jroutes.dto.UserDTO;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;

/**
 * @author Yuriy
 * 
 */
@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private SecurityRoleService securityRoleService;

	@Autowired
	private UserService userService;

	/**
	 * Handles request for showing list of users
	 * 
	 * @param model
	 * @return user page
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showRoles(ModelMap model) {
		List<User> users = userService.getPageOfUsers(2, 0);
		model.addAttribute("users", users);
		List<User> usersAll = userService.getAllUsers();
		model.addAttribute("usersCount", usersAll.size());
		model.addAttribute("actUsers", "active");

		return "users";
	}

	/**
	 * Handles request for list of users by ajax.
	 * 
	 * @param sendingValue
	 * @return list of users in json format
	 */
	@RequestMapping(value = "list/getUserList", method = RequestMethod.GET)
	public @ResponseBody
	String doMethod(@RequestParam String sendingValue,
			@RequestParam String pageNumber) {
		List<User> users = userService.getPageOfUsers(
				Integer.parseInt(sendingValue), Integer.parseInt(pageNumber));
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for (int i = 0; i < users.size(); i++) {
			UserDTO user = new UserDTO();
			user.setConfirmPassword("qqq");
			user.setEmail(users.get(i).getEmail());
			user.setFirstName(users.get(i).getFirstName());
			user.setLastName(users.get(i).getLastName());
			user.setIsBlocked(users.get(i).getIsBlocked());
			user.setId(users.get(i).getId());
			user.setPassword("dss");
			dtos.add(user);
		}
		String userList = new Gson().toJson(dtos);

		return userList;
	}

	/**
	 * Handles request for user count
	 * 
	 * @return user count
	 */
	@RequestMapping(value = "list/getUserCount", method = RequestMethod.GET)
	public @ResponseBody
	String doMethod2() {
		List<User> users = userService.filterUsers(userService.getAllUsers());
		String uCount = new Gson().toJson(users.size());

		return uCount;
	}

	/**
	 * Handles request for lock/unlock user, called by ajax.
	 * 
	 * @param userId
	 * @return user isBlocked state
	 */
	@RequestMapping(value = "list/changeLock", method = RequestMethod.GET)
	public @ResponseBody
	String changeLock(@RequestParam String userId) {
		User user = userService.getUserById(Long.parseLong(userId));
		if (user.getIsBlocked()) {
			user.setIsBlocked(false);
		} else {
			user.setIsBlocked(true);
		}
		try {
			userService.updateUser(user);
		} catch (NonUniqueException e) {
			e.printStackTrace();
		}
		String json = new Gson().toJson(user.getIsBlocked());
		System.out.println(json);

		return json;
	}

	/**
	 * Handles request for page to edit user roles
	 * 
	 * @param id
	 *            - id of user to edit roles
	 * @param model
	 * @return edit user roles page
	 */
	@RequestMapping(value = "/manageRoles/{id}", method = RequestMethod.GET)
	public String manageRoles(@PathVariable Long id, ModelMap model) {
		User user = userService.getUserById(id);
		List<SecurityRole> roles = securityRoleService
				.getOtherSecurityRoles(user.getRoles());
		model.addAttribute("exceptRoles", roles);
		model.addAttribute("user", user);
		model.addAttribute("userID", user.getId());

		return "manageUserSecurityRole";
	}

	/**
	 * Handles request for editing user roles
	 * 
	 * @param sendingRoles
	 * @return message, if roles were successfully edited, in json format
	 */
	@RequestMapping(value = "manageRoles/editRoles", method = RequestMethod.GET)
	public @ResponseBody
	String saveChanges(@RequestParam String sendingRoles,
			@RequestParam String userID) {
		String[] roleList = sendingRoles.split(",");
		User user = userService.getUserById(Long.parseLong(userID));
		user.getRoles().clear();
		for (int i = 0; i < roleList.length - 1; i++) {
			ArrayList<SecurityRole> list = (ArrayList<SecurityRole>) securityRoleService
					.getSecurityRolesByCriteria(roleList[i]);
			user.getRoles().add(list.get(0));
		}
		String message = null;
		try {
			userService.updateUser(user);
			message = "User roles were successfully edited.";
		} catch (NonUniqueException e) {
			e.printStackTrace();
			message = "Can not update user roles.";
		}
		String result = new Gson().toJson(message);

		return result;
	}

}