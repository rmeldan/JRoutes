package com.softserve.edu.jroutes.dto;

public class RouteDTO {
	private RouteConnectionDTO routeConnectionId;
	private Long sequenceNumber;
	private SavedRouteDto savedRouteId;

	public RouteConnectionDTO getRouteConnectionId() {
		return routeConnectionId;
	}

	public void setRouteConnectionId(RouteConnectionDTO routeConnectionId) {
		this.routeConnectionId = routeConnectionId;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public SavedRouteDto getSavedRouteId() {
		return savedRouteId;
	}

	public void setSavedRouteId(SavedRouteDto savedRouteId) {
		this.savedRouteId = savedRouteId;
	}


}

