package com.tim10.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.Destination;
import com.tim10.domain.Flight;
import com.tim10.domain.Seat;
import com.tim10.domain.SegmentClass;
import com.tim10.dto.FlightDTO;
import com.tim10.dto.SeatDTO;
import com.tim10.dto.SeatsUpdateDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.DestinationRepository;
import com.tim10.repository.FlightRepository;

@Service
public class FlightService {

	@Autowired
	AirlineRepository airlineRepository;

	@Autowired
	DestinationRepository destinationRepository;

	@Autowired
	FlightRepository flightRepository;

	private Airline getCurrentAdminAirline() throws EntityNotFoundException {
		
		AirlineAdmin airlineAdmin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(airlineAdmin == null)
			throw new EntityNotFoundException(String.format("Airline administrator not found."));

		Optional<Airline> airline = airlineRepository.findById(airlineAdmin.getCompany().getId());
		if(!airline.isPresent())
			throw new EntityNotFoundException(String.format("Airline administrator %s does not have an airline associated.", airlineAdmin.getUsername()));
		
		return airline.get();
	}
	
	public Set<Flight> getFlights() {

		Airline airline = getCurrentAdminAirline();
		return airline.getFlights();
	}

	public Flight addFlight(FlightDTO flightDTO) throws ParseException {
		Airline airline = getCurrentAdminAirline();
		Flight newFlight = new Flight();
		
		newFlight.setAirline(airline);
		newFlight.setFlightNumber(flightDTO.getFlightNumber());

		// TODO : Proveriti da li aviokompanija posluje na toj destinaciji
		
		Optional<Destination> departure = destinationRepository.findOneByName(flightDTO.getDeparture());
		if (!departure.isPresent())
			throw new EntityNotFoundException(String.format("Destination %s not found.", flightDTO.getDeparture()));
		newFlight.setDeparture(departure.get());
		
		Optional<Destination> destination = destinationRepository.findOneByName(flightDTO.getDestination());
		if (!destination.isPresent())
			throw new EntityNotFoundException(String.format("Destination %s not found.", flightDTO.getDestination()));
		newFlight.setDestination(destination.get());

		for (String destinationName : flightDTO.getTransitDestinations()) {
			Optional<Destination> transit = destinationRepository.findOneByName(destinationName);
			
			if (!transit.isPresent())
				throw new EntityNotFoundException(String.format("Destination %s not found.", destinationName));
			
			newFlight.getTransitDestinations().add(transit.get());
		}

		newFlight.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(flightDTO.getDepartureDate()));
		newFlight.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(flightDTO.getArrivalDate()));

		newFlight.setTicketPrice(flightDTO.getTicketPrice());
		newFlight.setDistance(flightDTO.getDistance());
		newFlight.setDuration(flightDTO.getDuration());

		
		// TODO: This needs a rework

		ArrayList<Seat> seats = new ArrayList<>();

		int createdFirst = 0;
		int createdBusiness = 0;
		int createdEco = 0;

		for (int i = 0; i < flightDTO.getRowsCount(); i++) {
			for (int j = 0; j < flightDTO.getColumnsCount(); j++) {
				if (createdFirst < flightDTO.getFirstClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.FIRST_CLASS);
					seats.add(s);
					createdFirst++;
				} else if (createdBusiness < flightDTO.getBusinessClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.BUSINESS_CLASS);
					seats.add(s);
					createdBusiness++;
				} else if (createdEco < flightDTO.getEcoClassSeatsCount()) {
					Seat s = new Seat(newFlight, i + 1, j + 1, SegmentClass.ECONOMIC_CLASS);
					seats.add(s);
					createdEco++;
				}
			}
		}

		newFlight.setSeats(new HashSet<Seat>(seats));

		newFlight.setTransitCount(flightDTO.getTransitDestinations().size());
		newFlight.setAverageFeedback(0.0);

		airline.getFlights().add(newFlight);
		airlineRepository.save(airline);
		
		Optional<Flight> savedFlight = flightRepository.findOneByFlightNumber(newFlight.getFlightNumber());
		
		if(!savedFlight.isPresent())
			throw new PersistenceException("Entity was not saved properly to DB.");
		
		return savedFlight.get();
	}

	public void deleteFlight(FlightDTO flightDTO) {

		Airline airline = getCurrentAdminAirline();
		
		Optional<Flight> flight = flightRepository.findOneByFlightNumber(flightDTO.getFlightNumber());

		if (!flight.isPresent())
			throw new EntityNotFoundException(String.format("Flight %s not found.", flightDTO.getFlightNumber()));
		
		if(!flight.get().getAirline().getId().equals(airline.getId()))
			throw new EntityNotFoundException(String.format("Flight %s not found.", flightDTO.getFlightNumber()));
		
		
		// TODO: Ne pretrazivati po broju leta nego po ID!
		// TODO: Proveriti da li za let postoji bar jedna rezervacija
		// TODO: Raditi logicko brisanje!

		// flightRepository.delete(flight.get());
	}

	public Set<Seat> getFlightSeats(FlightDTO flightDTO) {

		Optional<Flight> flight = flightRepository.findById(flightDTO.getId());
		if(!flight.isPresent())
			throw new EntityNotFoundException(String.format("Flight %s not found.", flightDTO.getFlightNumber()));
		
		return flight.get().getSeats();
	}
	
	public Flight updateFlightSeats(SeatsUpdateDTO seatsUpdateDTO) {
		
		Airline airline = getCurrentAdminAirline();
		
		Optional<Flight> flight = flightRepository.findById(seatsUpdateDTO.getFlightId());
		if(!flight.isPresent())
			throw new EntityNotFoundException(String.format("Flight %s not found.", seatsUpdateDTO.getFlightId()));
		
		if(!flight.get().getAirline().getId().equals(airline.getId()))
			throw new EntityNotFoundException(String.format("Flight %s not found.", seatsUpdateDTO.getFlightId()));
		
		
		Flight existingFlight = flight.get();
		
		int seatsCount = existingFlight.getSeats().size();
		int counter = 0;
		
		// TODO: Optimize
		for(SeatDTO seatDTO : seatsUpdateDTO.getUpdatedSeatsList()) {
			for (Seat seat : existingFlight.getSeats()) {
				if(seat.getRed().equals(seatDTO.getRed()) && seat.getKolona().equals(seatDTO.getKolona())) {
					seat.setIsActive(seatDTO.getIsActive());
					seat.setSegmentClass(seatDTO.getSegmentClass());
					counter++;
				}
			}
			if(counter == seatsCount) {
				break;
			}
		}
		
		for(int i = seatsCount; i < seatsUpdateDTO.getUpdatedSeatsList().size(); i++) {
			SeatDTO seatDTO = seatsUpdateDTO.getUpdatedSeatsList().get(i);
			Seat newSeat = new Seat(existingFlight, seatDTO.getRed(), seatDTO.getKolona(), seatDTO.getSegmentClass());
			existingFlight.getSeats().add(newSeat);
		}
		
		return flightRepository.save(existingFlight);
	}
}
