package com.softserve.edu.jroutes.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.dto.SavedRouteDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.SavedRouteService;

/**
 * @author Roman
 */

@Component
public class SavedRouteComponent {
	@Autowired
	private SavedRouteService savedRouteService;
	@Autowired
	private RouteConnectionService routeConnectionService;
	@Autowired
	private RoutePointService routePointService;
	@Autowired
	private RegUserInterface registeredUserObject;
	private static final Logger LOGGER = Logger.getLogger(SavedRouteComponent.class);
	
	public void fillPageUser(ModelMap model) {
		model.addAttribute("actSavedRoutes", "active");
		User user = registeredUserObject.getRegisteredUserObject();
		List<SavedRoute> savedRoutes = savedRouteService.getSavedRoutesByCriteria(user, null);
		TreeSet<SavedRouteDto> sRDtoList = new TreeSet<SavedRouteDto>();
		for (SavedRoute savedRoute : savedRoutes) {
			TreeSet<Route> routes = new TreeSet<Route>(savedRoute.getRoutes());
			Long price = 0L;
			Long time = 0L;
			List<RouteConnection> rcList = new ArrayList<RouteConnection>();
			for (Route route : routes) {
				rcList.add(route.getRouteConnectionId());
				price += route.getRouteConnectionId().getPrice();
				time += route.getRouteConnectionId().getTime();
			}			
			RoutePoint startPoint;
			RoutePoint finishPoint;
			if (rcList.size() != 1) {		
				startPoint = rcList.get(0).getRoutePointAId();
				if (startPoint.getId() == rcList.get(1).getRoutePointAId().getId()
						|| startPoint.getId() == rcList.get(1).getRoutePointBId().getId()) {
					startPoint = rcList.get(0).getRoutePointBId();
				}
				finishPoint = rcList.get(rcList.size() - 1)
						.getRoutePointAId();
				if (finishPoint.getId() == rcList.get(rcList.size() - 2).getRoutePointAId().getId()
						|| finishPoint.getId() == rcList.get(rcList.size() - 2)
								.getRoutePointBId().getId()) {
					finishPoint = rcList.get(rcList.size() - 1).getRoutePointBId();
				}
			} else {
				startPoint = rcList.get(0).getRoutePointAId();
				finishPoint = rcList.get(0).getRoutePointBId();
			}
			SavedRouteDto sRDto = new SavedRouteDto();
			sRDto.setId(savedRoute.getId());
			sRDto.setModificationTime(savedRoute.getModificationTime());
			sRDto.setName(savedRoute.getName());
			sRDto.setPrice(price);
			sRDto.setTime(time/60 + ":" +  String.format("%02d", time%60));
			sRDto.setStartPoint(startPoint);
			sRDto.setFinishPoint(finishPoint);
			sRDtoList.add(sRDto);			
		}
		model.addAttribute("sRDtoList", sRDtoList);
		LOGGER.info("Size of saved routes list: " + sRDtoList.size());
	}
	
