package com.tim10.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="BranchOffice")
public class BranchOffice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "locationId")
	private Location location;
	
	@JsonIgnore
	@OneToMany(mappedBy="branchOffice", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Vehicle> vehicle;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "mainOfficeId")
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