package com.tim10.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	
	@RequestMapping(
			value = "/flightSearch/getAllDestinations",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
		
		List<DestinationDTO> destinationsList = destinationService.getAllDestinations();
		
		return new ResponseEntity<List<DestinationDTO>>(destinationsList, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/flightSearch/oneWaySearch",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightSearchDTO>> performOneWaySearch(Pageable page,
			@PathParam("departureId") Long departureId,
			@PathParam("destinationId") Long destinationId,
			@PathParam("departureDate") String departureDate,
			@PathParam("passengerCount") Integer passengerCount,
			@PathParam("flightClassString") String flightClassString) {
		
		List<FlightSearchDTO> results = new ArrayList<FlightSearchDTO>();
		for(Flight flight : flightSearchService.performOneWaySearch(page, departureId, destinationId, departureDate, passengerCount, flightClassString)) {
			results.add(new FlightSearchDTO(flight));
		}
		
		return new ResponseEntity<List<FlightSearchDTO>>(results, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/flightSearch/oneWayFilterSearch",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightSearchDTO>> oneWayFilterSearch(Pageable page, @RequestBody FilterDTO filter) {
		
		List<FlightSearchDTO> results = new ArrayList<FlightSearchDTO>();
		for(Flight flight : flightSearchService.performOneWayFilterSearch(page, filter)) {
			results.add(new FlightSearchDTO(flight));
		}
		
		return new ResponseEntity<List<FlightSearchDTO>>(results, HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(
			value = "/flightSearch/getAirlineNames",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getAirlineNames() {
		
		List<String> airlineNames = flightSearchService.getAirlineNames();
		
		return new ResponseEntity<List<String>>(airlineNames, HttpStatus.OK);
	}
}
