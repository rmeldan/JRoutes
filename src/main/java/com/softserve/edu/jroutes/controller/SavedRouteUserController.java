package com.softserve.edu.jroutes.controller;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softserve.edu.jroutes.component.RegUserInterface;
import com.softserve.edu.jroutes.component.RouteAlgorithm;
import com.softserve.edu.jroutes.component.SavedRouteAlgorithm;
import com.softserve.edu.jroutes.component.SavedRouteComponent;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.dto.SavedRouteDto;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.validators.RouteBuiltValidation;
import com.softserve.edu.jroutes.validators.UserRouteSaveValidation;

/**
 * @author Roman
 */

@Controller
@RequestMapping("/")
public class SavedRouteUserController {	
	@Autowired
	private SavedRouteService savedRouteService;	
	@Autowired
    private UserRouteSaveValidation userRouteSaveValidation;
	@Autowired
	private SavedRouteAdminController savedRouteAdminController;
	@Autowired
	private SavedRouteComponent savedRouteComponent;	
	@Autowired
	private RouteBuiltValidation routeBuiltValidation;
	@Autowired
	private RegUserInterface registeredUserObject;
	private static final Logger LOGGER = Logger.getLogger(SavedRouteUserController.class);	
	
	@RequestMapping(value = "savedRoutesUser", method = RequestMethod.GET)
    public String savedRoutesUser(ModelMap model) {
		savedRouteComponent.fillPageUser(model);
		model.addAttribute("editedRoute", new SavedRouteDto());
		return "savedRoutesUser";
    }	
	
	@RequestMapping(value = "detailsOfSavedRoute", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> detailsOfSavedRoute(Long id, Long rpId, boolean isInBase) {
		LOGGER.info("Is route in base: " + isInBase);
		return savedRouteAdminController.detailsOfSavedRoute(id, rpId, isInBase);
	}
	
	@RequestMapping(value = "editSavedRoute", method = RequestMethod.GET)
    public void editSavedRoute(Long id) {	
		User user = registeredUserObject.getRegisteredUserObject();
		SavedRoute savedRoute = savedRouteService.getSavedRouteById(id);
		user.setSavedRouteEdit(savedRoute);
		LOGGER.info("Route " + savedRoute.getName() + " is editing");
	}

	@RequestMapping(value = "saveEditedRoute", method = RequestMethod.GET)
    public String saveEditedRoute(@ModelAttribute("editedRoute") SavedRouteDto sRDto, BindingResult result, ModelMap model) {
		routeBuiltValidation.validate(sRDto, result);
		if (result.hasErrors()) {
			model.addAttribute("error", true);			
			savedRouteComponent.fillPageUser(model);
			LOGGER.error("Validation error");
			return "savedRoutesUser";
		}
		User user = registeredUserObject.getRegisteredUserObject();
		SavedRoute savedRoute = user.getSavedRouteEdit();
		savedRoute.setName(sRDto.getName());
		try {	
			savedRouteService.updateSavedRoute(savedRoute);
			LOGGER.info("Saved route " + savedRoute.getName() + " was edited.");			
		} catch (NonUniqueException e) {
			result.rejectValue("name", "routes.validationNonUnique", e.getMessage());
			model.addAttribute("error", true);
			savedRouteComponent.fillPageUser(model);
			LOGGER.error("Name of route " + savedRoute.getName() + " is not unique.", e);
			return "savedRoutesUser";
		}
		LOGGER.info("Name of saved routes is " + savedRoute.getName());
		return "redirect:/savedRoutesUser";
	}	
	
	@RequestMapping(value = "deletingSavedRoute", method = RequestMethod.GET)
	public void deletingSavedRoute(Long id) {
		savedRouteService.deleteSavedRoute(savedRouteService.getSavedRouteById(id));
		LOGGER.info("Delete saved route by id:" + id);
	}
	
	@RequestMapping(value = "foundRouteSave/{idSR}/", method = RequestMethod.GET)
    public String foundRouteSave(ModelMap model) {
		LOGGER.info("Route is saving");
        return "userRouteSave";
    }
	
	@RequestMapping(value = "foundRouteSave/{idSR}/savingRoute", method = RequestMethod.POST)
	public String savingRoute(@PathVariable int idSR, @ModelAttribute RouteConnectPointDto routeConnectionDTO,
			BindingResult result, ModelMap model) {
		userRouteSaveValidation.validate(routeConnectionDTO, result);
		if (result.hasErrors()) {
			System.out.println("Validate error");
			return "redirect:/foundRouteSave/" + idSR + "/";
		}
		User user = registeredUserObject.getRegisteredUserObject();
		
		SavedRouteAlgorithm savedRouteAlgorithm = user.getSavedRoutesAlgorithm().get(idSR);
		SavedRoute savedRoute = new SavedRoute();		
		Set<Route> routes = new HashSet<Route>();			
		for(RouteAlgorithm routeAlgorithm : savedRouteAlgorithm.getRoutesAlgorithm()) {
			Route route = new Route();
			route.setRouteConnectionId(routeAlgorithm.getRouteConnectionId());
			route.setSequenceNumber((long) routeAlgorithm.getSequenceNumber());
			routes.add(route);
		}
		savedRoute.setRoutes(routes);	
		savedRoute.setName(routeConnectionDTO.getName());
		savedRoute.setIsCompanyRoute(false);
		savedRoute.setModificationTime(new java.sql.Date(
				(long) (new java.util.Date()).getTime()));		
		savedRoute.setUserId(user);
		for(Route route : savedRoute.getRoutes()) {
			route.setSavedRouteId(savedRoute);
		}
		try {
			savedRouteService.addSavedRoute(savedRoute);
			LOGGER.info("Route " + savedRoute.getName() + " was added.");
		} catch (NonUniqueException e) {
			LOGGER.error("Name of route " + savedRoute.getName() + " is not unique.", e);
			return "redirect:/foundRouteSave/" + idSR + "/";
		}
		User loggedUser = registeredUserObject.getRegisteredUserObject();
		Set<SecurityRole> loggedUserRoles = loggedUser.getRoles();
		for (SecurityRole securityRole : loggedUserRoles) {
			System.out.println("***" + securityRole.getName());
			if (securityRole.getName().equals("ROLE_ADMIN"))
				return "redirect:/routes/savedRoutesAdmin";
			if (securityRole.getName().equals("ROLE_SUPER-ADMIN"))
				return "redirect:/routes/savedRoutesAdmin";
			if (securityRole.getName().equals("ROLE_USER"))
				return "redirect:/savedRoutesUser";
		}
		return "redirect:/";
	}
}