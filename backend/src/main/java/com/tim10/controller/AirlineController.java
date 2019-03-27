package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Airline;
import com.tim10.service.AirlineService;

@CrossOrigin
@RestController
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	@RequestMapping(
			value = "api/airlines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Airline>> getAirlines() {
		Collection<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<Collection<Airline>>(airlines, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/airlines/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline(@PathVariable("id") Long id) {
		Airline airline = airlineService.findById(id);
		if (airline == null) {
			return new ResponseEntity<Airline>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Airline>(airline, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/airlines",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> createAirline(
			@RequestBody Airline airline) throws Exception {
		Airline savedAirline = airlineService.create(airline);
		return new ResponseEntity<Airline>(savedAirline, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/api/airlines/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> updateAirline(
			@RequestBody Airline airline) throws Exception {
		Airline updatedAirline = airlineService.update(airline);
		if (updatedAirline == null)
			return new ResponseEntity<Airline>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Airline>(updatedAirline, HttpStatus.OK);
	}
}
