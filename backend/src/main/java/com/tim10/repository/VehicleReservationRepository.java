package com.tim10.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.QuickVehicleReservation;
import com.tim10.domain.VehicleReservation;

public interface VehicleReservationRepository extends JpaRepository<VehicleReservation, Long>{
	
	
	
	@Query(value = "SELECT * FROM vehicle_reservations qr LEFT JOIN vehicles v ON qr.reserved_vehicle_id = v.id WHERE reserved_vehicle_id=:vehicleId", nativeQuery = true)
	Collection<QuickVehicleReservation> getAllQuickResFromVehicle(@Param("vehicleId") Long vehicleId);
	
	
	
	

}
