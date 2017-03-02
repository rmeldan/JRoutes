package com.softserve.edu.jroutes.dto;

public class RoutePointSearchDTO {
	private Long id;
	private String name;
	private String country;
	private Boolean isBlocked = false;

	public RoutePointSearchDTO(Long id, String name, String country,
			Boolean isBlocked) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		return "RoutePointSearchDTO [id=" + id + ", name=" + name
				+ ", country=" + country + ", isBlocked=" + isBlocked + "]";
	}

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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
