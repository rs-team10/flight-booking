package com.tim10.domain.model;

public class HotelAdmin extends User {

	private Boolean hasCustomPassword;
	public Hotel company;

	public HotelAdmin() {
		super();
	}

	public Boolean getHasCustomPassword() {
		return hasCustomPassword;
	}

	public Hotel getCompany() {
		return company;
	}

	public void setHasCustomPassword(Boolean hasCustomPassword) {
		this.hasCustomPassword = hasCustomPassword;
	}

	public void setCompany(Hotel company) {
		this.company = company;
	}
}