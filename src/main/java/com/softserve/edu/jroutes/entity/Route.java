package com.softserve.edu.jroutes.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Petro
 */

@Entity
@Table(name = "ROUTE")
public class Route implements Serializable, Comparable<Route> {
	private static final long serialVersionUID = -8027852530256172821L;
	private RouteConnection routeConnectionId;
	private Long sequenceNumber;
	private SavedRoute savedRouteId;

	@Id
	@ManyToOne
	@JoinColumn(name = "SAVED_ROUTE_ID", nullable = false)
	public SavedRoute getSavedRouteId() {
		return savedRouteId;
	}

	public void setSavedRouteId(SavedRoute savedRouteId) {
		this.savedRouteId = savedRouteId;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "ROUTE_CONNECTION_ID", nullable = false)
	public RouteConnection getRouteConnectionId() {
		return routeConnectionId;
	}

	public void setRouteConnectionId(RouteConnection routeConnectionId) {
		this.routeConnectionId = routeConnectionId;
	}

	@Column(name = "SEQUENCE_NUMBER", nullable = false)
	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	@Override
	public int compareTo(Route route) {
		return (int) (sequenceNumber - route.sequenceNumber);
	}
}