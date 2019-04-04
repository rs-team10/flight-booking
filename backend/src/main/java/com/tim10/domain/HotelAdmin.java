package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class HotelAdmin extends User {
	
	@Id
	private Long id;
	
	@Column(nullable=false)
	private Boolean hasCustomPassword;
	
	//@ManyToOne
	@Transient
	public Hotel company;

	public HotelAdmin() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	
	public Boolean getHasCustomPassword() {
		return hasCustomPassword;
	}

	public Hotel getCompany() {
		return company;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setHasCustomPassword(Boolean hasCustomPassword) {
		this.hasCustomPassword = hasCustomPassword;
	}

	public void setCompany(Hotel company) {
		this.company = company;
	}
}