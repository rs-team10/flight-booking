package com.tim10.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Hotel;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.QuickRoomReservation;
import com.tim10.domain.Room;
import com.tim10.dto.HotelDTO;
import com.tim10.dto.HotelReportDTO;
import com.tim10.dto.HotelRoomsDTO;
import com.tim10.dto.QuickRoomResDTO;
import com.tim10.dto.RoomDTO;
import com.tim10.repository.HotelAdminRepository;
import com.tim10.repository.HotelRepository;
import com.tim10.repository.RoomRepository;
import com.tim10.repository.UserRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HotelAdminRepository hotelAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	
	public Page<Hotel> findAll(Pageable page) {
		return hotelRepository.findAll(page);
	}
	
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	public Optional<Hotel> findOneByName(String name) {
		return hotelRepository.findOneByName(name);
	}
	
	public Optional<Hotel> findOne(Long id) {
		return hotelRepository.findById(id);
	}
	
	public Page<Hotel> findByParameter(Pageable pageable, String hotelName, String hotelLocation){
		return hotelRepository.findByParameter(pageable, hotelName, hotelLocation);
	}
	
	public Hotel registerHotel(Hotel hotel) throws Exception {
		for(HotelAdmin admin : hotel.getAdministrators()) {
			if(userRepository.findOneByUsername(admin.getUsername()).isPresent()) 
				throw new Exception("User with username: " + admin.getUsername() + " already exists");
			else if(userRepository.findOneByEmail(admin.getEmail()).isPresent())
				throw new Exception("User with email: " + admin.getEmail() + " already exists");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			admin.setHotel(hotel);
		}
		return save(hotel);
	}
	
	public Hotel updateHotel(Hotel hotel) throws Exception {
		Optional<Hotel> h = findOne(hotel.getId());
		if(h.isPresent()) {
			hotel.setAdministrators(h.get().getAdministrators());
			hotel.setQuickRoomReservations(h.get().getQuickRoomReservations());
				
			return save(hotel);
		}
		throw new Exception("Hotel doesn't exist");
	}
	
	public HotelRoomsDTO getHotelToEdit(String username) {
		HotelAdmin hotelAdmin = hotelAdminRepository.findOneByUsername(username).get();
		return new HotelRoomsDTO(hotelAdmin.getHotel());
	}
	
	public List<HotelDTO> searchHotels(Pageable page, String hotelName, String hotelLocation) {
		List<HotelDTO> dtos = new ArrayList<HotelDTO>();
		for(Hotel h : findByParameter(page, hotelName, hotelLocation)) {
			HotelDTO dto = new HotelDTO(h);
			dto.setNumberOfFeedbacks(hotelRepository.getNumberOfFeedbacks(h.getId()));
			dtos.add(dto);
		}
		return dtos;
	}
	
	public List<RoomDTO> getFreeRooms(Long id, String checkInDate, String checkOutDate) throws ParseException {
		Set<Room> rooms = findOne(id).get().getRooms();
		List<RoomDTO> responseRooms = new ArrayList<>();
		
		//Bilo bi brze preko upita, neka ga za sad ovako
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate);
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate);
		for(Room r : rooms) {
			if(!r.isReserved(dateFrom, dateTo))
				responseRooms.add(new RoomDTO(r));
		}
		return responseRooms;
		
	}

	public List<QuickRoomResDTO> getQuickRoomReservations(Long hotelId) {
		List<QuickRoomResDTO> responseList = new ArrayList<>();
		Set<QuickRoomReservation> quickRoomReservations = findOne(hotelId).get().getQuickRoomReservations();
		for(QuickRoomReservation res : quickRoomReservations) {
			responseList.add(new QuickRoomResDTO(res));
		}
		return responseList;
	}
	
	public List<QuickRoomResDTO> getQuickRoomReservations(Long hotelId, String checkInDate, String checkOutDate) throws ParseException{
		List<QuickRoomResDTO> responseList = new ArrayList<>();
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate);
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate);
		
		//TODO: Napisati query da proverava da li quick room res id se nalazi u rezervacijama
		//ako se nalazi, ne dodavati je
		for(QuickRoomReservation quickRoomReservation: findOne(hotelId).get().getQuickRoomReservations()) {
			if(quickRoomReservation.getDateFrom().equals(dateFrom) && quickRoomReservation.getDateTo().equals(dateTo)) {
				if(quickRoomReservation.getReservation() != null)
					responseList.add(new QuickRoomResDTO(quickRoomReservation));
			}
		}
		return responseList;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public QuickRoomResDTO createQuickRoomReservation(QuickRoomResDTO dto, Long hotelId, String checkInDate, String checkOutDate) throws ParseException {
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate);
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate);
		
		Optional<Room> repoRoom = roomRepository.findOneById(dto.getRoom().getId());
		if(!repoRoom.isPresent()) 
			throw new EntityNotFoundException("Room not found");
		Room room = repoRoom.get();
		
		if(room.isReserved(dto.getDateFrom(), dto.getDateTo()))
			throw new RollbackException("Room is already reserved in that period");
		
		QuickRoomReservation qrr = new QuickRoomReservation();
		qrr.setDiscount(dto.getDiscount());
		qrr.setDateFrom(dateFrom);
		qrr.setDateTo(dateTo);
		qrr.setTotalPrice(dto.getTotalPrice());
		qrr.setAdditionalServices(dto.getAdditionalServices());
		qrr.setRoom(room);
		
		Optional<Hotel> repoHotel = hotelRepository.findById(hotelId);
		if(!repoHotel.isPresent())
			throw new EntityNotFoundException("Hotel not found");
		Hotel hotel = repoHotel.get();
		hotel.getQuickRoomReservations().add(qrr);
		hotelRepository.save(hotel);
		return new QuickRoomResDTO(qrr);
		
	}
	
