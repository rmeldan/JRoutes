package com.softserve.edu.jroutes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.jroutes.dao.ElementDAO;
import com.softserve.edu.jroutes.dto.RouteConnectionExDTO;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RouteConnection;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.exception.NonUniqueException;

@Transactional
@Service("routeService")
public class RouteServiceImpl implements RouteService {
    @Autowired
    private ElementDAO<Route> routeDAO;
    @Autowired
    private ElementService<Transport> transportService;

    @Override
    public void addElement(Route element) throws NonUniqueException {
        routeDAO.addElement(element);
    }

    @Override
    public void updateElement(Route element) throws NonUniqueException {
        routeDAO.updateElement(element);
    }

    @Override
    public List<Route> getAllElements() {
        return routeDAO.getAllElements();
    }

    @Override
    public void deleteElement(Route element) {
        routeDAO.deleteElement(element);
    }

    @Override
    public List<Route> getElementsByCriteria(Object... criteria) {
        return routeDAO.getElementsByCriteria(criteria);
    }

    @Override
    public Route getElementByID(Long elementId) {
        return routeDAO.getElementByID(elementId);
    }

    @Override
    public boolean matchesUserFilter(List<Route> routeList,
            RouteConnectionExDTO dto, long departSequence, long arriveSequence) {

        for (Route route : routeList) {
            if (route.getSequenceNumber() >= departSequence
                    && route.getSequenceNumber() <= arriveSequence) {
                RouteConnection routeConnection = route.getRouteConnectionId();
                if (isCorrectConnection(routeConnection, dto) == false)
                    return false;
            }
        }

        return true;
    }

    private boolean isCorrectConnection(RouteConnection rc,
            RouteConnectionExDTO dto) {
        
        if (isCorrectConnectionTime(rc, dto) == false)
            return false;
        if (isCorrectConnectionPrice(rc, dto) == false)
            return false;
        if (isCorrectConnectionTransport(rc, dto) == false)
            return false;

        return true;
    }

    private boolean isCorrectConnectionTime(RouteConnection rc,
            RouteConnectionExDTO dto) {

        if (dto.getTime() == 0)
            return true;

        if (rc.getTime() < dto.getTime())
            return true;

        return false;
    }

    private boolean isCorrectConnectionPrice(RouteConnection rc,
            RouteConnectionExDTO dto) {

        if (dto.getPrice() == 0)
            return true;

        if (rc.getPrice() < dto.getPrice())
            return true;

        return false;
    }

    private boolean isCorrectConnectionTransport(RouteConnection rc,
            RouteConnectionExDTO dto) {

        if (dto.getTransport0() == null && dto.getTransport1() == null
                && dto.getTransport2() == null && dto.getTransport3() == null) {
            return true;
        }

        if (dto.getTransport0() != null) {
            if (rc.getTransportId().getId() == transportService
                    .getElementsByCriteria(dto.getTransport0()).get(0).getId())
                return true;
        }
        if (dto.getTransport1() != null) {
            if (rc.getTransportId().getId() == transportService
                    .getElementsByCriteria(dto.getTransport1()).get(0).getId())
                return true;
        }
        if (dto.getTransport2() != null) {
            if (rc.getTransportId().getId() == transportService
                    .getElementsByCriteria(dto.getTransport2()).get(0).getId())
                return true;
        }
        if (dto.getTransport3() != null) {
            if (rc.getTransportId().getId() == transportService
                    .getElementsByCriteria(dto.getTransport3()).get(0).getId())
                return true;
        }

        return false;
    }
}