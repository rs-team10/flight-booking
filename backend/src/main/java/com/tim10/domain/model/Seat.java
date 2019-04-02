package com.tim10.domain.model;

public class Seat {
	private Long id;
	private Integer row;
	private Integer column;
	private SegmentClass segmentClass;
	private Flight flight;

	public Seat() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Integer getRow() {
		return row;
	}

	public Integer getColumn() {
		return column;
	}

	public SegmentClass getSegmentClass() {
		return segmentClass;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public void setSegmentClass(SegmentClass segmentClass) {
		this.segmentClass = segmentClass;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

}