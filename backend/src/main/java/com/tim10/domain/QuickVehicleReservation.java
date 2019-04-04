package com.tim10.domain;

public class QuickVehicleReservation extends VehicleReservation {

	private Long id;
	private Double discount;

	public QuickVehicleReservation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}