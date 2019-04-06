package com.tim10.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Flights")
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="flightNumber", nullable=false)
	private String flightNumber;
	
	@Column(name="ticketPrice")
	private BigDecimal ticketPrice;
	
	@Column(name="date")
	private LocalDateTime date;
	
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="distance")
	private Integer distance;
	
	@Column(name="transitCount")
	private Integer transitCount;
	
	@Column(name="averageFeedback")
	private Double averageFeedback;
	
	//=======================================
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Destination destination;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Destination departure;
	//=======================================
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Destination> transitDestinations;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Seat> seats;
	
	@ManyToOne(fetch=FetchType.LAZY)
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

//	public Destination getDestination() {
//		return destination;
//	}
//
//	public Destination getDeparture() {
//		return departure;
//	}

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

//	public void setDestination(Destination destination) {
//		this.destination = destination;
//	}
//
//	public void setDeparture(Destination departure) {
//		this.departure = departure;
//	}

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