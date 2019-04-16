package com.tim10.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RegisteredUser;
import com.tim10.service.RegisteredUserService;

@CrossOrigin
@RestController
public class RegisteredUserController {
	//ne ovako xD
	
	@Autowired
	private RegisteredUserService userService;
	
	@RequestMapping(
			value = "api/registeredUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RegisteredUser>> getUsers() {
		Collection<RegisteredUser> registeredUsers= userService.findAll();
		return new ResponseEntity<Collection<RegisteredUser>>(registeredUsers, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "api/testU",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> testUser( 
			@RequestBody RegisteredUser registeredUsers) {
		System.out.println(registeredUsers.getUsername());
		return new ResponseEntity<RegisteredUser>(registeredUsers, HttpStatus.CREATED);
	}
	
	
/*	@RequestMapping(
			value = "/api/users/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(
			@PathVariable("id") Long id) {
		
		Optional<User> userPres= userService.findById(id);
		
		if (!userPres.isPresent()) {
			return new ResponseEntity<>("User with that id doesn't exist!",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(userPres.get(), HttpStatus.OK);
		
	}
	*/
	
	@RequestMapping(
			value = "/api/registeredUsers/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(
			@PathVariable("email") String email) {
		
		Optional<RegisteredUser> userPres= userService.findOneByEmail(email);
		
		if (!userPres.isPresent()) {
			return new ResponseEntity<>("User with that email doesn't exist!",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RegisteredUser>(userPres.get(), HttpStatus.OK);
		
	}
	
	
	
	
	@RequestMapping(
			value = "/api/registeredUsers",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(
			@RequestBody RegisteredUser user) throws Exception {
			
		if(userService.findOneByEmail(user.getEmail()).isPresent()) {
			return new ResponseEntity<>("User with that email already exist!", HttpStatus.CONFLICT);
		}
		if(userService.findOneByUsername(user.getUsername()).isPresent()) {
			return new ResponseEntity<>("User with that username already exist!", HttpStatus.CONFLICT);
		}
		
		RegisteredUser savedUser = (RegisteredUser) userService.save(user);
		System.out.println(savedUser.getEmail());
		return new ResponseEntity<RegisteredUser>(savedUser, HttpStatus.CREATED);
	
	}
	
	

}
