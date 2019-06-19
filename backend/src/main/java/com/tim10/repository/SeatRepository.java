package com.tim10.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.tim10.domain.Seat;


public interface SeatRepository extends JpaRepository<Seat, Long> {

	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	Seat findOneById(Long id);
}
