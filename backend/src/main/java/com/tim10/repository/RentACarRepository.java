package com.tim10.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.RentACar;

public interface RentACarRepository extends JpaRepository<RentACar, Long>{

	 Optional<RentACar> findOneByName(String name);
	 
	 
		@Query(value = "SELECT * FROM branch_office where main_office_id = :rentACarId", nativeQuery = true)
		Collection<BranchOffice> getBranchesFromRentACar(@Param("rentACarId") Long rentACarId);

}