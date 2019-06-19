package com.tim10.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
	
	List<Airline> findAll();
	
	Optional<Airline> findOneByName(String name);
	
	@Query(value = "SELECT a.name FROM Airlines a", nativeQuery = true)
	List<String> findAllNames();



	// TODO: Dodati quick reservations i proveriti da li radi kako treba
	@Query(value = "SELECT flight.departure_date "
			 + "FROM Airlines a "
			 + "RIGHT JOIN Flights flight ON a.id = flight.airline_id "
			 + "RIGHT JOIN Seats s ON flight.id = s.flight_id "
			 + "RIGHT JOIN Flight_Reservations fr ON s.id = fr.seat_id "
			 + "WHERE a.id = :airlineID "
			 + "AND (flight.departure_date BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE))"
			 , nativeQuery = true)
	List<Date> getFlightReservations(@Param("airlineID") Long id, @Param("start") String start, @Param("end") String end);
	
	
	// TODO: Proveriti da li radi kako treba
	@Query(value = "SELECT COUNT(*) "
			 + "FROM Airlines a "
			 + "RIGHT JOIN Flights flight ON a.id = flight.airline_id "
			 + "RIGHT JOIN Seats s ON flight.id = s.flight_id "
			 + "RIGHT JOIN Flight_Reservations fr ON s.id = fr.seat_id "
			 + "RIGHT JOIN Reviews r ON fr.review_id = r.id "
			 + "WHERE a.id = :airlineID AND r.company_feedback IS NOT NULL"
			 , nativeQuery = true)
	Integer getNumberOfFeedbacks(@Param("airlineID") Long id);
	
	// TODO: Dodati quick reservations i proveriti da li radi kako treba
	@Query(value = "SELECT SUM(flight.ticket_price) "
			 + "FROM Airlines a "
			 + "RIGHT JOIN Flights flight ON a.id = flight.airline_id "
			 + "RIGHT JOIN Seats s ON flight.id = s.flight_id "
			 + "RIGHT JOIN Flight_Reservations fr ON s.id = fr.seat_id "
			 + "WHERE a.id = :airlineID "
			 + "AND (flight.departure_date BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE))"
			 , nativeQuery = true)
	BigDecimal getIncomeReport(@Param("airlineID") Long id, @Param("start") String start, @Param("end") String end);

	
	@Query(value = "SELECT AVG(r.company_feedback) "
			 + "FROM Airlines a "
			 + "RIGHT JOIN Flights flight ON a.id = flight.airline_id "
			 + "RIGHT JOIN Seats s ON flight.id = s.flight_id "
			 + "RIGHT JOIN Flight_Reservations fr ON s.id = fr.seat_id "
			 + "RIGHT JOIN Reviews r ON fr.review_id = r.id "
			 + "WHERE a.id = :airlineID AND r.company_feedback IS NOT NULL"
			 , nativeQuery = true)
	Double getAverageFeedback(@Param("airlineID") Long id);
	
}
