package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Hotel;
import com.tim10.service.HotelService;

@CrossOrigin
@RestController
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotels() {
		Collection<Hotel> hotels = hotelService.findAll();
		return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/hotels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws Exception {
		Hotel savedHotel = hotelService.create(hotel);
		return new ResponseEntity<Hotel>(savedHotel, HttpStatus.CREATED);
	}
}
