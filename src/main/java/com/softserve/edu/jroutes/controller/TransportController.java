package com.softserve.edu.jroutes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.edu.jroutes.dto.TransportDTO;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.ElementService;


@Controller
@RequestMapping("/transport")
public class TransportController {
	
	@Autowired
	private ElementService<Transport> transportService;
	
	private String infoMessage = "";
	private String errorMessage = "";
	
	@RequestMapping(value = "/transportList", method = RequestMethod.GET)
	public String transport(ModelMap model) {
		List<Transport> transportList = transportService.getAllElements();
		model.addAttribute("transportList", transportList);
		model.addAttribute("infoMessage", infoMessage);
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("actTransport", "active");
		infoMessage = "";
		errorMessage = "";
		return "transport";
	}
	
	@RequestMapping(value = "/deletingTransport/{id}", method = RequestMethod.GET)
	public String deleteCountry(@PathVariable(value = "id") Long id, ModelMap model) {
		
		Transport entity = transportService.getElementByID(id);
		String deletingResult = "Transport was successfully deteted";
		try {
			transportService.deleteElement(entity);
		} catch (Exception e) {
			deletingResult = "Transport was not deleted";
		}
		model.addAttribute("deletingResult", deletingResult);
		return "redirect:/transport/transportList";
	}
	
	@RequestMapping(value = "/addingTransport", method = RequestMethod.POST)
	public String addTransport(@ModelAttribute TransportDTO transport,ModelMap model) {
		Transport entity = new Transport();
		entity.setName(transport.getName());
		infoMessage = "Transport was successfully saved";
		try {
			transportService.addElement(entity);
		} catch (NonUniqueException e) {
			errorMessage = "Cannot add transport: " + e.getMessage();
			infoMessage = "";
		}
		return "redirect:/transport/transportList";
	}
	
	@RequestMapping(value = "/editingTransport/{id}", method = RequestMethod.GET)
	public String editTransport(@PathVariable(value = "id") Long id, ModelMap model) {
		
		Transport transport = transportService.getElementByID(id);
		model.addAttribute("transport", transport);
		return "editTransport";
	}
	
	@RequestMapping(value = "/editingTransport/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute TransportDTO transportDTO, ModelMap model) {
		
		Transport entity = transportService.getElementByID(transportDTO.getId());
		entity.setName(transportDTO.getName());
		try {
			transportService.updateElement(entity);
		} catch (NonUniqueException e) {
			errorMessage = "Cannot edit transport: " + e.getMessage();
		}
		return "redirect:/transport/transportList";
	}

}
