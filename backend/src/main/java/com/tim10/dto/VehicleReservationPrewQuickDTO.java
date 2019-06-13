package com.tim10.dto;

import java.util.Date;

import com.tim10.domain.PriceList;

public class VehicleReservationPrewQuickDTO extends VehicleReservationPrewDTO {

	private Long reservationId;

	public VehicleReservationPrewQuickDTO(Long reservationId) {
		super();
		this.reservationId = reservationId;
	}

	public VehicleReservationPrewQuickDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleReservationPrewQuickDTO(Long rentACarId, String rentACarName, Long branchOfficeId, String country,
			String city, Long vehicleId, PriceList priceList, Date from, Date to) {
		super(rentACarId, rentACarName, branchOfficeId, country, city, vehicleId, priceList, from, to);
		// TODO Auto-generated constructor stub
	}
	
	
	public VehicleReservationPrewQuickDTO(Long rentACarId, String rentACarName, Long branchOfficeId, String country,
			String city, Long vehicleId, PriceList priceList, Date from, Date to, Long reservationId) {
		super(rentACarId, rentACarName, branchOfficeId, country, city, vehicleId, priceList, from, to);
		this.reservationId = reservationId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	
	
	
	
	
}
