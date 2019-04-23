package com.tim10.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="SpecialRoomPrices")
public class SpecialRoomPrice implements Serializable {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="validFrom")
	@Type(type="date")
	private Date validFrom;
	
	@Column(name="validTo")
	@Type(type="date")
	private Date validTo;

	public SpecialRoomPrice() {
		validFrom = new Date();
		validTo = new Date();
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