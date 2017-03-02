package com.softserve.edu.jroutes.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Roman
 */

@Entity
@Table(name = "SAVED_ROUTE")
public class SavedRoute {
	private Long id;
	private User userId;
	private String name;
	private Date modificationTime;
	private Boolean isCompanyRoute;
	private Set<Route> routes;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Column(name = "NAME", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "MODIFICATION_TIME", nullable = false)
	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Column(name = "IS_COMPANY_ROUTE", nullable = false)
	public Boolean getIsCompanyRoute() {
		return isCompanyRoute;
	}

	public void setIsCompanyRoute(Boolean isCompanyRoute) {
		this.isCompanyRoute = isCompanyRoute;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "savedRouteId", orphanRemoval = true, fetch = FetchType.LAZY)
	public Set<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}
}