package com.tim10.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.Airline;
import com.tim10.domain.Flight;
import com.tim10.domain.FlightReservation;
import com.tim10.domain.GroupReservation;
import com.tim10.domain.QuickFlightReservation;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.RequestStatus;
import com.tim10.domain.Reservation;
import com.tim10.domain.Seat;
import com.tim10.dto.FlightReservationDTO;
import com.tim10.dto.QuickFlightReservationDTO;
import com.tim10.dto.SeatReservationDTO;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.FlightRepository;
import com.tim10.repository.GroupReservationRepository;
import com.tim10.repository.QuickFlightReservationRepository;
import com.tim10.repository.RegisteredUserRepository;
import com.tim10.repository.ReservationRepository;
import com.tim10.repository.SeatRepository;

@Service
public class ReservationService {
	
	@Autowired
	AirlineRepository airlineRepository;
	
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
	QuickFlightReservationRepository quickFlightReservationRepository;
	
    @Autowired
	EmailService mailSender;
    
    @Transactional(readOnly = true)
    public void sendEmails(Long groupReservationId) throws MessagingException {
    	
    	Optional<GroupReservation> groupReservation = groupReservationRepository.findById(groupReservationId);
    	if(!groupReservation.isPresent())
    		throw new EntityNotFoundException(String.format("Group reservation with id %d not found.", groupReservationId));
    	
    	for(Reservation reservation : groupReservation.get().getReservations()) {
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
    }
	
	@Transactional(readOnly = false,
			propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.SERIALIZABLE,
			rollbackFor= {EntityNotFoundException.class, OptimisticLockException.class, PersistenceException.class, RuntimeException.class, StaleObjectStateException.class})
	public Long reserveFlight(FlightReservationDTO flightReservationDTO) throws Exception {
		
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
			if(!repoSeat.isPresent())
				throw new EntityNotFoundException(String.format("Seat with id %d not found.", dto.getSeatId()));
			Seat seat = repoSeat.get();
			if(seat.getIsReserved() || !seat.getIsActive())
				throw new EntityNotFoundException("Seat is unavailable.");
			
			seat.setIsReserved(true);
			flightReservation.setSeat(seat);

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
		
		for(Reservation r : groupReservation.getReservations())
			r.setGroupReservation(groupReservation);
		
		try {
			GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
			return savedGroupReservation.getId();
		} catch (Exception e) {
			throw new PersistenceException("Error while saving to DB.");
		}
	}
	
	@Transactional(readOnly = false,
			propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.SERIALIZABLE,
			rollbackFor= {EntityNotFoundException.class, OptimisticLockException.class, PersistenceException.class, RuntimeException.class, StaleObjectStateException.class})
	public Long reserveQuickFlight(QuickFlightReservationDTO flightReservationDTO) throws Exception {

		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();
		
		GroupReservation groupReservation = new GroupReservation(new Date());
		
		Optional<QuickFlightReservation> repoQuickFlightReservation = quickFlightReservationRepository.findById(flightReservationDTO.getId());
		if(!repoQuickFlightReservation.isPresent())
			throw new EntityNotFoundException("Quick flight reservation not found.");
		QuickFlightReservation qfr = repoQuickFlightReservation.get();
		
		qfr.setPassengerFirstName(currentUser.getFirstName());
		qfr.setPassengerLastName(currentUser.getLastName());

		Reservation reservation = new Reservation();
		reservation.setFlightReservation(qfr);
		
		reservation.setIsHost(true);
		reservation.setInvitationCode(null);
		reservation.setStatus(RequestStatus.ACCEPTED);
		reservation.setRegisteredUser(currentUser);
		currentUser.getReservations().add(reservation);
		currentUser.setBonusPoints(currentUser.getBonusPoints() + qfr.getSeat().getFlight().getDistance());
		
		reservation.setDistance(qfr.getSeat().getFlight().getDistance());
		reservation.setDiscount(5.0); 					// TODO: Discount
		//reservation.setUsedDiscount(0);				// TODO: Used discount
		reservation.setHasPassed(false);
		
		groupReservation.add(reservation);
		reservation.setGroupReservation(groupReservation);
		
		Optional<Airline> repoAirline = airlineRepository.findById(qfr.getSeat().getFlight().getAirline().getId());
		if(!repoAirline.isPresent())
			throw new EntityNotFoundException("Quick flight reservation not found.");
		
		repoAirline.get().getQuickFlightReservations().remove(qfr);
		
		try {
			GroupReservation savedGroupReservation = groupReservationRepository.save(groupReservation);
			return savedGroupReservation.getId();
		} catch (Exception e) {
			throw new PersistenceException("Error while saving to DB.");
		}
		
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW, isolation=Isolation.SERIALIZABLE)
	public void cancelFlightReservation(Long reservationId) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();
		
		Optional<Reservation> repoReservation = reservationRepository.findById(reservationId);
		if(!repoReservation.isPresent())
			throw new EntityNotFoundException(String.format("Reservation with id %d not found.", reservationId));
		Reservation r = repoReservation.get();
		
		Flight f = r.getFlightReservation().getSeat().getFlight();
		
		Date current = new Date();
		Date threeHoursBefore = new Date(f.getDepartureDate().getTime() - (3 * 60 * 60 * 1000));
		if(threeHoursBefore.before(current))
			throw new PersistenceException("It is no longer possible to cancel the reservation.");
		
		Optional<Seat> repoSeat = seatRepository.findById(r.getFlightReservation().getSeat().getId());
		if(!repoSeat.isPresent())
			throw new EntityNotFoundException(String.format("Seat with id %d not found.", r.getFlightReservation().getSeat().getId()));
		Seat seat = repoSeat.get();
		
		seat.setIsReserved(false);
		r.setStatus(RequestStatus.DENIED);
		r.setFlightReservation(null);
		
		currentUser.setBonusPoints(currentUser.getBonusPoints() - f.getDistance());
		
		reservationRepository.save(r);
		
		// TODO: Otkazati i rezervaciju hotela i rentacar-a ukoliko postoji
	}


	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW, isolation=Isolation.REPEATABLE_READ)
	public void acceptInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();

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
	}
	
	@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW, isolation=Isolation.REPEATABLE_READ)
	public void declineInvitation(String invitationCode) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();

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
	}
	
	@Transactional(readOnly = true)
	public List<Reservation> getAllInvitations() {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<RegisteredUser> repoUser = registeredUserRepository.findById(currentUser.getId());
		if(!repoUser.isPresent())
			throw new EntityNotFoundException("User not found.");
		currentUser = repoUser.get();
		
		return reservationRepository.getAllInvitations(currentUser.getId());
	}
}
