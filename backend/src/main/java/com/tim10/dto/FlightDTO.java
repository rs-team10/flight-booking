package com.tim10.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tim10.domain.Destination;
import com.tim10.domain.Flight;

public class FlightDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String airline;
	private String flightNumber;
	private String departure;
	private String departureCode;
	private String destination;
	private String destinationCode;
	private List<String> transitDestinations;
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
	private Double averageFeedback;
	
	public FlightDTO() {
	}
	
	public FlightDTO(Flight flight) {
		this.id = flight.getId();
		this.airline = flight.getAirline().getName();
		this.flightNumber = flight.getFlightNumber();
		this.departure = flight.getDeparture().getName();
		this.departureCode = flight.getDeparture().getAirportCode();
		this.destination = flight.getDestination().getName();
		this.destinationCode = flight.getDestination().getAirportCode();
		
		this.transitDestinations = new ArrayList<String>();
		
		for (Destination d : flight.getTransitDestinations()) {
			this.transitDestinations.add(d.getName());
		}
		
		this.departureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(flight.getDepartureDate());
		this.arrivalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(flight.getArrivalDate());
		
		this.ticketPrice = flight.getTicketPrice();
		this.duration = flight.getDuration();
		this.distance = flight.getDistance();
		
		// todo seats
		
		this.averageFeedback = flight.getAverageFeedback();
	}

	public Long getId() {
		return id;
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

	public List<String> getTransitDestinations() {
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

	public void setId(Long id) {
		this.id = id;
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

	public void setTransitDestinations(List<String> transitDestinations) {
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

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public String getDepartureCode() {
		return departureCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	
	
}
