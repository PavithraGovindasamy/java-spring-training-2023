package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
//    List<TimeSlot> findByRoomsAndDate(Rooms room, String date);

    List<TimeSlot> findByEmployeeAndDate(Employee teamMember, String date);




    List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Rooms rooms, String date, LocalTime endTime, LocalTime startTime);

    List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Teams teams, String date, LocalTime endTime, LocalTime startTime);

}

