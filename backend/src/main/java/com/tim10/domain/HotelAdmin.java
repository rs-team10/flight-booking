package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class HotelAdmin extends User {
	
	@Column(name="hasCustomPassword", nullable=false)
	private Boolean hasCustomPassword;
	
	@ManyToOne(fetch=FetchType.EAGER)
	public Hotel hotel;

	public HotelAdmin() {
		super();
	}
	
	public Boolean getHasCustomPassword() {
		return hasCustomPassword;
	}

	public Hotel getHotel() {
		return hotel;
	}
	
	public void setHasCustomPassword(Boolean hasCustomPassword) {
		this.hasCustomPassword = hasCustomPassword;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}