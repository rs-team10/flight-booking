package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long>{
	
	Optional<Destination> findOneByName(String name);
	
}