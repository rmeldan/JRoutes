package com.softserve.edu.jroutes.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.edu.jroutes.component.RouteCorrection;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.RouteService;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;

@Controller
@RequestMapping("/findRoute")
public class RouteDetailsController {
    
    @Autowired
    private SecurityRoleService securityRoleService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ElementService<Country> countryService;
    @Autowired
    private RoutePointService routePointService;
    @Autowired
    private SavedRouteService savedRouteService;
    @Autowired
    private RouteConnectionService routeConnectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ElementService<Transport> transportService;
    @Autowired
    private RouteCorrection routeCorrection;
/**
 * Controller method shows detailer information about selected route
 * @param id
 *      savedRouteId
 * @param routePointAId
 *      Depart
 * @param routePointBId
 *      Arrive
 * @param model
 * @return
 */
    @RequestMapping(value = "/details/{id}/{routePointAId}/{routePointBId}", method = RequestMethod.GET)
    protected String showUserRoutesMore(@PathVariable Long id,
            @PathVariable Long routePointAId, @PathVariable Long routePointBId,
            ModelMap model) {

        // DTO list that will be passed on jsp
        List<RouteConnectPointDto> routeConnectionDTOList = new LinkedList<RouteConnectPointDto>();
        RoutePoint depart = routePointService.getElementByID(routePointAId);

        // Getting saved route
        SavedRoute savedRoute = savedRouteService.getSavedRouteById(id);

        // Getting all routes of saved route
        List<Route> routeList = routeService.getElementsByCriteria(savedRoute,
                null);

        // Filling routeConnectionDTO list
        for (Route route : routeList) {
            RouteConnectPointDto routeConnectionDTO = new RouteConnectPointDto();

            routeConnectionDTO.setId(route.getSequenceNumber());
            routeConnectionDTO.setRouteConnectionId(route
                    .getRouteConnectionId());
            routeConnectionDTO.setTransportId(route.getRouteConnectionId()
                    .getTransportId());
            routeConnectionDTO
                    .setPrice(route.getRouteConnectionId().getPrice());
            routeConnectionDTO.setTime(route.getRouteConnectionId().getTime()
                    / 60
                    + ":"
                    + String.format("%02d", route.getRouteConnectionId()
                            .getTime() % 60));

            routeConnectionDTO.setRoutePointAId(route.getRouteConnectionId()
                    .getRoutePointAId());
            routeConnectionDTO.setRoutePointBId(route.getRouteConnectionId()
                    .getRoutePointBId());

            routeConnectionDTOList.add(routeConnectionDTO);
        }

        // correcting route
        routeConnectionDTOList = routeCorrection.correct(routeConnectionDTOList, depart);

        model.addAttribute("routeList", routeConnectionDTOList);

        return "userRoutesMore";
    }
}
