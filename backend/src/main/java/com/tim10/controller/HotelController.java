package com.tim10.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Hotel;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.Room;
import com.tim10.dto.HotelDTO;
import com.tim10.dto.HotelReservationDTO;
import com.tim10.dto.RoomDTO;
import com.tim10.dto.RoomsDTO;
import com.tim10.service.HotelService;
import com.tim10.service.UserService;

@RestController
@RequestMapping(value="/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels() {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll())
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{parameter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> searchHotels(@PathVariable("parameter") String param) {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h : hotelService.findByParameter(param))
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	/*IZMENITI - njaverovatnije nece biti potrebno dobavljanje ovakvog DTO-a zbog konkretnih soba, njih naknadno uzimati pri dodavanju kod admina*/
	@RequestMapping(value = "/pageHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotelsPage(Pageable page) {
		Page<Hotel> pageHotels = hotelService.findAll(page);
		List<HotelDTO> hotelsDTO = new ArrayList<>();
		for(Hotel h : pageHotels) 
			hotelsDTO.add(new HotelDTO(h));
		return new ResponseEntity<>(hotelsDTO, HttpStatus.OK);
	}
	
	/*Kod prikaza hotela registrovanom korisniku pri rezervaciji (ne salju se konkretne sobe u DTO)*/
	@RequestMapping(value = "/resHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelReservationDTO>> getResHotelsPage(Pageable page) {
		Page<Hotel> pageHotels = hotelService.findAll(page);
		List<HotelReservationDTO> hotelsDTO = new ArrayList<>();
		for(Hotel h : pageHotels) 
			hotelsDTO.add(new HotelReservationDTO(h));
		return new ResponseEntity<>(hotelsDTO, HttpStatus.OK);
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
			return new ResponseEntity<>(hotelService.registerHotel(hotel), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) throws Exception {
		Hotel existingHotel = hotelService.findOneByName(hotel.getName());
		if(existingHotel != null && existingHotel.getId() != hotel.getId())
			return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
		
		Optional<Hotel> h = hotelService.findOne(hotel.getId());
		if(h.isPresent()){
			hotel.setAdministrators(h.get().getAdministrators());
			return new ResponseEntity<>(hotelService.save(hotel), HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Wanted hotel does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*Metoda za proveravanje da li su dostupne trazene sobe, i vracanje liste konkretnih soba koje su za rezervaciju
	 * MOZE LI NEKAKO - da se te sobe sklone dok se rezervacija ne zavrsi skroz????*/
	@RequestMapping(value="/getRooms/{roomTypeId}/{numberOfRooms}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRooms(@PathVariable("roomTypeId") Long roomTypeId,
										@PathVariable("numberOfRooms") int numberOfRooms){
		
		//trenutno samo proveravamo da li ima dovoljno soba za rezervaciju
		//mozda bi trebalo vratiti te sobe koje cemo da rezervisemo da ih 
		//stavimo na time out
		List<Room> allRooms = hotelService.getRooms(roomTypeId);
		if(allRooms.size() < numberOfRooms) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		List<Room> responseRooms = new ArrayList<>();
		for(int i = 0; i < numberOfRooms; i++) 
			responseRooms.add(allRooms.get(i));
		
		return new ResponseEntity<>(responseRooms, HttpStatus.OK);

	}
	
	@RequestMapping(value="/getRooms", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRooms(@RequestBody List<RoomDTO> lista){
		
		List<Room> rooms;
		List<Room> responseRooms = new ArrayList<>();
		
		for(RoomDTO roomDTO : lista) {
			rooms = hotelService.getRooms(roomDTO.getRoomType().getId());
			if(rooms.size() < roomDTO.getNumberOfRooms()) 
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			for(int i = 0; i < roomDTO.getNumberOfRooms(); i++)
				responseRooms.add(rooms.get(i));
		}
		return new ResponseEntity<>(responseRooms, HttpStatus.OK);

	}
}
