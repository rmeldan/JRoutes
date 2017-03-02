package com.softserve.edu.jroutes.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.dto.RouteConnectionDTO;
import com.softserve.edu.jroutes.dto.RoutePointDTO;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("routeConnectionService")
public class RouteConnectionServiceImpl implements RouteConnectionService {
	@Autowired
	private ElementDAO<RouteConnection> routeConnectionDAO;
	
	@Autowired
	private ElementDAO<RoutePoint> routePointDAO;
	
	@Override
	public void addElement(RouteConnection element) throws NonUniqueException {
		routeConnectionDAO.addElement(element);
	}

	@Override
	public void updateElement(RouteConnection element)
			throws NonUniqueException {
		routeConnectionDAO.updateElement(element);
	}

	@Override
	public List<RouteConnection> getAllElements() {
		return routeConnectionDAO.getAllElements();
	}

	@Override
	public List<RouteConnectionDTO> getAllDtoElements() {

		List<RouteConnection> list = routeConnectionDAO.getAllElements();
		List<RouteConnectionDTO> listDTO = new ArrayList<RouteConnectionDTO>();
		for (RouteConnection routeConnection : list) {
			RouteConnectionDTO dto = new RouteConnectionDTO();
			dto.setId(routeConnection.getId());
			dto.setPrice(""+routeConnection.getPrice());
			dto.setTime(""+convertDateToString(routeConnection.getTime()));
			dto.setIsBlocked(routeConnection.getIsBlocked());
			dto.setTransport(routeConnection.getTransportId().getId());
			dto.setRoutePointA(routeConnection.getRoutePointAId().getId());
			dto.setRoutePointB(routeConnection.getRoutePointBId().getId());
			dto.setCountryA(routeConnection.getRoutePointAId().getCountry().getId());
			dto.setCountryB(routeConnection.getRoutePointBId().getCountry().getId());
			dto.setRoutePointAName(routeConnection.getRoutePointAId().getName());
			dto.setRoutePointBName(routeConnection.getRoutePointBId().getName());
			dto.setTransportName(routeConnection.getTransportId().getName());
			listDTO.add(dto);
		}
		return listDTO;
	}

	@Override
	public void deleteElement(RouteConnection element) {
		routeConnectionDAO.deleteElement(element);
	}

	@Override
	public void deleteElementById(Long id) {
		RouteConnection routeConnection = routeConnectionDAO.getElementByID(id);
		routeConnectionDAO.deleteElement(routeConnection);
	}

	@Override
	public List<RouteConnection> getElementsByCriteria(Object... criteria) {
		return routeConnectionDAO.getElementsByCriteria(criteria);
	}

	@Override
	public RouteConnection getElementByID(Long id) {
		return routeConnectionDAO.getElementByID(id);
	}

	@Override
	public List<RoutePoint> getCitiesByCountryId(Long id) {
		List<RoutePoint> routePointList = routePointDAO.getAllElements();
		List<RoutePoint> routePoints=new ArrayList<RoutePoint>();
		for(RoutePoint point: routePointList) {
			if(point.getCountry().getId()==id) {
				routePoints.add(point);
			}
		}
		return routePoints;
	}

	@Override
	public Long convertDateToLong(String date) {
		String[] array = date.split(" ");
		List list=Arrays.asList(array);
		Collections.reverse(list);
		array=(String[]) list.toArray();
		
		long minutes = 0;
		int multiple=1;
		for (int i = array.length-1; i >= 0; i--) {
			String tmp = array[i];
			String stringNumber = tmp.substring(0, tmp.length() - 1);
			if(i==2) {
				multiple=1414;
			} else if(i==1) {
				multiple=60;
			} else {
				multiple=1;
			}
			minutes += Integer.parseInt(stringNumber) * multiple;
		}
		return minutes;
	}

	@Override
	public  String convertDateToString(long value) {
		String date = "";
		long days = value / 1440;
		long hours = (value % 1440) / 60;
		long minutes = value - days * 1440 - hours * 60;
		if (days != 0) {
			date += days + "d ";
		}
		if(hours!=0 || days!=0){
			date+=hours+"h ";
		}
		return date + minutes + "m";
	}

}
