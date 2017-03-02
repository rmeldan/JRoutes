package com.softserve.edu.jroutes.service;

import java.util.List;

import com.softserve.edu.jroutes.dto.RouteConnectionDTO;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;

public interface RouteConnectionService {
	void addElement(RouteConnection routeConection) throws NonUniqueException;
	void updateElement(RouteConnection element) throws NonUniqueException;
	void deleteElement(RouteConnection element);
	void deleteElementById(Long id);
	List<RouteConnection> getAllElements();
	List<RouteConnectionDTO> getAllDtoElements();
	RouteConnection getElementByID(Long id);
	public List<RouteConnection> getElementsByCriteria(Object... criteria);
	public List<RoutePoint> getCitiesByCountryId(Long id);
	public Long convertDateToLong(String date);
	public String convertDateToString(long date);
}
