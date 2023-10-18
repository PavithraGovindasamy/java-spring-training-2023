package com.cdw.springenablement.helperapp.repository;

import com.cdw.springenablement.helperapp.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
/**
 * Repository which handles timeslot related operation
 */
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {


}
