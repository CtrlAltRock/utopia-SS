package com.smoothstack.ua.services;

import com.smoothstack.ua.models.FlightBookings;
import com.smoothstack.ua.models.FlightBookingsId;
import com.smoothstack.ua.repos.FlightBookingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlightBookingService {

    Logger logger = LoggerFactory.getLogger(FlightBookingService.class);

    @Autowired
    FlightBookingsRepository flightBookingsRepository;
    public List<FlightBookings> getFlightBookings() {
        return (List<FlightBookings>) flightBookingsRepository.findAll();
    }

    public FlightBookings getFlightBookingsById(FlightBookingsId flightBookingId) {
        Optional<FlightBookings> flightBooking = flightBookingsRepository.findById(flightBookingId);
        if(flightBooking.isPresent()) {
            return flightBooking.get();
        }
        else return null;
    }

    public FlightBookings saveFlightBooking(FlightBookings flightBooking) {
        FlightBookings posted = flightBookingsRepository.save(flightBooking);
        return posted;
    }

    public void deleteFlightBooking(FlightBookings flightBookings) {
        flightBookingsRepository.delete(flightBookings);
    }
}
