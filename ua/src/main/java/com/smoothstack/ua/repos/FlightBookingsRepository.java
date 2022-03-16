package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.FlightBookings;
import com.smoothstack.ua.models.FlightBookingsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightBookingsRepository extends JpaRepository<FlightBookings, FlightBookingsId> {

    @Query(value = "Select * from flight_bookings where booking_id = :bookingId", nativeQuery = true)
    public FlightBookings findFlightBookingsByBookingId(@Param("bookingId") Integer bookingId);
}
