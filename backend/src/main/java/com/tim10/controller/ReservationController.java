package com.tim10.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;
import com.tim10.dto.FlightReservationDTO;
import com.tim10.dto.InvitationDTO;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.dto.RoomTypesDTO;
import com.tim10.service.ReservationService;
import com.tim10.service.RoomReservationService;
import com.tim10.service.RoomService;

@RestController
@RequestMapping(value="/api/reservations")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	/**
	 * @author fivkovic
	 */
	@RequestMapping(
			value = "/reserveFlight",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveFlight(@RequestBody FlightReservationDTO flightReservationDTO) {
		
		Long reservationId = reservationService.reserveFlight(flightReservationDTO);
		
		if(reservationId != null)
			return new ResponseEntity<>(reservationId, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * @author fivkovic
	 */
	@RequestMapping(
			value = "/sendEmails/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendEmails(@PathVariable("id") Long groupReservationId) {
		
		boolean success = reservationService.sendEmails(groupReservationId);
		
		if(success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(
			value = "/acceptInvitation/{code}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptInvitation(@PathVariable("code") String invitationCode) {
		
		boolean success = reservationService.acceptInvitation(invitationCode);
		
		if(success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(
			value = "/declineInvitation/{code}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> declineInvitation(@PathVariable("code") String invitationCode) {
		
		boolean success = reservationService.declineInvitation(invitationCode);
		
		if(success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@RequestMapping(
			value = "/getAllInvitations",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			List<InvitationDTO> invitations = this.reservationService.getAllInvitations();
			return new ResponseEntity<List<InvitationDTO>>(invitations, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	

	@Autowired
	private RoomReservationService roomReservationService;

	@Autowired
	private RoomService roomService;

	/*
	 * Rezervisanje sobe/soba
	 */
//	@RequestMapping(value = "/reserveRoom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> reserveRooms(@RequestBody RoomReservationDTO reservationDTO) {
//
//		for (RoomDTO roomDTO : reservationDTO.getListOfRooms()) {
//			Room room = roomService.getRoom(roomDTO.getId()).get();
//			RoomReservation roomReservation = new RoomReservation(reservationDTO.getDateFrom(),
//					reservationDTO.getDateTo(), reservationDTO.getTotalPrice(), reservationDTO.getAdditionalServices(),
//					room);
//			roomReservationService.save(roomReservation);
//		}
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/reserveRoom/{dateFrom}/{dateTo}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveRooms(@RequestBody RoomReservationDTO reservationDTO,
											@PathVariable("dateFrom") String dateFrom,
											@PathVariable("dateTo") String dateTo) throws ParseException {
		try{
			roomReservationService.reserveRooms(reservationDTO, dateFrom, dateTo);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
