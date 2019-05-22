package com.tim10.dto;

import java.math.BigDecimal;
import java.util.List;

public class FilterDTO {
	
	private Long departureId;
	private Long destinationId;
	private String departureDate;
	private Integer passengerCount;
	private String flightClassString;
	private List<String> airlines;
	private Integer stopsCount;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	
	public FilterDTO() {
		super();
	}

	public Long getDepartureId() {
		return departureId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public String getFlightClassString() {
		return flightClassString;
	}

	public List<String> getAirlines() {
		return airlines;
	}

	public Integer getStopsCount() {
		return stopsCount;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setDepartureId(Long departureId) {
		this.departureId = departureId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public void setFlightClassString(String flightClassString) {
		this.flightClassString = flightClassString;
	}

	public void setAirlines(List<String> airlines) {
		this.airlines = airlines;
	}

	public void setStopsCount(Integer stopsCount) {
		this.stopsCount = stopsCount;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
}
