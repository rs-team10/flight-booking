package com.tim10.domain.model;

import java.util.Set;

public class Airline extends Company {

	private Set<Destination> businessLocations;
	private Set<Flight> flights;
	private Set<AirlineAdmin> administrators;
	private Set<QuickFlightReservation> quickFlightReservations;
	private PriceList luggagePriceList;

	public Airline() {
		super();
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