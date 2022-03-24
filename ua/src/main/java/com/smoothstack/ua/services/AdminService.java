package com.smoothstack.ua.services;

import com.smoothstack.ua.models.*;
import com.smoothstack.ua.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

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

    public List<Airplane> getAllAirplanes() {
        return (List<Airplane>) airplaneRepository.findAll();
    }
    public Airplane getAirplaneById(Integer id) { return airplaneRepository.findById(id).get(); }
    public List<Airplane> getAirplaneByAirplaneTypeId(Integer airplane_type_id) { return airplaneRepository.findByTypeId(airplane_type_id); }
    public List<Airplane> getAirplaneByMaxCapacity(Integer max_capacity) { return airplaneRepository.findByMaxCapacity(max_capacity); }
    public void saveAirplanes(List<Airplane> airplanes) {
        airplaneRepository.saveAll(airplanes);
    }
    public void saveAirplane(Airplane airplane) { airplaneRepository.save(airplane); }
    public void deleteAirplanes(List<Airplane> airplanes) { airplaneRepository.deleteAll(airplanes); }
    public void deleteAirplane(Airplane airplane) { airplaneRepository.delete(airplane); }
    public void deleteAirplaneById(Integer airplaneId) { airplaneRepository.deleteById(airplaneId); }

    public List<AirplaneType> getALLAirplaneTypes() {
        return (List<AirplaneType>) airplaneTypeRepository.findAll();
    }
    public void saveAirplaneTypes(List<AirplaneType> airplaneTypes) {
        airplaneTypeRepository.saveAll(airplaneTypes);
    }
    public void saveAirplaneType(AirplaneType airplaneType) { airplaneTypeRepository.save(airplaneType); }
    public void deleteAirplaneTypes(List<AirplaneType> airplaneTypes) { airplaneTypeRepository.deleteAll(airplaneTypes);}
    public void deleteAirplaneTypeById(Integer id) { airplaneTypeRepository.deleteById(id); }

    public List<Airport> getAllAirports() {
        return (List<Airport>) airportRepository.findAll();
    }
    public void saveAirports(List<Airport> airports) {
        airportRepository.saveAll(airports);
    }
    public void saveAirport(Airport airport) { airportRepository.save(airport); }
    public void deleteAirports(List<Airport> airports) { airportRepository.deleteAll(airports); }
    public void deleteAirportById(Integer id) { airportRepository.deleteById(id); }
    public void deleteAirport(Airport airport) {
        airportRepository.delete(airport);
    }

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }
    public void saveBookings(List<Booking> bookings) {
        bookingRepository.saveAll(bookings);
    }
    public void saveBooking(Booking booking) { bookingRepository.save(booking);   }
    public void deleteBookings(List<Booking> bookings) { bookingRepository.deleteAll(bookings); }
    public void deleteBookingById(Integer id) { bookingRepository.deleteById(id); }

    public List<BookingAgent> getALlBookingAgents() { return (List<BookingAgent>) bookingAgentRepository.findAll(); }
    public void saveBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.saveAll(bookingAgents); }
    public void saveBookingAgent(BookingAgent bookingAgent) { bookingAgentRepository.save(bookingAgent); }
    public void deleteBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.deleteAll(bookingAgents); }
    public void deleteBookingAgent(BookingAgent bookingAgent) { bookingAgentRepository.delete(bookingAgent); }

    public List<BookingGuest> getAllBookingGuests() { return (List<BookingGuest>) bookingGuestRepository.findAll(); }
    public void saveBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.saveAll(bookingGuests); }
    public void saveBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.save(bookingGuest);}
    public void deleteBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.deleteAll(bookingGuests); }
    public void deleteBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.delete(bookingGuest); }

    public List<BookingPayment> getAllBookingPayments() { return (List<BookingPayment>) bookingPaymentRepository.findAll(); }
    public void saveBookingPayments(List<BookingPayment> bookingPayments) { bookingPaymentRepository.saveAll(bookingPayments); }
    public void saveBookingPayment(BookingPayment bookingPayment) { bookingPaymentRepository.save(bookingPayment); }
    public void deleteBookingPayments(List<BookingPayment> bookingPayments) { bookingPaymentRepository.deleteAll(bookingPayments); }
    public void deleteBookingPayment(BookingPayment bookingPayment) { bookingPaymentRepository.delete(bookingPayment); }

    public List<BookingUser> getAllBookingUsers() {
        return (List<BookingUser>) bookingUserRepository.findAll();
    }
    public void saveBookingUsers(List<BookingUser> bookingUsers) {
        bookingUserRepository.saveAll(bookingUsers);
    }
    public void saveBookingUser(BookingUser bookingUser) {
        bookingUserRepository.save(bookingUser);
    }
    public void deleteBookingUsers(List<BookingUser> bookingUsers) { bookingUserRepository.deleteAll(bookingUsers); }
    public void deleteBookingUser(BookingUser bookingUser) { bookingUserRepository.delete(bookingUser); }

    public List<FlightBookings> getFlightBookings() { return (List<FlightBookings>) flightBookingsRepository.findAll(); }
    public void saveFlightBookings(List<FlightBookings> flightBookings) { flightBookingsRepository.saveAll(flightBookings); }
    public void saveFlightBooking(FlightBookings flightBooking) { flightBookingsRepository.save(flightBooking); }
    public void deleteFlightBookings(List<FlightBookings> flightBookings) { flightBookingsRepository.deleteAll(flightBookings); }
    public void deleteFlightBooking(FlightBookings flightBookings) { flightBookingsRepository.delete(flightBookings); }

    public List<Flight> getFlights() { return (List<Flight>) flightRepository.findAll(); }
    public Flight getFlightById(Integer flightId) { return flightRepository.findById(flightId).get(); }
    public List<Flight> getFlightsByRouteId(Integer routeId) { return flightRepository.findByRouteId(routeId); }
    public List<Flight> getFlightsByAirplaneId(Integer airplaneId) { return flightRepository.findByAirplaneId(airplaneId); }
    public void saveFlights(List<Flight> flights) { flightRepository.saveAll(flights); }
    public void saveFlight(Flight flight) { flightRepository.saveAndFlush(flight); }
    public void deleteFlights(List<Flight> flights) { flightRepository.deleteAll(flights); }
    public void deleteFlightById(Integer id) { flightRepository.deleteById(id);}

    public List<Passenger> getPassengers() {
        return (List<Passenger>) passengerRepository.findAll();
    }
    public Passenger getPassengerById(Integer passengerId) { return passengerRepository.findById(passengerId).get(); }
    public List<Passenger> getPassengersByBookingId(Integer bookingId) { return passengerRepository.findByBookingId(bookingId); }
    public List<Passenger> getPassengersByFamilyName(String familyName) { return passengerRepository.findByFamilyName(familyName); }
    public void savePassengers(List<Passenger> passengers) {
        for(Passenger passenger: passengers) {
            savePassenger(passenger);
        }
    }
    public void savePassenger(Passenger passenger) {
        FlightBookings flightBookings = flightBookingsRepository.findFlightBookingsByBookingId(passenger.getBooking_id().getId());
        Flight flight = flightRepository.findById(flightBookings.getFlightBookingsId().getFlight_id()).get();
        if(flight != null) {
            if(flight.getReserved_seats() > 0) {
                flight.setReserved_seats(flight.getReserved_seats()-1);
                flightRepository.save(flight);
                passengerRepository.save(passenger);
            }
            else throw new IllegalArgumentException("Not enough seats to add to");
        }
        else throw new IllegalArgumentException("No flights for passenger's booking id");
    }
    public void deletePassengers(List<Passenger> passengers) { passengerRepository.deleteAll(passengers); }
    public void deletePassengerById(Integer id) {
        Flight flight = flightRepository.findFlightByPassenger(id);
        if(flight.getReserved_seats() + 1 <= flight.getAirplane().getAirplaneType().getMax_capacity()){
            flight.setReserved_seats(flight.getReserved_seats() + 1);
            flightRepository.save(flight);
        }
        passengerRepository.deleteById(id);
    }

    public List<Route> getRoutes() { return (List<Route>) routeRepository.findAll(); }
    public void saveRoutes(List<Route> routes) { routeRepository.saveAll(routes); }
    public void saveRoute(Route route) { routeRepository.save(route); }
    public void deleteRoutes(List<Route> routes) { routeRepository.deleteAll(routes); }
    public void deleteRouteById(Integer id) {
        routeRepository.deleteById(id);
    }

    public List<User> getAllUsers() { return (List<User>) userRepository.findAll(); }
    public void saveUsers(List<User> users) {
        for (User user: users) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.saveAll(users);
    }

    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    public void deleteUsers(List<User> users) { userRepository.deleteAll(users); }
    public void deleteUserById(Long id) { userRepository.deleteById(id); }

    public List<UserRole> getAllUserRoles() { return (List<UserRole>) userRoleRepository.findAll(); }
    public void saveUserRoles(List<UserRole> userRoles) { userRoleRepository.saveAll(userRoles); }
    public void saveUserRole(UserRole userRole) { userRoleRepository.save(userRole); }
    public void deleteUserRoles(List<UserRole> userRoles) { userRoleRepository.deleteAll(userRoles); }
    public void deleteUserRoleById(Long id) { userRoleRepository.deleteById(id); }



}
