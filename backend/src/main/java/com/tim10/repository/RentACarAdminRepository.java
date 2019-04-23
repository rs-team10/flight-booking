package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.RentACarAdmin;

public interface RentACarAdminRepository extends JpaRepository<RentACarAdmin, Long>{

	
	//TODO napravi query da od datog id admina vrati njegov rent-a-car seris 
	
	
	
	
}
