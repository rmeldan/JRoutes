package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.softserve.edu.jroutes.component.AlgorithmComponent;
import com.softserve.edu.jroutes.component.RegUserInterface;
import com.softserve.edu.jroutes.component.RouteSearchFilter;
import com.softserve.edu.jroutes.component.UserRouteDTOFiller;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.dto.RouteConnectionExDTO;
import com.softserve.edu.jroutes.dto.SavedRouteDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.RouteService;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.service.SavedRouteServiceImpl;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;

@Controller
@RequestMapping("/")
public class RouteFindController {
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
    private RouteSearchFilter routeSearchFilter;
    @Autowired
    private RegUserInterface registeredUserObject;
    @Autowired
    private AlgorithmComponent algorithmComponent;

    private static final Logger LOGGER = Logger
            .getLogger(RouteBuiltController.class);

    /**
     * Controller method for showing userRoutes.jsp Method is looking for all
     * routes from PointA to PointB including price, time and transport filters
     * @param dto DTO with Depart, Arrive and filter information
     * @param model
     * @return userRoutes.jsp page
     */
    @RequestMapping(value = "/findRoute", method = RequestMethod.GET)
    protected String showFilteredRoutes(
            @ModelAttribute RouteConnectionExDTO dto, ModelMap model) {

        // if user is not logged in
        if (registeredUserObject.getRegisteredUserObject() == null) {
            return "admin";
        }

        // DTO list that will be passed on jsp
        List<RouteConnectPointDto> routeConnectionDTOList = new ArrayList<RouteConnectPointDto>();
        // DTO filler
        UserRouteDTOFiller filler;

        RoutePoint depart = routePointService.getElementByID((long) dto
                .getDepartCity());
        RoutePoint arrive = routePointService.getElementByID((long) dto
                .getArriveCity());

        // NONSTOP
        // Finding RC
        // Finding Routes
        if (dto.getTransferCity() == 0) {

            // route connections with pointA and pointB, transport, time, price
            // selected
            List<RouteConnection> filteredConnectionList = new ArrayList<RouteConnection>();

            // List of filtered ROUTES
            List<Route> routeListFinal = new ArrayList<Route>();

            // Filtering CONNECTIONS
            filteredConnectionList = routeSearchFilter
                    .routeConnectionSearchFilterByDTO(dto);

            // route list is forming here
            for (RouteConnection rct : filteredConnectionList) {

                List<Route> routeList = routeService.getElementsByCriteria(
                        null, rct);

                for (Route route : routeList) {
                    routeListFinal.add(route);
                }
            }

            // Filling dto with routes
            filler = new UserRouteDTOFiller();
            routeConnectionDTOList
                    .addAll(filler.fillWithRoutes(routeListFinal));

            // Filling routeConnectionDTO list with routeConnections
            routeConnectionDTOList.addAll(filler
                    .fillWithConnections(filteredConnectionList));
        }

        // INCLUDE STOPS
        if (dto.isIncludeLayout()) {

            // Routes that will be passed on jsp
            List<Route> routeListFinalList = new ArrayList<Route>();

            RoutePoint transfer = routePointService.getElementByID((long) dto
                    .getTransferCity());

            if (dto.getTransferCity() == 0) {
                algorithmComponent.showFoundRoutes(depart, arrive, null, dto,
                        model);
            } else {
                algorithmComponent.showFoundRoutes(depart, arrive, transfer,
                        dto, model);
            }

            // Getting connections
            List<RouteConnection> routeFirtsConnectionList = routeConnectionService
                    .getElementsByCriteria(depart, null, null, null, null);

            // Getting routes with that connections
            List<Route> routeList = new ArrayList<Route>();
            for (RouteConnection routeConnection : routeFirtsConnectionList) {
                routeList.addAll(routeService.getElementsByCriteria(null,
                        routeConnection));
            }
            // checking each route
            for (Route route : routeList) {
                List<Route> fullRouteList = new ArrayList<Route>();
                SavedRoute savedRoute = route.getSavedRouteId();
                fullRouteList.addAll(savedRoute.getRoutes());

                if (isCorrectIncludingTransfer(fullRouteList, depart, transfer,
                        arrive, dto)) {
                    routeListFinalList.add(fullRouteList.get(0));
                }
                if (dto.getTransferCity() == 0) {
                    if (isCorrectWithoutTransfer(fullRouteList, depart, arrive,
                            dto))
                        routeListFinalList.add(fullRouteList.get(0));
                }
            }
            filler = new UserRouteDTOFiller();
            routeConnectionDTOList.addAll(filler
                    .fillWithRoutes(routeListFinalList));

        }
        
        LOGGER.info("Depart city passed to userRoutes.jsp " + depart.getName());
        LOGGER.info("Arrive city passed to userRoutes.jsp " + arrive.getName());
        LOGGER.info("Size of dto list passed to userRoutes.jsp " + routeConnectionDTOList.size());
        
        model.addAttribute("routeList", routeConnectionDTOList);
        model.addAttribute("depart", depart);
        model.addAttribute("arrive", arrive);

        return "userRoutes";
    }

    private boolean isCorrectIncludingTransfer(List<Route> oneRouteList,
            RoutePoint depart, RoutePoint transfer, RoutePoint arrive,
            RouteConnectionExDTO dto) {
        long departSequence = 0;
        long transferSequence = 0;
        long arriveSequence = 0;
        boolean isCorrect = false;
        if (oneRouteList.size() < 2)
            return false;

        // Getting sequence of each point
        for (Route routePart : oneRouteList) {
            if (routePart.getRouteConnectionId().getRoutePointAId() == depart
                    || routePart.getRouteConnectionId().getRoutePointBId() == depart)
                departSequence = routePart.getSequenceNumber();
            if (routePart.getRouteConnectionId().getRoutePointBId() == transfer)
                transferSequence = routePart.getSequenceNumber();
            if (routePart.getRouteConnectionId().getRoutePointBId() == arrive
                    || routePart.getRouteConnectionId().getRoutePointAId() == arrive)
                arriveSequence = routePart.getSequenceNumber();
        }
        // checking the sequence
        if (departSequence != 0 && transferSequence != 0 && arriveSequence != 0)
            if (departSequence <= transferSequence
                    && transferSequence <= arriveSequence) {
                isCorrect = true;
            } else
                return false;

        // Checking if route matches filter
        if (routeService.matchesUserFilter(oneRouteList, dto, departSequence,
                arriveSequence) == false)
            return false;

        return isCorrect;
    }

    private boolean isCorrectWithoutTransfer(List<Route> oneRouteList,
            RoutePoint depart, RoutePoint arrive, RouteConnectionExDTO dto) {

        long departSequence = 0;
        long arriveSequence = 0;
        boolean isCorrect = false;

        // Getting sequence of each point
        for (Route routePart : oneRouteList) {
            if (routePart.getRouteConnectionId().getRoutePointAId() == depart
                    || routePart.getRouteConnectionId().getRoutePointBId() == depart)
                departSequence = routePart.getSequenceNumber();
            if (routePart.getRouteConnectionId().getRoutePointBId() == arrive
                    || routePart.getRouteConnectionId().getRoutePointAId() == arrive)
                arriveSequence = routePart.getSequenceNumber();
        }

        // Checking the sequence
        if (departSequence != 0 && arriveSequence != 0)
            if (departSequence < arriveSequence) {
                isCorrect = true;
            } else
                return false;

        // Checking if route matches filter
        if (routeService.matchesUserFilter(oneRouteList, dto, departSequence,
                arriveSequence) == false)
            return false;

        return isCorrect;
    }
}
