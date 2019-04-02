package com.tim10.controller;

import java.util.Collection;

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
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@RequestMapping(
			value = "api/registeredUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RegisteredUser>> getRegisteredUsers() {
		Collection<RegisteredUser> registeredUsers = registeredUserService.findAll();
		return new ResponseEntity<Collection<RegisteredUser>>(registeredUsers, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/registeredUsers/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> getRegisteredUser(@PathVariable("id") Long id) {
		RegisteredUser registeredUser = registeredUserService.findById(id);
		if (registeredUser == null) {
			return new ResponseEntity<RegisteredUser>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RegisteredUser>(registeredUser, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/registeredUsers",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> createRegisteredUser(
			@RequestBody RegisteredUser registeredUser) throws Exception {
		RegisteredUser savedRegisteredUser = registeredUserService.create(registeredUser);
		return new ResponseEntity<RegisteredUser>(savedRegisteredUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/api/registeredUsers/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> updateRegisteredUser(
			@RequestBody RegisteredUser registeredUser) throws Exception {
		RegisteredUser updatedRegisteredUser = registeredUserService.update(registeredUser);
		if (updatedRegisteredUser == null)
			return new ResponseEntity<RegisteredUser>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<RegisteredUser>(updatedRegisteredUser, HttpStatus.OK);
	}
}
