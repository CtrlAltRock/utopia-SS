package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.AirplaneType;
import com.smoothstack.ua.services.AirplaneTypeService;
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
public class AirplaneTypeController {

    private Logger logger = LoggerFactory.getLogger(AirplaneTypeController.class);

    @Autowired
    private AirplaneTypeService airplaneTypeService;

    @Timed("get.airplaneTypes.dump")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneTypes() {
        List<AirplaneType> airplaneTypes = airplaneTypeService.getAllAirplaneTypes();
        return new ResponseEntity<>(airplaneTypes, HttpStatus.OK);
    }

    @Timed("get.airplaneTypes")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{airplaneTypeId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneTypeById(@Valid @PathVariable Integer airplaneTypeId) {
        AirplaneType airplaneType = airplaneTypeService.getAirplaneTypeById(airplaneTypeId);
        if(airplaneType == null) {
            logger.info("airplane type id does not exist");
            return new ResponseEntity<>("airplane type id does not exist", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity<>(airplaneType, HttpStatus.OK);
    }

    /* Not checking if an airplane max_capacity already exists, admin should be aware */
    @Timed("post.airplaneTypes")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveAirplaneTypes(@RequestBody AirplaneType airplaneType) {
        AirplaneType posted = airplaneTypeService.saveAirplaneType(airplaneType);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }


    @Timed("put.airplaneType")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{airplaneTypeId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirplaneType(@RequestBody AirplaneType airplaneType, @PathVariable Integer airplaneTypeId) {
        AirplaneType sameType = airplaneTypeService.getAirplaneTypeById(airplaneTypeId);
        if(sameType == null) {
            return new ResponseEntity<>("airplane type id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            airplaneType.setId(airplaneTypeId);
            AirplaneType put = airplaneTypeService.saveAirplaneType(airplaneType);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.airplaneType")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{airplaneTypeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAirplaneTypeId(@PathVariable Integer airplaneTypeId) {
        AirplaneType toDelete = airplaneTypeService.getAirplaneTypeById(airplaneTypeId);
        if(toDelete == null) {
            logger.info("id for airplane type does not exist");
            return new ResponseEntity<>("id for airplane type does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(toDelete.toString(), "airplane type to delete");
            airplaneTypeService.deleteAirplaneTypeById(airplaneTypeId);
            return new ResponseEntity<>("deleting airplane type " + toDelete.toString(), HttpStatus.BAD_REQUEST);
        }

    }
}
