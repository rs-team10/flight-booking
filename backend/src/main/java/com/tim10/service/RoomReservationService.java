package com.tim10.service;

import java.text.ParseException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.GroupReservation;
import com.tim10.domain.Reservation;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.repository.GroupReservationRepository;
import com.tim10.repository.RoomRepository;
import com.tim10.repository.RoomReservationRepository;

@Service
public class RoomReservationService {
	
	@Autowired 
	private RoomReservationRepository roomReservationRepository;
	
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
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
				if(!optionalRoom.isPresent())
					throw new EntityNotFoundException("Room not found");
				Room room = optionalRoom.get();
				if(room.isReserved(reservationDTO.getDateFrom(), reservationDTO.getDateTo())) {
					System.out.println("USAO OVDE GDE TREBA");
					throw new PersistenceException("vec rezervisano u tom periodu");
				}
					
				
				for(int i = 0; i < room.getRoomType().getCapacity(); i++) {
					RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
							reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
							room);
					
					room.getRoomReservations().add(roomReservation);
					
					for(Reservation reservation : groupReservation.getReservations()) {
						if(reservation.getRoomReservation() == null) {
							reservation.setRoomReservation(roomReservation);
							//groupReservation.add(reservation);
							break;
						}
					}
				}
			}
			
			GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
			return savedGroupReservation.getId();
	}
}
