package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SpecialRoomPrice {
	
	@Id
	private Long id;
	
	@Column(nullable=false)
	private BigDecimal price;
	
	@Column(nullable=false)
	private Date validFrom;
	
	@Column(nullable=false)
	private Date validTo;

	public SpecialRoomPrice() {
		super();
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

}