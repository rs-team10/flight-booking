package com.tim10.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.tim10.dto.InvitationDTO;
import com.tim10.dto.SeatReservationDTO;
import com.tim10.repository.FlightRepository;
import com.tim10.repository.GroupReservationRepository;
import com.tim10.repository.RegisteredUserRepository;
import com.tim10.repository.ReservationRepository;
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
	ReservationRepository reservationRepository;
	
    @Autowired
	EmailService mailSender;
    
    public boolean sendEmails(Long groupReservationId) {
    	
    	GroupReservation groupReservation = groupReservationRepository.getOne(groupReservationId);
    	if(groupReservation == null)
    		return false;
    	
    	for(Reservation reservation : groupReservation.getReservations()) {
    		if(reservation.getIsHost()) {
    			
    			String emailAddress = reservation.getRegisteredUser().getEmail();
    	    	String departure = reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName();
    	    	String destination = reservation.getFlightReservation().getSeat().getFlight().getDestination().getName();
    	    	String departureDate = reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString();
    	    	Seat reservedSeat = reservation.getFlightReservation().getSeat();

    			mailSender.sendFlightReservationEmail(reservation, emailAddress, departure, destination, departureDate, reservedSeat);
    		} else {
    			
    			String emailAddress = reservation.getRegisteredUser().getEmail();
    			String invitationCode = reservation.getInvitationCode();
    	    	String departure = reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName();
    	    	String destination = reservation.getFlightReservation().getSeat().getFlight().getDestination().getName();
    	    	String departureDate = reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString();
    	    	Seat reservedSeat = reservation.getFlightReservation().getSeat();
    			
    			mailSender.sendInvitationEmail(reservation, emailAddress, invitationCode, departure, destination, departureDate, reservedSeat);
    		}
    	}
	
    	return true;
    	
    }
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public Long reserveFlight(FlightReservationDTO flightReservationDTO) {
		
		Optional<Flight> repoFlight = flightRepository.findById(flightReservationDTO.getFlightId());
		if (!repoFlight.isPresent()) {
			return null;
		}
		Flight flight = repoFlight.get();

		GroupReservation groupReservation = new GroupReservation();
		groupReservation.setCreationDateTime(new Date());

		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();
		
		for(SeatReservationDTO dto : flightReservationDTO.getSeatReservationDTOList()) {
			
			FlightReservation flightReservation = new FlightReservation(dto.getFirstName(), dto.getLastName(), dto.getPassportNumber());

			Optional<Seat> repoSeat = seatRepository.findById(dto.getSeatId());
			if(repoSeat.isPresent()) {
				Seat seat = repoSeat.get();
				
				if(seat.getVersion().equals(dto.getSeatVersion())) {
					seat.setIsReserved(true);
					flightReservation.setSeat(seat);
				} else {
					return null;
				}
			}

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
					
					// Trenutni korisnik
					
					reservation.setIsHost(true);
					reservation.setInvitationCode(null);
					reservation.setStatus(RequestStatus.ACCEPTED);
					reservation.setRegisteredUser(currentUser);
					currentUser.getReservations().add(reservation);
					currentUser.setBonusPoints(currentUser.getBonusPoints() + flight.getDistance());
					
				} else {
					
					// Prijatelj
					
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
		
		
		GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
		return savedGroupReservation.getId();

	}

	public boolean acceptInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();

		Reservation r = reservationRepository.findByInvitationCode(invitationCode);
		
		if(r.getStatus() == RequestStatus.ACCEPTED || r.getStatus() == RequestStatus.DENIED)
			return false;
		

		r.setStatus(RequestStatus.ACCEPTED);
		
		// TODO: dodati mu poene i dodati u njegovu kolekciju rezervacija?
		
		reservationRepository.save(r);
		
		return true;
	}
	
	public boolean declineInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();

		Reservation r = reservationRepository.findByInvitationCode(invitationCode);
				
		if(r.getStatus() == RequestStatus.ACCEPTED || r.getStatus() == RequestStatus.DENIED)
			return false;
		
		r.setStatus(RequestStatus.DENIED);
		
		// 4. Oslobodi rezervisano sediste i razvezi ga od rezervacije 
		r.getFlightReservation().getSeat().setIsReserved(false);

		
		reservationRepository.save(r);
		
		return true;
	}
	
	
	public List<InvitationDTO> getAllInvitations() {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Reservation> userInvitations = reservationRepository.getAllInvitations(currentUser.getId());
		
		List<InvitationDTO> invitations = new ArrayList<InvitationDTO>();
		
		for (Reservation reservation : userInvitations) {
			
			InvitationDTO dto = new InvitationDTO();
			dto.setId(reservation.getInvitationCode());
			dto.setDeparture(reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName());
			dto.setDestination(reservation.getFlightReservation().getSeat().getFlight().getDestination().getName());
			dto.setDate(reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString());
			dto.setStatus(reservation.getStatus());
			invitations.add(dto);
		}
		
		
		return invitations;
	}


}
