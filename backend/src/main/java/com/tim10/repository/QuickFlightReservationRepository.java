package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.QuickFlightReservation;

public interface QuickFlightReservationRepository extends JpaRepository<QuickFlightReservation, Long> {

}
