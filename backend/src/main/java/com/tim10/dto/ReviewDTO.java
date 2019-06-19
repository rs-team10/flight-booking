package com.tim10.dto;

import java.util.Date;

public class ReviewDTO {

	
	private Long reservationId;
    
	private Long flightId; 
	private String departureName;  
	private String destinationName; 
	private Date departureDate;   
	private Long airlineId; 
	private String airlineName;

	private Long roomTypeId;  
	private Long hotelId; 
	private String roomTypeName;
	private String hotelName;

	private Long vehicleId;  
	private Long rentACarId;
	private String rentACarName;
	private String manufacturer;
	private String model;
	
	
	private double ratingAirline;
	private double ratingFlight;
	private double ratingHotel;
	private double ratingRoomType;
	private double ratingRentACar;
	private double ratingVehicle;
	private Long flightReservationId;
	private Long roomReservationId;
	private Long vehicleReservationId;
	
	
	
	public ReviewDTO() {
		
	}
	
	public ReviewDTO(ReservationHistoryDTO reservationHistoryDTO) {
		
		this.reservationId = reservationHistoryDTO.getReservationId();
		this.flightId = reservationHistoryDTO.getFlightId();
		this.departureName = reservationHistoryDTO.getDepartureName();
		this.destinationName = reservationHistoryDTO.getDestinationName();
		this.departureDate = reservationHistoryDTO.getDepartureDate();
		this.airlineId = reservationHistoryDTO.getAirlineId();
		this.roomTypeId = reservationHistoryDTO.getRoomTypeId();
		this.hotelId = reservationHistoryDTO.getHotelId();
		this.vehicleId = reservationHistoryDTO.getVehicleId();
		this.rentACarId = reservationHistoryDTO.getRentACarId();
		
		this.flightReservationId = reservationHistoryDTO.getFlightReservationId();
		this.roomReservationId = reservationHistoryDTO.getRoomReservationId();
		this.vehicleReservationId = reservationHistoryDTO.getVehicleReservationId();
		
		this.ratingAirline = 0;
		this.ratingFlight = 0;
		this.ratingHotel = 0;
		this.ratingRoomType = 0;
		this.ratingRentACar = 0;
		this.ratingVehicle = 0;
		
	}

	
	public ReviewDTO(Long reservationId, Long flightId, String departureName, String destinationName,
			Date departureDate, Long airlineId, String airlineName, Long roomTypeId, Long hotelId, String roomTypeName,
			String hotelName, Long vehicleId, Long rentACarId, String rentACarName, String manufacturer, String model,
			double ratingAirline, double ratingFlight, double ratingHotel, double ratingRoomType, double ratingRentACar,
			double ratingVehicle) {
		super();
		this.reservationId = reservationId;
		this.flightId = flightId;
		this.departureName = departureName;
		this.destinationName = destinationName;
		this.departureDate = departureDate;
		this.airlineId = airlineId;
		this.airlineName = airlineName;
		this.roomTypeId = roomTypeId;
		this.hotelId = hotelId;
		this.roomTypeName = roomTypeName;
		this.hotelName = hotelName;
		this.vehicleId = vehicleId;
		this.rentACarId = rentACarId;
		this.rentACarName = rentACarName;
		this.manufacturer = manufacturer;
		this.model = model;
		this.ratingAirline = ratingAirline;
		this.ratingFlight = ratingFlight;
		this.ratingHotel = ratingHotel;
		this.ratingRoomType = ratingRoomType;
		this.ratingRentACar = ratingRentACar;
		this.ratingVehicle = ratingVehicle;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public String getDepartureName() {
		return departureName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public Long getRoomTypeId() {
		return roomTypeId;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public String getHotelName() {
		return hotelName;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public Long getRentACarId() {
		return rentACarId;
	}

	public String getRentACarName() {
		return rentACarName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getModel() {
		return model;
	}

	public double getRatingAirline() {
		return ratingAirline;
	}

	public double getRatingFlight() {
		return ratingFlight;
	}

	public double getRatingHotel() {
		return ratingHotel;
	}

	public double getRatingRoomType() {
		return ratingRoomType;
	}

	public double getRatingRentACar() {
		return ratingRentACar;
	}

	public double getRatingVehicle() {
		return ratingVehicle;
	}

	public Long getFlightReservationId() {
		return flightReservationId;
	}

	public Long getRoomReservationId() {
		return roomReservationId;
	}

	public Long getVehicleReservationId() {
		return vehicleReservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setRentACarId(Long rentACarId) {
		this.rentACarId = rentACarId;
	}

	public void setRentACarName(String rentACarName) {
		this.rentACarName = rentACarName;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setRatingAirline(double ratingAirline) {
		this.ratingAirline = ratingAirline;
	}

	public void setRatingFlight(double ratingFlight) {
		this.ratingFlight = ratingFlight;
	}

	public void setRatingHotel(double ratingHotel) {
		this.ratingHotel = ratingHotel;
	}

	public void setRatingRoomType(double ratingRoomType) {
		this.ratingRoomType = ratingRoomType;
	}

	public void setRatingRentACar(double ratingRentACar) {
		this.ratingRentACar = ratingRentACar;
	}

	public void setRatingVehicle(double ratingVehicle) {
		this.ratingVehicle = ratingVehicle;
	}

	public void setFlightReservationId(Long flightReservationId) {
		this.flightReservationId = flightReservationId;
	}

	public void setRoomReservationId(Long roomReservationId) {
		this.roomReservationId = roomReservationId;
	}

	public void setVehicleReservationId(Long vehicleReservationId) {
		this.vehicleReservationId = vehicleReservationId;
	}



	
	
	
	
}
