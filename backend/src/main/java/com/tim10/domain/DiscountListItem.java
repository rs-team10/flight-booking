package com.tim10.domain;

public class DiscountListItem {
	
	private Long id;
	private Integer minPoints;
	private Integer maxPoints;
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