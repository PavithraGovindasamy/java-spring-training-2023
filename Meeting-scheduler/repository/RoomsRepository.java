package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Integer> {
}