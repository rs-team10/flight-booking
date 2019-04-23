package com.tim10.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.BranchOffice;
import com.tim10.dto.BranchOfficeLocationDTO;

public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long>{

	@Query(value = "SELECT "
			+ "bo.branch_office_id, "
			+ "city, "
			+ "country"  
			+ "  FROM "
			+ 	"flight_booking.branch_office bo "
			+ 	"LEFT JOIN "
			+ 	"flight_booking.locations l" 
			+" ON "
			+ 	"bo.location_id = l.id"
			+" WHERE "
			+ 	"company_id = :rentACarId", nativeQuery = true)
	Collection<BranchOfficeLocationDTO> getBranchOfficesFromRentACar(@Param("rentACarId") Long rentACarId);
	
}
