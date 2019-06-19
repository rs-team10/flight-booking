package com.tim10.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;
import com.tim10.domain.PriceListItem;
import com.tim10.domain.QuickFlightReservation;
import com.tim10.dto.AirlineDTO;
import com.tim10.dto.AirlineProfileDTO;
import com.tim10.dto.AirlineReportDTO;
import com.tim10.dto.DestinationDTO;
import com.tim10.dto.NewAirlineDTO;
import com.tim10.dto.PriceListItemDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.service.AirlineService;

@RestController
@RequestMapping(value="/api")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	@RequestMapping(
			value = "/airlines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Airline>> getAirlines() {
		List<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<List<Airline>>(airlines, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/airlines/airlinePage",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AirlineDTO>> getAirlines(Pageable page){
		List<AirlineDTO> dtos = new ArrayList<AirlineDTO>();
		for(Airline airline : airlineService.findAll(page)) {
			dtos.add(new AirlineDTO(airline));
		}
		return new ResponseEntity<List<AirlineDTO>>(dtos, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/airlines/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> getAirline(@PathVariable("id") Long id) {
		Airline airline = airlineService.findOne(id).get();
		if(airline == null)
			return new ResponseEntity<Airline>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Airline>(airline, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/airlines",
			method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAirline(@RequestBody NewAirlineDTO airlineDTO) {
		Airline registeredAirline = null;
		Airline airline = new Airline(airlineDTO);
		try {
			registeredAirline = airlineService.registerAirline(airline);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(registeredAirline, HttpStatus.CREATED);
	}
	
	
	// ===========================================================================
	// AIRLINE CRUD
	// ===========================================================================
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value = "/airlines/getCurrentAdminAirline", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> getCurrentAdminAirline() {
		try {
			final Airline airline = airlineService.getCurrentAdminAirline();
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/updateAirline", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> updateAirline(@RequestBody @Valid Airline editedAirline) {
		try {
			final Airline airline = airlineService.updateAirline(editedAirline);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_AIRLINE_ADMIN', 'ROLE_REGISTERED_USER')")
	@GetMapping(value = "/airlines/getBusinessLocations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DestinationDTO>> getBusinessLocations() {
		try {
			List<DestinationDTO> businessLocations = new ArrayList<DestinationDTO>();
			for (Destination d : airlineService.getBusinessLocations())
				businessLocations.add(new DestinationDTO(d));
			return new ResponseEntity<List<DestinationDTO>>(businessLocations, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/addBusinessLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> addBusinessLocation(@RequestBody @Valid Destination destination) {
		try {
			final Airline airline = airlineService.addBusinessLocation(destination);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/removeBusinessLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> removeBusinessLocation(@RequestBody @Valid DestinationDTO destinationDTO) {
		try {
			final Airline airline = airlineService.removeBusinessLocation(destinationDTO);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_AIRLINE_ADMIN', 'ROLE_REGISTERED_USER')")
	@GetMapping(value = "/airlines/getPriceListItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PriceListItemDTO>> getPriceListItems() {
		try {
			List<PriceListItemDTO> priceListItems = new ArrayList<PriceListItemDTO>();
			for (PriceListItem item : airlineService.getPriceListItems())
				priceListItems.add(new PriceListItemDTO(item));
			return new ResponseEntity<List<PriceListItemDTO>>(priceListItems, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/addPriceListItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> addPriceListItem(@RequestBody @Valid PriceListItem item) {
		try {
			final Airline airline = airlineService.addPriceListItem(item);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/removePriceListItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> removePriceListItem(@RequestBody PriceListItemDTO itemDTO) {
		try {
			final Airline airline = airlineService.removePriceListItem(itemDTO);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_AIRLINE_ADMIN', 'ROLE_REGISTERED_USER')")
	@GetMapping(value = "/airlines/getQuickFlightReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickFlightReservationDTO>> getQuickFlightReservations() {
		try {
			List<QuickFlightReservationDTO> quickFlightReservations = new ArrayList<QuickFlightReservationDTO>();
			for (QuickFlightReservation item : airlineService.getQuickFlightReservations())
				quickFlightReservations.add(new QuickFlightReservationDTO(item));
			return new ResponseEntity<List<QuickFlightReservationDTO>>(quickFlightReservations, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PostMapping(value = "/airlines/createQuickFlightReservation")
	public ResponseEntity<QuickFlightReservationDTO> createQuickFlightReservation(@RequestBody QuickFlightReservationDTO dto) {
		try {
			QuickFlightReservation quickFlightReservation = airlineService.createQuickFlightReservation(dto);
			
			QuickFlightReservationDTO retVal = new QuickFlightReservationDTO();
			retVal.setId(quickFlightReservation.getId());
			retVal.setDiscount(quickFlightReservation.getDiscount());
			retVal.setSeatId(quickFlightReservation.getSeat().getId());
			
			return new ResponseEntity<QuickFlightReservationDTO>(retVal, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/airlines/deleteQuickFlightReservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteQuickFlightReservation(@RequestBody QuickFlightReservationDTO dto) {
		try {
			final Airline airline = airlineService.deleteQuickFlightReservation(dto);
			return new ResponseEntity<AirlineProfileDTO>(new AirlineProfileDTO(airline), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// ==============================================================================
	// REPORTS
	// ==============================================================================
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value="/airlines/getReport/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineReportDTO> getReport() {
		try {
			return new ResponseEntity<AirlineReportDTO>(airlineService.getReports(), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value="/airlines/getIncomeReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> getIncomeReport(@PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo) {
		try {
			return new ResponseEntity<BigDecimal>(airlineService.getIncomeReport(dateFrom, dateTo), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value="/airlines/dailyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getDailyReport(@PathParam("dateFrom") String dateFrom) {
		try {
			return new ResponseEntity<Map<Long, Integer>>(airlineService.getDailyReport(dateFrom), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value="/airlines/weeklyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> gteWeeklyReport(@PathParam("dateFrom") String dateFrom) {
		try {
			return new ResponseEntity<Map<Long, Integer>>(airlineService.getWeeklyReport(dateFrom), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@GetMapping(value="/airlines/monthlyReport", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getYearlyReport(@PathParam("numberOfYears") int numberOfYears) {
		try {
			return new ResponseEntity<Map<Long, Integer>>(airlineService.getYearlyReport(numberOfYears), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
