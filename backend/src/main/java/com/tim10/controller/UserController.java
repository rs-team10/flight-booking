package com.tim10.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.ConfirmationToken;
import com.tim10.domain.RegisteredUser;
import com.tim10.domain.User;
import com.tim10.service.ConfirmationTokenService;
import com.tim10.service.UserService;

@CrossOrigin
@RestController
public class UserController {
	//ne ovako xD
	
	@Autowired
	private UserService userService;
	

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
	private JavaMailSender mailSender;
    
    
	
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
			@RequestBody User user) throws Exception {
			
		if(userService.findOneByEmail(user.getEmail()).isPresent()) {
			return new ResponseEntity<>("User with that email already exist!", HttpStatus.CONFLICT);
		}
		if(userService.findOneByUsername(user.getUsername()).isPresent()) {
			return new ResponseEntity<>("User with that username already exist!", HttpStatus.CONFLICT);
		}
		
		User savedUser =  userService.save(user);
		//System.out.println(savedUser.getEmail());
		
		ConfirmationToken confirmationToken = new ConfirmationToken();
		confirmationToken.setUser(savedUser);
		confirmationTokenService.save(confirmationToken);
		
        //ovo prebaci u sendEmail nema potrebe da stoji ovde
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("flightbooking.tim10@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8081/confirm-account/"+confirmationToken.getConfirmationToken());
        
        
        mailSender.send(mailMessage);

        
		
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	
	}
	
	
	   @RequestMapping(
			   value="/confirm-account/{confirmationToken}", 
			   method= RequestMethod.PUT,
			   produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> confirmUserAccount(
	    		@PathVariable("confirmationToken") String confirmationToken)
	    {
	        Optional<ConfirmationToken> tokenMyb = confirmationTokenService.findByConfirmationToken(confirmationToken);

	        if(tokenMyb.isPresent())
	        {
	        	ConfirmationToken token = tokenMyb.get();
	        	Optional<User>  userMyb = userService.findOneByEmail(token.getUser().getEmail());
	        	RegisteredUser user = (RegisteredUser) userMyb.get();
	        	user.setIsConfirmed(true);
	            userService.save(user);
	            return new ResponseEntity<>("User "+user.getUsername()+" is successfully registered!", HttpStatus.OK);
	        }
	        else
	        {
	        	return new ResponseEntity<>("Message is invalid or brocken", HttpStatus.NOT_FOUND);
	        }

	    }
	

}




