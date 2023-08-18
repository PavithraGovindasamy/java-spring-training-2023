package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.List;

/**
 * Interface that defines that timeSlotRepository methods
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Rooms rooms, String date, LocalTime endTime, LocalTime startTime);

    List<TimeSlot> findByTeamsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Teams teams, String date, LocalTime endTime, LocalTime startTime);

}

