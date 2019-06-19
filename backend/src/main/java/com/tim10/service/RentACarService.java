package com.tim10.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.naming.NoPermissionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.RentACar;
import com.tim10.domain.RentACarAdmin;
import com.tim10.dto.RentACarDTO;
import com.tim10.dto.RentACarReportDTO;
import com.tim10.repository.RentACarRepository;
import com.tim10.repository.UserRepository;

@Service
public class RentACarService {
	
	@Autowired
	private RentACarRepository rentACarRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	
		
	public RentACarDTO getRentACarById(Long rentACarId)throws ResourceNotFoundException {
		
		Optional<RentACar> rac =  rentACarRepository.findById(rentACarId);
		if(!rac.isPresent()) 
			throw new ResourceNotFoundException("Rent-A-Car with id: "+rentACarId+" doesn't exist!");
		
		RentACar rnc = rac.get();
		
		RentACarDTO rdto = new RentACarDTO();
		rdto.setId(rnc.getId());
		rdto.setName(rnc.getName());
		rdto.setDescription(rnc.getDescription());
		rdto.setAdditionalServicesPriceList(rnc.getAdditionalServicesPriceList());
		
		
		return rdto;
		
	}
	
	
	public List<RentACar> findAll(){
		return rentACarRepository.findAll();
	}
	
	public Page<RentACar> findAll(Pageable page){
		return rentACarRepository.findAll(page);
	}
	
	public Optional<RentACar> findById(Long id) {
		return rentACarRepository.findById(id);
	}
	
	public RentACar registerRentACar(RentACar rentacar) throws Exception {
		for(RentACarAdmin admin : rentacar.getAdministrators()) {
			if(userRepository.findOneByUsername(admin.getUsername()).isPresent()) 
				throw new Exception("User with username: " + admin.getUsername() + " already exists");
			else if(userRepository.findOneByEmail(admin.getEmail()).isPresent())
				throw new Exception("User with email: " + admin.getEmail() + " already exists");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			admin.setCompany(rentacar);
		}
		return save(rentacar);
	}
	
	public RentACar save(RentACar rentACar) {
		return rentACarRepository.save(rentACar);
	}
	
	
	public Optional<RentACar> findOneByName (String name) {
		return rentACarRepository.findOneByName(name);
	}
	
	public void deleteById(Long id) throws Exception {
		if(!rentACarRepository.existsById(id)) 
			throw new ResourceNotFoundException("Rent=A=Car with id: "+id+" doesn't exist!");
		rentACarRepository.deleteById(id);
	}
	
	public boolean existsById(Long id) {
		return rentACarRepository.existsById(id);
	}

	
	//REPORTS-----------------------

	public RentACarReportDTO getReports(Long rentACarId) throws ParseException {
		RentACar rentACar = rentACarRepository.findById(rentACarId).get();
		RentACarReportDTO racReportDTO = new RentACarReportDTO(rentACar);
		//RentACarReportDTO.setNumberOfFeedbacks(rentACarRepository.getNumberOfFeedbacks(rentACarId));
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		//racReportDTO.setDailyReports(getDailyReport(rentACarId, todayString));	//nisam upisvao vreme kreiranja rezervacije
		racReportDTO.setWeeklyReports(getWeeklyReport(rentACarId, todayString));
		racReportDTO.setYearlyReport(getYearlyReport(rentACarId, 0));
		return racReportDTO;
	}

	
	public Map<Long, Integer> getDailyReport(Long rentACarId, String fromDate) throws ParseException{
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
		
		List<Date> lista = rentACarRepository.getVehicleReservationsFromDates(rentACarId, previousString, fromDate);
		for(Date datum : lista) {
			Integer value = dailyReport.get(datum.getTime());
			dailyReport.put(datum.getTime(), ++value);
		}
		return dailyReport;
	}
	
	public Map<Long, Integer> getWeeklyReport(Long rentACarId, String fromDate) throws ParseException {
		Map<Long, Integer> weeklyReport = new TreeMap<Long, Integer>();
	    Calendar c = Calendar.getInstance();
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);

	    c.setTime(dateFrom);
	    int diff = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    List<Date> listOfReservations;
	    String start, end;
	    
	    //postavi kalendar na ponedeljak trenutne nedelje
	    c.add(Calendar.DATE, -diff);
	    
		for(int i = 0; i < 12; i++) {
			c.add(Calendar.DATE, -7);
			
			start = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			Date monday = c.getTime();
			
			//dodajemo na ponedeljak da dobijemo nedelju
		    c.add(Calendar.DATE, 6);
		    end = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		    
		    listOfReservations = rentACarRepository.getVehicleReservationsFromDates(rentACarId, start, end);
		    weeklyReport.put(monday.getTime(), listOfReservations.size());
		    	 
		    c.add(Calendar.DATE, -6);
		}
		return weeklyReport;
	}
	
	public Map<Long, Integer> getYearlyReport(Long rentACarId, int numberOfYears){
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
			
			listOfReservations =  rentACarRepository.getVehicleReservationsFromDates(rentACarId, start, end);
		    yearlyReport.put(cal.getTime().getTime(), listOfReservations.size());
			
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		}
		
		return yearlyReport;
	}

	public BigDecimal getIncomeReport(Long rentACarId, String stringFrom, String stringTo) throws ParseException {
		return rentACarRepository.getIncomeReport(rentACarId, stringFrom, stringTo);
	}
		
	//-----------------------------
	
	
	public Long getRentACarFromAdmin() throws ClassCastException, Exception {
		
		RentACarAdmin currentRentACarAdmin;
		try {
			currentRentACarAdmin = (RentACarAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (ClassCastException e) {
			
			throw new ClassCastException("Current user isn't RentACarAdmin");
		}
		
		
		
		if(currentRentACarAdmin == null ) {
			throw new NoPermissionException("You are unauthorized to do this.");
		}
		Long rentACarId = userRepository.getRentACarIdFromUser(currentRentACarAdmin.getId());
		
		return rentACarId;
		
		
	}
	

}
