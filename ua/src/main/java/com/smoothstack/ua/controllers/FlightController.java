package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Airplane;
import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.models.Route;
import com.smoothstack.ua.services.AirplaneService;
import com.smoothstack.ua.services.FlightService;
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
public class FlightController {

    private Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private RouteService routeService;

    @Timed("get.flights.dump")
    @RequestMapping(path = "utopia/airlines/flights/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlights() {
        List<Flight> flights = flightService.getFlights();
        logger.info(flights.toString(), "getting all flights");
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Timed("get.flights.id")
    @RequestMapping(path = "utopia/airlines/flights/{flightId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightById(@PathVariable Integer flightId) {
        Flight check = flightService.getFlightById(flightId);
        if(check == null) {
            logger.info("flight does not exist");
            return new ResponseEntity<>("flight does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("flight does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }


    @Timed("post.flights")
    @RequestMapping(path = "utopia/airlines/flight/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postFlight(@RequestBody Flight flight) {
        if(flight.getAirplane() == null || flight.getRoute() == null || flight.getId() != null) {
            logger.info("not accepting body argument");
            return new ResponseEntity<>("either airplane is null or route is null or an id has been supplied to the RequestBody", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("flight body seems alright");
            Route routeCheck = routeService.getRouteById(flight.getRoute().getId());
            Airplane airplaneCheck = airplaneService.getAirplaneById(flight.getAirplane().getId());
            if(routeCheck == null || airplaneCheck == null) {
                logger.info("flight is missing either a route and/or an airplane");
                return new ResponseEntity<>("flight is missing either a route and/or an airplane", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight is acceptable");
                Flight posted = flightService.saveFlight(flight);
                return new ResponseEntity<>(flight, HttpStatus.OK);
            }
        }
    }

    @Timed("put.flights")
    @RequestMapping(path = "utopia/airlines/flight/{flightId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putFlight(@RequestBody Flight flight, @PathVariable Integer flightId) {
        Flight check = flightService.getFlightById(flightId);
        if(check == null) {
            logger.info("flight id does not exist");
            return new ResponseEntity<>("flight id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            if(flight.getAirplane() == null || flight.getRoute() == null || flight.getId() != null) {
                logger.info("not accepting body argument");
                return new ResponseEntity<>("either airplane is null or route is null or an id has been supplied to the RequestBody", HttpStatus.BAD_REQUEST);
            }
            else {
                logger.info("flight body seems alright");
                Route routeCheck = routeService.getRouteById(flight.getRoute().getId());
                Airplane airplaneCheck = airplaneService.getAirplaneById(flight.getAirplane().getId());
                if (routeCheck == null || airplaneCheck == null) {
                    logger.info("flight is missing either a route and/or an airplane");
                    return new ResponseEntity<>("flight is missing either a route and/or an airplane", HttpStatus.BAD_REQUEST);
                } else {
                    logger.info("flight is acceptable");
                    flight.setId(flightId);
                    Flight posted = flightService.saveFlight(flight);
                    return new ResponseEntity<>(flight, HttpStatus.OK);
                }
            }
        }
    }

    @Timed("delete.flights")
    @RequestMapping(path = "utopia/airlines/flight/{flightId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFlightById(@PathVariable Integer flightId) {
        Flight check = flightService.getFlightById(flightId);
        if(check == null) {
            logger.info("flight id does not exist");
            return new ResponseEntity<>("flight id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight id does exist");
            flightService.deleteFlightById(flightId);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
