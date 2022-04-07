package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Query(value = "select * from passenger where booking_id = :bookingId", nativeQuery = true)
    List<Passenger> findByBookingId(@Param("bookingId") Integer bookingId);

    @Query(value = "select * from passenger where family_name = :familyName", nativeQuery = true)
    List<Passenger> findByFamilyName(@Param("familyName") String familyName);

    @Query(value = "select * from passenger where family_name = :familyName and given_name = :givenName and booking_id = :bookingId", nativeQuery = true)
    Passenger findByNames(@Param("familyName") String familyName, @Param("givenName") String givenName, @Param("bookingId") Integer bookingId);
}
