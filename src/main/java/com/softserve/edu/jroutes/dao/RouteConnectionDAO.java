package com.softserve.edu.jroutes.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Taras
 */

@Repository("routeConnectionDAO")
public class RouteConnectionDAO extends ElementDAOImpl<RouteConnection> {
	public RouteConnectionDAO() {
		super(RouteConnection.class);
	}

	/**
	 * Method finds any similar elements in RouteConnection table with such
	 * parameters as: routPointAId, routPointAId and transportId return true if
	 * element is not in table yet return NonUniqueException if element is
	 * already in table
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RouteConnection checkForUnique(RouteConnection element,
			Session session) throws NonUniqueException {
		RoutePoint routePointAId = element.getRoutePointAId();
		RoutePoint routePointBId = element.getRoutePointBId();
		Transport transportId = element.getTransportId();
		Query query = session.createQuery("from RouteConnection where "
				+ "((routePointAId=:pointA "
				+ "and routePointBId=:pointB) or "
				+ "(routePointAId=:pointB " + "and routePointBId=:pointA))"
				+ "and  (transportId=:transport)");
		query.setParameter("pointA", routePointAId);
		query.setParameter("pointB", routePointBId);
		query.setParameter("transport", transportId);
		List<RouteConnection> list = query.list();
		if (list.size() == 0) {
			return element;
		} else {
			if (element.getId() == list.get(0).getId()) {
				list.get(0).setIsBlocked(element.getIsBlocked());
				list.get(0).setPrice(element.getPrice());
				list.get(0).setTime(element.getTime());
				return list.get(0);
			} else {
				throw new NonUniqueException(
						"This route connection already exists");
			}

		}

	}
	
	/**
	 * Method can takes one, two or three arguments: elements 
	 * are present in RouteConnection   
	 * 1) First argument: first RoutePoint
	 * 2) Second argument: last RoutePoint
	 * 3) Thread argument: this is an array that contains all transports that are allowed
	 * 4) Fourth argument: maximum price of route connection
	 * 5) Fifth argument: maximum time of route connection
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<RouteConnection> getElementsByCriteria(Object... criteria) {
		List<RouteConnection> elements = new ArrayList<RouteConnection>();
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				RouteConnection.class).add(Restrictions.eq("isBlocked", false));
		if (criteria[0] != null) {
			RoutePoint rpA = (RoutePoint) criteria[0];
			crit.add(Restrictions.or(Restrictions.eq("routePointAId", rpA), Restrictions.eq("routePointBId", rpA)));
		}
		if (criteria[1] != null) {
			RoutePoint rpB = (RoutePoint) criteria[1];
			crit.add(Restrictions.or(Restrictions.eq("routePointAId", rpB), Restrictions.eq("routePointBId", rpB)));
		}		
		if (criteria[2] != null) {
			Transport[] trs = (Transport[]) criteria[2];
			crit.add(Restrictions.in("transportId", trs));
		}
		if (criteria[3] != null) {
			Long priceMax = (Long) criteria[3];
			crit.add(Restrictions.le("price", priceMax));
		}
		if (criteria[4] != null) {
			Long timeMax = (Long) criteria[4];
			crit.add(Restrictions.le("time", timeMax));
		}
		elements = crit.list();
		return elements;
	}
}