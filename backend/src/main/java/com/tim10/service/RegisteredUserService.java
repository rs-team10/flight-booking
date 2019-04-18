package com.tim10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.RegisteredUser;
import com.tim10.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	
	@Autowired
	private RegisteredUserRepository userRepository;
	
	
	public RegisteredUser findVerificationCode (String findVerificationCode) throws ResourceNotFoundException {
		
		Optional<RegisteredUser> regUser = userRepository.findOneByVerificationCode(findVerificationCode);
		
		if(regUser.isPresent())
			return regUser.get();
		else
			throw new ResourceNotFoundException("Registered user with this verification code not found!"); 
	}
	
	public RegisteredUser save(RegisteredUser regUser) {
		return userRepository.save(regUser);
	}
	
	
	
	
}
