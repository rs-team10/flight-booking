package com.tim10.domain.model;

public class RentACarAdmin extends User {

	private Boolean hasCustomPassword;
	private RentACar company;

	public RentACarAdmin() {
		super();
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