package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
