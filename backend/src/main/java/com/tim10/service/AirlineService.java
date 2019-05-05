package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.Destination;
import com.tim10.repository.AirlineRepository;
import com.tim10.repository.DestinationRepository;

@Service
public class AirlineService {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private DestinationRepository destinationRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
	}
	
	public Airline registerAirline(Airline airline) {
		for(AirlineAdmin admin : airline.getAdministrators())
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		return airlineRepository.save(airline);
	}
	
	public Airline save(Airline airline) {
		return airlineRepository.save(airline);
	}
	
	public Airline findOneByName(String name) {
		return airlineRepository.findOneByName(name);
	}
	
	public Optional<Airline> findOne(Long id) {
		return airlineRepository.findById(id);
	}

	public boolean addBusinessLocation(Airline existingAirline, Destination destination) {
		
		try {
			existingAirline.getBusinessLocations().add(destination);
			airlineRepository.save(existingAirline);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean removeBusinessLocation(Airline existingAirline, Destination destination) {
		try {
			Destination destinationToRemove = destinationRepository.findById(destination.getId()).get();
			existingAirline.getBusinessLocations().remove(destinationToRemove);
			airlineRepository.save(existingAirline);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
