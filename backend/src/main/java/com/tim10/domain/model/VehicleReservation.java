package com.tim10.domain.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class VehicleReservation {
	private Long id;
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal totalPrice;
	private Set<PriceListItem> additionalServices;
	private Reservation reservation;
	private Review review;
	private Vehicle reservedVehicle;

	public VehicleReservation() {
		super();
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

	public Reservation getReservation() {
		return reservation;
	}

	public Review getReview() {
		return review;
	}

	public Vehicle getReservedVehicle() {
		return reservedVehicle;
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

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public void setReservedVehicle(Vehicle reservedVehicle) {
		this.reservedVehicle = reservedVehicle;
	}

}