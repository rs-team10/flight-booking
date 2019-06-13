package com.tim10.dto;

import java.util.Date;

import com.tim10.domain.PriceList;

public class VehicleReservationPrewDTO {

	
	private Long rentACarId;
	private String rentACarName;
	private Long branchOfficeId;
	private String country;
	private String city;
	private Long vehicleId;
	private PriceList priceList;
	
	private Date from;
	private Date to;
	
	public VehicleReservationPrewDTO() {
		
	}
	public VehicleReservationPrewDTO(Long rentACarId, String rentACarName, Long branchOfficeId, String country,
			String city, Long vehicleId, PriceList priceList, Date from, Date to) {
		super();
		this.rentACarId = rentACarId;
		this.rentACarName = rentACarName;
		this.branchOfficeId = branchOfficeId;
		this.country = country;
		this.city = city;
		this.vehicleId = vehicleId;
		this.priceList = priceList;
		this.from = from;
		this.to = to;
	}
	public VehicleReservationPrewDTO(Long rentACarId, String rentACarName, Long branchOfficeId, String country,
			String city, Long vehicleId, PriceList priceList) {
		super();
		this.rentACarId = rentACarId;
		this.rentACarName = rentACarName;
		this.branchOfficeId = branchOfficeId;
		this.country = country;
		this.city = city;
		this.vehicleId = vehicleId;
		this.priceList = priceList;
	}
	
	public Long getRentACarId() {
		return rentACarId;
	}
	public String getRentACarName() {
		return rentACarName;
	}
	public Long getBranchOfficeId() {
		return branchOfficeId;
	}
	public String getCountry() {
		return country;
	}
	public String getCity() {
		return city;
	}
	public Long getVehicleId() {
		return vehicleId;
	}
	public PriceList getPriceList() {
		return priceList;
	}
	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
	public void setRentACarId(Long rentACarId) {
		this.rentACarId = rentACarId;
	}
	public void setRentACarName(String rentACarName) {
		this.rentACarName = rentACarName;
	}
	public void setBranchOfficeId(Long branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
	
	
	
	
	
	
	
	
}
