package com.cdw.springenablement.helperapp.repository;

import com.cdw.springenablement.helperapp.entity.Bookings;
import com.cdw.springenablement.helperapp.entity.TimeSlot;
import com.cdw.springenablement.helperapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository which handles booking related operation
 */

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findByUsers(Users users);


    Bookings findByHelperIdAndTimeSlot(Long helperId, TimeSlot timeSlot);


    List<Bookings> findByHelperId(Long helperId);

    List<Bookings> findByTimeSlotId(Long id);

    List<Bookings> findByUsers(Optional<Users> newUser);

    List<Bookings> findByTimeSlotIdAndDate(Long id, LocalDate date);

    Bookings findByHelperIdAndTimeSlotAndDate(Long helperId, TimeSlot timeSlot, LocalDate date);

    List<Bookings> findByDate(LocalDate date);
}
