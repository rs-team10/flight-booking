package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.GroupReservation;

public interface GroupReservationRepository  extends JpaRepository<GroupReservation, Long> {

}
