package com.sirius.springenablement.meetingScheduler.repository;

import com.sirius.springenablement.meetingScheduler.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;


/**
 * Interface that defines that RoomsRepository methods
 */
@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Integer> {
    Rooms findByRoomName(String roomName);
    @Query("SELECT  r FROM Rooms r " +
            "JOIN r.timeSlots ts " +
            "WHERE ts.date = :date " +
            "AND ts.startTime >= :startTime " +
            "AND ts.endTime <= :endTime " +
            "AND r.id IN :roomIdsList")
    List<Rooms> findRoomsByTimeSlotsAndIdIn(@Param("date") Date date,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime,
                                            @Param("roomIdsList") List<Integer> roomIdsList);
}



