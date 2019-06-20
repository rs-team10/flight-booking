package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.QuickRoomReservation;

public interface QuickRoomReservationRepository extends JpaRepository<QuickRoomReservation, Long>{
	

}
