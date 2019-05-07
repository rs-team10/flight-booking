package com.tim10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.RoomReservation;
import com.tim10.repository.RoomReservationRepository;

@Service
public class RoomReservationService {
	
	@Autowired 
	private RoomReservationRepository roomReservationRepository;
	
	public RoomReservation save(RoomReservation roomReservation) {
		return roomReservationRepository.save(roomReservation);
	}
}
