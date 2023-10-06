package com.cdw.springenablement.helper_App.repository;

import com.cdw.springenablement.helper_App.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
/**
 * Repository which handles timeslot related operation
 */
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByDate(LocalDate date);

}
