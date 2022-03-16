package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.models.Route;
import com.smoothstack.ua.populate.Populate;
import com.smoothstack.ua.services.AdminService;
import com.smoothstack.ua.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class TestController {

    @Autowired
    AdminService adminService;

    @Autowired
    FlightService flightService;

    @RequestMapping(path = "utopia/flights/{flightId}/", method = RequestMethod.GET)
    public String getFlightById(@PathVariable Integer flightId) {
        return flightService.getFlightsById(flightId).toString();
    }

    @RequestMapping(path = "utopia/airlines/routes/generate/{amount}", method = RequestMethod.GET)
    public void populateRoutes(@PathVariable Integer amount) {
        List<Airport> airports = adminService.getAllAirports();
        Random rand = new Random(42);
        for(int i = 0; i < amount; i++) {
            Boolean quit = false;
            while(!quit) {
                Integer o = rand.nextInt(airports.size()+1);
                Integer d = rand.nextInt(airports.size()+1);
                if(o != d) {
                    Route route = new Route(airports.get(o), airports.get(d));
                    adminService.saveRoute(route);
                    quit = true;
                }
            }
        }
    }


}
