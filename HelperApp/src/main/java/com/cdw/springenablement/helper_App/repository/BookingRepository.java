package com.cdw.springenablement.helper_App.repository;

import com.cdw.springenablement.helper_App.entity.Bookings;
import com.cdw.springenablement.helper_App.entity.Helper;
import com.cdw.springenablement.helper_App.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findByHelper(Helper helper);




    Bookings findByTimeSlot(TimeSlot timeSlot);

    Bookings findByHelperAndTimeSlot(Helper helper, TimeSlot timeSlot);
}
