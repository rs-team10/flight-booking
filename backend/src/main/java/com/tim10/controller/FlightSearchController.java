package com.tim10.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Flight;
import com.tim10.dto.DestinationDTO;
import com.tim10.dto.FilterDTO;
import com.tim10.dto.FlightSearchDTO;
import com.tim10.service.DestinationService;
import com.tim10.service.FlightSearchService;

@RestController
@RequestMapping(value="/api")
public class FlightSearchController {
	
	@Autowired
	private DestinationService destinationService;
	
	@Autowired
	private FlightSearchService flightSearchService;
	
	
	@GetMapping(value = "/flightSearch/getAllDestinations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
		List<DestinationDTO> destinationsList = destinationService.getAllDestinations();
		return new ResponseEntity<List<DestinationDTO>>(destinationsList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/flightSearch/oneWaySearch", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightSearchDTO>> performOneWaySearch(Pageable page, @PathParam("departureId") Long departureId, @PathParam("destinationId") Long destinationId,
			@PathParam("departureDate") String departureDate, @PathParam("passengerCount") Integer passengerCount, @PathParam("flightClassString") String flightClassString) {
		
		try {
			List<FlightSearchDTO> results = new ArrayList<FlightSearchDTO>();
			for(Flight flight : flightSearchService.performOneWaySearch(page, departureId, destinationId, departureDate, passengerCount, flightClassString)) {
				results.add(new FlightSearchDTO(flight));
			}
			return new ResponseEntity<List<FlightSearchDTO>>(results, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/flightSearch/oneWayFilterSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightSearchDTO>> oneWayFilterSearch(Pageable page, @RequestBody FilterDTO filter) {
		
		try {
			List<FlightSearchDTO> results = new ArrayList<FlightSearchDTO>();
			for(Flight flight : flightSearchService.performOneWayFilterSearch(page, filter)) {
				results.add(new FlightSearchDTO(flight));
			}
			return new ResponseEntity<List<FlightSearchDTO>>(results, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/flightSearch/getAirlineNames", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getAirlineNames() {
		List<String> airlineNames = flightSearchService.getAirlineNames();
		return new ResponseEntity<List<String>>(airlineNames, HttpStatus.OK);
	}
}
