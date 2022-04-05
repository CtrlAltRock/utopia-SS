package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.models.FlightBookings;
import com.smoothstack.ua.models.Passenger;
import com.smoothstack.ua.repos.FlightBookingsRepository;
import com.smoothstack.ua.repos.FlightRepository;
import com.smoothstack.ua.repos.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassengerService {

    Logger logger = LoggerFactory.getLogger(PassengerService.class);

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightBookingsRepository flightBookingsRepository;

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


}
