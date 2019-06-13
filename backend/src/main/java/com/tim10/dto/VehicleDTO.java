package com.tim10.dto;

import java.math.BigDecimal;

public class VehicleDTO {
		
	private Long id;
	private String manufacturer;
	private String model;
	private Integer year;
	private String fuel;
	private Double engine;
	private Boolean transmission;
	private Integer seatsCount;
	private Boolean airCondition;
	private BigDecimal dailyRentalPrice;
	private Double averageFeedback;
	private String image;
	
	

	
	public VehicleDTO() {
		
	}
	
	

	public VehicleDTO(Long id, String manufacturer, String model, Integer year, String fuel, Double engine,
			Boolean transmission, Integer seatsCount, Boolean airCondition, BigDecimal dailyRentalPrice,
			Double averageFeedback, String image) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.model = model;
		this.year = year;
		this.fuel = fuel;
		this.engine = engine;
		this.transmission = transmission;
		this.seatsCount = seatsCount;
		this.airCondition = airCondition;
		this.dailyRentalPrice = dailyRentalPrice;
		this.averageFeedback = averageFeedback;
		this.image = image;
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

	public Double getEngine() {
		return engine;
	}

	public Boolean getTransmission() {
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

	public void setEngine(Double engine) {
		this.engine = engine;
	}

	public void setTransmission(Boolean transmission) {
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

	
	
	
	
}
