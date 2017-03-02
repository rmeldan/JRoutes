package com.softserve.edu.jroutes.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.softserve.edu.jroutes.component.RegUserInterface;
import com.softserve.edu.jroutes.component.SavedRouteComponent;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.SavedRouteService;

/**
 * @author Roman
 */

@Controller
@RequestMapping("/routes")
public class RouteBuiltController {
	@Autowired
	private RoutePointService routePointService;
	@Autowired
	private SavedRouteService savedRouteService;
	@Autowired
	private RegUserInterface registeredUserObject;
	@Autowired
	private SavedRouteComponent sRComponent;
	private static final Logger LOGGER = Logger.getLogger(RouteBuiltController.class);
	
	@RequestMapping(value = "/addingRoute", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> addingRoute(@ModelAttribute RouteConnectPointDto rCpDtoId) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		RouteConnectPointDto rCpDto = null;	
		for (int i = 0; i < user.getrCpDtoList().size(); i++) {
			if (user.getrCpDtoList().get(i).getId() == rCpDtoId.getId()) {
				rCpDtoListResult.add(user.getrCpDtoList().get(i));			
				rCpDtoListResult.get(rCpDtoListResult.size() - 1)
						.setId(rCpDtoListResult.size() - 1L);
				rCpDto = user.getrCpDtoList().get(i);
			}
		}		
		sRComponent.fillPossiblePoints(rCpDto);
		String s = new String();
		s.length();
		Set<Country> countries = new HashSet<Country>();
		for(RouteConnectPointDto rCpDtoVar : user.getrCpDtoList()) {
			countries.add(rCpDtoVar.getRoutePointBId().getCountry());
		}
		List<Object> json = new ArrayList<Object>();
		json.add(rCpDto);
		json.add(user.getrCpDtoList());
		json.add(countries);
		String jsonStr = new Gson().toJson(json);
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");
		LOGGER.info("Point " + rCpDto.getRoutePointBId().getName() + " was added.");
		return new ResponseEntity<String>(jsonStr, h, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetingRoute/{id}/Ajax", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> resetingRouteAjax(@PathVariable Integer id) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		List<RouteConnectPointDto> rCpDtoList = user.getrCpDtoList();
		for (int i = rCpDtoListResult.size() - 1; i >= 0; i--) {
			if (rCpDtoListResult.get(i).getId() >= id) {
				rCpDtoListResult.remove(i);
			}
		}
		List<Object> json = new ArrayList<Object>();
		if (rCpDtoListResult.isEmpty()) {
			rCpDtoList.clear();
			List<RoutePoint> routePoints = routePointService.getAllElements();		
			for (int i = 0; i < routePoints.size(); i++) {
				RouteConnectPointDto rCpDto = new RouteConnectPointDto();
				rCpDto.setId((long) i);
				rCpDto.setRoutePointBId(routePoints.get(i));
				rCpDtoList.add(rCpDto);
			}			
			json.add(null);
			LOGGER.info("After reseting list is empty.");
		} else {
			sRComponent.fillPossiblePoints(rCpDtoListResult.get(id - 1));
			json.add(rCpDtoListResult.get(id - 1));
			LOGGER.info("After reseting last point is " + rCpDtoListResult.get(id - 1).getRoutePointBId().getName());
		}
		Set<Country> countries = new HashSet<Country>();
		for(RouteConnectPointDto rCpDtoVar : user.getrCpDtoList()) {
			countries.add(rCpDtoVar.getRoutePointBId().getCountry());
		}	
		json.add(user.getrCpDtoList());
		json.add(countries);
		String jsonStr = new Gson().toJson(json);
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");
		return new ResponseEntity<String>(jsonStr, h, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/resetingRoute/{id}", method = RequestMethod.GET)
	public String resetingRoute(@PathVariable Integer id, ModelMap model) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		for (int i = rCpDtoListResult.size() - 1; i >= 0; i--) {
			if (rCpDtoListResult.get(i).getId() >= id) {
				rCpDtoListResult.remove(i);
			}
		}
		if (rCpDtoListResult.isEmpty()) {
			List<RoutePoint> routePoints = routePointService.getAllElements();
			for (int i = 0; i < routePoints.size(); i++) {
				RouteConnectPointDto rCpDto = new RouteConnectPointDto();
				rCpDto.setId((long) i);
				rCpDto.setRoutePointBId(routePoints.get(i));
				user.getrCpDtoList().add(rCpDto);
			}
		} else {
			sRComponent.fillPossiblePoints(rCpDtoListResult.get(id - 1));
		}
		model.addAttribute("savedRouteEdit", user.getSavedRouteEdit());
		sRComponent.fillPageAdmin(model);
		LOGGER.info("After reseting last point is " + rCpDtoListResult.get(id - 1).getRoutePointBId().getName());
		return "savedRoutesAdmin";
	}
	
	@RequestMapping(value = "/resetingRoute/{id}/clearForm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> clearForm(@PathVariable Integer id) {
		User user = registeredUserObject.getRegisteredUserObject();
		user.setSavedRouteEdit(new SavedRoute());
		LOGGER.info("Form clear");
		return resetingRouteAjax(id);
	}
	
	@RequestMapping(value = "/fillComboBox", method = RequestMethod.GET)
	protected @ResponseBody	ResponseEntity<String> fillComboBox(Long sendValue) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoList = user.getrCpDtoList();
		String json = null;
		if (sendValue != -1) {
			List<RouteConnectPointDto> rCpDtoSelectList = new ArrayList<RouteConnectPointDto>();
			for (RouteConnectPointDto rCpDto : rCpDtoList) {
				if (rCpDto.getRoutePointBId().getCountry().getId() == sendValue) {
					rCpDtoSelectList.add(rCpDto);
				}
			}
			json = new Gson().toJson(rCpDtoSelectList);
			LOGGER.info("ComboBox has " + rCpDtoSelectList.size() + " points");
		} else {
			json = new Gson().toJson(rCpDtoList);
			LOGGER.info("ComboBox has " + rCpDtoList.size() + " points");
		}
		HttpHeaders h = new HttpHeaders();
		h.add("Content-type", "text/html;charset=UTF-8");		
		return new ResponseEntity<String>(json, h, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hintsForNameOfSavedRoute", method = RequestMethod.GET)
	public @ResponseBody String hintsForNameOfSavedRoute(@RequestParam String enter)
			throws UnsupportedEncodingException {
		LOGGER.info("Enter: " + enter);
		User user = registeredUserObject.getRegisteredUserObject();
		final String savedRouteRegex = "^[A-Za-z0-9 ']{1,}$";	
		Pattern p = Pattern.compile(savedRouteRegex);
		Matcher m = p.matcher(enter);
		String message = "";
		if (enter.length() != 0) {
			if (!m.matches()) {
				message = "1";
			} else if (enter.toLowerCase().charAt(0) == enter.charAt(0)) {
				message = "2";
			} else if (enter.length() > 30 || enter.length() < 3) {
				message = "3";
			} else {
				if (user.getSavedRouteEdit().getName() != null
						&& user.getSavedRouteEdit().getName().equalsIgnoreCase(enter)) {
				} else {
					List<SavedRoute> sRList = savedRouteService
							.getSavedRoutesByCriteria(null, enter);
					if (sRList.size() > 0) {
						message = "4";
					}
				}
			}
		}
		LOGGER.info("Hints code is " + message);
		return message;
	}
}