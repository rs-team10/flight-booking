package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AirlineAdmin extends User {
	
	@Column(name="hasCustomPassword")
	private Boolean hasCustomPassword;

	@ManyToOne(fetch=FetchType.EAGER)
	private Airline company;
	
	public AirlineAdmin() {
		super();
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