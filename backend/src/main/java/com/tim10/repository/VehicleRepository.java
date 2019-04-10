package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	
	
}
