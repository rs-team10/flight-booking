package com.tim10.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.RentACar;

public interface RentACarRepository extends JpaRepository<RentACar, Long>{

	 Optional<RentACar> findOneByName(String name);
	Page<RentACar> findAll(Pageable pageable);

	 
	@Query(value = "SELECT * FROM branch_office where main_office_id = :rentACarId", nativeQuery = true)
	Collection<BranchOffice> getBranchesFromRentACar(@Param("rentACarId") Long rentACarId);
	
	
	@Query(value= 
			  "SELECT date_from FROM " + 
			  "flight_booking.vehicle_reservations vr " + 
			  "LEFT OUTER JOIN " + 
			  "flight_booking.vehicles v " + 
			  "ON vr.reserved_vehicle_id = v.id " + 
			  "LEFT OUTER JOIN " + 
			  "flight_booking.branch_office bo " + 
			  "ON v.branch_office_id = bo.id " + 
			  "WHERE main_office_id = :rentACarId " + 
			  "AND " + 
			  "date_from BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE)",
			//TODO: Otkomentarisati kada se implementiraju sve rezervacije (zbog brzih rezervacija); ne zaboravi razmak iznad na kraju
			//+ "AND rr.reservation_id IS NOT NULL",
			nativeQuery = true)
	List<Date> getVehicleReservationsFromDates(@Param("rentACarId") Long rentACarId, @Param("start") String start, @Param("end") String end);
	
	@Query(value= 
			  "SELECT SUM(total_price) FROM  " + 
			  "flight_booking.vehicle_reservations vr " + 
			  "LEFT OUTER JOIN " + 
			  "flight_booking.vehicles v " + 
			  "ON vr.reserved_vehicle_id = v.id " + 
			  "LEFT OUTER JOIN " + 
			  "flight_booking.branch_office bo " + 
			  "ON v.branch_office_id = bo.id " + 
			  "WHERE main_office_id = :rentACarId " + 
			  "AND " + 
			  "date_from BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE)",
			//TODO: Otkomentarisati kada se implementiraju sve rezervacije (zbog brzih rezervacija); ne zaboravi razmak iznad na kraju
			//+ "AND rr.reservation_id IS NOT NULL",
			nativeQuery = true)
	BigDecimal getIncomeReport(@Param("rentACarId") Long rentACarId, @Param("start") String start, @Param("end") String end);
	
	
	/*
	@Query(value = "SELECT id, description, name FROM rentacar where id = :rentACarId", nativeQuery = true)
	RentACarDTO getRentACarById(@Param("rentACarId") Long rentACarId);
	*/
}