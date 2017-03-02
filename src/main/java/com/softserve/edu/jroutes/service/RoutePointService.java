package com.softserve.edu.jroutes.service;

import java.util.List;

import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;



public interface RoutePointService{
	
	public void addElement(RoutePoint element) throws NonUniqueException;
	public void updateElement(RoutePoint element) throws NonUniqueException;
	public List<RoutePoint> getAllElements();	
	public void deleteElement(RoutePoint element);
	public List<RoutePoint> getElementsByCriteria(Object... criteria);
	public RoutePoint getElementByID(Long elementId);


}