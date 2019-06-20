package com.tim10.service;

import java.util.List;
import java.util.Optional;

import javax.naming.NoPermissionException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.HotelAdmin;
import com.tim10.domain.RentACarAdmin;
import com.tim10.domain.User;
import com.tim10.dto.AdminDTO;
import com.tim10.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@Transactional(readOnly = false)
	public boolean updateAirlineAdmin(AdminDTO adminDTO) throws Exception {
		AirlineAdmin currentAirlineAdmin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentAirlineAdmin != null && currentAirlineAdmin.getId().equals(adminDTO.getId())) {
			if(!currentAirlineAdmin.getEmail().equalsIgnoreCase(adminDTO.getEmail()) && 
					findOneByEmail(adminDTO.getEmail()).isPresent()){
				throw new Exception("Email taken.");
			}
			if(!currentAirlineAdmin.getUsername().equalsIgnoreCase(adminDTO.getUsername()) &&
					findOneByUsername(adminDTO.getUsername()).isPresent()) {
				throw new Exception("Username taken.");
			}
			Optional<User> userOptional = findById(adminDTO.getId());
			if(!userOptional.isPresent())
				throw new EntityNotFoundException("Airline admin not found");
			
			AirlineAdmin updatedAirlineAdmin = (AirlineAdmin)userOptional.get();
			updatedAirlineAdmin.setFirstName(adminDTO.getFirstName());
			updatedAirlineAdmin.setLastName(adminDTO.getLastName());
			updatedAirlineAdmin.setUsername(adminDTO.getUsername());
			updatedAirlineAdmin.setEmail(adminDTO.getEmail());
			if(!adminDTO.getPassword().isEmpty())
				updatedAirlineAdmin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
			userRepository.save(updatedAirlineAdmin);
			return true;
		}
		throw new NoPermissionException("You are unauthorized to do this.");
	}
	
	//ZA HOTEL ADMINA
	//====================================================================
	public boolean updateHotelAdmin(AdminDTO adminDTO) throws Exception {
		HotelAdmin currentHotelAdmin = (HotelAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentHotelAdmin != null && currentHotelAdmin.getId().equals(adminDTO.getId())) {
			if(!currentHotelAdmin.getEmail().equalsIgnoreCase(adminDTO.getEmail()) && 
					findOneByEmail(adminDTO.getEmail()).isPresent()){
				throw new Exception("Email taken!");
			}
			if(!currentHotelAdmin.getUsername().equalsIgnoreCase(adminDTO.getUsername()) &&
					findOneByUsername(adminDTO.getUsername()).isPresent()) {
				throw new Exception("Username taken!");
			}
			Optional<User> userOptional = findById(adminDTO.getId());
			if(!userOptional.isPresent())
				throw new EntityNotFoundException("Hotel admin not found");
			
			HotelAdmin updatedHotelAdmin = (HotelAdmin)userOptional.get();
			updatedHotelAdmin.setFirstName(adminDTO.getFirstName());
			updatedHotelAdmin.setLastName(adminDTO.getLastName());
			updatedHotelAdmin.setUsername(adminDTO.getUsername());
			updatedHotelAdmin.setEmail(adminDTO.getEmail());
			if(!adminDTO.getPassword().isEmpty())
				updatedHotelAdmin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
			userRepository.save(updatedHotelAdmin);
			return true;
		}
		throw new NoPermissionException("You are unauthorized to do this.");
	}
	//====================================================================
	
	//ZA RentACar ADMINA
	//====================================================================
	public RentACarAdmin updateRentACarAdmin(RentACarAdmin rentACarAdmin) throws Exception {
		RentACarAdmin currentRentACarAdmin = (RentACarAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(currentRentACarAdmin != null && currentRentACarAdmin.getId() == rentACarAdmin.getId()) {
			if(!currentRentACarAdmin.getEmail().equalsIgnoreCase(rentACarAdmin.getEmail()) && 
					findOneByEmail(rentACarAdmin.getEmail()) != null){
				throw new Exception("Email taken!");
			}
			if(!currentRentACarAdmin.getUsername().equalsIgnoreCase(rentACarAdmin.getUsername()) &&
					findOneByUsername(rentACarAdmin.getUsername()) != null) {
				throw new Exception("Username taken!");
			}
			RentACarAdmin updatedRentACarAdmin = (RentACarAdmin)findById(rentACarAdmin.getId()).get();
			updatedRentACarAdmin.setFirstName(rentACarAdmin.getFirstName());
			updatedRentACarAdmin.setLastName(rentACarAdmin.getLastName());
			updatedRentACarAdmin.setUsername(rentACarAdmin.getUsername());
			updatedRentACarAdmin.setEmail(rentACarAdmin.getEmail());
			if(!rentACarAdmin.getPassword().isEmpty())
				updatedRentACarAdmin.setPassword(passwordEncoder.encode(rentACarAdmin.getPassword()));
			return userRepository.save(updatedRentACarAdmin);
		}
		throw new NoPermissionException("You are unauthorized to do this.");
	}
	//====================================================================
}
