package com.tim10.repository;

import java.util.Collection;

import com.tim10.domain.Airline;

public interface AirlineRepository {
	
	Collection<Airline> findAll();
	
	Airline create(Airline airline);
	
	Airline findByName(String name);
	
	Airline findById(Long id);
	
	Airline update(Airline airline);
}
