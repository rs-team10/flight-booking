package com.tim10.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Flight;
import com.tim10.domain.FlightReservation;
import com.tim10.domain.Friendship;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.RequestStatus;
import com.tim10.domain.Reservation;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;
import com.tim10.domain.Vehicle;
import com.tim10.domain.VehicleReservation;
import com.tim10.dto.RegisteredUserSearchDTO;
import com.tim10.dto.ReservationHistoryDTO;
import com.tim10.dto.ReviewDTO;
import com.tim10.dto.UserFriendsDTO;
import com.tim10.repository.FriendshipRepository;
import com.tim10.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {
	
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
    @Autowired
    private FriendshipRepository friendshipRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public RegisteredUser firstSave(RegisteredUser registeredUser) {
		String password = passwordEncoder.encode(registeredUser.getPassword());
		registeredUser.setPassword(password);
		return registeredUserRepository.save(registeredUser);
	}
	
	public RegisteredUser save(RegisteredUser registeredUser) {
		return registeredUserRepository.save(registeredUser);
	}
	
	public RegisteredUser findOneByEmail(String email) {
		return registeredUserRepository.findOneByEmail(email);
	}
	
	public RegisteredUser findOne(Long id) {
		Optional<RegisteredUser> user = registeredUserRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}
	
	public RegisteredUser findOneByUsername(String username) {
		return registeredUserRepository.findOneByUsername(username);
	}

	public RegisteredUser findVerificationCode (String findVerificationCode) throws ResourceNotFoundException {
		
		Optional<RegisteredUser> regUser = registeredUserRepository.findOneByVerificationCode(findVerificationCode);
		
		if(regUser.isPresent())
			return regUser.get();
		else
			throw new ResourceNotFoundException("Registered user with this verification code not found!"); 
	}
	
	// =====================================================================
	// FRIENDSHIPS
	// =====================================================================
	
	public List<RegisteredUserSearchDTO> findByParameter(String parameter, Long currentUserId) {
		return registeredUserRepository.findByParameter(parameter, currentUserId);
	}
	
	public List<UserFriendsDTO> getAllFriends(Long id) {
		return friendshipRepository.getAllFriends(id);
	}
	
	public List<UserFriendsDTO> getAllFriendsAccepted(Long id) {
		return friendshipRepository.getAllFriendsAccepted(id);
	}
	
	public List<UserFriendsDTO> getAllFriendshipRequests(Long id) {
		return friendshipRepository.getAllFriendshipRequests(id);
	}

	public boolean addFriend(RegisteredUser currentUser, RegisteredUser friend) {
		if(friendRequestSent(currentUser, friend))
			return false;
		Friendship friendship = new Friendship(RequestStatus.WAITING, currentUser, friend);
		friendshipRepository.save(friendship);
		
		return true;
	}
	
	private boolean friendRequestSent(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		return (friendship == null) ? false : true;
    }
	
	public boolean removeFriend(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		friendshipRepository.delete(friendship);
		
		return true;
	}

	public boolean acceptFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		if(friendRequestSent(currentUser, friend) && friendship.getStatus() != RequestStatus.WAITING)
			return false;
		friendship.setStatus(RequestStatus.ACCEPTED);
		friendshipRepository.save(friendship);
		
		return true;
	}
    
	public boolean declineFriendRequest(RegisteredUser currentUser, RegisteredUser friend) {
		Friendship friendship = friendshipRepository.findOneByUserAndFriend(currentUser.getId(), friend.getId());
		if(!friendRequestSent(currentUser, friend) || friendship.getStatus() != RequestStatus.WAITING)
			return false;
		friendshipRepository.deleteById(friendship.getId());
		
		return true;
	}

	
	// =====================================================================
	// UPDATE USER
	// =====================================================================
	
	public boolean updateUserProfile(RegisteredUser registeredUser) {
		
		registeredUser.setAuthorities(registeredUserRepository.findById(registeredUser.getId()).get().getAuthorities());
		
		registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
		registeredUser.setIsConfirmed(true);
		registeredUserRepository.save(registeredUser);
		return true;
	}
	
	// =====================================================================
	// RESERVATIONS HISTORY
	// =====================================================================
	
	public Collection<ReservationHistoryDTO> getReservationsHistory() throws ResourceNotFoundException{
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		RegisteredUser currentUserFromBase = registeredUserRepository.getOne(currentUser.getId());
		
		Collection<Reservation> reservations = currentUserFromBase.getReservations();
		
		if(reservations==null) {
			throw new ResourceNotFoundException("User "+currentUserFromBase.getUsername()+" made non reservation!");
		}

		HashSet<ReservationHistoryDTO> reservationsDTO = new HashSet<ReservationHistoryDTO>();
		
		
		
		for(Reservation reservation : reservations) {
			if(reservation.getHasPassed()) {
			
				ReservationHistoryDTO reservationDTO = new ReservationHistoryDTO();
				
				FlightReservation flightReservation = reservation.getFlightReservation();
				RoomReservation roomReservation = reservation.getRoomReservation();
				VehicleReservation vehicleReservation = reservation.getVehicleReservation();
				
				
				reservationDTO.setFlightReservationId(flightReservation.getId());
				
				Flight flight = flightReservation.getSeat().getFlight();
				
				
				Long reservationId  = reservation.getId();
				Long flightId = flight.getId();
				String departureName = flight.getDeparture().getName();
				String destinationName = flight.getDestination().getName();
				Date departureDate = flight.getDepartureDate();
				Long airlineId = flight.getAirline().getId();
				
				reservationDTO.setReservationId(reservationId);
				
				reservationDTO.setFlightId(flightId);
				reservationDTO.setDepartureName(departureName);
				reservationDTO.setDestinationName(destinationName);
				reservationDTO.setDepartureDate(departureDate);
				reservationDTO.setAirlineId(airlineId);
				
				
				if(roomReservation != null) {
					RoomType roomType = reservation.getRoomReservation().getRoom().getRoomType();
					reservationDTO.setRoomReservationId(roomReservation.getId());
					if(roomType!= null) {
						reservationDTO.setRoomTypeId(roomType.getId());
						reservationDTO.setHotelId(roomReservation.getRoom().getHotel().getId());
						//Long hotelId videcemo kako
						
					}
				}
				
				if(vehicleReservation != null) {
					Vehicle vehicle = reservation.getVehicleReservation().getReservedVehicle();
					reservationDTO.setVehicleReservationId(vehicleReservation.getId());
					if(vehicle != null) {
						reservationDTO.setVehicleId(vehicle.getId());
						reservationDTO.setRentACarId(vehicle.getBranchOffice().getMainOffice().getId());
					}
				}
					
				
				reservationsDTO.add(reservationDTO);
			}
		}
		
		return reservationsDTO;
		
	}
	
	// =====================================================================
	// CURRENT RESERVATIONS
	// =====================================================================
	public Collection<ReservationHistoryDTO> getCurrentReservations() throws ResourceNotFoundException{
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		RegisteredUser currentUserFromBase = registeredUserRepository.getOne(currentUser.getId());
		
		Collection<Reservation> reservations = currentUserFromBase.getReservations();
		
		if(reservations==null) {
			throw new ResourceNotFoundException("User "+currentUserFromBase.getUsername()+" made non reservation!");
		}

		HashSet<ReservationHistoryDTO> reservationsDTO = new HashSet<ReservationHistoryDTO>();
		
		
		
		for(Reservation reservation : reservations) {
			if(!reservation.getHasPassed()) {
			
				ReservationHistoryDTO reservationDTO = new ReservationHistoryDTO();
				
				FlightReservation flightReservation = reservation.getFlightReservation();
				RoomReservation roomReservation = reservation.getRoomReservation();
				VehicleReservation vehicleReservation = reservation.getVehicleReservation();
				
				
				
				
				Flight flight = flightReservation.getSeat().getFlight();
				
				
				Long reservationId  = reservation.getId();
				Long flightId = flight.getId();
				String departureName = flight.getDeparture().getName();
				String destinationName = flight.getDestination().getName();
				Date departureDate = flight.getDepartureDate();
				Long airlineId = flight.getAirline().getId();
				
				reservationDTO.setReservationId(reservationId);
				
				reservationDTO.setFlightId(flightId);
				reservationDTO.setDepartureName(departureName);
				reservationDTO.setDestinationName(destinationName);
				reservationDTO.setDepartureDate(departureDate);
				reservationDTO.setAirlineId(airlineId);
				
				
				if(roomReservation != null) {
					RoomType roomType = reservation.getRoomReservation().getRoom().getRoomType();
					if(roomType!= null) {
						reservationDTO.setRoomTypeId(roomType.getId());
						reservationDTO.setHotelId(roomReservation.getRoom().getHotel().getId());
					}
				}
				
				if(vehicleReservation != null) {
					Vehicle vehicle = reservation.getVehicleReservation().getReservedVehicle();
					if(vehicle != null) {
						reservationDTO.setVehicleId(vehicle.getId());
						reservationDTO.setRentACarId(vehicle.getBranchOffice().getMainOffice().getId());
					}
				}
					
				
				reservationsDTO.add(reservationDTO);
			}
		}
		
		return reservationsDTO;
		
	}

	
}
