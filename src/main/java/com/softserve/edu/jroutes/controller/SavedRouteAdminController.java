package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import org.apache.log4j.Logger;

import com.softserve.edu.jroutes.component.RegUserInterface;
import com.softserve.edu.jroutes.component.RouteAlgorithm;
import com.softserve.edu.jroutes.component.SavedRouteAlgorithm;
import com.softserve.edu.jroutes.component.SavedRouteComponent;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.dto.SavedRouteDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.validators.RouteBuiltValidation;

/**
 * @author Roman
 */

@Controller
@RequestMapping("/routes")
public class SavedRouteAdminController {
	@Autowired
	private RoutePointService routePointService;
	@Autowired
	private SavedRouteService savedRouteService;
	@Autowired
	private RouteBuiltValidation routeBuiltValidation;
	@Autowired
	private RegUserInterface registeredUserObject;
	@Autowired
	private SavedRouteComponent savedRouteComponent;
	private static final Logger LOGGER = Logger.getLogger(SavedRouteAdminController.class);	
	
	@RequestMapping(value = "/savedRoutesAdmin", method = RequestMethod.GET)
	public String savedRoutesAdmin(ModelMap model) {	
		User user = registeredUserObject.getRegisteredUserObject();
		user.setSavedRouteEdit(new SavedRoute());
		user.getrCpDtoList().clear();
		user.getrCpDtoListResult().clear();
		List<RoutePoint> routePoints = routePointService.getAllElements();		
		for (int i = 0; i < routePoints.size(); i++) {
			RouteConnectPointDto rCpDto = new RouteConnectPointDto();
			rCpDto.setId((long) i);
			rCpDto.setRoutePointBId(routePoints.get(i));
			user.getrCpDtoList().add(rCpDto);
		}
		savedRouteComponent.fillPageAdmin(model);
		model.addAttribute("savedRouteEdit", new SavedRoute());
		model.addAttribute("unwrap", "collapse");
		LOGGER.info("Size of route points list: " + routePoints.size());
		return "savedRoutesAdmin";
	}
	
	@RequestMapping(value = "/editingSavedRoute", method = RequestMethod.GET)
	public ResponseEntity<String> editingSavedRoute(Long id, Long rpId) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		List<RouteConnectPointDto> rCpDtoList = user.getrCpDtoList();
		rCpDtoListResult.clear();
		RouteConnectPointDto rCpDtoStart = new RouteConnectPointDto();
		rCpDtoListResult.add(rCpDtoStart);
		user.setSavedRouteEdit(savedRouteService.getSavedRouteById(id));
		savedRouteComponent.fillRoutes(user.getSavedRouteEdit(), rpId);
		rCpDtoStart.setId(0L);
		rCpDtoStart.setRoutePointBId(rCpDtoListResult.get(1).getRoutePointAId());
		savedRouteComponent.fillPossiblePoints(rCpDtoListResult.get(rCpDtoListResult.size() - 1));
		Set<Country> countries = new HashSet<Country>();
		for(RouteConnectPointDto rCpDtoVar : rCpDtoList) {
			countries.add(rCpDtoVar.getRoutePointBId().getCountry());
		}
		List<Object> json = new ArrayList<Object>();
		json.add(rCpDtoListResult);
		json.add(rCpDtoList);
		json.add(countries);		
		String jsonStr = new Gson().toJson(json);
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");
		LOGGER.info("Editing saved route " + user.getSavedRouteEdit().getName());
		return new ResponseEntity<String>(jsonStr, h, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/savingRoute", method = RequestMethod.GET)
	public String savingRoute(@ModelAttribute("savedRouteEdit") SavedRouteDto srDto,
			BindingResult result, ModelMap model) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		if (rCpDtoListResult.size() < 2) {
			result.rejectValue("name", "routes.validationNotExistRoute", "Побудуйте хоча б один відрізок!");
			savedRouteComponent.fillPageAdmin(model);
			model.addAttribute("error", true);
			LOGGER.error("Route not built");
			return "savedRoutesAdmin";
		}
		routeBuiltValidation.validate(srDto, result);
		if (result.hasErrors()) {
			savedRouteComponent.fillPageAdmin(model);
			model.addAttribute("error", true);
			LOGGER.error("Validation error");
			return "savedRoutesAdmin";
		}
		SavedRoute savedRoute = new SavedRoute();
		Set<Route> routes = new HashSet<Route>();
		if (srDto.getId() != null) {
			savedRoute = user.getSavedRouteEdit();
			routes = savedRoute.getRoutes();
			routes.clear();
		}
		savedRoute.setName(srDto.getName());
		savedRoute.setIsCompanyRoute(srDto.getIsCompanyRoute());
		savedRoute.setModificationTime(new java.sql.Date(
				(long) (new java.util.Date()).getTime()));		
		savedRoute.setUserId(user);
		for (int i = 1; i < rCpDtoListResult.size(); i++) {				
			Route route = new Route();
			route.setRouteConnectionId(rCpDtoListResult.get(i).getRouteConnectionId());
			route.setSavedRouteId(savedRoute);
			route.setSequenceNumber((long) i);
			routes.add(route);
		}		
		savedRoute.setRoutes(routes);
		try {
			if (savedRoute.getId() == null) {
				savedRouteService.addSavedRoute(savedRoute);
				LOGGER.info("Route " + savedRoute.getName() + " was added.");
			} else {			
				savedRouteService.updateSavedRoute(savedRoute);
				LOGGER.info("Saved route " + savedRoute.getName() + " was edited.");
			}
		} catch (NonUniqueException e) {
			result.rejectValue("name", "routes.validationNonUnique", e.getMessage());
			savedRouteComponent.fillPageAdmin(model);
			model.addAttribute("error", true);
			LOGGER.error("Name of route " + savedRoute.getName() + " is not unique.", e);
			return "savedRoutesAdmin";
		}
		return "redirect:/routes/savedRoutesAdmin";
	}	
	
