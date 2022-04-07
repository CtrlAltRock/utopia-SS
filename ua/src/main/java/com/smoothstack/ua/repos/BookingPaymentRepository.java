package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.BookingPayment;
import com.smoothstack.ua.models.BookingPaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPaymentRepository extends JpaRepository<BookingPayment, BookingPaymentId> {

}
