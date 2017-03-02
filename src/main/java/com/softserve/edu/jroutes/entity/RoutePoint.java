package com.softserve.edu.jroutes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Volodymyr
 */

@Entity
@Table(name = "ROUTE_POINT")
public class RoutePoint {
	private Long id;
	private String name;
	private Country countryId;
	private Boolean isBlocked = false;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	public Country getCountry() {
		return countryId;
	}

	public void setCountry(Country countryId) {
		this.countryId = countryId;
	}

	@Column(name = "IS_BLOCKED", nullable = false)
	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
