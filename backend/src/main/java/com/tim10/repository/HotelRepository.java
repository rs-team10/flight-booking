package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findAll();
	
	Hotel findOneByName(String name);

	
}
