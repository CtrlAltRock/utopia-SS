package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.repos.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public Optional<Flight> getFlightsById(Integer id){
        return flightRepository.findById(id);
    }

    public void addFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public void addFlights(List<Flight> flights) {
        flightRepository.saveAll(flights);
    }
}
