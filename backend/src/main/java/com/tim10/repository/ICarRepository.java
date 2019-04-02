package com.tim10.repository;

import java.util.Collection;

import com.tim10.domain.Vehicle;

public interface ICarRepository {

	Collection<Vehicle> findAll();

	Collection<Vehicle> find(Vehicle car);

	Vehicle findById(Long id);

	Vehicle create(Vehicle car);

	Vehicle update(Vehicle car);

	Vehicle delete(Long id);

}
