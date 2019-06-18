package com.tim10.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	List<Hotel> findAll();
	Page<Hotel> findAll(Pageable pageable);
	
	Optional<Hotel> findOneByName(String name);
	Optional<Hotel> findById(Long id);
	
	@Query(value= 
			  "SELECT * "
			+ "FROM Hotels h "
			+ "INNER JOIN Locations l ON h.location_id = l.id "
			+ "WHERE lower(h.name) LIKE concat('%', lower(:hotelName), '%') "
			+ "AND lower(l.formatted_address) LIKE concat('%', lower(:hotelLocation), '%')",
			nativeQuery = true)
	Page<Hotel> findByParameter(Pageable pageable, @Param("hotelName") String hotelName, @Param("hotelLocation") String hotelLocation);
	
	@Query(value= 
			  "SELECT rr.date_from "
			+ "FROM Hotels h "
			+ "RIGHT JOIN Rooms r ON h.id = r.hotel_id "
			+ "RIGHT JOIN Room_Reservations rr ON r.id = rr.room_id "
			+ "WHERE h.id = :hotelId "
			+ "AND (rr.date_from BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE))",
			//TODO: Otkomentarisati kada se implementiraju sve rezervacije (zbog brzih rezervacija); ne zaboravi razmak iznad na kraju
			//+ "AND rr.reservation_id IS NOT NULL",
			nativeQuery = true)
	List<Date> getRoomReservations(@Param("hotelId") Long hotelId, @Param("start") String start, @Param("end") String end);
	
	@Query(value= 
			  "SELECT COUNT(*) "
			+ "FROM Hotels h "
			+ "RIGHT JOIN Rooms r ON h.id = r.hotel_id "
			+ "RIGHT JOIN Room_Reservations rr ON r.id = rr.room_id "
			+ "RIGHT JOIN Reviews rev ON rr.review_id = rev.id "
			+ "WHERE h.id = :hotelId AND rev.company_feedback IS NOT NULL",
			nativeQuery = true)
	int getNumberOfFeedbacks(@Param("hotelId") Long hotelId);
	
	@Query(value= 
			  "SELECT SUM(rr.total_price) "
			+ "FROM Hotels h "
			+ "RIGHT JOIN Rooms r ON h.id = r.hotel_id "
			+ "RIGHT JOIN Room_Reservations rr ON r.id = rr.room_id "
			+ "WHERE h.id = :hotelId "
			+ "AND (rr.date_from BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE))",
			//TODO: Otkomentarisati kada se implementiraju sve rezervacije (zbog brzih rezervacija); ne zaboravi razmak iznad na kraju
			//+ "AND rr.reservation_id IS NOT NULL",
			nativeQuery = true)
	BigDecimal getIncomeReport(@Param("hotelId") Long hotelId, @Param("start") String start, @Param("end") String end);
	
	@Query(value=
			  "SELECT * "
			+ "FROM Hotels h "
			+ "LEFT OUTER JOIN hotels_administrators ha ON h.id = ha.hotel_id "
			+ "LEFT OUTER JOIN user u ON ha.administrators_id = u.id "
			+ "WHERE u.username = :username", nativeQuery = true)
	Optional<Hotel> getHotelToEdit(@Param("username") String username);
}
