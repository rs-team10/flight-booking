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

@Entity
@Table(name="Seats")
public class Seat {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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

	public Seat() {
		super();
	}
	
	public Seat(Flight flight, Integer red, Integer kolona, SegmentClass segmentClass) {
		this.flight = flight;
		this.red = red;
		this.kolona = kolona;
		this.segmentClass = segmentClass;
		this.isReserved = false;
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
	
}