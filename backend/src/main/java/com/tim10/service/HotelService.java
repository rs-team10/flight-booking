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
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Hotel;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.QuickRoomReservation;
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
	
	public Hotel registerHotel(Hotel hotel) {
		for(HotelAdmin admin : hotel.getAdministrators()) 
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return hotelRepository.save(hotel);
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
		hotelReportDTO.setNumberOfFeedbacks(hotelRepository.getNumberOfFeedbacks(hotelId));
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		hotelReportDTO.setDailyReports(getDailyReport(hotel, todayString));	//od danasnjeg datuma
		hotelReportDTO.setWeeklyReports(getWeeklyReport(hotel, todayString));
		hotelReportDTO.setYearlyReport(getYearlyReport(hotel, 0));
		return hotelReportDTO;
	}
	
	public Map<Long, Integer> getDailyReport(Hotel hotel, String fromDate) throws ParseException{
		//8 prethodnih dana
		Map<Long, Integer> dailyReport = new TreeMap<Long, Integer>();
		Calendar cal = Calendar.getInstance();
		
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		cal.setTime(dateFrom);
		
		cal.add(Calendar.DATE, -12);
		String previousString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		for(int i = 0; i < 12; i++) {
			Calendar newCal = Calendar.getInstance();
			newCal.add(Calendar.DATE, -i);
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(newCal.getTime());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			dailyReport.put(date.getTime(), 0);
		}
		
		List<Date> lista = hotelRepository.getRoomReservations(hotel.getId(), previousString, fromDate);
		for(Date datum : lista) {
			Integer value = dailyReport.get(datum.getTime());
			dailyReport.put(datum.getTime(), ++value);
		}
		return dailyReport;
	}
	
	public Map<Long, Integer> getWeeklyReport(Hotel hotel, String fromDate) throws ParseException {
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
	
	public Map<Long, Integer> getYearlyReport(Hotel hotel, int numberOfYears){
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
	
//	public boolean isBetween(Date dateFrom, Date dateTo, Date checkInDate) {
//		if(checkInDate.equals(dateFrom) || checkInDate.equals(dateTo))
//			return true;
//		else if(checkInDate.after(dateFrom) && checkInDate.before(dateTo))
//			return true;
//		return false;
//	}
	
	public BigDecimal getIncomeReport(Long hotelId, String stringFrom, String stringTo) throws ParseException {
		return hotelRepository.getIncomeReport(hotelId, stringFrom, stringTo);
	}
}
