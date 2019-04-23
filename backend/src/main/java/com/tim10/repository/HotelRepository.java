package com.tim10.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Hotel;
import com.tim10.dto.HotelDTO;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findAll();
	Page<Hotel> findAll(Pageable pageable);
	
	@SuppressWarnings("unchecked")
	Hotel save(Hotel hotel);
	
	Hotel findOneByName(String name);
	Optional<Hotel> findById(Long id);
	
	@Query(value= 
			"SELECT * "
			+ "FROM Hotels h "
			+ "INNER JOIN Locations l ON h.location_id = l.id "
			+ "WHERE lower(h.name) LIKE concat('%', lower(:parameter), '%') "
			+ "OR lower(l.street) LIKE concat('%', lower(:parameter), '%')",
			nativeQuery = true)
	List<Hotel> findByParameter(@Param("parameter") String parameter);
}
