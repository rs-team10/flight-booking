package com.tim10.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Flight;
import com.tim10.domain.Seat;
import com.tim10.dto.FlightDTO;
import com.tim10.dto.SeatDTO;
import com.tim10.dto.SeatsUpdateDTO;
import com.tim10.service.FlightService;

@RestController
@RequestMapping(value="/api")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@PreAuthorize("hasAnyRole('ROLE_AIRLINE_ADMIN', 'ROLE_REGISTERED_USER')")
	@GetMapping(value = "/flights/getFlights", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDTO>> getFlights() {
		try {
			List<FlightDTO> flights = new ArrayList<FlightDTO>();
			for (Flight flight : flightService.getFlights())
				flights.add(new FlightDTO(flight));
			return new ResponseEntity<List<FlightDTO>>(flights, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PostMapping(value = "/flights/addFlight")
	public ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO) {
		try {
			final Flight flight = flightService.addFlight(flightDTO);
			return new ResponseEntity<FlightDTO>(new FlightDTO(flight), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (PersistenceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@DeleteMapping(value = "/flights/deleteFlight")
	public ResponseEntity<?> deleteFlight(@RequestBody FlightDTO flightDTO) {
		try {
			flightService.deleteFlight(flightDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@PreAuthorize("hasAnyRole('ROLE_AIRLINE_ADMIN', 'ROLE_REGISTERED_USER')")
	@PostMapping(value = "/flights/getFlightSeats")
	public ResponseEntity<List<SeatDTO>> getFlightSeats(@RequestBody FlightDTO flightDTO) {
		try {
			List<SeatDTO> seats = new ArrayList<SeatDTO>();
			for (Seat seat : flightService.getFlightSeats(flightDTO)) { 
				SeatDTO seatDTO = new SeatDTO(seat);
				seats.add(seatDTO); 
			}
			return new ResponseEntity<List<SeatDTO>>(seats, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PostMapping(value = "/flights/updateFlightSeats")
	public ResponseEntity<?> updateFlightSeats(@RequestBody SeatsUpdateDTO seatsUpdateDTO) {
		try {
			final Flight flight = flightService.updateFlightSeats(seatsUpdateDTO);
			return new ResponseEntity<FlightDTO>(new FlightDTO(flight), HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
