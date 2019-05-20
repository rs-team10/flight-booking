package com.tim10.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tim10.domain.Hotel;
import com.tim10.domain.Room;
import com.tim10.domain.RoomReservation;
import com.tim10.domain.RoomType;

public class HotelReportDTO {
	
	//Prosecna ocena hotela
	public Double averageFeedback;
	public Integer numberOfFeedbacks;
	
	//Prosecna ocena soba
	public Set<RoomType> roomTypes;
	
	
	public HotelReportDTO() {
		super();
	}
	
	public HotelReportDTO(Hotel hotel) {
		this.averageFeedback = hotel.getAverageFeedback() != null ? hotel.getAverageFeedback() : 0;
		this.numberOfFeedbacks = 0;
		for(Room room : hotel.getRooms()) {
			for(RoomReservation roomReservation : room.getRoomReservations()) {
				if(roomReservation.getReview() != null) {
					if(roomReservation.getReview().getCompanyFeedback() != null)
						numberOfFeedbacks += 1;
				}
			}
		} 
		this.roomTypes = hotel.getRoomTypes();
		
	}
	
}
