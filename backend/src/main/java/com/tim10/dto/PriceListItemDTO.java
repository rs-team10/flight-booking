package com.tim10.dto;

import java.math.BigDecimal;

import com.tim10.domain.PriceListItem;

public class PriceListItemDTO {
	
	private String name;
	private String description;
	private BigDecimal price;
	private BigDecimal discount;
	
	public PriceListItemDTO() {
		super();
	}
	
	public PriceListItemDTO(PriceListItem p) {
		super();
		this.name = p.getName();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.discount = p.getDiscount();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}
