package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DiscountListItems")
public class DiscountListItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="minPoints")
	private Integer minPoints;
	
	@Column(name="maxPoints")
	private Integer maxPoints;
	
	@Column(name="discount")
	private Double discount;

	public DiscountListItem() {
	}

	public Long getId() {
		return id;
	}

	public Integer getMinPoints() {
		return minPoints;
	}

	public Integer getMaxPoints() {
		return maxPoints;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

	public void setMaxPoints(Integer maxPoints) {
		this.maxPoints = maxPoints;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

}