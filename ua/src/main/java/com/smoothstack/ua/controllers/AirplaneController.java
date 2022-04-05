package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Airplane;
import com.smoothstack.ua.models.AirplaneType;
import com.smoothstack.ua.services.AirplaneService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class AirplaneController {

    private Logger logger = LoggerFactory.getLogger(AirplaneController.class);

    @Autowired
    private AirplaneService airplaneService;

    @Timed("get.airplanes.dump")
    @RequestMapping(path = "utopia/airlines/airplanes/", method = RequestMethod.GET, produces = {"application/json", "application/xml", })
    public ResponseEntity<?> getAirplanes() {
        return new ResponseEntity<>(airplaneService.getAllAirplanes(), HttpStatus.OK);
    }

    @Timed("get.airplanes.id")
    @RequestMapping(path = "utopia/airlines/airplanes/{airplaneId}/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneById(@Valid @PathVariable Integer airplaneId) {
        ResponseEntity<?> out;
        Airplane airplane = airplaneService.getAirplaneById(airplaneId);

        if (airplane == null){
            out = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else out = new ResponseEntity<>(airplane, HttpStatus.OK);
        return out;
    }

    /*Posting of an airplane POJO, if a new airplane type is included do not allow*/
    @Timed("post.airplanes")
    @RequestMapping(path = "utopia/airlines/airplanes/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveAirplane(@RequestBody Airplane airplane) {
        if(airplane.getAirplaneType().getId() == null) {
            logger.info("airplane type id not provided");
            return new ResponseEntity<>("airplane type id not provided", HttpStatus.BAD_REQUEST);
        }

        AirplaneType airplaneType = airplane.getAirplaneType();
        AirplaneType sameType = airplaneService.getAirplaneTypeById(airplaneType.getId());
        //airplane type does not exist don't add
        if(sameType == null) {
            logger.info(sameType.toString());
            return new ResponseEntity<>("airplane type does not exist", HttpStatus.BAD_REQUEST);
        }
        else{

            //but does airplanetype received actually match the one in the DB
            if(!airplaneType.getMax_capacity().equals(sameType.getMax_capacity())){
                return new ResponseEntity<>("airplane type does not match", HttpStatus.BAD_REQUEST);
            }
            else {
                //it does match , get the saved airplane w/new id
                airplane = airplaneService.saveAirplane(airplane);
                return new ResponseEntity<>(airplane, HttpStatus.OK);
            }
        }
    }

    @Timed("put.airplanes")
    @RequestMapping(path = "utopia/airlines/airplanes/{airplaneId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirplane(@RequestBody Airplane airplane, @PathVariable Integer airplaneId) {
        ResponseEntity<?> out = null;
        Airplane check = airplaneService.getAirplaneById(airplaneId);
        if(check == null) {
            logger.info("airplane id does not exist");
            return new ResponseEntity<>("airplane id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            AirplaneType sameType = airplaneService.getAirplaneTypeById(airplane.getAirplaneType().getId());
            if(sameType == null) {
                logger.info("airplane type to change to does not exist");
                return new ResponseEntity<>("airplane type to change to does not exist", HttpStatus.BAD_REQUEST);
            }
            else {
                if(!airplane.getAirplaneType().getMax_capacity().equals(sameType.getMax_capacity())) {
                    logger.info("airplane type to change to does not have the right max_capacity");
                    return new ResponseEntity<>("airplane type to change to does not have the right max_capacity", HttpStatus.BAD_REQUEST);
                }
                else{
                    airplane.setId(airplaneId);
                    logger.info(airplane.toString(), airplaneId.toString(), "put airplane request, airplane, id");
                    Airplane updated = airplaneService.updateAirplane(airplane);
                    return new ResponseEntity<>(updated, HttpStatus.OK);
                }
            }
        }
    }


    @Timed("delete.airplane")
    @RequestMapping(path = "utopia/airlines/airplanes/{airplaneId}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAirplaneById(@PathVariable Integer airplaneId) {
        Airplane toDelete = airplaneService.getAirplaneById(airplaneId);
        toDelete.setAirplaneType(null);
        if(toDelete == null) {
            logger.info("airplane id to delete does not exist");
            return new ResponseEntity<>("airplane id to delete does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(toDelete.toString(), "airplane to delete");
            airplaneService.deleteAirplaneById(airplaneId);
            return new ResponseEntity<>("deleting " + toDelete.toString(), HttpStatus.OK);
        }
    }

}
