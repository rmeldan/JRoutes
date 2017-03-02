package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.softserve.edu.jroutes.dto.RoutePointDTO;
import com.softserve.edu.jroutes.dto.RoutePointSearchDTO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RoutePointService;

/**
 * 
 * @author Volodymyr
 * 
 */

@Controller
@RequestMapping("/cities")
public class RoutePointController {
	@Autowired
	private RoutePointService routePointService;
	@Autowired
	private ElementService<Country> countryService;

	String result = "";
	String error = "";

	 public static final Logger  LOG=Logger.getLogger(RoutePointController.class);

	@RequestMapping(value = "/citiesList", method = RequestMethod.GET)
	public String cities(ModelMap model) {

		LOG.info("Strat routePointPage");
		List<RoutePoint> routePointList = routePointService.getAllElements();
		List<Country> countryList = countryService.getAllElements();
		List<RoutePointSearchDTO> search = new ArrayList<RoutePointSearchDTO>();

		for (RoutePoint temp : routePointList) {
			search.add(new RoutePointSearchDTO(temp.getId(), temp.getName(),
					temp.getCountry().getName(), temp.getIsBlocked()));
		}

		System.out.print("list");
		model.addAttribute("routePointList", search);
		model.addAttribute("countryList", countryList);
		model.addAttribute("actCities", "active");
		model.addAttribute("result", result);
		model.addAttribute("error", error);
		result = "";
		error = "";

		return "cities";
	}

	@RequestMapping(value = "/lock/{id}", method = RequestMethod.GET)
	public String lock(@PathVariable Long id, ModelMap model) {
		RoutePoint routePoint = routePointService.getElementByID(id);

		routePoint.setIsBlocked(true);
		routePoint.setName("a");
		try {
			routePointService.updateElement(routePoint);
		} catch (NonUniqueException e) {
			e.printStackTrace();
		}
		return "redirect:/cities/citiesList";
	}

	@RequestMapping(value = "/unlock/{id}", method = RequestMethod.GET)
	public String unlock(@PathVariable Long id, ModelMap model) {
		RoutePoint routePoint = routePointService.getElementByID(id);
		routePoint.setIsBlocked(false);
		routePoint.setName("a");
		try {
			routePointService.updateElement(routePoint);
		} catch (NonUniqueException e) {
			e.printStackTrace();
		}
		return "redirect:/cities/citiesList";
	}

	@RequestMapping(value = "/editForm/{id}", method = RequestMethod.GET)
	public String editForm(@PathVariable Long id, ModelMap model) {
		LOG.info("edit routePoint");
		List<Country> countryList = countryService.getAllElements();
		RoutePoint routePoint = routePointService.getElementByID(id);

		model.addAttribute("country", routePoint.getCountry().getName());
		model.addAttribute("city", routePoint);
		model.addAttribute("countryList", countryList);

		return "editCity";
	}

	@RequestMapping(value = "editForm/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute RoutePointDTO dto, ModelMap model) {
		RoutePoint routePoint = routePointService.getElementByID(dto.getId());

		Country country = countryService.getElementByID(dto.getCountry());

		routePoint.setName(dto.getName());
		routePoint.setCountry(country);
		System.err.println(dto.getIsBlocked());
		routePoint.setIsBlocked(dto.getIsBlocked());

		result = "Element edited.";
		try {
			routePointService.updateElement(routePoint);
		} catch (NonUniqueException e) {
			error = "Cannot edit element: " + e.getMessage();
		}

		return "redirect:/cities/citiesList";

	}

	@RequestMapping(value = "/deletingCities/{id}", method = RequestMethod.GET)
	public String deleteRoutePoint(@PathVariable(value = "id") Long id,
			ModelMap model) {
		LOG.info("delete routePointPage");
		RoutePoint entity = routePointService.getElementByID(id);
		result = "Element deleted";
		try {
			routePointService.deleteElement(entity);
		} catch (Exception e) {
			error = "Element was not deleted";
		}
		model.addAttribute("routePoint", entity);
		return "redirect:/cities/citiesList";
	}

	@RequestMapping(value = "/addingRoutePoint", method = RequestMethod.POST)
	public String addRoutePoint(@ModelAttribute RoutePointDTO dto,
			ModelMap model) {
		RoutePoint routePoint = new RoutePoint();

		Country country = countryService.getElementByID(dto.getCountry());

		routePoint.setName(dto.getName());
		routePoint.setCountry(country);

		try {
			routePointService.addElement(routePoint);
			result = "Element added";
		} catch (NonUniqueException exception) {
			error = "Element already exist";
			result = "";
		}

		return "redirect:/cities/citiesList";

	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String find(@ModelAttribute RoutePoint dto, ModelMap model) {

		List<RoutePoint> routePoint = routePointService.getElementsByCriteria(
				null, dto.getName());

		List<Country> country = countryService.getElementsByCriteria(dto
				.getName());

		List<RoutePoint> routePointList = routePointService.getAllElements();
		List<Country> countryList = countryService.getAllElements();

		if (dto.getName().equals(""))
			return "redirect:/cities/citiesList";
		if (routePoint.isEmpty()) {
			for (Country countryTemp : country) {
				for (RoutePoint routePointTemp : routePointList) {
					if (countryTemp.getId() == routePointTemp.getCountry()
							.getId()) {
						routePoint.add(routePointTemp);
					}
				}
			}
			model.addAttribute("routePointList", routePoint);
		} else {
			model.addAttribute("routePointList", routePoint);
		}

		model.addAttribute("countryList", countryList);
		model.addAttribute("actCities", "active");
		model.addAttribute("result", result);
		model.addAttribute("error", error);
		result = "";
		error = "";
		return "redirect:/cities/citiesList";
	}

	@RequestMapping(value = "/getElement", method = RequestMethod.GET)
	protected @ResponseBody
	String method(@RequestParam String values) {

		List<RoutePoint> routePoint = null;
		List<Country> country = null;
		String[] data = values.split(" ");
		if (data[1].equals("City")) {
			routePoint = routePointService.getElementsByCriteria(null, data[0]);
		}
		if (data[1].equals("Country")) {
			routePoint = new ArrayList<RoutePoint>();

			country = countryService.getElementsByCriteria(null, data[0]);
			for (Country temp : country) {
				for (RoutePoint rp : routePointService.getAllElements()) {
					if (temp.getId() == rp.getCountry().getId()) {
						routePoint.add(rp);
					}
				}
			}
		}

		List<RoutePointSearchDTO> list = new ArrayList<RoutePointSearchDTO>();

		for (RoutePoint temp : routePoint) {
			list.add(new RoutePointSearchDTO(temp.getId(), temp.getName(), temp
					.getCountry().getName(), temp.getIsBlocked()));
		}

		Gson gson = new Gson();
		String json = gson.toJson(list);
		return json;
	}

}
