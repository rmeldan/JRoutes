package com.softserve.edu.jroutes.service;

import java.util.List;
import java.util.Set;

import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.exception.NonUniqueException;



public interface SecurityRoleService {
	void addSecurityRole(SecurityRole securityRole) throws NonUniqueException;
	void updateSecurityRole(SecurityRole securityRole) throws NonUniqueException;
	void deleteSecurityRole(SecurityRole securityRole);
	List<SecurityRole> getAllSecurityRoles();
	List<SecurityRole> getOtherSecurityRoles(Set<SecurityRole> someSecurityRoles);
	SecurityRole getSecurityRoleById(Long id);
	public List<SecurityRole> getSecurityRolesByCriteria(Object... criteria);
}
