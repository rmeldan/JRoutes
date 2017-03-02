package com.softserve.edu.jroutes.component;

import com.softserve.edu.jroutes.entity.RouteConnection;

public class RouteAlgorithm {
	private RouteConnection routeConnectionId;
	private byte sequenceNumber;
	
	public RouteConnection getRouteConnectionId() {
		return routeConnectionId;
	}
	public void setRouteConnectionId(RouteConnection routeConnectionId) {
		this.routeConnectionId = routeConnectionId;
	}
	public byte getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(byte sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
