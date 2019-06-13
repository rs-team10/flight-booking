package com.tim10.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tim10.domain.PriceListItem;

public class VehicleReservationDTO {


	private Date dateFrom;
	
	private Date dateTo;
	
	private Set<PriceListItem> additionalServices;
	
	private Long vehicleId;

	
	public VehicleReservationDTO() {
		
		additionalServices = new HashSet<PriceListItem>();
	}
	
	
	
	
	public VehicleReservationDTO(Date dateFrom, Date dateTo, BigDecimal totalPrice,
			Set<PriceListItem> additionalServices, Long vehicleId) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.additionalServices = additionalServices;
		this.vehicleId = vehicleId;
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

	
	
	
	
	
	
	
	
}
