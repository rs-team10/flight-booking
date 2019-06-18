package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.HotelAdmin;

public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long>{
	
	Optional<HotelAdmin> findOneByUsername(String username);
}
