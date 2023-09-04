package com.sirius.springenablement.meetingScheduler.repository;

import com.sirius.springenablement.meetingScheduler.entity.Rooms;
import com.sirius.springenablement.meetingScheduler.entity.Teams;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Interface that defines that timeSlotRepository methods
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Rooms rooms, LocalDate date, LocalTime endTime, LocalTime startTime);

    List<TimeSlot> findByTeamsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(Teams teams, LocalDate date, LocalTime endTime, LocalTime startTime);

}

