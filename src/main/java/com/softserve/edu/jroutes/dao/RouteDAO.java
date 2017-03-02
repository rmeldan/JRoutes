package com.softserve.edu.jroutes.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Petro
 */

@Repository("routeDAO")
public class RouteDAO extends ElementDAOImpl<Route> {
	public RouteDAO() {
		super(Route.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Route checkForUnique(Route element, Session session)
			throws NonUniqueException {
		RouteConnection routeConnectionId = element.getRouteConnectionId();
		SavedRoute savedRouteId = element.getSavedRouteId();

		List<Route> routeList = session.createCriteria(Route.class)
				.add(Restrictions.eq("routeConnectionId", routeConnectionId))
				.add(Restrictions.eq("savedRouteId", savedRouteId)).list();
		if (routeList.size() == 0)
			return element;
		// update case
		else {
			Route rt = routeList.get(0);
			if (rt.getSavedRouteId() == element.getSavedRouteId()
					&& rt.getRouteConnectionId() == element
							.getRouteConnectionId()) {

				rt.setSequenceNumber(element.getSequenceNumber());

				return rt;
			} else {
				throw new NonUniqueException("This Route already exists");
			}
		}
	}

    // getting elements by savedRouteId(first parameter) or
    // routeConnectionid(second parameter)
    @Override
    @SuppressWarnings("unchecked")
    public List<Route> getElementsByCriteria(Object... criteria) {
        Session session = null;
        List<Route> routeList = new ArrayList<Route>();
        session = sessionFactory.getCurrentSession();
        if (criteria[0] != null) {
            Criteria crit = session.createCriteria(Route.class).add(
                    Restrictions.eq("savedRouteId", criteria[0]));
            routeList = crit.list();
        }
        if (criteria[1] != null) {
            Criteria crit = session.createCriteria(Route.class).add(
                    Restrictions.eq("routeConnectionId", criteria[1]));
            routeList = crit.list();
        }

        return routeList;
    }
}
