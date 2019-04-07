package com.tim10.controller;

import java.util.List;

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
@RequestMapping(value="/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET /*produces = MediaType.APPLICATION_JSON_VALUE*/)
	public ResponseEntity<List<Hotel>> getHotels() {
		
		List<Hotel> hotels = hotelService.findAll();
		
		//convert hotels to DTOs
//		List<HotelDTO> hotelsDTO = new ArrayList<>();
//		for(Hotel h : hotels) {
//			hotelsDTO.add(new HotelDTO(h));
//		}
	
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registerHotel", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel){
		
		//proverimo da li postoji hotel sa tim imenom
		if(hotelService.findOneByName(hotel.getName()) == null) {
//			Hotel hotel = new Hotel();
//			hotel.setName(hotelDTO.getName());
//			hotel.setDescription(hotelDTO.getDescription());
//			
//			//Privremena lokacija (samo ulicu cuva)
//			Location location = new Location();
//			location.setStreet(hotelDTO.getLocation().getStreet());
//			hotel.setLocation(location);
			
			//sta radi sa setovima???
			Hotel returnHotel = hotelService.save(hotel);
			return new ResponseEntity<>(returnHotel, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/updateHotel", method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) throws Exception {
		
		if(hotelService.findOne(hotel.getId()) != null){
			return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.OK);
		}
		return new ResponseEntity<>("Wanted hotel does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Collection<Hotel>> getHotels() {
//		Collection<Hotel> hotels = hotelService.findAll();
//		return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/api/hotels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws Exception {
//		Hotel savedHotel = hotelService.create(hotel);
//		return new ResponseEntity<Hotel>(savedHotel, HttpStatus.CREATED);
//	}
//
//	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) throws Exception {
//		Hotel updatedHotel = hotelService.update(hotel);
//		if(updatedHotel == null) {
//			return new ResponseEntity<Hotel>(
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return new ResponseEntity<Hotel>(updatedHotel, HttpStatus.OK);
//	}

}