//	public List<QuickRoomResDTO> setQuickRoomReservations(Set<QuickRoomResDTO> quickRoomReservations, Long hotelId) {
//		Hotel hotel = findOne(hotelId).get();
//		for(QuickRoomResDTO dto : quickRoomReservations) {
//			QuickRoomReservation qrr = new QuickRoomReservation();
//			qrr.setId(dto.getId());
//			qrr.setDateFrom(dto.getDateFrom());
//			qrr.setDateTo(dto.getDateTo());
//			qrr.setTotalPrice(dto.getTotalPrice());
//			qrr.setAdditionalServices(dto.getAdditionalServices());
//			qrr.setRoom(dto.getRoom());
//		}
//		hotel.setQuickRoomReservations(quickRoomReservations);
//		save(hotel);
//		List<QuickRoomResDTO> responseList = new ArrayList<>();
//		for(QuickRoomReservation qrr : quickRoomReservations) 
//			responseList.add(new QuickRoomResDTO(qrr));
//		
//		return responseList;
//	}
	
	public HotelReportDTO getReports(String adminUsername) throws ParseException {
		Hotel hotel = hotelAdminRepository.findOneByUsername(adminUsername).get().getHotel();

		//Hotel hotel = findOne(hotelId).get();
		HotelReportDTO hotelReportDTO = new HotelReportDTO(hotel);
		hotelReportDTO.setNumberOfFeedbacks(hotelRepository.getNumberOfFeedbacks(hotel.getId()));
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		hotelReportDTO.setDailyReports(getDailyReport(adminUsername, todayString));	//od danasnjeg datuma
		hotelReportDTO.setWeeklyReports(getWeeklyReport(adminUsername, todayString));
		hotelReportDTO.setYearlyReport(getYearlyReport(adminUsername, 0));
		return hotelReportDTO;
	}
	
	public Map<Long, Integer> getDailyReport(String adminUsername, String fromDate) throws ParseException{
		Hotel hotel = hotelAdminRepository.findOneByUsername(adminUsername).get().getHotel();
		
		//8 prethodnih dana
		Map<Long, Integer> dailyReport = new TreeMap<Long, Integer>();
		Calendar cal = Calendar.getInstance();
		
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		cal.setTime(dateFrom);
		
		cal.add(Calendar.DATE, -12);
		String previousString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(dateFrom);
		for(int i = 0; i < 12; i++) {
			newCal.add(Calendar.DATE, -1);
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(newCal.getTime());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			dailyReport.put(date.getTime(), 0);
		}
		
		List<Date> lista = hotelRepository.getRoomReservations(hotel.getId(), previousString, fromDate);
		for(Date datum : lista) {
			String tempDateString = new SimpleDateFormat("yyyy-MM-dd").format(datum);
			Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(tempDateString);
			
			Integer value = dailyReport.get(tempDate.getTime());
			dailyReport.put(tempDate.getTime(), ++value);
		}
		return dailyReport;
	}
	
	public Map<Long, Integer> getWeeklyReport(String adminUsername, String fromDate) throws ParseException {
		Hotel hotel = hotelAdminRepository.findOneByUsername(adminUsername).get().getHotel();
		
		Map<Long, Integer> weeklyReport = new TreeMap<Long, Integer>();
	    Calendar c = Calendar.getInstance();
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);

	    c.setTime(dateFrom);
	    int diff = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    List<Date> listOfReservations;
	    String start, end;
	    
	    //postavi kalendar na ponedeljak trenutne nedelje
	    c.add(Calendar.DATE, -diff);
	    String temp = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    
		for(int i = 0; i < 12; i++) {
			c.add(Calendar.DATE, -7);
			
			start = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			Date monday = c.getTime();
			
			//dodajemo na ponedeljak da dobijemo nedelju
		    c.add(Calendar.DATE, 6);
		    end = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		    
		    listOfReservations = hotelRepository.getRoomReservations(hotel.getId(), start, end);
		    weeklyReport.put(monday.getTime(), listOfReservations.size());
		    	 
		    c.add(Calendar.DATE, -6);
		}
		return weeklyReport;
	}
	
	public Map<Long, Integer> getYearlyReport(String adminUsername, int numberOfYears){
		Hotel hotel = hotelAdminRepository.findOneByUsername(adminUsername).get().getHotel();
		
		Map<Long, Integer> yearlyReport = new TreeMap<Long, Integer>();
		
		Calendar cal = Calendar.getInstance();
		String start, end;
		List<Date> listOfReservations;
		
		cal.add(Calendar.YEAR, -numberOfYears);
		for(int i = 0; i < 12; i++) {
			cal.set(Calendar.MONTH, i);
			start = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			end = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			listOfReservations = hotelRepository.getRoomReservations(hotel.getId(), start, end);
		    yearlyReport.put(cal.getTime().getTime(), listOfReservations.size());
			
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		}
		
		return yearlyReport;
	}

	public BigDecimal getIncomeReport(String adminUsername, String stringFrom, String stringTo) throws ParseException {
		Hotel hotel = hotelAdminRepository.findOneByUsername(adminUsername).get().getHotel();
		return hotelRepository.getIncomeReport(hotel.getId(), stringFrom, stringTo);
	}
}
