package com.tim10.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.ReservationHistoryDTO;
import com.tim10.dto.ReviewDTO;
import com.tim10.service.ReviewService;

@RestController
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@RequestMapping(
			value = "/api/getReview",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReview(
			@RequestBody  ReservationHistoryDTO reservationHistoryDTO) {
		
		
		ReviewDTO reviewDTO;
		
		try {
			reviewDTO = reviewService.getReview(reservationHistoryDTO);
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ReviewDTO>(reviewDTO, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/api/setReview",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setReview(
			@RequestBody  ReviewDTO reviewDTO) {
		
		try {
			reviewService.setReview(reviewDTO);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Review is done", HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/api/declineRoomReservation",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> declineRoomReservation(
			@RequestBody  ReviewDTO reviewDTO){
		
		try {
			reviewService.declineRoomReservation(reviewDTO.getDepartureDate(), reviewDTO.getReservationId());
		}catch(ParseException e) {
			return new ResponseEntity<String>("Los datum", HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("Trebalo je ranije da se otkaze", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Room reservation is declined", HttpStatus.OK);
		
	}
	
	
	@RequestMapping(
			value = "/api/declineVehicleReservation",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> declineVehicleReservation(
			@RequestBody ReservationHistoryDTO reservationHistoryDTO){
		
		try {
			reviewService.declineVehicleReservation(reservationHistoryDTO.getDepartureDate(), reservationHistoryDTO.getReservationId());
		}catch(ParseException e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Vehicle reservation is declined", HttpStatus.OK);
		
	}

}
