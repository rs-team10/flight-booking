package com.tim10.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Authority;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Role;
import com.tim10.domain.User;
import com.tim10.domain.UserTokenState;
import com.tim10.dto.UserDTOtest;
import com.tim10.security.TokenUtils;
import com.tim10.security.auth.JwtAuthenticationRequest;
import com.tim10.service.EmailService;
import com.tim10.service.RegisteredUserService;
import com.tim10.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	
	@Autowired
	private RegisteredUserService regUserService;

    @Autowired
	private EmailService mailSender;
    
/*
	@Autowired
	private DeviceProvider deviceProvider;
*/	
	
	@RequestMapping(
			value = "/auth/signup",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(
			@RequestBody RegisteredUser user) throws Exception {
		
		if(userService.findOneByUsername(user.getUsername()).isPresent()) {
			return new ResponseEntity<>("User with that username already exist!", HttpStatus.CONFLICT);
		}
			
		if(userService.findOneByEmail(user.getEmail()).isPresent()) {
			return new ResponseEntity<>("User with that email already exist!", HttpStatus.CONFLICT);
		}
		

		user.setVerificationCode(UUID.randomUUID().toString());		
		user.setIsConfirmed(false);
		   
		RegisteredUser savedUser =  regUserService.firstSave(user);
		
		RegisteredUser savedRegUser = savedUser;
		mailSender.sendEmail(savedRegUser);

        
		
		return new ResponseEntity<RegisteredUser>(savedRegUser, HttpStatus.CREATED);
	
	}
	
	
	   @RequestMapping(
			   value="/auth/{verificationCode}", 
			   method= RequestMethod.GET,
			   produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> confirmUserAccount(
	    		@PathVariable("verificationCode") String verificationCode)
	    {
		   
		   try {
			   
			   Authority aut = new Authority();
			   aut.setRole(Role.ROLE_REGISTERED_USER);
			 
			   RegisteredUser regUser = regUserService.findVerificationCode(verificationCode);
	
			   regUser.setIsConfirmed(true);
			   regUser.getAuthorities().add(aut);
	   
			   userService.save(regUser);
			   
			   return new ResponseEntity<>("User "+regUser.getUsername()+" is successfully registered!", HttpStatus.OK);
		   }catch(Exception e){
			   
			   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		   }
		   
	
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
	
			return new ResponseEntity<>("NO TOKEN! NO PROBLEM",HttpStatus.OK);
		}
		// Ubaci username + password u kontext STA SE OVDE DESAVA!?
		
		
		//ako bude pustao da se loguje bez confirma ovde ubaci proveru!
		

		User user = (User) authentication.getPrincipal();
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		if(user.getAuthorities().size() == 0) {
			RegisteredUser userR = (RegisteredUser)user;
			
			if(!userR.getIsConfirmed()) 
				return new ResponseEntity<>("User didn't confirm email", HttpStatus.OK);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		
		
		Set<Authority> aut = user.getAuthorities();
		Iterator<Authority> i = aut.iterator();
		Role role = i.next().getRole();
		
		//mozda ce trebati da se dodaje ROLE_ u prefiks
		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, expiresIn, role), HttpStatus.OK);
	}
	
	
	
	@RequestMapping(
			value = "/auth/getLoggedTest",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLogged() {


		RegisteredUser testuser = (RegisteredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDTOtest t = new UserDTOtest(testuser.getUsername(), testuser.getEmail());
		
		
		return new ResponseEntity<UserDTOtest>(t, HttpStatus.OK);
		
	}
	
	
	
	
	
}