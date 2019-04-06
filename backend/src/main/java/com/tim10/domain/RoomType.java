package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="RoomTypes")
public class RoomType {
	
	@Id
	private Long id;
	
	@Column(name="type", nullable=false)
	private String type;
	
	@Column(name="pricePerNight", nullable=false)
	private BigDecimal pricePerNight;
	
	@Column(name="capacity", nullable=false)
	private int capacity;
	
	@Column(name="singleBedCount", nullable=false)
	private Integer singleBedCount;
	
	@Column(name="doubleBedCount", nullable=false)
	private Integer doubleBedCount;
	
	@Column(name="hasTV")
	private Boolean hasTV;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image", nullable=false)
	private String image;
	
	@Column(name="averageFeedback")							
	//moze biti null ako nema nikakav feedback, ne prikazujes korisniku
	private Double averageFeedback;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
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