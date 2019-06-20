package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.RentACarAdmin;
import com.tim10.domain.User;
import com.tim10.dto.AdminDTO;
import com.tim10.service.UserService;


@RestController
public class UserController {
	//ne ovako xD
	
	@Autowired
	private UserService userService;
	


	@RequestMapping(
			value = "api/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> getUsers() {
		Collection<User> registeredUsers= userService.findAll();
		return new ResponseEntity<Collection<User>>(registeredUsers, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "api/testU",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> testUser( 
			@RequestBody User users) {
		System.out.println(users.getUsername());
		return new ResponseEntity<User>(users, HttpStatus.CREATED);
	}
	
	
	@GetMapping(value="/api/users/currentAirlineAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminDTO> getCurrentAirlineAdmin(){
		AirlineAdmin currentAirlineAdmin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentAirlineAdmin != null) {
			AdminDTO adminDTO = new AdminDTO(currentAirlineAdmin);
			return new ResponseEntity<AdminDTO>(adminDTO, HttpStatus.OK);
		}
		return new ResponseEntity<AdminDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_AIRLINE_ADMIN')")
	@PutMapping(value="/api/users/updateAirlineAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateAirlineAdmin(@RequestBody AdminDTO adminDTO){
		try {
			userService.updateAirlineAdmin(adminDTO);
			return new ResponseEntity<>("Airline admin updated successfully", HttpStatus.OK);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	
	//ZA HOTEL ADMINA
	//=================================================================================
	@RequestMapping(value="/api/users/currentHotelAdmin", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminDTO> getCurrentHotelAdmin(){
		HotelAdmin currentHotelAdmin = (HotelAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentHotelAdmin != null) {
			AdminDTO adminDTO = new AdminDTO(currentHotelAdmin);
			return new ResponseEntity<AdminDTO>(adminDTO, HttpStatus.OK);
		}
		return new ResponseEntity<AdminDTO>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/api/users/hotelAdmin", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateHotelAdmin(@RequestBody AdminDTO adminDTO){
		try {
			userService.updateHotelAdmin(adminDTO);
			return new ResponseEntity<>("Hotel admin updated successfully", HttpStatus.OK);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	
	//=================================================================================
	
	
	//ZA RentACar ADMINA
	//=================================================================================
	@RequestMapping(value="/api/users/currentRentACarAdmin", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminDTO> getCurrentRentACarAdmin(){
		RentACarAdmin currentRentACar = (RentACarAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentRentACar != null) {
			AdminDTO adminDTO = new AdminDTO(currentRentACar);
			return new ResponseEntity<AdminDTO>(adminDTO, HttpStatus.OK);
		}
		return new ResponseEntity<AdminDTO>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/api/users/rentACarAdmin", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRentACarAdmin(@RequestBody RentACarAdmin rentACarAdmin){
		RentACarAdmin updatedAdmin = null;
		try {
			updatedAdmin = userService.updateRentACarAdmin(rentACarAdmin);
			return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	
	//=================================================================================
	

}




