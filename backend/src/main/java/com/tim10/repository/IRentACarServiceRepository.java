package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.RentACar;

public interface IRentACarServiceRepository extends JpaRepository<RentACar, Long>{

	RentACar findOneByName(String name);
	
	
	//RentACar update(RentACar rentACarService);
	
	
}
