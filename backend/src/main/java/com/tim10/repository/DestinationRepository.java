package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long>{
	
	Destination findOneByName(String name);
	
}