package com.tim10.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.RentACar;
import com.tim10.dto.RentACarDTO;

public interface RentACarRepository extends JpaRepository<RentACar, Long>{

	 Optional<RentACar> findOneByName(String name);
	 
	 
	@Query(value = "SELECT * FROM branch_office where main_office_id = :rentACarId", nativeQuery = true)
	Collection<BranchOffice> getBranchesFromRentACar(@Param("rentACarId") Long rentACarId);
	
	@Query(value = "SELECT company_id, description, name FROM rentacar where company_id = :rentACarId", nativeQuery = true)
	RentACarDTO getRentACarById(@Param("rentACarId") Long rentACarId);

}