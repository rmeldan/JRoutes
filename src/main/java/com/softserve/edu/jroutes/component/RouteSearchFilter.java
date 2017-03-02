package com.softserve.edu.jroutes.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.edu.jroutes.dto.RouteConnectionExDTO;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;


@Component
public class RouteSearchFilter {
    
    @Autowired
    private RouteConnectionService routeConnectionService;
    @Autowired
    private RoutePointService routePointService;
    @Autowired
    private ElementService<Transport> transportService;
    
    public List<RouteConnection> routeConnectionSearchFilterByDTO(RouteConnectionExDTO dto) {

        List<RouteConnection> filteredRouteConnectionList = new ArrayList<RouteConnection>();
        List<RouteConnection> finalRouteConnectionList = new ArrayList<RouteConnection>();
        
        RoutePoint depart = routePointService.getElementByID((long) dto
                .getDepartCity());
        RoutePoint arrive = routePointService.getElementByID((long) dto
                .getArriveCity());

        if (dto.getTransport0() == null && dto.getTransport1() == null
                && dto.getTransport2() == null && dto.getTransport3() == null) {        	
            filteredRouteConnectionList.addAll(routeConnectionService.getElementsByCriteria(depart,arrive, null, null, null));
            finalRouteConnectionList.addAll(filterByTimeAndPrice(filteredRouteConnectionList, dto));
        } else {
            if (dto.getTransport0() != null) {
                Transport transport = transportService.getElementsByCriteria(dto.getTransport0()).get(0);
                filteredRouteConnectionList.addAll(routeConnectionService.getElementsByCriteria(depart,arrive,new Transport[] { transport }, null, null));
                finalRouteConnectionList.addAll(filterByTimeAndPrice(filteredRouteConnectionList, dto));
            }
            if (dto.getTransport1() != null) {
                Transport transport = transportService.getElementsByCriteria(dto.getTransport1()).get(0);
                filteredRouteConnectionList.addAll(routeConnectionService.getElementsByCriteria(depart,arrive,new Transport[] { transport }, null, null));
                finalRouteConnectionList.addAll(filterByTimeAndPrice(filteredRouteConnectionList, dto));
            }
            if (dto.getTransport2() != null) {
                Transport transport = transportService.getElementsByCriteria(dto.getTransport2()).get(0);
                filteredRouteConnectionList.addAll(routeConnectionService.getElementsByCriteria(depart,arrive,new Transport[] { transport }, null, null));
                finalRouteConnectionList.addAll(filterByTimeAndPrice(filteredRouteConnectionList, dto));
            }
            if (dto.getTransport3() != null) {
                Transport transport = transportService.getElementsByCriteria(dto.getTransport3()).get(0);
                filteredRouteConnectionList.addAll(routeConnectionService.getElementsByCriteria(depart,arrive,new Transport[] { transport }, null, null));
                finalRouteConnectionList.addAll(filterByTimeAndPrice(filteredRouteConnectionList, dto));
            }
        }
        return filteredRouteConnectionList;
    }

    public List<RouteConnection> filterByTimeAndPrice(
            List<RouteConnection> routeConnectionList, RouteConnectionExDTO dto) {

        List<RouteConnection> filteredByTimeAndPrice = new ArrayList<RouteConnection>();

        for (RouteConnection rc : routeConnectionList) {
            if (dto.getPrice() == 0) {
                if (dto.getTime() == 0) {
                    filteredByTimeAndPrice.add(rc);
                } else {
                    if (rc.getTime() < dto.getTime()) {
                        filteredByTimeAndPrice.add(rc);
                    }
                }
            } else {
                if (rc.getPrice() < dto.getPrice()) {
                    if (dto.getTime() == 0) {
                        filteredByTimeAndPrice.add(rc);
                    } else {
                        if (rc.getTime() < dto.getTime()) {
                            filteredByTimeAndPrice.add(rc);
                        }
                    }
                }
            }
        }

        return filteredByTimeAndPrice;
    }
}
