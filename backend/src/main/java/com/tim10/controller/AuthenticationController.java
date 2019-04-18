package com.tim10.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

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
import com.tim10.domain.Authority;
import com.tim10.domain.Role;
import com.tim10.domain.User;
import com.tim10.domain.UserTokenState;
import com.tim10.security.TokenUtils;
import com.tim10.security.auth.JwtAuthenticationRequest;
import com.tim10.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@CrossOrigin
@RestController
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceProvider deviceProvider;
	
	
	
	

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
	
			return new ResponseEntity<>("NO TOKEN! NO PROBLEM",HttpStatus.OK);
		}
		// Ubaci username + password u kontext STA SE OVDE DESAVA!?
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		
		
		Set<Authority> aut = user.getAuthorities();
		Iterator<Authority> i = aut.iterator();
		Role role = i.next().getRole();
		
		//mozda ce trebati da se dodaje ROLE_ u prefiks
		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, expiresIn, role), HttpStatus.OK);
	}
}