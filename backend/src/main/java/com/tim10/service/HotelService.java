package com.tim10.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Hotel;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.QuickRoomReservation;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.dto.HotelReportDTO;
import com.tim10.dto.QuickRoomResDTO;
import com.tim10.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	
	public Page<Hotel> findAll(Pageable page) {
		return hotelRepository.findAll(page);
	}
	
	public Hotel registerHotel(Hotel hotel) {
		for(HotelAdmin admin : hotel.getAdministrators()) 
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return hotelRepository.save(hotel);
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
	
	public List<QuickRoomResDTO> getQuickRoomReservations(Long hotelId, String checkInDate, String checkOutDate) throws ParseException{
		List<QuickRoomResDTO> responseList = new ArrayList<>();
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate);
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate);
		for(QuickRoomReservation quickRoomReservation: findOne(hotelId).get().getQuickRoomReservations()) {
			if(quickRoomReservation.getDateFrom().equals(dateFrom) && quickRoomReservation.getDateTo().equals(dateTo))
				responseList.add(new QuickRoomResDTO(quickRoomReservation));
		}
		return responseList;
	}
	
	public HotelReportDTO getReports(Long hotelId) throws ParseException {
		Hotel hotel = findOne(hotelId).get();
		HotelReportDTO hotelReportDTO = new HotelReportDTO(hotel);
		hotelReportDTO.setDailyReports(getDailyReport(hotel, 8));
		return hotelReportDTO;
	}
	
	/*
	 * Trenutno vraca samo prazan odgovor, zato sto su reference na rezervacije svugde null
	 */
	public Map<Date, Integer> getDailyReport(Hotel hotel, int numberOfDays) throws ParseException{
		Map<Date, Integer> dailyReport = new HashMap<Date, Integer>(numberOfDays);
		Calendar cal = Calendar.getInstance();
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date todayDate = new SimpleDateFormat("yyyy-MM-dd").parse(todayString);
		
		cal.add(Calendar.DATE, -numberOfDays);
		String previousString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date previousDate = new SimpleDateFormat("yyyy-MM-dd").parse(previousString);
		
		for(int i = 0; i < numberOfDays; i++) {
			Calendar newCal = Calendar.getInstance();
			newCal.add(Calendar.DATE, -i);
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(newCal.getTime());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			dailyReport.put(date, 0);
		}
		
		for(Room room : hotel.getRooms()) {
			for(RoomReservation roomReservation : room.getRoomReservations()) {
				if(roomReservation.getReservation() != null) {
					if(isBetween(previousDate, todayDate, roomReservation.getDateFrom())) {
						Integer value = dailyReport.get(roomReservation.getDateFrom());
						dailyReport.put(roomReservation.getDateFrom(), ++value);
					}
				}
			}
		}
		return dailyReport;
	}
	
	public boolean isBetween(Date dateFrom, Date dateTo, Date checkInDate) {
		if(checkInDate.equals(dateFrom) || checkInDate.equals(dateTo))
			return true;
		else if(checkInDate.after(dateFrom) && checkInDate.before(dateTo))
			return true;
		return false;
	}
}
