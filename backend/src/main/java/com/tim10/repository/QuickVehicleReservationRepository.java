package com.tim10.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.tim10.domain.QuickVehicleReservation;

public interface QuickVehicleReservationRepository extends JpaRepository<QuickVehicleReservation, Long>{
	
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	QuickVehicleReservation findOneById(Long id);

}
