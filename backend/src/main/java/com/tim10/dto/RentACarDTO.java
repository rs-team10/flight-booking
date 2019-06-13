package com.tim10.dto;

import com.tim10.domain.PriceList;

public class RentACarDTO {
	
	
	private Long id;
	private String name;
	private String description;
	private PriceList additionalServicesPriceList;
	
	
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public PriceList getAdditionalServicesPriceList() {
		return additionalServicesPriceList;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}
	
	
	
}
