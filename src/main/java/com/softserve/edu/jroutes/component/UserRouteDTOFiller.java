package com.softserve.edu.jroutes.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;

@Component
public class UserRouteDTOFiller {

    public List<RouteConnectPointDto> fillWithRoutes(List<Route> routeListFinal) {

        // DTO list that will be passed on jsp
        List<RouteConnectPointDto> routeConnectionDTOList = new ArrayList<RouteConnectPointDto>();
        long totalPrice = 0;
        long totalTime = 0;

        for (Route route : routeListFinal) {
            RouteConnectPointDto routeConnectionDTO = new RouteConnectPointDto();
            totalPrice = 0;
            totalTime = 0;
            RoutePoint realStart = null;
            if (route.getSavedRouteId().getIsCompanyRoute() == true) {
                routeConnectionDTO.setId(route.getSequenceNumber());
                routeConnectionDTO.setRouteConnectionId(route
                        .getRouteConnectionId());
                routeConnectionDTO.setTransportId(route.getRouteConnectionId()
                        .getTransportId());

                SavedRoute savedRoute = route.getSavedRouteId();
                Set<Route> fullRoute = savedRoute.getRoutes();
                List<Route> fullRouteList = new ArrayList<Route>();
                for (Route routePart : fullRoute) {
                    // Calculating total time, price
                    totalPrice += routePart.getRouteConnectionId().getPrice();
                    totalTime += routePart.getRouteConnectionId().getTime();
                    fullRouteList.add(routePart);
                }

                routeConnectionDTO.setPrice(totalPrice);
                routeConnectionDTO.setTime(totalTime
                        / 60
                        + ":"
                        + String.format("%02d", route.getRouteConnectionId()
                                .getTime() % 60));
                routeConnectionDTO.setSavedRouteId(route.getSavedRouteId());
                
                // Filling startServer point
                realStart = getRealDeparture(fullRouteList);
                if (realStart != null) {
                    routeConnectionDTO.setRoutePointAId(realStart);
                } else {
                    routeConnectionDTO.setRoutePointAId(route
                            .getRouteConnectionId().getRoutePointAId());
                }
                routeConnectionDTO.setRoutePointBId(route
                        .getRouteConnectionId().getRoutePointBId());
                routeConnectionDTO.setFromRoute(true);

                routeConnectionDTOList.add(routeConnectionDTO);
            }
        }
        return routeConnectionDTOList;
    }

    public RoutePoint getRealDeparture(List<Route> routeList) {
        RoutePoint realDeparture = null;
        RoutePoint start = null;
        Route firstSequenceRoute = null;
        Route secondSequenceRoute = null;

        // Getting first and second sequence route
        for (Route route : routeList) {
            if (route.getSequenceNumber() == 1) {
                firstSequenceRoute = route;
            } else if (route.getSequenceNumber() == 2) {
                secondSequenceRoute = route;
            }
        }

        if (firstSequenceRoute == null || secondSequenceRoute == null)
            return null;

        // Finding real departure point
        start = firstSequenceRoute.getRouteConnectionId().getRoutePointAId();
        if (start == secondSequenceRoute.getRouteConnectionId()
                .getRoutePointAId()
                || start == secondSequenceRoute.getRouteConnectionId()
                        .getRoutePointBId())
            realDeparture = firstSequenceRoute.getRouteConnectionId()
                    .getRoutePointBId();
        else
            realDeparture = start;

        return realDeparture;
    }

    public List<RouteConnectPointDto> fillWithConnections(
            List<RouteConnection> filteredConnectionList) {

        List<RouteConnectPointDto> routeConnectionDTOList = new ArrayList<RouteConnectPointDto>();

        // Filling routeConnectionDTO list with routeConnections
        for (RouteConnection routeConnection : filteredConnectionList) {
            RouteConnectPointDto routeConnectionDTO = new RouteConnectPointDto();

            routeConnectionDTO.setId(routeConnection.getId());
            routeConnectionDTO.setRouteConnectionId(routeConnection);
            routeConnectionDTO.setTransportId(routeConnection.getTransportId());
            routeConnectionDTO.setPrice(routeConnection.getPrice());
            routeConnectionDTO.setTime(routeConnection.getTime() / 60 + ":"
                    + String.format("%02d", routeConnection.getTime() % 60));
            // routeConnectionDTO.setSavedRouteId(route.getSavedRouteId());

            routeConnectionDTO.setRoutePointAId(routeConnection
                    .getRoutePointAId());
            routeConnectionDTO.setRoutePointBId(routeConnection
                    .getRoutePointBId());
            routeConnectionDTO.setFromRoute(false);

            routeConnectionDTOList.add(routeConnectionDTO);
        }

        return routeConnectionDTOList;
    }
}
