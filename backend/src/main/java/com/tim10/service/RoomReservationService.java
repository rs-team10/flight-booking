package com.tim10.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.repository.RoomRepository;
import com.tim10.repository.RoomReservationRepository;

@Service
public class RoomReservationService {
	
	@Autowired 
	private RoomReservationRepository roomReservationRepository;
	
	@Autowired 
	private RoomRepository roomRepository;
	
	public RoomReservation save(RoomReservation roomReservation) {
		return roomReservationRepository.save(roomReservation);
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public void reserveRooms(RoomReservationDTO reservationDTO, String dateFrom, String dateTo) throws ParseException {
		
		Date checkInDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
		Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
		
		for (RoomDTO roomDTO : reservationDTO.getListOfRooms()) {
			Room room = roomRepository.findOneById(roomDTO.getId());
			
			if(room.isReserved(reservationDTO.getDateFrom(), reservationDTO.getDateTo()))
				throw new PersistenceException("vec rezervisano u tom periodu");
			
			RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
					reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
					room);
			save(roomReservation);
			
			
//			RoomType roomType = roomTypesDTO.getRoomType();
//			int numberOfRooms = roomTypesDTO.getNumberOfRooms();
			
//			List<Room> sobe  =new ArrayList<>();
//			try {
//				sobe = roomRepository.getNumberOfRooms(roomType.getId(), numberOfRooms);
//			}catch(Exception ex) {
//				ex.printStackTrace();
//			}
//			for(Room room : sobe) {
//				RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
//						reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
//						room);
//				save(roomReservation);
//			}
			
			
//			for(int i = 0; i < numberOfRooms; i++) {
//				Room room = roomRepository.findOneByRoomTypeId(roomType.getId());
//				RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
//						reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
//						room);
//				save(roomReservation);
//				
//			}
			//sa pesimistickim zakljucavanjem
			
			//selektujemo sobu po roomType id-u
		}
	}
}
