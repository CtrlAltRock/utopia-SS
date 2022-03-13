package com.smoothstack.ua.controllers;

import com.smoothstack.ua.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    FlightService flightService;

    @RequestMapping(path = "utopia/flights/{flightId}/", method = RequestMethod.GET)
    public String getFlightById(@PathVariable Integer flightId) {
        return flightService.getFlightsById(flightId).toString();
    }


}
