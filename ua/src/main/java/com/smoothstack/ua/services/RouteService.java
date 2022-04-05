package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Route;
import com.smoothstack.ua.repos.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RouteService {

    Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private RouteRepository routeRepository;


    public List<Route> getRoutes() { return (List<Route>) routeRepository.findAll(); }

    public Route getRouteById(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if(route.isPresent()) {
            return route.get();
        }
        else return null;
    }

    public Route getRouteByOriginDestination(String origin, String destination) {
        Optional<Route> route = routeRepository.findByOriginDestination(origin, destination);
        if(route.isPresent()) {
            return route.get();
        }
        else return null;
    }

    public void saveRoutes(List<Route> routes) { routeRepository.saveAll(routes); }

    public Route saveRoute(Route route) {
        Route posted = routeRepository.save(route);
        return posted;
    }

    public void deleteRoute(Route route) {
        routeRepository.delete(route);
    }

    public void deleteRouteById(Integer id) {
        routeRepository.deleteById(id);
    }
}
