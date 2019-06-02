package com.tim10.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.websocket.server.PathParam;

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
import com.tim10.domain.QuickRoomReservation;
import com.tim10.domain.Room;
import com.tim10.dto.HotelDTO;
import com.tim10.dto.HotelReportDTO;
import com.tim10.dto.HotelRoomsDTO;
import com.tim10.dto.QuickRoomResDTO;
import com.tim10.dto.RoomDTO;
import com.tim10.service.HotelService;
import com.tim10.service.UserService;

@RestController
@RequestMapping(value="/api/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired 
	private UserService userService;
	
	/*
	 * ZA IZMENU (DODATI PAGINACIJU)
	 * za sad sluzi za prikaz svih hotela u ViewHotels
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getHotels() {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h: hotelService.findAll())
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<List<HotelDTO>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel){
		if(!hotelService.findOneByName(hotel.getName()).isPresent()) {
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
		Hotel existingHotel = hotelService.findOneByName(hotel.getName()).get();
		if(existingHotel != null && existingHotel.getId() != hotel.getId())
			return new ResponseEntity<>("Hotel with that name already exists!", HttpStatus.FORBIDDEN);
		
		Optional<Hotel> h = hotelService.findOne(hotel.getId());
		if(h.isPresent()){
			hotel.setAdministrators(h.get().getAdministrators());
			hotel.setQuickRoomReservations(h.get().getQuickRoomReservations());
			Hotel returnHotel = hotelService.save(hotel);
			return new ResponseEntity<>(returnHotel, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wanted hotel does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	 * Pretraga hotela po nazivu i lokaciji. Vraca stranice
	 */
	@RequestMapping(value="/searchHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> searchHotels(Pageable page,
														@PathParam("hotelName") String hotelName,
														@PathParam("hotelLocation") String hotelLocation) {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h : hotelService.findByParameter(page, hotelName, hotelLocation))
			dtos.add(new HotelDTO(h));
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
	
	/*
	 * Za sad se koristi samo pri rezervaciji hotela, fetchuje sve hotele (po stranicama) 
	 */
	@RequestMapping(value = "/pageHotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDTO>> getResHotelsPage(Pageable page) {
		Page<Hotel> pageHotels = hotelService.findAll(page);
		List<HotelDTO> hotelsDTO = new ArrayList<>();
		for(Hotel h : pageHotels) 
			hotelsDTO.add(new HotelDTO(h));
		return new ResponseEntity<>(hotelsDTO, HttpStatus.OK);
	}
	
	/* 
	 * Vracanje svih soba iz hotela (koristi se kod editHotel)
	 */
	@RequestMapping(value="/getHotelRooms/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelRoomsDTO> getHotelRooms(@PathVariable("id") Long id){
		Hotel hotel = hotelService.findOne(id).get();
		return new ResponseEntity<>(new HotelRoomsDTO(hotel), HttpStatus.OK);
	}

	/*
	 * Vracanje svih soba iz hotela koje nisu rezervisane u odredjenom periodu
	 * (koristi se kod rezervisanja soba)
	 */
	@RequestMapping(value="/getHotelRooms/{id}/{checkInDate}/{checkOutDate}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> getHotelRooms(@PathVariable("id") Long id,
														@PathVariable("checkInDate") String checkInDate,
														@PathVariable("checkOutDate") String checkOutDate) throws ParseException{
		Set<Room> rooms = hotelService.findOne(id).get().getRooms();
		List<RoomDTO> responseRooms = new ArrayList<>();
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate);
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate);
		
		for(Room r : rooms) {
			if(!r.isReserved(dateFrom, dateTo))
				responseRooms.add(new RoomDTO(r));
		}
		return new ResponseEntity<>(responseRooms, HttpStatus.OK);
	}
	
	/*
	 * Vracanje brzih rezervacija hotela (hotelAdmin)
	 */
	@RequestMapping(value="/quickRoomReservations/{hotelId}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickRoomResDTO>> getAllQuickRoomReservations(@PathVariable("hotelId") Long hotelId){
		List<QuickRoomResDTO> responseList = new ArrayList<>();
		Set<QuickRoomReservation> quickRoomReservations = hotelService.findOne(hotelId).get().getQuickRoomReservations();
		
		for(QuickRoomReservation res : quickRoomReservations) {
			responseList.add(new QuickRoomResDTO(res));
		}
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	/*
	 * Dodavanje nove/novih brzih rezervacija hotela (hotelAdmin)
	 */
	@RequestMapping(value="/quickRoomReservations/{hotelId}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setQuickRoomReservations(@RequestBody Set<QuickRoomReservation> quickRoomReservations, @PathVariable("hotelId") Long hotelId){
		Hotel hotel = hotelService.findOne(hotelId).get();
		hotel.setQuickRoomReservations(quickRoomReservations);
		hotelService.save(hotel);	
		return new ResponseEntity<>(HttpStatus.OK);
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
