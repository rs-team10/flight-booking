package com.tim10.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.FlightReservation;
import com.tim10.domain.Reservation;
import com.tim10.domain.Review;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;
import com.tim10.domain.Vehicle;
import com.tim10.domain.VehicleReservation;
import com.tim10.dto.ReservationHistoryDTO;
import com.tim10.dto.ReviewDTO;
import com.tim10.repository.ReservationRepository;

@Service
public class ReviewService {

	@Autowired
	private ReservationRepository reservationRepository;

	
	
	public ReviewDTO getReview(ReservationHistoryDTO reservationHistoryDTO) throws ResourceNotFoundException{
		

		Long reservationId = reservationHistoryDTO.getReservationId();
		
		Optional<Reservation> reservationMyb = reservationRepository.findById(reservationId);
		
		
		if(!reservationMyb.isPresent())
			throw new ResourceNotFoundException("Reservation with id: "+reservationId+" doesn't exist!");
		
		Reservation reservation = reservationMyb.get();
		
		ReviewDTO reviewDTO = new ReviewDTO(reservationHistoryDTO);
		
		
		FlightReservation flightReservation = reservation.getFlightReservation();
		RoomReservation roomReservation = reservation.getRoomReservation();
		VehicleReservation vehicleReservation = reservation.getVehicleReservation();
		
		
		String airlineName = flightReservation.getSeat().getFlight().getAirline().getName();
		reviewDTO.setAirlineName(airlineName);

		Review flightReservationReview = flightReservation.getReview();
		
		if(flightReservationReview!=null) {
			double ratingAirline = flightReservationReview.getCompanyFeedback()/2;
			double ratingFlight = flightReservationReview.getServiceFeedback()/2;
			reviewDTO.setRatingAirline(ratingAirline);
			reviewDTO.setRatingFlight(ratingFlight);
			
		}
		
	

		if(roomReservation != null) {
			RoomType roomType = reservation.getRoomReservation().getRoom().getRoomType();
			if(roomType!= null) {

				reviewDTO.setHotelName(roomReservation.getRoom().getHotel().getName());
				reviewDTO.setRoomTypeName(roomReservation.getRoom().getRoomType().toString());
			}
			
			Review roomReservationReview = roomReservation.getReview();
			
			if(roomReservationReview!=null) {
				double ratingHotel = roomReservationReview.getCompanyFeedback()/2;
				double ratingRoomType = roomReservationReview.getServiceFeedback()/2;
				reviewDTO.setRatingHotel(ratingHotel);
				reviewDTO.setRatingRoomType(ratingRoomType);
			}
		}
		
		if(vehicleReservation != null) {
			Vehicle vehicle = reservation.getVehicleReservation().getReservedVehicle();
			if(vehicle != null) {
				reviewDTO.setModel(vehicle.getModel());
				reviewDTO.setManufacturer(vehicle.getManufacturer());
				reviewDTO.setRentACarName(vehicle.getBranchOffice().getMainOffice().getName());
			}
			
			
			Review vehicleReservationReview = vehicleReservation.getReview();
			
			if(vehicleReservationReview!=null) {
				double ratingRentACar = vehicleReservationReview.getCompanyFeedback()/2;
				double ratingVehicle = vehicleReservationReview.getServiceFeedback()/2;
				
				reviewDTO.setRatingRentACar(ratingRentACar);
				reviewDTO.setRatingVehicle(ratingVehicle);
			}
		}
		

		return reviewDTO;
		
	}
	
