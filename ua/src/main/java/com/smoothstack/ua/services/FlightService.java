package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.repos.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightService {

    Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    FlightRepository flightRepository;
    public List<Flight> getFlights() { return (List<Flight>) flightRepository.findAll(); }

    public Flight getFlightById(Integer flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        if(flight.isPresent()) return flight.get();
        else return null;
    }

    public List<Flight> getFlightsByRouteId(Integer routeId) {
        return flightRepository.findByRouteId(routeId);
    }

    public Flight saveFlight(Flight flight) {
        Flight posted = flightRepository.save(flight);
        return posted;
    }

    public void deleteFlightById(Integer id) {
        flightRepository.deleteById(id);
    }
}
