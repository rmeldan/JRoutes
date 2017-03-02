package com.softserve.edu.jroutes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.dao.RoutePointDAO;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("routePointService")
public class RoutePointServiceImpl implements RoutePointService{

	@Autowired
	private ElementDAO<RoutePoint> routePointDAO;
	
	@Override
	public void addElement(RoutePoint element) throws NonUniqueException {
		routePointDAO.addElement(element);
	}

	@Override
	public void updateElement(RoutePoint element) throws NonUniqueException {
		routePointDAO.updateElement(element);
		
	}

	@Override
	public List<RoutePoint> getAllElements() {
		return routePointDAO.getAllElements();
	}

	@Override
	public void deleteElement(RoutePoint element) {
		routePointDAO.deleteElement(element);
		
	}

	@Override
	public List<RoutePoint> getElementsByCriteria(Object... criteria) {
		return routePointDAO.getElementsByCriteria(criteria);

	}

	@Override
	public RoutePoint getElementByID(Long elementId) {
		return routePointDAO.getElementByID(elementId);
	}
	

}
