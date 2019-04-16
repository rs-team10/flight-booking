package com.tim10.controller;

import java.util.List;

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
@RequestMapping(value="/api")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	
	@RequestMapping(
			value = "/airlines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Airline>> getAirlines() {
		List<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<List<Airline>>(airlines, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/airlines/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline(@PathVariable("id") Long id) {
		Airline airline = airlineService.findOne(id).get();
		if(airline == null)
			return new ResponseEntity<Airline>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Airline>(airline, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/airlines",
			method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAirline(@RequestBody Airline airline) {
		
		if(airlineService.findOneByName(airline.getName()) == null) {
			Airline returnAirline = airlineService.save(airline);
			return new ResponseEntity<>(returnAirline, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Airline with that name already exists!", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(
			value="/airlines",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAirline(@RequestBody Airline airline) {
		
		Airline existingAirline = airlineService.findOneByName(airline.getName());
		if(existingAirline != null && existingAirline.getId() != airline.getId())
			return new ResponseEntity<>("Airline with that name already exists!", HttpStatus.FORBIDDEN);
		if(airlineService.findOne(airline.getId()) != null)
			return new ResponseEntity<>(airlineService.save(airline), HttpStatus.OK);
		
		return new ResponseEntity<>("Wanted airline does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
