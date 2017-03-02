package com.softserve.edu.jroutes.component;

import java.util.List;

public class SavedRouteAlgorithm implements Comparable<SavedRouteAlgorithm> {
	private List<RouteAlgorithm> routesAlgorithm;

	public List<RouteAlgorithm> getRoutesAlgorithm() {
		return routesAlgorithm;
	}

	public void setRoutesAlgorithm(List<RouteAlgorithm> routesAlgorithm) {
		this.routesAlgorithm = routesAlgorithm;
	}

	@Override
	public int compareTo(SavedRouteAlgorithm o) {
		int time1 = 0;
		for(RouteAlgorithm routeAlgorithm : routesAlgorithm) {
			time1 += routeAlgorithm.getRouteConnectionId().getTime();
		}
		int time2 = 0;
		for(RouteAlgorithm routeAlgorithm : o.getRoutesAlgorithm()) {
			time2 += routeAlgorithm.getRouteConnectionId().getTime();
		}
		return time1 - time2;
	}
}
