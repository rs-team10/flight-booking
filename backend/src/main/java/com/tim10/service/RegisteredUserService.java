package com.tim10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.RegisteredUser;
import com.tim10.repository.RegisteredUserRepository;


@Service
public class RegisteredUserService {
	
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	public RegisteredUser save(RegisteredUser registeredUser) {
		
		
		String password = passwordEncoder.encode(registeredUser.getPassword());
		System.out.println(password);
		registeredUser.setPassword(password);


		return registeredUserRepository.save(registeredUser);
	}
	
	public RegisteredUser findOneByEmail(String email) {
		return registeredUserRepository.findOneByEmail(email);
	}
	
	public Optional<RegisteredUser> findOne(Long id) {
		return registeredUserRepository.findById(id);
	}

	public RegisteredUser findVerificationCode (String findVerificationCode) throws ResourceNotFoundException {
		
		Optional<RegisteredUser> regUser = registeredUserRepository.findOneByVerificationCode(findVerificationCode);
		
		if(regUser.isPresent())
			return regUser.get();
		else
			throw new ResourceNotFoundException("Registered user with this verification code not found!"); 
	}
}
