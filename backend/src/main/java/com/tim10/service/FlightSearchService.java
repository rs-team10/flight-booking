package com.tim10.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tim10.domain.Flight;
import com.tim10.domain.SegmentClass;
import com.tim10.dto.FilterDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.FlightRepository;

@Service
public class FlightSearchService {
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	AirlineRepository airlineRepository;

	public Page<Flight> performOneWaySearch(Pageable page, Long departureId, Long destinationId, String departureDateString, Integer passengerCount, String flightClassString) {
		
		// TODO: Proveriti datum !!!
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date departureDate = null;
		
		try {
			departureDate = format.parse(departureDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SegmentClass flightClass = null;
		
		
		if(flightClassString.equals("Business"))
			flightClass = SegmentClass.BUSINESS_CLASS;
		else if(flightClassString.equals("First"))
			flightClass = SegmentClass.FIRST_CLASS;
		else
			flightClass = SegmentClass.ECONOMIC_CLASS;
		
		return flightRepository.performOneWaySearch(page, departureId, destinationId, departureDate, passengerCount, flightClass.toString());
	}
	
	public Page<Flight> performOneWayFilterSearch(Pageable page, FilterDTO filter) {
		
		// TODO: Proveriti datum !!!
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date departureDate = null;
		
		System.out.println(filter.getDepartureDate());
		
		try {
			departureDate = format.parse(filter.getDepartureDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SegmentClass flightClass = null;
		
		if(filter.getFlightClassString().equals("Business"))
			flightClass = SegmentClass.BUSINESS_CLASS;
		else if(filter.getFlightClassString().equals("First"))
			flightClass = SegmentClass.FIRST_CLASS;
		else
			flightClass = SegmentClass.ECONOMIC_CLASS;
		
		List<String> airlines = filter.getAirlines();
		airlines.add("");
		
		return flightRepository.performOneWayFilterSearch(page, filter.getDepartureId(), filter.getDestinationId(), departureDate, filter.getPassengerCount(),
				flightClass.toString(), airlines, filter.getStopsCount(), filter.getMinPrice(), filter.getMaxPrice());
	}
	
	

	public List<String> getAirlineNames() {
		return airlineRepository.findAllNames();
	}
}
