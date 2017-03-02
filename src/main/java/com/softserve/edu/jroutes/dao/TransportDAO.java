package com.softserve.edu.jroutes.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author ruslana
 */

@Repository("transportDAO")
public class TransportDAO extends ElementDAOImpl<Transport> {
	public TransportDAO() {
		super(Transport.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Transport checkForUnique(Transport element, Session session)
			throws NonUniqueException {
		String transName = element.getName();
		List<Transport> trnList = session.createCriteria(Transport.class)
				.add(Restrictions.eq("name", transName)).list();
		if (trnList.size() == 0) {
			return element;
		} else {
			Transport tr = trnList.get(0);
			if (tr.getId() == element.getId()) {
				return tr;
			} else {
				throw new NonUniqueException("This Transport already exists");
			}
		}
	}

	// first argument - transport name
	@Override
	@SuppressWarnings("unchecked")
	public List<Transport> getElementsByCriteria(Object... criteria) {
		List<Transport> transportList = new ArrayList<Transport>();
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Transport.class).add(
				Restrictions.eq("name", (String) criteria[0]));
		transportList = crit.list();
		return transportList;
	}
}