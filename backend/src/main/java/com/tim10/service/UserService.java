package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.User;
import com.tim10.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	
	public User findUserByEmail(String email) {
	
		return userRepository.findOneByEmail(email).get();
	}
	
	public void save(User user) {
		userRepository.save(user);
		
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findOneByUsername (String username) {
		return userRepository.findOneByUsername(username);
	}
	
	public Optional<User> findOneByEmail (String email) {
		return userRepository.findOneByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findOneByUsername(username);
		
		if(!user.isPresent())
			throw new UsernameNotFoundException("User with "+ username+" doesn't exists!");
		
		
		
		return user.get();
	}
}
