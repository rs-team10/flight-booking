package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tim10.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query(value = "SELECT * FROM Reservations r WHERE r.registered_user_id = :userId AND r.invitation_code IS NOT NULL", nativeQuery = true)
	List<Reservation> getAllInvitations(@Param("userId") Long userId);
	
	@Query(value = "SELECT * FROM Reservations r WHERE r.invitation_code = :code", nativeQuery = true)
	Reservation findByInvitationCode(@Param("code") String code);
}
