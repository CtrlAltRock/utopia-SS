package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Airport;
import com.smoothstack.ua.services.AirportService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class AirportController {

    private Logger logger = LoggerFactory.getLogger(AirportController.class);

    @Autowired
    private AirportService airportService;

    @Timed("get.airports.dump")
    @RequestMapping(path = "utopia/airlines/airports/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<Airport> getAirports() {
        logger.info("getting all airports");
        return airportService.getAllAirports();
    }

    @Timed("get.airports.id")
    @RequestMapping(path = "utopia/airlines/airports/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirportById(@PathVariable String id) {
        Airport check = airportService.getAirportById(id);
        if(check == null) {
            logger.info("id of airport does not exist");
            return new ResponseEntity<>(id + " does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(check.toString(), "airport does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.airports")
    @RequestMapping(path = "utopia/airlines/airports/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postAirport(@Valid @RequestBody Airport airport) {
        Airport check = airportService.getAirportById(airport.getIata_id());
        if(check == null) {
            logger.info(airport.toString(), "airport does not exist and can be posted");
            Airport posted = airportService.saveAirport(airport);
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }
        else{
            logger.info("airport already exists");
            return new ResponseEntity<>("airport already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.airports")
    @RequestMapping(path = "utopia/airlines/airports/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirport(@RequestBody Airport airport, @PathVariable String id) {
        Airport put = airportService.getAirportById(id);
        if(put == null) {
            logger.info("airport id to update does not exist");
            return new ResponseEntity<>("airport id to update does not exist\n" + airport.toString() + "\n" + id, HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("airport id exists going to update city");
            airport.setIata_id(id);
            Airport posted = airportService.saveAirport(airport);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }


    @Timed("delete.airports")
    @RequestMapping(path = "utopia/airlines/airports/{id}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteAirport(@PathVariable String id) {
        Airport check = airportService.getAirportById(id);
        if(check == null) {
            logger.info(id, "id does not exist");
            return new ResponseEntity<>("id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            airportService.deleteAirport(check);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

}
