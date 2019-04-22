package com.tim10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Airline;
import com.tim10.domain.AirlineAdmin;
import com.tim10.service.AirlineService;
import com.tim10.service.UserService;

@RestController
@RequestMapping(value="/api")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private UserService userService;
	
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
			for(AirlineAdmin admin : airline.getAdministrators()) {
				if(userService.findOneByUsername(admin.getUsername()).isPresent()) 
					return new ResponseEntity<>("User with username: " + admin.getUsername() + " already exists!", HttpStatus.FORBIDDEN);
				else if(userService.findOneByEmail(admin.getEmail()).isPresent()) 
					return new ResponseEntity<>("User with email: " + admin.getEmail() + " already exists!", HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(airlineService.save(airline), HttpStatus.CREATED);
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
