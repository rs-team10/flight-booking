package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.repository.AirlineRepository;

@Service
public class AirlineService {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	public List<Airline> findAll() {
		return airlineRepository.findAll();
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

}
