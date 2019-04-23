package com.tim10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RegisteredUser;
import com.tim10.dto.RegisteredUserDTO;
import com.tim10.dto.RegisteredUserSearchDTO;
import com.tim10.dto.SearchUsersDTO;
import com.tim10.dto.UserFriendsDTO;
import com.tim10.service.RegisteredUserService;

@RestController
@RequestMapping(value="/api")
public class RegisteredUserController {
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@RequestMapping(
			value = "/registeredUsers/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> getRegisteredUser(@PathVariable("id") Long id) {
		RegisteredUser registeredUser = registeredUserService.findOne(id);
		if (registeredUser == null)
			return new ResponseEntity<RegisteredUser>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<RegisteredUser>(registeredUser, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/currentUser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUserDTO> getCurrentUser() {
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (currentUser == null)
			return new ResponseEntity<RegisteredUserDTO>(HttpStatus.NOT_FOUND);
		RegisteredUserDTO user = new RegisteredUserDTO(currentUser);
		return new ResponseEntity<RegisteredUserDTO>(user, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/registeredUsers/",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRegisteredUser(@RequestBody RegisteredUser registeredUser) throws Exception {

		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(currentUser != null && currentUser.getId() == registeredUser.getId()) {

			if(!currentUser.getEmail().equalsIgnoreCase(registeredUser.getEmail()) && registeredUserService.findOneByEmail(registeredUser.getEmail()) != null)
				return new ResponseEntity<>("Email taken.", HttpStatus.FORBIDDEN);
			
			if(!currentUser.getUsername().equalsIgnoreCase(registeredUser.getUsername())  && registeredUserService.findOneByUsername(registeredUser.getUsername()) != null)
				return new ResponseEntity<>("Username taken.", HttpStatus.FORBIDDEN);
			
			boolean success = registeredUserService.updateUserProfile(registeredUser);
			if(success)
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	// =====================================================================
	// FRIENDSHIPS
	// =====================================================================
	
	@RequestMapping(
			value = "/searchUsers/{parameter}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchUsersDTO> searchUsers(@PathVariable("parameter") String parameter) {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		//RegisteredUser currentUser = this.registeredUserService.findOne(1L); 			// TODO: Hardcoded
		if(currentUser != null) {
			List<RegisteredUserSearchDTO> resultList = registeredUserService.findByParameter(parameter, currentUser.getId());
			SearchUsersDTO usersDTO = new SearchUsersDTO(resultList);
			return new ResponseEntity<>(usersDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(
			value = "/getAllFriends",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFriendsDTO>> getAllFriends() {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			List<UserFriendsDTO> friends = this.registeredUserService.getAllFriends(currentUser.getId());
			return new ResponseEntity<List<UserFriendsDTO>>(friends, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(
			value = "/getAllFriendshipRequests",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFriendsDTO>> getAllFriendshipRequests() {
		
		RegisteredUser currentUser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(currentUser != null) {
			List<UserFriendsDTO> friends = this.registeredUserService.getAllFriendshipRequests(currentUser.getId());
			return new ResponseEntity<List<UserFriendsDTO>>(friends, HttpStatus.OK);
		}
		 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(
			value = "/addFriend",
			method = RequestMethod.PUT,
			consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(
            value = "/removeFriend",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	
	@RequestMapping(
			value = "/acceptFriendRequest",
			method = RequestMethod.PUT,
			consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(
            value = "/declineFriendRequest",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

}