package com.softserve.edu.jroutes.dto;

import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.Transport;

/**
 * @author Roman
 */

public class RouteConnectPointDto implements Comparable<RouteConnectPointDto> {
	private Long id;
	private Transport transportId;
	private RoutePoint routePointAId;
	private RoutePoint routePointBId;
	private RouteConnection routeConnectionId;
	private SavedRoute savedRouteId;
	private Long price;
	private String time;
	private String name;
	private boolean fromRoute;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Transport getTransportId() {
		return transportId;
	}

	public void setTransportId(Transport transportId) {
		this.transportId = transportId;
	}

	public RoutePoint getRoutePointAId() {
		return routePointAId;
	}

	public void setRoutePointAId(RoutePoint routePointAId) {
		this.routePointAId = routePointAId;
	}

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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    public SavedRoute getSavedRouteId() {
        return savedRouteId;
    }

    public void setSavedRouteId(SavedRoute savedRouteId) {
        this.savedRouteId = savedRouteId;
    }

    @Override
    public int compareTo(RouteConnectPointDto arg0) {
        Long comparedSize = arg0.id;
        if (this.id > comparedSize) {
            return 1;
        } else if (this.id == comparedSize) {
            return 0;
        } else {
            return -1;
        }
    }

    public boolean isFromRoute() {
        return fromRoute;
    }

    public void setFromRoute(boolean isFromRoute) {
        this.fromRoute = isFromRoute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }	
}
