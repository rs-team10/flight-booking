package com.tim10.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RegisteredUser;
import com.tim10.dto.FlightReservationDTO;
import com.tim10.dto.InvitationDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.dto.RoomReservationDTO;
import com.tim10.service.ReservationService;
import com.tim10.service.RoomReservationService;

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

		Long reservationId;
		
		try {
			reservationId = reservationService.reserveFlight(flightReservationDTO);
			return new ResponseEntity<>(reservationId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	/**
	 * @author fivkovic
	 */
	@RequestMapping(
			value = "/reserveQuickFlight",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveQuickFlight(@RequestBody QuickFlightReservationDTO dto) {

		Long reservationId;
		
		try {
			reservationId = reservationService.reserveQuickFlight(dto);
			return new ResponseEntity<>(reservationId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
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
			value = "/cancelFlightReservation/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelReservation(@PathVariable("id") Long groupReservationId) {
		
		boolean success = reservationService.cancelFlightReservation(groupReservationId);
		
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

	/*
	 * Rezervisanje sobe/soba
	 */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(value = "/reserveRoom/{groupResId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reserveRooms(@RequestBody RoomReservationDTO reservationDTO,
											@PathVariable Long groupResId) {
		try{
			roomReservationService.reserveRooms(reservationDTO, groupResId);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(value = "/quickReserveRoom", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> quickReserveRoom(@PathParam("quickReservationId") Long quickReservationId, @PathParam("groupReservationId") Long groupReservationId){
		try {
			roomReservationService.quickReserveRoom(quickReservationId, groupReservationId);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
