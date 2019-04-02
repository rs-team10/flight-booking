package com.tim10.domain;

import java.util.*;

public class BranchOffice {
	private Long id;
	private Location location;

	private Set<Vehicle> vehicle;
	private RentACar mainOffice;

	public BranchOffice() {
	}

	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public Set<Vehicle> getVehicle() {
		return vehicle;
	}

	public RentACar getMainOffice() {
		return mainOffice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setVehicle(Set<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public void setMainOffice(RentACar mainOffice) {
		this.mainOffice = mainOffice;
	}

}