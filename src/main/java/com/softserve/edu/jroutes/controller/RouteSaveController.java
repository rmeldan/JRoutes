package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.edu.jroutes.component.RegisteredUserObject;
import com.softserve.edu.jroutes.component.UserRouteSaveFiller;
import com.softserve.edu.jroutes.dto.RouteConnectPointDto;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.RouteService;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;
import com.softserve.edu.jroutes.validators.UserRouteSaveValidation;

@Controller
@RequestMapping("/findRoute/saveRoute")
public class RouteSaveController {

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
    private UserRouteSaveValidation userRouteSaveValidation;
    @Autowired
    private UserRouteSaveFiller userRouteSaveFiller;
    @Autowired
    private RegisteredUserObject registeredUserObject;

    private static final Logger LOGGER = Logger
            .getLogger(RouteBuiltController.class);

    public void setUserRouteValidation(
            UserRouteSaveValidation userRouteSaveValidation) {
        this.userRouteSaveValidation = userRouteSaveValidation;
    }

    /**
     * Controller method for displaying userRouteSave.jsp page
     * @param isRoute if true means user is saving route, false -
     *            routeConnection
     * @param RCorSRid RouteConnectionId or SavedRouteId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{isRoute}/{RCorSRid}/nameIt", method = RequestMethod.GET)
    protected String saveUserRoute(@PathVariable Boolean isRoute,
            @PathVariable Long RCorSRid, ModelMap model) {

        // if user is not logged in
        if (registeredUserObject.getRegisteredUserObject() == null) {
            return "redirect:/";
        }

        String message = userRouteSaveFiller.getMessage();
        boolean isValid = userRouteSaveFiller.isValid();

        // DTO will pass to next jsp
        RouteConnectPointDto routeConnectionDTO = new RouteConnectPointDto();

        LOGGER.info("userRouteSave.jsp controller(returns userRouteSave.jsp)");
        LOGGER.info("Message passed to userRouteSave.jsp : " + message);
        LOGGER.info("Boolean value to show error message or not : " + isValid);

        model.addAttribute("message", message);
        model.addAttribute("isValid", isValid);
        model.addAttribute("routeConnectionDTO", routeConnectionDTO);

        return "userRouteSave";
    }

    /**
     * Controller method for saving selected route
     * @param isRoute if true means user is saving route, false -
     *            routeConnection
     * @param RCorSRid RouteConnectionId or SavedRouteId
     * @param routeConnectionDTO DTO filler with information about selected
     *            route
     * @param model
     * @param result used by validation
     * @return
     */
    @RequestMapping(value = "/{isRoute}/{RCorSRid}/savingRoute", method = RequestMethod.POST)
    public String savingRoute(@PathVariable Boolean isRoute,
            @PathVariable Long RCorSRid,
            @ModelAttribute RouteConnectPointDto routeConnectionDTO,
            ModelMap model, BindingResult result) {

        // if user is not logged in
        if (registeredUserObject.getRegisteredUserObject() == null) {
            LOGGER.info("userRouteSave.jsp USER was NOT LOGGED IN");
            return "redirect:/";
        }

        // validation fields
        String message = "saveRoute.saveError";
        boolean isValid = false;
        userRouteSaveFiller.setMessage(message);
        userRouteSaveFiller.setValid(isValid);

        // Controller validation
        userRouteSaveValidation.validate(routeConnectionDTO, result);
        if (result.hasErrors()) {
            LOGGER.info("Route name is not valid");
            return "redirect:/findRoute/saveRoute/" + isRoute + "/" + RCorSRid
                    + "/nameIt";
        }

        // Getting logged in user
        User user = registeredUserObject.getRegisteredUserObject();

        // user is saving Route
        if (isRoute) {
            // New User Route
            List<Route> userRouteList = new ArrayList<Route>();
            // Creating new savedRoute for user
            SavedRoute newUserSavedRoute = new SavedRoute();
            newUserSavedRoute.setUserId(user);
            newUserSavedRoute.setName(routeConnectionDTO.getName());
            newUserSavedRoute.setIsCompanyRoute(false);
            newUserSavedRoute.setModificationTime(new java.sql.Date(
                    (long) (new java.util.Date()).getTime()));

            try {
                savedRouteService.addSavedRoute(newUserSavedRoute);
                isValid = true;
                userRouteSaveFiller.setValid(isValid);

                LOGGER.info("Saving new user route with savedRouteId:"
                        + newUserSavedRoute.getId());
            } catch (NonUniqueException e) {
                // friendly exit
                isValid = false;
                userRouteSaveFiller.setValid(isValid);

                LOGGER.info("SavedRoute was not unique!");

                return "redirect:/findRoute/saveRoute/" + isRoute + "/"
                        + RCorSRid + "/nameIt";
            }

            // Getting Route
            List<Route> routeList = routeService.getElementsByCriteria(
                    savedRouteService.getSavedRouteById(RCorSRid), null);

            // Adding routes to user list with modified savedRoute
            for (Route route : routeList) {
                Route userRoute = new Route();
                userRoute.setRouteConnectionId(route.getRouteConnectionId());
                userRoute.setSavedRouteId(newUserSavedRoute);

                userRoute.setSequenceNumber(route.getSequenceNumber());

                userRouteList.add(userRoute);
            }
            // adding route elements to db
            for (Route userRoute : userRouteList) {
                try {
                    routeService.addElement(userRoute);
                    isValid = true;
                    userRouteSaveFiller.setValid(isValid);

                    LOGGER.info("Saving new user route with routeId (savedRouteId/routeConnectionId ):"
                            + userRoute.getSavedRouteId().getId()
                            + "/"
                            + userRoute.getRouteConnectionId().getId());
                } catch (NonUniqueException e) {
                    isValid = false;
                    userRouteSaveFiller.setValid(isValid);
                    LOGGER.info("Route was not unique!");

                    return "redirect:/findRoute/saveRoute/" + isRoute + "/"
                            + RCorSRid + "/nameIt";
                }
            }

            // routeConnection
        } else {
            // creating SavedRoute
            SavedRoute newUserSavedRoute = new SavedRoute();
            newUserSavedRoute.setUserId(user);
            newUserSavedRoute.setIsCompanyRoute(routeConnectionDTO
                    .isFromRoute());
            newUserSavedRoute.setName(routeConnectionDTO.getName());
            newUserSavedRoute.setModificationTime(new java.sql.Date(
                    (long) (new java.util.Date()).getTime()));

            try {
                savedRouteService.addSavedRoute(newUserSavedRoute);
                isValid = true;
                userRouteSaveFiller.setValid(isValid);
                LOGGER.info("Saving new user route with savedRouteId:"
                        + newUserSavedRoute.getId());
            } catch (NonUniqueException e) {
                isValid = false;
                userRouteSaveFiller.setValid(isValid);
                LOGGER.info("SavedRoute was not unique!");

                return "redirect:/findRoute/saveRoute/" + isRoute + "/"
                        + RCorSRid + "/nameIt";
            }

            // creating user Route
            Route userRoute = new Route();

            userRoute.setRouteConnectionId(routeConnectionService
                    .getElementByID(RCorSRid));
            userRoute.setSavedRouteId(newUserSavedRoute);
            userRoute.setSequenceNumber((long) 1);

            Set<Route> routes = new HashSet<Route>();
            routes.add(userRoute);
            newUserSavedRoute.setRoutes(routes);

            try {
                savedRouteService.addSavedRoute(newUserSavedRoute);
                isValid = true;
                userRouteSaveFiller.setValid(isValid);

                LOGGER.info("Saving new user route with routeId (savedRouteId/routeConnectionId ):"
                        + userRoute.getSavedRouteId().getId()
                        + "/"
                        + userRoute.getRouteConnectionId().getId());

            } catch (NonUniqueException e) {
                isValid = false;
                userRouteSaveFiller.setValid(isValid);
                LOGGER.info("Route was not unique!");

                return "redirect:/findRoute/saveRoute/" + isRoute + "/"
                        + RCorSRid + "/nameIt";
            }
        }

        User loggedUser = registeredUserObject.getRegisteredUserObject();
        Set<SecurityRole> loggedUserRoles = loggedUser.getRoles();

        LOGGER.info("UserId that saved route: " + loggedUser.getId());

        for (SecurityRole securityRole : loggedUserRoles) {
            System.out.println("***" + securityRole.getName());
            if (securityRole.getName().equals("ROLE_ADMIN"))
                return "redirect:/routes/savedRoutesAdmin";
            if (securityRole.getName().equals("ROLE_SUPER-ADMIN"))
                return "redirect:/routes/savedRoutesAdmin";
            if (securityRole.getName().equals("ROLE_USER"))
                return "redirect:/savedRoutesUser";
        }
        return "redirect:/";
    }
}
