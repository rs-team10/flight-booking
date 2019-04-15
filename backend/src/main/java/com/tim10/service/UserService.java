package com.tim10.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Authority;
import com.tim10.domain.Role;
import com.tim10.domain.User;
import com.tim10.repository.AuthorityRepository;
import com.tim10.repository.UserRepository;

@Service("userService")
public class UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	public User findUserByEmail(String email) {
	

		
		return userRepository.findByEmail(email);
	}
	
	
	public void saveUser(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		// ovde cemo u nekom dto da saljemo koju je ulogu user izabrao
		// i naravno da napunimo bazu autoritetima...----------
		Authority obrisi = new Authority();
		obrisi.setRole(Role.SYSTEM_ADMIN);
		authorityRepository.save(obrisi);
		///----------------------------------------------------

		Authority authority = authorityRepository.findByRole("SYSTEM_ADMIN");
		user.setAuthorities(new HashSet<Authority>(Arrays.asList(authority)));
		
		userRepository.save(user);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	Collection<User> findAll();
	
	User create(User user) throws Exception;
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	User update(User user) throws Exception;
	*/
}
