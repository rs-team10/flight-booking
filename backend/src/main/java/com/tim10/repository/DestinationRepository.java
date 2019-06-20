package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long>{
	
	Optional<Destination> findOneByName(String name);

	@Query(value="SELECT * FROM Destinations d WHERE lower(d.name) LIKE concat('%', lower(:airportName), '%') AND lower(d.airport_code) LIKE concat('%', lower(:airportCode), '%')", nativeQuery=true)
	Optional<Destination> findOneByNameAndCode(@Param("airportName") String airportName, @Param("airportCode") String airportCode);
	
	@Query(value="SELECT COUNT(*) FROM Flights f WHERE f.departure = :id OR f.destination = :id", nativeQuery=true)
	Integer getNumberOfFlights(@Param("id") Long destinationId);
	
}