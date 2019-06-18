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
	public Hotel hotel;

	public HotelAdmin() {
		super();
		hasCustomPassword = false;
		
		//Ne znam sta se desava sa tabelom autoriteta u bazi - bukvalno veze blage nemam
		//ako se sa onim joinom tabela i ova druga tabela updatuje ovo je okej
		//u drugom slucaju bi moralo i tamo da se cuvaju role pre nog se dodele
		//odredjenom useru...
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_HOTEL_ADMIN);
		this.getAuthorities().add(aut);

	}
	
	//bezveze ali potrebno za registraciju hotela
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