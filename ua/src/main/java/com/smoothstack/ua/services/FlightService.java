package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.repos.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FlightService {

    Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    FlightRepository flightRepository;
    public List<Flight> getFlights() { return (List<Flight>) flightRepository.findAll(); }

    public Flight getFlightById(Integer flightId) { return flightRepository.findById(flightId).get(); }

    public List<Flight> getFlightsByRouteId(Integer routeId) { return flightRepository.findByRouteId(routeId); }

    public List<Flight> getFlightsByAirplaneId(Integer airplaneId) { return flightRepository.findByAirplaneId(airplaneId); }

    public void saveFlights(List<Flight> flights) { flightRepository.saveAll(flights); }

    public Flight saveFlight(Flight flight) {
        Flight posted = flightRepository.saveAndFlush(flight);
        return posted;
    }

    public void deleteFlights(List<Flight> flights) { flightRepository.deleteAll(flights); }

    public void deleteFlightById(Integer id) { flightRepository.deleteById(id);}
}
