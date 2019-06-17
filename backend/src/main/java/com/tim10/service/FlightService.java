package com.tim10.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;
import com.tim10.domain.Flight;
import com.tim10.domain.QuickFlightReservation;
import com.tim10.domain.Seat;
import com.tim10.domain.SegmentClass;
import com.tim10.dto.FlightDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.dto.SeatDTO;
import com.tim10.dto.SeatsUpdateDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.DestinationRepository;
import com.tim10.repository.FlightRepository;
import com.tim10.repository.SeatRepository;

@Service
public class FlightService {

	@Autowired
	AirlineRepository airlineRepository;

	@Autowired
	DestinationRepository destinationRepository;

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	SeatRepository seatRespository;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public ResponseEntity<List<FlightDTO>> getFlights() {
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();

		if (airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		List<FlightDTO> flightsList = new ArrayList<FlightDTO>();

		for (Flight flight : airline.getFlights()) {
			FlightDTO flightDTO = new FlightDTO(flight);
			flightDTO.setDepartureDate(sdf.format(flight.getDepartureDate()));
			flightDTO.setArrivalDate(sdf.format(flight.getArrivalDate()));
			flightsList.add(flightDTO);
		}

		return new ResponseEntity<List<FlightDTO>>(flightsList, HttpStatus.OK);
	}

	public ResponseEntity<FlightDTO> addFlight(FlightDTO flightDTO) {

		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju

		// Airline existingAirline =
		// airlineRepository.findOneByName(flightDTO.getAirline());
		Airline existingAirline = airlineRepository.findById(1L).get();

		if (existingAirline == null)
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
			if (destination != null)
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

		existingAirline.getFlights().add(newFlight);
		airlineRepository.save(existingAirline);
		
		
		
		Flight savedFlight = flightRepository.findOneByFlightNumber(newFlight.getFlightNumber());
		FlightDTO retval = new FlightDTO(savedFlight);

		return new ResponseEntity<FlightDTO>(retval, HttpStatus.OK);
	}

	public ResponseEntity<?> deleteFlight(FlightDTO flightDTO) {

		// TODO: HARDCODED
		// Airline existingAirline =
		// airlineRepository.findOneByName(flightDTO.getAirline());
		Airline existingAirline = airlineRepository.findById(1L).get();

		if (existingAirline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Flight existingFlight = flightRepository.findOneByFlightNumber(flightDTO.getFlightNumber());

		if (existingFlight == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// TODO: Proveriti da li za let postoji bar jedna rezervacija! Raditi logicko
		// brisanje!

		flightRepository.delete(existingFlight);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<List<SeatDTO>> getFlightSeats(FlightDTO flightDTO) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		if (airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Flight flight = flightRepository.findById(flightDTO.getId()).get();
		if(flight == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		List<SeatDTO> seatsList = new ArrayList<SeatDTO>();

		for (Seat seat : flight.getSeats()) { 
			SeatDTO seatDTO = new SeatDTO(seat);
			seatsList.add(seatDTO); 
		}
		 
		return new ResponseEntity<List<SeatDTO>>(seatsList, HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateFlightSeats(SeatsUpdateDTO seatsUpdateDTO) {
		
		// TODO: preuzeti od trenutnog korisnika tj. admin-a njegovu aviokompaniju
		Airline airline = airlineRepository.findById(1L).get();
		if (airline == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Flight flight = flightRepository.findById(seatsUpdateDTO.getFlightId()).get();
		if(flight == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int seatsCount = flight.getSeats().size();
		int counter = 0;

		for(SeatDTO seatDTO : seatsUpdateDTO.getUpdatedSeatsList()) {
			for (Seat seat : flight.getSeats()) {
				if(seat.getRed() == seatDTO.getRed() && seat.getKolona() == seatDTO.getKolona()) {
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
			Seat newSeat = new Seat(flight, seatDTO.getRed(), seatDTO.getKolona(), seatDTO.getSegmentClass());
			flight.getSeats().add(newSeat);
		}
		
		flightRepository.save(flight);
		 
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public QuickFlightReservation createQuickFlightReservation(QuickFlightReservationDTO dto) {
		
		Optional<Seat> repoSeat = seatRespository.findById(dto.getSeatId());
		if(repoSeat.isPresent()) {
			
			Seat seat = repoSeat.get();
			if(!seat.getIsActive() || seat.getIsReserved())
				throw new RollbackException("Seat is reserved.");
			
			if(!seat.getFlight().getDepartureDate().before(new Date()))
				throw new RollbackException("Flight has already passed.");
			
			seat.setIsReserved(true);
			QuickFlightReservation qfr = new QuickFlightReservation();

			qfr.setDiscount(dto.getDiscount());
			qfr.setSeat(seat);

			Optional<Airline> repoAirline = airlineRepository.findById(seat.getFlight().getAirline().getId());
			if(repoAirline.isPresent()) {
				Airline airline = repoAirline.get();
				airline.getQuickFlightReservations().add(qfr);
				
				airlineRepository.save(airline);
				
				return qfr;
			} else {
				throw new EntityNotFoundException("Airline not found");
			}
		} else {
			throw new EntityNotFoundException("Seat not found.");
		}
	}

}
