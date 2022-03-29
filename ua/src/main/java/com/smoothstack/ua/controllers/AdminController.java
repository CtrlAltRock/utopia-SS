package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.security.jwt.JwtRequest;
import com.smoothstack.ua.security.jwt.JwtResponse;
import com.smoothstack.ua.security.jwt.JwtTokenUtil;
import com.smoothstack.ua.security.jwt.JwtUserDetailsService;
import com.smoothstack.ua.security.models.UserDTO;
import com.smoothstack.ua.services.AdminService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Autowired
    AdminService adminService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private User user;


    @Timed("authenticate")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        user = adminService.getUserByUsername(authenticationRequest.getUsername());
        adminService.setUser(user);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Timed("register")
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airplanes.dump")
    @RequestMapping(path = "utopia/airlines/airplanes/", method = RequestMethod.GET, produces = {"application/json", "application/xml", })
    public ResponseEntity<?> getAirplanes() {
        return new ResponseEntity<>(adminService.getAllAirplanes(), HttpStatus.OK);
    }

    @Timed("get.airplanes.id")
    @RequestMapping(path = "utopia/airlines/airplanes/{id}/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneById(@Valid @PathVariable Integer id) {
        ResponseEntity<?> out;
        Airplane airplane = adminService.getAirplaneById(id);

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
        ResponseEntity<?> out = null;
        AirplaneType airplaneType = airplane.getAirplaneType();
        AirplaneType sameType = adminService.getAirplaneTypeById(airplaneType.getId());
        logger.info(airplane.toString(), "airplane received");
        logger.info(sameType.toString(), "airplaneType in DB");

        //airplane type does not exist don't add
        if(sameType == null) {
            logger.info(sameType.toString());
            out = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else{

            //but does airplanetype received actually match the one in the DB
            if(!airplaneType.getMax_capacity().equals(sameType.getMax_capacity())){
                out = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            else {
                //it does match , get the saved airplane w/new id
                try{
                    airplane = adminService.saveAirplane(airplane);
                    out = new ResponseEntity<>(airplane, HttpStatus.OK);
                } catch (Exception e) {
                    logger.info(e.toString(), "saveAirplane");
                }
            }
        }
        return out;
    }

    @Timed("put.airplanes")
    @RequestMapping(path = "utopia/airlines/airplanes/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirplane(@RequestBody Airplane airplane, @PathVariable Integer id) {
        ResponseEntity<?> out = null;
        logger.info(airplane.toString(), "airplane recieved" );
        logger.info(id.toString(), "id received to modify");
        Airplane check = adminService.getAirplaneById(id);
        if(check == null) {
            logger.info("airplane id does not exist");
            return new ResponseEntity<>("airplane id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            AirplaneType sameType = adminService.getAirplaneTypeById(airplane.getAirplaneType().getId());
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
                    airplane.setId(id);
                    logger.info(airplane.toString(),id.toString(), "put airplane request, airplane, id");
                    Airplane updated = adminService.updateAirplane(airplane);
                    return new ResponseEntity<>(updated, HttpStatus.OK);
                }
            }
        }
    }


    @Timed("delete.airplane")
    @RequestMapping(path = "utopia/airlines/airplanes/{id}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAirplaneById(@PathVariable Integer id) {
        Airplane toDelete = adminService.getAirplaneById(id);
        toDelete.setAirplaneType(null);
        if(toDelete == null) {
            logger.info("airplane id to delete does not exist");
            return new ResponseEntity<>("airplane id to delete does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(toDelete.toString(), "airplane to delete");
            adminService.deleteAirplaneById(id);
            return new ResponseEntity<>("deleting " + toDelete.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airplaneTypes.dump")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneTypes() {
        logger.info("getting all airplane types");
        List<AirplaneType> airplaneTypes = adminService.getALLAirplaneTypes();
        return new ResponseEntity<>(airplaneTypes, HttpStatus.OK);
    }

    @Timed("get.airplaneTypes")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirplaneTypeById(@Valid @PathVariable Integer id) {
        AirplaneType airplaneType = adminService.getAirplaneTypeById(id);
        logger.info(id.toString(), "id of airplane type to get");
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
        logger.info(airplaneType.toString(), "airplane type to be posted");
        AirplaneType posted = adminService.saveAirplaneType(airplaneType);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }


    @Timed("put.airplaneType")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirplaneType(@RequestBody AirplaneType airplaneType, @PathVariable Integer id) {
        logger.info(id.toString(), "id of airplane type to be changed");
        logger.info(airplaneType.toString(), "airplane type to change to");
        AirplaneType sameType = adminService.getAirplaneTypeById(id);
        if(sameType == null) {
            return new ResponseEntity<>("airplane type id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            airplaneType.setId(id);
            AirplaneType put = adminService.saveAirplaneType(airplaneType);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.airplaneType")
    @RequestMapping(path = "utopia/airlines/airplaneTypes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAirplaneTypeId(@PathVariable Integer id) {
        logger.info(id.toString(), "id of airplane type to delete");
        AirplaneType toDelete = adminService.getAirplaneTypeById(id);
        if(toDelete == null) {
            logger.info("id for airplane type does not exist");
            return new ResponseEntity<>("id for airplane type does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(toDelete.toString(), "airplane type to delete");
            adminService.deleteAirplaneTypeById(id);
            return new ResponseEntity<>("deleting airplane type " + toDelete.toString(), HttpStatus.BAD_REQUEST);
        }

    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.airports.dump")
    @RequestMapping(path = "utopia/airlines/airports/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<Airport> getAirports() {
        logger.info("getting all airports");
        return adminService.getAllAirports();
    }

    @Timed("get.airports.id")
    @RequestMapping(path = "utopia/airlines/airports/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getAirportById(@PathVariable String id) {
        logger.info(id, "Id of airport to get");
        Airport check = adminService.getAirportById(id);
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
    public ResponseEntity<?> postAirport(@RequestBody Airport airport) {
        logger.info(airport.toString(), "airport to post");
        Airport check = adminService.getAirportById(airport.getIata_id());
        if(check == null) {
            logger.info(airport.toString(), "airport does not exist and can be posted");
            Airport posted = adminService.saveAirport(airport);
            return new ResponseEntity<>(airport, HttpStatus.OK);
        }
        else{
            logger.info("airport already exists");
            return new ResponseEntity<>("airport already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("post.airport")
    @RequestMapping(path = "utopia/airlines/airports/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putAirport(@RequestBody Airport airport, @PathVariable String id) {
        logger.info(id, "airport that is being updated");
        logger.info(airport.toString(), "airport to change to ");
        Airport put = adminService.getAirportById(id);
        if(put == null) {
            logger.info("airport id to update does not exist");
            return new ResponseEntity<>("airport id to update does not exist\n" + airport.toString() + "\n" + id, HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("airport id exists going to update city");
            airport.setIata_id(id);
            Airport posted = adminService.saveAirport(airport);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }


    @Timed("delete.airport")
    @RequestMapping(path = "utopia/airlines/airport/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteAirport(@PathVariable String id) {
        logger.info(id, "airport to delete");
        Airport check = adminService.getAirportById(id);
        if(check == null) {
            logger.info(id, "id does not exist");
            return new ResponseEntity<>("id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            adminService.deleteAirport(check);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookings.dump")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookings() {
        return new ResponseEntity<>(adminService.getAllBookings(), HttpStatus.OK);
    }

    @Timed("get.bookings.id")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookings(@PathVariable Integer id) {
        logger.info(id.toString(), "booking id to find");
        Booking check = adminService.getBookingsById(id);

        if(check == null) {
            logger.info("booking does not exist");
            return new ResponseEntity<>("booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.bookings")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookings(@RequestBody Booking booking) {
        logger.info(booking.toString(), "booking to post");
        Booking posted = adminService.saveBooking(booking);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @Timed("post.booking")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBooking(@RequestBody Booking booking, @PathVariable Integer id) {
        logger.info(id.toString(), "id of booking to update");
        logger.info(booking.toString(), "booking update");

        Booking check = adminService.getBookingsById(id);
        if(check == null) {
            logger.info("id of booking does not exist");
            return new ResponseEntity<>("id of booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking exists");
            check.setIs_active(booking.getIs_active());
            check.setConfirmation_code(booking.getConfirmation_code());
            Booking put = adminService.saveBooking(check);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.booking")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBookingById(@PathVariable Integer id) {
        logger.info(id.toString(), "id of booking to delete");
        Booking check = adminService.getBookingsById(id);

        if(check == null) {
            logger.info("booking does not exist");
            return new ResponseEntity<>("booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking does exist");
            adminService.deleteBookingById(id);
            return new ResponseEntity<>(check, HttpStatus.OK);
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingAgents.dump")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingAgents() {
        return new ResponseEntity<>(adminService.getALlBookingAgents(), HttpStatus.OK);
    }

    @Timed("get.bookingAgents.id")
    @RequestMapping(path = "utopia/airlines/bookingAgents/{bookingId}/{userId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingAgentById(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingId.toString(), "booking id argument");
        logger.info(userId.toString(), "user id argument");
        BookingAgentId bookingAgentId = new BookingAgentId(bookingId, userId);
        BookingAgent bookingAgent = adminService.getBookingAgentById(bookingAgentId);
        if(bookingAgent == null) {
            logger.info("booking agent does not exist");
            return new ResponseEntity<>("booking agent does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(bookingAgent.toString(), "booking agent returned");
            return new ResponseEntity<>(bookingAgent, HttpStatus.OK);
        }
    }


    @Timed("post.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookingAgents(@RequestBody BookingAgent bookingAgent) {
        logger.info(bookingAgent.toString(), "booking agent to post");
        BookingAgent check = adminService.getBookingAgentById(bookingAgent.getBookingAgentId());

        if(check == null) {
            logger.info("booking agent already exists");
            return new ResponseEntity<>("booking agent already exists", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking agent does not exist");
            BookingAgent posted = adminService.saveBookingAgent(bookingAgent);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }

/*
    @Timed("post.bookingAgent")
    @RequestMapping(path = "utopia/airlines/bookingAgent/{bookingId}/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingAgent(@RequestBody BookingAgent bookingAgent, @PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingAgent.toString(), "booking agent to update");
        logger.info(bookingId.toString(), "booking id to update");
        logger.info(userId.toString(), "user id to update");
        BookingAgentId bookingAgentId = new BookingAgentId(bookingId, userId);
        BookingAgent toUpdate = adminService.getBookingAgentById(bookingAgentId);

        if(toUpdate == null) {
            logger.info("booking agent does not exist");
            return new ResponseEntity<>("booking agent does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking agent to change from does exist");
            BookingAgent updated = adminService.saveBookingAgent(bookingAgent);

        }
    }
*/

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgents(@RequestBody List<BookingAgent> bookingAgents) { adminService.deleteBookingAgents(bookingAgents); }

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgent/{id}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgent(@RequestBody BookingAgent bookingAgent) { adminService.deleteBookingAgent(bookingAgent); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingGuests.dump")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<BookingGuest> getBookingGuests() { return adminService.getAllBookingGuests(); }

    @Timed("post.bookingGuests")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { adminService.saveBookingGuests(bookingGuests); }

    @Timed("post.bookingGuest")
    @RequestMapping(path = "utopia/airlines/bookingGuest/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public void saveBookingGuest(@RequestBody BookingGuest bookingGuest) { adminService.saveBookingGuest(bookingGuest); }

    @Timed("delete.bookingGuests")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { adminService.deleteBookingGuests(bookingGuests); }

    @Timed("delete.bookingGuest")
    @RequestMapping(path = "utopia/airlines/bookingGuest/{id}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuest(@RequestBody BookingGuest bookingGuest) { adminService.deleteBookingGuest(bookingGuest); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingPayments.dump")
    @RequestMapping(path = "utopia/airlines/bookingPayments/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingPayments() {
        return new ResponseEntity<>(adminService.getAllBookingPayments(), HttpStatus.OK);
    }

    @Timed("get.bookingPayments.id")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingPaymentsById(@PathVariable Integer bookingId) {
        logger.info(bookingId.toString(), "bookingI id argument");
        BookingPaymentId bookingPaymentId = new BookingPaymentId(bookingId);
        BookingPayment bookingPayment  = adminService.getBookingPaymentById(bookingId);
        if(bookingPayment == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking payment id does exist");
            return new ResponseEntity<>(bookingPayment, HttpStatus.OK);
        }
    }
    @Timed("post.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postBookingPayments(@RequestBody BookingPayment bookingPayment) {
        logger.info(bookingPayment.toString(), "booking payment argument");
        BookingPayment check = adminService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id());
        if(check == null) {
            logger.info("booking payment id doesn't exist");
            BookingPayment posted = adminService.saveBookingPayment(bookingPayment);
            return new ResponseEntity<>(posted, HttpStatus.OK);

        }
        else{
            logger.info("booking payment id already exists");
            return new ResponseEntity<>("booking payment id already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingPaymentId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingPayments(@RequestBody BookingPayment bookingPayment, @PathVariable Integer bookingPaymentId) {
        logger.info(bookingPayment.toString(), "booking payment argument");
        logger.info(bookingPaymentId.toString(), "bookingPaymentId argument to change");

        BookingPayment check = adminService.getBookingPaymentById(bookingPaymentId);

        if(check == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking payment id does exist");
            BookingPaymentId putBookingPaymentId = new BookingPaymentId(bookingPaymentId);
            bookingPayment.setBookingPaymentId(putBookingPaymentId);
            BookingPayment put = adminService.saveBookingPayment(bookingPayment);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingPaymentId}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteBookingPayments(@PathVariable Integer bookingPaymentId) {
        logger.info(bookingPaymentId.toString(), "booking payment id argument");
        BookingPayment check = adminService.getBookingPaymentById(bookingPaymentId);

        if(check == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking payment id does exist");
            adminService.deleteBookingPaymentById(new BookingPaymentId(bookingPaymentId));
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.bookingUsers.dump")
    @RequestMapping(path = "utopia/airlines/bookingUsers/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingUsers() {
        List<BookingUser> bookingUsers = adminService.getAllBookingUsers();
        logger.info(bookingUsers.toString(),"getting all bookingUsers");
        return new ResponseEntity<>(bookingUsers, HttpStatus.OK);
    }

    @Timed("get.bookingUsers.id")
    @RequestMapping(path = "utopia/airlines/bookingUsers/{bookingId}/{userId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingUserById(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingId.toString(), "bookingId argument");
        logger.info(userId.toString(), "userId argument");
        BookingUser check = adminService.getBookingUserById(new BookingUserId(bookingId, userId));
        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking user does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUsers/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postBookingUsers(@RequestBody BookingUser bookingUser) {
        logger.info(bookingUser.toString(), "booking user argument");
        BookingUser check = adminService.getBookingUserById(bookingUser.getBookingUserId());
        if(check == null) {
            logger.info("booking user does not exist");
            BookingUser posted = adminService.saveBookingUser(bookingUser);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
        else{
            logger.info("booking user already exists");
            return new ResponseEntity<>("booking user already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUser/{bookingId}/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingUser(@RequestBody BookingUser bookingUser, @PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingUser.toString(), "booking user argument");
        logger.info(bookingId.toString(), "booking id argument");
        logger.info(userId.toString(), "user id argument");

        BookingUser check = adminService.getBookingUserById(new BookingUserId(bookingId, userId));

        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking user does exist");
            bookingUser.setBookingUserId(new BookingUserId(bookingId, userId));
            BookingUser put = adminService.saveBookingUser(bookingUser);
            return new ResponseEntity<>(put, HttpStatus.OK);

        }
    }

    @Timed("delete.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUser/{bookingId}/{userId}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteBookingUser(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingId.toString(), "booking id argument");
        logger.info(userId.toString(), ("user id argument"));
        BookingUser check = adminService.getBookingUserById(new BookingUserId(bookingId, userId));
        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking user does exist");
            adminService.deleteBookingUser(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.flights.dump")
    @RequestMapping(path = "utopia/airlines/flights/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlights() {
        List<Flight> flights = adminService.getFlights();
        logger.info(flights.toString(), "getting all flights");
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Timed("get.flights.id")
    @RequestMapping(path = "utopia/airlines/flights/{flightId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightById(@PathVariable Integer flightId) {
        logger.info(flightId.toString(), "flight id argument");
        Flight check = adminService.getFlightById(flightId);
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
        logger.info(flight.toString(), "flight argument");
        if(flight.getAirplane() == null || flight.getRoute() == null || flight.getId() != null) {
            logger.info("not accepting body argument");
            return new ResponseEntity<>("either airplane is null or route is null or an id has been supplied to the RequestBody", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("flight body seems alright");
            Route routeCheck = adminService.getRouteById(flight.getRoute().getId());
            Airplane airplaneCheck = adminService.getAirplaneById(flight.getAirplane().getId());
            if(routeCheck == null || airplaneCheck == null) {
                logger.info("flight is missing either a route and/or an airplane");
                return new ResponseEntity<>("flight is missing either a route and/or an airplane", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight is acceptable");
                Flight posted = adminService.saveFlight(flight);
                return new ResponseEntity<>(flight, HttpStatus.OK);
            }
        }
    }

    @Timed("put.flights")
    @RequestMapping(path = "utopia/airlines/flight/{flightId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putFlight(@RequestBody Flight flight, @PathVariable Integer flightId) {
        logger.info(flight.toString(), "flight argument");
        logger.info(flightId.toString(), "flight id argument");
        Flight check = adminService.getFlightById(flightId);
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
                Route routeCheck = adminService.getRouteById(flight.getRoute().getId());
                Airplane airplaneCheck = adminService.getAirplaneById(flight.getAirplane().getId());
                if (routeCheck == null || airplaneCheck == null) {
                    logger.info("flight is missing either a route and/or an airplane");
                    return new ResponseEntity<>("flight is missing either a route and/or an airplane", HttpStatus.BAD_REQUEST);
                } else {
                    logger.info("flight is acceptable");
                    flight.setId(flightId);
                    Flight posted = adminService.saveFlight(flight);
                    return new ResponseEntity<>(flight, HttpStatus.OK);
                }
            }
        }
    }

    @Timed("delete.flights")
    @RequestMapping(path = "utopia/airlines/flight/{flightId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFlightById(@PathVariable Integer flightId) {
        logger.info(flightId.toString(), "flight id argument");
        Flight check = adminService.getFlightById(flightId);
        if(check == null) {
            logger.info("flight id does not exist");
            return new ResponseEntity<>("flight id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight id does exist");
            adminService.deleteFlightById(flightId);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.flightBookings.dump")
    @RequestMapping(path = "utopia/airlines/flightBookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightBookings() {
        List<FlightBookings> flightBookings = adminService.getFlightBookings();
        return new ResponseEntity<>(flightBookings, HttpStatus.OK);
    }

    @Timed("get.flightBookings.ids")
    @RequestMapping(path = "utopia/airlines/flightBookings/{flightId}/{bookingId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightBookingsById(@PathVariable Integer flightId, @PathVariable Integer bookingId) {
        logger.info(flightId.toString(), "flight id argument");
        logger.info(bookingId.toString(), "booking id argument");
        FlightBookings check = adminService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight booking does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight booking does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBookings/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postFlightBookings(@RequestBody FlightBookings flightBooking) {
        logger.info(flightBooking.toString(), "flight booking body argument");
        FlightBookings check = adminService.getFlightBookingsById(flightBooking.getFlightBookingsId());
        if(check == null) {
            logger.info("flight Booking does not exist");
            Flight flight = adminService.getFlightById(flightBooking.getFlightBookingsId().getFlight_id());
            Booking booking = adminService.getBookingsById(flightBooking.getFlightBookingsId().getBooking_id());
            if(flight == null || booking == null) {
                logger.info("Either flight and/or booking does not exist");
                return new ResponseEntity<>("either flight and/or booking does not exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight booking appears to be in order");
                FlightBookings posted = adminService.saveFlightBooking(flightBooking);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
        else{
            logger.info("flight booking composite id already exists");
            return new ResponseEntity<>("flight booking composite id already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBookings/{flightId}/{bookingId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putFlightBooking(@RequestBody FlightBookings flightBooking, @PathVariable Integer flightId, @PathVariable Integer bookingId) {
        logger.info(flightBooking.toString(), "flight booking argument");
        logger.info(flightId.toString(), "flight id argument");
        logger.info(bookingId.toString(), "booking id argument");

        FlightBookings check = adminService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight bookings does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            Flight flight = adminService.getFlightById(flightBooking.getFlightBookingsId().getFlight_id());
            Booking booking = adminService.getBookingsById(flightBooking.getFlightBookingsId().getBooking_id());
            if(flight == null || booking == null) {
                logger.info("Either flight and/or booking does not exist");
                return new ResponseEntity<>("either flight and/or booking does not exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight booking appears to be in order");
                FlightBookings posted = adminService.saveFlightBooking(flightBooking);
                adminService.deleteFlightBooking(check);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }

    }


    @Timed("delete.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBooking/{flightId}/{bookingId}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteFlightBookingsById(@PathVariable Integer flightId, @PathVariable Integer bookingId) {
        logger.info(flightId.toString(), "flight id argument");
        logger.info(bookingId.toString(), "booking id argument");
        FlightBookings check = adminService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight booking does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight booking does exist");
            adminService.deleteFlightBooking(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.passengers.dump")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getPassengers() {
        List<Passenger> passengers = adminService.getPassengers();
        logger.info(passengers.toString(), "passengers from db");
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @Timed("get.passengers.id")
    @RequestMapping(path = "utopia/airline/passengers/{passengerId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getPassengerById(@PathVariable Integer passengerId) {
        logger.info(passengerId.toString(), "passenger id argument");
        Passenger check = adminService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger does not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }


    @Timed("post.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postPassengers(@RequestBody Passenger passenger) {
        logger.info(passenger.toString(), "passenger argument");
        if(passenger.getBooking_id() == null || passenger.getId() != null) {
            logger.info("either booking is not provided and/or id has been provided");
            return new ResponseEntity<>("either booking is not provided and/or id has been provided", HttpStatus.BAD_REQUEST);
        }
        else {
            Booking check = adminService.getBookingsById(passenger.getBooking_id().getId());
            if(check == null) {
                logger.info("booking doesn't exist");
                return new ResponseEntity<>("booking doesn't exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("passenger seems good to post");
                Passenger posted = adminService.savePassenger(passenger);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("put.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putPassengers(@RequestBody Passenger passenger, @PathVariable Integer passengerId) {
        logger.info(passenger.toString(), "passenger argument");
        logger.info(passengerId.toString(), "passenger id argument");
        Passenger check = adminService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger does not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            if(check.getBooking_id().getId() != passenger.getBooking_id().getId()) {
                logger.info("checking updated booking id if it exists");
                Booking booking = adminService.getBookingsById(passenger.getBooking_id().getId());
                if(booking == null) {
                    logger.info("updated booking  does not exist");
                    return new ResponseEntity<>("booking being updated does not exist", HttpStatus.BAD_REQUEST);
                }
            }
            logger.info("passenger to update seems to be in order");
            passenger.setId(passengerId);
            Passenger posted = adminService.savePassenger(passenger);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }


    @Timed("delete.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePassengerById(@PathVariable Integer passengerId) {
        logger.info(passengerId.toString(), "passenger id argument");
        Passenger check = adminService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger doe not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            check.setBooking_id(null);
            Passenger update = adminService.savePassenger(check);
            adminService.deleteBookingById(check.getId());
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.routes.dump")
    @RequestMapping(path = "utopia/airlines/routes/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getRoutes() {
        logger.info("getting all routes");
        List<Route> routes = adminService.getRoutes();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @Timed("get.routes.id")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getRoutesById(@PathVariable Integer routeId) {
        logger.info(routeId.toString(), "route id argument");
        Route check = adminService.getRouteById(routeId);
        if(check == null) {
            logger.info("route does not exist");
            return new ResponseEntity<>("route does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("route does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.routes")
    @RequestMapping(path = "utopia/airlines/routes/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveRoutes(@RequestBody Route route) {
        logger.info(route.toString(), "route argument");
        Route check = adminService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id());
        if(check == null) {
            logger.info("route does not exist");
            Route posted = adminService.saveRoute(route);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
        else {
            logger.info("route already exists");
            return new ResponseEntity<>("route already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.routes")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveRoute(@RequestBody Route route) {
        logger.info(route.toString(),"route argument");
        if(route.getOriginAirport() == null || route.getDestinationAirport() == null) {
            logger.info("either origin airport is null and/or destination airport is null");
            return new ResponseEntity<>("either origin airport is null and/or destination airport is null", HttpStatus.BAD_REQUEST);
        }
        else{
            Route check = adminService.getRouteByOriginDestination(route.getOriginAirport().getIata_id(), route.getDestinationAirport().getIata_id());
            if(check == null) {
                logger.info("route does not exist");
                Route posted = adminService.saveRoute(route);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
            else {
                logger.info("route already exists");
                return new ResponseEntity<>("route already exists", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Timed("delete.route")
    @RequestMapping(path = "utopia/airlines/routes/{routeId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRouteById(@PathVariable Integer routeId) {
        logger.info(routeId.toString(), "route id argument");
        Route check = adminService.getRouteById(routeId);
        if(check == null) {
            logger.info("route does not exist");
            return new ResponseEntity<>("route does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("route does exist");
            check.setOriginAirport(null);
            check.setDestinationAirport(null);
            adminService.deleteRoute(check);
            return new ResponseEntity<>("deleting route " + routeId.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.users.dump")
    @RequestMapping(path = "utopia/airlines/users/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUsers() {
        logger.info("getting all users");
        List<User> users = adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Timed("get.users.id")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> getUsersById(@PathVariable Integer userId) {
        logger.info(userId.toString(), "user id argument");
        User check = adminService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.users")
    @RequestMapping(path = "utopia/airlines/users/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postUsers(@RequestBody User user) {
        logger.info(user.toString(), "user body argument");
        if(user.getId() != 0) {
            logger.info("user has an id other than zero");
            return new ResponseEntity<>("user has an id other than zero", HttpStatus.BAD_REQUEST);
        }
        else {
            UserRole userRoleCheck = adminService.getUserRoleById(user.getUserRole().getId());
            logger.info(userRoleCheck.toString(), "db role to check against");

            if(userRoleCheck == null) {
                logger.info("trying to change UserRole from user post");
                return new ResponseEntity<>("trying to change UserRole from user post", HttpStatus.BAD_REQUEST);
            }
            else if(!user.getUserRole().equals(userRoleCheck)){
                logger.info("trying to create a new user role");
                return new ResponseEntity<>("trying to create a new user role", HttpStatus.BAD_REQUEST);
            }
            else {
                logger.info("user seems fine to post");
                User posted = adminService.saveUser(user);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("put.users")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putUsers(@RequestBody User user, @PathVariable Integer userId) {
        logger.info(user.toString(), "user argument");
        logger.info(userId.toString(), "user id argument");
        User check = adminService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else if(user.getId() != 0) {
            logger.info("user has an id other than zero");
            return new ResponseEntity<>("user has an id other than zero", HttpStatus.BAD_REQUEST);
        }
        else{
            UserRole userRoleCheck = adminService.getUserRoleById(user.getUserRole().getId());
            if(userRoleCheck == null) {
                logger.info("user role does not exist");
                return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
            }
            else if(!userRoleCheck.equals(user.getUserRole())) {
                logger.info("not allowed to override an existing user role");
                return new ResponseEntity<>("not allowed to override an existing user role", HttpStatus.BAD_REQUEST);
            }
            else {
                logger.info("user seems fine to post");
                user.setId(userId.longValue());
                User posted = adminService.saveUser(user);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("delete.users")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsersById(@PathVariable Integer userId) {
        logger.info(userId.toString(), "user id argument");
        User check = adminService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            check.setUserRole(null);
            adminService.deleteUser(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Timed("get.userRoles.dump")
    @RequestMapping(path = "utopia/airlines/userRoles/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUserRoles() {
        logger.info("getting all user roles");
        List<UserRole> roles = adminService.getAllUserRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Timed("get.userRoles.id")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUserRolesById(@PathVariable Integer userRoleId) {
        logger.info(userRoleId.toString(), "user role id argument");
        UserRole check = adminService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user role does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postUserRoles(@RequestBody UserRole userRole) {
        logger.info(userRole.toString(), "user role argument");
        UserRole check = adminService.getUserRoleByName(userRole.getName());
        if(check != null) {
            logger.info("user role already exists");
            return new ResponseEntity<>("user role already exists", HttpStatus.BAD_REQUEST);
        }
        else if(userRole.getId() != Integer.valueOf(0).longValue()) {
            logger.info("user role id has been included or is not zero");
            return new ResponseEntity<>("user role id has been included or is not zero", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user role does not exist");
            UserRole posted = adminService.saveUserRole(userRole);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }

    @Timed("put.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putUserRoles(@RequestBody UserRole userRole, @PathVariable Integer userRoleId) {
        logger.info(userRole.toString(), "user role argument");
        logger.info(userRoleId.toString(), "user role id argument");
        UserRole check = adminService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else if(userRole.getId().intValue() != 0) {
            logger.info("user role has an id inluded");
            return new ResponseEntity<>("user role has id included", HttpStatus.BAD_REQUEST);
        }
        else {
            userRole.setId(userRoleId.longValue());
            UserRole put = adminService.saveUserRole(userRole);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserRole(@PathVariable Integer userRoleId) {
        logger.info(userRoleId.toString(), "user role id argument");
        UserRole check = adminService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            adminService.deleteUserRoleById(userRoleId.longValue());
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
