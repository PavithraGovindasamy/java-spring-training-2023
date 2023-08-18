package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface that defines that RoomsRepository methods
 */
@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Integer> {
    Rooms findByRoomName(String roomName);
}