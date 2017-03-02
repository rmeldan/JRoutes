package com.softserve.edu.jroutes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softserve.edu.jroutes.component.RouteAlgorithm;
import com.softserve.edu.jroutes.component.RouteConnectionAlgorithm;
import com.softserve.edu.jroutes.component.SavedRouteAlgorithm;
import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Roman
 */

@Transactional
@Service("savedRouteService")
public class SavedRouteServiceImpl implements SavedRouteService {
	
	@Autowired
	private ElementDAO<SavedRoute> savedRouteDAO;
	@Autowired
	private ElementDAO<RouteConnection> routeConnectionDAO;
	private List<RouteConnection> routeConnections;

	@Override
	public void addSavedRoute(SavedRoute savedRoute) throws NonUniqueException {
		savedRouteDAO.addElement(savedRoute);
	}

	@Override
	public void updateSavedRoute(SavedRoute savedRoute)
			throws NonUniqueException {
		savedRouteDAO.updateElement(savedRoute);
	}

	@Override
	public void deleteSavedRoute(SavedRoute savedRoute) {
		savedRouteDAO.deleteElement(savedRoute);
	}

	@Override
	public List<SavedRoute> getAllSavedRoutes() {
		return savedRouteDAO.getAllElements();
	}

	@Override
	public SavedRoute getSavedRouteById(Long id) {
		return savedRouteDAO.getElementByID(id);
	}

	@Override
	public List<SavedRoute> getSavedRoutesByCriteria(Object... criteria) {
		return savedRouteDAO.getElementsByCriteria(criteria);
	}
		
	@Override
	public List<SavedRouteAlgorithm> getBuiltRoutes(RoutePoint startPoint, RoutePoint finishPoint,
			Transport[] trs, Long priceMax, Long timeMax) {
		if(priceMax == 0) {
			priceMax = null;
		}
		if(timeMax == 0) {
			timeMax = null;
		}			
		routeConnections = routeConnectionDAO.getElementsByCriteria(null, null, trs, null, null);
		RouteConnectionAlgorithm startConnect = new RouteConnectionAlgorithm();
		startConnect.setRoutePointBId(startPoint);
		startConnect.setRouteConnectionsAlgorithm(getTreeRouteConnections(startConnect, finishPoint.getId(), 
				priceMax, timeMax, new ArrayList<Long>()));
		List<SavedRouteAlgorithm> savedRoutesAlgorithm = getRoutes(startConnect, new ArrayList<RouteAlgorithm>(), (byte) 0);
		Collections.sort(savedRoutesAlgorithm);		
		for (int i = 1000; i < savedRoutesAlgorithm.size();) {
			savedRoutesAlgorithm.remove(i);
		}
		return savedRoutesAlgorithm;
	}
	
	private List<RouteConnectionAlgorithm> getTreeRouteConnections(RouteConnectionAlgorithm rcAlg, Long finishPointId, 
			Long priceMax, Long timeMax, List<Long> usedPoint) {
		List<RouteConnectionAlgorithm> rCsDtoList = new ArrayList<RouteConnectionAlgorithm>();
		RoutePoint routePoint = rcAlg.getRoutePointBId();
		usedPoint.add(routePoint.getId()); 
		List<RouteConnection> routeConnections = getRouteConnectionsByCriteria(routePoint, priceMax, timeMax);
		l: for (int i = 0; i < routeConnections.size(); i++) {
			RouteConnectionAlgorithm rcAlgorithm = new RouteConnectionAlgorithm();
			rcAlgorithm.setRouteConnectionId(routeConnections.get(i));	
			if (routeConnections.get(i).getRoutePointAId().getId() != routePoint.getId()) {
				rcAlgorithm.setRoutePointBId(routeConnections.get(i).getRoutePointAId());	
			} else {
				rcAlgorithm.setRoutePointBId(routeConnections.get(i).getRoutePointBId());
			}
			long idP = rcAlgorithm.getRoutePointBId().getId();
			for (Long id : usedPoint) {
				if (id == idP)
					continue l;
			}
			if(priceMax != null) {
				priceMax -= routeConnections.get(i).getPrice();
			}
			if(timeMax != null) {
				timeMax -= routeConnections.get(i).getTime();
			}
			if(finishPointId != idP) { 				
				List<RouteConnectionAlgorithm> rCpDtoListIn = getTreeRouteConnections(rcAlgorithm, finishPointId,
						priceMax, timeMax, usedPoint);
				rcAlgorithm.setRouteConnectionsAlgorithm(rCpDtoListIn);
				if (rCpDtoListIn.size() != 0) {
					rCsDtoList.add(rcAlgorithm);
				}
			} else {
				rCsDtoList.add(rcAlgorithm); 
			}
			if(priceMax != null) {
				priceMax += routeConnections.get(i).getPrice();
			}
			if(timeMax != null) {
				timeMax += routeConnections.get(i).getTime();
			}
		}
		usedPoint.remove(routePoint.getId());	
		return rCsDtoList;
	}
	