	public void setReview(ReviewDTO reviewDTO) throws ResourceNotFoundException{
		

		Long reservationId = reviewDTO.getReservationId();
		
		Optional<Reservation> reservationMyb = reservationRepository.findById(reservationId);
		
		
		if(!reservationMyb.isPresent())
			throw new ResourceNotFoundException("Reservation with id: "+reservationId+" doesn't exist!");
		
		Reservation reservation = reservationMyb.get();
		
		FlightReservation flightReservation = reservation.getFlightReservation();
		
		Integer ratingAirline = new Integer((int)(reviewDTO.getRatingAirline()*2));
		Integer ratingFlight = new Integer((int)(reviewDTO.getRatingFlight()*2));
		Integer ratingHotel = new Integer((int)(reviewDTO.getRatingHotel()*2));
		Integer ratingRoomType = new Integer((int)(reviewDTO.getRatingRoomType()*2));
		Integer ratingRentACar = new Integer((int)(reviewDTO.getRatingRentACar()*2));
		Integer ratingVehicle = new Integer((int)(reviewDTO.getRatingVehicle()*2));
		
		
		Long roomReservationId = reviewDTO.getRoomReservationId();
		Long vehicleReservationId = reviewDTO.getVehicleReservationId();
		
		if(ratingAirline!=0 || ratingFlight!=0) {
		
				
			Review flightReservationRew = flightReservation.getReview();
			
			if(flightReservationRew!=null) {
				flightReservationRew.setCompanyFeedback(ratingAirline);
				flightReservationRew.setServiceFeedback(ratingFlight);
				flightReservation.setReview(flightReservationRew);
			}else {
				
				Review review = new Review();
				review.setCompanyFeedback(ratingAirline);
				review.setServiceFeedback(ratingFlight);
				flightReservation.setReview(review);
			}
			reservation.setFlightReservation(flightReservation);
			
		}
		
		
		if(ratingHotel!=0 || ratingRoomType!=0) {
			if(roomReservationId!=null) {
				RoomReservation roomReservation = reservation.getRoomReservation();
				Review roomTypeReview = roomReservation.getReview();
				
				if(roomTypeReview!=null) {
					roomTypeReview.setCompanyFeedback(ratingHotel);
					roomTypeReview.setServiceFeedback(ratingRoomType);
					roomReservation.setReview(roomTypeReview);
				}else {
					
					Review review = new Review();
					review.setCompanyFeedback(ratingHotel);
					review.setServiceFeedback(ratingRoomType);
					roomReservation.setReview(review);
				}
				reservation.setRoomReservation(roomReservation);
			}
		}
		
		if(ratingRentACar!=0 || ratingVehicle!=0) {
			if(vehicleReservationId!=null) {
				VehicleReservation vehicleReservation = reservation.getVehicleReservation();
				Review vehicleResReview = vehicleReservation.getReview();
				
				if(vehicleResReview!=null) {
					vehicleResReview.setCompanyFeedback(ratingRentACar);
					vehicleResReview.setServiceFeedback(ratingVehicle);
					vehicleReservation.setReview(vehicleResReview);
				}else {
					Review review = new Review();
					review.setCompanyFeedback(ratingRentACar);
					review.setServiceFeedback(ratingVehicle);
					vehicleReservation.setReview(review);
				}
				reservation.setVehicleReservation(vehicleReservation);
			}
			
		}
		
		reservationRepository.save(reservation);
		
	}
	
	
	public void declineVehicleReservation(Date date, Long reservationId) throws ParseException, Exception{
		
	
		Date now = new Date();
		
		LocalDate cFrom = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate cTo = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int days = (int) ChronoUnit.DAYS.between(cFrom, cTo);
		
		if(days < 3)
			throw new Exception("You cannot cancil this reservation!");
		
		Reservation reservation = reservationRepository.getOne(reservationId);
		reservation.setVehicleReservation(null);
		reservationRepository.save(reservation);
		
		
	}
	
	public void declineRoomReservation(Date date, Long reservationId) throws ParseException, Exception {
		
	
		Date now = new Date();
		
		LocalDate cFrom = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate cTo = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int days = (int) ChronoUnit.DAYS.between(cTo, cFrom);
        
		
		if(days < 3)
			throw new Exception("You cannot cancil this reservation!");
		
		
				
		Reservation reservation = reservationRepository.getOne(reservationId);
		reservation.setRoomReservation(null);
		reservationRepository.save(reservation);
		
	}
	
	
	
	
}
