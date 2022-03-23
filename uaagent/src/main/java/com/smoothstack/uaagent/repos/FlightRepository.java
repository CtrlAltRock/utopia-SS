package com.smoothstack.uaagent.repos;

import com.smoothstack.uaagent.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query(value = "select * from flight where flight.route_id = :routeId", nativeQuery = true)
    List<Flight> findByRouteId(@Param("routeId") Integer routeId);

    @Query(value = "select * from flight where flight.airplane_id = :airplaneId", nativeQuery = true)
    List<Flight> findByAirplaneId(@Param("airplaneId") Integer airplaneId);

    @Query(value = "select * from flight where flight.departure_time > :departureTime", nativeQuery = true)
    List<Flight> getAvailableFlights(@Param("departureTime") LocalDateTime departureTime);

    @Query(value = "select * from flight where flight.departure_time >= :time1 and flight.departure_time <= :time2", nativeQuery = true)
    List<Flight> findByDateRange(@Param("time1") LocalDateTime time1, @Param("time2") LocalDateTime time2);
}
