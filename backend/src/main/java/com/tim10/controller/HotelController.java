package com.tim10.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tim10.domain.QuickRoomReservation;
import com.tim10.dto.HotelDTO;
import com.tim10.dto.HotelReportDTO;
import com.tim10.dto.HotelRoomsDTO;
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
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels() {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll())
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	/*
	 * GET hotela sa paginacijom
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels(Pageable page) {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll(page))
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel){
		Hotel registeredHotel = null;
		try {
			registeredHotel = hotelService.registerHotel(hotel);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(registeredHotel, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	 * ZA IZMENU: Lokacija!!!!
	 * Pretraga hotela po nazivu i lokaciji. Vraca stranice.
	 */
	@RequestMapping(value="/searchHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> searchHotels(Pageable page,
														@PathParam("hotelName") String hotelName,
														@PathParam("hotelLocation") String hotelLocation) {
		return new ResponseEntity<>(hotelService.searchHotels(page, hotelName, hotelLocation), HttpStatus.OK);
	}
	
	/*
	 * Za sad se koristi samo pri rezervaciji hotela, fetchuje sve hotele (po stranicama) 
	 */
//	@RequestMapping(value = "/pageHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<HotelDTO>> getResHotelsPage(Pageable page) {
//		Page<Hotel> pageHotels = hotelService.findAll(page);
//		List<HotelDTO> hotelsDTO = new ArrayList<>();
//		for(Hotel h : pageHotels) 
//			hotelsDTO.add(new HotelDTO(h));
//		return new ResponseEntity<>(hotelsDTO, HttpStatus.OK);
//	}
	
	/* 
	 * Vracanje svih soba iz hotela (koristi se kod editHotel)
	 */
	@RequestMapping(value="/getHotelRooms/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHotelRooms(@PathVariable("id") Long id){
		HotelRoomsDTO dto = null;
		try {
			dto = hotelService.getHotelRooms(id);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/*
	 * Vracanje svih soba iz hotela koje nisu rezervisane u odredjenom periodu
	 * (koristi se kod rezervisanja soba)
	 */
	@RequestMapping(value="/getHotelRooms/{id}/{checkInDate}/{checkOutDate}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> getFreeRooms(@PathVariable("id") Long id,
														@PathVariable("checkInDate") String checkInDate,
														@PathVariable("checkOutDate") String checkOutDate) throws ParseException{
		return new ResponseEntity<>(hotelService.getFreeRooms(id, checkInDate, checkOutDate), HttpStatus.OK);
	}
	
	/*
	 * Vracanje brzih rezervacija hotela (hotelAdmin)
	 */
	@RequestMapping(value="/quickRoomReservations/{hotelId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> getQuickRoomReservations(@PathVariable("hotelId") Long hotelId){
		return new ResponseEntity<>(hotelService.getQuickRoomReservations(hotelId), HttpStatus.OK);
	}
	
	/*
	 * Prikaz brzih rezervacija korisniku za odredjeni period
	 */
	@RequestMapping(value="/quickRoomReservations", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> getQuickRoomReservations(@PathParam("hotelId") Long hotelId,
																			@PathParam("checkInDate") String checkInDate,
																			@PathParam("checkOutDate") String checkOutDate) throws ParseException{
		return new ResponseEntity<>(hotelService.getQuickRoomReservations(hotelId, checkInDate, checkOutDate), HttpStatus.OK);
	}
	
	/*
	 * Dodavanje nove/novih brzih rezervacija hotela (hotelAdmin)
	 */
	@RequestMapping(value="/quickRoomReservations/{hotelId}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> setQuickRoomReservations(@RequestBody Set<QuickRoomReservation> quickRoomReservations, @PathVariable("hotelId") Long hotelId){	
		return new ResponseEntity<>(hotelService.setQuickRoomReservations(quickRoomReservations, hotelId), HttpStatus.OK);
	}
	
	/*
	 * Dobavljanje svih izvestaja (osim zarade) za hotel sa prosledjenim id-jem
	 */
	@RequestMapping(value="/getReport/{hotelId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelReportDTO> getReport(@PathVariable("hotelId") Long hotelId) throws ParseException{
		return new ResponseEntity<>(hotelService.getReports(hotelId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getIncomeReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> getIncomeReport(@PathParam("hotelId") Long hotelId,
											@PathParam("dateFrom") String dateFrom,
											@PathParam("dateTo") String dateTo) throws ParseException {
		return new ResponseEntity<>(hotelService.getIncomeReport(hotelId,  dateFrom,  dateTo), HttpStatus.OK);
	}
	
	@RequestMapping(value="/dailyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getDailyReport(@PathParam("hotelId") Long hotelId,
															@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(hotelService.getDailyReport(hotelService.findOne(hotelId).get(), dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(value="/weeklyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> gteWeeklyReport(@PathParam("hotelId") Long hotelId,
															@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(hotelService.getWeeklyReport(hotelService.findOne(hotelId).get(), dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(value="/monthlyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getYearlyReport(@PathParam("hotelId") Long hotelId,
															@PathParam("numberOfYears") int numberOfYears) throws ParseException{
		return new ResponseEntity<>(hotelService.getYearlyReport(hotelService.findOne(hotelId).get(), numberOfYears), HttpStatus.OK);
	}
	
	
	
	
	
}
