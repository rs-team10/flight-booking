package com.tim10.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;
import com.tim10.domain.PriceListItem;
import com.tim10.dto.AirlineProfileDTO;
import com.tim10.dto.AirlineReportDTO;
import com.tim10.dto.DestinationDTO;
import com.tim10.dto.NewAirlineDTO;
import com.tim10.dto.PriceListItemDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.service.AirlineService;
import com.tim10.service.UserService;

@RestController
@RequestMapping(value="/api")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(
			value = "/airlines",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Airline>> getAirlines() {
		List<Airline> airlines = airlineService.findAll();
		return new ResponseEntity<List<Airline>>(airlines, HttpStatus.OK);
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
	
	// ===========================================================================
	
	@RequestMapping(
			value = "/airlines/getCurrentAdminAirline",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineProfileDTO> getCurrentAdminAirline() {
		return airlineService.getCurrentAdminAirline();
	}
	
	@RequestMapping(
			value="/airlines/updateAirline",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAirline(@RequestBody Airline editedAirline, 
			@RequestParam(value = "previousAirlineName", required = true) String previousAirlineName) {
		return airlineService.updateAirline(editedAirline, previousAirlineName);
	}
	
	@RequestMapping(
			value = "/airlines/getBusinessLocations",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DestinationDTO>> getBusinessLocations() {
		return airlineService.getBusinessLocations();
	}
	
	@RequestMapping(
			value="/airlines/addBusinessLocation",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addBusinessLocation(@RequestBody Destination destination) {
		return airlineService.addBusinessLocation(destination);
	}
	
	@RequestMapping(
			value="/airlines/removeBusinessLocation",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeBusinessLocation(@RequestBody DestinationDTO destinationDTO) {
		return airlineService.removeBusinessLocation(destinationDTO);
	}
	
	@RequestMapping(
			value = "/airlines/getPriceListItems",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PriceListItemDTO>> getPriceListItems() {
		return airlineService.getPriceListItems();
	}
	
	@RequestMapping(
			value="/airlines/addPriceListItem",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addPriceListItem(@RequestBody PriceListItem item) {
		return airlineService.addPriceListItem(item);
	}
	
	@RequestMapping(
			value="/airlines/removePriceListItem",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removePriceListItem(@RequestBody PriceListItemDTO itemDTO) {
		return airlineService.removePriceListItem(itemDTO);
	}
	
	@RequestMapping(
			value = "/airlines/getQuickFlightReservations",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<QuickFlightReservationDTO>> getQuickFlightReservations() {
		return airlineService.getQuickFlightReservations();
	}
	
	@RequestMapping(
			value="/airlines/deleteQuickFlightReservation",
			method=RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteQuickFlightReservation(@RequestBody QuickFlightReservationDTO dto) {
		return airlineService.deleteQuickFlightReservation(dto);
	}
	
	// ==============================================================================
	// REPORTS
	// ==============================================================================
	
	@RequestMapping(
			value="airlines/getReport/",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AirlineReportDTO> getReport() {
		return new ResponseEntity<>(airlineService.getReports(), HttpStatus.OK);
	}
	
	@RequestMapping(
			value="airlines/getIncomeReport",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> getIncomeReport(@PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo) {
		return new ResponseEntity<>(airlineService.getIncomeReport(dateFrom,  dateTo), HttpStatus.OK);
	}
	
	@RequestMapping(
			value="airlines/dailyReport",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getDailyReport(@PathParam("dateFrom") String dateFrom) {
		return new ResponseEntity<>(airlineService.getDailyReport(dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(
			value="airlines/weeklyReport",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> gteWeeklyReport(@PathParam("dateFrom") String dateFrom) {
		return new ResponseEntity<>(airlineService.getWeeklyReport(dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(
			value="airlines/monthlyReport",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getYearlyReport(@PathParam("numberOfYears") int numberOfYears) {
		return new ResponseEntity<>(airlineService.getYearlyReport(numberOfYears), HttpStatus.OK);
	}

}
