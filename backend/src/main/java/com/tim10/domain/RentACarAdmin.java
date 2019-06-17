package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RentACarAdmin extends User {


	@Column(name="hasCustomPassword")
	private Boolean hasCustomPassword;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "rentACarId")//nullable false
	private RentACar company;

	public RentACarAdmin() {
		super();
		hasCustomPassword = false;
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_RENT_A_CAR_ADMIN);
		this.getAuthorities().add(aut);
	}

	public Boolean getHasCustomPassword() {
		return hasCustomPassword;
	}

	public RentACar getCompany() {
		return company;
	}

	public void setHasCustomPassword(Boolean hasCustomPassword) {
		this.hasCustomPassword = hasCustomPassword;
	}

	public void setCompany(RentACar company) {
		this.company = company;
	}
}