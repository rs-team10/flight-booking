package com.tim10.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="Seats")
public class Seat {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="red")
	private Integer red;
	
	@Column(name="kolona")
	private Integer kolona;
	
	@Enumerated(EnumType.STRING)
	@Column(name="segmentClass")
	private SegmentClass segmentClass;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Flight flight;
	
	@Column(name="isReserved")
	private Boolean isReserved;
	
	@Column(name="isActive")
	private Boolean isActive;
	
	@Version
	@Column(name="version")
	private Long version;

	public Seat() {
		super();
	}
	
	public Seat(Flight flight, Integer red, Integer kolona, SegmentClass segmentClass) {
		this.flight = flight;
		this.red = red;
		this.kolona = kolona;
		this.segmentClass = segmentClass;
		this.isReserved = false;
		this.isActive = true;
	}

	public Long getId() {
		return id;
	}

	public Integer getRed() {
		return red;
	}

	public Integer getKolona() {
		return kolona;
	}

	public SegmentClass getSegmentClass() {
		return segmentClass;
	}

	public Flight getFlight() {
		return flight;
	}

	public Boolean getIsReserved() {
		return isReserved;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public void setKolona(Integer kolona) {
		this.kolona = kolona;
	}

	public void setSegmentClass(SegmentClass segmentClass) {
		this.segmentClass = segmentClass;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public void setIsReserved(Boolean isReserved) {
		this.isReserved = isReserved;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}