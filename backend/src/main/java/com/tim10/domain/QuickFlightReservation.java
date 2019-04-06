package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="QuickFlightReservations")
public class QuickFlightReservation extends FlightReservation {
	
	@Column(name="discount")
	private Double discount;

	public QuickFlightReservation() {
		super();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}