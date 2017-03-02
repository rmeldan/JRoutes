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

import com.softserve.edu.jroutes.dto.CountryDTO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.ElementService;

@Controller
@RequestMapping("/countries")
public class CountryController {
	@Autowired
	private ElementService<Country> countryService;
	
	@Autowired
	private CountryValidator countryValidator;

	public void setCountryValidation( CountryValidator countryValidator) {
		this.countryValidator = countryValidator;
	}
	
	@RequestMapping(value = "/countryList", method = RequestMethod.GET)
	public String countries(ModelMap model) {
		
		List<Country> countyList = countryService.getAllElements();
		model.addAttribute("country", new CountryDTO());
	 	model.addAttribute("countyList", countyList);
		model.addAttribute("actCountries", "active");
		return "countries";
	}	
	
	@RequestMapping(value = "/countryList",params = { "isValid","message"}, method = RequestMethod.GET)
	public String countries( 
			@RequestParam(value = "isValid") boolean isValid,
			@RequestParam(value = "message") String message,
			ModelMap model) {
		
		List<Country> countyList = countryService.getAllElements();
		model.addAttribute("country", new CountryDTO());
	 	model.addAttribute("countyList", countyList);
	 	model.addAttribute("isValid", isValid);
	 	model.addAttribute("message", message);
		model.addAttribute("actCountries", "active");
		return "countries";
	}	
	
	@RequestMapping(value = "/deletingCountry/{id}", method = RequestMethod.GET)
	public String deleteCountry(@PathVariable(value = "id") Long id, ModelMap model) {
		
		Country entity = countryService.getElementByID(id);
		String message="countries.delete.error" ;
		boolean isValid = false;
		try {
			countryService.deleteElement(entity);
		    isValid = true;
		    model.addAttribute("isValid", isValid);
			message ="countries.deleteSuccess";
		} catch (Exception e) {
			message = "countries.delete.error";
			model.addAttribute("isValid", isValid);
		}
		model.addAttribute("message", message);
		return "redirect:/countries/countryList";
	}	

	@RequestMapping(value = "/addingCountry", method = RequestMethod.POST)
	public String addCountry(@ModelAttribute(value="country") CountryDTO country,ModelMap model, BindingResult result) {
		
		List<Country> countyList = countryService.getAllElements();
		model.addAttribute("countyList", countyList);
		model.addAttribute("actCountries", "active");
		
		countryValidator.validate(country, result);
		if (result.hasErrors()) {
			return "redirect:/countries/countryList";
		} 
			
		
		Country entity = new Country();
		entity.setName(country.getName());
		String message = "countries.addSuccess";
		try {
			boolean isValid = true;
			model.addAttribute("isValid", isValid);
			model.addAttribute("message", message);
			countryService.addElement(entity);
		} catch (NonUniqueException e) {
			result.rejectValue("name", "error.name", e.getMessage());
		}
		return "redirect:/countries/countryList";
	}	
	
	@RequestMapping(value = "/editingCountry/{id}", method = RequestMethod.GET)
	public String editCountry(@PathVariable(value = "id") Long id, ModelMap model) {
		
		Country country = countryService.getElementByID(id);
		model.addAttribute("country", country);
		return "editCountry";
	}
	
	@RequestMapping(value = "/editingCountry/edit/{id}", method = RequestMethod.POST)
	public String edit(@PathVariable(value = "id") Long id,
			@ModelAttribute(value="country") CountryDTO country,
			ModelMap model, BindingResult result) {
		
		countryValidator.validate(country, result);
		if (result.hasErrors()) {
			return "redirect:/countries/editingCountry/{id}";
		} 
		
		Country entity = countryService.getElementByID(country.getId());
		entity.setName(country.getName());
		String message = "countries.editSuccess";
		try {
			boolean isValid = true;
			model.addAttribute("isValid", isValid);
			model.addAttribute("message", message);
			countryService.updateElement(entity);
		} catch (NonUniqueException e) {
			message = e.getMessage();
		}
		model.addAttribute("editingResult", message);
		return "redirect:/countries/countryList";
	}
	
	
}