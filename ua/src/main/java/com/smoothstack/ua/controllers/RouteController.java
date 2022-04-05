package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.models.Route;
import com.smoothstack.ua.services.AirportService;
import com.smoothstack.ua.services.RouteService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @Autowired
    private AirportService airportService;

    @Timed("get.routes.dump")
    @RequestMapping(path = "utopia/airlines/routes/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getRoutes() {
        List<Route> routes = routeService.getRoutes();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @Timed("get.routes.id")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getRoutesById(@PathVariable Integer routeId) {
        Route check = routeService.getRouteById(routeId);
        if(check == null) {
            logger.info("route does not exist");
            return new ResponseEntity<>("route does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("route does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.routes")
    @RequestMapping(path = "utopia/airlines/routes/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveRoutes(@RequestBody Route route) {
        Route check = routeService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id());
        if(check == null) {
            Airport originCheck = airportService.getAirportById(route.getOriginAirport().getIata_id());
            Airport destinationCheck = airportService.getAirportById(route.getDestinationAirport().getIata_id());
            if(originCheck == null || destinationCheck == null) {
                logger.info("either origin and/or destination airport does not exist");
                return new ResponseEntity<>("either origin and/or destination airport does not exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("route does not exist");
                Route posted = routeService.saveRoute(route);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
        else {
            logger.info("route already exists");
            return new ResponseEntity<>("route already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.routes")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveRoute(@RequestBody Route route, @PathVariable Integer routeId) {
        if(route.getOriginAirport() == null || route.getDestinationAirport() == null) {
            logger.info("either origin airport is null and/or destination airport is null");
            return new ResponseEntity<>("either origin airport is null and/or destination airport is null", HttpStatus.BAD_REQUEST);
        }
        else{
            Route check = routeService.getRouteById(routeId);
            if(check == null) {
                logger.info("route does not exist");
                return new ResponseEntity<>("does not exist", HttpStatus.BAD_REQUEST);
            }
            else {
                Airport originCheck = airportService.getAirportById(route.getOriginAirport().getIata_id());
                Airport destinationCheck = airportService.getAirportById(route.getDestinationAirport().getIata_id());
                if(originCheck == null || destinationCheck == null) {
                    logger.info("either origin and/or destination airport does not exist");
                    return new ResponseEntity<>("either origin and/or destination airport does not exist", HttpStatus.BAD_REQUEST);
                }
                else{
                    logger.info("route exists");
                    route.setId(routeId);
                    Route posted = routeService.saveRoute(route);
                    return new ResponseEntity<>(posted, HttpStatus.OK);
                }
            }
        }
    }

    @Timed("delete.route")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRouteById(@PathVariable Integer routeId) {
        logger.info(routeId.toString(), "route id argument");
        Route check = routeService.getRouteById(routeId);
        if(check == null) {
            logger.info("route does not exist");
            return new ResponseEntity<>("route does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("route does exist");
            check.setOriginAirport(null);
            check.setDestinationAirport(null);
            routeService.deleteRoute(check);
            return new ResponseEntity<>("deleting route " + check.toString(), HttpStatus.OK);
        }
    }
}
