package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="QuickFlightReservations")
public class QuickFlightReservation extends FlightReservation {
	
	@Column(name="discount")
	private Double discount;
	
	@Version
	@Column(name="version")
	private Long version;

	public QuickFlightReservation() {
		super();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}