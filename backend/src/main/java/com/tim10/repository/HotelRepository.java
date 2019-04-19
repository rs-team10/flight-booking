package com.tim10.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findAll();
	Page<Hotel> findAll(Pageable pageable);
	
	@SuppressWarnings("unchecked")
	Hotel save(Hotel hotel);
	
	Hotel findOneByName(String name);
	Optional<Hotel> findById(Long id);
}
