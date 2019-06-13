	package com.tim10.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="RoomTypes")
public class RoomType implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="pricePerNight")
	private BigDecimal pricePerNight;
	
	@Column(name="capacity")
	private int capacity;
	
	@Column(name="singleBedCount")
	private Integer singleBedCount;
	
	@Column(name="doubleBedCount")
	private Integer doubleBedCount;
	
	@Column(name="hasTV")
	private Boolean hasTV;
	
	@Column(name="squareFootage")
	private Integer squareFootage;
	
	@Column(name="hasBalcony")
	private Boolean hasBalcony;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image")
	private String image;
	
	@Column(name="averageFeedback")							
	private Double averageFeedback;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private Set<SpecialRoomPrice> specialRoomPrices;

	public RoomType() {
		specialRoomPrices = new HashSet<SpecialRoomPrice>();
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

	public int getCapacity() {
		return capacity;
	}

	public Integer getSingleBedCount() {
		return singleBedCount;
	}

	public Integer getDoubleBedCount() {
		return doubleBedCount;
	}

	public Boolean getHasTV() {
		return hasTV;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Set<SpecialRoomPrice> getSpecialRoomPrices() {
		return specialRoomPrices;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setSingleBedCount(Integer singleBedCount) {
		this.singleBedCount = singleBedCount;
	}

	public void setDoubleBedCount(Integer doubleBedCount) {
		this.doubleBedCount = doubleBedCount;
	}

	public void setHasTV(Boolean hasTV) {
		this.hasTV = hasTV;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSquareFootage() {
		return squareFootage;
	}

	public Boolean getHasBalcony() {
		return hasBalcony;
	}

	public void setSquareFootage(Integer squareFootage) {
		this.squareFootage = squareFootage;
	}

	public void setHasBalcony(Boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setSpecialRoomPrices(Set<SpecialRoomPrice> specialRoomPrices) {
		this.specialRoomPrices = specialRoomPrices;
	}

}