package com.smoothstack.ua.services;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirplaneTypeRepository airplaneTypeRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingAgentRepository bookingAgentRepository;

    @Autowired
    BookingGuestRepository bookingGuestRepository;

    @Autowired
    BookingPaymentRepository bookingPaymentRepository;

    @Autowired
    BookingUserRepository bookingUserRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightBookingsRepository flightBookingsRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserByUsername(String username) { return userRepository.findUserByUsername(username).get(); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Airplane> getAllAirplanes() {
        logger.info("getting all airplanes");
        return (List<Airplane>) airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(Integer id) {
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        if(airplane.isPresent()) return airplane.get();
        else return null;
    }

    public List<Airplane> getAirplaneByAirplaneTypeId(Integer airplane_type_id) { return airplaneRepository.findByTypeId(airplane_type_id); }

    public List<Airplane> getAirplaneByMaxCapacity(Integer max_capacity) { return airplaneRepository.findByMaxCapacity(max_capacity); }

    public void saveAirplanes(List<Airplane> airplanes) {
        airplaneRepository.saveAll(airplanes);
    }

    public Airplane updateAirplane(Airplane airplane) { Airplane updated = airplaneRepository.save(airplane);
        return updated;
    }

    public Airplane saveAirplane(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    public void deleteAirplanes(List<Airplane> airplanes) { airplaneRepository.deleteAll(airplanes); }

    public void deleteAirplane(Airplane airplane) { airplaneRepository.delete(airplane); }

    public void deleteAirplaneById(Integer airplaneId) { airplaneRepository.deleteById(airplaneId); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<AirplaneType> getALLAirplaneTypes() {
        return (List<AirplaneType>) airplaneTypeRepository.findAll();
    }

    public AirplaneType getAirplaneTypeById(Integer id) {
        Optional<AirplaneType> airplaneType = airplaneTypeRepository.findById(id);
        if(airplaneType.isPresent()) return airplaneType.get();
        return null;
    }

    public void saveAirplaneTypes(List<AirplaneType> airplaneTypes) {
        airplaneTypeRepository.saveAll(airplaneTypes);
    }

    public AirplaneType saveAirplaneType(AirplaneType airplaneType) { AirplaneType posted = airplaneTypeRepository.save(airplaneType);
        return posted;
    }

    public void deleteAirplaneTypes(List<AirplaneType> airplaneTypes) { airplaneTypeRepository.deleteAll(airplaneTypes);}

    public void deleteAirplaneTypeById(Integer id) { airplaneTypeRepository.deleteById(id); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public void deleteAirports(List<Airport> airports) { airportRepository.deleteAll(airports); }

    public void deleteAirportById(String id) { airportRepository.deleteById(id); }

    public void deleteAirport(Airport airport) {
        airportRepository.delete(airport);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public Booking getBookingsById(Integer bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()) {
            return booking.get();
        }
        else return null;
    }

    public void saveBookings(List<Booking> bookings) {
        bookingRepository.saveAll(bookings);
    }

    public Booking saveBooking(Booking booking) {
        Booking posted = bookingRepository.save(booking);
        return posted;
    }

    public void deleteBookings(List<Booking> bookings) { bookingRepository.deleteAll(bookings); }

    public void deleteBookingById(Integer id) { bookingRepository.deleteById(id); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookingAgent> getALlBookingAgents() { return (List<BookingAgent>) bookingAgentRepository.findAll(); }

    public BookingAgent getBookingAgentById(BookingAgentId bookingAgentId) {
        Optional<BookingAgent> bookingAgent = bookingAgentRepository.findById(bookingAgentId);
        if(bookingAgent.isPresent()) {
            return bookingAgent.get();
        }
        else return null;
    }

    public void saveBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.saveAll(bookingAgents); }

    public BookingAgent saveBookingAgent(BookingAgent bookingAgent) {
        BookingAgent posted = bookingAgentRepository.save(bookingAgent);
        return posted;
    }

    public void deleteBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.deleteAll(bookingAgents); }

    public void deleteBookingAgent(BookingAgent bookingAgent) { bookingAgentRepository.delete(bookingAgent); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookingGuest> getAllBookingGuests() { return (List<BookingGuest>) bookingGuestRepository.findAll(); }

    public void saveBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.saveAll(bookingGuests); }

    public void saveBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.save(bookingGuest);}

    public void deleteBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.deleteAll(bookingGuests); }

    public void deleteBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.delete(bookingGuest); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookingPayment> getAllBookingPayments() { return (List<BookingPayment>) bookingPaymentRepository.findAll(); }

    public BookingPayment getBookingPaymentById(Integer bookingId) {
        Optional<BookingPayment> bookingPayment = bookingPaymentRepository.findById(new BookingPaymentId(bookingId));
        if(bookingPayment.isPresent()) {
            return bookingPayment.get();
        }
        else return null;
    }

    public void saveBookingPayments(List<BookingPayment> bookingPayments) { bookingPaymentRepository.saveAll(bookingPayments); }

    public BookingPayment saveBookingPayment(BookingPayment bookingPayment) {
        BookingPayment posted = bookingPaymentRepository.save(bookingPayment);
        return posted;
    }

    public void deleteBookingPayments(List<BookingPayment> bookingPayments) { bookingPaymentRepository.deleteAll(bookingPayments); }

    public void deleteBookingPayment(BookingPayment bookingPayment) { bookingPaymentRepository.delete(bookingPayment); }

    public void deleteBookingPaymentById(BookingPaymentId bookingPaymentId) {
        bookingPaymentRepository.deleteById(bookingPaymentId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<BookingUser> getAllBookingUsers() {
        return (List<BookingUser>) bookingUserRepository.findAll();
    }

    public BookingUser getBookingUserById(BookingUserId bookingUserId) {
        Optional<BookingUser> bookingUser = bookingUserRepository.findById(bookingUserId);
        if(bookingUser.isPresent()) {
            return bookingUser.get();
        }
        else return null;
    }

    public void saveBookingUsers(List<BookingUser> bookingUsers) {
        bookingUserRepository.saveAll(bookingUsers);
    }

    public BookingUser saveBookingUser(BookingUser bookingUser) {
        BookingUser posted = bookingUserRepository.save(bookingUser);
        return posted;
    }

    public void deleteBookingUsers(List<BookingUser> bookingUsers) { bookingUserRepository.deleteAll(bookingUsers); }

    public void deleteBookingUser(BookingUser bookingUser) { bookingUserRepository.delete(bookingUser); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<FlightBookings> getFlightBookings() { return (List<FlightBookings>) flightBookingsRepository.findAll(); }

    public FlightBookings getFlightBookingsById(FlightBookingsId flightBookingId) {
        Optional<FlightBookings> flightBooking = flightBookingsRepository.findById(flightBookingId);
        if(flightBooking.isPresent()) {
            return flightBooking.get();
        }
        else return null;
    }

    public void saveFlightBookings(List<FlightBookings> flightBookings) { flightBookingsRepository.saveAll(flightBookings); }

    public FlightBookings saveFlightBooking(FlightBookings flightBooking) {
        FlightBookings posted = flightBookingsRepository.save(flightBooking);
        return posted;
    }

    public void deleteFlightBookings(List<FlightBookings> flightBookings) { flightBookingsRepository.deleteAll(flightBookings); }

    public void deleteFlightBooking(FlightBookings flightBookings) { flightBookingsRepository.delete(flightBookings); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Passenger> getPassengers() {
        return (List<Passenger>) passengerRepository.findAll();
    }

    public Passenger getPassengerById(Integer passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if(passenger.isPresent()) return passenger.get();
        else return null;
    }

    public List<Passenger> getPassengersByBookingId(Integer bookingId) { return passengerRepository.findByBookingId(bookingId); }

    public List<Passenger> getPassengersByFamilyName(String familyName) { return passengerRepository.findByFamilyName(familyName); }

    public void savePassengers(List<Passenger> passengers) {
        for(Passenger passenger: passengers) {
            savePassenger(passenger);
        }
    }

    public Passenger savePassenger(Passenger passenger) {
        FlightBookings flightBookings = flightBookingsRepository.findFlightBookingsByBookingId(passenger.getBooking_id().getId());
        Flight flight = flightRepository.findById(flightBookings.getFlightBookingsId().getFlight_id()).get();
        if(flight != null) {
            if(flight.getReserved_seats() > 0) {
                flight.setReserved_seats(flight.getReserved_seats()-1);
                flightRepository.save(flight);
                passenger = passengerRepository.save(passenger);
            }
            else throw new IllegalArgumentException("Not enough seats to add to");
        }
        else throw new IllegalArgumentException("No flights for passenger's booking id");
        return passenger;
    }

    public void deletePassengers(List<Passenger> passengers) { passengerRepository.deleteAll(passengers); }
    public void deletePassenger(Passenger passenger) {
        passengerRepository.delete(passenger);
    }

    public void deletePassengerById(Integer id) {
        Flight flight = flightRepository.findFlightByPassenger(id);
        if(flight.getReserved_seats() + 1 <= flight.getAirplane().getAirplaneType().getMax_capacity()){
            flight.setReserved_seats(flight.getReserved_seats() + 1);
            flightRepository.save(flight);
        }
        passengerRepository.deleteById(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Route> getRoutes() { return (List<Route>) routeRepository.findAll(); }

    public Route getRouteById(Integer id) {
        Optional<Route> route = routeRepository.findById(id);
        if(route.isPresent()) {
            return route.get();
        }
        else return null;
    }

    public Route getRouteByOriginDestination(String origin, String destination) {
        Optional<Route> route = routeRepository.findByOriginDestination(origin, destination);
        if(route.isPresent()) {
            return route.get();
        }
        else return null;
    }

    public void saveRoutes(List<Route> routes) { routeRepository.saveAll(routes); }

    public Route saveRoute(Route route) {
        Route posted = routeRepository.save(route);
        return posted;
    }

    public void deleteRoute(Route route) {
        routeRepository.delete(route);
    }

    public void deleteRouteById(Integer id) {
        routeRepository.deleteById(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<User> getAllUsers() { return (List<User>) userRepository.findAll(); }

    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId.longValue());
        if(user.isPresent()) {
            return user.get();
        }
        else return null;
    }

    public void saveUsers(List<User> users) {
        for (User user: users) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.saveAll(users);
    }

    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User posted = userRepository.save(user);
        return posted;
    }

    public void deleteUsers(List<User> users) { userRepository.deleteAll(users); }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUserById(Long id) { userRepository.deleteById(id); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<UserRole> getAllUserRoles() { return (List<UserRole>) userRoleRepository.findAll(); }

    public UserRole getUserRoleById(Long userRoleId) {
        Optional<UserRole> userRole = userRoleRepository.findById(userRoleId);
        if(userRole.isPresent()) {
            return userRole.get();
        }
        else return null;
    }

    public UserRole getUserRoleByName(String name) {
        Optional<UserRole> userRole = userRoleRepository.findByName(name);
        if(userRole.isPresent()) {
            return userRole.get();
        }
        else return null;
    }

    public void saveUserRoles(List<UserRole> userRoles) { userRoleRepository.saveAll(userRoles); }

    public UserRole saveUserRole(UserRole userRole) {
        UserRole posted = userRoleRepository.save(userRole);
        return posted;
    }

    public void deleteUserRoles(List<UserRole> userRoles) { userRoleRepository.deleteAll(userRoles); }

    public void deleteUserRoleById(Long id) { userRoleRepository.deleteById(id); }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
