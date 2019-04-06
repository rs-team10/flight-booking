package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="QuickVehicleReservations")
public class QuickVehicleReservation extends VehicleReservation {

	@Column(name="discount", nullable=false)
	private Double discount;

	public QuickVehicleReservation() {
		super();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}