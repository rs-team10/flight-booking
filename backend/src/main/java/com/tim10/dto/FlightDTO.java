package com.tim10.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tim10.domain.Flight;

public class FlightDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String airline;
	private String flightNumber;
	private String departure;
	private String destination;
	private String[] transitDestinations;
	private String departureDate;
	private String arrivalDate;
	private BigDecimal ticketPrice;
	private Integer duration;
	private Integer distance;
	private Integer rowsCount;
	private Integer columnsCount;
	private Integer firstClassSeatsCount;
	private Integer businessClassSeatsCount;
	private Integer ecoClassSeatsCount;
	
	public FlightDTO() {
	}
	
	public FlightDTO(Flight flight) {
		this.airline = flight.getAirline().getName();
	}

	public String getAirline() {
		return airline;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public String getDeparture() {
		return departure;
	}

	public String getDestination() {
		return destination;
	}

	public String[] getTransitDestinations() {
		return transitDestinations;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public Integer getDuration() {
		return duration;
	}

	public Integer getDistance() {
		return distance;
	}

	public Integer getRowsCount() {
		return rowsCount;
	}

	public Integer getColumnsCount() {
		return columnsCount;
	}

	public Integer getFirstClassSeatsCount() {
		return firstClassSeatsCount;
	}

	public Integer getBusinessClassSeatsCount() {
		return businessClassSeatsCount;
	}

	public Integer getEcoClassSeatsCount() {
		return ecoClassSeatsCount;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setTransitDestinations(String[] transitDestinations) {
		this.transitDestinations = transitDestinations;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public void setRowsCount(Integer rowsCount) {
		this.rowsCount = rowsCount;
	}

	public void setColumnsCount(Integer columnsCount) {
		this.columnsCount = columnsCount;
	}

	public void setFirstClassSeatsCount(Integer firstClassSeatsCount) {
		this.firstClassSeatsCount = firstClassSeatsCount;
	}

	public void setBusinessClassSeatsCount(Integer businessClassSeatsCount) {
		this.businessClassSeatsCount = businessClassSeatsCount;
	}

	public void setEcoClassSeatsCount(Integer ecoClassSeatsCount) {
		this.ecoClassSeatsCount = ecoClassSeatsCount;
	}
}
