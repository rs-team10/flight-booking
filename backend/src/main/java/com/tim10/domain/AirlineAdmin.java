package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AirlineAdmin extends User {
	
	private static final long serialVersionUID = 1L;

	@Column(name="hasCustomPassword")
	private Boolean hasCustomPassword;

	@ManyToOne(fetch=FetchType.EAGER)
	private Airline company;
	
	public AirlineAdmin() {
		super();
		hasCustomPassword = false;
		Authority aut = new Authority();
		aut.setRole(Role.ROLE_AIRLINE_ADMIN);
		this.getAuthorities().add(aut);
	}

	public Boolean getHasCustomPassword() {
		return hasCustomPassword;
	}

	public Airline getCompany() {
		return company;
	}

	public void setHasCustomPassword(Boolean hasCustomPassword) {
		this.hasCustomPassword = hasCustomPassword;
	}

	public void setCompany(Airline company) {
		this.company = company;
	}

}