package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class QuickRoomReservation extends RoomReservation {
	
	@Column(name="discount")
	private Double discount;

	public QuickRoomReservation() {
		super();
	}
	
	public QuickRoomReservation(Date dateFrom, Date dateTo, BigDecimal totalPrice,
			Set<PriceListItem> additionalServices, Room room, Double discount) {
		super(dateFrom, dateTo, totalPrice, additionalServices, room);
		this.discount = discount;
	}


	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}