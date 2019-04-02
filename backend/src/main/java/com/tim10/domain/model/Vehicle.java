package com.tim10.domain.model;

import java.math.BigDecimal;
import java.util.Set;

public class Vehicle {
	private Long id;
	private String manufacturer;
	private String model;
	private Integer year;
	private String fuel;
	private String engine;
	private String transmission;
	private Integer seatsCount;
	private Boolean airCondition;
	private BigDecimal dailyRentalPrice;
	private Double averageFeedback;
	private String image;
	private Set<VehicleReservation> reservations;
	private BranchOffice branchOffice;

	public Vehicle() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public Integer getYear() {
		return year;
	}

	public String getFuel() {
		return fuel;
	}

	public String getEngine() {
		return engine;
	}

	public String getTransmission() {
		return transmission;
	}

	public Integer getSeatsCount() {
		return seatsCount;
	}

	public Boolean getAirCondition() {
		return airCondition;
	}

	public BigDecimal getDailyRentalPrice() {
		return dailyRentalPrice;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public String getImage() {
		return image;
	}

	public Set<VehicleReservation> getReservations() {
		return reservations;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public void setSeatsCount(Integer seatsCount) {
		this.seatsCount = seatsCount;
	}

	public void setAirCondition(Boolean airCondition) {
		this.airCondition = airCondition;
	}

	public void setDailyRentalPrice(BigDecimal dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setReservations(Set<VehicleReservation> reservations) {
		this.reservations = reservations;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}

}