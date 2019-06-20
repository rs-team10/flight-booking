package com.tim10.service;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.GroupReservation;
import com.tim10.domain.QuickRoomReservation;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Reservation;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.repository.GroupReservationRepository;
import com.tim10.repository.QuickRoomReservationRepository;
import com.tim10.repository.RoomRepository;
import com.tim10.repository.RoomReservationRepository;

@Service
public class RoomReservationService {
	
	@Autowired 
	private RoomReservationRepository roomReservationRepository;
	
	@Autowired
	private QuickRoomReservationRepository quickRoomReservationRepository;
	
	@Autowired 
	private RoomRepository roomRepository;
	
	@Autowired
	private GroupReservationRepository groupReservationRepository;
	
	public RoomReservation save(RoomReservation roomReservation) {
		return roomReservationRepository.save(roomReservation);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public Long reserveRooms(RoomReservationDTO reservationDTO, Long groupResId){
		
		GroupReservation groupReservation = groupReservationRepository.getOne(groupResId);
		Set<Reservation> reservations = groupReservation.getReservations();
		
			for(RoomDTO roomDTO : reservationDTO.getListOfRooms()) {
				
				Optional<Room> optionalRoom = roomRepository.findOneById(roomDTO.getId());
								
				if(!optionalRoom.isPresent())
					throw new EntityNotFoundException("Room not found");
				Room room = optionalRoom.get();
				if(room.isReserved(reservationDTO.getDateFrom(), reservationDTO.getDateTo())) {
					throw new PersistenceException("Room is already reserved in that period");
				}
				
				for(int i = 0; i < room.getRoomType().getCapacity(); i++) {
					RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
							reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
							room);
					
					room.getRoomReservations().add(roomReservation);
					
					for(Reservation reservation : groupReservation.getReservations()) {
						if(reservation.getRoomReservation() == null) {
							reservation.setRoomReservation(roomReservation);
							
							roomReservation.setReservation(reservation);
							roomReservationRepository.save(roomReservation);
							break;
						}
					}
				}
				//roomRepository.save(room);
			}
			
			GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
			return savedGroupReservation.getId();
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public boolean quickReserveRoom(Long quickRoomResId, Long groupResId) {
		
		GroupReservation groupReservation = groupReservationRepository.getOne(groupResId);
		
		//get quick room reservation (imas svoju verziju)
		Optional<QuickRoomReservation> optionalQuickRoomRes = quickRoomReservationRepository.findById(quickRoomResId);
		if(!optionalQuickRoomRes.isPresent())
			throw new EntityNotFoundException("Quick room reservation not found");
		QuickRoomReservation quickRoomRes = optionalQuickRoomRes.get();
		
		if(quickRoomRes.getReservation() != null)
			throw new EntityNotFoundException("Quick room reservation has already been reserved");
		
		
		//uzmi trenutnog ulogovanog korisnika
		RegisteredUser activeUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		//prolazi kroz rezervacije od group reservation
		for(Reservation res : groupReservation.getReservations()) {
			if(res.getRegisteredUser().getId().equals(activeUser.getId())) {
				res.setRoomReservation(quickRoomRes);
				
				quickRoomRes.setReservation(res);
				quickRoomReservationRepository.save(quickRoomRes);
				break;
			}
		}
		
		groupReservationRepository.save(groupReservation);
		return true;
	}	
}
