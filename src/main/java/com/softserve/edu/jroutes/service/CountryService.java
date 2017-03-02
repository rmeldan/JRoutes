package com.softserve.edu.jroutes.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("countryService")
public class CountryService implements ElementService<Country> {
	@Autowired
    @Qualifier("countryDAO")
	private ElementDAO<Country> countryDAO;

	@Override
	public void addElement(Country element) throws NonUniqueException {
		countryDAO.addElement(element);
	}

	@Override
	public void updateElement(Country element) throws NonUniqueException {
		countryDAO.updateElement(element);
	}

	@Override
	public List<Country> getAllElements() {
		
		List<Country> countyList = countryDAO.getAllElements();	
		
		/*Countries sort by name*/
		Comparator<Country> comparator= new Comparator<Country>(){
			public int compare(Country country, Country another){
	            return country.getName().compareToIgnoreCase(another.getName());
	        } 
		};
		Collections.sort(countyList, comparator);
		
		
		return countyList;
	}

	@Override
	public void deleteElement(Country element) {
		countryDAO.deleteElement(element);
	}

	@Override
	public List<Country> getElementsByCriteria(Object... criteria) {
		return countryDAO.getElementsByCriteria(criteria);
	}

	@Override
	public Country getElementByID(Long elementId) {
		return countryDAO.getElementByID(elementId);
	}
}