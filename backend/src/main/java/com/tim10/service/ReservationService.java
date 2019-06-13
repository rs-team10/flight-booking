package com.tim10.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Flight;
import com.tim10.domain.FlightReservation;
import com.tim10.domain.GroupReservation;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.RequestStatus;
import com.tim10.domain.Reservation;
import com.tim10.domain.Seat;
import com.tim10.dto.FlightReservationDTO;
import com.tim10.dto.SeatReservationDTO;
import com.tim10.repository.FlightRepository;
import com.tim10.repository.GroupReservationRepository;
import com.tim10.repository.RegisteredUserRepository;
import com.tim10.repository.SeatRepository;

@Service
public class ReservationService {
	
	//@Autowired
	//FlightReservationRepository flightReservationRepository;
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	GroupReservationRepository groupReservationRepository;
	
    @Autowired
	EmailService mailSender;
    
    public boolean sendEmails(Long groupReservationId) {
    	
    	GroupReservation groupReservation = groupReservationRepository.getOne(groupReservationId);
    	if(groupReservation == null)
    		return false;
    	
		// TODO 5 : Posalji mail za rezervaciju host-u
		//			Posalji mailove za pozive ostalima
    	
    	boolean isMailSent = false;
    	boolean areInvitationsSent = false;
    	
    	for(Reservation reservation : groupReservation.getReservations()) {
    		if(reservation.getIsHost()) {
    			
    			String emailAddress = reservation.getRegisteredUser().getEmail();
    	    	String departure = reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName();
    	    	String destination = reservation.getFlightReservation().getSeat().getFlight().getDestination().getName();
    	    	String departureDate = reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString();
    	    	Seat reservedSeat = reservation.getFlightReservation().getSeat();

    			isMailSent = true;
    			mailSender.sendFlightReservationEmail(reservation, emailAddress, departure, destination, departureDate, reservedSeat);
    		} else {
    			
    			String emailAddress = reservation.getRegisteredUser().getEmail();
    			String invitationCode = reservation.getInvitationCode();
    	    	String departure = reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName();
    	    	String destination = reservation.getFlightReservation().getSeat().getFlight().getDestination().getName();
    	    	String departureDate = reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString();
    	    	Seat reservedSeat = reservation.getFlightReservation().getSeat();
    			
    			areInvitationsSent = true;
    			mailSender.sendInvitationEmail(reservation, emailAddress, invitationCode, departure, destination, departureDate, reservedSeat);
    		}
    	}
	
    	return isMailSent && areInvitationsSent;
    	
    }
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public Long reserveFlight(FlightReservationDTO flightReservationDTO) {
		
		Optional<Flight> repoFlight = flightRepository.findById(flightReservationDTO.getFlightId());
		
		if (!repoFlight.isPresent()) {
			return null;
		}
		
		Flight flight = repoFlight.get();
		
		
		// STEP 1 : Kreiraj novu grupnu rezervaciju, dodeli joj trenutni datum kao datum kreiranja
		//			Sve prethodno kreirane rezervacije dodaj u GroupReservation
		
		GroupReservation groupReservation = new GroupReservation();
		groupReservation.setCreationDateTime(new Date());
		
		// STEP 2 : Za svako rezervisano sediste kreirati FlightReservation
		//			Popuniti podatke o putniku za sediste
		//			Povezati sediste koje se rezervise sa FlightReservation
		//			Review objekat postaviti na null
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();
		
		
		for(SeatReservationDTO dto : flightReservationDTO.getSeatReservationDTOList()) {
			
			FlightReservation flightReservation = new FlightReservation();
			flightReservation.setPassengerFirstName(dto.getFirstName());
			flightReservation.setPassengerLastName(dto.getLastName());
			flightReservation.setPassportNumber(dto.getPassportNumber());
			
			Optional<Seat> repoSeat = seatRepository.findById(dto.getSeatId());
			
			if(repoSeat.isPresent()) {
				Seat seat = repoSeat.get();
				
				if(seat.getVersion().equals(dto.getSeatVersion())) {
					seat.setIsReserved(true);
					flightReservation.setSeat(seatRepository.save(seat));
				} else {
					return null;
				}
			}
			
			// STEP 3 : Za svaki kreirani objekat FlightReservation kreirati Reservation i povezati ih
			//			Za trenutnog korisnika postavi da je host, za ostale da nisu host
			// 			Generisi za svaku rezervaciju invitation code, osim za hosta
			//			Postavi status rezervacije hosta na accepted, a ostale na waiting
			//			Za distance i discount postavi vrednosti sa leta koji se rezervise
			// 			Za usedDiscount ???
			//			hasPassed stavi na false (nakon 3 dana stavi na true)
			
			Reservation reservation = new Reservation();
			reservation.setFlightReservation(flightReservation);
			

			
			if(dto.getUserId() == null) {
				
				// Nije korisnik aplikacije
				
				reservation.setIsHost(false);
				reservation.setInvitationCode(null);
				reservation.setStatus(RequestStatus.ACCEPTED);
				reservation.setRegisteredUser(null);
				
			} else {
				
				
				
				// Jeste korisnik aplikacije
				
				if(dto.getUserId().equals(currentUser.getId())) {
					reservation.setIsHost(true);
					reservation.setInvitationCode(null);
					reservation.setStatus(RequestStatus.ACCEPTED);
					reservation.setRegisteredUser(currentUser);
					currentUser.getReservations().add(reservation);
				} else {
					
					UUID uuid = UUID.randomUUID();
			        String randomUUIDString = uuid.toString();
			        
					reservation.setIsHost(false);
					reservation.setInvitationCode(randomUUIDString);
					reservation.setStatus(RequestStatus.WAITING);
					RegisteredUser friend = registeredUserRepository.getOne(dto.getUserId());
					reservation.setRegisteredUser(friend);
					friend.getReservations().add(reservation);
					
				}
			}
			
			reservation.setDistance(flight.getDistance());
			reservation.setDiscount(5.0); 					// TODO: Discount
			//reservation.setUsedDiscount(0);				// TODO: Used discount
			reservation.setHasPassed(false);
			
			groupReservation.add(reservation);
		}
		
		
		// TODO 4: Pokusaj cuvanje u bazu
		
		try {
		
			GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
			return savedGroupReservation.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		

	}
}
