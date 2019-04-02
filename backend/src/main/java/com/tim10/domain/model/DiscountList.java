
package com.tim10.domain.model;

import java.util.Set;

public class DiscountList {
	
	private Long id;

	private Set<DiscountListItem> discountListItem;

	public DiscountList() {
	}

	public Long getId() {
		return id;
	}

	public Set<DiscountListItem> getDiscountListItem() {
		return discountListItem;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDiscountListItem(Set<DiscountListItem> discountListItem) {
		this.discountListItem = discountListItem;
	}

}