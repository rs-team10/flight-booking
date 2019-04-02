package com.tim10.domain;

import java.util.Set;

public class PriceList {

	private Long id;
	private Set<PriceListItem> priceListItems;

	public PriceList() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Set<PriceListItem> getPriceListItems() {
		return priceListItems;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPriceListItems(Set<PriceListItem> priceListItems) {
		this.priceListItems = priceListItems;
	}
}