package com.tim10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.FlightDTO;
import com.tim10.service.FlightService;

@RestController
@RequestMapping(value="/api")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping(
			value = "/addFlight",
			method = RequestMethod.POST)
	public ResponseEntity<?> addFlight(@RequestBody FlightDTO flightDTO) {
		return flightService.addFlight(flightDTO);
	}

}
