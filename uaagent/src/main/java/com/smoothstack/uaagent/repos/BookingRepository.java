package com.smoothstack.uaagent.repos;


import com.smoothstack.uaagent.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select * from booking where is_active = :isActive", nativeQuery = true)
    List<Booking> findByIsActive(@Param("isActive") Boolean isActive);

    @Query(value = "select * from booking where confirmation_code = :confirmationCode", nativeQuery = true)
    List<Booking> findByConfirmationCode(@Param("confirmationCode") String confirmationCode);
}

