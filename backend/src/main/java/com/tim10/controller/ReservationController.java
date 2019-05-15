package com.tim10.controller;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.QuickRoomReservation;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.service.RoomReservationService;
import com.tim10.service.RoomService;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

	@Autowired
	private RoomReservationService roomReservationService;

	@Autowired
	private RoomService roomService;

	/*
	 * Rezervisanje sobe/soba
	 */
	@RequestMapping(value = "/reserveRoom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveRooms(@RequestBody RoomReservationDTO reservationDTO) {

		for (RoomDTO roomDTO : reservationDTO.getListOfRooms()) {
			Room room = roomService.getRoom(roomDTO.getId()).get();
			RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
					reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
					room);
			roomReservationService.save(roomReservation);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	/*
//	 * Dodavanje brze rezervacije (hotel admin)
//	 */
//	@RequestMapping(value = "/addQuickReservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> addQuickReservation(@RequestBody RoomReservationDTO reservationDTO) {
//		
//		long diffInMillies = Math.abs(reservationDTO.getDateTo().getTime() - reservationDTO.getDateFrom().getTime());
//	    int days = (int)TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//
//		for(RoomDTO roomDTO : reservationDTO.getListOfRooms()) {
//			Room room = roomService.getRoom(roomDTO.getId()).get();
//			QuickRoomReservation quickRoomReservation = 
//					new QuickRoomReservation(reservationDTO.getDateFrom(), reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(), room, reservationDTO.getDiscount());
//			
//			BigDecimal additionalServicesPrice = quickRoomReservation.getTotalPrice();
//			quickRoomReservation.setTotalPrice(additionalServicesPrice.add(room.getRoomType().getPricePerNight().multiply(new BigDecimal(days))));
//			roomReservationService.save(quickRoomReservation);
//		}
//		
//		//zabraniti rezervaciju ako je soba rezervisana
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

}
