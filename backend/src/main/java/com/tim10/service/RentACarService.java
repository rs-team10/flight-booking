package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.RentACar;
import com.tim10.repository.RentACarRepository;

@Service
public class RentACarService {
	
	@Autowired
	private RentACarRepository rentACarRepository;
	
	public List<RentACar> findAll(){
		return rentACarRepository.findAll();
	}
	
	public Optional<RentACar> findById(Long id) {
		return rentACarRepository.findById(id);
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
