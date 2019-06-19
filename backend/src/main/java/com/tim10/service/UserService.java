package com.tim10.service;

import java.util.List;
import java.util.Optional;

import javax.naming.NoPermissionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.HotelAdmin;
import com.tim10.domain.User;
import com.tim10.dto.AdminDTO;
import com.tim10.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private PasswordEncoder passwordEncoder;
	
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
	
	//ZA HOTEL ADMINA
	//====================================================================
	public HotelAdmin updateHotelAdmin(HotelAdmin hotelAdmin) throws Exception {
		HotelAdmin currentHotelAdmin = (HotelAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentHotelAdmin != null && currentHotelAdmin.getId() == hotelAdmin.getId()) {
			if(!currentHotelAdmin.getEmail().equalsIgnoreCase(hotelAdmin.getEmail()) && 
					findOneByEmail(hotelAdmin.getEmail()) != null){
				throw new Exception("Email taken!");
			}
			if(!currentHotelAdmin.getUsername().equalsIgnoreCase(hotelAdmin.getUsername()) &&
					findOneByUsername(hotelAdmin.getUsername()) != null) {
				throw new Exception("Username taken!");
			}
			HotelAdmin updatedHotelAdmin = (HotelAdmin)findById(hotelAdmin.getId()).get();
			updatedHotelAdmin.setFirstName(hotelAdmin.getFirstName());
			updatedHotelAdmin.setLastName(hotelAdmin.getLastName());
			updatedHotelAdmin.setUsername(hotelAdmin.getUsername());
			updatedHotelAdmin.setEmail(hotelAdmin.getEmail());
			if(!hotelAdmin.getPassword().isEmpty())
				updatedHotelAdmin.setPassword(hotelAdmin.getPassword());
			return userRepository.save(updatedHotelAdmin);
		}
		throw new NoPermissionException("You are unauthorized to do this.");
	}
	
	
	//====================================================================
}
