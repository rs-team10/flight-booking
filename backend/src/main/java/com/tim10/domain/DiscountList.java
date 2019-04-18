
package com.tim10.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DiscountLists")
public class DiscountList {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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