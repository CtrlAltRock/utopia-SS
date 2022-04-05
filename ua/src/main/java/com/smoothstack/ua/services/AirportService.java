package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.repos.AirportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    Logger logger = LoggerFactory.getLogger(AirportService.class);

    @Autowired
    private AirportRepository airportRepository;


    public List<Airport> getAllAirports() {
        return (List<Airport>) airportRepository.findAll();
    }

    public Airport getAirportById(String iata_id) {
        Optional<Airport> airport = airportRepository.findById(iata_id);
        if(airport.isPresent()) {
            return airport.get();
        }
        else return null;
    }

    public void saveAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }

    public Airport saveAirport(Airport airport) {
        Airport posted = airportRepository.save(airport);
        return posted;
    }
    public void deleteAirports(List<Airport> airports) {
        airportRepository.deleteAll(airports);
    }

    public void deleteAirportById(String id) {
        airportRepository.deleteById(id);
    }

    public void deleteAirport(Airport airport) {
        airportRepository.delete(airport);
    }
}
