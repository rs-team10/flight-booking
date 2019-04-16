package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.User;
import com.tim10.repository.UserRepository;

@Service
public class UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> findOneByUsername (String username) {
		return userRepository.findOneByUsername(username);
	}
	
	public Optional<User> findOneByEmail (String email) {
		return userRepository.findOneByEmail(email);
	}
	

}
