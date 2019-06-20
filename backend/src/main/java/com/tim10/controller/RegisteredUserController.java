package com.tim10.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RegisteredUser;
import com.tim10.dto.RegisteredUserDTO;
import com.tim10.dto.RegisteredUserSearchDTO;
import com.tim10.dto.ReservationHistoryDTO;
import com.tim10.dto.SearchUsersDTO;
import com.tim10.dto.UserFriendsDTO;
import com.tim10.service.RegisteredUserService;

@RestController
@RequestMapping(value="/api")
public class RegisteredUserController {
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@GetMapping(value = "/registeredUsers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> getRegisteredUser(@PathVariable("id") Long id) {
		RegisteredUser registeredUser = registeredUserService.findOne(id);
		if (registeredUser == null)
			return new ResponseEntity<RegisteredUser>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<RegisteredUser>(registeredUser, HttpStatus.OK);
	}
	
	@GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUserDTO> getCurrentUser() {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (currentUser == null)
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.NOT_FOUND);
		RegisteredUserDTO user = new RegisteredUserDTO(currentUser);
		return new ResponseEntity<RegisteredUserDTO>(user, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/registeredUsers/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRegisteredUser(@RequestBody RegisteredUser registeredUser) throws Exception {

		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(currentUser != null && currentUser.getId().equals(registeredUser.getId())) {

			if(!currentUser.getEmail().equalsIgnoreCase(registeredUser.getEmail()) && registeredUserService.findOneByEmail(registeredUser.getEmail()) != null)
				return new ResponseEntity<>("Email taken.", HttpStatus.FORBIDDEN);
			
			if(!currentUser.getUsername().equalsIgnoreCase(registeredUser.getUsername())  && registeredUserService.findOneByUsername(registeredUser.getUsername()) != null)
				return new ResponseEntity<>("Username taken.", HttpStatus.FORBIDDEN);
			
			registeredUserService.updateUserProfile(registeredUser);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	// =====================================================================
	// FRIENDSHIPS
	// =====================================================================
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/searchUsers/{parameter}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchUsersDTO> searchUsers(@PathVariable("parameter") String parameter) {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser != null) {
			List<RegisteredUserSearchDTO> resultList = registeredUserService.findByParameter(parameter, currentUser.getId());
			SearchUsersDTO usersDTO = new SearchUsersDTO(resultList);
			return new ResponseEntity<>(usersDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/getAllFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFriendsDTO>> getAllFriends() {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser != null) {
			List<UserFriendsDTO> friends = this.registeredUserService.getAllFriends(currentUser.getId());
			return new ResponseEntity<List<UserFriendsDTO>>(friends, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/getAllFriendsAccepted", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFriendsDTO>> getAllFriendsAccepted() {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser != null) {
			List<UserFriendsDTO> friends = this.registeredUserService.getAllFriendsAccepted(currentUser.getId());
			return new ResponseEntity<List<UserFriendsDTO>>(friends, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/getAllFriendshipRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFriendsDTO>> getAllFriendshipRequests() {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentUser != null) {
			List<UserFriendsDTO> friends = this.registeredUserService.getAllFriendshipRequests(currentUser.getId());
			return new ResponseEntity<List<UserFriendsDTO>>(friends, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/addFriend", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addFriend(@RequestBody String friendEmail) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			RegisteredUser friend = this.registeredUserService.findOneByEmail(friendEmail);
			if(friend != null) {
				boolean success = this.registeredUserService.addFriend(currentUser, friend);
				if(success) {
					
					// TODO: Notification
					
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/removeFriend", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeFriend(@RequestBody String friendEmail) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			RegisteredUser friend = this.registeredUserService.findOneByEmail(friendEmail);
			if(friend != null) {
				boolean success = this.registeredUserService.removeFriend(currentUser, friend);
				if(success) {
					
					// TODO: Notification
					
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/acceptFriendRequest", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptFriendRequest(@RequestBody String friendEmail) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			RegisteredUser friend = this.registeredUserService.findOneByEmail(friendEmail);
			if(friend != null) {
				boolean success = this.registeredUserService.acceptFriendRequest(currentUser, friend);
				if(success) {
					
					// TODO: Notification
					
					return new ResponseEntity<>(HttpStatus.OK);
				}
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/declineFriendRequest", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> declineFriendRequest(@RequestBody String friendEmail) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			RegisteredUser friend = this.registeredUserService.findOneByEmail(friendEmail);
			if(friend != null) {
				boolean success = this.registeredUserService.declineFriendRequest(currentUser, friend);
				if(success) {
		
					// TODO: Notification
					
					return new ResponseEntity<>(HttpStatus.OK);
				}
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	    }
	    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	// =====================================================================
	// RESERVATIONS HISTORY
	// =====================================================================
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/reservationHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReservationsHistory(){
		
		Collection<ReservationHistoryDTO> reservationsHistoryDTO;
		
		try {
			reservationsHistoryDTO = registeredUserService.getReservationsHistory();
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reservationsHistoryDTO, HttpStatus.OK);
	}
	
	// =====================================================================
	// CURRENT RESERVATIONS
	// =====================================================================
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@GetMapping(value = "/currentReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCurrentReservations(){
		
		Collection<ReservationHistoryDTO> reservationsHistoryDTO;
		
		try {
			reservationsHistoryDTO = registeredUserService.getCurrentReservations();
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reservationsHistoryDTO, HttpStatus.OK);
	}

}