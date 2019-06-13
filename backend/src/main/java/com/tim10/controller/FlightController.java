package com.tim10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.FlightDTO;
import com.tim10.dto.SeatsUpdateDTO;
import com.tim10.service.FlightService;

@RestController
@RequestMapping(value="/api")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping(
			value = "/flights/getFlights",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDTO>> getFlights() {
		return flightService.getFlights();
	}
	
	@RequestMapping(
			value = "/flights/addFlight",
			method = RequestMethod.POST)
	public ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO) {
		return flightService.addFlight(flightDTO);
	}
	
	@RequestMapping(
			value = "/flights/deleteFlight",
			method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFlight(@RequestBody FlightDTO flightDTO) {
		return flightService.deleteFlight(flightDTO);
	}
	
	@RequestMapping(
			value = "/flights/getFlightSeats",
			method = RequestMethod.POST)
	public ResponseEntity<?> getFlightSeats(@RequestBody FlightDTO flightDTO) {
		return flightService.getFlightSeats(flightDTO);
	}
	
	@RequestMapping(
			value = "/flights/updateFlightSeats",
			method = RequestMethod.POST)
	public ResponseEntity<?> updateFlightSeats(@RequestBody SeatsUpdateDTO seatsUpdateDTO) {
		return flightService.updateFlightSeats(seatsUpdateDTO);
	}
	
	
	
	
	
}
