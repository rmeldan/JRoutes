package com.softserve.edu.jroutes.component;

import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.entity.RoutePoint;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RouteCorrection {

    public List<RouteConnectPointDto> correct(
            List<RouteConnectPointDto> routeConnectionDTOList, RoutePoint depart) {

        // sorting by sequence number
        Collections.sort(routeConnectionDTOList);

        /*
         * Correcting pointA with pointB 1.If there is only one connection
         */
        if (routeConnectionDTOList.size() == 1) {
            if (routeConnectionDTOList.get(0).getRouteConnectionId()
                    .getRoutePointAId().getId() != depart.getId()) {

                rotate(routeConnectionDTOList, 0);
            }
            // 2.There is more then one connection
        } else {
            for (int i = 0; i < routeConnectionDTOList.size(); i++) {
                // if has next element
                if (i != routeConnectionDTOList.size() - 1) {
                    // (B A) (B C) type connection
                    if (routeConnectionDTOList.get(i).getRoutePointAId()
                            .getId() == routeConnectionDTOList.get(i + 1)
                            .getRoutePointAId().getId()) {

                        rotate(routeConnectionDTOList, i);

                    }
                    // (B A) (C B) type connection
                    else if (routeConnectionDTOList.get(i).getRoutePointAId()
                            .getId() == routeConnectionDTOList.get(i + 1)
                            .getRoutePointBId().getId()) {

                        rotate(routeConnectionDTOList, i);
                    }
                }
                // last element
                if (i == routeConnectionDTOList.size() - 1) {
                    if (routeConnectionDTOList.get(i).getRoutePointAId()
                            .getId() != routeConnectionDTOList.get(i - 1)
                            .getRoutePointBId().getId()) {

                        rotate(routeConnectionDTOList, i);
                    }
                }
            }
        }

        return routeConnectionDTOList;
    }

    public void rotate(List<RouteConnectPointDto> routeConnectionDTOList, int i) {
        // saved A to tmp
        RoutePoint rcTemp = routeConnectionDTOList.get(i).getRoutePointAId();
        // A = B
        routeConnectionDTOList.get(i).setRoutePointAId(
                routeConnectionDTOList.get(i).getRoutePointBId());
        // B = tmp
        routeConnectionDTOList.get(i).setRoutePointBId(rcTemp);
    }
}
