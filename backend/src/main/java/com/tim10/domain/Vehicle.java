package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Vehicles")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="manufacturer")
	private String manufacturer;
	
	@Column(name="model")
	private String model;
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="fuel")
	private String fuel;
	
	@Column(name="engine")
	private String engine;
	
	@Column(name="transmission")
	private String transmission;
	
	@Column(name="seatsCount")
	private Integer seatsCount;
	
	@Column(name="airCondition")
	private Boolean airCondition;
	
	@Column(name="dailyRentalPrice")
	private BigDecimal dailyRentalPrice;
	
	@Column(name="averageFeedback")
	private Double averageFeedback;
	
	@Column(name="image")
	private String image;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<VehicleReservation> reservations;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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