package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.RegisteredUser;
import com.tim10.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService{

	@Autowired
	private RegisteredUserRepository userRepository;
	
	
	public List<RegisteredUser> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<RegisteredUser> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public RegisteredUser save(RegisteredUser user) {
		return userRepository.save(user);
	}
	
	public Optional<RegisteredUser> findOneByUsername (String username) {
		return userRepository.findOneByUsername(username);
	}
	
	public Optional<RegisteredUser> findOneByEmail (String email) {
		return userRepository.findOneByEmail(email);
	}
	

}
