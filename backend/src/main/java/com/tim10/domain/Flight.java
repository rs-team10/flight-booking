package com.tim10.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Airline airline;
	
	@Column(name="flightNumber", nullable=false)
	private String flightNumber;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "departure")
	private Destination departure;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "destination")
	private Destination destination;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Destination> transitDestinations;
	
	@Column(name="departureDate")
	private Date departureDate;
	
	@Column(name="arrivalDate")
	private Date arrivalDate;
	
	@Column(name="ticketPrice")
	private BigDecimal ticketPrice;
	
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="distance")
	private Integer distance;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Seat> seats;
	
	@Column(name="transitCount")
	private Integer transitCount;
	
	@Column(name="averageFeedback")
	private Double averageFeedback;

	public Flight() {
		transitDestinations = new HashSet<Destination>();
		seats = new HashSet<Seat>();
	}

	public Long getId() {
		return id;
	}

	public Airline getAirline() {
		return airline;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public Destination getDeparture() {
		return departure;
	}

	public Destination getDestination() {
		return destination;
	}

	public Set<Destination> getTransitDestinations() {
		return transitDestinations;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public Date getArrivalDate() {
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

	public Set<Seat> getSeats() {
		return seats;
	}

	public Integer getTransitCount() {
		return transitCount;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setDeparture(Destination departure) {
		this.departure = departure;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setTransitDestinations(Set<Destination> transitDestinations) {
		this.transitDestinations = transitDestinations;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public void setArrivalDate(Date arrivalDate) {
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

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public void setTransitCount(Integer transitCount) {
		this.transitCount = transitCount;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

}