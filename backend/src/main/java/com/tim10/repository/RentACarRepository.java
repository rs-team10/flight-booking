package com.tim10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.RentACar;

public interface RentACarRepository extends JpaRepository<RentACar, Long>{

	 Optional<RentACar> findOneByName(String name);
}