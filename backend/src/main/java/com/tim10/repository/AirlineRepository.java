package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long>{
	
	Airline findOneByName(String name);
	
	
//	List<Airline> findAll();
//	
//	Airline create(Airline airline);
//	
//	//Airline findByName(String name);
//	
//	Optional<Airline> findById(Long id);
//	
//	Airline update(Airline airline);
}
