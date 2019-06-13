package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.RoomReservation;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>{

	
}
