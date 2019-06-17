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

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.Destination;
import com.tim10.domain.Location;
import com.tim10.domain.PriceListItem;
import com.tim10.domain.QuickFlightReservation;
import com.tim10.dto.AirlineProfileDTO;
import com.tim10.dto.AirlineReportDTO;
import com.tim10.dto.DestinationDTO;
import com.tim10.dto.PriceListItemDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.QuickFlightReservationRepository;
import com.tim10.repository.UserRepository;

@Service
public class AirlineService {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private QuickFlightReservationRepository quickFlightReservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
	}
	
	public Airline registerAirline(Airline airline) throws Exception {
		for(AirlineAdmin admin : airline.getAdministrators()) {
			if(userRepository.findOneByUsername(admin.getUsername()).isPresent())
				throw new Exception("User with username: " + admin.getUsername() + " already exists");
			else if(userRepository.findOneByEmail(admin.getEmail()).isPresent())
				throw new Exception("User with email: " + admin.getEmail() + " already exists");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		}
		return save(airline);
	}
	
	public Airline save(Airline airline) {
		return airlineRepository.save(airline);
	}
	
	public Airline findOneByName(String name) {
		return airlineRepository.findOneByName(name);
	}
	
	public Optional<Airline> findOne(Long id) {
		return airlineRepository.findById(id);
	}

	// ===========================================================================
	
	// ===========================================================================

	public ResponseEntity<AirlineProfileDTO> getCurrentAdminAirline() {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		AirlineProfileDTO airlineProfileDTO = new AirlineProfileDTO(airline);
		return new ResponseEntity<AirlineProfileDTO>(airlineProfileDTO, HttpStatus.OK);
	}

	public ResponseEntity<?> updateAirline(Airline editedAirline, String previousAirlineName) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		// tada ce moci da se izbaci previousAirlineName jer korisnik moze da menja
		// samo svoju aviokompaniju
		Airline existingAirline = airlineRepository.findOneByName(previousAirlineName);
		
		if(existingAirline == null)
			return new ResponseEntity<>("Airline not found.", HttpStatus.BAD_REQUEST);
		if(editedAirline.getName() != null && !editedAirline.getName().equals(existingAirline.getName())) {
			if(airlineRepository.findOneByName(editedAirline.getName()) != null)
				return new ResponseEntity<>("Airline name already in use.", HttpStatus.BAD_REQUEST);
			existingAirline.setName(editedAirline.getName());
		}
		if(editedAirline.getDescription() != null)
			existingAirline.setDescription(editedAirline.getDescription());
		if(editedAirline.getLocation().getCountry() != null && editedAirline.getLocation().getCity() != null && editedAirline.getLocation().getStreet() != null) {
			Location updatedLocation = editedAirline.getLocation();
			existingAirline.getLocation().setCountry(updatedLocation.getCountry());
			existingAirline.getLocation().setCity(updatedLocation.getCity());
			existingAirline.getLocation().setStreet(updatedLocation.getStreet());
			existingAirline.getLocation().setformattedAddress(updatedLocation.getformattedAddress());
			existingAirline.getLocation().setLatitude(updatedLocation.getLatitude());
			existingAirline.getLocation().setLongitude(updatedLocation.getLongitude());
		}
		
		try {
			airlineRepository.save(existingAirline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<DestinationDTO>> getBusinessLocations() {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<DestinationDTO> businessLocationsList = new ArrayList<DestinationDTO>();
		
		for (Destination destination : airline.getBusinessLocations()) {
			businessLocationsList.add(new DestinationDTO(destination));
		}
		
		return new ResponseEntity<List<DestinationDTO>>(businessLocationsList, HttpStatus.OK);
	}
	
	public ResponseEntity<?> addBusinessLocation(Destination destination) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// TODO: Proveriti da li vec postoji lokacija sa istim nazivom i airport Code
		airline.getBusinessLocations().add(destination);
		
		try {
			airlineRepository.save(airline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> removeBusinessLocation(DestinationDTO destinationDTO) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// TODO : Mora da postoji neki drugi nacin za ovo
		Destination destinationToRemove = null;
		for (Destination d : airline.getBusinessLocations()) {
			if(d.getName().equals(destinationDTO.getName()) && d.getAirportCode().equals(destinationDTO.getAirportCode()))
				destinationToRemove = d;
		}
		
		// TODO: Proveriti da li postoje letovi koji su vezani za ovu lokaciju, ukoliko ne postoje, obrisati
		if(destinationToRemove != null)
			airline.getBusinessLocations().remove(destinationToRemove);
		
		try {
			airlineRepository.save(airline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<PriceListItemDTO>> getPriceListItems() {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<PriceListItemDTO> priceListItems = new ArrayList<PriceListItemDTO>();
		
		for (PriceListItem item : airline.getLuggagePriceList().getPriceListItems()) {
			priceListItems.add(new PriceListItemDTO(item));
		}
		
		return new ResponseEntity<List<PriceListItemDTO>>(priceListItems, HttpStatus.OK);
	}
	
	public ResponseEntity<?> addPriceListItem(PriceListItem item) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		airline.getLuggagePriceList().getPriceListItems().add(item);
		
		try {
			airlineRepository.save(airline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<?> removePriceListItem(PriceListItemDTO itemDTO) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// TODO : Mora da postoji neki drugi nacin za ovo
		PriceListItem itemToRemove = null;
		for (PriceListItem i : airline.getLuggagePriceList().getPriceListItems()) {
			if(i.getName().equals(itemDTO.getName()))
				itemToRemove = i;
		}
		
		// TODO: Proveriti da li postoje rezervacije koje referenciraju ovaj price list item, ukoliko ne postoje, obrisati
		if(itemToRemove != null)
			airline.getLuggagePriceList().getPriceListItems().remove(itemToRemove);
		
		try {
			airlineRepository.save(airline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<QuickFlightReservationDTO>> getQuickFlightReservations() {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<QuickFlightReservationDTO> dtos = new ArrayList<QuickFlightReservationDTO>();
		
		for (QuickFlightReservation item : airline.getQuickFlightReservations()) {
			
			QuickFlightReservationDTO dto = new QuickFlightReservationDTO();
			
			dto.setId(item.getId());
			dto.setSeatId(item.getSeat().getId());
			dto.setSeatNumber(item.getSeat().getRed().toString() + (char)(item.getSeat().getKolona() + 64));
			dto.setDiscount(item.getDiscount());
			
			dto.setFlightNumber(item.getSeat().getFlight().getFlightNumber());
			dto.setDeparture(item.getSeat().getFlight().getDeparture().getName());
			dto.setDestination(item.getSeat().getFlight().getDestination().getName());
			dto.setDate(item.getSeat().getFlight().getDepartureDate().toString());
			dto.setOriginalPrice(item.getSeat().getFlight().getTicketPrice());
			
			dtos.add(dto);
		}
		
		return new ResponseEntity<List<QuickFlightReservationDTO>>(dtos, HttpStatus.OK);
		
	}

	public ResponseEntity<?> deleteQuickFlightReservation(QuickFlightReservationDTO dto) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		
		if(airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		// TODO : Mora da postoji neki drugi nacin za ovo
		QuickFlightReservation itemToRemove = null;
		for (QuickFlightReservation i : airline.getQuickFlightReservations()) {
			if(i.getId().equals(dto.getId()))
				itemToRemove = i;
		}
		
		// TODO: Proveriti !!!
		if(itemToRemove != null) {
			if(itemToRemove.getPassengerFirstName().equals(null) && itemToRemove.getPassengerLastName().equals(null) && itemToRemove.getPassportNumber().equals(null)) {
				airline.getQuickFlightReservations().remove(itemToRemove);
				quickFlightReservationRepository.delete(itemToRemove);
			}
		}
		
		try {
			airlineRepository.save(airline);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PersistenceException e) {
			return new ResponseEntity<>("Error occured while trying to save entity to DB.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	// ==============================================================================
	// REPORTS
	// ==============================================================================
	

	public AirlineReportDTO getReports() {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			Airline airline = repoAirline.get();
			
			AirlineReportDTO dto = new AirlineReportDTO(airline);
			dto.setNumberOfFeedbacks(airlineRepository.getNumberOfFeedbacks(airline.getId()));
			
			String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			dto.setAverageFeedback(getAverageFeedback());
			dto.setDailyReports(getDailyReport(todayString));
			dto.setWeeklyReports(getWeeklyReport(todayString));
			dto.setYearlyReports(getYearlyReport(0));
			
			return dto;
		}
		
		return null;
	}
	
	public Double getAverageFeedback() {
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			Airline airline = repoAirline.get();
			
			return airlineRepository.getAverageFeedback(airline.getId());
		}
		return null;
		
	}

	public BigDecimal getIncomeReport(String dateFrom, String dateTo) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			Airline airline = repoAirline.get();
			
			return airlineRepository.getIncomeReport(airline.getId(), dateFrom, dateTo);
		}
		return null;
	}

	public Map<Long, Integer> getDailyReport(String dateFrom) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			
			Airline airline = repoAirline.get();
			
			Map<Long, Integer> dailyReport = new TreeMap<Long, Integer>();
			Calendar calendar = Calendar.getInstance();
			
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
			calendar.setTime(date);

			calendar.add(Calendar.DATE, -12);
			String previousString = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			
			
			Calendar newCalendar = Calendar.getInstance();
			newCalendar.setTime(date);
			for(int i = 0; i < 12; i++) {

				newCalendar.add(Calendar.DATE, -1);
				String dateString = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());
				
				Date newDate;
				try {
					newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
				
				dailyReport.put(newDate.getTime(), 0);
				
			}
			
			List<Date> datesList = airlineRepository.getFlightReservations(airline.getId(), previousString, dateFrom);
			
			for(Date d : datesList) {
				
				String tempDateString = new SimpleDateFormat("yyyy-MM-dd").format(d);
				Date tempDate = null;
				try {
					tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(tempDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Integer value = dailyReport.get(tempDate.getTime());
				dailyReport.put(tempDate.getTime(), ++value);
			}
			
			return dailyReport;
		}
		
		return null;
	}

	public Map<Long, Integer> getWeeklyReport(String dateFrom) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			
			Airline airline = repoAirline.get();
			
			Map<Long, Integer> weeklyReport = new TreeMap<Long, Integer>();
			Calendar calendar = Calendar.getInstance();
			
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
			calendar.setTime(date);
			int diff = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
			
			List<Date> listOfReservations;
			String start, end;
			
			calendar.add(Calendar.DATE, -diff);
			String temp = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			
			for(int i = 0; i < 12; i++) {
				
				calendar.add(Calendar.DATE, -7);
				
				start = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				Date monday = calendar.getTime();

				calendar.add(Calendar.DATE, 6);
			    end = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			    
			    listOfReservations = airlineRepository.getFlightReservations(airline.getId(), start, end);
			    weeklyReport.put(monday.getTime(), listOfReservations.size());
			    	 
			    calendar.add(Calendar.DATE, -6);
			}
			
			return weeklyReport;
	
		}
		
		return null;
	}

	public Map<Long, Integer> getYearlyReport(int numberOfYears) {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		
		Optional<Airline> repoAirline = airlineRepository.findById(1L);
		if(repoAirline.isPresent()) {
			Airline airline = repoAirline.get();
			
			Map<Long, Integer> yearlyReport = new TreeMap<Long, Integer>();
			Calendar calendar = Calendar.getInstance();
			String start, end;
			List<Date> listOfReservations;
			
			calendar.add(Calendar.YEAR, -numberOfYears);
			
			for(int i = 0; i < 12; i++) {
				calendar.set(Calendar.MONTH, i);
				start = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
				end = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				
				listOfReservations = airlineRepository.getFlightReservations(airline.getId(), start, end);
			    yearlyReport.put(calendar.getTime().getTime(), listOfReservations.size());
				
			    calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			}
			
			return yearlyReport;
		}
		
		return null;
	}



	
	
}
