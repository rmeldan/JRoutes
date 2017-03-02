package com.softserve.edu.jroutes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Taras
 */

@Entity
@Table(name = "route_connection")
public class RouteConnection {
	private Long id;
	private RoutePoint routePointAId;
	private RoutePoint routePointBId;
	private Transport transportId;
	private Long price;
	private Long time;
	private Boolean isBlocked = false;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "route_point_a_id", nullable = false)
	public RoutePoint getRoutePointAId() {
		return routePointAId;
	}

	public void setRoutePointAId(RoutePoint routePointAId) {
		this.routePointAId = routePointAId;
	}

	@OneToOne
	@JoinColumn(name = "route_point_b_id", nullable = false)
	public RoutePoint getRoutePointBId() {
		return routePointBId;
	}

	public void setRoutePointBId(RoutePoint routePointBId) {
		this.routePointBId = routePointBId;
	}

	@OneToOne
	@JoinColumn(name = "transport_id", nullable = false)
	public Transport getTransportId() {
		return transportId;
	}

	public void setTransportId(Transport transportId) {
		this.transportId = transportId;
	}

	@Column(name = "price", nullable = false)
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Column(name = "time", nullable = false)
	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Column(name = "is_blocked", nullable = false)
	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}