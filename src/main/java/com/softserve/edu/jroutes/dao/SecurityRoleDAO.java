/*
 * SecurityRoleDAO
 * 
 * version 0.0.1
 * 
 * 31.03.2014
 * 
 * DAO class for security role
 * 
 */

package com.softserve.edu.jroutes.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Yuriy
 */

@Repository("securityRoleDAO")
public class SecurityRoleDAO extends ElementDAOImpl<SecurityRole> {
	public SecurityRoleDAO() {
		super(SecurityRole.class);
	}

	/**
	 * Check SecurityRole element for uniqueness in database
	 * @param element - object to check
	 * @param session - database session
	 * @return element if it is unique
	 */
	@Override
	@SuppressWarnings("unchecked")
	public SecurityRole checkForUnique(SecurityRole element, Session session)
			throws NonUniqueException {
		String securityRoleName = element.getName();
		List<SecurityRole> srList = session.createCriteria(SecurityRole.class)
				.add(Restrictions.eq("name", securityRoleName)).list();
		if (srList.size() == 0) {
			return element;
		} else {
			SecurityRole securityRole = srList.get(0);
			if ((element.getId() != null)
					&& (securityRole.getId() == element.getId())) {
				return securityRole;
			} else {
				throw new NonUniqueException("this security role already exist.");
			}
		}
	}

	/**
	 * Get all security roles from database by security role name
	 * @param criteria - in this case it is String - security role name
	 * @return list of security roles with particular name
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SecurityRole> getElementsByCriteria(Object... criteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(SecurityRole.class).add(
				Restrictions.eq("name", (String) criteria[0]));
		List<SecurityRole> securityRoles = crit.list();
		return securityRoles;
	}
}