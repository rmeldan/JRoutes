package com.softserve.edu.jroutes.service;

import java.util.List;
import com.softserve.edu.jroutes.component.SavedRouteAlgorithm;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Roman
 */

public interface SavedRouteService {
	void addSavedRoute(SavedRoute savedRoute) throws NonUniqueException;
	void updateSavedRoute(SavedRoute savedRoute) throws NonUniqueException;
	void deleteSavedRoute(SavedRoute savedRoute);
	List<SavedRoute> getAllSavedRoutes();
	SavedRoute getSavedRouteById(Long id);
	List<SavedRoute> getSavedRoutesByCriteria(Object... criteria);
	List<SavedRouteAlgorithm> getBuiltRoutes(RoutePoint startPoint,
			RoutePoint finishPoint, Transport[] trs, Long priceMax, Long timeMax);
}
