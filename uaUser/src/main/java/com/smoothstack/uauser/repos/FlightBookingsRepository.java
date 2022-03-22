package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.FlightBookings;
import com.smoothstack.uauser.models.FlightBookingsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightBookingsRepository extends JpaRepository<FlightBookings, FlightBookingsId> {

    @Query(value = "Select * from flight_bookings where booking_id = :bookingId", nativeQuery = true)
    public FlightBookings findFlightBookingsByBookingId(@Param("bookingId") Integer bookingId);

    @Query(value = "Select * from flight_bookings where flight_id = :flightId", nativeQuery = true)
    public FlightBookings findFlightBookingsByFlightId(@Param("flightId") Integer id);
}
