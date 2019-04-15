package com.tim10.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.common.DeviceProvider;
import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.RentACarAdmin;
import com.tim10.domain.Role;
import com.tim10.domain.User;
import com.tim10.domain.UserTokenState;
import com.tim10.security.TokenUtils;
import com.tim10.security.auth.JwtAuthenticationRequest;
import com.tim10.service.CustomUserDetailsService;

//Kontroler zaduzen za autentifikaciju korisnika
@CrossOrigin
@RestController
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DeviceProvider deviceProvider;
	
	
	
	@RequestMapping(
			value = "/auth/login",
			method = RequestMethod.GET
			)
	public ResponseEntity<String> loginRedirect() {
		
		return new ResponseEntity<String>("localhost:8080/login", HttpStatus.FOUND);
	}

	
	
	

	@RequestMapping(
			value = "/auth/login",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest,
						HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();	// OVDE NASTAJE PROBLEM, zato sto sve ovo izvlaci tog usera iz baze :/... trebaju mi podaci u bazi da istestiram
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		
		Role role = null;

		if (user instanceof RegisteredUser) {
			role = Role.REGISTERED_USER;
		} else if (user instanceof HotelAdmin) {
			role = Role.HOTEL_ADMIN;
		} else if (user instanceof RentACarAdmin) {
			role = Role.RENT_A_CAR_ADMIN;
		} else if (user instanceof AirlineAdmin) {
			role = Role.AIRLINE_ADMIN;
		} else {
			role = Role.SYSTEM_ADMIN;
		}
//mozda ce trebati da se dodaje ROLE_ u prefiks
		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, expiresIn, role), HttpStatus.OK);
	}
}