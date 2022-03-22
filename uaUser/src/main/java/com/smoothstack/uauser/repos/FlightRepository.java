package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query(value = "select * from flight where flight.route_id = :routeId", nativeQuery = true)
    List<Flight> findByRouteId(@Param("routeId") Integer routeId);

    @Query(value = "select * from flight where flight.airplane_id = :airplaneId", nativeQuery = true)
    List<Flight> findByAirplaneId(@Param("airplaneId") Integer airplaneId);

    @Query(value = "select * from flight where id in " +
            "(select flight_id from flight_bookings where booking_id in " +
            "(select booking_id from booking_user where user_id in ( " +
            "select id from utopia.user where id = :userId)))", nativeQuery = true)
    List<Flight> findAllByUserId(@Param("userId") Long userId);
}