	@RequestMapping(value = "/deletingSavedRoute", method = RequestMethod.GET)
	public void deletingSavedRoute(Long id) {
		savedRouteService.deleteSavedRoute(savedRouteService.getSavedRouteById(id));
		LOGGER.info("Delete saved route by id:" + id);
	}	
	
	@RequestMapping(value = "/detailsOfSavedRoute", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> detailsOfSavedRoute(Long id, Long rpId, boolean isInBase) {
		User user = registeredUserObject.getRegisteredUserObject();
		user.getrCpDtoListResult().clear();
		SavedRoute savedRoute = new SavedRoute();
		if (isInBase) {
			savedRoute = savedRouteService.getSavedRouteById(id);
		} else {
			SavedRouteAlgorithm savedRouteAlgorithm = user.getSavedRoutesAlgorithm().get((int) (long) id);
			Set<Route> routes = new HashSet<Route>();			
			for(RouteAlgorithm routeAlgorithm : savedRouteAlgorithm.getRoutesAlgorithm()) {
				Route route = new Route();
				route.setRouteConnectionId(routeAlgorithm.getRouteConnectionId());
				route.setSequenceNumber((long) routeAlgorithm.getSequenceNumber());
				routes.add(route);
			}
			savedRoute.setRoutes(routes);
		}
		savedRouteComponent.fillRoutes(savedRoute, rpId);
		List<Object> json = new ArrayList<Object>();
		json.add(user.getrCpDtoListResult());
		json.add(savedRoute.getName());
		String jsonStr = new Gson().toJson(json);
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");
		LOGGER.info("Show details of saved route " + savedRoute.getName());
		return new ResponseEntity<String>(jsonStr, h, HttpStatus.OK);
	}			
	
	@RequestMapping(value = "/changingCompany", method = RequestMethod.GET)
	protected void changingCompany(Long sendValue) {
		SavedRoute savedRoute = savedRouteService.getSavedRouteById(sendValue);
		if (savedRoute.getIsCompanyRoute() == true) {
			savedRoute.setIsCompanyRoute(false);
		} else {
			savedRoute.setIsCompanyRoute(true);
		}
		try {
			savedRouteService.updateSavedRoute(savedRoute);	
			LOGGER.info("Saved route " + savedRoute.getName() + " is company:" + savedRoute.getIsCompanyRoute());
		} catch (NonUniqueException e) {
			LOGGER.error("Saved route " + savedRoute.getName() + " is not unique.", e);
		}
	}	
}