	public void fillPageAdmin(ModelMap model) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		List<RouteConnectPointDto> rCpDtoList = user.getrCpDtoList();
		Set<Country> countries = new HashSet<Country>();
		for(RouteConnectPointDto rCpDto : rCpDtoList) {
			countries.add(rCpDto.getRoutePointBId().getCountry());
		}
		model.addAttribute("countries", countries);
		model.addAttribute("actRoutes", "active");
		model.addAttribute("rCpDtoList", rCpDtoList);
		model.addAttribute("rCpDtoListResult", rCpDtoListResult);
		List<SavedRoute> savedRoutes = savedRouteService.getSavedRoutesByCriteria(user, null);
		TreeSet<SavedRouteDto> sRDtoList = new TreeSet<SavedRouteDto>();
		for (SavedRoute savedRoute : savedRoutes) {
			TreeSet<Route> routes = new TreeSet<Route>(savedRoute.getRoutes());
			Long price = 0L;
			Long time = 0L;
			List<RouteConnection> rcList = new ArrayList<RouteConnection>();
			for (Route route : routes) {
				rcList.add(route.getRouteConnectionId());
				price += route.getRouteConnectionId().getPrice();
				time += route.getRouteConnectionId().getTime();
			}			
			RoutePoint startPoint;
			RoutePoint finishPoint;
			if (rcList.size() != 1) {		
				startPoint = rcList.get(0).getRoutePointAId();
				if (startPoint.getId() == rcList.get(1).getRoutePointAId().getId()
						|| startPoint.getId() == rcList.get(1).getRoutePointBId().getId()) {
					startPoint = rcList.get(0).getRoutePointBId();
				}
				finishPoint = rcList.get(rcList.size() - 1)
						.getRoutePointAId();
				if (finishPoint.getId() == rcList.get(rcList.size() - 2).getRoutePointAId().getId()
						|| finishPoint.getId() == rcList.get(rcList.size() - 2)
								.getRoutePointBId().getId()) {
					finishPoint = rcList.get(rcList.size() - 1).getRoutePointBId();
				}
			} else {
				startPoint = rcList.get(0).getRoutePointAId();
				finishPoint = rcList.get(0).getRoutePointBId();
			}
			SavedRouteDto sRDto = new SavedRouteDto();
			sRDto.setId(savedRoute.getId());
			sRDto.setIsCompanyRoute(savedRoute.getIsCompanyRoute());
			sRDto.setModificationTime(savedRoute.getModificationTime());
			sRDto.setName(savedRoute.getName());
			sRDto.setPrice(price);
			sRDto.setTime(time/60 + ":" +  String.format("%02d", time%60));
			sRDto.setStartPoint(startPoint);
			sRDto.setFinishPoint(finishPoint);
			sRDtoList.add(sRDto);			
		}
		model.addAttribute("sRDtoList", sRDtoList);
		LOGGER.info("Size of saved routes list: " + sRDtoList.size());
	}
	
	public void fillPossiblePoints(RouteConnectPointDto rCpDto) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		List<RouteConnectPointDto> rCpDtoList = user.getrCpDtoList();
		rCpDtoList.clear();
		RoutePoint routePoint = rCpDto.getRoutePointBId();
		List<RouteConnection> routeConnections = routeConnectionService
				.getElementsByCriteria(routePoint, null, null, null, null);
		l: for (int i = 0; i < routeConnections.size(); i++) {
			RouteConnectPointDto rcDtoNext = new RouteConnectPointDto();
			rcDtoNext.setId((long) i);
			rcDtoNext.setRouteConnectionId(routeConnections.get(i));
			rcDtoNext.setTransportId(routeConnections.get(i).getTransportId());
			rcDtoNext.setPrice(routeConnections.get(i).getPrice());
			rcDtoNext.setTime(routeConnections.get(i).getTime()/60 + ":"
					+ String.format("%02d", routeConnections.get(i).getTime()%60));
			if (routeConnections.get(i).getRoutePointAId().getId() != routePoint.getId()) {
				for (RouteConnectPointDto rCpDtoResult : rCpDtoListResult) {
					if (rCpDtoResult.getRoutePointBId().getId() == routeConnections
							.get(i).getRoutePointAId().getId()) {
						continue l;
					}
				}
				rcDtoNext.setRoutePointAId(routeConnections.get(i).getRoutePointBId());
				rcDtoNext.setRoutePointBId(routeConnections.get(i).getRoutePointAId());
			} else {
				for (RouteConnectPointDto rCpDtoResult : rCpDtoListResult) {
					if (rCpDtoResult.getRoutePointBId().getId() == routeConnections
							.get(i).getRoutePointBId().getId()) {
						continue l;
					}
				}
				rcDtoNext.setRoutePointAId(routeConnections.get(i).getRoutePointAId());
				rcDtoNext.setRoutePointBId(routeConnections.get(i).getRoutePointBId());
			}
			rCpDtoList.add(rcDtoNext);
		}
		LOGGER.info("Size possible points list: " + rCpDtoList.size());
	}
	
	public void fillRoutes(SavedRoute savedRoute, Long rpId) {
		User user = registeredUserObject.getRegisteredUserObject();
		List<RouteConnectPointDto> rCpDtoListResult = user.getrCpDtoListResult();
		RoutePoint startPoint = routePointService.getElementByID(rpId);
		TreeSet<Route> routes = new TreeSet<Route>(savedRoute.getRoutes());
		for (Route route : routes) {
			RouteConnectPointDto rCpDto = new RouteConnectPointDto();
			rCpDto.setId(route.getSequenceNumber());
			rCpDto.setRouteConnectionId(route.getRouteConnectionId());
			rCpDto.setTransportId(route.getRouteConnectionId().getTransportId());
			rCpDto.setPrice(route.getRouteConnectionId().getPrice());
			rCpDto.setTime(route.getRouteConnectionId().getTime()/60 + ":"
					+ String.format("%02d", route.getRouteConnectionId().getTime()%60));
			if (route.getRouteConnectionId().getRoutePointAId().getId() != startPoint.getId()) {
				rCpDto.setRoutePointAId(route.getRouteConnectionId().getRoutePointBId());
				rCpDto.setRoutePointBId(route.getRouteConnectionId().getRoutePointAId());
				startPoint = route.getRouteConnectionId().getRoutePointAId();
			} else {
				rCpDto.setRoutePointAId(route.getRouteConnectionId().getRoutePointAId());
				rCpDto.setRoutePointBId(route.getRouteConnectionId().getRoutePointBId());
				startPoint = route.getRouteConnectionId().getRoutePointBId();
			}
			rCpDtoListResult.add(rCpDto);
		}
		LOGGER.info("Saved route " + savedRoute.getName() + " contain: "
				+ rCpDtoListResult.size() + " routes connection.");
	}	
}