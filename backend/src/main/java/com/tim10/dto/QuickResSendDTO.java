package com.tim10.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.tim10.domain.PriceListItem;
import com.tim10.domain.Vehicle;

public class QuickResSendDTO {

	private Long id;
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal totalPrice;
	private Set<PriceListItem> additionalServices;
	private Double discount;
	private Vehicle vehicle;
	
	
	public QuickResSendDTO() {

	}


	public QuickResSendDTO(Long id, Date dateFrom, Date dateTo, BigDecimal totalPrice,
			Set<PriceListItem> additionalServices, Double discount, Vehicle vehicle) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.totalPrice = totalPrice;
		this.additionalServices = additionalServices;
		this.discount = discount;
		this.vehicle = vehicle;
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


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public Set<PriceListItem> getAdditionalServices() {
		return additionalServices;
	}


	public Double getDiscount() {
		return discount;
	}


	public Vehicle getVehicle() {
		return vehicle;
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


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public void setAdditionalServices(Set<PriceListItem> additionalServices) {
		this.additionalServices = additionalServices;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
	
	
	
	
	
}
