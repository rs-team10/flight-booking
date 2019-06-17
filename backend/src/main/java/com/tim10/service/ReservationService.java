package com.tim10.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

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
    
    @Transactional(readOnly = true)
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

    			try {
					mailSender.sendFlightReservationEmail(reservation, emailAddress, departure, destination, departureDate, reservedSeat);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
    		} else {
    			
    			String emailAddress = reservation.getRegisteredUser().getEmail();
    			String invitationCode = reservation.getInvitationCode();
    	    	String departure = reservation.getFlightReservation().getSeat().getFlight().getDeparture().getName();
    	    	String destination = reservation.getFlightReservation().getSeat().getFlight().getDestination().getName();
    	    	String departureDate = reservation.getFlightReservation().getSeat().getFlight().getDepartureDate().toString();
    	    	Seat reservedSeat = reservation.getFlightReservation().getSeat();
    			
    			try {
					mailSender.sendInvitationEmail(reservation, emailAddress, invitationCode, departure, destination, departureDate, reservedSeat);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
    		}
    	}
	
    	return true;
    	
    }
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public Long reserveFlight(FlightReservationDTO flightReservationDTO) {
		
		Optional<Flight> repoFlight = flightRepository.findById(flightReservationDTO.getFlightId());
		if (!repoFlight.isPresent())
			throw new EntityNotFoundException("Flight not found.");
		Flight flight = repoFlight.get();

		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();
		
		GroupReservation groupReservation = new GroupReservation(new Date());
		
		for(SeatReservationDTO dto : flightReservationDTO.getSeatReservationDTOList()) {
			
			FlightReservation flightReservation = new FlightReservation(dto.getFirstName(), dto.getLastName(), dto.getPassportNumber());

			Optional<Seat> repoSeat = seatRepository.findById(dto.getSeatId());
			if(repoSeat.isPresent()) {
				Seat seat = repoSeat.get();
				if(seat.getVersion().equals(dto.getSeatVersion())) {
					seat.setIsReserved(true);
					flightReservation.setSeat(seat);
				} else {
					throw new OptimisticLockException("Selected seat is taken.");
				}
			} else {
				throw new EntityNotFoundException("Seat not found.");
			}

			Reservation reservation = new Reservation();
			reservation.setFlightReservation(flightReservation);
			
			if(dto.getUserId() == null) {								// Nije korisnik aplikacije

				reservation.setIsHost(false);
				reservation.setInvitationCode(null);
				reservation.setStatus(RequestStatus.ACCEPTED);
				reservation.setRegisteredUser(null);
				
			} else {													// Jeste korisnik aplikacije

				if(dto.getUserId().equals(currentUser.getId())) {		// Trenutni korisnik

					reservation.setIsHost(true);
					reservation.setInvitationCode(null);
					reservation.setStatus(RequestStatus.ACCEPTED);
					reservation.setRegisteredUser(currentUser);
					currentUser.getReservations().add(reservation);
					currentUser.setBonusPoints(currentUser.getBonusPoints() + flight.getDistance());
					
				} else {												// Prijatelj
					
					UUID uuid = UUID.randomUUID();
			        String randomUUIDString = uuid.toString();
			        
					reservation.setIsHost(false);
					reservation.setInvitationCode(randomUUIDString);
					reservation.setStatus(RequestStatus.WAITING);
					RegisteredUser friend = registeredUserRepository.getOne(dto.getUserId());
					reservation.setRegisteredUser(friend);
				}
			}
			
			reservation.setDistance(flight.getDistance());
			reservation.setDiscount(5.0); 					// TODO: Discount
			//reservation.setUsedDiscount(0);				// TODO: Used discount
			reservation.setHasPassed(false);
			
			groupReservation.add(reservation);
		}
		
		
		GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
		
		//for (Reservation r : savedGroupReservation.getReservations()) {
		//	r.setGroupReservation(savedGroupReservation);
			//reservationRepository.save(r);
		//}
		
		return savedGroupReservation.getId();
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public boolean cancelFlightReservation(Long groupReservationId) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();
		
		GroupReservation gr = groupReservationRepository.findById(groupReservationId).get();
		
		for (Reservation r : gr.getReservations()) {
			
			Flight f = r.getFlightReservation().getSeat().getFlight();
			
			Date current = new Date();
			Date threeHoursBefore = new Date(f.getDepartureDate().getTime() - (3 * 60 * 60 * 1000));
			if(threeHoursBefore.before(current))
				throw new PersistenceException("It is no longer possible to cancel the reservation.");
			
			r.getFlightReservation().getSeat().setIsReserved(false);
			r.setStatus(RequestStatus.DENIED);
			r.setFlightReservation(null);
			
			currentUser.setBonusPoints(currentUser.getBonusPoints() - f.getDistance());
		}
		
		groupReservationRepository.save(gr);

		return true;
		
		// TODO: Otkazati i rezervaciju hotela i rentacar-a ukoliko postoji
	}


	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public boolean acceptInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();

		Reservation r = reservationRepository.findByInvitationCode(invitationCode);
		
		if(r.getStatus() == RequestStatus.ACCEPTED || r.getStatus() == RequestStatus.DENIED)
			throw new PersistenceException("Invitation already accepted/declined.");

		Date current = new Date();
		Date threeDaysBefore = new Date(current.getTime() - (3 * 24 * 60 * 60 * 1000));
		if(r.getGroupReservation().getCreationDateTime().before(threeDaysBefore))
			throw new PersistenceException("Invitation has expired (3 days passed).");
		
		Date departureDate = r.getFlightReservation().getSeat().getFlight().getDepartureDate();
		Date threeHoursBefore = new Date(departureDate.getTime() - (3 * 60 * 60 * 1000));
		if(threeHoursBefore.before(current))
			throw new PersistenceException("Invitation has expired (3 hours before flight).");
		

		currentUser.setBonusPoints(currentUser.getBonusPoints() + r.getDistance());
		currentUser.getReservations().add(r);
		
		r.setStatus(RequestStatus.ACCEPTED);
		reservationRepository.save(r);
		
		return true;
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW)
	public boolean declineInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentUser = registeredUserRepository.findById(currentUser.getId()).get();

		Reservation r = reservationRepository.findByInvitationCode(invitationCode);
		
		if(r.getStatus() == RequestStatus.ACCEPTED || r.getStatus() == RequestStatus.DENIED)
			throw new PersistenceException("Invitation already accepted/declined.");

		Date current = new Date();
		Date threeDaysBefore = new Date(current.getTime() - (3 * 24 * 60 * 60 * 1000));
		if(r.getGroupReservation().getCreationDateTime().before(threeDaysBefore))
			throw new PersistenceException("Invitation has expired (3 days passed).");
		
		Date departureDate = r.getFlightReservation().getSeat().getFlight().getDepartureDate();
		Date threeHoursBefore = new Date(departureDate.getTime() - (3 * 60 * 60 * 1000));
		if(threeHoursBefore.before(current))
			throw new PersistenceException("Invitation has expired (3 hours before flight).");
		
		r.setStatus(RequestStatus.DENIED);
		r.getFlightReservation().getSeat().setIsReserved(false);
		reservationRepository.save(r);
		
		return true;
	}
	
	@Transactional(readOnly = true)
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
			
			GroupReservation gr = reservation.getGroupReservation();
			for (Reservation r : gr.getReservations()) {
				if (r.getIsHost()) {
					dto.setBy(r.getRegisteredUser().getFirstName() + " " + r.getRegisteredUser().getLastName());
				}
			}			

			dto.setStatus(reservation.getStatus());
			invitations.add(dto);
		}
			
		return invitations;
	}

}
