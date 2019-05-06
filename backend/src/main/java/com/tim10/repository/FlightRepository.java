package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
