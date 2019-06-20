package com.tim10.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Hotel;
import com.tim10.dto.HotelDTO;
import com.tim10.dto.HotelReportDTO;
import com.tim10.dto.HotelRoomsDTO;
import com.tim10.dto.NewHotelDTO;
import com.tim10.dto.QuickRoomResDTO;
import com.tim10.dto.RoomDTO;
import com.tim10.service.HotelService;

@RestController
@RequestMapping(value="/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	/*
	 * GET hotela bez paginacije
	 */
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels() {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll())
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	/*
	 * GET hotela sa paginacijom
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels(Pageable page) {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll(page))
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	/*
	 * Pretraga hotela po nazivu i lokaciji. Vraca stranice.
	 */
	@GetMapping(value="/searchHotels", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> searchHotels(Pageable page,
														@PathParam("hotelName") String hotelName,
														@PathParam("hotelLocation") String hotelLocation) {
		return new ResponseEntity<>(hotelService.searchHotels(page, hotelName, hotelLocation), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerHotel(@RequestBody NewHotelDTO hotelDTO){
		Hotel registeredHotel = null;
		Hotel hotel = new Hotel(hotelDTO);
		try {
			registeredHotel = hotelService.registerHotel(hotel);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(registeredHotel, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel) throws Exception {
		Hotel updatedHotel = null;
		try {
			updatedHotel = hotelService.updateHotel(hotel);
		}catch(Exception ex){
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
	}
	
	/*
	 * Vracanje hotela za edit od prosledjenog Hotel Admina
	 */
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/hotelToEdit/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHotelToEdit(@PathVariable("username") String username){
		HotelRoomsDTO dto = null;
		try {
			dto = hotelService.getHotelToEdit(username);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*
	 * Vracanje svih soba iz hotela koje nisu rezervisane u odredjenom periodu
	 * (koristi se kod rezervisanja soba)
	 */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value="/getHotelRooms/{id}/{checkInDate}/{checkOutDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> getFreeRooms(@PathVariable("id") Long id,
														@PathVariable("checkInDate") String checkInDate,
														@PathVariable("checkOutDate") String checkOutDate) throws ParseException{
		return new ResponseEntity<>(hotelService.getFreeRooms(id, checkInDate, checkOutDate), HttpStatus.OK);
	}
	
	/*
	 * Vracanje brzih rezervacija hotela (hotelAdmin)
	 */
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/quickRoomReservations/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> getQuickRoomReservations(@PathVariable("hotelId") Long hotelId){
		return new ResponseEntity<>(hotelService.getQuickRoomReservations(hotelId), HttpStatus.OK);
	}
	
	/*
	 * Prikaz brzih rezervacija korisniku za odredjeni period
	 */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value="/quickRoomReservations", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> getQuickRoomReservations(@PathParam("hotelId") Long hotelId,
																			@PathParam("checkInDate") String checkInDate,
																			@PathParam("checkOutDate") String checkOutDate) throws ParseException{
		return new ResponseEntity<>(hotelService.getQuickRoomReservations(hotelId, checkInDate, checkOutDate), HttpStatus.OK);
	}
	
	/*
	 * Dodavanje nove/novih brzih rezervacija hotela (hotelAdmin)
	 */
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@PostMapping(value="/quickRoomReservations/{hotelId}/{dateFrom}/{dateTo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createQuickRoomReservation(@RequestBody QuickRoomResDTO dto, 
														@PathVariable("hotelId") Long hotelId,
														@PathVariable("dateFrom") String dateFrom,
														@PathVariable("dateTo") String dateTo){
		QuickRoomResDTO quickRoomResDto = null;
		try {
			dto = hotelService.createQuickRoomReservation(dto, hotelId, dateFrom, dateTo);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(quickRoomResDto, HttpStatus.CREATED);
	}
	
	/*
	 * Dobavljanje svih izvestaja (osim zarade) za hotel ciji je admin prosledjen
	 */
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/getReport/{adminUsername}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelReportDTO> getReport(@PathVariable("adminUsername") String username) throws ParseException{
		return new ResponseEntity<>(hotelService.getReports(username), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/getIncomeReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> getIncomeReport(@PathParam("adminUsername") String adminUsername,
											@PathParam("dateFrom") String dateFrom,
											@PathParam("dateTo") String dateTo) throws ParseException {
		return new ResponseEntity<>(hotelService.getIncomeReport(adminUsername,  dateFrom,  dateTo), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/dailyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getDailyReport(@PathParam("adminUsername") String adminUsername,
															@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(hotelService.getDailyReport(adminUsername, dateFrom), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/weeklyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> gteWeeklyReport(@PathParam("adminUsername") String adminUsername,
															@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(hotelService.getWeeklyReport(adminUsername, dateFrom), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
	@GetMapping(value="/monthlyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getYearlyReport(@PathParam("adminUsername") String adminUsername,
															@PathParam("numberOfYears") int numberOfYears) throws ParseException{
		return new ResponseEntity<>(hotelService.getYearlyReport(adminUsername, numberOfYears), HttpStatus.OK);
	}
	
	
	
	
	
}
