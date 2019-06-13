package com.tim10.dto;

import com.tim10.domain.Seat;
import com.tim10.domain.SegmentClass;

public class SeatDTO {
	
	private Long id;
	private Integer red;
	private Integer kolona;
	private SegmentClass segmentClass;
	private Boolean isReserved;
	private Boolean isActive;
	private Boolean isSelected;
	
	public SeatDTO() {
		super();
	}
	
	public SeatDTO(Seat s) {
		this.id = s.getId();
		this.red = s.getRed();
		this.kolona = s.getKolona();
		this.segmentClass = s.getSegmentClass();
		this.isReserved = s.getIsReserved();
		this.isActive = s.getIsActive();
		this.isSelected = false;
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

	public Boolean getIsReserved() {
		return isReserved;
	}

	public Boolean getIsActive() {
		return isActive;
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

	public void setIsReserved(Boolean isReserved) {
		this.isReserved = isReserved;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
