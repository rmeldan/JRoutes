package com.softserve.edu.jroutes.component;

import java.util.List;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;

/**
 * @author Roman
 */

public class RouteConnectionAlgorithm {
	private RoutePoint routePointBId;
	private RouteConnection routeConnectionId;
	private List<RouteConnectionAlgorithm> routeConnectionsAlgorithm;
	
	public RoutePoint getRoutePointBId() {
		return routePointBId;
	}
	public void setRoutePointBId(RoutePoint routePointBId) {
		this.routePointBId = routePointBId;
	}
	public RouteConnection getRouteConnectionId() {
		return routeConnectionId;
	}
	public void setRouteConnectionId(RouteConnection routeConnectionId) {
		this.routeConnectionId = routeConnectionId;
	}
	public List<RouteConnectionAlgorithm> getRouteConnectionsAlgorithm() {
		return routeConnectionsAlgorithm;
	}
	public void setRouteConnectionsAlgorithm(List<RouteConnectionAlgorithm> routeConnectionsAlgorithm) {
		this.routeConnectionsAlgorithm = routeConnectionsAlgorithm;
	}
}
