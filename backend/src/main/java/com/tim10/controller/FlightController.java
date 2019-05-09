package com.tim10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.FlightDTO;
import com.tim10.dto.PriceListItemDTO;
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
	
	@RequestMapping(
			value = "/deleteFlight",
			method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFlight(@RequestBody FlightDTO flightDTO) {
		return flightService.deleteFlight(flightDTO);
	}
	
	@RequestMapping(
			value = "/addPriceListItem",
			method = RequestMethod.POST)
	public ResponseEntity<?> addPriceListItem(@RequestBody PriceListItemDTO priceListItemDTO) {
		return flightService.addPriceListItem(priceListItemDTO);
	}
	
	@RequestMapping(
			value = "/deletePriceListItem",
			method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePriceListItem(@RequestBody PriceListItemDTO priceListItemDTO) {
		return flightService.deletePriceListItem(priceListItemDTO);
	}

}
