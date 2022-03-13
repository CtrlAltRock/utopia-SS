package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.BookingPayment;
import com.smoothstack.ua.models.BookingPaymentId;
import org.springframework.data.repository.CrudRepository;

public interface BookingPaymentRepository extends CrudRepository<BookingPayment, BookingPaymentId> {

}
