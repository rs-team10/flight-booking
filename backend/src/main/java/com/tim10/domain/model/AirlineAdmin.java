package com.tim10.domain.model;

public class AirlineAdmin extends User {
	
	private Boolean hasCustomPassword;

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