	private List<SavedRouteAlgorithm> getRoutes(RouteConnectionAlgorithm possibleRoutes, List<RouteAlgorithm> rList, byte i) {		
		List<SavedRouteAlgorithm> srList = new LinkedList<SavedRouteAlgorithm>();
		List<RouteAlgorithm> rListn = new ArrayList<RouteAlgorithm>(rList);
		if (possibleRoutes.getRouteConnectionsAlgorithm() != null) {			
			++i;
			for (RouteConnectionAlgorithm rcAlg : possibleRoutes.getRouteConnectionsAlgorithm()) {
				RouteAlgorithm routeAlgorithm = new RouteAlgorithm();
				routeAlgorithm.setRouteConnectionId(rcAlg.getRouteConnectionId());
				routeAlgorithm.setSequenceNumber(i);
				rListn.add(routeAlgorithm);
				srList.addAll(getRoutes(rcAlg, rListn, i));
				rListn.remove(routeAlgorithm);
			}
			--i;
		} else {
			SavedRouteAlgorithm sr = new SavedRouteAlgorithm();
			sr.setRoutesAlgorithm(rListn);
			srList.add(sr);
		}
		return srList;
	}
	
	private List<RouteConnection> getRouteConnectionsByCriteria(RoutePoint startPoint, Long priceMax, Long timeMax) {
		Long startPointId = startPoint.getId();
		List<RouteConnection> rcCriteria = new ArrayList<RouteConnection>();
		if(priceMax == null && timeMax == null) {
			for(RouteConnection routeConnection : routeConnections) {
				if (routeConnection.getRoutePointAId().getId() == startPointId
						|| routeConnection.getRoutePointBId().getId() == startPointId) {
					rcCriteria.add(routeConnection);
				}
			}	
		
		} else if(priceMax == null) {
			for(RouteConnection routeConnection : routeConnections) {
				if ((routeConnection.getRoutePointAId().getId() == startPointId
						|| routeConnection.getRoutePointBId().getId() == startPointId)
						&& routeConnection.getTime() <= timeMax) {
					rcCriteria.add(routeConnection);
				}
			}
		} else if(timeMax == null) {
			for(RouteConnection routeConnection : routeConnections) {
				if ((routeConnection.getRoutePointAId().getId() == startPointId
						|| routeConnection.getRoutePointBId().getId() == startPointId)
						&& routeConnection.getPrice() <= priceMax) {
					rcCriteria.add(routeConnection);
				}
			}
		} else {
			for(RouteConnection routeConnection : routeConnections) {
				if ((routeConnection.getRoutePointAId().getId() == startPointId
						|| routeConnection.getRoutePointBId().getId() == startPointId)
						&& routeConnection.getTime() <= timeMax && routeConnection.getPrice() <= priceMax) {
					rcCriteria.add(routeConnection);
				}
			}
		}
		return rcCriteria;
	}
}