package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="QuickRoomReservations")
public class QuickRoomReservation extends RoomReservation {
	
	@Column(name="discount", nullable=false)
	private Double discount;

	public QuickRoomReservation() {
		super();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}