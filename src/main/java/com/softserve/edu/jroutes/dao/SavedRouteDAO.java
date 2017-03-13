package com.softserve.edu.jroutes.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Roman
 */

@Repository("savedRouteDAO")
public class SavedRouteDAO extends ElementDAOImpl<SavedRoute> {	
	private static final Logger LOGGER = Logger.getLogger(SavedRouteDAO.class);

    public SavedRouteDAO() {
        super(SavedRoute.class);
    }

	@Override
	@SuppressWarnings("unchecked")
	public SavedRoute checkForUnique(SavedRoute element, Session session)
			throws NonUniqueException {
		String name = element.getName();
		User user = element.getUserId();
		List<SavedRoute> srList = session.createCriteria(SavedRoute.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("userId", user)).list();
		if (srList.size() == 0) {
			LOGGER.info("Saved route " + element.getName() + " is unique.");
			return element;
		} else {
			SavedRoute sr = srList.get(0);
			if (sr.getId() == element.getId()) {
				sr.setModificationTime(element.getModificationTime());
				sr.setUserId(element.getUserId());
				sr.setIsCompanyRoute(element.getIsCompanyRoute());
				sr.setRoutes(element.getRoutes());
				LOGGER.info("Saved route " + element.getName() + " is unique before update.");
				return sr;
			} else {
				LOGGER.info("Saved route " + element.getName() + " is not unique.");
				throw new NonUniqueException("«бережений маршрут з назвою \""
						+ element.getName() + "\" вже ≥снуЇ!");
			}
		}
	}

	/**
	 * This method takes the following parameters: 
	 * 1)User user Ц be returned saved routes only that user
	 * 2)String name Ц be returned saved routes with that name
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SavedRoute> getElementsByCriteria(Object... criteria) {
		Session session = null;
		List<SavedRoute> elements = new ArrayList<SavedRoute>();
		session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SavedRoute.class);
		if (criteria[0] != null) {
			User u = (User) criteria[0];
			crit.add(Restrictions.eq("userId", u));
		}
		if (criteria[1] != null) {
			String name = (String) criteria[1];
			crit.add(Restrictions.eq("name", name));
		}
		elements = crit.list();
		LOGGER.info("Size of saved routes list: " + elements.size());
		return elements;
	}
}