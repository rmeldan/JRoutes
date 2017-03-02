package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.softserve.edu.jroutes.dto.RouteConnectionDTO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;


/*
 * Controller get all request 
 */
@Controller
@SessionAttributes(value = { "routeConnectionList", "routeConnectionDto",
		"transportList", "transport", "countryList", "country", "dto", "perPage" })
// @RequestMapping("/routeconnection")
public class RouteConnectionController {
	String dangerResult = "";
	String successResult = "";
	String dangerResultEdit="";
	@Autowired
	private RoutePointService routePointService;
	@Autowired
	private ElementService<Country> countryService;
	@Autowired
	private ElementService<Transport> transportService;
	@Autowired
	private RouteConnectionService routeConnectionService;
	@Autowired
	private RouteConnectionValidation routeConnectionValidation;

	@RequestMapping(value = "/routeconnection", method = RequestMethod.GET)
	public String routeconnection(ModelMap model) {
		List<RouteConnectionDTO> list = routeConnectionService.getAllDtoElements();
		List<Transport> transportList = transportService.getAllElements();
		List<Country> countryList = countryService.getAllElements();
		model.addAttribute("dto", new RouteConnectionDTO());
		model.addAttribute("transportList", transportList);
		model.addAttribute("transport", new Transport());
		model.addAttribute("countryList", countryList);
		model.addAttribute("country", new Country());
		model.addAttribute("routeConnectionList", list);
		model.addAttribute("routeConnectionDto", new RouteConnectionDTO());
		model.addAttribute("dangerResult", dangerResult);
		model.addAttribute("successResult", successResult);
		dangerResult = "";
		successResult = "";
		dangerResultEdit="";
		model.addAttribute("perPage", 5);
		model.addAttribute("actRouteConnection", "active");
		return "routeconnection";
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteRouteConnection(@PathVariable("id") Long id) {
		
		
		try{
			dangerResult = "routeconnection.validation.errorDelete";
			successResult = "";	
		routeConnectionService.deleteElementById(id);
			dangerResult = "";
			successResult = "routeconnection.validation.successDelete";
		
		}finally{
		return "redirect:/routeconnection";
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editRC(@PathVariable("id") Long id,
			ModelMap model) {
		System.out.println("Started");
		RouteConnection routeConnection = routeConnectionService
				.getElementByID(id);
		List<RoutePoint> routePointFrom = routeConnectionService
				.getCitiesByCountryId(routeConnection.getRoutePointAId()
						.getCountry().getId());
		List<RoutePoint> routePointTo = routeConnectionService
				.getCitiesByCountryId(routeConnection.getRoutePointBId()
						.getCountry().getId());

		List<Transport> transportList = transportService.getAllElements();
		List<Country> countryList = countryService.getAllElements();
		model.addAttribute("dangerResultEdit", dangerResultEdit);
		dangerResultEdit="";
		model.addAttribute("id", id);
		model.addAttribute("routePointFrom", routePointFrom);
		model.addAttribute("routePointTo", routePointTo);
		model.addAttribute("routePoint", new RoutePoint());

		model.addAttribute("countryList", countryList);
		model.addAttribute("country", new Country());
		model.addAttribute("transportList", transportList);
		model.addAttribute("transport", new Transport());
		
		model.addAttribute("pointAId", routeConnection.getRoutePointAId().getId());
		model.addAttribute("pointBId", routeConnection.getRoutePointBId().getId());
		model.addAttribute("countryAId", routeConnection.getRoutePointAId().getCountry().getId());
		model.addAttribute("countryBId", routeConnection.getRoutePointBId().getCountry().getId());
		model.addAttribute("transportId", routeConnection.getTransportId().getId());
		model.addAttribute("price", routeConnection.getPrice());
		model.addAttribute("time", routeConnectionService.convertDateToString(routeConnection.getTime()));
		model.addAttribute("isBlocked", routeConnection.getIsBlocked());
		System.out.println("end of edit controller");
		return "editRouteConn";
	}

	@RequestMapping(value = "/addRouteConnection", method = RequestMethod.POST)
	public String addRouteConnection(
			@ModelAttribute("dto") RouteConnectionDTO dto,
			RedirectAttributes attr, BindingResult result, ModelMap model) {
		RouteConnection routeConnection = new RouteConnection();
		routeConnectionValidation.validate(dto, result);
		Long pointAId = dto.getRoutePointA();
		Long pointBId = dto.getRoutePointB();
		Long countryAId = dto.getCountryA();
		Long countryBId = dto.getCountryB();
		Long transportId = dto.getTransport();
		
		model.addAttribute("pointAId", pointAId);
		model.addAttribute("pointBId", pointBId);
		model.addAttribute("countryAId", countryAId);
		model.addAttribute("countryBId", countryBId);
		model.addAttribute("transportId", transportId);
		model.addAttribute("routePointFrom",routeConnectionService
				.getCitiesByCountryId(countryAId));
		model.addAttribute("routePointTo",routeConnectionService
				.getCitiesByCountryId(countryBId));
		model.addAttribute("routePoint", new RoutePoint());
		//model.addAttribute("price",dto.getPrice());
		//model.addAttribute("time",dto.getTime());
		
		
		if (result.hasErrors()) {			
			return "routeconnection";
		}

		Long price = Long.parseLong(dto.getPrice());
		Long time = routeConnectionService.convertDateToLong(dto.getTime());

		RoutePoint pointA = (RoutePoint) routePointService
				.getElementByID(pointAId);
		RoutePoint pointB = (RoutePoint) routePointService
				.getElementByID(pointBId);
		Transport transport = (Transport) transportService
				.getElementByID(transportId);

		routeConnection.setRoutePointAId(pointA);
		routeConnection.setRoutePointBId(pointB);
		routeConnection.setTransportId(transport);
		routeConnection.setPrice(price);
		routeConnection.setTime(time);
		
		try {
			routeConnectionService.addElement(routeConnection);
			successResult = "routeconnection.uniq.successAdd";
			dangerResult="";
		} catch (NonUniqueException exception) {
			successResult="";
			dangerResult = "routeconnection.uniq.errorAdd";
		}

		return routeconnection(model);
	}

	@RequestMapping(value = "/changeElementsPerPage/{value}", method = RequestMethod.GET)
	public String changeAmountElementsPerPage(
			@PathVariable("value") Long value, ModelMap model) {
		model.addAttribute("perPage", value);
		return "routeconnection";
	}

	@RequestMapping(value = "/updateRC/{id}", method = RequestMethod.POST)
	public String updateRouteConnection(
			@ModelAttribute("dto") RouteConnectionDTO dto,
			BindingResult result, @PathVariable("id") Long id, ModelMap model) {
		System.out.println("Started Upd");
		Long pointAId = dto.getRoutePointA();
		Long pointBId = dto.getRoutePointB();
		Long countryAId = dto.getCountryA();
		Long countryBId = dto.getCountryB();
		Long transportId = dto.getTransport();
		
		model.addAttribute("id", id);
		model.addAttribute("pointAId", pointAId);
		model.addAttribute("pointBId", pointBId);
		model.addAttribute("countryAId", countryAId);
		model.addAttribute("countryBId", countryBId);
		model.addAttribute("transportId", transportId);
		model.addAttribute("routePointFrom",routeConnectionService
				.getCitiesByCountryId(countryAId));
		model.addAttribute("routePointTo",routeConnectionService
				.getCitiesByCountryId(countryBId));
		model.addAttribute("routePoint", new RoutePoint());
		
		
		routeConnectionValidation.validate(dto, result);
		if (result.hasErrors()) {
			return "editRouteConn";
		}

		RouteConnection routeConnection = routeConnectionService
				.getElementByID(id);

		Long price = Long.parseLong(dto.getPrice());
		Long time = routeConnectionService.convertDateToLong(dto.getTime());
		Boolean isBlocked = dto.getIsBlocked();

		RoutePoint pointA = (RoutePoint) routePointService
				.getElementByID(pointAId);
		RoutePoint pointB = (RoutePoint) routePointService
				.getElementByID(pointBId);
		Transport transport = (Transport) transportService
				.getElementByID(transportId);

		routeConnection.setRoutePointAId(pointA);
		routeConnection.setRoutePointBId(pointB);
		routeConnection.setTransportId(transport);
		routeConnection.setPrice(price);
		routeConnection.setTime(time);
		routeConnection.setIsBlocked(isBlocked);
		
		try {
			routeConnectionService.updateElement(routeConnection);
			successResult = "routeconnection.validation.successEdit";
			dangerResultEdit="";
			dangerResult="";
		} catch (NonUniqueException exception) {
			dangerResultEdit = "routeconnection.validation.errorEdit";
			model.addAttribute("dangerResultEdit", dangerResultEdit);
			return "editRouteConn";
		}
		System.out.println("Stoped Upd");
		return "redirect:/routeconnection";
	}

	@RequestMapping(value = "/getCities", method = RequestMethod.GET)
	protected @ResponseBody
	String doMethod(@RequestParam String sendValue) {
		System.out
				.println("--------11111111111111111111111111111111111111-----------------");
		Long sendValueId = Long.parseLong(sendValue);
		Country country = countryService.getElementByID(sendValueId);
		List<RoutePoint> routePointList = new ArrayList<RoutePoint>();
		List<RoutePoint> allRoutePointList = routePointService.getAllElements();
		for (int i = 0; i < allRoutePointList.size(); i++) {
			RoutePoint rp = allRoutePointList.get(i);
			if (rp.getCountry().getId() == country.getId()) {
				routePointList.add(rp);
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(routePointList);
		return json;
	}
	
	@RequestMapping(value = "/filterCity", method = RequestMethod.GET)
	protected @ResponseBody
	String filterFromCity(@RequestParam String fromCity) {
		System.out
				.println("--------22222222222222222222222-----------------");
		String[] data=fromCity.split(" ");
		System.out.println("from city is -"+data[0]+"-"+data.length);
		List<RouteConnectionDTO> list = routeConnectionService.getAllDtoElements();
		List<RouteConnectionDTO> filteredList=new ArrayList<RouteConnectionDTO>();
		for(RouteConnectionDTO dto:list) {
			if(data[1].equals("filter_fromCity")) {
				if( dto.getRoutePointAName().contains(data[0]) ) {
					filteredList.add(dto);
					
				}	
			} else {
				if( dto.getRoutePointBName().contains(data[0]) ) {
					filteredList.add(dto);
				}
			}
			
		}
		System.out.println("size is: "+filteredList.size());
		Gson gson = new Gson();
		String json = gson.toJson(filteredList);
		if(data[0].equals("")) {
			return gson.toJson(list);
		}
		return json;
	}
	
	
	

}