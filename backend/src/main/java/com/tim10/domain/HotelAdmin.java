package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class HotelAdmin extends User {
	
	@Column(name="hasCustomPassword")
	private Boolean hasCustomPassword;
	
	@ManyToOne(fetch=FetchType.EAGER)
	public Hotel hotel;

	public HotelAdmin() {
		super();
		hasCustomPassword = false;
		//this.setRole(Role.ROLE_HOTEL_ADMIN);
		
		//Ne znam sta se desava sa tabelom autoriteta u bazi - bukvalno veze blage nemam
		//ako se sa onim joinom tabela i ova druga tabela updatuje ovo je okej
		//u drugom slucaju bi moralo i tamo da se cuvaju role pre nog se dodele
		//odredjenom useru...
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