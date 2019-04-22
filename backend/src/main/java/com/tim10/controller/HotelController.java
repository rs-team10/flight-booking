package com.tim10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Hotel;
import com.tim10.domain.HotelAdmin;
import com.tim10.dto.HotelDTO;
import com.tim10.service.HotelService;
import com.tim10.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value="/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getHotels() {
		return new ResponseEntity<List<Hotel>>(hotelService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{parameter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> searchHotels(@PathVariable("parameter") String param) {
		List<Hotel> hotels = hotelService.findByParameter(param);
		return new ResponseEntity<>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pageHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Hotel>> getHotelsPage(Pageable page) {
		//page object holds data about pagination and sorting
		//the object is created based on the url parameters "page", "size" and "sort" 
		return new ResponseEntity<>(hotelService.findAll(page), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel){
		if(hotelService.hotelExists(hotel)) {
			for(HotelAdmin admin : hotel.getAdministrators()) {
				if(userService.findOneByUsername(admin.getUsername()).isPresent()) 
					return new ResponseEntity<>("User with username: " + admin.getUsername() + " already exists!", HttpStatus.FORBIDDEN);
				else if(userService.findOneByEmail(admin.getEmail()).isPresent())
					return new ResponseEntity<>("User with email: " + admin.getEmail() + " already exists!", HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) throws Exception {
		Hotel existingHotel = hotelService.findOneByName(hotel.getName());
		if(existingHotel != null && existingHotel.getId() != hotel.getId())
			return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
		
		if(hotelService.findOne(hotel.getId()) != null){
			return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Wanted hotel does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
