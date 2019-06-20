package com.tim10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class HotelAdmin extends User {
	
	private static final long serialVersionUID = 1L;

	@Column(name="hasCustomPassword")
	private Boolean hasCustomPassword;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	//@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public HotelAdmin() {
		super();
		hasCustomPassword = false;
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_HOTEL_ADMIN);
		this.getAuthorities().add(aut);

	}
	
	//bezveze ali potrebno za registraciju hotela
	//ne treba vise???
	public HotelAdmin(User user) {
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setEmail(user.getEmail());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		
		hasCustomPassword = false;
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_HOTEL_ADMIN);
		this.getAuthorities().add(aut);
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