package com.tim10.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.GroupReservation;
import com.tim10.domain.Reservation;
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
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(value = "/sendEmails/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendEmails(@PathVariable("id") Long groupReservationId) {
		try {
			reservationService.sendEmails(groupReservationId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (MessagingException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(value = "/reserveFlight", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> reserveFlight(@RequestBody FlightReservationDTO flightReservationDTO) {
		try {
			Long reservationId = reservationService.reserveFlight(flightReservationDTO);
			return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(value = "/reserveQuickFlight", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> reserveQuickFlight(@RequestBody QuickFlightReservationDTO dto) {
		try {
			Long reservationId = reservationService.reserveQuickFlight(dto);
			return new ResponseEntity<Long>(reservationId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/cancelFlightReservation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelReservation(@PathVariable("id") Long groupReservationId) {
		try {
			reservationService.cancelFlightReservation(groupReservationId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/acceptInvitation/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptInvitation(@PathVariable("code") String invitationCode) {
		try {
			reservationService.acceptInvitation(invitationCode);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping( value = "/declineInvitation/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> declineInvitation(@PathVariable("code") String invitationCode) {
		try {
			reservationService.declineInvitation(invitationCode);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/getAllInvitations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
		try {
			List<InvitationDTO> invitations = new ArrayList<InvitationDTO>();
			for (Reservation reservation : reservationService.getAllInvitations()) {
				
				InvitationDTO dto = new InvitationDTO();
				dto.setId(reservation.getInvitationCode());
				dto.setDeparture(reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName());
				dto.setDestination(reservation.getFlightReservation().getSeat().getFlight().getDestination().getName());
				dto.setDate(reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString());
				
				GroupReservation gr = reservation.getGroupReservation();
				for (Reservation r : gr.getReservations()) {
					if (r.getIsHost()) {
						dto.setBy(r.getRegisteredUser().getFirstName() + " " + r.getRegisteredUser().getLastName());
					}
				}			
	
				dto.setStatus(reservation.getStatus());
				invitations.add(dto);
			}
			return new ResponseEntity<List<InvitationDTO>>(invitations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
