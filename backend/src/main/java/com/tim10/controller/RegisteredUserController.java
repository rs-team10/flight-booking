package com.tim10.controller;

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
@RequestMapping(value="/api")
public class RegisteredUserController {
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@RequestMapping(
			value = "/registeredUsers/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> getRegisteredUser(@PathVariable("id") Long id) {
		RegisteredUser registeredUser = registeredUserService.findOne(id).get();
		if (registeredUser == null)
			return new ResponseEntity<RegisteredUser>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<RegisteredUser>(registeredUser, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/registeredUsers",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createRegisteredUser(
			@RequestBody RegisteredUser registeredUser) throws Exception {
		
		if(registeredUserService.findOneByEmail(registeredUser.getEmail()) == null) {
			RegisteredUser returnUser = registeredUserService.save(registeredUser);
			return new ResponseEntity<>(returnUser, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Registered user with that email already exists!", HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(
			value = "/registeredUsers/",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRegisteredUser(
			@RequestBody RegisteredUser registeredUser) throws Exception {
		
		RegisteredUser existingUser = registeredUserService.findOneByEmail(registeredUser.getEmail());
		if(existingUser != null && existingUser.getId() != registeredUser.getId())
			return new ResponseEntity<>("User with that email already exists!", HttpStatus.FORBIDDEN);
		
		if(registeredUserService.findOneByEmail(registeredUser.getEmail()) != null)
			return new ResponseEntity<>(registeredUserService.save(registeredUser), HttpStatus.OK);
		
		return new ResponseEntity<>("Wanted user does not exist in the database :(", HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
}
