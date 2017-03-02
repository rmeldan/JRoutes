/*
 * SecurityRoleDTO
 * 
 * version 0.0.1
 * 
 * 06.04.2014
 * 
 * Data transfer object for security role
 * 
 */
package com.softserve.edu.jroutes.dto;

/**
 * @author Yuriy
 */
public class SecurityRoleDTO {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}