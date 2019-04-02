package com.tim10.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class Flight {
	
	private Long id;
	private String flightNumber;
	private BigDecimal ticketPrice;
	private LocalDateTime date;
	private Integer duration;
	private Integer distance;
	private Integer transitCount;
	private Double averageFeedback;

	private Destination destination;
	private Destination departure;
	private Set<Destination> transitDestinations;
	private Set<Seat> seats;
	private Airline airline;

	public Flight() {
	}

	public Long getId() {
		return id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Integer getDuration() {
		return duration;
	}

	public Integer getDistance() {
		return distance;
	}

	public Integer getTransitCount() {
		return transitCount;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Destination getDestination() {
		return destination;
	}

	public Destination getDeparture() {
		return departure;
	}

	public Set<Destination> getTransitDestinations() {
		return transitDestinations;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public void setTransitCount(Integer transitCount) {
		this.transitCount = transitCount;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setDeparture(Destination departure) {
		this.departure = departure;
	}

	public void setTransitDestinations(Set<Destination> transitDestinations) {
		this.transitDestinations = transitDestinations;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

}