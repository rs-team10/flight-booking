package com.tim10.service;

import java.util.Collection;

import com.tim10.domain.Airline;

public interface AirlineService {

	Collection<Airline> findAll();
	
	Airline create(Airline airline) throws Exception;
	
	Airline findByName(String name);
	
	Airline findById(Long id);
	
	Airline update(Airline airline) throws Exception;
}
