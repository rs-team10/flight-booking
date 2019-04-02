package com.tim10.domain;

import java.math.BigDecimal;

public class PriceListItem {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private BigDecimal discount;

	public PriceListItem() {
		super();
	}

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
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