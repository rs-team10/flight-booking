package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim10.domain.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
	
	List<Airline> findAll();
	
	Airline findOneByName(String name);
	
	@Query(value = "SELECT a.name FROM Airlines a", nativeQuery = true)
	List<String> findAllNames();
	
}
