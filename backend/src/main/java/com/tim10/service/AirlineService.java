package com.tim10.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Airline;
import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.Destination;
import com.tim10.domain.Location;
import com.tim10.domain.PriceListItem;
import com.tim10.domain.QuickFlightReservation;
import com.tim10.domain.Seat;
import com.tim10.dto.AirlineReportDTO;
import com.tim10.dto.DestinationDTO;
import com.tim10.dto.PriceListItemDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.QuickFlightReservationRepository;
import com.tim10.repository.SeatRepository;
import com.tim10.repository.UserRepository;

@Service
public class AirlineService {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private SeatRepository seatRespository;
	
	@Autowired
	private QuickFlightReservationRepository quickFlightReservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
	}
	
	public Page<Airline> findAll(Pageable page){
		return airlineRepository.findAll(page);
	}
	
	public Airline registerAirline(Airline airline) throws Exception {
		for(AirlineAdmin admin : airline.getAdministrators()) {
			if(userRepository.findOneByUsername(admin.getUsername()).isPresent())
				throw new Exception("User with username: " + admin.getUsername() + " already exists");
			else if(userRepository.findOneByEmail(admin.getEmail()).isPresent())
				throw new Exception("User with email: " + admin.getEmail() + " already exists");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			admin.setCompany(airline);
		}
		return save(airline);
	}
	
	public Airline save(Airline airline) {
		return airlineRepository.save(airline);
	}
	
	public Airline findOneByName(String name) {
		return airlineRepository.findOneByName(name).get();
	}
	
	public Optional<Airline> findOne(Long id) {
		return airlineRepository.findById(id);
	}

	// ===========================================================================
	// AIRLINE CRUD
	// ===========================================================================
	
	public Airline getCurrentAdminAirline() throws EntityNotFoundException {
		
		AirlineAdmin airlineAdmin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(airlineAdmin == null)
			throw new EntityNotFoundException(String.format("Airline administrator not found."));

		Optional<Airline> airline = airlineRepository.findById(airlineAdmin.getCompany().getId());
		if(!airline.isPresent())
			throw new EntityNotFoundException(String.format("Airline administrator %s does not have an airline associated.", airlineAdmin.getUsername()));
		
		return airline.get();
	}

	public Airline updateAirline(Airline editedAirline) throws EntityExistsException {

		Airline airline = getCurrentAdminAirline();
		
		if(editedAirline.getName() != null && !editedAirline.getName().equals(airline.getName())) {
			
			if(airlineRepository.findOneByName(editedAirline.getName()).isPresent())
				throw new EntityExistsException(String.format("Airline name %s already in use.", editedAirline.getName()));
			else
				airline.setName(editedAirline.getName());
		}
		
		if(editedAirline.getDescription() != null)
			airline.setDescription(editedAirline.getDescription());
		
		if(editedAirline.getLocation().getCountry() != null && editedAirline.getLocation().getCity() != null && editedAirline.getLocation().getStreet() != null) {
			Location updatedLocation = editedAirline.getLocation();
			airline.getLocation().setCountry(updatedLocation.getCountry());
			airline.getLocation().setCity(updatedLocation.getCity());
			airline.getLocation().setStreet(updatedLocation.getStreet());
			airline.getLocation().setformattedAddress(updatedLocation.getformattedAddress());
			airline.getLocation().setLatitude(updatedLocation.getLatitude());
			airline.getLocation().setLongitude(updatedLocation.getLongitude());
		}
		
		return airlineRepository.save(airline);
	}
	
	public Set<Destination> getBusinessLocations() {
		
		Airline airline = getCurrentAdminAirline();
		return airline.getBusinessLocations();
		
	}
	
	public Airline addBusinessLocation(Destination newDestination) {
		
		Airline airline = getCurrentAdminAirline();
		
		// TODO: Proveriti da li vec postoji lokacija sa istim nazivom i airport Code
		
		airline.getBusinessLocations().add(newDestination);
		return airlineRepository.save(airline);
	}
	
	public Airline removeBusinessLocation(DestinationDTO destinationDTO) {
		
		Airline airline = getCurrentAdminAirline();
		
		Destination destinationToRemove = null;
		for (Destination d : airline.getBusinessLocations())
			if(d.getName().equals(destinationDTO.getName()) && d.getAirportCode().equals(destinationDTO.getAirportCode()))
				destinationToRemove = d;
		
		if(destinationToRemove == null)
			throw new EntityNotFoundException(String.format("Destination with name %s does not exist.", destinationDTO.getName()));
		
		// TODO: Proveriti da li postoji let za datu lokaciju i zabraniti brisanje
		
		airline.getBusinessLocations().remove(destinationToRemove);
		return airlineRepository.save(airline);
	}
	
	public Set<PriceListItem> getPriceListItems() {
		
		Airline airline = getCurrentAdminAirline();
		return airline.getLuggagePriceList().getPriceListItems();
		
	}
	
	public Airline addPriceListItem(PriceListItem item) {
		
		Airline airline = getCurrentAdminAirline();
		airline.getLuggagePriceList().getPriceListItems().add(item);
		
		return airlineRepository.save(airline);
	}

	public Airline removePriceListItem(PriceListItemDTO itemDTO) {
		
		Airline airline = getCurrentAdminAirline();
		
		PriceListItem itemToRemove = null;
		for (PriceListItem i : airline.getLuggagePriceList().getPriceListItems())
			if(i.getName().equals(itemDTO.getName()))
				itemToRemove = i;
		
		// TODO: Proveriti da li postoje rezervacije koje referenciraju ovaj price list item, ukoliko ne postoje, obrisati
		
		if(itemToRemove == null)
			throw new EntityNotFoundException(String.format("Price list item with name %s does not exist.", itemDTO.getName()));
		
		airline.getLuggagePriceList().getPriceListItems().remove(itemToRemove);
		return airlineRepository.save(airline);
	}

	public Set<QuickFlightReservation> getQuickFlightReservations() {

		Airline airline = getCurrentAdminAirline();
		return airline.getQuickFlightReservations();
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public QuickFlightReservation createQuickFlightReservation(QuickFlightReservationDTO dto) {
		
		// TODO: Dovrsiti transakcije
		
		Optional<Seat> repoSeat = seatRespository.findById(dto.getSeatId());
		if(!repoSeat.isPresent())
			throw new EntityNotFoundException("Seat not found.");
			
		Seat seat = repoSeat.get();
		if(!seat.getIsActive() || seat.getIsReserved())
			throw new RollbackException("Seat is reserved.");
		
		if(seat.getFlight().getDepartureDate().before(new Date()))
			throw new RollbackException("Flight has already passed.");
		
		seat.setIsReserved(true);
		QuickFlightReservation quickFlightReservation = new QuickFlightReservation();

		quickFlightReservation.setDiscount(dto.getDiscount());
		quickFlightReservation.setSeat(seat);

		Optional<Airline> repoAirline = airlineRepository.findById(seat.getFlight().getAirline().getId());
		if(!repoAirline.isPresent()) 
			throw new EntityNotFoundException("Airline not found");
		
		Airline airline = repoAirline.get();
		airline.getQuickFlightReservations().add(quickFlightReservation);
		
		airlineRepository.save(airline);
		
		return quickFlightReservation;
	}
	
	public Airline deleteQuickFlightReservation(QuickFlightReservationDTO dto) {
		
		Airline airline = getCurrentAdminAirline();
		
		QuickFlightReservation itemToRemove = null;
		for (QuickFlightReservation i : airline.getQuickFlightReservations())
			if(i.getId().equals(dto.getId()))
				itemToRemove = i;
		
		if(itemToRemove == null) 
			throw new EntityNotFoundException(String.format("Quick flight reservation with id %d does not exist.", dto.getId()));
			
		if(itemToRemove.getPassengerFirstName() == null && itemToRemove.getPassengerLastName() == null && itemToRemove.getPassportNumber() == null) {
			itemToRemove.getSeat().setIsReserved(false);
			itemToRemove.setSeat(null);
			airline.getQuickFlightReservations().remove(itemToRemove);
			quickFlightReservationRepository.delete(itemToRemove);
		}
		
		return airlineRepository.save(airline);
	}
	
	// ==============================================================================
	// REPORTS
	// ==============================================================================

	public AirlineReportDTO getReports() throws ParseException {
		
		Airline airline = getCurrentAdminAirline();
	
		AirlineReportDTO reportDTO = new AirlineReportDTO(airline);
		reportDTO.setNumberOfFeedbacks(airlineRepository.getNumberOfFeedbacks(airline.getId()));
		
		String todayString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		reportDTO.setAverageFeedback(getAverageFeedback());
		reportDTO.setDailyReports(getDailyReport(todayString));
		reportDTO.setWeeklyReports(getWeeklyReport(todayString));
		reportDTO.setYearlyReports(getYearlyReport(0));
		
		return reportDTO;
	}
	
	public Double getAverageFeedback() {
		Airline airline = getCurrentAdminAirline();
		return airlineRepository.getAverageFeedback(airline.getId());
	}

	public BigDecimal getIncomeReport(String dateFrom, String dateTo) {
		Airline airline = getCurrentAdminAirline();
		return airlineRepository.getIncomeReport(airline.getId(), dateFrom, dateTo);
	}

	public Map<Long, Integer> getDailyReport(String dateFrom) throws ParseException {
		
		Airline airline = getCurrentAdminAirline();
			
		Map<Long, Integer> dailyReport = new TreeMap<Long, Integer>();
		Calendar calendar = Calendar.getInstance();
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);

		calendar.setTime(date);
		calendar.add(Calendar.DATE, -12);
		
		String previousString = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		
		
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(date);
		for(int i = 0; i < 12; i++) {
			newCalendar.add(Calendar.DATE, -1);
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime());
			Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		
			dailyReport.put(newDate.getTime(), 0);
		}
		
		List<Date> datesList = airlineRepository.getFlightReservations(airline.getId(), previousString, dateFrom);
		
		for(Date d : datesList) {
			
			String tempDateString = new SimpleDateFormat("yyyy-MM-dd").format(d);
			Date tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(tempDateString);
			
			Integer value = dailyReport.get(tempDate.getTime());
			dailyReport.put(tempDate.getTime(), ++value);
		}
		
		return dailyReport;
	}

	public Map<Long, Integer> getWeeklyReport(String dateFrom) throws ParseException {
		Airline airline = getCurrentAdminAirline();
			
		Map<Long, Integer> weeklyReport = new TreeMap<Long, Integer>();
		Calendar calendar = Calendar.getInstance();
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);

		calendar.setTime(date);
		int diff = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
		
		List<Date> listOfReservations;
		String start, end;
		
		calendar.add(Calendar.DATE, -diff);
		
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

	public Map<Long, Integer> getYearlyReport(int numberOfYears) {
		
		Airline airline = getCurrentAdminAirline();
			
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
}
