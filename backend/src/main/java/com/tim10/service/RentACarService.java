package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.RentACar;
import com.tim10.domain.RentACarAdmin;
import com.tim10.dto.RentACarDTO;
import com.tim10.repository.RentACarRepository;
import com.tim10.repository.UserRepository;

@Service
public class RentACarService {
	
	@Autowired
	private RentACarRepository rentACarRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	
		
	public RentACarDTO getRentACarById(Long rentACarId)throws ResourceNotFoundException {
		
		Optional<RentACar> rac =  rentACarRepository.findById(rentACarId);
		if(!rac.isPresent()) 
			throw new ResourceNotFoundException("Rent-A-Car with id: "+rentACarId+" doesn't exist!");
		
		RentACar rnc = rac.get();
		
		RentACarDTO rdto = new RentACarDTO();
		rdto.setId(rnc.getId());
		rdto.setName(rnc.getName());
		rdto.setDescription(rnc.getDescription());
		rdto.setAdditionalServicesPriceList(rnc.getAdditionalServicesPriceList());
		
		
		return rdto;
		
	}
	
	
	public List<RentACar> findAll(){
		return rentACarRepository.findAll();
	}
	
	public Page<RentACar> findAll(Pageable page){
		return rentACarRepository.findAll(page);
	}
	
	public Optional<RentACar> findById(Long id) {
		return rentACarRepository.findById(id);
	}
	
	public RentACar registerRentACar(RentACar rentacar) throws Exception {
		for(RentACarAdmin admin : rentacar.getAdministrators()) {
			if(userRepository.findOneByUsername(admin.getUsername()).isPresent()) 
				throw new Exception("User with username: " + admin.getUsername() + " already exists");
			else if(userRepository.findOneByEmail(admin.getEmail()).isPresent())
				throw new Exception("User with email: " + admin.getEmail() + " already exists");
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		}
		return save(rentacar);
	}
	
	public RentACar save(RentACar rentACar) {
		return rentACarRepository.save(rentACar);
	}
	
	
	public Optional<RentACar> findOneByName (String name) {
		return rentACarRepository.findOneByName(name);
	}
	
	public void deleteById(Long id) throws Exception {
		if(!rentACarRepository.existsById(id)) 
			throw new ResourceNotFoundException("Rent=A=Car with id: "+id+" doesn't exist!");
		rentACarRepository.deleteById(id);
	}
	
	public boolean existsById(Long id) {
		return rentACarRepository.existsById(id);
	}


}
