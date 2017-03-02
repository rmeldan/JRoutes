package com.softserve.edu.jroutes.dto;

public class RouteConnectionDTO {
	private Long id;
	private Long routePointA;
	private Long routePointB;
	private String routePointAName;
	private String routePointBName;
	private String transportName;
	private Long countryA;
	private Long countryB;
	private Long transport;
	private String price;
	private String time;
	private Boolean isBlocked=false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoutePointA() {
		return routePointA;
	}
	public void setRoutePointA(Long routePointA) {
		this.routePointA = routePointA;
	}
	public Long getRoutePointB() {
		return routePointB;
	}
	public void setRoutePointB(Long routePointB) {
		this.routePointB = routePointB;
	}
	
	public String getRoutePointAName() {
		return routePointAName;
	}
	public void setRoutePointAName(String routePointAName) {
		this.routePointAName = routePointAName;
	}
	public String getRoutePointBName() {
		return routePointBName;
	}
	public void setRoutePointBName(String routePointBName) {
		this.routePointBName = routePointBName;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	public Long getCountryA() {
		return countryA;
	}
	public void setCountryA(Long countryA) {
		this.countryA = countryA;
	}
	public Long getCountryB() {
		return countryB;
	}
	public void setCountryB(Long countryB) {
		this.countryB = countryB;
	}
	public Long getTransport() {
		return transport;
	}
	public void setTransport(Long transport) {
		this.transport = transport;
	}	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
	
}