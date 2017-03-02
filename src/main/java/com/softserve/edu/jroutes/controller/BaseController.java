package com.softserve.edu.jroutes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.softserve.edu.jroutes.dto.RouteConnectionExDTO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.service.ElementService;
import com.softserve.edu.jroutes.service.RouteConnectionService;
import com.softserve.edu.jroutes.service.RoutePointService;
import com.softserve.edu.jroutes.service.RouteService;
import com.softserve.edu.jroutes.service.RouteServiceImpl;
import com.softserve.edu.jroutes.service.SavedRouteService;
import com.softserve.edu.jroutes.service.SecurityRoleService;
import com.softserve.edu.jroutes.service.UserService;

@Controller
@RequestMapping("/")
public class BaseController {

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
    
    private static final Logger LOGGER = Logger.getLogger(RouteBuiltController.class);
/**
 * Controller method for showing index.jsp page
 * @param model
 * @return index.jsp
 */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String welcome(ModelMap model) {

        // sending roles to index.jsp (roles will be seen in the Role tab)
        List<SecurityRole> roles = securityRoleService.getAllSecurityRoles();

        // Getting all countries
        List<Country> countries = countryService.getAllElements();
        // Getting all transport
        List<Transport> transportList = transportService.getAllElements();
        // Transport that will be passed on page
        List<Transport> transportListFinal = new ArrayList<Transport>();

        // Adding first four on page
        for (int i = 0; i < transportList.size(); i++) {
            if (i < 4) {
                transportListFinal.add(transportList.get(i));
            }
        }

        model.addAttribute("transportList", transportListFinal);
        model.addAttribute("countries", countries);
        model.addAttribute("RouteConnectionExDTO", new RouteConnectionExDTO());
        model.addAttribute("roles", roles);
        
        LOGGER.info("Size of Countries passed to index: " + countries.size());
        LOGGER.info("Size of transport list passed to index: " + transportListFinal.size());
        return "index";
    }
/**
 * Controller method for filling combo boxes of cities using CountryId
 * @param sendValue
 *          CountryId
 * @return List of cities of selected country
 */
    @RequestMapping(value = "/ajaxFirstTwoCombos", method = RequestMethod.GET)
    protected @ResponseBody
    String ajaxFirstTwoCombos(@RequestParam String sendValue) {
        Long sendValueId = Long.parseLong(sendValue);
        // Getting country selected in combo box
        Country country = countryService.getElementByID(sendValueId);

        // List of cities that will be returned
        List<RoutePoint> routePointList = new ArrayList<RoutePoint>();
        // Getting all cities
        List<RoutePoint> allRoutePointList = routePointService.getAllElements();

        // Getting all cities of the country selected
        for (int i = 0; i < allRoutePointList.size(); i++) {
            RoutePoint rp = allRoutePointList.get(i);
            if (rp.getCountry().getId() == country.getId()) {
                routePointList.add(rp);
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(routePointList);
        
        LOGGER.info("Combo box city fill function");
        LOGGER.info("Country selected: " + country.getName());
        LOGGER.info("Size of cities passed to index: " + routePointList.size());
        
        return json;
    }

}