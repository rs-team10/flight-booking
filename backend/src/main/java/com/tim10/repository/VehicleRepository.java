package com.tim10.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Vehicle;
import com.tim10.dto.VehicleDTO;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
	/*
	FROM table1
	LEFT JOIN table2
	ON table1.column_name = table2.column_name;
	*/
	@Query(value = "SELECT * FROM vehicles v LEFT JOIN branch_office b ON v.branch_office_id = b.id WHERE main_office_id=:rentACarId", nativeQuery = true)
	Collection<Vehicle> getVehiclesFromRentACar(@Param("rentACarId") Long rentACarId);

	@Query(value = "SELECT v.id AS id,"
			+ "v.manufacturer AS manufacturer,"
			+ "v.model AS model,"
			+ "v.year AS year,"
			+ "v.fuel AS fuel,"
			+ "v.engine AS engine,"
			+ "v.transmission AS transmission,"
			+ "v.seats_count AS seatsCount,"
			+ "v.air_condition AS airCondition,"
			+ "v.daily_rental_price AS dailyRentalPrice,"
			+ "v.average_feedback AS averageFeedback FROM vehicles v where branch_office_id = :branchId", nativeQuery = true)
	Collection<VehicleDTO> getVehiclesFromBranch(@Param("branchId") Long branchId);
	
	
	@Query(value = "SELECT v.id AS id,"
			+ "v.manufacturer AS manufacturer,"
			+ "v.model AS model,"
			+ "v.year AS year,"
			+ "v.fuel AS fuel,"
			+ "v.engine AS engine,"
			+ "v.transmission AS transmission,"
			+ "v.seats_count AS seatsCount,"
			+ "v.air_condition AS airCondition,"
			+ "v.daily_rental_price AS dailyRentalPrice,"
			+ "v.average_feedback AS averageFeedback FROM vehicles v where branch_office_id = :branchId and id = :vehicleId", nativeQuery = true)
	VehicleDTO getVehicleFromBranch(@Param("branchId") Long branchId, @Param("vehicleId") Long vehicleId);
	
	
	
	@Query(value = "DELETE FROM branch_office_vehicle WHERE vehicle_id = :vehicleId", nativeQuery = true)
	public void deleteBranchOfficeVehicleConnection(@Param("vehicleId") Long vehicleId);
	
	//kako ovo?
	@Query(value = "SELECT "
			+ "* "
			+ "FROM "
			+ "vehicles v  "
			+ "INNER JOIN "
			+ "branch_office b "
			+ "ON "
			+ "v.branch_office_id = b.id " 
			+ "INNER JOIN "
			+ "locations l "
			+ "ON "
			+ "l.id = b.location_id " 
			+ "WHERE l.country = :country", nativeQuery = true) //promeni l.country - dok ne napravim pretragu za gradove
	Collection<Vehicle> vehiclesFromCountry(@Param("country") String country);
	
	//kako ovo?
	@Query(value = "SELECT "
			+ "* "
			+ "FROM "
			+ "vehicles v  "
			+ "INNER JOIN "
			+ "branch_office b "
			+ "ON "
			+ "v.branch_office_id = b.id " 
			+ "INNER JOIN "
			+ "locations l "
			+ "ON "
			+ "l.id = b.location_id " 
			+ "WHERE l.country = :country and l.city = :city", nativeQuery = true)
	Collection<Vehicle> vehiclesFromCity(@Param("country") String country, @Param("city") String city);
	
	
	
	
	
//treba dodati za sliku i tako svasta nesto	
}

