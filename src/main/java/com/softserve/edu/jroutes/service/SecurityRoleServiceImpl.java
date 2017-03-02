/*
 * SecurityRoleService
 * 
 * version 0.0.1
 * 
 * 31.03.2014
 * 
 * Service class for security role
 * 
 */
package com.softserve.edu.jroutes.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.exception.NonUniqueException;

/**
 * @author Yuriy
 */

@Transactional
@Service("securityRoleService")
public class SecurityRoleServiceImpl implements SecurityRoleService {
	
	@Autowired
	private ElementDAO<SecurityRole> securityRoleDAO;

	@Override
	public void addSecurityRole(SecurityRole securityRole) throws NonUniqueException {
		securityRoleDAO.addElement(securityRole);
	}

	@Override
	public void updateSecurityRole(SecurityRole securityRole) throws NonUniqueException {
		securityRoleDAO.updateElement(securityRole);
	}

	@Override
	public List<SecurityRole> getAllSecurityRoles() {
		return securityRoleDAO.getAllElements();
	}

	@Override
	public void deleteSecurityRole(SecurityRole securityRole) {
		securityRoleDAO.deleteElement(securityRole);
	}

	@Override
	public List<SecurityRole> getSecurityRolesByCriteria(Object... criteria) {
		return securityRoleDAO.getElementsByCriteria(criteria);
	}

	@Override
	public SecurityRole getSecurityRoleById(Long securityRoleId) {
		return securityRoleDAO.getElementByID(securityRoleId);
	}

	@Override
	public List<SecurityRole> getOtherSecurityRoles(
			Set<SecurityRole> someSecurityRoles) {
		List<SecurityRole> allRoles = securityRoleDAO.getAllElements();
		Iterator<SecurityRole> i = someSecurityRoles.iterator();
		while (i.hasNext()) {
			SecurityRole role = i.next();
			for (int j = 0; j < allRoles.size(); j++) {
				if (role.getName().equals(allRoles.get(j).getName())) {
					allRoles.remove(j);
				}
			}
		}
		
		return allRoles;
	}
}