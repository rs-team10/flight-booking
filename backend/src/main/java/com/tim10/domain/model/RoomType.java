package com.tim10.domain.model;

import java.math.BigDecimal;
import java.util.Set;

public class RoomType {
	private Long id;
	private String type;
	private BigDecimal pricePerNight;
	private int capacity;
	private Integer singleBedCount;
	private Integer doubleBedCount;
	private Boolean hasTV;
	private String description;
	private String image;
	private Double averageFeedback;
	private Set<SpecialRoomPrice> specialRoomPrices;

	public RoomType() {
		super();
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

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setSpecialRoomPrices(Set<SpecialRoomPrice> specialRoomPrices) {
		this.specialRoomPrices = specialRoomPrices;
	}

}