package com.tim10.dto;

import java.util.HashSet;
import java.util.Set;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;
import com.tim10.domain.QuickFlightReservation;

public class AirlineDTO {

	private AirlineProfileDTO airlineProfileDTO;
	private Set<Destination> destinations;
	private Set<QuickFlightReservationDTO> quickFlightReservations;

	public AirlineDTO(Airline airline) {
		airlineProfileDTO = new AirlineProfileDTO(airline);
		destinations = airline.getBusinessLocations();
		
		quickFlightReservations = new HashSet<QuickFlightReservationDTO>();
		
		for (QuickFlightReservation qfr : airline.getQuickFlightReservations()) {
			
			QuickFlightReservationDTO dto = new QuickFlightReservationDTO();
			dto.setId(qfr.getId());
			dto.setSeatId(qfr.getSeat().getId());
			dto.setSeatNumber(qfr.getSeat().getRed().toString() + (char)(qfr.getSeat().getKolona() + 64));
			dto.setDiscount(qfr.getDiscount());
			
			dto.setFlightNumber(qfr.getSeat().getFlight().getFlightNumber());
			dto.setDeparture(qfr.getSeat().getFlight().getDeparture().getName());
			dto.setDestination(qfr.getSeat().getFlight().getDestination().getName());
			dto.setDate(qfr.getSeat().getFlight().getDepartureDate().toString());
			dto.setOriginalPrice(qfr.getSeat().getFlight().getTicketPrice());
			
			quickFlightReservations.add(dto);

		}
	}

	public AirlineProfileDTO getAirlineProfileDTO() {
		return airlineProfileDTO;
	}

	public void setAirlineProfileDTO(AirlineProfileDTO airlineProfileDTO) {
		this.airlineProfileDTO = airlineProfileDTO;
	}

	public AirlineDTO() {
		super();
	}

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Destination> destinations) {
		this.destinations = destinations;
	}

	public Set<QuickFlightReservationDTO> getQuickFlightReservations() {
		return quickFlightReservations;
	}

	public void setQuickFlightReservations(Set<QuickFlightReservationDTO> quickFlightReservations) {
		this.quickFlightReservations = quickFlightReservations;
	}

}
