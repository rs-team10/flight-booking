package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Seat;


public interface SeatRepository extends JpaRepository<Seat, Long> {

}
