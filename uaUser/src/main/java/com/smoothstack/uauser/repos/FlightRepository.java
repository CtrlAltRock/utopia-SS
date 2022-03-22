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
}
