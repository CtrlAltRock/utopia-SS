package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query(value = "select * from route where origin_id = :originId and destination_id = :destinationId", nativeQuery = true)
    Optional<Route> findByOriginDestination(@Param("originId") String origin, @Param("destinationId") String destination);

    @Query(value = "delete from route where id = :routeId", nativeQuery = true)
    void deleteRouteById(@Param("routeId") Integer routeId);
}
