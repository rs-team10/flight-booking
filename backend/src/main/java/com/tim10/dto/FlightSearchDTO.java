package com.tim10.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tim10.domain.Destination;
import com.tim10.domain.Flight;

public class FlightSearchDTO {
	
	private Long id;
	private String airline;
	private String flightNumber;
	private String departure;
	private String departureAirport;
	private String departureCode;
	private String destination;
	private String destinationAirport;
	private String destinationCode;
	private String departureDate;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private Integer transitCount;
	private List<DestinationDTO> transitDestinations;
	private Integer flightDuration;
	private Integer flightDistance;
	private BigDecimal ticketPrice;
	
	public FlightSearchDTO() {
		super();
		this.transitDestinations = new ArrayList<DestinationDTO>();
	}
	
	public FlightSearchDTO(Flight f) {
		super();
		this.id = f.getId();
		this.airline = f.getAirline().getName();
		this.flightNumber = f.getFlightNumber();
		this.departure = f.getDeparture().getName();
		this.departureAirport = f.getDeparture().getAirportName();
		this.departureCode = f.getDeparture().getAirportCode();
		this.destination = f.getDestination().getName();
		this.destinationAirport = f.getDestination().getAirportName();
		this.destinationCode = f.getDestination().getAirportCode();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		this.departureDate = dateFormat.format(f.getDepartureDate());
		this.departureTime = timeFormat.format(f.getDepartureDate());
		this.arrivalDate = dateFormat.format(f.getArrivalDate());
		this.arrivalTime = timeFormat.format(f.getArrivalDate());
		
		this.transitCount = f.getTransitCount();
		
		this.transitDestinations = new ArrayList<DestinationDTO>();
		
		for (Destination d : f.getTransitDestinations()) {
			this.transitDestinations.add(new DestinationDTO(d));
		}
		
		this.flightDuration = f.getDuration();
		this.flightDistance = f.getDistance();
		this.ticketPrice = f.getTicketPrice();
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

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getDepartureCode() {
		return departureCode;
	}

	public String getDestination() {
		return destination;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public Integer getTransitCount() {
		return transitCount;
	}

	public List<DestinationDTO> getTransitDestinations() {
		return transitDestinations;
	}

	public Integer getFlightDuration() {
		return flightDuration;
	}

	public Integer getFlightDistance() {
		return flightDistance;
	}

	public BigDecimal getTicketPrice() {
		return ticketPrice;
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

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setTransitCount(Integer transitCount) {
		this.transitCount = transitCount;
	}

	public void setTransitDestinations(List<DestinationDTO> transitDestinations) {
		this.transitDestinations = transitDestinations;
	}

	public void setFlightDuration(Integer flightDuration) {
		this.flightDuration = flightDuration;
	}

	public void setFlightDistance(Integer flightDistance) {
		this.flightDistance = flightDistance;
	}

	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
}
