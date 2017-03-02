package com.softserve.edu.jroutes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.softserve.edu.jroutes.component.SavedRouteAlgorithm;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;

/*
 * @author Maryan
 */

@Entity
@Table(name = "USER")
public class User {
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Boolean isBlocked = false;
	private Set<SecurityRole> roles;
	private List<RouteConnectPointDto> rCpDtoList = new ArrayList<RouteConnectPointDto>();
	private List<RouteConnectPointDto> rCpDtoListResult = new ArrayList<RouteConnectPointDto>();
	private SavedRoute savedRouteEdit;
	private List<SavedRouteAlgorithm> savedRoutesAlgorithm;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}

	@JoinTable(name = "USER_SECURITY_REF", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SECURITY_ROLE_ID") })
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<SecurityRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SecurityRole> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "EMAIL", nullable = false, length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FIRST_NAME", nullable = false, length = 45)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", nullable = false, length = 45)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "IS_BLOCKED", nullable = false)
	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Transient
	public List<RouteConnectPointDto> getrCpDtoList() {
		return rCpDtoList;
	}

	public void setrCpDtoList(List<RouteConnectPointDto> rCpDtoList) {
		this.rCpDtoList = rCpDtoList;
	}

	@Transient
	public List<RouteConnectPointDto> getrCpDtoListResult() {
		return rCpDtoListResult;
	}

	public void setrCpDtoListResult(List<RouteConnectPointDto> rCpDtoListResult) {
		this.rCpDtoListResult = rCpDtoListResult;
	}

	@Transient
	public SavedRoute getSavedRouteEdit() {
		return savedRouteEdit;
	}

	public void setSavedRouteEdit(SavedRoute savedRouteEdit) {
		this.savedRouteEdit = savedRouteEdit;
	}

	@Transient
	public List<SavedRouteAlgorithm> getSavedRoutesAlgorithm() {
		return savedRoutesAlgorithm;
	}

	public void setSavedRoutesAlgorithm(List<SavedRouteAlgorithm> savedRoutesAlgorithm) {
		this.savedRoutesAlgorithm = savedRoutesAlgorithm;
	}
}