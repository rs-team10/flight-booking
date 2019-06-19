package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.Location;
import com.tim10.domain.RentACar;
import com.tim10.domain.Vehicle;

public class RcsDTO {

	private String name;
	private String description;
	private Location location;
	private Double averageFeedback;
	private Set<BranchOffice> branchOffices;
	private Set<VehicleDTO> vehicles;

	public RcsDTO() {

	}

	public RcsDTO(RentACar rcs) {
		name = rcs.getName();
		description = rcs.getDescription();
		location = rcs.getLocation();
		averageFeedback = rcs.getAverageFeedback();
		branchOffices = rcs.getBranchOffices();
		for (BranchOffice bo : branchOffices) {
			for (Vehicle v : bo.getVehicle())
				vehicles.add(new VehicleDTO(v));
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Location getLocation() {
		return location;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Set<BranchOffice> getBranchOffices() {
		return branchOffices;
	}

	public Set<VehicleDTO> getVehicles() {
		return vehicles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setBranchOffices(Set<BranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}

	public void setVehicles(Set<VehicleDTO> vehicles) {
		this.vehicles = vehicles;
	}

}
