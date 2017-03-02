package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.service.UserService;
import com.softserve.edu.jroutes.service.UserServiceImpl;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userservice;

	/**
	 * Custom UserDetailsService implementation
	 * 
	 * @param username
	 * @return org.springframework.security.core.userdetails.User Object
	 */

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		com.softserve.edu.jroutes.entity.User user = null;
		for (com.softserve.edu.jroutes.entity.User value : userservice
				.getAllUsers()) {
			if (value.getEmail().equals(username)) {
				user = value;
				break;
			}
		}
		if (user.equals(null)) {
			throw new UsernameNotFoundException("Username " + username
					+ " not found");
		}
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<SecurityRole> role = user.getRoles();
		Set<String> list_roles = new HashSet<String>();
		Iterator<SecurityRole> it = role.iterator();

		while (it.hasNext()) {
			list_roles.add(it.next().getName());
		}
		for (String temp : list_roles) {
			authorities.add(new SimpleGrantedAuthority(temp));
		}
		return new User(user.getEmail(), user.getPassword(), true, true,
				true, true, authorities);
	}
}
