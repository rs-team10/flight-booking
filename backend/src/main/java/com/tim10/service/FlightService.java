package com.tim10.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;
import com.tim10.domain.Flight;
import com.tim10.domain.Seat;
import com.tim10.domain.SegmentClass;
import com.tim10.dto.FlightDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.DestinationRepository;

@Service
public class FlightService {
	
	@Autowired
	AirlineRepository airlineRepository;
	
	@Autowired
	DestinationRepository destinationRepository;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public ResponseEntity<?> addFlight(FlightDTO flightDTO) {
		
		// TODO: HARDCODED
		//Airline existingAirline = airlineRepository.findOneByName(flightDTO.getAirline());
		Airline existingAirline = airlineRepository.findById(1L).get();
		
		
		if(existingAirline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Flight newFlight = new Flight();
		
		newFlight.setAirline(existingAirline);
		newFlight.setFlightNumber(flightDTO.getFlightNumber());
		
		// TODO : Proveriti da li aviokompanija posluje na toj destinaciji
		newFlight.setDeparture(destinationRepository.findOneByName(flightDTO.getDeparture()));
		newFlight.setDestination(destinationRepository.findOneByName(flightDTO.getDestination()));
		
		// TRANSIT DESTINATIONS
		
		for (String destinationName : flightDTO.getTransitDestinations()) {
			Destination destination = destinationRepository.findOneByName(destinationName);
			if(destination != null)
				newFlight.getTransitDestinations().add(destination);
		}
		
		// DATES
		
		try {
			newFlight.setDepartureDate(sdf.parse(flightDTO.getDepartureDate()));
			newFlight.setArrivalDate(sdf.parse(flightDTO.getArrivalDate()));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		newFlight.setTicketPrice(flightDTO.getTicketPrice());
		newFlight.setDistance(flightDTO.getDistance());
		newFlight.setDuration(flightDTO.getDuration());

		
		// SEATS
		
		ArrayList<Seat> seats = new ArrayList<>();

		int createdFirst = 0;
		int createdBusiness = 0;
		int createdEco = 0;
		
		for(int i = 0; i < flightDTO.getRowsCount(); i++) {
			for(int j = 0; j < flightDTO.getColumnsCount(); j++) {
				if(createdFirst < flightDTO.getFirstClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.FIRST_CLASS);
					seats.add(s);
					createdFirst++;
				} else if(createdBusiness < flightDTO.getBusinessClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.BUSINESS_CLASS);
					seats.add(s);
					createdBusiness++;
				} else if(createdEco < flightDTO.getEcoClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.ECONOMIC_CLASS);
					seats.add(s);
					createdEco++;
				}
			}
		}
		
		newFlight.setSeats(new HashSet<Seat>(seats));
		
		newFlight.setTransitCount(flightDTO.getTransitDestinations().length);
		newFlight.setAverageFeedback(0.0);
		
		existingAirline.getFlights().add(newFlight);
		airlineRepository.save(existingAirline);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
