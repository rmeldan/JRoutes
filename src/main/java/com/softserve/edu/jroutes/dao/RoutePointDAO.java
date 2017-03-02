package com.softserve.edu.jroutes.dao;

import java.util.List;

import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Volodymyr
 */

@Repository("routePointDAO")
public class RoutePointDAO extends ElementDAOImpl<RoutePoint> {
	public RoutePointDAO() {
		super(RoutePoint.class);
	}

	/* check with name and country, because we have one city in two country */
	@Override
	@SuppressWarnings("unchecked")
	public RoutePoint checkForUnique(RoutePoint element, Session session)
			throws NonUniqueException {

		String routePointName = element.getName();
		Country routeCountryId = element.getCountry();

		List<RoutePoint> list = session.createCriteria(RoutePoint.class)
				.add(Restrictions.eq("name", routePointName))
				.add(Restrictions.eq("country", routeCountryId)).list();
		if (list.size() == 0)
			return element;
		else {
			RoutePoint tempRoutePoint = list.get(0);
			if ((element.getId() != null)
					&& tempRoutePoint.getName().equals(element.getName())
					) {
				return tempRoutePoint;
			} else {
				throw new NonUniqueException("This RoutePoint already exists");
			}
		}
	}

	@SuppressWarnings("unchecked")
	/*
	 * return list of RoutePoint check by name
	 */
	public List<RoutePoint> getElementsByCriteria(Object... object) {
		Session session = sessionFactory.getCurrentSession();
		String cityName;
		List<RoutePoint>  list;
		
		
		if(object.length == 1){
			 cityName = (String) object[0];
			 list = session.createCriteria(RoutePoint.class)
				.add(Restrictions.eq("name", cityName)).list();
		}else{
			cityName = (String) object[1];
			final String getCountry ="from RoutePoint where name like '"+ cityName+"%'";
			Query query = session.createQuery(getCountry);

			list = query.list();
			
		}
		return list;
	}
	
}