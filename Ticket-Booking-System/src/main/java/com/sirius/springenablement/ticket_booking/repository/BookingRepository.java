package com.sirius.springenablement.ticket_booking.repository;

import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {


    java.util.List<com.sirius.springenablement.ticket_booking.entity.Bookings> findByUserId(Long userId);
//    Optional<Bookings> findByUserIdAndShowIdAndDate(Long userId, Long showId,java.time.LocalDate date);

    java.util.Optional<com.sirius.springenablement.ticket_booking.entity.Bookings> findByUserIdAndShowId(Long userId, Long userId1);
}