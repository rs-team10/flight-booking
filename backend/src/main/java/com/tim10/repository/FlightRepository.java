package com.tim10.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	
	Flight findOneByFlightNumber(String flightNumber);
	
	@Query(value = "SELECT * " +
				   "FROM Flights f " +
				   "WHERE (f.departure = :departureId " + 
				   	  "AND f.destination = :destinationId " + 
				   	  "AND f.departure_date >= :departureDate) " +
				   "AND " +
				   		"(SELECT COUNT(*) " + 
				   		"FROM Seats as T WHERE " +
				   		"(T.flight_id = f.id AND lower(T.segment_class) LIKE concat('%', lower(:flightClass), '%') AND T.is_active = 1 AND T.is_reserved = 0)" +
				   		") >= :passengerCount"
				   , nativeQuery = true)
	Page<Flight> performOneWaySearch(Pageable page, @Param("departureId") Long departureId, @Param("destinationId") Long destinationId, @Param("departureDate") Date departureDate,
													@Param("passengerCount") Integer passengerCount, @Param("flightClass") String flightClass);
	
	@Query(value = "SELECT * " +
			   "FROM Flights f JOIN Airlines a ON f.airline_company_id = a.company_id " +
			   "WHERE (f.departure = :departureId " + 
			   	  "AND f.destination = :destinationId " + 
			   	  "AND f.departure_date >= :departureDate " + 
			   	  "AND a.name IN :airlines " +
			   	  "AND f.transit_count <= :stopsCount " +
			   	  "AND f.ticket_price BETWEEN :minPrice AND :maxPrice) " +
			   "AND " +
			   		"(SELECT COUNT(*) " + 
			   		"FROM Seats as T WHERE " +
			   		"(T.flight_id = f.id AND lower(T.segment_class) LIKE concat('%', lower(:flightClass), '%') AND T.is_active = 1 AND T.is_reserved = 0)" +
			   		") >= :passengerCount"
			   , nativeQuery = true)
	Page<Flight> performOneWayFilterSearch(Pageable page, @Param("departureId") Long departureId, @Param("destinationId") Long destinationId, @Param("departureDate") Date departureDate,
			@Param("passengerCount") Integer passengerCount, @Param("flightClass") String flightClass, @Param("airlines") List<String> airlines, @Param("stopsCount") Integer stopsCount,
			@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

}
