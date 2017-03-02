package com.softserve.edu.jroutes.service;

import java.util.List;

import com.softserve.edu.jroutes.dto.RouteConnectionExDTO;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.exception.NonUniqueException;

public interface RouteService {
    void addElement(Route routeConection) throws NonUniqueException;

    void updateElement(Route element) throws NonUniqueException;

    void deleteElement(Route element);

    List<Route> getAllElements();

    Route getElementByID(Long id);

    public List<Route> getElementsByCriteria(Object... criteria);

    boolean matchesUserFilter(List<Route> routeList, RouteConnectionExDTO dto,
            long departSequence, long arriveSequence);

}
