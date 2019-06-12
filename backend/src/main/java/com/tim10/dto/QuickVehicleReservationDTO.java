package com.tim10.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tim10.domain.PriceListItem;

public class QuickVehicleReservationDTO {

	
	private Long id;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private Set<PriceListItem> additionalServices;
	
	private Long vehicleId;
	
	private String manufacturer;
	
	private String model;
	
	private BigDecimal totalPrice;
	
	private Double discount;
	
	


	
	public QuickVehicleReservationDTO(Long id, Date dateFrom, Date dateTo, Set<PriceListItem> additionalServices,
			Long vehicleId, String manufacturer, String model, BigDecimal totalPrice, Double discount) {
		super();
		this.setId(id);
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.additionalServices = additionalServices;
		this.vehicleId = vehicleId;
		this.manufacturer = manufacturer;
		this.model = model;
		this.totalPrice = totalPrice;
		this.discount = discount;
	}



	public QuickVehicleReservationDTO() {
		
		this.additionalServices = new HashSet<PriceListItem>();
	}

	
	
	public Long getId() {
		return id;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public Set<PriceListItem> getAdditionalServices() {
		return additionalServices;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Double getDiscount() {
		return discount;
	}
	



	public void setId(Long id) {
		this.id = id;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public void setAdditionalServices(Set<PriceListItem> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}




	
	
	
	
	
	
	
	

	
	
}
