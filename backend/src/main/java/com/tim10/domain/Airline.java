package com.tim10.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tim10.dto.NewAirlineDTO;

@Entity
@Table(name="Airlines")
public class Airline extends Company {
	
	private static final long serialVersionUID = 1L;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Destination> businessLocations;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Flight> flights;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<AirlineAdmin> administrators;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<QuickFlightReservation> quickFlightReservations;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private PriceList luggagePriceList;

	public Airline() {
		businessLocations = new HashSet<Destination>();
		flights = new HashSet<Flight>();
		administrators = new HashSet<AirlineAdmin>();
		quickFlightReservations = new HashSet<QuickFlightReservation>();
		luggagePriceList = new PriceList();
	}
	
	public Airline(NewAirlineDTO dto) {
		businessLocations = new HashSet<Destination>();
		flights = new HashSet<Flight>();
		quickFlightReservations = new HashSet<QuickFlightReservation>();
		luggagePriceList = new PriceList();
		this.setName(dto.getName());
		this.setLocation(dto.getLocation());
		administrators = dto.getAdministrators();
	}

	public Set<Destination> getBusinessLocations() {
		return businessLocations;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public Set<AirlineAdmin> getAdministrators() {
		return administrators;
	}

	public PriceList getLuggagePriceList() {
		return luggagePriceList;
	}

	public Set<QuickFlightReservation> getQuickFlightReservations() {
		return quickFlightReservations;
	}

	public void setBusinessLocations(Set<Destination> businessLocations) {
		this.businessLocations = businessLocations;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public void setAdministrators(Set<AirlineAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setLuggagePriceList(PriceList luggagePriceList) {
		this.luggagePriceList = luggagePriceList;
	}

	public void setQuickFlightReservations(Set<QuickFlightReservation> quickFlightReservations) {
		this.quickFlightReservations = quickFlightReservations;
	}
}