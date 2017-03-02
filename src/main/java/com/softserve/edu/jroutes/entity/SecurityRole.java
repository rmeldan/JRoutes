/*
 * SecurityRole
 * 
 * version 0.0.1
 * 
 * 29.03.2014
 * 
 * Entity class for security role
 * 
 */

package com.softserve.edu.jroutes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Yuriy
 */

@Entity
@Table(name = "SECURITY_ROLE")
public class SecurityRole {
	private Long id;
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}