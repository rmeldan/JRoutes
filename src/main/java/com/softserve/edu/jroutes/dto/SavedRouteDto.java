package com.softserve.edu.jroutes.dto;

import java.sql.Date;
import com.softserve.edu.jroutes.entity.RoutePoint;

/**
 * @author Roman
 */

public class SavedRouteDto  implements Comparable<SavedRouteDto> {
	private Long id;
	private String name;
	private Date modificationTime;
	private Boolean isCompanyRoute = false;
	private String time;
	private Long price;
	private RoutePoint startPoint;
	private RoutePoint finishPoint;

	public RoutePoint getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(RoutePoint startPoint) {
		this.startPoint = startPoint;
	}

	public RoutePoint getFinishPoint() {
		return finishPoint;
	}

	public void setFinishPoint(RoutePoint finishPoint) {
		this.finishPoint = finishPoint;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsCompanyRoute() {
		return isCompanyRoute;
	}

	public void setIsCompanyRoute(Boolean isCompanyRoute) {
		this.isCompanyRoute = isCompanyRoute;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	@Override
	public int compareTo(SavedRouteDto savedRouteDto) {
		return (int) (id - savedRouteDto.id);
	}
}