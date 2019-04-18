package com.tim10.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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

import com.tim10.domain.Authority;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.Role;
import com.tim10.domain.User;
import com.tim10.service.EmailService;
import com.tim10.service.RegisteredUserService;
import com.tim10.service.UserService;

@CrossOrigin
@RestController
public class UserController {
	//ne ovako xD
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private RegisteredUserService regUserService;

    @Autowired
	private EmailService mailSender;
    
    
	
	@RequestMapping(
			value = "api/registeredUsers",
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
		
		Optional<User> userPres= userService.findOneByEmail(email);
		
		if (!userPres.isPresent()) {
			return new ResponseEntity<>("User with that email doesn't exist!",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<User>(userPres.get(), HttpStatus.OK);
		
	}
	
	
	
	
	@RequestMapping(
			value = "/api/registeredUsers",
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
		
		//ovo obrisi 
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_REGISTERED_USER);
		
		user.getAuthorities().add(aut);
		user.setVerificationCode(UUID.randomUUID().toString());		
		
		RegisteredUser savedUser =  regUserService.save(user);
		
		RegisteredUser savedRegUser = savedUser;
		//mailSender.sendEmail(savedRegUser);

        
		
		return new ResponseEntity<RegisteredUser>(savedRegUser, HttpStatus.CREATED);
	
	}
	
	
	   @RequestMapping(
			   value="/confirm-account/{verificationCode}", 
			   method= RequestMethod.GET,
			   produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> confirmUserAccount(
	    		@PathVariable("verificationCode") String verificationCode)
	    {
		   
		   try {
			   
			   Authority aut = new Authority();
			   aut.setRole(Role.ROLE_REGISTERED_USER);
			 
			   RegisteredUser regUser = regUserService.findVerificationCode(verificationCode);
			   System.out.println(regUser.getUsername());
			   regUser.setIsConfirmed(true);
			   regUser.getAuthorities().add(aut);
			   regUserService.save(regUser);
			   return new ResponseEntity<>("User "+regUser.getUsername()+" is successfully registered!", HttpStatus.OK);
		   }catch(Exception e){
			   
			   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		   }
		   
	
	    }
	

}




