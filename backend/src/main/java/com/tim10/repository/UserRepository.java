package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tim10.domain.RentACar;
import com.tim10.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findOneByUsername(String username);
	
	Optional<User> findOneByEmail(String email);
	
	
	
	
	//rentACar from admin
	@Query(value = "SELECT id FROM  flight_booking.rentacar r "
			+ "LEFT OUTER JOIN  "
			+ "flight_booking.rentacar_administrators ra "
			+ "ON "
			+ "r.id = ra.rentacar_id "
			+ "where "
			+ "administrators_id = :id", nativeQuery = true)
	Long getRentACarIdFromUser(Long id);
	
	
}
